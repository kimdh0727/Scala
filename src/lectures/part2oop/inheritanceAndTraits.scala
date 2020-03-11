package lectures.part2oop

object inheritanceAndTraits extends App {

  //  single class inheritance
  sealed class Animal {
    val creatureType = "wild"
    protected def eat: Unit = println("nomnom")
    def show(): Unit = this.eat
  }

  class Cat extends Animal {  // Cat: Sub class, Animal: Super class
    def crunch: Unit = {
      eat
      println("crunch crunch")
    }
  }

  val cat = new Cat
//  cat.eat
  cat.crunch

  //  constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  //  overriding
  class Dog(override val creatureType: String = "domestic") extends Animal {
//    override val creatureType: String = "domestic"
    override def eat: Unit = {
      super.eat
      println("crunch, crunch")
    }
  }


//  SAME
//  class Dog(dogType: String) extends Animal {
//    override val creatureType: String = dogType
//  }

  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  //  type substitution (broad: polymorphism)
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.show

  //  overRIDING vs overLOADING

  //  super

  //  preventing overrides: can not override member
  //  1 - use final on member
  //  2 - use final on the entire class
  //  3 - seal the class = extend classes in THIS FILE, prevent extension in other file
}
