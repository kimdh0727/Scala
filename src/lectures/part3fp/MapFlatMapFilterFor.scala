package lectures.part3fp

object MapFlatMapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(x => List(x, x + 1)))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")
  // List("a1", "a2", ... "d4")

  // "iterating"
  val comb = chars.flatMap(c => numbers.map(x => s"$c$x"))
  println(comb)
  val comb2 = chars.flatMap(c => numbers.flatMap(x => colors.map(color => s"$c$x-$color")))
  println(comb2)
  val comb2filter = chars.flatMap(c =>
    numbers.withFilter(_ % 2 == 0).flatMap(x =>
      colors.map(color =>
        s"$c$x-$color")))
  println(comb2filter)

  // foreach
  numbers.foreach(println)

  // for-comprehension
  val forCombinations = for {
    c <- chars
    n <- numbers if n % 2 == 0
    color <- colors
  } yield s"$c$n-$color"
  println(forCombinations)

  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /**
   *  1.  MyList supports for comprehension
   *      map(f: A => B) => MyList[B]
   *      filter(p: A => Boolean) => MyList[A]
   *      flatMap(f: A => MyList[B]) => MyList[B]
   *  2.  A small collection of at most ONE element - Maybe[+T]
   *      - map, flatMap filter
   */
}