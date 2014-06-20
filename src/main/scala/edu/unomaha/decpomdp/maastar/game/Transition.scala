package edu.unomaha.decpomdp.maastar.game

class Transition (reward : Double = 0, observations : Map[Agent, Map[Observation, Double]]) {
  var nextStates : Map[State, Double] = null
  def setNextStates(nextStates : Map[State,Double]) = {
    this.nextStates = nextStates
  }
  
  override def toString() : String = "{ reward: " + reward +
    ", observations: " + observations.toString() +
    ", nextStates: " + nextStates + "}"
}