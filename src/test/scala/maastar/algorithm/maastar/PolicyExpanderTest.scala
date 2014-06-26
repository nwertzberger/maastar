package maastar.algorithm.maastar

import maastar.agent.{DecPomdpAgent, Agent}
import maastar.game.{Observation, Action}
import maastar.policy.{PolicyNode, Policy}
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, ShouldMatchers}

@RunWith(classOf[JUnitRunner])
class PolicyExpanderTest extends FlatSpec with ShouldMatchers with MockFactory {
  "PolicyExpander" should "expand to every possible action" in {
    val agent = new DecPomdpAgent("agent")
    val observation = new Observation("burn")
    val action = new Action("jump")
    val action2 = new Action("sit")
    val policy = new Policy(Map(agent -> new PolicyNode(action)))
    val expander = new PolicyExpander(Set(agent), Set(action, action2), Set(observation))
    val expandedPolicy = expander.expandPolicyNodes(policy)

    expandedPolicy should equal(
      Set(
        new Policy(
          Map(agent -> new PolicyNode(action, Map(observation -> new PolicyNode(action))))
        )
      )
    )
  }
}
