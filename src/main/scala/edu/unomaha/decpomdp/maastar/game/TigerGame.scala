package edu.unomaha.decpomdp.maastar.game

import edu.unomaha.decpomdp.maastar.game.Action
import edu.unomaha.decpomdp.maastar.Agent
import edu.unomaha.decpomdp.maastar.game.Observation
import edu.unomaha.decpomdp.maastar.game.State
import edu.unomaha.decpomdp.maastar.game.Transition

class TigerGame {
  val agent1 = new Agent()
  val agent2 = new Agent()

  val listen = new Action()
  val doorLeft = new Action()
  val doorRight = new Action()

  val growlLeft = new Observation()
  val growlRight = new Observation()
  
  val randomTigerNoise = Map(
          agent1 -> Map(growlLeft -> 0.5, growlRight -> 0.5),
          agent2 -> Map(growlLeft -> 0.5, growlRight -> 0.5)
          )

  val tigerLeftTransition = new Transition(
      reward = -2.0,
      observations = Map(
          agent1 -> Map(growlLeft -> 0.85, growlRight -> 0.15),
          agent2 -> Map(growlLeft -> 0.85, growlRight -> 0.15)
          )
      )
  val tigerRightTransition = new Transition(
      reward = -2.0,
      observations = Map(
          agent1 -> Map(growlLeft -> 0.15, growlRight -> 0.85),
          agent2 -> Map(growlLeft -> 0.15, growlRight -> 0.85)
          )
      )
  val singleAgentDeath = new Transition(
      reward = -101.0,
      observations = randomTigerNoise)
  val doubleAgentDeath = new Transition(
      reward = -50.0,
      observations = randomTigerNoise)
  val singleAgentWin = new Transition(
      reward = 9.0,
      observations = randomTigerNoise)
  val doubleAgentWin = new Transition(
      reward = 20.0,
      observations = randomTigerNoise)
  val oneAgentWinsOtherDies = new Transition(
      reward = -100.0,
      observations = randomTigerNoise)

  val tigerLeft = new State(Map(
    Map(agent1 -> listen, agent2 -> listen) -> tigerLeftTransition,
    Map(agent1 -> doorLeft, agent2 -> listen) -> singleAgentDeath,
    Map(agent1 -> listen, agent2 -> doorLeft) -> singleAgentDeath,
    Map(agent1 -> doorLeft, agent2 -> doorLeft) -> doubleAgentDeath,
    Map(agent1 -> doorRight, agent2 -> listen) -> singleAgentWin,
    Map(agent1 -> listen, agent2 -> doorRight) -> singleAgentWin,
    Map(agent1 -> doorRight, agent2 -> doorRight) -> doubleAgentWin,
    Map(agent1 -> doorLeft, agent2 -> doorRight) -> oneAgentWinsOtherDies,
    Map(agent1 -> doorRight, agent2 -> doorLeft) -> oneAgentWinsOtherDies))

  val tigerRight = new State(Map(
    Map(agent1 -> listen, agent2 -> listen) -> tigerRightTransition,
    Map(agent1 -> doorLeft, agent2 -> listen) -> singleAgentWin,
    Map(agent1 -> listen, agent2 -> doorLeft) -> singleAgentWin,
    Map(agent1 -> doorLeft, agent2 -> doorLeft) -> doubleAgentWin,
    Map(agent1 -> doorRight, agent2 -> listen) -> singleAgentDeath,
    Map(agent1 -> listen, agent2 -> doorRight) -> singleAgentDeath,
    Map(agent1 -> doorRight, agent2 -> doorRight) -> doubleAgentDeath,
    Map(agent1 -> doorLeft, agent2 -> doorRight) -> oneAgentWinsOtherDies,
    Map(agent1 -> doorRight, agent2 -> doorLeft) -> oneAgentWinsOtherDies))
  

  tigerLeftTransition.setNextStates(
      Map(tigerLeft -> 1.0, tigerRight -> 0.0)
      )
  tigerRightTransition.setNextStates(
      Map(tigerLeft -> 0.0, tigerRight -> 1.0)
      )
      
  val randomSwitch = Map(tigerLeft -> 0.5, tigerRight -> 0.5)
  singleAgentWin.setNextStates(randomSwitch)
  doubleAgentWin.setNextStates(randomSwitch)
  singleAgentDeath.setNextStates(randomSwitch)
  doubleAgentDeath.setNextStates(randomSwitch)
  oneAgentWinsOtherDies.setNextStates(randomSwitch)
  
}