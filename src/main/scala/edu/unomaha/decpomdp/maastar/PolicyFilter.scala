package edu.unomaha.decpomdp.maastar

import edu.unomaha.decpomdp.maastar.game.State


class PolicyFilter (policy: Policy, belief: Map[State, Float]) {
  def estimatedValuesBelow(value : Float, heuristic : Heuristic) : Set[Policy] = {
    return null
  }
}