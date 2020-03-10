package lectures.part2oop

object AbstractDataTypes extends App {

  //  abstract
  abstract class Animal {
    val creatureTyps: String
    def eat(): Unit
  }

  class Dog extends Animal {
    override val creatureTyps: String = "Canine"
    override def eat(): Unit = println("crunch crunch")
  }

  //  traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  class Crocodile extends Animal with Carnivore {
    override val creatureTyps: String = "croc"
    def eat(): Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureTyps}")
  }

  val x: Carnivore = new Crocodile
  val y: Animal = new Dog

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)
  croc.eat()

  /*
      traits vs abstract classes
      1.  Traits do not have constructor parameters
      2.  Multiple traits may be inherited by the same class
      3.  Traits = behavior, abstract class = "thing"
   */
}