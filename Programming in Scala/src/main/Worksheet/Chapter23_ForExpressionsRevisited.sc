// Chapter23_ForExpressionsRevisited.sc
case class Person(name: String, isMale: Boolean, children: Person*)

val lara = Person("Lara", false)
val bob = Person("Bob", true)
val julie = Person("Julie", false, lara, bob)
val persons = List(lara, bob, julie)

persons withFilter(p => !p.isMale) flatMap(p =>
  (p.children map(c => (p.name, c.name))))

for (p <- persons; if !p.isMale; c <- p.children)
  yield (p.name, c.name)

for (x <- List(1, 2); y <- List("one", "two"))
  yield (x, y)


object nQueen {
  def queens(n: Int): List[List[(Int, Int)]] = {
    def placeQueens(k: Int): List[List[(Int, Int)]] =
      if (k == 0)
        List(List())
      else
        for {
          queens <- placeQueens(k - 1)
          column <- 1 to n
          queen = (k, column)
          if isSafe(queen, queens)
        } yield queen :: queens
    placeQueens(n)
  }
  def isSafe(queen: (Int, Int), queens: List[(Int, Int)]) =
    queens forall (q => !inCheck(queen, q))
  def inCheck(q1: (Int, Int), q2: (Int, Int)) =
    q1._1 == q2._1 ||
    q1._2 == q2._2 ||
    (q1._1 - q2._1).abs == (q1._2 - q2._2).abs
}

nQueen.queens(4)

case class Book(title: String, authors: String*)

val books: List[Book] =
  List(
    Book(
      "Structure and Interpretation of Computer Programs",
      "Abelson, Harold", "Sussman, Gerald J."
    ),
    Book(
      "Principles of Compiler Design",
      "Aho, Alfred", "Ullman, Jeffrey"
    ),
    Book(
      "Programming in Modula-2",
      "Wirth, Niklaus"
    ),
    Book(
      "Elements of ML Programming",
      "Ullman, Jeffrey"
    ),
    Book(
      "The Java Language Specification", "Gosling, James",
      "Joy, Bill", "Steele, Guy", "Bracha, Gilad"
    )
  )

for (b <- books; a <- b.authors
     if a startsWith "Gosling")
  yield b.title

for (b <- books if (b.title indexOf "Program") >= 0)
  yield b.title

val res = for (b1 <- books; b2 <- books if b1 != b2;
     a1 <- b1.authors; a2 <- b2.authors if a1 == a2)
  yield a1

def removeDuplicates[A](xs: List[A]): List[A] = {
  if (xs.isEmpty) xs
  else
    xs.head :: removeDuplicates(
      // xs.tail filter (x => x != xs.head)
      for (x <- xs.tail if x != xs.head) yield x
    )
}

removeDuplicates(res)

object Demo {
  def map[A, B](xs: List[A], f: A => B): List[B] =
    for (x <- xs) yield f(x)

  def flatMap[A, B](xs: List[A], f: A => List[B]): List[B] =
    for (x <- xs; y <- f(x)) yield y

  def filter[A](xs: List[A], p: A => Boolean): List[A] =
    for (x <- xs if p(x)) yield x
}

abstract class C[A] {
  def map[B](f: A => B): C[B]
  def flatMap[B](f: A => C[B]): C[B]
  def withFilter(p: A => Boolean): C[A]
  def foreach(b: A => Unit): Unit
}