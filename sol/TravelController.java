package sol;

import src.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TravelController implements ITravelController<City, Transport> {

    /**
     * Our travelController helps us test our BFS and Dijkstra
     *
     */
    private TravelGraph graph;

    /**
     * We did not need to instantiate anything in our travelController constructor because we initialized our graph
     * in the load method.
     *
     */
    public TravelController() {
    }

    /**
     * Our load method initializes the graph we made. We then create a variable of type Function<Map<String, String>, Void>
     * that will build the nodes in a graph. In our functions to add vertices and edges need explicit return null to
     * account for Void type. The add edge function creates another variable of type Function<Map<String, String>, Void>
     * which will build connections between nodes in a graph. We then call parseLocations with the first and second Function
     * variable as an argument and the right file and pass in string for CSV and function to create City (vertex) using
     * city name. We then pass in string for CSV and function to create City (vertex) using city name.
     *
     */
    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null;
        };

        Function<Map<String, String>, Void> addEdge = map -> {
            this.graph.addEdge(new City(map.get("name")), new Transport(this.graph.getCityByName(map.get("source")),
                    this.graph.getCityByName(map.get("destination")), TransportType.fromString(map.get("type")),
                    Double.parseDouble(map.get("price")), Double.parseDouble(map.get("duration"))));
            return null;
        };

        try {
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        try {
            parser.parseLocations(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }

        return "Successfully loaded cities and transportation files.";
    }

    /**
     * Our fastestRoute method calls on our Dijkstra class and passes in a source, destination, and edgeWeight to return
     * the shortestPath using the Dijkstra algorithm.
     *
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Function<Transport, Double> edgeWeight = edge -> edge.getMinutes();

        Dijkstra<City, Transport>  dijkstra = new Dijkstra<>();
        return dijkstra.getShortestPath(
                this.graph, this.graph.getCityByName(source), this.graph.getCityByName(destination), edgeWeight);
    }

    /**
     * Our fastestRoute method calls on our Dijkstra class and passes in a source, destination, and edgeWeight to return
     * the shortestPath using the Dijkstra algorithm.
     *
     */
    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Function<Transport, Double> edgeWeight = edge -> edge.getPrice();

        Dijkstra<City, Transport>  dijkstra = new Dijkstra<>();
        return dijkstra.getShortestPath(
                this.graph, this.graph.getCityByName(source), this.graph.getCityByName(destination), edgeWeight);
    }

    /**
     * Our fastestRoute method calls on our BFS class and passes in a source and destination to return the shortestPath
     * using the BFS algorithm.
     *
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS<City, Transport>  bfs = new BFS();
        return bfs.getPath(this.graph, this.graph.getCityByName(source), this.graph.getCityByName(destination));
    }
}
