//package playground

//object Playground extends App {
//
//}

//package bobsrockets {
//  package navigation {
//
//    class Navigator {
//      // bobsrockets.navigation.StarMap
//      val map = new StarMap
//
//    }
//
//    class StarMap
//  }
//  class Ship {
//    // bobsrockets.navigation.Navigator
//    val nav = new navigation.Navigator
//  }
//  package fleets {
//    class Fleet {
//      // bobsrockets.Ship
//      def addShip() = new Ship
//    }
//  }
//}

//package launch {
//  class Booster3
//}
//
//package bobsrockets {
//  package navigations {
//    package launch {
//      class Booster1
//    }
//
//    class MissionControl {
//      val booster1 = new launch.Booster1
//      val booster2 = new bobsrockets.launch.Booster2
//      val booster3 = new _root_.launch.Booster3
//    }
//
//  }
//
//  package launch {
//    class Booster2
//  }
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

package bobsrockets

package navigation {
  private[bobsrockets] class Navigator {
    protected [navigation] def useStarChart() = {}
    class LegOfJourney {
      private[Navigator] val distance = 100
    }
    private[this] var speed = 200
  }
}
package launch {
//  import navigation._

  object Vehicle {
    private[launch] val guide = new Navigator
  }
}

class Rocket {
  import Rocket.fuel
  private def canGoHomeAgain = fuel > 20
}
object Rocket {
  private def fuel = 10
  def chooseStrategy(rocket: Rocket) = {
    if (rocket.canGoHomeAgain)
      goHome()
    else
      pickAStar()
  }
  def goHome() = {}
  def pickAStar() = {}
}

// bobsdelights/package.scala
package object bobsdelights {
  def showFruit(fruit: Fruit) = {
    import fruit._
    println(name + "s are " + color)
  }
}

// PrintMenu.scala
package printmenu
improt bobsdelights.Fruits
improt bobsdelights.showFruit
object PrintMenu {
  def main(args: Array[String]) = {
    for (fruit <- Fruits.menu) {
      showFruit(fruit)
    }
  }
}



object test extends App {

}