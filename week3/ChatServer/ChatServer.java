import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    public List<String> messages = new ArrayList<String>() {
        {
            add("System:Welcome to the chat room!");
        }
    };
    
    public String handleRequest(URI url) {
        try {
            if (url.getPath().equals("/add-message")) {
                String[] args = url.getQuery().split("&", 2);
                String message = null;
                String user = null;

                for (String arg : args) {
                    String[] kvp = arg.split("=", 2);
                    if ("user".equals(kvp[0])) {
                        user = kvp[1];
                    } else if ("s".equals(kvp[0])) {
                        message = kvp[1];
                    }
                }

                messages.add(user + ":" + message);

                String response = new String(Files.readAllBytes(Paths.get("./src/index.html")));
                return responseHandler(response);
            } else if (url.getPath().equals("/clear")) {
                messages.clear();
                messages.add("System:Welcome to the chat room!");
                return "<html><head><script>window.location.href='/';</script></head></html>";
            } else if (url.getPath().equals("/")) {
                String response = new String(Files.readAllBytes(Paths.get("./src/index.html")));
                return responseHandler(response);
              
            } else if (url.getPath().contains(".js") || url.getPath().contains(".css")) {
                return new String(Files.readAllBytes(Paths.get("./src" + url.getPath())));  
            } else {
                throw new Exception("404 Missing");
            }   
        } catch (Exception e)
        {
            return e.getMessage();
        }
    }

    private String responseHandler(String response)
    {
        String[] lines = response.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("<!-- messages -->")) {
                String messagesHTML = "";
                for (String message : messages) {
                    String[] args = message.split(":", 2);
                    String type = args[0];
                    String text = args[1];
                    if (type.equals("System")) {
                        messagesHTML += "<p class='system-message'>" + text + "</p>\n";
                    } else {
                        messagesHTML += "<p>" + 
                        "<span class='message-header'>" + type + "</span>" +
                        "<span class='message-body'>: " + text + "</span>" + 
                        "</p>\n";
                    }
                }
                lines[i] = messagesHTML;
                return String.join("", lines);
            }
        }
        return response;
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
