package maastar.agent

import maastar.game.Action
import maastar.game.Observation

trait Agent {
  def observe(observations: Set[Observation]) 
  def chooseAction() : Action
}