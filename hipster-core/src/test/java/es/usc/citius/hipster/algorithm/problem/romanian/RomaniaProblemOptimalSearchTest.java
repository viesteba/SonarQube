package es.usc.citius.hipster.algorithm.problem.romanian;

import es.usc.citius.hipster.algorithm.Algorithm;
import es.usc.citius.hipster.model.CostNode;
import es.usc.citius.hipster.model.Node;
import es.usc.citius.hipster.util.examples.RomanianProblem;
import es.usc.citius.hipster.graph.HipsterGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Generic test suite for algorithms that order the OPEN queue in a similar way that A* (using the score of a node).
 * The test cases implemented in this class are the following:
 * <ul>
 * <li>Cost of the nodes expanded by the algorithm.</li>
 * <li>Score of the nodes expanded by the algorithm.</li>
 * <li>Optimal path retrieved.</li>
 * </ul>
 * Different algorithms need to test these situations when performing optimal search (this means: not anytime search nor
 * replanning).
 * This test case suite uses the Romania problem searching from Arad to Bucharest.
 *
 * @author Adrián González Sieira <adrian.gonzalez@usc.es>
 * @since 0.1.0
 */
public abstract class RomaniaProblemOptimalSearchTest {

    protected final HipsterGraph<RomanianProblem.City, Double> graph = RomanianProblem.graph();
    protected Collection<Node<Void, RomanianProblem.City, ?>> expandedNodesTested;
    protected List<? extends Node<Void, RomanianProblem.City, ?>> optimalPathIterator;
    protected List<? extends Node<Void, RomanianProblem.City, ?>> optimalPathSearchMethod;
    protected List<RomanianProblem.City> optimalPath;
    protected final HashMap<RomanianProblem.City, Double> costsFromArad;
    protected static final RomanianProblem.City GOAL = RomanianProblem.City.BUCHAREST;

    public RomaniaProblemOptimalSearchTest(){
        costsFromArad = new HashMap<RomanianProblem.City, Double>();
        costsFromArad.put(RomanianProblem.City.ARAD, 0d);
        costsFromArad.put(RomanianProblem.City.ZERIND, 75d);
        costsFromArad.put(RomanianProblem.City.TIMISOARA, 118d);
        costsFromArad.put(RomanianProblem.City.SIBIU, 140d);
        costsFromArad.put(RomanianProblem.City.ORADEA, 146d);
        costsFromArad.put(RomanianProblem.City.FAGARAS, 239d);
        costsFromArad.put(RomanianProblem.City.RIMNICU_VILCEA, 220d);
        costsFromArad.put(RomanianProblem.City.CRAIOVA, 366d);
        costsFromArad.put(RomanianProblem.City.PITESTI, 317d);
        costsFromArad.put(RomanianProblem.City.BUCHAREST, 418d);
        costsFromArad.put(RomanianProblem.City.LUGOJ, 229d);
        costsFromArad.put(RomanianProblem.City.MEHADIA, 299d);
        costsFromArad.put(RomanianProblem.City.DROBETA, 374d);
        costsFromArad.put(RomanianProblem.City.GIURGIU, 508d);
        costsFromArad.put(RomanianProblem.City.URZICENI, 503d);
        costsFromArad.put(RomanianProblem.City.HIRSOVA, 601d);
        costsFromArad.put(RomanianProblem.City.EFORIE, 687d);
        costsFromArad.put(RomanianProblem.City.VASLUI, 645d);
        costsFromArad.put(RomanianProblem.City.IASI, 737d);
        costsFromArad.put(RomanianProblem.City.NEAMT, 824d);

        optimalPath =
                Arrays.asList(
                        RomanianProblem.City.ARAD,
                        RomanianProblem.City.SIBIU,
                        RomanianProblem.City.RIMNICU_VILCEA,
                        RomanianProblem.City.PITESTI,
                        RomanianProblem.City.BUCHAREST
                );
    }

    /**
     * Check the returned path of the algorithm to be the optimal for the problem definition.
     */
    @Test
    public void optimalPathFromAradToBucharest() {
        //check elements returned by the search algorithm
        assertEquals("Solutions have not the same size", optimalPathIterator.size(), optimalPath.size());
        for(int i = 0; i < optimalPath.size(); i++){
            //check if current element of the path is equals to the
            assertEquals(
                    "Failed checking element " + i + " of the path. Expected: " +
                            optimalPath.get(i) + ", found: " + optimalPathIterator.get(i).state(),
                    optimalPath.get(i),
                    optimalPathIterator.get(i).state()
            );
        }
    }

    /**
     * Check the returned path of the algorithm to be the optimal for the problem definition.
     */
    @Test
    public void optimalPathFromAradToBucharestSearchMethod() {
        //check elements returned by the search algorithm
        assertEquals("Solutions have not the same size", optimalPathSearchMethod.size(), optimalPath.size());
        for(int i = 0; i < optimalPath.size(); i++){
            //check if current element of the path is equals to the
            assertEquals(
                    "Failed checking element " + i + " of the path. Expected: " +
                            optimalPath.get(i) + ", found: " + optimalPathSearchMethod.get(i).state(),
                    optimalPath.get(i),
                    optimalPathSearchMethod.get(i).state()
            );
        }
    }

    /**
     * Check the costs of the elements expanded by the algorithm finding the optimal path.
     */
    @Test
    public void costsFromAradToBucharest() {
        for(Node<Void, RomanianProblem.City, ?> node : expandedNodesTested){
            CostNode<Void, RomanianProblem.City, Double, ?> costNode
                    = (CostNode<Void, RomanianProblem.City, Double, ?>) node;
            //compare returned cost with expected
            assertEquals(
                    "Failed checking cost of " + node.state().toString() + ". Expected: " +
                            costsFromArad.get(node.state()) + ", found: " + costNode.getCost(),
                    costNode.getCost(),
                    costsFromArad.get(node.state())
            );
        }
    }

    /**
     * Definition of abstract method to use the same test suite with different algorithms.
     * This method fills the list of explored nodes and obtains the optimal path, wihch
     * are stored in "expandedNodesTested" and "optmalPath" variables.
     */
    @Before
    public void doSearch(){
        Algorithm<Void, RomanianProblem.City, ? extends Node<Void, RomanianProblem.City, ?>> algorithm = createAlgorithm();
        //execute iterative search
        this.optimalPathIterator = iterativeSearch(algorithm.iterator());
        this.optimalPathSearchMethod = algorithm.search(RomanianProblem.City.BUCHAREST).getGoalNode().path();
    };

    /**
     * Method to execute the iterative search from a search iterator.
     *
     * @param iterator instance of {@link Iterator<? extends Node<Void, RomanianProblem.City, ?>>} to search
     * @return solution list obtained by sarching step-by-step
     */
    public abstract List<? extends Node<Void, RomanianProblem.City, ?>> iterativeSearch(Iterator<? extends Node<Void, RomanianProblem.City, ?>> iterator);

    /**
     * Method to initialize the search algorithm.
     *
     * @return instance of {@link Algorithm} to search over the graph
     */
    public abstract Algorithm<Void, RomanianProblem.City, ? extends Node<Void, RomanianProblem.City, ?>> createAlgorithm();

}