package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static RequestInfo parseRequest(BufferedReader reader) throws IOException {
        // Read request line
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Empty request");
        }
        String[] parts = requestLine.split(" ");
        String httpCommand = parts[0];
        String uri = parts[1];

        // Parse URI
        String[] uriParts = uri.split("\\?");
        String[] uriSegments = uriParts[0].substring(1).split("/");
        Map<String, String> parameters = new HashMap<>();
        if (uriParts.length > 1) {
            for (String param : uriParts[1].split("&")) {
                String[] keyValue = param.split("=", 2);
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                parameters.put(key, value);
            }
        }

        // Read headers
        String line;
        int contentLength = 0;
        while (!(line = reader.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        // Read content
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            contentBuilder.append((char) reader.read());
        }
        byte[] content = contentBuilder.toString().getBytes();

        return new RequestInfo(httpCommand, uri, uriSegments, parameters, content);
    }

    public static class RequestInfo {
        private final String httpCommand;
        private final String uri;
        private final String[] uriSegments;
        private final Map<String, String> parameters;
        private final byte[] content;

        public RequestInfo(String httpCommand, String uri, String[] uriSegments, Map<String, String> parameters, byte[] content) {
            this.httpCommand = httpCommand;
            this.uri = uri;
            this.uriSegments = uriSegments;
            this.parameters = parameters;
            this.content = content;
        }

        public String getHttpCommand() {
            return httpCommand;
        }

        public String getUri() {
            return uri;
        }

        public String[] getUriSegments() {
            return uriSegments;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public byte[] getContent() {
            return content;
        }
    }
}
