// Chapter20_AbstractMembers.sc

trait Abstract {
  type T
  def transform(x: T): T
  val initial: T
  var current: T
}

class Concrete extends Abstract {
  type T = String
  def transform(x: T) = x + x
  val initial = "hi"
  var current = initial
}

abstract class Fruit {
  val v: String
  def m: String
}

abstract class Apple extends Fruit {
  val v: String
  val m: String
}

//abstract class BadApple extends Fruit {
//  def v: String
//  def m: String
//}

trait AbstractTime {
  var hour: Int
  var minute: Int
}

trait AbstractTime2 {
  def hour: Int
  def hour_=(x: Int)
  def minute: Int
  def minute_=(x: Int)
}

class ConcreteTime extends AbstractTime {
  var h: Int = _
  var m: Int = _
  override def hour = h
  override def hour_=(x: Int): Unit = (h = x)
  override def minute = m
  override def minute_=(x: Int): Unit = (m = x)
}

val t = new ConcreteTime
t.hour = 3
t.hour

trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
  require(denomArg != 0)
  private val g = gcd(numerArg, denomArg)
  val numer = numerArg / g
  val denom = denomArg / g
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  override def toString: String = numer + " / " + denom
}

new RationalTrait {
  val numerArg: Int = 1
  val denomArg: Int = 2
}

//new Rational1(expr)

object Color extends Enumeration {
  val Red = Value
  val Green = Value
  val Blue = Value
}

Color.Red
println(Color.Green)