package test;

import org.junit.Test;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    @Test
    public void testGetVertices() {
        this.createSimpleGraph();
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
        //do we need edge cases
    }

    @Test
    public void testGetEdgeSource() {
        this.createSimpleGraph();
        assertEquals(this.graph.getEdgeSource(edgeAB), this.a);
        assertEquals(this.graph.getEdgeSource(edgeBC), this.b);
        assertEquals(this.graph.getEdgeSource(edgeAC), this.a);
    }

    @Test
    public void testGetEdgeTarget() {
        this.createSimpleGraph();
        assertEquals(this.graph.getEdgeTarget(edgeAB), this.b);
        assertEquals(this.graph.getEdgeTarget(edgeBC), this.c);
        assertEquals(this.graph.getEdgeTarget(edgeAC), this.c);
    }

    @Test
    public void testGetOutgoingEdges() {
        this.createSimpleGraph();
        HashSet<SimpleEdge> setA = new HashSet<>();
        setA.add(this.edgeAB);
        setA.add(this.edgeAC);
        HashSet<SimpleEdge> setB = new HashSet<>();
        setB.add(this.edgeBC);
        HashSet<SimpleEdge> setC = new HashSet<>();
        setC.add(this.edgeCA);
        assertEquals(this.graph.getOutgoingEdges(this.a), setA);
        assertEquals(this.graph.getOutgoingEdges(this.b), setB);
        assertEquals(this.graph.getOutgoingEdges(this.c), setC);
    }
    // TODO: write more tests + make sure you test all the cases in your testing plan!
}
