package maastar.algorithm.maastar

import maastar.algorithm.policyiteration.MdpPolicyIterationEvaluator
import maastar.game.State
import maastar.policy.Policy


class PolicyChooser(
    policyEvaluator: PolicyEvaluator = new PolicyEvaluator(),
    heuristicEvaluator: MdpPolicyIterationEvaluator = new MdpPolicyIterationEvaluator()
) {
    def findBestPolicy(openPolicies: Set[Policy], belief: Map[State, Double], depth: Int = 4): Policy = {
        openPolicies.map { policy =>
            val policyDepth = policy.depth()
            val heuristicDepth = depth - policyDepth
            policyEvaluator.utilityOf(policy.agentPolicies, belief, depth)
        }
        null
    }
}