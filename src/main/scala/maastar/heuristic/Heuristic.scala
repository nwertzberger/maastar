package maastar.heuristic

import maastar.agent.Agent
import maastar.game.State

trait Heuristic {
  def estimateReward(agentStates: Map[Agent,Map[State, Double]]) : Double
}