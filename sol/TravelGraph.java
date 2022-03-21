package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {

    /**
     * Our TravelGraph class makes the key methods for adding vertices and edges to our graphs
     *
     */
    private HashMap<String, City> hashMap;

    public TravelGraph(){
        this.hashMap = new HashMap<>();
    }

    /**
     * The addVertex method adds a vertex to the hashMap
     *
     */
    @Override
    public void addVertex(City vertex) {
        this.hashMap.put(vertex.toString(), vertex);
    }

    /**
     * The addEdge method adds an edge to the hashMap
     *
     */
    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    /**
     * The getVertices method gets a vertex from the hashMap
     *
     */
    @Override
    public Set<City> getVertices() {
        HashSet<City> hashSet = new HashSet<>(this.hashMap.values());
        return hashSet;
    }

    /**
     * The getEdgeSource method gets the vertex from which the edge is coming from in the hashMap
     *
     */
    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    /**
     * The getEdgeTarget method gets the vertex to which the edge is going to in the hashMap
     *
     */
    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    /**
     * The getOutgoingEdges method gets the edges that are going out from a vertex in the hashMap
     *
     */
    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    /**
     * The getCityByName gets the city object from based on the city's name, which is passed in as a String
     *
     */
    public City getCityByName(String name){
        return this.hashMap.get(name);
    }
}