package maastar.algorithm.policyiteration

import maastar.agent.Agent
import maastar.game.{Action, State, TigerGame}

/**
 * Created by nwertzberger on 7/10/14.
 */
class MdpPolicyEvaluator {

    def determineBellmanValue(
        policy: Map[State, Map[Agent, Action]],
        game: TigerGame,
        currUtility: Map[State, Double] = Map(),
        decay: Double = 1.0
    ): Map[State, Double] = {
        game.getStates().map { state =>
            val jointAction = policy(state)
            val transition = state.getJointActionTransition(jointAction)
            val utility = transition
                .nextStates()
                .map { case (stateObs, p) =>
                p * currUtility.getOrElse(stateObs.state(), 0.0) * decay
            }.foldLeft(0.0) { (a, b) =>
                a + b
            }
            state -> (utility + transition.reward)
        }.toMap
    }

}
