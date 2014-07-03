package maastar.policy

import maastar.agent.Agent

class Policy(_agentPolicies: Map[Agent,PolicyNode] = Map(),
             _actualValue : Double = 0.0,
             _estimatedValue : Double = 0.0) {

  val agentPolicies = _agentPolicies
  val value = _actualValue
  val estimate = _estimatedValue
  val depth = if (agentPolicies.size > 0) agentPolicies.map(n => n._2.depth()).max else 0

  def canEqual(other: Any): Boolean = other.isInstanceOf[Policy]

  def leafNodes(agent : Agent) : Set[PolicyNode] = {
    val nodes = agentPolicies(agent)
    leafNodes(nodes)
  }

  private def leafNodes(node : PolicyNode) : Set[PolicyNode] = {
    node
      .transitions
      .values
      .map(
        child => if (child.transitions.size > 0) leafNodes(child) else Set(child)
      )
      .toSet
      .flatten
  }

  override def equals(other: Any): Boolean = other match {
    case that: Policy =>
      (that canEqual this) &&
        agentPolicies == that.agentPolicies &&
        value == that.value &&
        estimate == that.estimate
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(_agentPolicies)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString() : String = {
    agentPolicies.toString()
  }
}