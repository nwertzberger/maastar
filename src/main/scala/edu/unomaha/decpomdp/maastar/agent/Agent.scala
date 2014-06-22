package edu.unomaha.decpomdp.maastar.agent

import edu.unomaha.decpomdp.maastar.game.Action
import edu.unomaha.decpomdp.maastar.game.Observation

trait Agent {
  def observe(observations: Set[Observation]) 
  def chooseAction() : Action
}