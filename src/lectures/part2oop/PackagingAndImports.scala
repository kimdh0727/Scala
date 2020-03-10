package lectures.part2oop

//import playground._
import playground.{Cinderella => Princess, PrinceCharming}

import java.util.Date
import java.sql.{Date => SqlDate}

object PackagingAndImports extends App {

  // package members are accessible by their simple name
  val writer = new Writer("Daniel", "RockTheJVM", 2018)

  // import the package
  val princess = new Princess // playground.Cinderella = fully qualified name

  // packages are in hierarchy
  // matching folder structure.

  // package object
  sayHello
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming

  val d = new Date
  // 1. use FQ names
  val sqlDate1 = new java.sql.Date(2018, 5, 4)
  // 2. use aliasing
  val sqlDate2 = new SqlDate(2018, 5, 4)

  /*
   default imports
     1. java.lang = String, Object, Exception
     2. scala - Int, Nothing, Function
     3. scala.Predef - println, ???
   */
}