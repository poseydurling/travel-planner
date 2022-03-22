package test;

import org.junit.Test;
import sol.BFS;
import sol.Dijkstra;
import src.IDijkstra;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleGraph graph1;
    private SimpleGraph graph2;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex NYC;
    private SimpleVertex BOS;
    private SimpleVertex PROV;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    /**
     * Creates a SimpleGraph that replicates the graph in the project handout with the corresponding weights.
     *
     */
    public void makeGraph1(){
        this.graph1 = new SimpleGraph();

        this.NYC = new SimpleVertex("NYC");
        this.BOS = new SimpleVertex("Boston");
        this.PROV = new SimpleVertex("Providence");

        this.graph1.addVertex(this.NYC);
        this.graph1.addVertex(this.BOS);
        this.graph1.addVertex(this.PROV);

        this.graph1.addEdge(this.NYC, new SimpleEdge(50, this.NYC, this.BOS));
        this.graph1.addEdge(this.NYC, new SimpleEdge(250, this.NYC, this.PROV));
        this.graph1.addEdge(this.BOS, new SimpleEdge(50, this.BOS, this.NYC));
        this.graph1.addEdge(this.BOS, new SimpleEdge(150, this.BOS, this.PROV));
        this.graph1.addEdge(this.PROV, new SimpleEdge(150, this.PROV, this.BOS));
    }

    /**
     * Creates a SimpleGraph that checks for infinite loops.
     *
     */
    public void makeGraph2(){
        this.graph2 = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph2.addVertex(this.a);
        this.graph2.addVertex(this.b);
        this.graph2.addVertex(this.c);
        this.graph2.addVertex(this.d);
        this.graph2.addVertex(this.e);

        this.graph2.addEdge(this.a, new SimpleEdge(20, this.a, this.b));
        this.graph2.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph2.addEdge(this.c, new SimpleEdge(1, this.c, this.d));
        this.graph2.addEdge(this.d, new SimpleEdge(10, this.d, this.b));
        this.graph2.addEdge(this.d, new SimpleEdge(100, this.c, this.e));
        this.graph2.addEdge(this.d, new SimpleEdge(1, this.e, this.b));
    }

    /**
     * A basic test given to us in the stencil code.
     *
     */
    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    /**
     * TESTS FOR GRAPH1: classic cases with a size of one, going in a circle (testing for infinite loops), edge cases
     * (going from NYC to NYC), when there are two possible paths, etc. Tests using both size and objects.
     *
     */
    @Test
    public void testNYCPROV(){
        this.makeGraph1();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph1, this.NYC, this.PROV, edgeWeightCalculation);
        assertEquals(200, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    @Test
    public void testPROVNYC(){
        this.makeGraph1();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph1, this.PROV, this.NYC, edgeWeightCalculation);
        assertEquals(200, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    @Test
    public void NYCNYC(){
        this.makeGraph1();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph1, this.NYC, this.NYC, edgeWeightCalculation);
        assertEquals(0, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(0, path.size());
    }

    /**
     * TESTS FOR GRAPH2: classic cases with a size of one, going in a circle (testing for infinite loops), edge cases
     * (going from a to a), when there are two possible paths, etc. Tests using both size and objects.
     *
     */
    @Test
    public void testAB(){
        this.makeGraph2();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph2, this.a, this.b, edgeWeightCalculation);
        assertEquals(20, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(1, path.size());
    }

    @Test
    public void testAA(){
        this.makeGraph2();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph2, this.a, this.a, edgeWeightCalculation);
        assertEquals(0, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(0, path.size());
    }

    @Test
    public void testCB(){
        this.makeGraph2();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph2, this.c, this.b, edgeWeightCalculation);
        assertEquals(11, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    //NOT WORKING
    @Test
    public void testDC(){
        this.makeGraph2();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph2, this.d, this.c, edgeWeightCalculation);
        assertEquals(11, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    @Test
    public void testEA(){
        this.makeGraph2();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph2, this.e, this.a, edgeWeightCalculation);
        assertEquals(0, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(0, path.size());
    }
}
