package maastar.algorithm.policyiteration

import maastar.agent.Agent
import maastar.game.{Action, State, TigerGame}

/**
  */
class MdpPolicyBuilder {

    def buildMaximalPolicy(
        game: TigerGame,
        utility: Map[State, Double] = Map()
    ): Map[State, Map[Agent, Action]] = {

        val jointActions = getAllJointActions(game.getAgents(), game.getActions())
        game.getStates().map { state =>
            state -> jointActions.foldLeft[Map[Agent, Action]](null) { (curr, next) =>
                if (curr == null || getExpectedUtility(state, curr, utility) < getExpectedUtility(state, next, utility)) {
                    next
                } else {
                    curr
                }
            }
        }.toMap
    }

    def getExpectedUtility(state: State, currAction: Map[Agent, Action], utility: Map[State, Double]): Double = {
        val currTran = state.getJointActionTransition(currAction)
        val currUtil = currTran
            .nextStates()
            .map { case (state, p) =>
            p * utility.getOrElse(state.state(), 0.0)
        }
            .foldLeft(0.0) { (acc, curr) =>
            acc + curr
        }
        return state.getJointActionTransition(currAction).reward + currUtil
    }

    def getAllJointActions(
        agents: Set[Agent],
        actions: Set[Action]
    ): List[Map[Agent, Action]] = {

        val actionCombos = getAllActions(actions, agents.size)

        actionCombos.map { combo =>
            (agents zip combo).toMap
        }.toList
    }

    def getAllActions(
        actions: Set[Action],
        width: Int
    ): Iterator[List[Action]] = {
        if (width == 1) {
            actions.map { act => List(act)}.toIterator
        }
        else {
            var newList: List[List[Action]] = List()
            for (act <- actions) {
                newList = newList ++ getAllActions(actions, width - 1).map { actionList =>
                    List(act) ++ actionList
                }
            }
            newList.toIterator
        }
    }

}
