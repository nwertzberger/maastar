package maastar.algorithm.maastar

import maastar.game.Action

class Policy(childPolicies: Map[Action, Policy] = Map()) {
  val children = childPolicies

  def getValue() = Float.NegativeInfinity

  def canEqual(other: Any): Boolean = other.isInstanceOf[Policy]

  override def equals(other: Any): Boolean = other match {
    case that: Policy =>
      (that canEqual this) &&
        children == that.children
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(children)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}