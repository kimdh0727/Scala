// Chpater20_AbstractMembers.sc

trait Abstract {
  type T
  def transform(x: T): T
  val initial: T
  var current: T
}

class Concrete extends Abstract {
  type T = String
  def transform(x: T) = x + x
  val initial = "hi"
  var current = initial
}