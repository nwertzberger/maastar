package maastar.algorithm.maastar

import maastar.agent.Agent
import maastar.game.{Observation, Action}
import maastar.policy.{PolicyNode, Policy}

import scala.collection.mutable


class PolicyExpander(allPossibleAgents: Set[Agent] = Set(),
                     allPossibleActions: Set[Action] = Set(),
                     allPossibleObservations: Set[Observation] = Set()) {

  val observationCombos = (0 to allPossibleObservations.size).map(
    len => allPossibleObservations.subsets(len).toSet
  ).toSet.flatten

  val allObservationPolicies = actionObservationPermutations(
    observationCombos,
    allPossibleActions.map(act => new PolicyNode(act))
  )

  private def actionObservationPermutations(
      observations: Set[Set[Observation]],
      policies: Set[PolicyNode],
      policyTransitions: Map[Set[Observation], PolicyNode] = Map()
      ): Set[Map[Set[Observation], PolicyNode]] = {

    if (observations.isEmpty) {
      return Set(policyTransitions)
    }
    else {
      return policies.map(policy =>
        actionObservationPermutations(
          observations.tail,
          policies,
          Map(observations.head -> policy) ++ policyTransitions)
      ).toSet.flatten
    }
  }

  def getAgentPolicyCombos(
      agents: Set[Agent],
      policies: Set[PolicyNode]
      ): Iterator[Map[Agent, Policy]] = {
    // For each agent and policy base
    // expand the policy base one level.
    val stack = new mutable.Stack()

    null
  }

  def expandPolicyNodes(policy: PolicyNode): Iterator[PolicyNode] = {
    val targetPolicy = policy.createClone()
    val policyStack = generateNodePolicyStack(targetPolicy)

    return new Iterator[PolicyNode] {
      def hasNext() = !policyStack.isEmpty

      def next(): PolicyNode = {
        val currPolicy = targetPolicy.createClone()
        // Determine next policy
        val finishedNodes = getFinishedNodes(policyStack)

        if (!policyStack.isEmpty) {
          finishedNodes.push(policyStack.pop())
          while (!finishedNodes.isEmpty) {
            val (node, childIterator) = getNextNode(finishedNodes.pop())
            node.setTransitions(childIterator.next())
            policyStack.push((node, childIterator))
          }
        }
        return currPolicy
      }
    }
  }

  def getFinishedNodes(
      policyStack: mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])]
      ): mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = {
    val finishedNodes: mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = new mutable.Stack()
    while (!policyStack.isEmpty && policyStack.top._2.isEmpty) {
      finishedNodes.push(policyStack.pop())
    }
    return finishedNodes
  }

  def getNextNode(
      top: (PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])
      ): (PolicyNode, Iterator[Map[Set[Observation], PolicyNode]]) = {
    val (node, childIterator) = top
    if (childIterator.isEmpty) {
      return (node, allObservationPolicies.toIterator)
    }
    return (node, childIterator)
  }

  def generateNodePolicyStack(
      targetPolicy: PolicyNode
      ): mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = {
    val policyStack: mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = new mutable.Stack()
    val traversedNodes: mutable.Stack[PolicyNode] = new mutable.Stack()
    traversedNodes.push(targetPolicy)

    while (!traversedNodes.isEmpty) {
      val currNode = traversedNodes.pop()
      for ((obs, node) <- currNode.transitions) {
        traversedNodes.push(node)
      }
      if (currNode.transitions.size == 0) {
        val obsPolicies = allObservationPolicies.toIterator
        currNode.setTransitions(obsPolicies.next())
        policyStack.push((currNode, obsPolicies))
      }
    }
    return policyStack
  }
}