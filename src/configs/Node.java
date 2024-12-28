package configs;

import graph.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
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
    public Message getMsg() {
        return msg;
    }
    public void setMsg(graph.Message msg) {
        this.msg = msg;
    }

    public void addEdge(Node node) {
        this.edges.add(node);
    }
    public Boolean hasCycles(){
        Set<Node> visited = new HashSet<>();
        Set<Node> stack = new HashSet<>();
        return hasCyclesMine(this,visited,stack);
    }




}