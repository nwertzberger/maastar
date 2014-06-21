package edu.unomaha.decpomdp.maastar.engine

import org.scalatest.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalamock.scalatest.MockFactory
import edu.unomaha.decpomdp.maastar.game.TigerGame
import edu.unomaha.decpomdp.maastar.game.Agent
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import edu.unomaha.decpomdp.maastar.game.Action
import edu.unomaha.decpomdp.maastar.game.State

@RunWith(classOf[JUnitRunner])
class GameEngineTest extends FlatSpec with ShouldMatchers with MockFactory {

  "GameEngine" should "ask agents for actions in a step" in {
    val game = stub[TigerGame]
    val agent = stub[Agent]
    val action = stub[Action]
    val state = stub[State]

    (agent.chooseAction _).when().returns(action)
    (game.getStartingState _).when().returns(state)
    (game.getAgents _).when().returns(Set(agent))

    val engine = new GameEngine(game)
    engine.step()

    (agent.chooseAction _).verify()
  }

}