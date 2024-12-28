package graph;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    public final String name;
    protected List<Agent> sub;
    protected List<Agent> pub;
    private Message lastMessage;

    Topic(String name){
        if (name == null){
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name=name;
        this.sub=new ArrayList<>();
        this.pub=new ArrayList<>();
    }

    public void subscribe(Agent a){
        if (!sub.contains(a)){
            sub.add(a);
        }
    }
    public void unsubscribe(Agent a){
        sub.remove(a);
    }

    public void publish(Message m){
        lastMessage=m;
        for(Agent a:sub){
            a.callback(name, m);
        }
    }

    public void addPublisher(Agent a){
        if (!pub.contains(a)){
            pub.add(a);
        }
    }

    public void removePublisher(Agent a){
            pub.remove(a);
    }

    public Message getMsg(){
        return lastMessage;
    }

    public List<Agent> getPub() {
        return pub;
    }

    public List<Agent> getSub() {
        return sub;
    }
}
