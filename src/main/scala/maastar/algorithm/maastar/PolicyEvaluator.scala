package maastar.algorithm.maastar

import java.beans.Statement

import maastar.agent.Agent
import maastar.algorithm.policyiteration.{MdpPolicyIterationEvaluator, MdpPolicyEvaluator}
import maastar.game._
import maastar.policy.PolicyNode

import scala.collection.mutable

class PolicyEvaluator(
    policyHeuristic : MdpPolicyIterationEvaluator = new MdpPolicyIterationEvaluator(),
    game : TigerGame = new TigerGame()
) {

    val heuristicPolicy = policyHeuristic.generatePolicy(game)

    def utilityOf(
        jointPolicy: Map[Agent, PolicyNode],
        belief: Map[State, Double],
        depth : Int
    ): Double = {
        belief.map { case (state, prob) =>
            val transition = getTransition(jointPolicy, state)
            prob * (transition.reward + getFuture(transition, jointPolicy, depth - 1))
        }.fold(0.0) { (acc: Double, x: Double) => acc + x }
    }

    private def getFuture(
        transition: Transition, policies: Map[Agent, PolicyNode],
        depth : Int
    ): Double = {
        if (isAnyPolicyEmpty(policies)) {
            if (depth > 0) {
                transition.nextStates().map { case (stateObs, stateProb) =>
                    val agentObservations = stateObs.observations()
                    val state = stateObs.state()
                    stateProb * policyHeuristic.estimateValueAtState(state, depth, game, heuristicPolicy)
                }.fold(0.0) { (acc: Double, x: Double) => acc + x }
            } else {
                0.0
            }
        } else {
            transition.nextStates().map { case (stateObs, stateProb) =>
                val agentObservations = stateObs.observations()
                val state = stateObs.state()
                stateProb * allTransitionRewards(
                    policies,
                    agentObservations,
                    state,
                    depth)
            }.fold(0.0) { (acc: Double, x: Double) => acc + x }
        }
    }

    private def allTransitionRewards(
        policies: Map[Agent, PolicyNode],
        agentObservations: Map[Agent, Map[Observation, Double]],
        state: State,
        depth : Int
    ): Double = {
        getAllAgentObservationCombinations(
            agentObservations.map {
                case (agent, obs) => agent -> obs.keys.toSet
            }
        ).map { agentObs =>
            val newPolicy = policies.map { case (agent, node) =>
                agent -> node.transitions(agentObs(agent))
            }.toMap
            val prob = calculateJointObservationProbability(agentObs, agentObservations)
            val util = utilityOf(newPolicy, Map(state -> 1.0), depth)
            prob * util
        }.fold(0.0) { (acc, x) => acc + x }
    }

    private def getTransition(policies: Map[Agent, PolicyNode], state: State) = {
        val jointAction = policies.map {
            case (agent, node) => agent -> node.action
        }.toMap
        state.getJointActionTransition(jointAction)
    }

    private def isAnyPolicyEmpty(policies: Map[Agent, PolicyNode]): Boolean = {
        policies.values
            .map { policy => policy.transitions.isEmpty }
            .fold(false) { (acc, x) => x || acc }
    }

    private def calculateJointObservationProbability(
        agentObs: Map[Agent, Set[Observation]],
        agentObservationProbabilities: Map[Agent, Map[Observation, Double]]
    ): Double = {

        agentObservationProbabilities.map {
            case (agent, observationProbabilities) =>
                observationProbabilities.map { case (obs, prob) =>
                    if (agentObs(agent).contains(obs)) prob else (1.0 - prob)
                }.fold(1.0) { (acc, x) => acc * x }
        }.fold(1.0) { (acc, x) => acc * x }

    }

    def getAllAgentObservationCombinations(
        agentObservations: Map[Agent, Set[Observation]]
    ): Iterator[Map[Agent, Set[Observation]]] = {

        val agentCombos: mutable.HashMap[Agent, Iterator[Set[Observation]]] = new mutable.HashMap()
        val agents: mutable.Stack[Agent] = new mutable.Stack()

        agentObservations.foreach { case (agent, observations) =>
            agentCombos.put(agent, observations.subsets)
            agents.push(agent)
        }

        val activeAgentMap: mutable.Map[Agent, Set[Observation]] = new mutable.HashMap()

        return new Iterator[Map[Agent, Set[Observation]]]() {
            def hasNext() = agentCombos.values
                .map { sets => !sets.isEmpty }
                .fold(false) { (acc, x) => acc || x }

            def next(): Map[Agent, Set[Observation]] = {
                if (activeAgentMap.isEmpty) {
                    initializeCombos(agentCombos, activeAgentMap)
                } else {
                    getNextCombo(agentCombos, agents, agentObservations, activeAgentMap)
                }
            }
        }
    }

    private def getNextCombo(
        agentCombos: mutable.Map[Agent, Iterator[Set[Observation]]],
        agents: mutable.Stack[Agent],
        agentObservations: Map[Agent, Set[Observation]],
        activeAgentObservations: mutable.Map[Agent, Set[Observation]]
    ): Map[Agent, Set[Observation]] = {

        // Used to track any agents that will be updated
        val changingAgents: mutable.Stack[Agent] = new mutable.Stack[Agent]

        // Find all the agents that should change this move.
        var agent = agents.pop()
        while (!agents.isEmpty && agentCombos(agent).isEmpty) {
            changingAgents.push(agent)
            agentCombos.put(agent, agentObservations(agent).subsets)
            agent = agents.pop()
        }
        changingAgents.push(agent)

        // Activate the needed changes.
        while (!changingAgents.isEmpty) {
            val agent = changingAgents.pop()
            activeAgentObservations.put(agent, agentCombos(agent).next())
            agents.push(agent)
        }

        // Create an immutable map
        return activeAgentObservations.toMap
    }

    private def initializeCombos(
        agentCombos: mutable.Map[Agent, Iterator[Set[Observation]]],
        newCombos: mutable.Map[Agent, Set[Observation]]
    ): Map[Agent, Set[Observation]] = {

        agentCombos.foreach { case (agent, obsSetIterator) =>
            newCombos.put(agent, obsSetIterator.next())
        }
        return newCombos.toMap
    }
}
