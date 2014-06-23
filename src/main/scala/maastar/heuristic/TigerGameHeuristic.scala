package maastar.heuristic

import maastar.agent.Agent
import maastar.game.{Action, State}

/**
 * In the tiger game, converting it to an optimistic answer can be done
 * more naievely than in other MDP situations.
 *
 * We know the damned state. We'll pick the right one every time.
 *
 */
class TigerGameHeuristic extends Heuristic {
  def estimateReward(
                      agentStates: Map[Agent, Map[State, Double]],
                      jointAction: Map[Agent, Action]): Double = {
    return agentStates
      .values
      .foldLeft(Set[State]()) { (acc, stuff) => acc ++ stuff.keySet}
      .map(_.getJointActionTransition(jointAction).reward())
      .max
  }
}