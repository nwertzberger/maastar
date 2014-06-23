package maastar.algorithm.maastar

import maastar.game.{Action, State}


class PolicyExpander(allPossibleActions: Set[Action] = Set()) {
  def determinePoliciesAtBelief(belief: Map[State, Float]): Set[Policy] = {
    return null
  }

  def expandPolicyNodes(currPolicy: Policy): Set[Policy] = {
    return allPossibleActions.map(
      action => new Policy(Map(action -> currPolicy))
    ).toSet
  }

}