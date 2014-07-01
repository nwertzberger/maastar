package maastar.policy

import maastar.game.{Action, Observation}

class PolicyNode(_action : Action, _transitions : Map[Set[Observation],PolicyNode] = Map(), _value : Double = 0.0) {
  def depth() : Int = if (_transitions.size == 0) 0 else _transitions.values.map(p => p.depth()).max
  val action = _action
  var transitions = _transitions
  val value = _value

  def setTransitions(newTransitions: Map[Set[Observation], PolicyNode]) = {
    transitions = newTransitions
  }
  override def toString() : String = {
    "new PolicyNode(" + _action.toString + ", " + _transitions.toString() + ")"
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[PolicyNode]

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
