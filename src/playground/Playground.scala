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

}
