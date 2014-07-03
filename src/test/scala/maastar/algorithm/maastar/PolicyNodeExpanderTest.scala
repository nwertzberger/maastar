package maastar.algorithm.maastar

import maastar.agent.{DecPomdpAgent, Agent}
import maastar.game.{Observation, Action}
import maastar.policy.{PolicyNode, Policy}
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, ShouldMatchers}

@RunWith(classOf[JUnitRunner])
class PolicyNodeExpanderTest extends FlatSpec with ShouldMatchers with MockFactory {

  // This is the toy world we built... one observation, two agents, two actions
  val alice = new DecPomdpAgent("alice")
  val bob = new DecPomdpAgent("bob")
  val burn = new Observation("burn")
  val jump = new Action("jump")
  val sit = new Action("sit")

  val expander = new PolicyNodeExpander(Set(alice, bob), Set(jump, sit), Set(burn))

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
    val policyTrees = expander.expandPolicyNode(currPolicyNodes).toSet

    val expandedTrees = expectedObservationPolicies
      .map(policy => new PolicyNode(jump, policy))
      .toSet

    val policySet = policyTrees.toSet
    policySet.toSet should equal(expandedTrees)
  }

  "PolicyExpander" should "expand exponentially" in {
    var expandedNodeIterator = expander.expandPolicyNode(new PolicyNode(jump))
    expandedNodeIterator = expander.expandPolicyNode(expandedNodeIterator.next())
    var runs = 0
    for (expandedNode <- expandedNodeIterator) {
      runs = runs + 1
      expandedNode.depth() should be(2)
    }
    runs should be(16)
  }

  "PolicyExpander" should "not blow up on big expansions" in {
    var expandedNodeIterator = expander.expandPolicyNode(new PolicyNode(jump))
    for (i <- 2 to 10) {
      expandedNodeIterator = expander.expandPolicyNode(expandedNodeIterator.next())
    }
    expandedNodeIterator.next().depth() should be(10)
    expandedNodeIterator.next().totalNodes() should be(math.pow(2, 11).toInt - 1)
  }

}
