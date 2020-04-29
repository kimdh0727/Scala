// Chapter22_ImplementingLists.sc
import scala.collection.mutable.ListBuffer

abstract class Fruit
class Apple extends Fruit
class Orange extends Fruit

val apples = new Apple :: Nil
val fruits = new Orange :: apples

def incAll(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case x :: xs1 => x + 1 :: incAll(xs1)
}

def incAll2(xs: List[Int]): List[Int] = {
  var result = List[Int]()
  for (x <- xs) result = result ::: List(x + 1)
  result
}

def incAll3(xs: List[Int]): List[Int] = {
  val buf = new ListBuffer[Int]
  for (x <- xs) buf += x + 1
  buf.toList
}
