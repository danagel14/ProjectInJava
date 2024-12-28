package configs;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.*;
import graph.Topic;
import graph.TopicManagerSingleton;
import graph.TopicManagerSingleton.TopicManager;
import graph.Agent;



public class Graph extends ArrayList<Node>{

    private HashMap<String,Node> nodeMap;

    public Graph() {
        super();
        nodeMap = new HashMap<>();
    }
    public boolean hasCycles() {
        for (Node node : this) {
            if (hasCyclesHelper(node,new HashSet<>(),new HashSet<>())){
                return true;
            }
        }
        return false;
    }
    public void createFromTopics(){
        this.clear();
        nodeMap.clear();

        TopicManager topicMen=TopicManagerSingleton.get();
        Collection<Topic> topics = topicMen.getTopics();

        for(Topic topic:topics){
            String topicNodeName="T" + topic.name;
            Node topicNode= nodeMap.computeIfAbsent(topicNodeName,Node::new);
            this.add(topicNode);
        }

        for (Topic topic:topics){
            String topicNodeName="T" + topic.name;
            Node topicNode= nodeMap.get(topicNodeName);

            if (topicNode==null){
                throw new IllegalStateException("The topic " + topic.name + " does not exist");
            }

            for (Agent subscribe: topic.getSub())
            {
                Node subscriberNode=findOrCreatAgentNode(subscribe);
                topicNode.addEdge(subscriberNode);
            }

            for (Agent publish: topic.getPub())
            {
                Node publisherNode=findOrCreatAgentNode(publish);
                publisherNode.addEdge(topicNode);
            }
        }

    } private Node findOrCreatAgentNode(Agent agent) {
        String agentNodeName="A"+agent.getName();
        Node node=nodeMap.get(agentNodeName);
        if (node==null){
            node=new Node(agentNodeName);
            this.add(node);
            nodeMap.put(agentNodeName,node);
        }
        return node;
    }


    private boolean hasCyclesHelper(Node curr, Set<Node> visited, Set<Node> stack) {
        if (stack.contains(curr)) {
            return true;
        }
        if (visited.contains(curr)) {
            return false;
        }
        visited.add(curr);
        stack.add(curr);

        for (Node neighbor : curr.getEdges()){
            if (hasCyclesHelper(neighbor, visited, stack)) {
                return true;
            }
        }
        stack.remove(curr);
        return false;
    }


}
