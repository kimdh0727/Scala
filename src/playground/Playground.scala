package playground

import scala.collection.mutable.ArrayBuffer

object Playground extends App {

//  class Animal
//  trait HasLegs

  trait Philosophical {
    def philosophize(): Unit =
      println("I consume memory, therefore I am!")
  }

  class Frog extends Animal with Philosophical with HasLegs {
    override def toString: String = "green"
  }

  val frog = new Frog
  frog.philosophize()

  val phil: Philosophical = frog
  phil.philosophize()

  trait CharSequence {
    def charAt(index: Int): Char
    def length: Int
    def subSequence(start: Int, end: Int): CharSequence
    def toString: String
  }

  class Point(val x: Int, val y: Int)
  trait Rectangular {
    def topLeft: Point
    def bottomRight: Point

    def left: Int = topLeft.x
    def right: Int = bottomRight.x
    def width: Int = right - left
    // etc Geometric Method
  }

  abstract class Component extends Rectangular {
    // etc Geometric Method
  }

  class Rectangle (val topLeft: Point, val bottomRight: Point) extends Rectangular {
    // etc Geometric Method
  }

  val rect = new Rectangle(new Point(1, 1), new Point(10, 10))
  println(rect.left)
  println(rect.right)
  println(rect.width)

  class Rational(n: Int, d: Int) extends Ordered[Rational] {
    def numor: Int = n
    def denom: Int = d
    // ...
    override def compare(that: Rational): Int =
      this.numor * that.denom - that.numor * this.denom
  }

  val half = new Rational(1, 2)
  val third = new Rational(1, 3)
  println(half < third)
  println(half > third)

  // IntQueue abstract class
  abstract class IntQueue {
    def get: Int
    def put(x: Int)
  }

  // BasicIntQueue
  class BasicIntQueue extends IntQueue {
    private val buf = new ArrayBuffer[Int]
    def get: Int = buf.remove(0)
    def put(x: Int): Unit = buf += x
  }

  val queue = new BasicIntQueue
  queue.put(10)
  queue.put(20)
  println(queue.get)
  println(queue.get)

  trait Doubling extends IntQueue {
    abstract override def put(x: Int): Unit = super.put(2 * x)
  }
  trait Incrementing extends IntQueue {
    abstract override def put(x: Int): Unit = super.put(x + 1)
  }
  trait Filtering extends IntQueue {
    abstract override def put(x: Int): Unit = if (x >= 0) super.put(x)
  }

  class MyQueue extends BasicIntQueue with Doubling
  val queue2 = new MyQueue
  queue2.put(10)
  println(queue2.get)

  val queue3 = new BasicIntQueue with Doubling
  queue3.put(10)
  println(queue3.get)

  val queue4 = new BasicIntQueue with Incrementing with Filtering
  queue4.put(-1); queue4.put(0); queue4.put(1);
  println(queue4.get)
  println(queue4.get)

  val queue5 = new BasicIntQueue with Filtering with Incrementing
  queue5.put(-1); queue5.put(0); queue5.put(1);
  println(queue5.get)
  println(queue5.get)

  class Animal
  trait Furry extends Animal
  trait HasLegs extends Animal
  trait FourLegged extends HasLegs
  class Cat extends Animal with Furry with FourLegged
}