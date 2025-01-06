package configs;

import graph.Agent;
import graph.Message;
import graph.Topic;
import graph.TopicManagerSingleton;

public class IncAgent implements Agent {
    private final String name;
    private final Topic input;
    private final Topic output;

    public IncAgent(String[] subs, String[] pubs) {
        if (subs.length < 1 || pubs.length < 1) {
            throw new IllegalArgumentException("IncAgent requires at least one subscription and one publication.");
        }

        this.name = "IncAgent";
        TopicManagerSingleton.TopicManager manager = TopicManagerSingleton.get();

        this.input = manager.getTopic(subs[0]);
        this.output = manager.getTopic(pubs[0]);

        this.input.subscribe(this);
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public void reset(){
    }
    @Override
    public void callback(String topic, Message msg) {
        if (topic.equals(input.name)) {
            try {
                double incrementedValue = msg.asDouble + 1;
                output.publish(new Message(incrementedValue));
            } catch (Exception e) {
                System.out.println("Error processing message in IncAgent: " + e.getMessage());
            }
        }
    }

    @Override
    public void close(){
        input.unsubscribe(this);
    }



}
