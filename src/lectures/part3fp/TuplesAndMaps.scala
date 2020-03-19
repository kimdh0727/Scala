package lectures.part3fp

import scala.annotation.tailrec

object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"
  val aTuple = Tuple2(2, "hello, Scala")  // Tuple2[Int, String] = (It, String)
aTuple
  println(aTuple._1)  // 2
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap)  // ("hello, Scala", 2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  // withDefaultValue(-1): Avoid NoSuchElementException
  val phoneBook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)

  // a -> b is sugar for (a, b)
  println(phoneBook)

  // map ops
  println(phoneBook.contains("Jim"))
  println(phoneBook("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhoneBook = phoneBook + newPairing
  println(newPhoneBook)

  // functionals on maps
  // map, flatMap, filter
  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phoneBook.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phoneBook.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phoneBook.toList)
  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))

  /**
   *  1.  What would happen if I had two original entries "Jim" -> 555 and "JIM" => 900?
   *
   *      !!! careful with mapping keys.
   *
   *  2.  Overly simplified social network based on maps
   *      Person = String
   *      - add a person to the network
   *      - remove
   *      - friend (mutual)
   *      - unFriend
   *
   *      - number of friends of a person
   *      - person with most friends
   *      - how many people have NO friends
   *      - if there is a social connection between two people (direct or not)
   */

  val phoneBook1 = Map(("Jim", 555), ("JIM", 9000)).withDefaultValue(-1)
  println(phoneBook1)
  println(phoneBook1.map(pair => pair._1.toLowerCase -> pair._2))

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())
  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    @tailrec
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unFriend(networkAcc, person, friends.head))

    val unFriended = removeAux(network(person), network)
    unFriended - person
  }
  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendA = network(a)
    val friendB = network(b)

    network + (a -> (friendA + b)) + (b -> (friendB + a))
  }
  def unFriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendA = network(a)
    val friendB = network(b)

    network + (a -> (friendA - b)) + (b -> (friendB - a))
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unFriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  def mostFriend(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1

  println(mostFriend(testNet))

  def nPeopleWithNoFriend(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty)
    // network.filterKeys(k => network(k).isEmpty).size

  println(nPeopleWithNoFriend(testNet))

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    @tailrec
    def bps(visited: Set[String], queue: Set[String]): Boolean = {
      if (queue.isEmpty) false
      else {
        val person = queue.head
        if (person == b) true
        else if (visited.contains(person)) bps(visited, queue.tail)
        else bps(visited + person, queue.tail ++ network(person))
      }
    }

    bps(Set(), network(a) + a)
  }

  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Mary", "Bob"))
}
