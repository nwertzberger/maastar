package edu.unomaha.decpomdp.maastar.game

import edu.unomaha.decpomdp.maastar.agent.Agent

class State (
    desc : String = "Default",
    jointActionTransitions : Map[Map[Agent, Action], Transition] = null) {
  def getJointActionTransition(jointAction: Map[Agent, Action]) : Transition = {
    return jointActionTransitions(jointAction)
  }
}