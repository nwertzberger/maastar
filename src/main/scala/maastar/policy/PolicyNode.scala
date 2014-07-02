package maastar.policy

import maastar.game.{Action, Observation}

class PolicyNode(_action : Action, _transitions : Map[Set[Observation],PolicyNode] = Map(), _value : Double = 0.0) {
  def depth() : Int = if (_transitions.size == 0) 0 else _transitions.values.map(p => p.depth()).max + 1
  val action = _action
  var transitions = _transitions
  val value = _value

  def setTransitions(newTransitions: Map[Set[Observation], PolicyNode]) = {
    transitions = newTransitions
  }

  def createClone(): PolicyNode = {
    new PolicyNode(
      action,
      transitions
        .map(t => t._1 -> t._2.createClone())
        .toMap,
      value)
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[PolicyNode]

  override def toString() : String = {
    "new PolicyNode(" + action.toString + ", " + transitions.toString() + ")"
  }
  override def equals(other: Any): Boolean = other match {
    case that: PolicyNode =>
      (that canEqual this) &&
        action == that.action &&
        transitions == that.transitions &&
        value == that.value
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(action, transitions, value)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
