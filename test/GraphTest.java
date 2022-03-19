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
 */
public class GraphTest {
    private SimpleGraph graph;
    private SimpleGraph graph2;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;
    private SimpleEdge edgeBD;
    private SimpleEdge edgeCD;
    private SimpleEdge edgeAE;
    private SimpleEdge edgeAD;
    private SimpleEdge edgeBE;
    private SimpleEdge edgeCE;
    private SimpleEdge edgeDE;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
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

    private void createSimpleGraph2() {
        this.graph2 = new SimpleGraph();

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");
        this.d = new SimpleVertex("D");
        this.e = new SimpleVertex("E");

        this.graph2.addVertex(this.a);
        this.graph2.addVertex(this.b);
        this.graph2.addVertex(this.c);
        this.graph2.addVertex(this.d);
        this.graph2.addVertex(this.e);

        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(2, this.b, this.c);
        this.edgeCA = new SimpleEdge(6, this.c, this.a);
        this.edgeAC = new SimpleEdge(8, this.a, this.c);

        this.edgeAD = new SimpleEdge(3, this.a, this.d);
        this.edgeBD = new SimpleEdge(8, this.b, this.d);
        this.edgeCD = new SimpleEdge(12, this.c, this.d);
        this.edgeAE = new SimpleEdge(8, this.a, this.e);
        this.edgeBE = new SimpleEdge(7, this.b, this.e);
        this.edgeCE = new SimpleEdge(9, this.c, this.e);
        this.edgeDE = new SimpleEdge(8, this.d, this.e);

        this.graph2.addEdge(this.a, this.edgeAB);
        this.graph2.addEdge(this.a, this.edgeAC);
        this.graph2.addEdge(this.a, this.edgeAD);
        this.graph2.addEdge(this.a, this.edgeAE);
        this.graph2.addEdge(this.b, this.edgeBE);
        this.graph2.addEdge(this.b, this.edgeBC);
        this.graph2.addEdge(this.b, this.edgeBD);
        this.graph2.addEdge(this.c, this.edgeCA);
        this.graph2.addEdge(this.d, this.edgeDE);

    }

    @Test
    public void testGetVertices() {
        this.createSimpleGraph2();
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
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

    @Test
    public void testGetVertices2() {
        this.createSimpleGraph2();
        assertEquals(this.graph2.getVertices().size(), 5);
        assertTrue(this.graph2.getVertices().contains(this.a));
        assertTrue(this.graph2.getVertices().contains(this.b));
        assertTrue(this.graph2.getVertices().contains(this.c));
        assertTrue(this.graph2.getVertices().contains(this.d));
        assertTrue(this.graph2.getVertices().contains(this.e));
    }

    @Test
    public void testGetEdgeSource2() {
        this.createSimpleGraph2();
        assertEquals(this.graph2.getEdgeSource(edgeAB), this.a);
        assertEquals(this.graph2.getEdgeSource(edgeBC), this.b);
        assertEquals(this.graph2.getEdgeSource(edgeAC), this.a);
        assertEquals(this.graph2.getEdgeSource(edgeBC), this.b);

        assertEquals(this.graph2.getEdgeSource(edgeAD), this.a);
        assertEquals(this.graph2.getEdgeSource(edgeBD), this.b);
        assertEquals(this.graph2.getEdgeSource(edgeCD), this.c);
        assertEquals(this.graph2.getEdgeSource(edgeAE), this.a);
        assertEquals(this.graph2.getEdgeSource(edgeBE), this.b);
        assertEquals(this.graph2.getEdgeSource(edgeCE), this.c);
        assertEquals(this.graph2.getEdgeSource(edgeDE), this.d);
    }

    @Test
    public void testGetEdgeTarget2() {
        this.createSimpleGraph2();
        assertEquals(this.graph2.getEdgeTarget(edgeAB), this.b);
        assertEquals(this.graph2.getEdgeTarget(edgeBC), this.c);
        assertEquals(this.graph2.getEdgeTarget(edgeAC), this.c);

        assertEquals(this.graph2.getEdgeTarget(edgeAD), this.d);
        assertEquals(this.graph2.getEdgeTarget(edgeBD), this.d);
        assertEquals(this.graph2.getEdgeTarget(edgeCD), this.d);
        assertEquals(this.graph2.getEdgeTarget(edgeAE), this.e);
        assertEquals(this.graph2.getEdgeTarget(edgeBE), this.e);
        assertEquals(this.graph2.getEdgeTarget(edgeCE), this.e);
        assertEquals(this.graph2.getEdgeTarget(edgeDE), this.e);
    }

    @Test
    public void testGetOutgoingEdges2() {
        this.createSimpleGraph2();
        HashSet<SimpleEdge> setA = new HashSet<>();
        setA.add(this.edgeAB);
        setA.add(this.edgeAC);
        setA.add(this.edgeAD);
        setA.add(this.edgeAE);
        HashSet<SimpleEdge> setB = new HashSet<>();
        setB.add(this.edgeBC);
        setB.add(this.edgeBE);
        setB.add(this.edgeBD);
        HashSet<SimpleEdge> setC = new HashSet<>();
        setC.add(this.edgeCA);
        HashSet<SimpleEdge> setD = new HashSet<>();
        HashSet<SimpleEdge> setE = new HashSet<>();
        setD.add(this.edgeDE);
        assertEquals(this.graph2.getOutgoingEdges(this.a), setA);
        assertEquals(this.graph2.getOutgoingEdges(this.b), setB);
        assertEquals(this.graph2.getOutgoingEdges(this.c), setC);
        assertEquals(this.graph2.getOutgoingEdges(this.d), setD);
        assertEquals(this.graph2.getOutgoingEdges(this.e), setE);
    }
}
