package maastar.algorithm.policyiteration

import maastar.game.{TigerGame, State}

import scala.annotation.tailrec
import scala.util.Random

/**
 * Created by nwertzberger on 7/6/14.
 */
class PolicyIteration(
    evaluator : PolicyEvaluator = new PolicyEvaluator(),
    builder : PolicyBuilder = new PolicyBuilder()
  ) {

  @tailrec final def generatePolicy(
      initialState : State,
      game : TigerGame,
      currUtility : Map[State, Double] = Map()
    ) : Double = {

    // Initialize to a random policy
    val actionLocations = game.getActions().toList
    val rand = new Random()
    val agents = game.getAgents()

    val initialPolicy = game.getStates().map { state =>
      state -> agents.map { agent =>
        agent -> actionLocations(rand.nextInt(actionLocations.size))
      }.toMap
    }.toMap

    val utility = evaluator.determineBellmanValue(initialPolicy, game, currUtility)

    // Calculate policy using current value
    val newPolicy = builder.buildMaximalPolicy(game, utility)

    if (initialPolicy != initialPolicy) {
      return generatePolicy(initialState, game, utility)
    } else {
      return utility(initialState)
    }
  }
}
