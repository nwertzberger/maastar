package maastar.algorithm.maastar

import maastar.agent.{DecPomdpAgent, Agent}
import maastar.game.{StateObservation, Transition, Action, State}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 6/23/14.
 */
class PolicyEvaluatorTest extends FlatSpec with ShouldMatchers with MockFactory {
  val agent1 = new DecPomdpAgent("agent1")
  val agent2 = new DecPomdpAgent("agent2")
  val action = new Action("stomp")

  "PolicyEvaluator" should "return 0 for a policy that does not affect cost" in {

  }

}
