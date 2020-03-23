package playground

object Element {

  private class ArrayElement(val contents: Array[String]) extends Element
  private class LineElement(s: String) extends Element {
    val contents: Array[String] = Array(s)
    override def width: Int = s.length
    override def height: Int = 1
  }

  private class UniformElement(
                                ch: Char,
                                override val width: Int,
                                override val height: Int
                              ) extends Element {
    private val line = ch.toString * width
    def contents: Array[String] = Array.fill(height)(line)
  }

  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)

  def elem(line: String): Element =
    new LineElement(line)
}

abstract class Element {
    def contents: Array[String]

    def width: Int = if (height == 0) 0 else contents(0).length
    def height: Int = contents.length

    def above(that: Element): Element = {
      val this1 = this widen that.width
      val that1 = that widen this.width
      Element.elem(this1.contents ++ that1.contents)
    }

    def beside(that: Element): Element = {
      val this1 = this heighten that.height
      val that1 = that heighten this.height
      Element.elem(
        for (
          (line1, line2) <- this1.contents zip that1.contents
        ) yield line1 + line2
      )
    }

    def widen(w: Int): Element =
      if (w <= width) this
      else {
        val left = Element.elem(' ', (w - width) / 2, height)
        val right = Element.elem(' ', w - width - left.width, height)
        left beside this beside right
      }

    def heighten(h: Int): Element =
      if (h <= height) this
      else {
        val top = Element.elem(' ', width, (h - height) / 2)
        val bot = Element.elem(' ', width, h - height - top.height)
        top above this above bot
      }

    override def toString: String = contents mkString "\n"
  }

sealed abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

class ExprFormatter {
  private val opGroups =
    Array(
      Set("|", "||"),
      Set("&", "&&"),
      Set("^"),
      Set("==", "!="),
      Set("<", "<=", ">", ">="),
      Set("+", "-"),
      Set("*", "&")
    )
  private val precedence = {
    val assocs =
      for {
        i <- 0 until opGroups.length
        op <- opGroups(i)
      } yield op -> i
    assocs.toMap
  }
  private val unaryPrecedence = opGroups.length
  private val fractionPrecedence = -1
  private def format(e: Expr, enclPrec: Int): Element =
    e match {
      case Var(name) =>
        Element.elem(name)
      case Number(num) =>
        def stripDot(s: String) =
          if (s endsWith ".0") s.substring(0, s.length - 2)
          else s
        Element.elem(stripDot(num.toString))
      case UnOp(op, arg)  =>
        Element.elem(op) beside format(arg, unaryPrecedence)
      case BinOp("/", left, right) =>
        val top = format(left, fractionPrecedence)
        val bot = format(right, fractionPrecedence)
        val line = Element.elem('-', top.width max bot.width, 1)
        val frac = top above line above bot
        if (enclPrec != fractionPrecedence) frac
        else Element.elem(" ") beside frac beside Element.elem(" ")
      case BinOp(op, left, right) =>
        val opPrec = precedence(op)
        val l = format(left, opPrec)
        val r = format(right, opPrec + 1)
        val oper = l beside Element.elem(" " + op + " ") beside r
        if (enclPrec <= opPrec) oper
        else Element.elem("(") beside oper beside Element.elem(")")
    }
  def format(e: Expr): Element = format(e, 0)
}

object Express extends App {
  val f = new ExprFormatter
  val e1 = BinOp("*", BinOp("/", Number(1), Number(2)),
    BinOp("+", Var("x"), Number(1)))
  val e2 = BinOp("+", BinOp("/", Var("x"), Number(2)),
    BinOp("/", Number(1.5), Var("x")))
  val e3 = BinOp("/", e1, e2)
  def show(e: Expr) = println(f.format(e)+ "\n\n")
  for (e <- Array(e1, e2, e3)) show(e)
}
