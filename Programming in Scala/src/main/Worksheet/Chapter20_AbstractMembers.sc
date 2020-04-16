// Chapter20_AbstractMembers.sc
import scala.annotation.tailrec

trait Abstract {
  type T
  def transform(x: T): T
  val initial: T
  var current: T
}

class Concrete extends Abstract {
  type T = String
  def transform(x: T) = x + x
  val initial = "hi"
  var current = initial
}

abstract class Fruit {
  val v: String
  def m: String
}

abstract class Apple extends Fruit {
  val v: String
  val m: String
}

//abstract class BadApple extends Fruit {
//  def v: String
//  def m: String
//}

trait AbstractTime {
  var hour: Int
  var minute: Int
}

trait AbstractTime2 {
  def hour: Int
  def hour_=(x: Int)
  def minute: Int
  def minute_=(x: Int)
}

class ConcreteTime extends AbstractTime {
  var h: Int = _
  var m: Int = _
  override def hour = h
  override def hour_=(x: Int): Unit = (h = x)
  override def minute = m
  override def minute_=(x: Int): Unit = (m = x)
}

val t = new ConcreteTime
t.hour = 3
t.hour

trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
  require(denomArg != 0)
  private val g = gcd(numerArg, denomArg)
  val numer = numerArg / g
  val denom = denomArg / g
  @tailrec
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  override def toString: String = numer + " / " + denom
}

//new RationalTrait {
//  val numerArg: Int = 1 * 2
//  val denomArg: Int = 2 * 3
//}

new {
  val numerArg: Int = 1 * 2
  val denomArg: Int = 2 * 3
} with RationalTrait

object twoThirds extends {
  val numerArg = 2
  val denomArg = 3
} with RationalTrait

class RationalClass(n: Int, d: Int) extends {
  val numerArg = n
  val denomArg = d
} with RationalTrait {
  def + (that: RationalClass) = new RationalClass(
    numer * that.denom + that.numer * denom,
    denom * that.denom
  )
}

object Demo {
  val x = { println("initializing x"); "done" }
}

Demo
Demo.x

trait LazyRationalTrait {
  val numerArg: Int
  val denomArg: Int
  private lazy val g = {
    require(denomArg != 0)
    gcd(numerArg, denomArg)
  }
  lazy val numer = numerArg / g
  lazy val denom = denomArg / g
  @tailrec
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  override def toString: String = numer + " / " + denom
}

new LazyRationalTrait {
  val numerArg: Int = 1 * 2
  val denomArg: Int = 2 * 3
}

class Food
abstract class Animal {
  type SuitableFood <: Food
  def eat(food: SuitableFood)
}

class Grass extends Food
class Cow extends Animal {
  type SuitableFood = Grass
  override def eat(food: Grass): Unit = {}
}
class Fish extends Food
val bessy: Animal = new Cow
//bessy eat (new Fish)

class DogFood extends Food
class Dog extends Animal {
  type SuitableFood = DogFood
  override def eat(food: DogFood): Unit = {}
}

val lassie = new Dog
val bootsie = new Dog
lassie eat (new bootsie.SuitableFood)

class Outer {
  class Inner
}

val o1 = new Outer
val o2 = new Outer
new o1.Inner
//new Outer#Inner

object Color extends Enumeration {
  val Red, Green, Blue = Value
}

object Direction extends Enumeration {
  val North = Value("North")
  val East = Value("East")
  val South = Value("South")
  val West = Value("West")
}

for (d <- Direction.values) print(d + " ")

Direction.East.id

object Converter {
  var exchangeRate = Map(
    "USD" -> Map("USD" -> 1.0   , "EUR" -> 0.7596,
      "JPY" -> 1.211 , "CHF" -> 1.223),
    "EUR" -> Map("USD" -> 1.316 , "EUR" -> 1.0,
      "JPY" -> 1.594 , "CHF" -> 1.623),
    "JPY" -> Map("USD" -> 0.8257, "EUR" -> 0.6272,
      "JPY" -> 1.0   , "CHF" -> 1.018),
    "CHF" -> Map("USD" -> 0.8108, "EUR" -> 0.6160,
      "JPY" -> 0.982 , "CHF" -> 1.0)
  )
}

abstract class CurrencyZone {
  type Currency <: AbstractCurrency
  def make(amount: Long): Currency

  abstract class AbstractCurrency {
    val amount: Long
    def designation: String

    def +(that: Currency): Currency =
      make(this.amount + that.amount)
    def *(x: Double): Currency =
      make((this.amount * x).toLong)
    def - (that: Currency): Currency =
      make(this.amount - that.amount)
    def / (that: Double): Currency =
      make((this.amount / that).toLong)
    def / (that: Currency): Double =
      this.amount.toDouble / that.amount

    def from(other: CurrencyZone#AbstractCurrency): Currency =
      make(math.round(
        other.amount.toDouble * Converter.exchangeRate
        (other.designation)(this.designation)))
    private def decimals(n: Long): Int =
      if (n == 1) 0 else 1 + decimals(n / 10)

    override def toString =
      ((amount.toDouble / CurrencyUnit.amount.toDouble)
        formatted("%." + decimals(CurrencyUnit.amount) + "f")
        + " " + designation)
  }
  val CurrencyUnit: Currency
}

object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }
  type Currency = Dollar
  def make(x: Long) = new Dollar { val amount = x }
  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
}

object Europe extends CurrencyZone {
  abstract class Euro extends AbstractCurrency {
    def designation = "EUR"
  }
  type Currency = Euro
  def make(cents: Long) = new Euro {
    val amount = cents
  }
  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}

object Japan extends CurrencyZone {
  abstract class Yen extends AbstractCurrency {
    def designation = "JPY"
  }
  type Currency = Yen
  def make(yen: Long) = new Yen {
    val amount = yen
  }
  val Yen = make(1)
  val CurrencyUnit = Yen
}

val res0 = Japan.Yen from US.Dollar * 100

val res1 = Europe.Euro from res0

val res2 =  US.Dollar from res1