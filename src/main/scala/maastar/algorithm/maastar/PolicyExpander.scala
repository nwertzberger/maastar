package maastar.algorithm.maastar

import maastar.agent.Agent
import maastar.game.{Observation, Action}
import maastar.policy.{PolicyNode, Policy}


class PolicyExpander(allPossibleAgents: Set[Agent] = Set(),
                     allPossibleActions: Set[Action] = Set(),
                     allPossibleObservations: Set[Observation] = Set()) {

  val observationCombos = (0 to allPossibleObservations.size).map(
    len => allPossibleObservations.subsets(len).toSet
  ).toSet.flatten

  val allObservationPolicies = getActionObservationCombos(
    observationCombos,
    allPossibleActions.map(act => new PolicyNode(act))
  )

  private def getActionObservationCombos(observations: Set[Set[Observation]],
                                         policies: Set[PolicyNode],
                                         policyTransitions: Map[Set[Observation], PolicyNode] = Map()
                                          ): Set[Map[Set[Observation], PolicyNode]] = {
    if (observations.isEmpty) {
      return Set(policyTransitions)
    }
    else {
      return policies.map(policy =>
        getActionObservationCombos(
          observations.tail,
          policies,
          Map(observations.head -> policy) ++ policyTransitions)
      ).toSet.flatten
    }
  }


  def expandPolicyNodes(currPolicy: Policy): Set[Policy] = {
    // For every observation combination
    // For every action
    // Create a new observation -> action link for a given leaf node.

    // Grab all leaf nodes
    // for each combination of observations and actions,

  }

}