package edu.unomaha.decpomdp.maastar.game

class State (
    desc : String = "Default",
    jointActionTransitions : Map[Map[Agent, Action], Transition] = null) {
  def getJointActionTransition(jointAction: Map[Agent, Action]) : Transition = {
    return jointActionTransitions(jointAction)
  }
}