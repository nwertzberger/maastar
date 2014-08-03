package maastar.agent

import maastar.game.{Action, Observation}

class DecPomdpAgent(desc: String) extends Agent {

    def chooseAction(): Action = {
        null
    }

    def observe(observations: Set[Observation]): Unit = {
    }

    override def toString(): String = {
        "\"" + desc + "\""
    }
}