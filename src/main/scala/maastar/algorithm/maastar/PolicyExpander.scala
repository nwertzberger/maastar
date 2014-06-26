package maastar.algorithm.maastar

import maastar.agent.Agent
import maastar.game.{Observation, Action}
import maastar.policy.{PolicyNode, Policy}


class PolicyExpander(allPossibleAgents : Set[Agent] = Set(),
                     allPossibleActions: Set[Action] = Set(),
                     allPossibleObservations : Set[Observation] = Set()
                     ) {

  def expandPolicyNodes(currPolicy: Policy): Set[Policy] = {
    // For every observation
    // For every action
    // Create a new observation -> action link from the current policy leaf nodes.
    null
  }

}