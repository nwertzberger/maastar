package maastar.algorithm.maastar

import maastar.game.{State, TigerGame}
import maastar.policy.{Policy, PolicyNode, PolicyValueOrdering}

import scala.collection.mutable.PriorityQueue


/**
 * The class for the MaaStar Algorithm
 *
 * MaaStar
 */
class MaaStar(
    evaluator : PolicyEvaluator = new PolicyEvaluator(),
    maxDepth: Int = 2 ) {

    def calculatePolicy(initialBelief: Map[State, Double], game: TigerGame = new TigerGame()): Policy = {
        val policyExpander = new PolicyExpander(
            new PolicyNodeExpander(game.getActions(), game.getObservations())
        )
        val openPolicies = new PriorityQueue[Policy]()(PolicyValueOrdering)

        game.getActions().foreach { act1 =>
            game.getActions().foreach { act2 =>
                val newPolicy = new Policy(Map(game.agent1 -> new PolicyNode(act1), game.agent2 -> new PolicyNode(act2)))
                newPolicy.estimate = evaluator.utilityOf(newPolicy.agentPolicies, initialBelief, maxDepth)
                openPolicies.enqueue(newPolicy)
            }
        }

        while (!openPolicies.isEmpty) {
            val candidate = openPolicies.dequeue()
            println("Depth = " + candidate.depth())
            println("Value = " + candidate.estimate)
            println(candidate)
            if (candidate.depth >= maxDepth)
                return candidate

            val children = policyExpander.expandPolicy(candidate)
            var count = 0
            children.foreach { c =>
                count = count + 1
                println("Depth = " + c.depth())
                println("Count = " + count)
                c.estimate = evaluator.utilityOf(c.agentPolicies, initialBelief, maxDepth)
                openPolicies.enqueue(c)
            }
        }
        throw new Exception("candidate policy blew up")
    }
}