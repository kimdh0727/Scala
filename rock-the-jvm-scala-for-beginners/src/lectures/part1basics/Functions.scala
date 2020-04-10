package lectures.part1basics

object Functions extends App {
  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterlessFunction(): Int = 42
  println(aParameterlessFunction())
  println(aParameterlessFunction)   // WARNING compiler

  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n-1)
  }
  println(aRepeatedFunction("hello", 3))

  // WHEN YOU NEED LOOPS, USE RECURSION

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b
    aSmallerFunction(n, n-1)
  }
  println(aBigFunction(3))

  /*
    1, A greeting function (name, age) => "Hi, my name is $name I am $age years old"
    2. Factorial function 1 * 2  * 3 * .. * n
    3. A Fibonacci function
       f(1) = 1
       f(2) = 1
       f(n) = f(n - 1) + f(n - 2)
    4. Tests if a number is prime.
   */

  def greeting(name: String, age: Int): String = "Hi, my name is " + name + " I am " + age + " years old"
  println(greeting("David", 12))

  def factorial(n: Int):Int = {
    if (n <= 1)   1
    else          n * factorial(n - 1)
  }
  println(factorial(5))

  def fibonacci(n: Int):Int = {
    if (n == 0)       0
    else if (n == 1)  1
    else              fibonacci(n - 1) + fibonacci(n - 2)
  }
  println(fibonacci(8))


  def isPrime(n: Int):Boolean = {
    def sqrt(n: Int):Int = {
      def getSqrt(t: Int):Int = {
        if (t * t >= n)   t
        else              getSqrt(t + 1)
      }
      getSqrt(0)
    }
    def isPrimeSub(t: Int):Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeSub(t - 1)
    }
    isPrimeSub(sqrt(n))
  }
  println(isPrime(37))
  println(isPrime(37 * 17))
}