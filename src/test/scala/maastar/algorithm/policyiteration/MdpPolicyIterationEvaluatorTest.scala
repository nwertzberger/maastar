package maastar.algorithm.policyiteration

import maastar.game.TigerGame
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 7/7/14.
 */
class MdpPolicyIterationEvaluatorTest extends FlatSpec with ShouldMatchers with MockFactory {
    "PolicyIteration" should "determine an optimal policy" in {
        // Markov decision processes are based on the "markov assumption", which is that
        // decisions are based only on the current state.
        val iteration = new MdpPolicyIterationEvaluator()
        val game = new TigerGame()

        iteration.generatePolicy(game) should be(Map(
            game.tigerLeft -> Map(
                game.agent1 -> game.doorRight,
                game.agent2 -> game.doorRight
            ),
            game.tigerRight -> Map(
                game.agent1 -> game.doorLeft,
                game.agent2 -> game.doorLeft
            )
        ))
    }

    it should "determine the value at horizon T for a policy" in {
        val iteration = new MdpPolicyIterationEvaluator()
        val game = new TigerGame()

        val policy = iteration.generatePolicy(game)

        iteration.estimateValueAtState(game.getStartingState(), 1, game, policy) should be(20.0)
        iteration.estimateValueAtState(game.getStartingState(), 2, game, policy) should be(40.0)
    }
}
