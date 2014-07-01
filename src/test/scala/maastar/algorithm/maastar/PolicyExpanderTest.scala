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

  // This is the toy world we built... one observation, two agents, two actions
  val alice = new DecPomdpAgent("alice")
  val bob = new DecPomdpAgent("bob")
  val burn = new Observation("burn")
  val jump = new Action("jump")
  val sit = new Action("sit")

  val expander = new PolicyExpander(Set(alice, bob), Set(jump, sit), Set(burn))

  "PolicyExpander" should "pre-generate every possible leaf node" in {

    val expectedObservationPolicies = Set(
      Map(Set(burn) -> new PolicyNode(jump, Map()), Set() -> new PolicyNode(jump, Map())),
      Map(Set(burn) -> new PolicyNode(sit, Map()), Set() -> new PolicyNode(jump, Map())),
      Map(Set(burn) -> new PolicyNode(jump, Map()), Set() -> new PolicyNode(sit, Map())),
      Map(Set(burn) -> new PolicyNode(sit, Map()), Set() -> new PolicyNode(sit, Map()))
    )
    expander.allObservationPolicies should equal(expectedObservationPolicies)
  }

  "PolicyExpander" should "generate every policy tree combo without blowing up the stack" in {
    val currPolicyNodes = new PolicyNode(jump,Map(
        Set() -> new PolicyNode(jump),
        Set(burn) -> new PolicyNode(sit)
    ))
    val policyTrees = expander.expandPolicyNodes(currPolicyNodes)

    /*
    for (policy <- policyTrees) {
      println(policy)
    }
    */

  }
}
