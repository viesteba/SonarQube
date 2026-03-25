package es.usc.citius.hipster.algorithm.problem.romanian;

import es.usc.citius.hipster.model.HeuristicNode;
import es.usc.citius.hipster.model.Node;
import es.usc.citius.hipster.util.examples.RomanianProblem;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by adrian.gonzalez on 8/06/15.
 */
public abstract class RomaniaProblemOptimalHeuristicSearchTest extends RomaniaProblemOptimalSearchTest {

    protected final HashMap<RomanianProblem.City, Double> scoresFromArad;

    public RomaniaProblemOptimalHeuristicSearchTest(){
        super();
        //obtain score map for expanding nodes to Bucharest
        scoresFromArad = new HashMap<RomanianProblem.City, Double>();
        scoresFromArad.put(RomanianProblem.City.ARAD, 366d);
        scoresFromArad.put(RomanianProblem.City.ZERIND, 449d);
        scoresFromArad.put(RomanianProblem.City.TIMISOARA, 447d);
        scoresFromArad.put(RomanianProblem.City.SIBIU, 393d);
        scoresFromArad.put(RomanianProblem.City.ORADEA, 526d);
        scoresFromArad.put(RomanianProblem.City.FAGARAS, 415d);
        scoresFromArad.put(RomanianProblem.City.RIMNICU_VILCEA, 413d);
        scoresFromArad.put(RomanianProblem.City.CRAIOVA, 526d);
        scoresFromArad.put(RomanianProblem.City.PITESTI, 417d);
        scoresFromArad.put(RomanianProblem.City.BUCHAREST, 418d);
        scoresFromArad.put(RomanianProblem.City.LUGOJ, 473d);
        scoresFromArad.put(RomanianProblem.City.MEHADIA, 540d);
        scoresFromArad.put(RomanianProblem.City.DROBETA, 616d);
    }

    /**
     * Check the scores of the elements expanded by the algorithm.
     */
    @Test
    public void scoresFromAradToBucharest() {
        for(Node<Void, RomanianProblem.City, ?> node : expandedNodesTested){
            HeuristicNode<Void, RomanianProblem.City, Double, ?> heuristicNode =
                    (HeuristicNode<Void, RomanianProblem.City, Double, ?>) node;
            //compare returned score with expected
            assertEquals(
                    "Failed checking score of " + heuristicNode.state().toString(),
                    scoresFromArad.get(heuristicNode.state()), heuristicNode.getScore()
            );
        }
    }

}
