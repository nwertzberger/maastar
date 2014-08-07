package maastar.game

class Observation(_desc: String) {
    val desc = _desc

    override def toString(): String = {
        "new Observation(\"" + desc + "\")"
    }

    def canEqual(other: Any): Boolean = other.isInstanceOf[Observation]

}