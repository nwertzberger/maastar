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

  def expandPolicyNodes(currPolicy: PolicyNode): Iterator[PolicyNode] = {
    // clone the current policy tree
    // go to each leaf node.
    // every time you encounter a leaf node, that node needs to repeat its pattern one entire loop before the previous
    // leaf node changes.

    // track an iterator for each leafnode
    // build new leaf nodes and clone that new tree.
    val policyStack : mutable.Stack[PolicyNode] = new mutable.Stack().push(currPolicy)
    null
  }


}