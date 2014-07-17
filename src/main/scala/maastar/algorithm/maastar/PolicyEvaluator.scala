package maastar.algorithm.maastar

import maastar.algorithm.policyiteration.MdpPolicyIteration
import maastar.game.State
import maastar.heuristic.{TigerGameHeuristic, Heuristic}
import maastar.policy.{PolicyNode, Policy}

class PolicyEvaluator(heuristic: MdpPolicyIteration = new MdpPolicyIteration(),
                      maxLayers: Int = 10) {
  def utilityOf(policy: PolicyNode, belief: Map[State, Double]): Policy = {
    return null
  }
}
