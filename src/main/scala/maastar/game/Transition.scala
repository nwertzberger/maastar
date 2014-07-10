package maastar.game

class Transition (_reward : Double = 0.0) {
  var _nextStates : Map[StateObservation,Double] = null

  def setNextStates(nextStates : Map[StateObservation,Double]) = {
    this._nextStates = nextStates
  }
  
  def nextStates() = _nextStates
  val reward = _reward
  

  override def toString() : String = 
    "new Transition(" + _reward + ").setNextStates(" + _nextStates + ")"

}