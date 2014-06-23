package maastar.algorithm.maastar

import maastar.game.Action


class PolicyExpander(allPossibleActions: Set[Action] = Set()) {

  def expandPolicyNodes(currPolicy: Policy): Set[Policy] = {
    return allPossibleActions.map(
      action => new Policy(Map(action -> currPolicy))
    ).toSet
  }

}