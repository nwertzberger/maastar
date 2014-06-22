package maastar.engine

import maastar.game.TigerGame
import maastar.game.State
import maastar.agent.Agent
import maastar.game.Action
import maastar.game.Observation
import maastar.game.Transition
import maastar.game.StateObservation

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