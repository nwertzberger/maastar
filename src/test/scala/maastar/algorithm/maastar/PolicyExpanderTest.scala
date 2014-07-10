package maastar.algorithm.maastar

import maastar.agent.{DecPomdpAgent, Agent}
import maastar.game.{Action, Observation}
import maastar.policy.{Policy, PolicyNode}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 7/2/14.
 */
class PolicyExpanderTest extends FlatSpec with ShouldMatchers with MockFactory {

  val smell = new Observation("smell")
  val jump = new Action("jump")
  val sit = new Action("sit")
  val node = new PolicyNode(jump)
  val agent1 = new DecPomdpAgent("agent1")
  val agent2 = new DecPomdpAgent("agent2")
  val testPolicy = new Policy(Map(agent1 -> node, agent2 -> node))

  val nodeExpander = new PolicyNodeExpander(Set(jump, sit), Set(smell))
  val expander = new PolicyExpander(nodeExpander)

  "PolicyExpander" should "expand to every possible sub policy" in {
    var policyCount = 0
    // Test a brute force expansion of 2 agents at 1 layer deep
    for (policy <- expander.expandPolicy(testPolicy)) {
      policyCount = policyCount + 1
    }
    policyCount should be(math.pow(4, 2).toInt)
  }

  "PolicyExpander" should "expand policies exponentially" in {
    var policyCount = 0
    // Test a brute force expansion of 2 agents at 2 layers deep
    for (policy <- expander.expandPolicy(testPolicy)) {
      for (subPolicy <- expander.expandPolicy(policy)) {
        policyCount = policyCount + 1
      }
    }
    policyCount should be (math.pow(4 * 16,2).toInt)
  }
}
