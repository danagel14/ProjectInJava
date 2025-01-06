package configs;


import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import graph.Agent;

public class GenericConfig implements Config {
    private String confFilePath;
    private List<ParallelAgent> agents = new ArrayList<>();

    public void setConfFile(String filePath) {
        this.confFilePath = filePath;
    }

    @Override
    public void create() {
        try (BufferedReader reader = new BufferedReader(new FileReader(confFilePath))) {
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            if (lines.size() % 3 != 0) {
                throw new IllegalArgumentException("Invalid configuration file format.");
            }

            for (int i = 0; i < lines.size(); i += 3) {
                String className = lines.get(i);
                String[] subs = lines.get(i + 1).split(",");
                String[] pubs = lines.get(i + 2).split(",");

                Class<?> clazz = Class.forName(className);
                Constructor<?> constructor = clazz.getConstructor(String[].class, String[].class);
                Agent agent = (Agent) constructor.newInstance((Object) subs, (Object) pubs);

                ParallelAgent parallelAgent = new ParallelAgent(agent);
                agents.add(parallelAgent);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to create configuration: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        for (ParallelAgent agent : agents) {
            agent.close();
        }
        agents.clear();
    }

    @Override
    public String getName() {
        return "GenericConfig";
    }

    @Override
    public int getVersion() {
        return 1;
    }

}
