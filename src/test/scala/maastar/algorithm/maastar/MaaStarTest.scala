package maastar.algorithm.maastar

import maastar.agent.DecPomdpAgent
import maastar.game.TigerGame
import maastar.policy.PolicyNode
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers


/**
 * The class for the MaaStar Algorithm
 */
@RunWith(classOf[JUnitRunner])
class MaaStarTest extends FlatSpec with ShouldMatchers {
    "game" should "work for depth 0 policies in an unsure game" in {
        val game = new TigerGame()

        val maaStar = new MaaStar(game)
        val policy = maaStar.calculatePolicy(Map(
            game.tigerLeft -> 0.5,
            game.tigerRight -> 0.5
        ), 0)

        println(policy)
        policy.agentPolicies should be (Map(
            game.agent1 -> new PolicyNode(game.listen),
            game.agent2 -> new PolicyNode(game.listen)
        ))
    }
    "game" should "work for depth 0 policies in a sure game (right)" in {
        val game = new TigerGame()

        val maaStar = new MaaStar(game)
        val policy = maaStar.calculatePolicy(Map(
            game.tigerLeft -> 0.01,
            game.tigerRight -> 0.99
        ), 0)

        println(policy)
        policy.agentPolicies should be (Map(
            game.agent1 -> new PolicyNode(game.doorLeft),
            game.agent2 -> new PolicyNode(game.doorLeft)
        ))
    }
    "game" should "work for depth 0 policies in a sure game (left)" in {
        val game = new TigerGame()

        val maaStar = new MaaStar(game)
        val policy = maaStar.calculatePolicy(Map(
            game.tigerLeft -> 0.99,
            game.tigerRight -> 0.01
        ), 0)

        println(policy)
        policy.agentPolicies should be (Map(
            game.agent1 -> new PolicyNode(game.doorRight),
            game.agent2 -> new PolicyNode(game.doorRight)
        ))
    }

    "game" should "work for depth 1 policies in an unsure game" in {
        val game = new TigerGame()

        val maaStar = new MaaStar(game)
        val policy = maaStar.calculatePolicy(Map(
            game.tigerLeft -> 0.5,
            game.tigerRight -> 0.5
        ), 1)

        policy.agentPolicies.foreach{ case (agent, p) =>
            println(agent + " = " + p.action)
            p.transitions.foreach{ case (obs, node) =>
                    println("    " + obs + " = " + node)
            }
        }
    }

    "game" should "work for depth 1 policies in a skewed game" in {
        val game = new TigerGame()

        val maaStar = new MaaStar(game)
        val policy = maaStar.calculatePolicy(Map(
            game.tigerLeft -> 0.6,
            game.tigerRight -> 0.4
        ), 1)

        policy.agentPolicies.foreach{ case (agent, p) =>
            println(agent + " = " + p.action)
            p.transitions.foreach{ case (obs, node) =>
                println("    " + obs + " = " + node)
            }
        }
    }

}