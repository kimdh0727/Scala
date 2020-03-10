package playground

import java.awt.Shape

object Playground extends App {

  abstract class Element {
    def contents: Array[String]
    def height: Int = contents.length
    def width: Int = if (height == 0) 0 else contents(0).length
  }

  class ArrayElement(val contents: Array[String]) extends Element
  class LineElement(s: String) extends ArrayElement(Array(s)) {
    override def width: Int = s.length
    override def height: Int = 1
  }

  class UniformElement(
    ch: Char,
    override val width: Int,
    override val height: Int
  ) extends Element {
    private val line = ch.toString * width
    def contents: Array[String] = Array.fill(height)(line)
  }

  val e1: Element = new ArrayElement(Array("hello", "world"))
  val ae: ArrayElement = new LineElement("hello")
  val e2: Element = ae
  val e3: Element = new UniformElement('x', 2, 3)

  println(e1.getClass)
  println(ae.getClass)
  println(e2.getClass)
  println(e3.getClass)

//  def elem(s: String): Element

//  val column1 = elem("hello") above elem("***")
//  val column2 = elem("***") above elem("world")
//  column1 beside column2
}