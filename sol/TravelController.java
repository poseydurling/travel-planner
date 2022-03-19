package sol;

import src.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!

    // Instantiates a new Graph object in the graph field
    private TravelGraph graph;

    public TravelController() {
    }

    @Override
    public String load(String citiesFile, String transportFile) {
        // creates an instance of the TravelCSVParser
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        // Creates a variable of type Function<Map<String, String>, Void>
        //       that will build the nodes in a graph. Keep in mind
        //       that the input to this function is a hashmap that relates the
        //       COLUMN NAMES of the csv to the VALUE IN THE COLUMN of the csv.
        //       It might be helpful to write a method in this class that takes the
        //       information from the csv needed to create an edge and uses that to
        //       build the edge on the graph.

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        // Creates another variable of type Function<Map<String, String>, Void> which will
        //  build connections between nodes in a graph.
        Function<Map<String, String>, Void> addEdge = map -> {
            this.graph.addEdge(new City(map.get("name")), new Transport(this.graph.getCityByName(map.get("source")),
                    this.graph.getCityByName(map.get("destination")), TransportType.fromString(map.get("type")),
                    Double.parseDouble(map.get("price")), Double.parseDouble(map.get("duration"))));
            return null; // need explicit return null to account for Void type
        };


        // call parseLocations with the first Function variable as an argument and the right file
        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        // call parseTransportation with the second Function variable as an argument and the right file
        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }

        // hint: note that parseLocations and parseTransportation can
        //       throw IOExceptions. How can you handle these calls cleanly?

        return "Successfully loaded cities and transportation files.";
    }

    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Function<Transport, Double> edgeWeight = edge -> edge.getMinutes();

        Dijkstra<City, Transport>  dijkstra = new Dijkstra<>();
        return dijkstra.getShortestPath(
                this.graph, this.graph.getCityByName(source), this.graph.getCityByName(destination), edgeWeight);
    }

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Function<Transport, Double> edgeWeight = edge -> edge.getPrice();

        Dijkstra<City, Transport>  dijkstra = new Dijkstra<>();
        return dijkstra.getShortestPath(
                this.graph, this.graph.getCityByName(source), this.graph.getCityByName(destination), edgeWeight);
    }

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS<City, Transport>  bfs = new BFS();
        return bfs.getPath(this.graph, this.graph.getCityByName(source), this.graph.getCityByName(destination));
    }
}
