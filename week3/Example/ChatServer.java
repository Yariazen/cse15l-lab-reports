import java.io.IOException;
import java.net.URI;

import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    public List<String> messages = new ArrayList<String>();
    
    public String handleRequest(URI url) {
        if (url.getPath().equals("/add-message")) {
            String[] args = url.getQuery().split("&");
            for (String arg : args) {
                String[] kvp = arg.split("=");
                String message = null;
                String user = null;
                if (kvp[0].equals("message")) {
                    message = kvp[1];
                } else if (kvp[0].equals("user")) {
                    user = kvp[1];
                }
                if (message != null && user != null) {
                    messages.add(user + ": " + message);
                }
            }
            return String.join("\n", messages);
        } else {
            return "404 Missing";
        }
    }
}

class ChatServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}