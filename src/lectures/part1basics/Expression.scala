package lectures.part1basics

object Expression extends App {
  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 + 4)
  // mathematically expression
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x)
  // relational operation
  // == !- > >= < <=

  println(!(1 == x))
  // logical operation
  // ! && ||

  var aVariable = 2
  aVariable += 3 // also works with -= *= /= ...... side effects
  println(aVariable)

  // Instructions (DO) vs Expressions (VALUE)

  // IF expression
  val aCondition = true
  val aConditionValue = if (aCondition) 5 else 3  // IF EXPRESSION
  println(aConditionValue)
  println(if(aCondition) 5 else 3)
  println(1 + 3)

  var i = 0
  var whileExpression = while (i < 10) {
    println(i)
    i += 1
  }

  // NEVER WRITE THIS AGAIN

  // EVERYTHING in Scala is an Expression!

  val aWeirdValue = (aVariable = 3) // Unit === void
  println(aWeirdValue)

  // side effects: println(), whiles, reassigning

  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  }

  // 1. difference between "hello world" vs println("hello world")?
  // 2.
  val someValue = {
    2 < 3
  }
  println(someValue)

  val someOtherValue = {
    if(someValue) 239 else 986
    42
  }
  println(someOtherValue)
}