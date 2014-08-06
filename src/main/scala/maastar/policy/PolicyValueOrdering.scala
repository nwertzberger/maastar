package maastar.policy

import maastar.agent.Agent
import maastar.algorithm.maastar.PolicyEvaluator
import maastar.algorithm.policyiteration.MdpPolicyIterationEvaluator
import maastar.game.{State, TigerGame}

/**
 * Created by nwertzberger on 6/25/14.
 */
object PolicyValueOrdering extends Ordering[Policy] {
    def compare(pol1: Policy, pol2: Policy) = {
        pol1.estimate compare pol2.estimate
    }
}
