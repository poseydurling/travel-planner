package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {

    private HashMap<String, City> hashMap;

    public TravelGraph(){
        this.hashMap = new HashMap<>();
    }

    @Override
    public void addVertex(City vertex) {
        this.hashMap.put(vertex.toString(), vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    @Override
    public Set<City> getVertices() {
        HashSet<City> hashSet = new HashSet<>(this.hashMap.values());
        return hashSet;
    }

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    public City getCityByName(String name){
        return this.hashMap.get(name);
    }
}