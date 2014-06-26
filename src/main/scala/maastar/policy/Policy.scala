package maastar.policy

import maastar.agent.Agent

class Policy(agentPolicies: Map[Agent,PolicyNode] = Map(),
             actualValue : Double = 0.0,
             estimatedValue : Double = 0.0) {

  val _depth = if (agentPolicies.size > 0) agentPolicies.map(n => n._2.depth()).max else 0
  val _agentPolicies = agentPolicies
  val _value = actualValue
  val _estimate = estimatedValue

  def depth() = _depth
  def value() = _value
  def estimate() = _estimate
  def canEqual(other: Any): Boolean = other.isInstanceOf[Policy]

  def leafNodes(agent : Agent) : Set[PolicyNode] = {
    val nodes = agentPolicies(agent)
    leafNodes(nodes)
  }

  private def leafNodes(node : PolicyNode) : Set[PolicyNode] = {
    node.transitions()
      .values
      .map(
        child => if (child.transitions().size > 0) leafNodes(child) else Set(child)
      )
      .toSet
      .flatten
  }

  override def equals(other: Any): Boolean = other match {
    case that: Policy =>
      (that canEqual this) &&
        _agentPolicies == that._agentPolicies &&
        _value == that._value &&
        _estimate == that._estimate
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(_agentPolicies)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}