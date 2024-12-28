package configs;

import java.util.*;

import graph.Topic;
import graph.TopicManagerSingleton;
import graph.TopicManagerSingleton.TopicManager;
import graph.Agent;


public class Graph extends ArrayList<Node>{

    private HashMap<String,Node> nodeHashMap;

    public Graph() {
        super();
        nodeHashMap = new HashMap<>();
    }
    public boolean hasCycles() {
        for (Node node : this) {
            if (hasCyclesMine(node,new HashSet<>(),new HashSet<>())){
                return true;
            }
        }
    }
    public void createFromTopics(){
        this.clear();
        nodeHashMap.clear();

        TopicManager topicMen=TopicManagerSingleton.get();
        Collection<Topic> topics = topicMen.getTopics();

        for(Topic t:topics){
            String topicNodeName="T" + t.name;
            Node topicNode= nodeHashMap.computeIfAbsent(topicNodeName,Node::new);
            this.add(topicNode);
        }

        for (Topic t:topics){
            String topicNodeName="T" + t.name;
            Node topicNode= nodeHashMap.get(topicNodeName);

            if (topicNode==null){
                throw new IllegalStateException("The topic " + t.name + " does not exist");
            }

            for (Agent subscribe: topics.getSub())
            {
                Node subscriberNode=findOrCreatAgentNode(subscribe);
                topicNode.addEdge(subscriberNode);
            }

            for (Agent publisher: topics.getPubs())
            {
                Node publisherNode=findOrCreatAgentNode(publisher);
                publisherNode.addEdge(topicNode);
            }
        }


    }

    private Node findOrCreatAgentNode(Agent agent) {
        String agentNodeName="A"+agent.getName();
        Node node=nodeHashMap.get(agentNodeName);
        if (node==null){
            node=new Node(agentNodeName);
            this.add(node);
            nodeHashMap.put(agentNodeName,node);
        }
        return node;
    }
    private Boolean hasCyclesMine(Node node, Set<Node> visited, Set<Node> stack) {
        if (visited.contains(node)) {
            return true;
        }
        if (stack.contains(node)) {
            return false;
        }
        visited.add(node);
        stack.add(node);
        for (Node no: current.getAdges())
        {
            if (hasCyclesMine(no,visited,stack))
            {
                return true;
            }
        }
        stack.remove(node);
        return false;
    }

}
