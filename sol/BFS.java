package sol;

import src.City;
import src.IBFS;
import src.IGraph;
import src.Transport;

import java.util.*;

public class BFS<V, E> implements IBFS<V, E> {

    private HashMap<V, E> hashMap;

    public BFS(){
        this.hashMap = new HashMap<>();
    }

    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        V currentNode = end;
        LinkedList<E> queue = new LinkedList<>();
        HashSet<E> edgesVisited = new HashSet<>();
        LinkedList<E> transports = new LinkedList<>();
        while(!queue.isEmpty()){
            E currentPath = queue.remove();
            if(edgesVisited.contains(currentPath)){
                continue;
            }
            edgesVisited.add(currentPath);
            if(!this.hashMap.containsKey(graph.getEdgeTarget(currentPath))){
                this.hashMap.put(graph.getEdgeTarget(currentPath), currentPath);
            }
        }
        while(currentNode != start){
            E node = this.hashMap.get(currentNode);
            transports.addFirst(node);
            currentNode = graph.getEdgeSource(node);
        }
        return transports;
    }


    public E getCityByName(String name){
        return this.hashMap.get(name);
    }
}
