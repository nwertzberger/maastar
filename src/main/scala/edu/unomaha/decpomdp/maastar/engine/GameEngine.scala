package edu.unomaha.decpomdp.maastar.engine

import edu.unomaha.decpomdp.maastar.game.TigerGame
import edu.unomaha.decpomdp.maastar.game.State
import edu.unomaha.decpomdp.maastar.agent.Agent
import edu.unomaha.decpomdp.maastar.game.Action
import edu.unomaha.decpomdp.maastar.game.Observation
import edu.unomaha.decpomdp.maastar.game.Transition
import edu.unomaha.decpomdp.maastar.game.StateObservation

class GameEngine(game: TigerGame, communicator: AgentCommunicator = new AgentCommunicator()) {
  var _state: State = null
  var totalReward = 0.0

  def start() = {
    _state = game.getStartingState()
    totalReward = 0.0
  }

  def step() = {
    val jointAction = game
      .getAgents()
      .map { agent => (agent, agent.chooseAction()) }
      .toMap
    val transition = state.getJointActionTransition(jointAction)
    updateTotalReward(transition)
    _state = communicator.doTransition(transition)
  }

  def state() = _state

  private def updateTotalReward(transition: Transition) = {
    totalReward = totalReward + transition.reward()
  }

}