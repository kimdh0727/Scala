// Chapter21_ImplicitConversionsAndParameters.sc

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton
import scala.annotation.tailrec

val button = new JButton
button.addActionListener(
  new ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      println("pressed!")
    }
  }
)

implicit def function2ActionListener(f: ActionEvent => Unit) =
  new ActionListener {
    override def actionPerformed(event: ActionEvent): Unit = f(event)
  }

button.addActionListener(
  function2ActionListener(
    (_: ActionEvent) => println("pressed!")
  )
)

button.addActionListener(
  (_: ActionEvent) => println("pressed!")
)

object MyConversions {
  implicit def stringWrapper(s: String):
    IndexedSeq[Char] = ???
  implicit def intToString(x: Int): String = ???
}

//import MyConversions.stringWrapper
// stringWrapper using ...

implicit def doubleToInt(x: Double) = x.toInt
val i: Int = 3.5

class Rational(val n: Int, val d: Int) {
  println("Created " + n + " / " + d)   //  Debugging Message
  println(require(d != 0))
  private val g = gcd(n.abs, d.abs)
  @tailrec
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  val numer: Int = n / g
  val denom: Int = d / g

  def this(n: Int) = this(n, 1)

  def + (that: Rational): Rational =
    new Rational (
      this.numer * that.denom + that.numer * this.denom,
      this.denom * that.denom
    )
  def + (i: Int): Rational =
    new Rational(numer + i * denom, denom)

  def - (that: Rational): Rational =
    new Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )
  def - (i: Int): Rational =
    new Rational(numer - i * denom, denom)

  def * (that: Rational): Rational =
    new Rational(this.numer * that.numer, this.denom * that.denom)
  def * (i: Int): Rational =
    new Rational(numer * i, denom)

  def / (that: Rational): Rational =
    new Rational(numer * that.denom, denom * that.numer)
  def / (i: Int): Rational =
    new Rational(numer, denom * i)

  def lessThan(that: Rational): Boolean =
    this.numer * that.denom < that.numer * this.denom
  def max(that: Rational): Rational =
    if (this.lessThan(that)) that else this

  override def toString: String = s"$numer / $denom"
}

val oneHalf = new Rational(1, 2)
oneHalf + oneHalf
oneHalf + 1

implicit def intToRational(x: Int): Rational =
  new Rational(x, 1)

1 + oneHalf

Map(1 -> "one", 2 -> "two", 3 -> "three")

case class Rectangle(width: Int, height: Int)

implicit class RectangleMake(width: Int) {
  def x(height: Int) = Rectangle(width, height)
}

val myRectangle = 3 x 4


// NOT WORKING IN worksheet
//class PreferredPrompt(val preference: String)
//
//object Greeter {
//  def greet(name: String)(implicit prompt: PreferredPrompt) = {
//    println("Welcome, " + name + ". The system is ready.")
//    println(prompt.preference)
//  }
//}
//
//val bobsPrompt = new PreferredPrompt("relax> ")
//Greeter.greet("Bob")(bobsPrompt)
//
//object JoesPrefs {
//  implicit val prompt = new PreferredPrompt("Yes, master> ")
//}
//
//import JoesPrefs._
//Greeter.greet("Joe")

//class PreferredPrompt(val preference: String)
//class PreferredDrink(val preference: String)
//
//object Greeter {
//  def greet(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink) = {
//    println("Welcome, " + name + ". The system is ready.")
//    print("But While you work, ")
//    println("why not enjoy a cup of " + drink.preference + "?")
//    println(prompt.preference)
//  }
//}
//
//object JoesPrefs {
//  implicit val prompt = new PreferredPrompt("Yes, master> ")
//  implicit val drink = new PreferredDrink("tea")
//}
//
//import JoesPrefs._
//Greeter.greet("Joe")

def maxListOrdering[T](elements: List[T])
                      (ordering: Ordering[T]): T =
  elements match {
    case List() =>
      throw new IllegalArgumentException("empty list!")
    case List(x) => x
    case x :: rest =>
      val maxRest = maxListOrdering(rest)(ordering)
      if (ordering.gt(x, maxRest)) x
      else maxRest
  }

def maxListImpParam[T](elements: List[T])
                     (implicit ordering: Ordering[T]): T =
  elements match {
    case List() =>
      throw new IllegalArgumentException("empty list!")
    case List(x) => x
    case x :: rest =>
      val maxRest = maxListOrdering(rest)(ordering)
      if (ordering.gt(x, maxRest)) x
      else maxRest
  }

maxListOrdering(List(1, 5, 10, 3))(Ordering[Int])

maxListImpParam(List(1, 5, 10, 3))
maxListImpParam(List(1.5, 5.2, 10.7, 3.14159))
maxListImpParam(List("one", "two", "three"))

def maxList[T: Ordering](elements: List[T]): T =
  elements match {
    case List() =>
      throw new IllegalArgumentException("empty list!")
    case List(x) => x
    case x :: rest =>
      val maxRest = maxList(rest)
      if (implicitly[Ordering[T]].gt(x, maxRest)) x
      else maxRest
  }

def printLength(seq: Seq[Int]) = println(seq)
implicit def intToRange(i: Int) = 1 to i
implicit def intToDigits(i: Int) = i.toString.toList.map(_.toInt)

//printLength(12)

//val chars: List[Char] = wrapString("xyz")

object Mocha extends App {
  class PreferredDrink(val preference: String)
  implicit val pref = new PreferredDrink("mocha")
  def enjoy(name: String)(implicit drink: PreferredDrink) = {
    print("Welcome, " + name)
    print(". Enjoy a ")
    print(drink.preference)
    println("!")
  }
  enjoy("reader")
}
