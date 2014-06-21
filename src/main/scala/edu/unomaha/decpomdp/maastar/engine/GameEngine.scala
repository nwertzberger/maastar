package edu.unomaha.decpomdp.maastar.engine

import edu.unomaha.decpomdp.maastar.game.TigerGame
import edu.unomaha.decpomdp.maastar.game.State
import edu.unomaha.decpomdp.maastar.game.Agent
import edu.unomaha.decpomdp.maastar.game.Action
import edu.unomaha.decpomdp.maastar.game.Observation
import edu.unomaha.decpomdp.maastar.game.Transition
import edu.unomaha.decpomdp.maastar.game.StateObservation

class GameEngine(game: TigerGame) {
  var _state: State = null
  var totalReward = 0.0

  def start(start: State) = {
    _state = start
  }

  def step() = {
    val jointAction = game
      .getAgents()
      .map { agent => (agent, agent.chooseAction()) }
      .toMap
    _state = doAction(_state, jointAction)
  }

  def state() = _state

  private def updateTotalReward(transition: Transition) = {
    totalReward = totalReward + transition.reward()
  }

  private def doAction(state : State, jointAction: Map[Agent, Action]) : State = {
    val transition = state.getJointActionTransition(jointAction)
    updateTotalReward(transition)

    val choice = Math.random()
    val stateObs = findStateObservationBasedOnChoice(transition.nextStates(), choice)

    revealObservationsToAgents(stateObs.observations())
    return stateObs.state()
  }


  private def revealObservationsToAgents(agentObservations: Map[Agent, Map[Observation, Double]]) = {
    for ((agent, possibleObservations) <- agentObservations) {
      val observations = possibleObservations
        .filter(p => p._2 > Math.random())
        .keySet
      agent.observe(observations)
    }
  }

  private def findStateObservationBasedOnChoice(nextStates: Map[StateObservation, Double], choice: Double): StateObservation = {
    val (stateObs, probability) = nextStates.head
    if (choice < probability) {
      return stateObs
    } else {
      return findStateObservationBasedOnChoice(
        nextStates.tail,
        choice - probability)
    }
  }
}