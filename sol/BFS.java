package sol;

import src.City;
import src.IBFS;
import src.IGraph;
import src.Transport;

import java.util.*;

public class BFS<V, E> implements IBFS<V, E> {

    /**
     * The BFS class implements a breadth first search algorithm which we use to determine which
     * route is the most direct from one city to another.  We initialise our hashmap in the class constructor
     * and in the getPath method we implement the algorithm and return a linkedList.
     * @param <V>
     * @param <E>
     */
    private HashMap<V, E> hashMap;

    /**
     * The BFS constructor initialises our hashmap instance variable that we will use in our getPath method.
     */
    public BFS(){
        this.hashMap = new HashMap<>();
    }

    /**
     * In our getPath method, we take in an IGraph called graph, the graph object of the graph to be searching through
     * , a vertex representing the starting point and a vertex representing the end point. We use a while hoop to loop through
     * the list of vertices in the graph until there are no more left to loop through.
     * As we iterate through the list, we take the first element from the front of the list and remove it to check if
     * the list is in teh correct order. Then we check to see fi we have visited the current city and create a hashset
     * to maintains a set of all the unique edges visited.  If you have visited the city we ignore it and continue
     * with the edge in the list. Next, we get the vertex that the edge points to and check to see if the node has
     * been visited, as there may be an edge you haven't visited but the node itself may have been seen as
     * you can have two edges leading to the same node. If it has, there exists a shorter path to the node and we
     * track the path by mapping a vertex to the transport, if it hashnt we add to the hashmap
     * if the target vertex hasn't been visited. Then we check to see if the
     * target vertex is the end vertex int eh hashmap, if so we've found a path to the end and we stop looping and return the transports list.
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return
     */
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        V currentNode = end;
        LinkedList<E> queue = new LinkedList<>();
        queue.addAll(graph.getOutgoingEdges(start));
        HashSet<E> edgesVisited = new HashSet<>();
        LinkedList<E> transports = new LinkedList<>();
        while(!queue.isEmpty()){
            E currentPath = queue.poll();
            if(edgesVisited.contains(currentPath)){
                //return new ArrayList<>();
                continue;
            }
            edgesVisited.add(currentPath);
            if(!this.hashMap.containsKey(graph.getEdgeTarget(currentPath))){
                this.hashMap.put(graph.getEdgeTarget(currentPath), currentPath);
            }
            if((graph.getEdgeTarget(currentPath).equals(end))){
                return addTransport(currentNode, start, transports, graph);
                //queue.addAll(graph.getOutgoingEdges(graph.getEdgeTarget(currentPath)));
            }
        }
        return transports;
    }

    public LinkedList<E> addTransport(V currentNode, V start, LinkedList<E> transports, IGraph<V, E> graph){
        while(currentNode != start){
            E node = this.hashMap.get(currentNode);
            transports.addFirst(node);
            currentNode = graph.getEdgeSource(node);
        }
        return transports;
    }

    /**
     * getCity By Name takes in a string name and returns and edge object of that city.
     * @param name
     * @return
     */
    public E getCityByName(String name){
        return this.hashMap.get(name);
    }
}
