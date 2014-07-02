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

  val expectedObservationPolicies = Set(
    Map[Set[Observation],PolicyNode](Set(burn) -> new PolicyNode(jump, Map()), Set() -> new PolicyNode(jump, Map())),
    Map[Set[Observation],PolicyNode](Set(burn) -> new PolicyNode(sit, Map()), Set() -> new PolicyNode(jump, Map())),
    Map[Set[Observation],PolicyNode](Set(burn) -> new PolicyNode(jump, Map()), Set() -> new PolicyNode(sit, Map())),
    Map[Set[Observation],PolicyNode](Set(burn) -> new PolicyNode(sit, Map()), Set() -> new PolicyNode(sit, Map()))

  )

  "PolicyExpander" should "pre-generate every possible leaf node" in {
    expander.allObservationPolicies should equal(expectedObservationPolicies)
  }

  "PolicyExpander" should "generate every policy tree combo" in {
    val currPolicyNodes = new PolicyNode(jump)
    val policyTrees = expander.expandPolicyNodes(currPolicyNodes).toSet

    val expandedTrees = expectedObservationPolicies
      .map(policy => new PolicyNode(jump, policy))
      .toSet

    policyTrees.toSet should equal(expandedTrees)
  }

  "PolicyExpander" should "not blow up on big expansions" in {
    var expandedNodeIterator = expander.expandPolicyNodes(new PolicyNode(jump))
    for (n <- 2 to 100) {
      expandedNodeIterator = expander.expandPolicyNodes(expandedNodeIterator.next())
    }
    expandedNodeIterator.next().depth() should be(100)
  }

}
