package maastar.algorithm.policyiteration

import maastar.agent.{DecPomdpAgent, Agent}
import maastar.game.{TigerGame, Action}
import org.scalatest.{ShouldMatchers, FlatSpec}

/**
 * Created by nwertzberger on 7/14/14.
 */
class PolicyBuilderTest extends FlatSpec with ShouldMatchers {
  val builder = new PolicyBuilder()
  val agents: Set[Agent] = Set(new DecPomdpAgent("agent1"), new DecPomdpAgent("agent2"))
  val actions = Set(new Action("hit"), new Action("miss"))

  "PolicyBuilder" should "shoot out every combo" in {
    val combos = builder.getAllJointActions(agents, actions)
    combos.size should be(4)
  }

  it should "find an optimal policy" in {
    println(builder.buildMaximalPolicy(new TigerGame()))
  }
}
