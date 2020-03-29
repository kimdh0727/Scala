package playground

import scala.util.Try

object Playground extends App {

  val fruit = "apples" :: "oranges" :: "pears" ::  Nil
  val nums = 1 :: 2 :: 3 :: 4 :: Nil
  val diag3 = (1 :: 0 :: 0 :: Nil) ::
              (0 :: 1 :: 0 :: Nil) ::
              (0 :: 0 :: 1 :: Nil) :: Nil
  val empty = Nil

  def isort(xs: List[Int]): List[Int] = xs match {
    case List() => List()
    case x :: xs1 => insert(x, isort(xs1))
  }
  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List() => List(x)
    case y :: ys => if (x <= y) x :: xs
                    else y :: insert(x, ys)
  }

  List(1, 2) ::: List(3, 4, 5)
  List() ::: List(1, 2, 3)
  List(1, 2, 3) ::: List(4)

  def append[T](xs: List[T], ys: List[T]): List[T] =
    xs match {
      case List() => ys
      case x :: xs1 => x :: append(xs1, ys)
    }

  val abcde = List('a', 'b', 'c', 'd', 'e')
  println(abcde.last)
  println(abcde.init)

  def rev[T](xs: List[T]): List[T] = xs match {
    case List() => xs
    case x :: xs1 => rev(xs1) ::: List(x)
  }

  println(abcde take 2)
  println(abcde drop 2)
  println(abcde splitAt 2)

  println(abcde(2))
  println(abcde.indices)

  println(List(List(1, 2), List(3, 4)).flatten)
//  println(List(1, 2, 3).flatten)

  println(abcde.indices zip abcde)
  println(abcde.zipWithIndex)
  println(List((1, 2), ('a', 'b')).unzip)
  println(List((1, 2, 3),(4, 5, 6)).unzip3)

  println(abcde.toString)
  println(abcde)

  println(abcde mkString("[", ",", "]"))
  println(abcde mkString "")
  println(abcde.mkString)

  val buf = new StringBuilder
  println(abcde addString (buf, "{", ";", "}"))

  val arr = abcde.toArray
  println(arr.mkString)
  println(arr.toList)

  val arr2 = new Array[Int](10)
  List(1, 2, 3) copyToArray(arr2, 3)
  println(arr2 mkString ", ")

  val it = abcde.iterator
  println(it.next)
  println(it.next)

  def msort[T](less: (T, T) => Boolean)
              (xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (less(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt(n)
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

  println(msort((x: Int, y: Int) => x < y)(List(5, 7, 1, 3)))

  val intSort = msort((x: Int, y: Int) => x < y) _
  val reverseIntSort = msort((x: Int, y: Int) => x > y) _

  val mixedInts = List(4, 1, 9, 0, 5, 8, 3, 6, 2, 7)
  println(intSort(mixedInts))
  println(reverseIntSort(mixedInts))

  val words = List("the", "quick", "brown", "fox")
  println(words map(_.toList))
  println(words flatMap(_.toList))
  words foreach println

  println(List(1, 2, 3, 4, 5) filter (_ % 2 == 0))
  println(List(1, 2, 3, 4, 5) partition  (_ % 2 == 0))
  println(List(1, 2, 3, 4, 5) find (_ % 2 == 0))
  println(List(1, 2, 3, 4, 5) find (_ < 0))

  println(List(1, 2, 3, -4 , -5) takeWhile (_ > 0))
  println(List(1, -2, 3, -4, 5) dropWhile (_ > 0))
  println(List(1, 2, 3, -4, 5) span (_ > 0))

  def hasZeroRow(m: List[List[Int]]) =
    m exists (row => row forall (_ == 0))
  println(hasZeroRow(diag3))

  def flattenLeft[T](xss: List[List[T]]) =
    (xss foldLeft List[T]()) (_ ::: _)

  def flattenRight[T](xss: List[List[T]]) =
    (xss foldRight List[T]()) (_ ::: _)

  def reverseLeft[T](xs: List[T]): List[T] =
    (xs foldLeft(List[T]()))((ys, y) => y :: ys)

  println(List(1, -3, 4, 2, 6) sortWith (_ < _))

  println(List.range(1, 5))
  println(List.range(1, 9, 2))
  println(List.range(9, 1, -3))

  println(List.fill(5)('a'))
  println(List.fill(2, 3)('b'))

  println(List.tabulate(5)(n => n * n))
  println(List.tabulate(5, 5)(_ * _))

  println(List.concat(List('a', 'b'), List('c')))
  println(List.concat(List(), List('b'), List('c')))
  println(List.concat())

  println((List(10, 20), List(3, 4, 5)).zipped.map(_ * _))
  println((List("abc", "de"), List(3, 2)).zipped.forall(_.length == _))
  println((List("abc", "de"), List(3, 2)).zipped.exists(_.length != _))

}
