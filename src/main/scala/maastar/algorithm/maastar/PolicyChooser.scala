package maastar.algorithm.maastar

import maastar.game.State
import maastar.heuristic.Heuristic


class PolicyChooser(heuristic : Heuristic) {
  def findBestPolicy(openPolicies: Set[Policy], belief: Map[State, Double]) : Policy = {
    return new Policy()
  }
}