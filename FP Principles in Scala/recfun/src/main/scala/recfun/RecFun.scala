package recfun

import scala.annotation.tailrec

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    @tailrec
    def balanceHelper(lst: List[Char] = chars, acc: Int = 0): Boolean =
      if (acc < 0) false
      else if (lst.isEmpty)
        if (acc == 0) true else false
      else if (lst.head == '(') balanceHelper(lst.tail, acc + 1)
      else if (lst.head == ')') balanceHelper(lst.tail, acc - 1)
      else balanceHelper(lst.tail, acc)
    balanceHelper()
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money > 0 && coins.nonEmpty)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    else if (money == 0) 1 else 0
  }
}