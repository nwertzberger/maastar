package edu.unomaha.decpomdp.maastar.game

class State (desc : String, jointActionTransitions : Map[Map[Agent, Action], Transition]) {
  def getJointActionTransition(jointAction: Map[Agent, Action]) : Transition = {
    return jointActionTransitions(jointAction)
  }
}