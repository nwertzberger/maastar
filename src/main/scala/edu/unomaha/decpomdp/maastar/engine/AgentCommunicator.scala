package edu.unomaha.decpomdp.maastar.engine

import edu.unomaha.decpomdp.maastar.game.Observation
import edu.unomaha.decpomdp.maastar.agent.Agent
import edu.unomaha.decpomdp.maastar.game.StateObservation
import edu.unomaha.decpomdp.maastar.game.State
import edu.unomaha.decpomdp.maastar.game.Action
import edu.unomaha.decpomdp.maastar.game.Transition

class AgentCommunicator {
  def doTransition(transition: Transition): State = {
    val choice = Math.random()
    val stateObs = findStateObservationBasedOnChoice(
      transition.nextStates(),
      choice)

    revealObservationsToAgents(stateObs.observations())
    return stateObs.state()
  }

  def revealObservationsToAgents(
    agentObservations: Map[Agent, Map[Observation, Double]]) = {

    for ((agent, possibleObservations) <- agentObservations) {
      val observations = possibleObservations
        .filter(p => p._2 > Math.random())
        .keySet
      agent.observe(observations)
    }
  }

  def findStateObservationBasedOnChoice(
    nextStates: Map[StateObservation, Double],
    choice: Double): StateObservation = {

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