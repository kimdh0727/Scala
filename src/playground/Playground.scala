package playground

import scala.collection.immutable.Queue

object Playground extends App {

  val q = Queue(1, 2, 3)
  val q1 = q enqueue 4
  println(q)
  println(q1)

  class Queue[T] (private val leading: List[T], private val trailing: List[T]) {
    private def mirror =
      if (leading.isEmpty)
        new Queue(trailing.reverse, Nil)
      else
        this

    def head = mirror.leading.head

    def tail = {
      val q = mirror
      new Queue(q.leading.tail, q.trailing)
    }

    def enqueue(x: T) =
      new Queue(leading, x :: trailing)
  }

}