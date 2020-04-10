package playground

import scala.collection.immutable.Queue

object Playground extends App {

  val q = Queue(1, 2, 3)
  val q1 = q enqueue 4
  println(q)
  println(q1)

  trait Queue[T] {
    def head: T
    def tail: Queue[T]
    def enqueue(x: T): Queue[T]
  }

  object Queue {

    def apply[T](xs: T*): Queue[T] = new QueueImpl[T](xs.toList, Nil)

    private class QueueImpl[T] (private val leading: List[T], private val trailing: List[T]) extends Queue[T] {
      def mirror =
        if (leading.isEmpty)
          new QueueImpl(trailing.reverse, Nil)
        else
          this

      def head = mirror.leading.head

      def tail = {
        val q = mirror
        new QueueImpl(q.leading.tail, q.trailing)
      }

      def enqueue(x: T) =
        new QueueImpl(leading, x :: trailing)
    }
  }

  class Cell[T](init: T) {
    private[this] var current = init
    def get = current
    def set(x: T) = { current = x }
  }

//  val c1 = new Cell[String]("abc")
//  val c2: Cell[Any] = c1
//  c2.set(1)
//  val s: String = c1.get

  val a1 = Array("abc")
//  val a2: Array[Any] = a1

  val a2: Array[Object] = a1.asInstanceOf[Array[Object]]

}