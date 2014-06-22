package maastar.engine


import org.scalatest.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalamock.scalatest.MockFactory

import maastar.game.TigerGame
import maastar.agent.Agent

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import maastar.game.Action
import maastar.game.State
import maastar.game.Transition

@RunWith(classOf[JUnitRunner])
class GameEngineTest extends FlatSpec with ShouldMatchers with MockFactory {

  "GameEngine" should "ask agents for actions in a step" in {
    val (engine, agent, _, _, _, _) = setupEngine()

    engine.start()
    engine.step()

    (agent.chooseAction _).verify()
  }
  
  it should "find the transition for a joint action" in {
    val(engine, agent, action, state, _, _) = setupEngine()
    
    engine.start()
    engine.step()
    
    (state.getJointActionTransition _).verify(Map(agent -> action))
  }
  
  it should "send the joint action transition to a communicator" in {
    val (engine, agent, action, state, transition, communicator) = setupEngine()
    
    engine.start()
    engine.step()
    
    (communicator.doTransition _).verify(transition)
  }
  
  def setupEngine() : (GameEngine, Agent, Action, State, Transition, AgentCommunicator) = {
    val agent = stub[Agent]
    val communicator = stub[AgentCommunicator]
    val game = stub[TigerGame]
    val action = stub[Action]
    val state = stub[State]
    val transition = stub[Transition]

    (agent.chooseAction _).when().returns(action)
    (game.getStartingState _).when().returns(state)
    (game.getAgents _).when().returns(Set(agent))
    (state.getJointActionTransition _).when(*).returns(transition)

    return (new GameEngine(game, communicator), agent, action, state, transition, communicator)
  }

}