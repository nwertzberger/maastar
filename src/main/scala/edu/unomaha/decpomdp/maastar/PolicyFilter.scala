package edu.unomaha.decpomdp.maastar

import edu.unomaha.decpomdp.maastar.game.Policy

class PolicyFilter (policy: Policy, belief: Belief) {
  def estimatedValuesBelow(value : Float, heuristic : Heuristic) : Set[Policy] = {
    return null
  }
}