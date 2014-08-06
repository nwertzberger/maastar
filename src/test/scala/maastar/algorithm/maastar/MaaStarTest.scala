package maastar.algorithm.maastar

import maastar.game.TigerGame
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers


/**
 * The class for the MaaStar Algorithm
 */
@RunWith(classOf[JUnitRunner])
class MaaStarTest extends FlatSpec with ShouldMatchers {
    "game" should "work" in {
        val game = new TigerGame()
        val maaStar = new MaaStar()
        val policy =maaStar.calculatePolicy(Map(
            game.tigerLeft -> 0.5,
            game.tigerRight -> 0.5
        ))
        println(policy)
    }

}