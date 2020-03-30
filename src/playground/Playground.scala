package playground

object Playground extends App {

  val colors = List("red", "blue", "green") // List init
  println(colors.head)
  println(colors.tail)

  val fiveInts = new Array[Int](5)
  println(fiveInts mkString ", ")

  val fiveToOne = Array(5, 4, 3 ,2, 1)
  println(fiveToOne mkString ", ")

  fiveInts(0) = fiveToOne(4)
  println(fiveInts mkString ", ")

  import scala.collection.mutable.ListBuffer

  val buf = new ListBuffer[Int]
  buf += 1
  buf += 2
  println(buf)
  3 +=: buf
  println(buf.toList)

  import scala.collection.mutable.ArrayBuffer

  val buf2 = new ArrayBuffer[Int]()
  buf2 += 12
  buf2 += 15
  0 +=: buf2
  println(buf2)
  println(buf2.length)
  println(buf2(0))

  def hasUpperCase(s: String) = s.exists(_.isUpper)
  println(hasUpperCase("Robert Frost"))
  println(hasUpperCase("e e cummings"))


}
