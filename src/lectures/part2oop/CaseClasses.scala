package lectures.part2oop

object CaseClasses extends App {

  /*
    equals, hashCode, toString
   */

  class Person(var name: String, age: Int) {
    var x = 1
  }

  // 1. class parameters are fields
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. sensible toString
  // println(instance) = println(instance.toString // syntactic sugar
  println(jim.toString)

  // 3. equals and hashCode implemented OOTB (out of the box)
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)
  println(jim.hashCode())
  println(jim2.hashCode())
  jim2.x = 2
  println(jim == jim2)
  println(jim.hashCode())
  println(jim2.hashCode())

  println(jim.x)
  println(jim2.x)
  println(jim.x == jim2.x)
  // 4. CCs have handy copy method
//  val jim3 = jim.copy(age = 45)
//
//  // 5. CCs have companion objects
//  val thePersom = Person
//  val mary = Person("Mary", 23)

  // 6. CCs are serializable
  // Akka

  // 7. CCs have extractor patterns = CCs can be used in PATTERN MATCHING

  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  /*
    Expand MyList - use case classes and case objects
   */
}
