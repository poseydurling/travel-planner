package test;

import org.junit.Test;
import sol.BFS;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleVertex NYC;
    private SimpleVertex BOS;
    private SimpleVertex PROV;
    private SimpleGraph graph;
    private SimpleGraph graph1;
    private SimpleGraph graph2;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    /**
     * Creates a SimpleGraph that replicates the graph in the project handout.
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

        this.graph1.addEdge(this.NYC, new SimpleEdge(1, this.NYC, this.BOS));
        this.graph1.addEdge(this.NYC, new SimpleEdge(1, this.NYC, this.PROV));
        this.graph1.addEdge(this.BOS, new SimpleEdge(1, this.BOS, this.NYC));
        this.graph1.addEdge(this.BOS, new SimpleEdge(1, this.BOS, this.PROV));
        this.graph1.addEdge(this.PROV, new SimpleEdge(1, this.PROV, this.BOS));
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

        this.graph2.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph2.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph2.addEdge(this.c, new SimpleEdge(1, this.c, this.d));
        this.graph2.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph2.addEdge(this.d, new SimpleEdge(1, this.c, this.e));
        this.graph2.addEdge(this.d, new SimpleEdge(1, this.e, this.b));

        // do we need to do other weights?
    }

    /**
     * A basic test given to us in the stencil code.
     *
     */
    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    /**
     * A second test given to us in the stencil code.
     *
     */
    @Test
    public void testBFS(){
        this.makeSimpleGraph();

        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.b);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 1.0, DELTA);
        assertEquals(path.size(), 1);
        //loadCVS.transportation (or cities) - test objects
    }

    /**
     * Tests graph1: classic cases with a size of one, going in a circle (testing for infinite loops), edge cases
     * (going from NYC to NYC), etc. Tests using both size and objects.
     *
     */
    @Test
    public void testGraph1(){
        this.makeGraph1();

        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph1, this.NYC, this.PROV);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 1.0, DELTA);
        assertEquals(path.size(), 1);

        BFS<SimpleVertex, SimpleEdge> bfs2 = new BFS<>();
        List<SimpleEdge> path2 = bfs2.getPath(this.graph1, this.PROV, this.NYC);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path2), 2.0, DELTA);
        assertEquals(path2.size(), 2);
        assertEquals(bfs2.getPath(this.graph1, this.PROV, this.NYC), path2);

        BFS<SimpleVertex, SimpleEdge> bfs3 = new BFS<>();
        List<SimpleEdge> path3 = bfs3.getPath(this.graph1, this.NYC, this.NYC);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path3), 0.0, DELTA);
        assertEquals(path3.size(), 0);
        assertEquals(bfs3.getPath(this.graph1, this.NYC, this.NYC), path3);
    }

    /**
     * Tests graph2: classic cases with a size of one, going in a circle (testing for infinite loops), edge cases
     * (going from NYC to NYC), when there are two possible paths, etc. Tests using both size and objects.
     *
     */
    @Test
    public void testGraph2(){
        this.makeGraph2();

        /*BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph2, this.a, this.b);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 1.0, DELTA);
        assertEquals(path.size(), 1);
        assertEquals(bfs.getPath(this.graph2, this.a, this.b), path);

        BFS<SimpleVertex, SimpleEdge> bfs2 = new BFS<>();
        List<SimpleEdge> path2 = bfs2.getPath(this.graph2, this.c, this.b);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path2), 2.0, DELTA);
        assertEquals(path2.size(), 2);
        assertEquals(bfs2.getPath(this.graph2, this.c, this.b), path2);*/

        BFS<SimpleVertex, SimpleEdge> bfs3 = new BFS<>();
        List<SimpleEdge> path3 = bfs3.getPath(this.graph2, this.d, this.c);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path3), 2.0, DELTA);
        assertEquals(path3.size(), 2);

        //test no possible paths

        /*BFS<SimpleVertex, SimpleEdge> bfs4 = new BFS<>();
        List<SimpleEdge> path4 = bfs4.getPath(this.graph2, this.c, this.b);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path4), 2.0, DELTA);
        assertEquals(path4.size(), 2);

        //do we need to test getShortestPath

        BFS<SimpleVertex, SimpleEdge> bfs5 = new BFS<>();
        List<SimpleEdge> path6 = bfs5.getPath(this.graph2, this.a, this.a);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path6), 0.0, DELTA);
        assertEquals(path6.size(), 0);
        assertEquals(bfs5.getPath(this.graph2, this.a, this.a), path6);*/
    }
}
