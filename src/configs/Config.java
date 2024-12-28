package configs; //configuration=set of settings that wrote how program/machine behavior

public interface Config { //public->for access from each place in the project
    void create(); //creating conf
    String getName();
    int getVersion();
}
//all the classes need to use this methods it is not a class it is a interface-"memshak"
