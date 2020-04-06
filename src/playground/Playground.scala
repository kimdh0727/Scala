package playground

object Playground extends App {

  class BankAccount {
    private var bal: Int = 0
    def balance: Int = bal
    def deposit(amount: Int) = {
      require(amount > 0)
      bal += amount
    }
    def withdraw(amount: Int): Boolean = {
      if (amount > bal) false
      else {
        bal -= amount
        true
      }
    }
  }

  val account = new BankAccount
  account deposit 100
  println(account withdraw 80)
  println(account withdraw 80)

  class Keyed {
    def computeKey: Int = ??? // this will take some time
  }

  class MemoKeyed extends Keyed {
    private var keyCache: Option[Int] = None
    override def computeKey: Int = {
      if (!keyCache.isDefined)
        keyCache = Some(super.computeKey)
      keyCache.get
    }
  }

//  class Time {
//    var hour = 12
//    var minute = 0
//  }

  class Time {
    private[this] var h = 12
    private[this] var m = 0

    def hour: Int = h
    def hour_=(x: Int): Unit = {
      require(0 <= x && x < 24)
      h = x
    }

    def minute: Int = m
    def minute_=(x: Int): Unit = {
      require(0 <= x && x < 60)
      m = x
    }
  }

  class Thermometer {
    var celsius: Float = _

    def fahrenheit: Float = celsius * 9 / 5 + 32
    def fahrenheit_= (f: Float): Unit = {
      celsius = (f - 32) * 5 / 9
    }
    override def toString = fahrenheit + " F / " + celsius + " C"
  }

  val t = new Thermometer
  t.celsius = 100
  println(t)
  t.fahrenheit = - 40
  println(t)
}

abstract class Simulation {
  type Action = () => Unit

  case class WorkItem(time: Int, action: Action)

  private var curtime = 0
  def currentTime: Int = curtime

  private var agenda: List[WorkItem] = List()

  private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] = {
    if (ag.isEmpty || item.time < ag.head.time) item :: ag
    else ag.head :: insert(ag.tail, item)
  }

  def afterDelay(delay: Int)(block: => Unit) = {
    val item = WorkItem(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }

  private def next() = {
    (agenda: @unchecked) match {
      case item :: rest =>
        agenda = rest
        curtime = item.time
        item.action()
    }
  }

  def run() = {
    afterDelay(0) {
      println(s"*** simulation started, time = $currentTime ***")
    }
    while (!agenda.isEmpty) next()
  }
}

abstract class BasicCircuitSimulation extends Simulation {
  def InvertDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int

  class Wire {
    private var sigVal = false
    private var actions: List[Action] = List()

    def getSignal = sigVal

    def setSignal(s: Boolean) =
      if (s != sigVal) {
        sigVal = s
        actions foreach(_ ())
      }
    def addAction(a: Action) = {
      actions = a :: actions
      a()
    }
  }

  def inverter(input: Wire, output: Wire): Unit = {
    def invertAction(): Unit = {
      val inputSig = input.getSignal
      afterDelay(InvertDelay) {
        output setSignal !inputSig
      }
    }
    input addAction invertAction
  }

  def andGate(a1: Wire, a2: Wire, output: Wire): Unit = {
    def andAction(): Unit = {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(AndGateDelay) {
        output setSignal (a1Sig & a2Sig)
      }
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  def orGate(o1: Wire, o2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      val o1Sig = o1.getSignal
      val o2Sig = o2.getSignal
      afterDelay(OrGateDelay) {
        output setSignal (o1Sig | o2Sig)
      }
    }
    o1 addAction orAction
    o2 addAction orAction
  }

  def probe(name: String, wire: Wire): Unit = {
    def probeAction(): Unit = {
      println(s"$name $currentTime new-value = ${wire.getSignal}")
    }
    wire addAction probeAction
  }
}

abstract class CircuitSimulations extends BasicCircuitSimulation {

  def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) = {
    val d, e = new Wire
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)
  }

  def fullAdder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire) = {
    val s, c1, c2 = new Wire
    halfAdder(a, cin, s, c1)
    halfAdder(b, s, sum, c2)
    orGate(c1, c2, cout)
  }
}

object MySimulation extends CircuitSimulations with App {
  override def InvertDelay: Int = 1
  override def AndGateDelay: Int = 3
  override def OrGateDelay: Int = 5

  val input1, input2, sum, carry = new Wire
  probe("sum", sum)
  probe("carry", carry)

  halfAdder(input1, input2, sum, carry)
  input1 setSignal true
  run()
  input2 setSignal true
  run()
}