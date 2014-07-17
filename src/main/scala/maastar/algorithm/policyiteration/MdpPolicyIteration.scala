package maastar.algorithm.policyiteration

import maastar.agent.Agent
import maastar.game.{Action, TigerGame, State}

import scala.annotation.tailrec
import scala.util.Random

/**
 * Created by nwertzberger on 7/6/14.
 */
class MdpPolicyIteration(
    evaluator : PolicyEvaluator = new PolicyEvaluator(),
    builder : PolicyBuilder = new PolicyBuilder()
  ) {

  def generatePolicy(
      game : TigerGame
    ) : Map[State, Map[Agent, Action]] = {

    // Initialize to a random policy
    val actionLocations = game.getActions().toList
    val rand = new Random()
    val agents = game.getAgents()

    val initialPolicy = game.getStates().map { state =>
      state -> agents.map { agent =>
        agent -> actionLocations(rand.nextInt(actionLocations.size))
      }.toMap
    }.toMap

    iteratePolicy(initialPolicy, game)
  }

  @tailrec final def estimateValueAtState(
    state: State,
    policy: Map[State, Map[Agent, Action]],
    game :TigerGame,
    horizon: Int,
    currUtility : Map[State, Double] = Map()) : Double = {
    val utility = evaluator.determineBellmanValue(policy, game, currUtility)
    if (horizon == 1) {
      return utility(state)
    } else {
      return estimateValueAtState(state, policy, game, horizon - 1, utility)
    }
  }

  @tailrec private final def iteratePolicy(
      currPolicy : Map[State, Map[Agent, Action]],
      game : TigerGame,
      currUtility : Map[State, Double] = Map()
      ) : Map[State, Map[Agent, Action]] = {

    val utility = evaluator.determineBellmanValue(currPolicy, game, currUtility)

    // Calculate policy using current value
    val newPolicy = builder.buildMaximalPolicy(game, utility)

    if (currPolicy == newPolicy) {
      return newPolicy
    } else {
      return iteratePolicy(newPolicy, game, utility)
    }
  }
}
