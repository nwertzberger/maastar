package maastar.algorithm.maastar

import maastar.algorithm.policyiteration.MdpPolicyIterationEvaluator
import maastar.game.{State, TigerGame}
import maastar.policy.{Policy, PolicyNode, PolicyValueOrdering}

import scala.collection.JavaConversions
import scala.collection.mutable.PriorityQueue


/**
 * The class for the MaaStar Algorithm
 *
 * MaaStar
 */
class MaaStar(
    game: TigerGame
) {

    val evaluator = new PolicyEvaluator(
        new MdpPolicyIterationEvaluator(),
        game)

    val policyExpander = new PolicyExpander(
        new PolicyNodeExpander(game.getActions(), game.getObservations())
    )

    def calculatePolicyJava(initialBelief: java.util.Map[State, java.lang.Double], maxDepth: Int): Policy = {
        val newBelief = JavaConversions.mapAsScalaMap(initialBelief)
            .map{case (k, v) => k -> Double2double(v)}.toMap

        calculatePolicy(newBelief, maxDepth)
    }

    def calculatePolicy(initialBelief: Map[State, Double], maxDepth: Int): Policy = {

        val openPolicies = new PriorityQueue[Policy]()(PolicyValueOrdering)

        var counter = 0
        // Initialize all possible depth 0 policies.
        game.getActions().foreach { act1 =>
            game.getActions().foreach { act2 =>
                val newPolicy = new Policy(
                    Map(game.agent1 -> new PolicyNode(act1),
                        game.agent2 -> new PolicyNode(act2)
                    )
                )
                counter = counter + 1
                newPolicy.estimate = evaluator.utilityOf(
                    newPolicy.agentPolicies,
                    initialBelief,
                    maxDepth)
                openPolicies.enqueue(newPolicy)
            }
        }

        while (!openPolicies.isEmpty) {
            val candidate = openPolicies.dequeue()
            if (candidate.depth >= maxDepth)
                return candidate

            val children = policyExpander.expandPolicy(candidate)
            children.foreach { c =>
                counter = counter + 1
                c.estimate = evaluator.utilityOf(c.agentPolicies, initialBelief, maxDepth)
                openPolicies.enqueue(c)
            }
        }

        throw new Exception("candidate policy blew up")
    }
}