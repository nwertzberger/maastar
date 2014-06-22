package maastar.game

import maastar.agent.Agent

import maastar.agent.DecPomdpAgent

class TigerGame (
    agent1 : Agent = new DecPomdpAgent("Agent 1"),
    agent2 : Agent = new DecPomdpAgent("Agent 2")) {

  val listen = new Action("Listen")
  val doorLeft = new Action("Open Door Left")
  val doorRight = new Action("Open Door Right")

  val growlLeft = new Observation("Hears a Growl Behind Left Door")
  val growlRight = new Observation("Hears a Growl Behind Right Door")

  // Set up transitions
  val randomTigerNoise = Map(
    agent1 -> Map(growlLeft -> 0.5, growlRight -> 0.5),
    agent2 -> Map(growlLeft -> 0.5, growlRight -> 0.5))
  val tigerProbablyLeft = Map(
    agent1 -> Map(growlLeft -> 0.85, growlRight -> 0.15),
    agent2 -> Map(growlLeft -> 0.85, growlRight -> 0.15))

  val tigerProbablyRight = Map(
    agent1 -> Map(growlLeft -> 0.15, growlRight -> 0.85),
    agent2 -> Map(growlLeft -> 0.15, growlRight -> 0.85))

  val tigerLeftTransition = new Transition(-2.0)
  val tigerRightTransition = new Transition(-2.0)
  val singleAgentDeath = new Transition(-101.0)
  val doubleAgentDeath = new Transition(-50.0)
  val singleAgentWin = new Transition(9.0)
  val doubleAgentWin = new Transition(20.0)
  val oneAgentWinsOtherDies = new Transition(-100.0)

  // Set up states
  val tigerLeft = new State("Tiger behind left door", Map(
    Map(agent1 -> listen, agent2 -> listen) -> tigerLeftTransition,
    Map(agent1 -> doorLeft, agent2 -> listen) -> singleAgentDeath,
    Map(agent1 -> listen, agent2 -> doorLeft) -> singleAgentDeath,
    Map(agent1 -> doorLeft, agent2 -> doorLeft) -> doubleAgentDeath,
    Map(agent1 -> doorRight, agent2 -> listen) -> singleAgentWin,
    Map(agent1 -> listen, agent2 -> doorRight) -> singleAgentWin,
    Map(agent1 -> doorRight, agent2 -> doorRight) -> doubleAgentWin,
    Map(agent1 -> doorLeft, agent2 -> doorRight) -> oneAgentWinsOtherDies,
    Map(agent1 -> doorRight, agent2 -> doorLeft) -> oneAgentWinsOtherDies))

  val tigerRight = new State("Tiger behind right door", Map(
    Map(agent1 -> listen, agent2 -> listen) -> tigerRightTransition,
    Map(agent1 -> doorLeft, agent2 -> listen) -> singleAgentWin,
    Map(agent1 -> listen, agent2 -> doorLeft) -> singleAgentWin,
    Map(agent1 -> doorLeft, agent2 -> doorLeft) -> doubleAgentWin,
    Map(agent1 -> doorRight, agent2 -> listen) -> singleAgentDeath,
    Map(agent1 -> listen, agent2 -> doorRight) -> singleAgentDeath,
    Map(agent1 -> doorRight, agent2 -> doorRight) -> doubleAgentDeath,
    Map(agent1 -> doorLeft, agent2 -> doorRight) -> oneAgentWinsOtherDies,
    Map(agent1 -> doorRight, agent2 -> doorLeft) -> oneAgentWinsOtherDies))

  // Set up transitions
  tigerLeftTransition.setNextStates(
    Map(
        new StateObservation(tigerLeft, tigerProbablyLeft) -> 1.0,
        new StateObservation(tigerRight, tigerProbablyRight) -> 0.0)
        )
  tigerRightTransition.setNextStates(
    Map(
        new StateObservation(tigerLeft, tigerProbablyLeft) -> 0.0,
        new StateObservation(tigerRight, tigerProbablyRight) -> 1.0)
        )

  val randomSwitch = Map(
      new StateObservation(tigerLeft, randomTigerNoise) -> 0.5,
      new StateObservation(tigerRight, randomTigerNoise) -> 0.5)

  singleAgentWin.setNextStates(randomSwitch)
  doubleAgentWin.setNextStates(randomSwitch)
  singleAgentDeath.setNextStates(randomSwitch)
  doubleAgentDeath.setNextStates(randomSwitch)
  oneAgentWinsOtherDies.setNextStates(randomSwitch)

  def getStates(): Set[State] = {
    return Set(tigerLeft, tigerRight)
  }

  def getAgents(): Set[Agent] = {
    return Set(agent1, agent2)
  }

  def getActions(): Set[Action] = {
    return Set(listen, doorLeft, doorRight)
  }

  def getStartingState() : State = {
    if (Math.random() < 0.5) tigerLeft else tigerRight
  }
}