package maastar.algorithm.maastar

import maastar.game.State
import maastar.heuristic.Heuristic


class PolicyFilter (policy: Policy, belief: Map[State, Double]) {
  def estimatedValuesBelow(value : Float) : Set[Policy] = {
    return null
  }
}