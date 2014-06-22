package maastar.algorithm

import maastar.game.State
import maastar.agent.Agent
import maastar.game.Action

trait Heuristic {
  def estimateReward(agentStates: Map[Agent,Map[State, Double]]) : Double
}