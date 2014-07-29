package maastar.algorithm.maastar

import maastar.agent.{DecPomdpAgent, Agent}
import maastar.game.{StateObservation, Transition, Action, State}
import maastar.policy.PolicyNode
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 6/23/14.
 */
class PolicyEvaluatorTest extends FlatSpec with ShouldMatchers with MockFactory {
  val agent1 = new DecPomdpAgent("agent1")
  val agent2 = new DecPomdpAgent("agent2")
  val action = new Action("stomp")

  val doubleStomp : Map[Agent, Action] = Map(agent1 -> action, agent2 -> action)
  val state = new State("wasted",
    Map(
       doubleStomp -> new Transition(0.1)
    )
  )
  state.getJointActionTransition(doubleStomp)
    .setNextStates(Map(
      new StateObservation(state, Map()) -> 1.0
    )
  )

  val eval = new PolicyEvaluator()

  "PolicyEvaluator" should "return the base value for a policy" in {
    val stupidPolicy : Map[Agent, PolicyNode] = Map(
      agent1 -> new PolicyNode(action),
      agent2 -> new PolicyNode(action)
    )

    eval.utilityOf(stupidPolicy, Map(state -> 1.0)) should be(0.1)
  }

  it should "track down to sub-transitions" in {
    val stupidPolicy : Map[Agent, PolicyNode] = Map(
      agent1 -> new PolicyNode(action, Map(Set() -> new PolicyNode(action))),
      agent2 -> new PolicyNode(action, Map(Set() -> new PolicyNode(action)))
    )
    eval.utilityOf(stupidPolicy, Map(state -> 1.0)) should be(0.2)
  }

}
