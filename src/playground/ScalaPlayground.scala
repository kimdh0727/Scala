package playground

import playground.ScalaPlayground.Element
import playground.Spiral.spiral

object ScalaPlayground {

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
}

import Element.elem

object Spiral {

  val space: Element = elem(" ")
  val corner: Element = elem("+")

  def spiral(nEdges: Int, direction: Int): Element = {
    if (nEdges == 1)
      elem("+")
    else {
      val sp = spiral(nEdges - 1, (direction + 3) % 4)

      def verticalBar: Element = elem('|', 1, sp.height)

      def horizontalBar: Element = elem('-', sp.width, 1)

      if (direction == 0)
        (corner beside horizontalBar) above (sp beside space)
      else if (direction == 1)
        (sp above space) beside (corner above verticalBar)
      else if (direction == 2)
        (space beside sp) above (horizontalBar beside corner)
      else
        (verticalBar above corner) beside (space above sp)
    }
  }

  def main(args: Array[String]): Unit = {
    val nSides = args(0).toInt
    println(spiral(nSides, 0))
  }
}

object main extends App {
  println(spiral(6, 0))
  println(spiral(11, 0))
  println(spiral(17, 0))
  println(spiral(5, 1))
}