package lectures.part1basics

object CBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println("by value: " + x) // println("by Value: " + x (fix value))
    println("by value: " + x) // println("by Value: " + x (fix value))
  }

  def calledByName(x: => Long): Unit = {
    println("by name: " + x)  // println("by name: " + System.nanoTime())
    println("by name: " + x)  // println("by name: " + System.nanoTime())
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

//printFirst(infinite(), 34)  // Stack Overflow
  printFirst(34, infinite())  // never evaluate infinite()
}
