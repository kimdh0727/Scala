package lectures.part2oop

object Exceptions extends App {

  val x: String = null
//  println(x.length)
//  this ^^ will crash with a NPE (NullPointerException)

  // 1. throwing and catching exceptions
//  val aWeirdValue = throw new NullPointerException

  // throwable classes extend the Throwable class
  // Exception and Error are the major Throwable subtypes

  // 2. how to catch exceptions
  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  val potentialFail = try {
    // code that might throw
    getInt(true)
  } catch {
//    case e: RuntimeException => println("caught a Runtime exception")
//    case e: NullPointerException => println("caught a Runtime exception")
    case e: RuntimeException => 43
  } finally {
    // code that will get executed NO MATTER WHAT
    // optional
    // does not influence the return type of this expression
    // use finally only  for side effects
    println("finally")
  }

  println(potentialFail)

  // 3. how to define your own exceptions
  class MyException extends Exception
  val exceptions = new MyException

//  throw exceptions

  /*
    1.  Crash your program with an OutOfMemoryError
    2.  Crash with StackOverflowError
    3.  PocketCalculator
        - add(x, y)
        - subtract(x, y)
        - multiply(x, y)
        - divide(x, y)

        Throw
          - OverflowException if add(x, y) exceeds Int.MAX_VALUE
          - UnderflowException if subtrack(x, y) exceeds Int.MIN_VALUE
          - MathCalculationException for division by 0
   */

  // OutOfMemory
  // val array = Array.ofDim(Int.MaxValue)

  // StackOverflow
  // def loop: Int = loop + 1
  // loop

  class OverflowException extends Exception
  class UnderflowException extends Exception
  class MathCalculationException extends Exception

  object PocketCalculator {
    def add(x: Int, y: Int) = {
      val res = x + y
      if (x > 0 && y > 0 && res < 0) throw new OverflowException
      else if (x < 0 && y < 0 && res > 0) throw new UnderflowException
      else res
    }
    def sub(x: Int, y: Int) = {
      val res = x - y
      if (x < 0 && y > 0 && res > 0) throw new UnderflowException
      else if (x > 0 && y < 0 && res < 0) throw new OverflowException
      else res
    }
    def mul(x: Int, y: Int) = {
      val res = x * y
      if (x > 0 && y > 0 && res < 0) throw new OverflowException
      else if (x < 0 && y < 0 && res < 0) throw new OverflowException
      else if (x > 0 && y < 0 && res > 0) throw new UnderflowException
      else if (x < 0 && y > 0 && res > 0) throw new UnderflowException
      else res
    }
    def div(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }
  }

  // OverflowException
  // PocketCalculator.add(1, Int.MaxValue)

  // UnderflowException
  // PocketCalculator.add(-1, Int.MinValue)

  // MathCalculationException
  // PocketCalculator.div(1, 0)
}