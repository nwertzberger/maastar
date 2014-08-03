package maastar.algorithm.policyiteration

import maastar.agent.{Agent, DecPomdpAgent}
import maastar.game.{Action, TigerGame}
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 7/14/14.
 */
class MdpPolicyBuilderTest extends FlatSpec with ShouldMatchers {
    val builder = new MdpPolicyBuilder()
    val agents: Set[Agent] = Set(new DecPomdpAgent("agent1"), new DecPomdpAgent("agent2"))
    val actions = Set(new Action("hit"), new Action("miss"))

    "PolicyBuilder" should "shoot out every combo" in {
        val combos = builder.getAllJointActions(agents, actions)
        combos.size should be(4)
    }

    it should "find an optimal policy" in {
        val game = new TigerGame()
        builder.buildMaximalPolicy(game) should be(Map(
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
}
