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

  def getAgentPolicyCombos(agents : Set[Agent], policies : Set[PolicyNode]) : Iterator[Map[Agent, Policy]] = {
    // For each agent and policy base
    // expand the policy base one level.
    val stack = new mutable.Stack()

    null
  }

  def expandPolicyNodes(policy: PolicyNode): Iterator[PolicyNode] = {
    // Track current combinations as a stack of iterators
    val policyStack : mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation],PolicyNode]])] = new mutable.Stack()
    val targetPolicy = policy.createClone()

    // prime the policy stack
    val traversedNodes : mutable.Stack[PolicyNode] = new mutable.Stack()
    traversedNodes.push(targetPolicy)

    while (!traversedNodes.isEmpty) {
      val currNode = traversedNodes.pop()
      for ((obs, node) <- currNode.transitions) {
        traversedNodes.push(node)
      }
      if (currNode.transitions.size == 0) {
        policyStack.push((currNode, allObservationPolicies.iterator))
      }
    }

    return new Iterator[PolicyNode] {
      def hasNext() = !policyStack.isEmpty && policyStack.top._2.hasNext
      def next() : PolicyNode = {
        // Determine next policy
        val finishedNodes : mutable.Stack[(PolicyNode,Iterator[Map[Set[Observation],PolicyNode]])] = new mutable.Stack()

        while (!policyStack.isEmpty && policyStack.top._2.isEmpty) {
          finishedNodes.push(policyStack.pop())
        }

        if (policyStack.isEmpty) {
          throw new RuntimeException("Next called on empty iterator")
        }
        else {
          finishedNodes.push(policyStack.pop())
          while (!finishedNodes.isEmpty) {
            val top = finishedNodes.pop()
            val node = top._1
            var childIterator = top._2
            if (childIterator.isEmpty) {
              childIterator = allObservationPolicies.iterator
            }
            node.setTransitions(childIterator.next())
            policyStack.push(top)
          }
          return targetPolicy.createClone()
        }
      }
    }
  }


}