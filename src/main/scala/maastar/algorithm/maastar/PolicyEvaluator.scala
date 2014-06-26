package maastar.algorithm.maastar

import maastar.game.State
import maastar.heuristic.{TigerGameHeuristic, Heuristic}
import maastar.policy.{PolicyNode, Policy}

class PolicyEvaluator(heuristic: Heuristic = new TigerGameHeuristic(),
                      maxLayers: Int = 10) {
  def utilityOf(policy: PolicyNode, belief: Map[State, Double]): Policy = {
    return null
  }
}
