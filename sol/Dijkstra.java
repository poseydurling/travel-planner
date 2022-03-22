package sol;

import src.City;
import src.IDijkstra;
import src.IGraph;
import src.Transport;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    /**
     * The Dijkstra class implements the IDijstrka interface to determine which route is the best to take based on price or time.
     * our getShortestPath method executes the algorithm where we decorate each node with distnace from the start and the previous node in the path.  and our traceBack method returns the finished List after it has been traced back to get the
     * path form the source destination.
     * @param <V>
     * @param <E>
     */
    private HashMap<V, Double> distanceFromStart;
    private HashMap<V, E> prevVertex;

    /**
     * The Dijkstra constructor initalises our two instance variables, a hashmap called distanceFromStart that contians a key Vertex and a double value
     * and another hashMap called prevVertex which contains a Vertex key and an Edge value
     */
    public Dijkstra() {
        this.distanceFromStart = new HashMap<>();
        this.prevVertex = new HashMap<>();
    }

    /**
     * the getShortestPath method takes in a graph, a vertex called source, a vertex called destination,
     * and a function called edgeweight. First, we use a for each loop to look through the vertices in the graph, inside we fill in the maps by intialisizing distnace
     * decorations and previous pointers to null. Then we create our priority queue to store all teh vertices so that if you remove a minimum element it gives you the smallest vertice. WE
     * also make a comparator to compare keys so that hte priority queue knows how to order things.
     * While the priority queue is not empty, we pull the first city from the priority queue.  Then we use
     * a for loop to tst every outgoing edge form currentCity. If the distance from the start fo the current city
     * and the edge weight is less than the distance from the start of the target city, we change the distance associate with target city
     * to distance from the start of the current city and its edge weight.We then call our
     * traceback helper method to return the sorted list.
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight
     * @return
     */
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        for (V vertex : graph.getVertices()) {
            this.distanceFromStart.put(vertex, 1000000.0);
            this.prevVertex.put(vertex, null);
            this.distanceFromStart.put(source, 0.0);
        }
        Comparator<V> priorityQueueComparator = (V city1, V city2) ->
                this.distanceFromStart.get(city1).compareTo(this.distanceFromStart.get(city2));
        PriorityQueue<V> priorityQueue = new PriorityQueue<>(priorityQueueComparator);
        priorityQueue.addAll(graph.getVertices());
        while (!priorityQueue.isEmpty()) {
            V currentCity = priorityQueue.poll();
            for (E outgoingEdge : graph.getOutgoingEdges(currentCity)) {
                V targetCity = graph.getEdgeTarget(outgoingEdge);
                if (this.distanceFromStart.get(currentCity) + edgeWeight.apply(outgoingEdge) <
                        this.distanceFromStart.get(targetCity)) {
                    this.distanceFromStart.replace(targetCity, this.distanceFromStart.get(currentCity)
                            + edgeWeight.apply(outgoingEdge));
                    this.prevVertex.replace(targetCity, outgoingEdge);
                    priorityQueue.add(targetCity);
                }
            }
        }
        return traceBack(graph, destination);
    }

    /**
     * our traceBack helper method takes in a graph and a Vertex called destination. Here we create a new arraylist
     * set the current city to the destination and the current edge to the edge of the currentcity. while the current edge is not null
     * we add the edge to our new list and reset the current city and edge. once we have looped through the whole hashmap we return the list.
     * @param graph
     * @param destination
     * @return
     */
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
