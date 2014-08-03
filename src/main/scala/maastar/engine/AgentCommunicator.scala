package maastar.engine

import maastar.agent.Agent
import maastar.game.{Observation, State, StateObservation, Transition}

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
        agentObservations: Map[Agent, Map[Observation, Double]]
    ) = {

        for ((agent, possibleObservations) <- agentObservations) {
            val observations = possibleObservations
                .filter(p => p._2 > Math.random())
                .keySet
            agent.observe(observations)
        }
    }

    def findStateObservationBasedOnChoice(
        nextStates: Map[StateObservation, Double],
        choice: Double
    ): StateObservation = {

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