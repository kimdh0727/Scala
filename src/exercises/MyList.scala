package exercises

abstract class MyList[+A] {

  /*
      head = first element of the list
      tail = remainder of the list
      isEmpty = is this list empty
      add(int) => new list with this element added
      toString => a string representation of the list
   */

  /*
  1.  Generic trait MyPredicate[-T] with a little method test(T) => Boolean
  2.  Generic trait MyTransformer[-A, B] with a method transform(A) => B
  3.  MyList:
      - map(transformer) => MyList
      - filter(predicate) => MyList
      - flatMap(transformer from A to MyList[B]) => MyList

      class EvenPredicate extends MyPredicate[Int]
      class StringToIntTransformer extends MyTransformer[String, Int]

      [1, 2, 3].map(n * 2) = [2, 4, 6]
      [1, 2, 3, 4].filter(n % 2) = [2, 4]
      [1, 2, 3].flatMap(n => [n, n + 1]) => [1, 2, 2, 3, 3, 4]
   */

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]
  def flapMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]

  def map[B](transform: A => B): MyList[B]
  def filter(test: A => Boolean): MyList[A]
  def flapMap[B](transform: A => MyList[B]): MyList[B]

  def ++[B >: A](list: MyList[B]): MyList[B]
}

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def flapMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty

  def map[B](transform: Nothing => B): MyList[B] = Empty
  def filter(test: Nothing => Boolean): MyList[Nothing] = Empty
  def flapMap[B](transform: Nothing => MyList[B]): MyList[B] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
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
  def map[B](transformer: MyTransformer[A, B]): MyList[B] =
    Cons(transformer.transform(h), t.map(transformer))
  /*
    [1, 2, 3].filter(n % 2 == 0)
     = [2, 3].filter(n % 2 == 0)
     = new Cons(2, [3].filter(n % 2 == 0))
     = new Cons(2, Empty.filter(n % 2 ==0))
     = new Cons(2, Empty)
     = [2]
   */
  def filter(predicate: MyPredicate[A]): MyList[A] =
    if (predicate.test(h)) Cons(h, t.filter(predicate))
    else t.filter(predicate)
  /*
    [1, 2, 3].flatMap(n => [n, n + 1])
      = (1 => [1, 2]) ++ [2, 3].flatMap(n => [n, n + 1])
      = [1, 2] ++ (2 => [2, 3]) ++ [3].flatMap(n => n + 1])
      = [1, 2] ++ [2, 3] ++ (3 => [3, 4]) ++ Empty.flatMap(n => n + 1)
      = [1, 2] ++ [2, 3] ++ [3, 4] ++ Empty
      = [1, 2, 2, 3, 3, 4]
   */
  def flapMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
    transformer.transform(h) ++ t.flapMap(transformer)

  def map[B](transform: A => B): MyList[B] = Cons(transform(h), t.map(transform))
  def filter(test: A => Boolean): MyList[A] =
    if (test(h)) Cons(h, t.filter(test))
    else t.filter(test)
  def flapMap[B](transform: A => MyList[B]): MyList[B] =
    transform(h) ++ t.flapMap(transform)

  /*
    [1, 2] ++ [3, 4, 5]
      = new Cons(1, [2] ++ [3, 4, 5])
      = new Cons(1, new Cons(2, Empty ++ [3, 4, 5]))
      = new Cons(1, new Cons(2, [3, 4, 5]))
      == new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5, Empty)))))
      = [1, 2, 3, 4, 5]
   */
  def ++[B >: A](list: MyList[B]): MyList[B] = Cons(h, t ++ list)
}

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

object ListTest extends App {
  val listOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val cloneListOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val anotherListOfIntegers: MyList[Int] = Cons(1, Cons(4, Cons(5, Empty)))
  val listOfStrings: MyList[String] = Cons("Hello", Cons("Scala", Empty))
  val a = List(1,2)

  println(listOfIntegers)
  println(listOfStrings)

  println(listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(element: Int): Int = element * 2
  }))

  println(listOfIntegers.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element % 2 == 0
  }))

  println(listOfIntegers.flapMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(element: Int): MyList[Int] = Cons(element, Cons(element + 1, Empty))
  }))

  println(listOfIntegers.map((element: Int) => element * 2))
  listOfIntegers.filter((element: Int) => element % 2 == 0)
  listOfIntegers.flapMap((element: Int) => Cons(element, Cons(element + 1, Empty)))

  println(listOfIntegers ++ anotherListOfIntegers)

  println(cloneListOfIntegers == listOfIntegers)

}
