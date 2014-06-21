package edu.unomaha.decpomdp.maastar.game

class StateObservation (_state:State, _observations : Map[Agent, Map[Observation, Double]]) {
  
  def state() = _state
  def observations() = _observations


}