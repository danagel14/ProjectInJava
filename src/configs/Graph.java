
package configs;

import java.util.*;
import graph.Agent;
import graph.TopicManagerSingleton;
import graph.TopicManagerSingleton.TopicManager;

public class Graph extends ArrayList<Node> {
    private HashMap<String, Node> nodeMap; // Maps the final node name ("TA", "Aplus", etc.) to the Node

    public Graph() {
        super();
        nodeMap = new HashMap<>();
    }

    public void createFromTopics() {
        this.clear();
        nodeMap.clear();

        TopicManager tm = TopicManagerSingleton.get();

        Collection<Topic> topics = tm.getTopics();

        for (Topic t : topics) {
            String topicNodeName = "T" + t.name;
            Node topicNode = nodeMap.get(topicNodeName);
            if (topicNode == null) {
                topicNode = new Node(topicNodeName);
                this.add(topicNode);
                nodeMap.put(topicNodeName, topicNode);
            }
        }

        for (Topic t : topics) {
            String topicNodeName = "T" + t.name;
            Node topicNode = nodeMap.get(topicNodeName);

            for (Agent subscriber : t.getSubs()) {
                Node subNode = findOrCreateAgentNode(subscriber);
                // topicNode -> subNode
                topicNode.addEdge(subNode);
            }

            for (Agent publisher : t.getPubs()) {
                Node pubNode = findOrCreateAgentNode(publisher);
                pubNode.addEdge(topicNode);
            }
        }
    }

    private Node findOrCreateAgentNode(Agent agent) {
        String agentNodeName = "A" + agent.getName();
        Node node = nodeMap.get(agentNodeName);
        if (node == null) {
            node = new Node(agentNodeName);
            this.add(node);
            nodeMap.put(agentNodeName, node);
        }
        return node;
    }

    public boolean hasCycles() {
        for (Node node : this) {
            if (node.hasCycles()) {
                return true;
            }
        }
        return false;
    }
}
