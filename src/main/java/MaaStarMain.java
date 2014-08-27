import maastar.agent.Agent;
import maastar.algorithm.maastar.MaaStar;
import maastar.game.Observation;
import maastar.game.State;
import maastar.game.TigerGame;
import maastar.policy.Policy;
import maastar.policy.PolicyNode;
import scala.collection.JavaConversions;
import scala.collection.Set;
import sun.management.resources.agent;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nwertzberger on 8/27/14.
 */
public class MaaStarMain {

    public static void main(String[] args) {
        TigerGame game = new TigerGame();
        Map<State, Double> initialBelief = new HashMap<State, Double>();
        initialBelief.put(game.tigerLeft(), 0.5);
        initialBelief.put(game.tigerRight(), 0.5);

        MaaStar maaStar = new MaaStar(game);
        Policy policy = maaStar.calculatePolicyJava(initialBelief, Integer.valueOf(args[0]));
        for(Agent agent : JavaConversions.mapAsJavaMap(policy.agentPolicies()).keySet()) {
            PolicyNode policyNode = policy.agentPolicies().get(agent).get();
            System.out.println(agent.toString() + " -> " + policyNode.action());
            printPolicy(policyNode, 1);
        }
    }

    private static void printPolicy(PolicyNode policy, int tabs) {
        for (Set<Observation> obs : JavaConversions.mapAsJavaMap(policy.transitions()).keySet()) {
            for (int i=0; i < tabs; i++) System.out.print("  ");
            PolicyNode policyNode = policy.transitions().get(obs.<Observation>toSet()).get();
            System.out.println(obs + " -> " + policyNode.action());
            printPolicy(policyNode, tabs + 1);

        }
    }
}
