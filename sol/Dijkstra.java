package sol;

import src.City;
import src.IDijkstra;
import src.IGraph;
import src.Transport;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    private HashMap<V, Double> distanceFromStart;
    private HashMap<V, E> prevVertex;
    // private HashMap<City, use getEdgeWeight method that you pass into Dijkstra> hashMapWeight;
    // use this for backtracking, when you go from end to the start (you can go the whole way back using graph)


    public Dijkstra() {
        this.distanceFromStart = new HashMap<>();
        this.prevVertex = new HashMap<>();
    }


    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        for (V vertex : graph.getVertices()) {
            this.distanceFromStart.put(vertex, 1000000.0);
            this.prevVertex.put(vertex, null);
            this.distanceFromStart.put(source, 0.0);
        }
        Comparator<V> priorityQueueComparator = (V city1, V city2) -> this.distanceFromStart.get(city1).compareTo(this.distanceFromStart.get(city2));
        PriorityQueue<V> priorityQueue = new PriorityQueue<>(priorityQueueComparator);
        priorityQueue.addAll(graph.getVertices());
        while (!priorityQueue.isEmpty()) {
            V currentCity = priorityQueue.poll();
            for (E outgoingEdge : graph.getOutgoingEdges(currentCity)) {
                V targetCity = graph.getEdgeTarget(outgoingEdge);
                if (this.distanceFromStart.get(currentCity) + edgeWeight.apply(outgoingEdge) <
                        this.distanceFromStart.get(targetCity)) {
                    distanceFromStart.replace(targetCity, this.distanceFromStart.get(currentCity)
                            + edgeWeight.apply(outgoingEdge));
                    this.prevVertex.put(targetCity, outgoingEdge);
                    priorityQueue.remove(targetCity);
                    priorityQueue.add(targetCity);
                }
            }
        }
        return traceBack(graph, destination);
    }

    public List<E> traceBack(IGraph<V, E> graph, V destination){
        List<E> doneList = new ArrayList<E>();
        V currentCity = destination;
        E currentEdge = this.prevVertex.get(currentCity);
        while(currentEdge != null){
            doneList.add(0, currentEdge);
            currentCity = graph.getEdgeSource(currentEdge);
            currentEdge = this.prevVertex.get(currentCity);
        }
        return doneList;

    }

}
