package exercises

import lectures.part2oop.Generics.MyList

abstract class MyList[+A] {

  /*
      head = first element of the list
      tail = remainder of the list
      isEmpty = is this list empty
      add(int) => new list with this element added
      toString => a string representation of the list
   */

  /**
   *  1.  Generic trait MyPredicate[-T] with a little method test(T) => Boolean
   *  2.  Generic trait MyTransformer[-A, B] with a method transform(A) => B
   *  3.  MyList:
   *      - map(transformer) => MyList
   *      - filter(predicate) => MyList
   *      - flatMap(transformer from A to MyList[B]) => MyList
   *
   *  class EvenPredicate extends MyPredicate[Int]
   *  class StringToIntTransformer extends MyTransformer[String, Int]
   *
   *  [1, 2, 3].map(n * 2) = [2, 4, 6]
   *  [1, 2, 3, 4].filter(n % 2) = [2, 4]
   *  [1, 2, 3].flatMap(n => [n, n + 1]) => [1, 2, 2, 3, 3, 4]
   */

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transformer: A => B): MyList[B]
  def withFilter(predicate: A => Boolean): MyList[A]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]

  /**
   *  Expand MyList
   *  - foreach method A => Unit
   *    [1, 2, 3].foreach(x => println(x))
   *
   *  - sort function ((A, A) => Int) => MyList
   *    [1, 2, 3].sort((x, y) => y - x) => [3, 2, 1]
   *
   *  - zipWith (list, (A, A) => B) => MyList[B]
   *    [1, 2, 3].zipWith([4, 5, 6], x * y) => [1 * 4, 2 * 5, 3 * 6] = [4, 10, 18]
   *
   *  - fold(start)(function) => a value
   *    [1, 2, 3].fold(0)(x + y) = 6
   */
  def foreach(f: A => Unit): Unit
  def sort(f: (A, A) => Int): MyList[A]

  /**
   *  MyList supports for comprehension
   *  - map(f: A => B) => MyList[B]
   *  - filter(p: A => Boolean) => MyList[A]
   *  - flatMap(f: A => MyList[B]) => MyList[B]
   */
}

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList[B] = Empty
  def withFilter(predicate: Nothing => Boolean): MyList[Nothing] = Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  def foreach(f: Nothing => Unit): Unit = ()
  def sort(f: (Nothing, Nothing) => Int): MyList[Nothing] = Empty
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = Cons(element, this)
  def printElements: String =
    if(t.isEmpty) "" + h
    else          h + " " + tail.printElements

  /*
    [1, 2, 3].map(n * 2)
      = new Cons(2, [2, 3].map(n * 2)
      = new Cons(2, new Cons(4, [3].map(n * 2)))
      = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
      = new Cons(2, new Cons(4, new Cons(6)))
      = [2, 4, 6]
   */
  def map[B](transformer: A => B): MyList[B] = Cons(transformer(h), t.map(transformer))
  /*
    [1, 2, 3].filter(n % 2 == 0)
     = [2, 3].filter(n % 2 == 0)
     = new Cons(2, [3].filter(n % 2 == 0))
     = new Cons(2, Empty.filter(n % 2 == 0))
     = new Cons(2, Empty)
     = [2]
   */
  def withFilter(predicate: A => Boolean): MyList[A] =
    if (predicate(h)) Cons(h, t.withFilter(predicate))
    else t.withFilter(predicate)
  /*
    [1, 2, 3].flatMap(n => [n, n + 1])
      = (1 => [1, 2]) ++ [2, 3].flatMap(n => [n, n + 1])
      = [1, 2] ++ (2 => [2, 3]) ++ [3].flatMap(n => n + 1])
      = [1, 2] ++ [2, 3] ++ (3 => [3, 4]) ++ Empty.flatMap(n => n + 1)
      = [1, 2] ++ [2, 3] ++ [3, 4] ++ Empty
      = [1, 2, 2, 3, 3, 4]
   */
  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)

  /*
    [1, 2] ++ [3, 4, 5]
      = new Cons(1, [2] ++ [3, 4, 5])
      = new Cons(1, new Cons(2, Empty ++ [3, 4, 5]))
      = new Cons(1, new Cons(2, [3, 4, 5]))
      == new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5, Empty)))))
      = [1, 2, 3, 4, 5]
   */
  def ++[B >: A](list: MyList[B]): MyList[B] = Cons(h, t ++ list)

  def foreach(f: A => Unit): Unit = {
    f(h)
    tail.foreach(f)
  }
  def sort(f: (A, A) => Int): MyList[A] = {

    def sweep(x: A, y: A): MyList[A] = {
      if (f(x, y) > 0) Cons(x, Cons(y, Empty))
      else Cons(y, Cons(x, Empty))
    }

    if (t.isEmpty) this
    else sweep(h, t.head) ++ tail.sort(f)
  }
}

object ListTest extends App {
  val listOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val cloneListOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val anotherListOfIntegers: MyList[Int] = Cons(1, Cons(4, Cons(5, Empty)))
  val listOfStrings: MyList[String] = Cons("Hello", Cons("Scala", Empty))
  val a = List(1,2)

  println(listOfIntegers)
  println(listOfStrings)

  println(listOfIntegers.map(_ * 2))
  listOfIntegers.withFilter(_ % 2 == 0)
  listOfIntegers.flatMap(element => Cons(element, Cons(element + 1, Empty)))

  println(listOfIntegers ++ anotherListOfIntegers)

  println(cloneListOfIntegers == listOfIntegers)

  listOfIntegers.foreach(println)

  // for comprehension
  val combinations = for {
    n <- listOfIntegers if (n % 2 == 0)
    string <- listOfStrings
  } yield n + "-" + string
  println(combinations)

  for {
    string <- listOfStrings
  } println(string)

}
