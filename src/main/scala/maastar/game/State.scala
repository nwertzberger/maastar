package maastar.game

import maastar.agent.Agent

class State(
    desc: String = "Default",
    jointActionTransitions: Map[Map[Agent, Action], Transition] = null
) {

    def getJointActionTransition(jointAction: Map[Agent, Action]): Transition = {
        return jointActionTransitions(jointAction)
    }

    override def toString(): String = {
        "\"" + desc + "\""
    }
}