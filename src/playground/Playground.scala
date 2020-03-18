//package playground
//
//object Playground extends App {
//
//  def above(that: Element): Element = {
//    val this1 = this widen that.width
//    val that1 = that widen this.width
//    assert(this1.width == that1.width)
//    elem(this1.contents ++ that1.contents)
//  }
//
//  private def widen(w: Int): Element =
//    if (w <= width)
//      this
//    else {
//      val left = elem(' ', (w - width) / 2, height)
//      val right = elem(' ', w - width - left.width, height)
//      left beside this beside right
//    } ensuring(w <= _.width)
//}

//package bobsdelights
//
//abstract class Fruit (val name: String, val color: String)
//
//object Fruits {
//  object Apple extends Fruit("apple", "red")
//  object Orange extends Fruit("orange", "orange")
//  object Pear extends Fruit("pear", "yellowish")
//  val menu = List(Apple, Orange, Pear)
//}
//
//// easy access to Fruit
//import bobsdelights.Fruit
//
//// easy access to all members of bobsdelights
//import bobsdelights._
//
//// easy access to all members of Fruits
//import bobsdelights.Fruit._

//class Outer {
//  class Inner {
//    private def f() = println("f")
//
//    class InnerMost {
//      f()   // OK
//    }
//  }
//  (new Inner).f() // error: f is not accessible
//}
//
//package p {
//  class Super {
//    protected def f() = println("f")
//  }
//  class Sub extends Super {
//    f()   // OK
//  }
//  class Other {
//    (new Super).f() // error: f is not accessible
//  }
//}

//package bobsrockets
//
//package navigation {
//  private[bobsrockets] class Navigator {
//    protected [navigation] def useStarChart() = {}
//    class LegOfJourney {
//      private[Navigator] val distance = 100
//    }
//    private[this] var speed = 200
//  }
//}
//package launch {
////  import navigation._
//
//  object Vehicle {
//    private[launch] val guide = new Navigator
//  }
//}
//
//class Rocket {
//  import Rocket.fuel
//  private def canGoHomeAgain = fuel > 20
//}
//object Rocket {
//  private def fuel = 10
//  def chooseStrategy(rocket: Rocket) = {
//    if (rocket.canGoHomeAgain)
//      goHome()
//    else
//      pickAStar()
//  }
//  def goHome() = {}
//  def pickAStar() = {}
//}
//
//// bobsdelights/package.scala
//package object bobsdelights {
//  def showFruit(fruit: Fruit) = {
//    import fruit._
//    println(name + "s are " + color)
//  }
//}

// PrintMenu.scala
//package printmenu
//improt bobsdelights.Fruits
//improt bobsdelights.showFruit
//object PrintMenu {
//  def main(args: Array[String]) = {
//    for (fruit <- Fruits.menu) {
//      showFruit(fruit)
//    }
//  }
//}



object test extends App {

}
