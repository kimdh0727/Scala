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

package bobsdelights

abstract class Fruit (val name: String, val color: String)

object Fruits {
  object Apple extends Fruit("apple", "red")
  object Orange extends Fruit("orange", "orange")
  object Pear extends Fruit("pear", "yellowish")
  val menu = List(Apple, Orange, Pear)
}

// easy access to Fruit
import bobsdelights.Fruit

// easy access to all members of bobsdelights
import bobsdelights._

// easy access to all members of Fruits
import bobsdelights.Fruit._

object test extends App {

}