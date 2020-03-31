package playground

object Playground extends App {

  val colors = List("red", "blue", "green") // List init
  println(colors.head)
  println(colors.tail)

  val fiveInts = new Array[Int](5)
  println(fiveInts mkString ", ")

  val fiveToOne = Array(5, 4, 3 ,2, 1)
  println(fiveToOne mkString ", ")

  fiveInts(0) = fiveToOne(4)
  println(fiveInts mkString ", ")

  import scala.collection.mutable.ListBuffer

  val buf = new ListBuffer[Int]
  buf += 1
  buf += 2
  println(buf)
  3 +=: buf
  println(buf.toList)

  import scala.collection.mutable.ArrayBuffer

  val buf2 = new ArrayBuffer[Int]()
  buf2 += 12
  buf2 += 15
  0 +=: buf2
  println(buf2)
  println(buf2.length)
  println(buf2(0))

  def hasUpperCase(s: String) = s.exists(_.isUpper)
  println(hasUpperCase("Robert Frost"))
  println(hasUpperCase("e e cummings"))

  import collection.mutable
  val test = "See Spot run. Run, Spot. Run!"
  val wordsArray = test.split("[ !,.]+")
  val words = mutable.Set.empty[String]
  for (word <- wordsArray)
    words += word.toLowerCase
  println(words)

  val map = mutable.Map.empty[String, Int]
  map("hello") = 1
  map("there") = 2
  println(map)
  println(map("hello"))

  def countWords(text: String) = {
    val counts = mutable.Map.empty[String, Int]
    for (rawWord <- text.split("[ ,!.]+")) {
      val word = rawWord.toLowerCase
      val oldCount =
        if (counts.contains(word))
          counts(word)
        else
          0
      counts += (word -> (oldCount + 1))
    }
    counts
  }
  println(countWords("See Spot run! Run, Spot, Run!"))

  import scala.collection.immutable.{TreeSet, TreeMap}
  val ts = TreeSet(9, 3, 1, 8, 0, 2, 7, 4, 6, 5)
  println(ts)

  val tm = TreeMap(3 -> 'x', 1 -> 'x', 4 -> 'x')
  println(tm + (2 -> 'x'))

  List(1, 2, 3)
  Set('a', 'b', 'c')
  Map("hi" -> 2, "there" -> 5)
  Array(1.0, 2.0, 3.0)

  val stuff = mutable.Set[Any](42)
  stuff += "abc"

  val colors2 = List("blue", "yellow", "red", "green")
  val treeSet = TreeSet[String]() ++ colors2

  println(treeSet)
  val mutaSet = mutable.Set.empty ++ treeSet
  val immutaSet = Set.empty ++ mutaSet

  println(mutaSet)
  println(immutaSet)

  def longestWord(words: Array[String]) = {
    var word = words(0)
    var idx = 0
    for (i <- 1 until words.length)
      if (words(i).length > word.length) {
        word = words(i)
        idx = i
      }
    (word, idx)
  }

  val longest = longestWord("The quick brown fox".split(" "))
  println(longest)

  println(longest._1)
  println(longest._2)

  val (word, idx) = longest
  println(word)

  val tuple1, tuple2 = longest
  println(tuple1)
  println(tuple2)

  var people = Set("Nancy", "Jane")
  people += "Bob"
  println(people)

  var roughlyPi = 3.0
  roughlyPi += 0.1
  roughlyPi += 0.04
  println(roughlyPi)

}
