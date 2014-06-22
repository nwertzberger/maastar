package maastar.algorithm

import maastar.agent.Agent
import maastar.game.State
import maastar.game.Action

/**
 * In the tiger game, converting it to an optimistic answer can be done
 * more naievely than in other MDP situations.
 * 
 * We know the damned state. We'll pick the right one every time.
 * 
 */
class TigerGameHeuristic extends Heuristic {
  def estimateReward(agentStates: Map[Agent,Map[State,Double]]): Double = {
    val stateValues = agentStates.values
    return 0.0
  }
}