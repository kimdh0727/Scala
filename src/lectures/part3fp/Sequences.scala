package lectures.part3fp

import scala.util.Random

object Sequences extends App {

  // Seq
  val aSequence = Seq(1, 3, 2, 4)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(5, 6, 7))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 to 10
  aRange.foreach(println)
  (1 to 10).foreach(x => println("Hello"))

  // lists
  val aList: List[Int] = List(1, 2, 3)
  val ssPrepended = 42 :: aList
  println(ssPrepended)
  val prepended = 42 +: aList :+ 89
  println(prepended)
  println("hi")
  val apple5 = List.fill(5)("apple")
  println(apple5)
  println(aList.mkString("-!-"))

  // Array
  val numbers = Array(1, 2, 3, 4)
  numbers.foreach(println)
  val threeElements = Array.ofDim[String](3)
  println(threeElements)
  threeElements.foreach(println)

  // mutation
  numbers(2) = 0 // syntax sugar for numbers.update(2, 0)
  println(numbers.mkString)

  // arrays and seq
  val numbersSeq: Seq[Int] = numbers  // implicit conversion
  println(numbersSeq)

  // vector
  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector)

  // vectors vs lists
  val maxRuns = 1000
  val maxCpacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val currentTime = System.nanoTime()
    val r = new Random
    for (it <- 1 to maxRuns)
      collection.updated(r.nextInt(maxCpacity), r.nextInt())
    (System.nanoTime() - currentTime) / maxRuns
  }
  val numbersList = (1 to maxCpacity).toList
  val numbersVector = (1 to maxCpacity).toVector

  // keeps reference to tail
  // updating an element in the middle takes long
  println(getWriteTime(numbersList))
  // depth of the tree is small
  // needs to replace an entire 32-element chunk
  println(getWriteTime(numbersVector))
}
