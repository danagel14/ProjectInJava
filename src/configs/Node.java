package configs;

import graph.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Node {
    private String name;
    private List<Node> edges;
    private graph.Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<Node>();
        this.msg = null;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getEdges() {
        return edges;
    }
    public void setEdges(List<Node> edges) {
        this.edges = edges;
    }
    public graph.Message getMsg() {
        return msg;
    }
    public void setMsg(graph.Message msg) {
        this.msg = msg;
    }

    public void addEdge(Node node) {
        edges.add(node);
    }
    public Boolean hasCycles(){
        Set<Node> visited = new HashSet<Node>();
        Set<Node> stack = new HashSet<Node>();
        return hasCyclesMine(this,visited,stack);
    }
    private Boolean hasCyclesMine(Node curr, Set<Node> visited, Set<Node> stack) {
        if (visited.contains(curr)) {
            return true;
        }
        if (stack.contains(curr)) {
            return false;
        }
        visited.add(curr);
        stack.add(curr);
        for (Node node: curr.getEdges())
        {
            if (hasCyclesMine(node,visited,stack))
            {
                return true;
            }
        }
        stack.remove(curr);
        return false;
    }



}