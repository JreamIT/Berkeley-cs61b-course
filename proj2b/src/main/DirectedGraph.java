package main;
import java.util.*;

public class DirectedGraph {
    private Map<Integer, List<Integer>> graph;

    public DirectedGraph() {
        graph = new HashMap<>();
    }

    public void addNode(int num) {
        if (!graph.containsKey(num)) {
            graph.put(num, new ArrayList<>());
        }
    }

    public void addEdge(int num1, int num2) {
        addNode(num1);
        addNode(num2);
        List<Integer> item = graph.get(num1);
        item.add(num2);
        graph.put(num1, item);
    }

    public List<Integer> getEdges(Integer id) {
        return graph.get(id);
    }

}
