package maastar.game

class Action(desc: String = "Default") {
    val description = desc

    override def toString(): String = {
        "\"" + desc + "\""
    }

    def canEqual(other: Any): Boolean = other.isInstanceOf[Action]

}