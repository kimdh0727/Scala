package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n - 1))
      val result = n * factorial(n - 1)
      println("Computed factorial of " + n)
      result
    }

  println(factorial(10))
//  println(factorial(5000))

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else        factHelper(x - 1, x * accumulator)  // TAIL RECURSION = use recursive call as the LAST expression

    factHelper(n, 1)
  }

  println(anotherFactorial(5000))

  /*
    anotherFactorial(10) = factHelper(10, 1)
    = factHelper (9, 10 * 1)
    = factHelper (8, 9 * 10 * 1
    = factHelper (7, 8 * 9 * 10 * 1)
    = ...
    = factHelper (2, 3 * 4 * ... * 10 * 1)
    = factHelper (1, 1 * 2 * 3 * 4 * ... * 10)
    = 1 * 2 * 3 * 4 * ... * 10
   */

  // WHEN YOU NEED LOOPS, USE _TAIL_ RECURSION.

  /*
    1.  Concatenate a sting n times
    2.  IsPrime function tail recursive
    3.  Fibonacci function, tail recursive
   */

  @tailrec
  def concatenate(aString: String, n: Int, acc: String = ""): String = {
    if (n < 1)  acc
    else        concatenate(aString, n - 1, acc + aString)
  }

  println(concatenate("hello ", 3))

  def isPrime(x: Int): Boolean = {
    def sqrt(n: Int):Int = {
      @tailrec
      def getSqrt(t: Int):Int = {
        if (t * t >= n) t
        else            getSqrt(t + 1)
      }

      getSqrt(0)
    }

    @tailrec
    def isPrimeHelper(n: Int, to: Int): Boolean = {
      if (x % n == 0 && x != n) false
      else if (n == sqrt(x))    true
      else                      isPrimeHelper(n + 1, to)
    }

    if (x < 2)  false
    else        isPrimeHelper(2, sqrt(x))
  }

  for (i <- 1 to 20)
    println(i + " " + isPrime(i))

  def fibonacci(x: Int): Int = {
    @tailrec
    def fibonacciHelper(n: Int, first: Int = 0, second: Int = 1): Int = {
      if (x == n) first
      else        fibonacciHelper(n + 1, second, first + second)
    }
    if (x < 0)  -1
    else        fibonacciHelper(0)
  }

  println(fibonacci(8))
}
