package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyHTTPServer extends Thread implements HTTPServer {
    private final ExecutorService threadPool;

    public MyHTTPServer(int port, int nThreads) {
        threadPool = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public void addServlet(String httpCommand, String uri, Servlet s) {
        // Add servlet to mapping
    }

    @Override
    public void removeServlet(String httpCommand, String uri) {
        // Remove servlet from mapping
    }

    @Override
    public void run() {
        // Main server loop
    }

    @Override
    public void close() {
        threadPool.shutdown();
    }
}
