package lectures.part2oop

object OOBasics extends App {

  val person = new Person("John", 26)
  println(person.age)
  println(person.x)
  person.greet("Daniel")
  person.greet()
  val person1 = new Person

  val author = new Writer("Charles", "Dickens", 1812)
  val imposter = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)

  println(novel.authorAge)
  println(novel.isWrittenBy(author))
  println(novel.isWrittenBy(imposter))

  val counter = new Counter
  println(counter.currentCount)
  println(counter.increment.currentCount)
  println(counter.increment(10).currentCount)

}

//  constructor
class Person(name: String, val age: Int) {
  //  body
  val x = 2

  println(1 + 3)

  //  method
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  //  overloading
  def greet(): Unit = println(s"Hi $name")

  //  multiple constructors
  def this(name: String) = this(name, 0)
  def this() = this("John Doe")

} //  NOT 'CODE BLOCK'

// class parameters are NOT FIELDS

/*
  Novel and a Writer

  Writer: first name, surname, year
    - method full name

  Novel: name, year of release, author
    - authorAge
    - isWrittenBy(author)
    - copy (new year of release) = new instance of Novel

 */

class Writer(firstName: String, surname: String, val year: Int) {
  def fullName: String = firstName + " " + surname
}

class Novel(name: String, yearOfRelease: Int, author: Writer) {
  def authorAge: Int = this.yearOfRelease - this.author.year
  def isWrittenBy(author: Writer): Boolean = this.author == author
  def copy (newYearOfRelease: Int): Novel = new Novel(this.name, newYearOfRelease, this.author)
}

/*
  Counter class
    - receives an int value
    - method current count
    - method to increment/decrement => new Counter
    - overload inc/dec to receive an amount
 */

class Counter(x: Int = 0) {
  def currentCount: Int = this.x
  def increment: Counter = new Counter(x + 1)
  def decrement: Counter = new Counter(x - 1)
  def increment(amount: Int): Counter = new Counter(x + amount)
  def decrement(amount: Int): Counter = new Counter(x - amount)
}
