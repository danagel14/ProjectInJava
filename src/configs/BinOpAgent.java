package configs;

import java.util.function.BinaryOperator;

import graph.Agent;
import graph.Message;
import graph.Topic;
import graph.TopicManagerSingleton;
import graph.TopicManagerSingleton.TopicManager;



public class BinOpAgent{
    private Agent agent;
    private String agentName;
    private String firstNameTopic;
    private String secondNameTopic;
    private String outTopicName;
    private BinaryOperator<Double> function;

    public BinOpAgent(String agentName,String firstNameTopic,String secondNameTopic,String outTopicName,BinaryOperator<Double> function){
        TopicManager topicMen=TopicManagerSingleton.get();
        Topic firstTopic=topicMen.getTopic(firstNameTopic);
        Topic secondTopic=topicMen.getTopic(secondNameTopic);
        Topic outTopic=topicMen.getTopic(outTopicName);

        if (firstTopic==null || secondTopic==null||outTopic==null){
                throw new IllegalArgumentException("Cannot be null");
        }
        this.agentName=agentName;
        this.firstNameTopic=firstNameTopic;
        this.secondNameTopic=secondNameTopic;
        this.outTopicName=outTopicName;
        this.function=function;

        this.agent =new Agent() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public void reset() {


            }

            @Override
            public void callback(String topic, Message msg) {
                if (topic.equals(firstNameTopic))
                {
                    double x= msg.asDouble;
                    double y= (secondNameTopic.getMsg()!=null)? secondNameTopic.getMsg().asDouble:0;
                    double result=function.apply(x,y);
                    outTopic.publish(new Message(result));

                } else if (topic.equals(secondNameTopic)) {
                    double y= msg.asDouble;
                    double x=(firstNameTopic.getMsg()!=null)? firstNameTopic.getMsg().asDouble:0;
                    double result=function.apply(x,y);
                    outTopic.publish(new Message(result));
                }
            }

            @Override
            public void close() {

            }

        };

        firstTopic.subscribe(this.agent);
        secondTopic.subscribe(this.agent);
        outTopic.subscribe(this.agent);
    }


    public void reset(TopicManager topicMen){
        topicMen.getTopic(firstNameTopic).publish(new Message(0.0));
        topicMen.getTopic(secondNameTopic).publish(new Message(0.0));
    }

    public void closeAgent(){
        TopicManager topicMen=TopicManagerSingleton.get();
        topicMen.getTopic(firstNameTopic).unsubscribe(this.agent);
        topicMen.getTopic(secondNameTopic).unsubscribe(this.agent);
        topicMen.getTopic(outTopicName).removePublisher(this.agent);
    }

}
