package maastar.agent

import maastar.game.{Action, Observation}

trait Agent {
    def observe(observations: Set[Observation])

    def chooseAction(): Action
}