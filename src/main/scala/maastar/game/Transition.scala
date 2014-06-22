package maastar.game

class Transition (_reward : Double = 0) {
  var _nextStates : Map[StateObservation,Double] = null

  def setNextStates(nextStates : Map[StateObservation,Double]) = {
    this._nextStates = nextStates
  }
  
  def nextStates() = _nextStates
  
  def reward() : Double = _reward

  override def toString() : String = 
    "{ reward: " + _reward + ", nextStates: " + _nextStates + "}"

}