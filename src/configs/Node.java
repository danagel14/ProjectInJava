package configs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import graph.Message;

public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Node> getEdges() {
        return edges;
    }

    public Message getMsg() {
        return msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEdge(Node node) {
        if (!edges.contains(node)) {
            edges.add(node);
        }
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public boolean hasCycles() {
        Set<Node> visited = new HashSet<>();
        Set<Node> inStack = new HashSet<>();
        return dfsCheck(this, visited, inStack);
    }

    private boolean dfsCheck(Node current, Set<Node> visited, Set<Node> inStack) {
        if (!visited.contains(current)) {
            visited.add(current);
            inStack.add(current);

            for (Node neighbor : current.edges) {
                //for nodes we dont visit yet
                if (!visited.contains(neighbor) && dfsCheck(neighbor, visited, inStack)) {
                    return true;
                }
                //circle in current node
                else if (inStack.contains(neighbor)) {
                    return true;
                }
            }
        }
        inStack.remove(current);
        return false;
    }
}