import java.io.IOException;
import java.net.URI;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    HashSet<String> items = new HashSet<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.join("\n", items) + "\n";
        } else if (url.getPath().equals("/add")) {
            String[] args = url.getQuery().split("&");
            HashSet<String> newItems = new HashSet<>();
            for (String item : args) {
                newItems.add(item);
            }
            items.addAll(newItems);
            return String.format("Added %s!\n", String.join(", ", newItems));
        } else if (url.getPath().equals("/search")) {
            String arg = url.getQuery();
            List<String> foundItems = new ArrayList<>();
            for (String item : items) {
                if (item.contains(arg)) {
                    foundItems.add(item);
                }
            }
            return String.join("\n", foundItems) + "\n";
        } else {
            return "404 Not Found!\n";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
