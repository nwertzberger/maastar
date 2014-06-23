package maastar.algorithm.maastar

import maastar.game.Action
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, ShouldMatchers}

@RunWith(classOf[JUnitRunner])
class PolicyExpanderTest extends FlatSpec with ShouldMatchers with MockFactory {
  "PolicyExpander" should "expand to every possible action" in {
    val action = new Action()
    val policy = new Policy()
    val expander = new PolicyExpander(Set(action))
    val expandedPolicy = expander.expandPolicyNodes(policy)

    expandedPolicy should equal(Set(new Policy(Map(action -> policy))))
  }

}
