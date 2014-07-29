package maastar.algorithm.maastar

import maastar.agent.Agent
import maastar.algorithm.policyiteration.MdpPolicyIteration
import maastar.game._
import maastar.heuristic.{TigerGameHeuristic, Heuristic}
import maastar.policy.{PolicyNode, Policy}

class PolicyEvaluator() {
  def utilityOf(
      policies: Map[Agent, PolicyNode],
      belief: Map[State, Double]): Double = {

    belief.map { case (state, prob) =>
      val transition = getTransition(policies, state)
      prob * (transition.reward + getFuture(transition, policies))
    }.fold(0.0) { (acc: Double, x: Double) => acc + x}
  }

  def getTransition(policies: Map[Agent, PolicyNode], state: State) = {
    state.getJointActionTransition(
      policies.map{case (agent, node) => agent -> node.action}.toMap
    )
  }

  def getFuture(transition: Transition, policies: Map[Agent, PolicyNode]): Double = {
    if (policies.values
      .map{policy => policy.transitions.isEmpty}
      .fold(false){(acc, x) => x || acc}) {
      0.0
    }
    else {
      transition.nextStates().map { case (stateObs, stateProb) =>
        val agentObservations = stateObs.observations()
        val state = stateObs.state()
        stateProb * allTransitionRewards(
          policies,
          agentObservations,
          state)
      }.fold(0.0) { (acc: Double, x: Double) => acc + x}
    }
  }

  def allTransitionRewards(
      policies: Map[Agent, PolicyNode],
      agentObservations: Map[Agent, Map[Observation, Double]],
      state: State): Double = {
    getAllAgentObservationCombinations(agentObservations.map{case (agent, obs) => agent -> obs.keys.toSet})
      .map { agentObs =>
        val newPolicy = policies.map { case (agent, node) =>
          agent -> node.transitions(agentObs(agent))
        }.toMap
        val prob = getAgentObsProbability(agentObs, agentObservations)
        val util = utilityOf(newPolicy, Map(state -> 1.0))
        prob * util
      }.fold(0.0){(acc, x) => acc + x}
  }

  def getAgentObsProbability(
      agentObs : Map[Agent, Set[Observation]],
      agentObsProbs : Map[Agent, Map[Observation, Double]]) : Double = {
    0.1
  }

  def getAllAgentObservationCombinations(
      agentObservations: Map[Agent, Set[Observation]]): Iterator[Map[Agent, Set[Observation]]] = {
    Set[Map[Agent, Set[Observation]]]().toIterator
  }
}
