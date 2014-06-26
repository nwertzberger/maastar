package maastar.policy

import maastar.game.{Action, Observation}

class PolicyNode(_action : Action, _transitions : Map[Observation,PolicyNode] = Map(), value : Double = 0.0) {
  def depth() : Int = if (_transitions.size == 0) 0 else _transitions.values.map(p => p.depth()).max
  def action() = _action
  def transitions() = _transitions
}
