package maastar.heuristic

import maastar.agent.Agent
import maastar.game.{Action, State}

trait Heuristic {
    def estimateReward(
        agentStates: Map[Agent, Map[State, Double]],
        jointAction: Map[Agent, Action]
    ): Double
}