package maastar.game


import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class TigerGameTest extends FlatSpec with ShouldMatchers {
  val game = new TigerGame();

  "TigerGame" should "know what states it has" in {
    game.getStates() should equal (Set(game.tigerLeft, game.tigerRight))
  }
  
  it should "be able to give probabilitis for transitions" in {
    val agents = game.getAgents().toIterator
    val actions = game.getActions().toIterator

    val jointAction = Map(
        agents.next() -> actions.next(),
        agents.next() -> actions.next())
    
    game.getStates()
      .toIterator
      .next()
      .getJointActionTransition(jointAction) should not equal(null)
  }

}