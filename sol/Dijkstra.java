package sol;

import src.City;
import src.IDijkstra;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    private HashMap<City, Transport> hashMap;
   // private HashMap<City, use getEdgeWeight method that you pass into Dijkstra> hashMapWeight;
    // use this for backtracking, when you go from end to the start (you can go the whole way back using graph)


    public Dijkstra(){
        this.hashMap = new HashMap<>();
    }


    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!
        return null;
    }

    // TODO: feel free to add your own methods here!
}
