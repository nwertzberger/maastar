package maastar.policy

import maastar.agent.Agent

class Policy(
    _agentPolicies: Map[Agent, PolicyNode] = Map(),
    _estimatedValue: Double = Double.NaN
) {

    val agentPolicies = _agentPolicies
    var estimate = _estimatedValue

    def depth(): Int = {
        if (agentPolicies.size > 0)
            agentPolicies.values.map { (node: PolicyNode) =>
                node.depth()
            }.max
        else
            0
    }

    def canEqual(other: Any): Boolean = other.isInstanceOf[Policy]

    def leafNodes(agent: Agent): Set[PolicyNode] = {
        val nodes = agentPolicies(agent)
        leafNodes(nodes)
    }

    private def leafNodes(node: PolicyNode): Set[PolicyNode] = {
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
                estimate == that.estimate
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(_agentPolicies)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }

    override def toString(): String = {
        agentPolicies.toString()
    }
}