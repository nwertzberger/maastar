package maastar.algorithm.policyiteration

import maastar.game.TigerGame
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 7/10/14.
 */
class MdpPolicyEvaluatorTest extends FlatSpec with ShouldMatchers with MockFactory {

    "PolicyEvaluator" should "calculate bellman equations" in {
        val game = new TigerGame()
        val evaluator = new MdpPolicyEvaluator()
        val agentActions = game.getAgents().map { agent =>
            agent -> game.listen
        }.toMap
        val policy = game.getStates().map { state =>
            (state -> agentActions)
        }.toMap

        val stepOneUtil = evaluator.determineBellmanValue(policy, game)
        stepOneUtil should be(game.getStates().map { state => state -> -2.0}.toMap)

        val stepTwoUtil = evaluator.determineBellmanValue(policy, game, stepOneUtil)
        stepTwoUtil should be(game.getStates().map { state => state -> -4.0}.toMap)

    }


}
