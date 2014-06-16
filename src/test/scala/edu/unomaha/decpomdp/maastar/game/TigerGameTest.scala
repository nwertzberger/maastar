package edu.unomaha.decpomdp.maastar.game

import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class TigerGameTest extends FlatSpec with ShouldMatchers {
  "TigerGame" should "be able to give back its states" in {
    val game = new TigerGame();
    game.getStates().head should (be (game.tigerLeft) or be (game.tigerRight))
  }

}