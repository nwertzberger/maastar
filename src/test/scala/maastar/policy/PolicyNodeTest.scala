package maastar.policy

import maastar.game.{Action, Observation}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 6/30/14.
 */
class PolicyNodeTest extends FlatSpec with ShouldMatchers with MockFactory {

    "PolicyNode" should "create a perfect clone of itself" in {
        val act = new Action("bite")
        val obs = new Observation("smell")
        val node = new PolicyNode(act, Map(Set(obs) -> new PolicyNode(act)))
        node.createClone() should not be theSameInstanceAs(node)
        node.createClone() should equal(node)
    }


}
