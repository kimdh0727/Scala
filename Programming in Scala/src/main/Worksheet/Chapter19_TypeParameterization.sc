// Chapter19_TypeParametrization.sc

//  val q = Queue(1, 2, 3)
//  val q1 = q enqueue 4
//  q
//  q1

//  trait Queue[+T] {
//    def head: T
//
//    def tail: Queue[T]
//
//    def enqueue[U >: T](x: U): Queue[U]
//  }
//
//  object Queue {
//
//    def apply[T](xs: T*): Queue[T] = new QueueImpl[T](xs.toList, Nil)
//
//    private class QueueImpl[T](private val leading: List[T], private val trailing: List[T]) extends Queue[T] {
//      def mirror =
//        if (leading.isEmpty)
//          new QueueImpl(trailing.reverse, Nil)
//        else
//          this
//
//      def head = mirror.leading.head
//
//      def tail = {
//        val q = mirror
//        new QueueImpl(q.leading.tail, q.trailing)
//      }
//
//      def enqueue[U >: T](x: U) =
//        new QueueImpl(leading, x :: trailing)
//    }
//
//  }

class Cell[T](init: T) {
  private[this] var current = init

  def get = current

  def set(x: T) = {
    current = x
  }
}

//  val c1 = new Cell[String]("abc")
//  val c2: Cell[Any] = c1
//  c2.set(1)
//  val s: String = c1.get

val a1 = Array("abc")
//  val a2: Array[Any] = a1

val a2: Array[Object] = a1.asInstanceOf[Array[Object]]

//  class StrangeIntQueue extends Queue[Int] {
//    override def enqueue(x: Int): Queue[Int] = {
//      println(math.sqrt(x))
//      super.enqueue(x)
//    }
//
//    override def head: Int = ???
//
//    override def tail: Queue[Int] = ???
//  }
//
//  val x: Queue[Any] = new StrangeIntQueue
//  x.enqueue("abc")

class Queue[+T] private(
                        private[this] var leading: List[T],
                        private[this] var trailing: List[T]
                      ) {
  private def mirror() =
    if (leading.isEmpty) {
      while (!trailing.isEmpty) {
        leading = trailing.head :: leading
        trailing = trailing.tail
      }
    }

  def head: T = {
    mirror()
    leading.head
  }

  def tail: Queue[T] = {
    mirror()
    new Queue(leading.tail, trailing)
  }

  def enqueue[U >: T](x: U) =
    new Queue[U](leading, x :: trailing)
}

object Queue {
  def apply[T](xs: T*) = new Queue[T](xs.toList, Nil)
}

abstract class Cat[-T, +U] {
  def meow[W](volume: T, listener: Cat[U, T])
  : Cat[Cat[U, T], U] = ???
}

abstract class Fruit
case class Apple() extends Fruit
case class Orange() extends Fruit

val fruits = Queue[Apple](Apple())
fruits enqueue (Orange()).getClass

trait OutputChannel[-T] {
  def write(x: T)
}

trait Function1[-S, T] {
  def apply(x: S): T
}

class Publication(val title: String)
class Book(title: String) extends Publication(title)

object Library {
  val books: Set[Book] =
    Set(
      new Book("Programming in Scala"),
      new Book("Walden")
    )

  def printBookList(info: Book => AnyRef) = {
    for (book <- books) println(info(book))
  }
}

object Customer {
  def getTitle(p: Publication): String = p.title
}

Library.printBookList(Customer.getTitle)

class Person(val firstName: String, val lastName: String)
  extends Ordered[Person] {
  override def compare(that: Person): Int = {
    val lastNameComparison =
      lastName.compareToIgnoreCase(that.lastName)
    if (lastNameComparison != 0)
      lastNameComparison
    else
      firstName.compareToIgnoreCase(that.firstName)
  }

  override def toString: String = firstName + " " + lastName
}

def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = {
  def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
    case (Nil, _) => ys
    case (_, Nil) => xs
    case (x :: xs1, y :: ys1) =>
      if (x < y) x :: merge(xs1, ys)
      else y :: merge(xs, ys1)
  }

  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (ys, zs) = xs splitAt n
    merge(orderedMergeSort(ys), orderedMergeSort(zs))
  }
}

val people = List(
  new Person("Larry", "Wall"),
  new Person("Anders", "Hejlsberg"),
  new Person("Guido", "van Rossum"),
  new Person("Alan", "Kay"),
  new Person("Yukihiro", "Matsumoto")
)

val sortedPeople = orderedMergeSort(people)
sortedPeople