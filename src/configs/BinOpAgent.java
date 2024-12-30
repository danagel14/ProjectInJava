package configs;

import java.util.function.BinaryOperator;

import graph.Agent;
import graph.Message;
import graph.TopicManagerSingleton;
import graph.TopicManagerSingleton.TopicManager;

public class BinOpAgent {
    private Agent agent;
    private String agentName;
    private String firstTopicName;
    private String secondTopicName;
    private String outputTopicName;
    private BinaryOperator<Double> func;

    public BinOpAgent(String AgentName, String FirstTopicName, String SecondTopicName, String OutputTopicName, BinaryOperator<Double> func) {
        TopicManager tm = TopicManagerSingleton.get();
        Topic firstTopic = tm.getTopic(FirstTopicName);
        Topic secondTopic = tm.getTopic(SecondTopicName);
        Topic outputTopic = tm.getTopic(OutputTopicName);

        this.agentName = AgentName;
        this.firstTopicName = FirstTopicName;
        this.secondTopicName = SecondTopicName;
        this.outputTopicName = OutputTopicName;
        this.func = func;

        this.agent = new Agent() {
            @Override
            public String getName() {
                return agentName;
            }

            @Override
            public void reset() {
                firstTopic.publish(new Message(0.0));
                secondTopic.publish(new Message(0.0));
            }

            @Override
            public void callback(String topic, Message msg) {
                if (topic.equals(firstTopicName)) {
                    // Message came from the first topic
                    double x = msg.asDouble;
                    double y = (secondTopic.getMsg() != null) ? secondTopic.getMsg().asDouble : 0;
                    double result = func.apply(x, y);
                    outputTopic.publish(new Message(result));
                } else if (topic.equals(secondTopicName)) {
                    // Message came from the second topic
                    double y = msg.asDouble;
                    double x = (firstTopic.getMsg() != null) ? firstTopic.getMsg().asDouble : 0;
                    double result = func.apply(x, y);
                    outputTopic.publish(new Message(result));
                }
            }

            @Override
            public void close() {
                // No cleanup needed here
            }
        };

        firstTopic.subscribe(this.agent);
        secondTopic.subscribe(this.agent);
        outputTopic.addPublisher(this.agent);
    }
}