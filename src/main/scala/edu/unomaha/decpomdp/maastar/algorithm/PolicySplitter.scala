package edu.unomaha.decpomdp.maastar

import edu.unomaha.decpomdp.maastar.game.State


class PolicySplitter {
  def splitOnDepth(children: Set[Policy], maxLayers: Int) : (Set[Policy], Set[Policy]) = {
    return (null, null)
  }
  
  def filter(policies : Set[Policy], belief : Map[State, Float]) : PolicyFilter = {
     return null
  }
}