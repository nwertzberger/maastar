package maastar.algorithm.maastar

import maastar.game.State
import maastar.heuristic.Heuristic


class PolicyFilter (policy: Policy, belief: Map[State, Float]) {
  def estimatedValuesBelow(value : Float, heuristic : Heuristic) : Set[Policy] = {
    return null
  }
}