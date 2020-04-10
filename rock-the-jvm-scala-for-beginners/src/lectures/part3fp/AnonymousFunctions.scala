package lectures.part3fp

object AnonymousFunctions extends App {

  // anonymous function (LAMBDA)
  val doubler: Int => Int = (x: Int) => x * 2

  // multiple params in a lambda
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // no params
  val justDoSomething: () => Int = () => 3

  // careful
  println(justDoSomething)    // function itself
  println(justDoSomething())  // function call

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MORE syntactic sugar
  val niceIncrementer: Int => Int = _ + 1   // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _  // equivalent to (a, b) => a + b

  /**
   *  1.  MyList: replace all FunctionX calls with lambda
   *  2.  Rewrite the "special" adder as an anonymous function
   */

  def superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4))
}
