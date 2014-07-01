package maastar.game

class Action (desc : String = "Default") {
  val description = desc

  override def toString() : String = {
    "new Action(\"" + desc + "\")"
  }

}