package maastar.agent

import maastar.game.{Action, Observation}

class DecPomdpAgent(_desc: String) extends Agent {
    val desc = _desc
    def chooseAction(): Action = {
        null
    }

    override def observe(observations : Set[Observation]) = {

    }

    override def toString(): String = {
        "\"" + desc + "\""
    }

    def canEqual(other: Any): Boolean = other.isInstanceOf[DecPomdpAgent]

    override def equals(other: Any): Boolean = other match {
        case that: DecPomdpAgent =>
            (that canEqual this) &&
                desc == that.desc
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(desc)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
}