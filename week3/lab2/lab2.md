---
header-includes:
 - \usepackage{fvextra}
 - \DefineVerbatimEnvironment{Highlighting}{Verbatim}{breaklines,breakanywhere,commandchars=\\\{\}}
---
# Lab 2
## Part 1
ChatServer.java
```java
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
```

Queries using curl

![Querys](./images/Screenshot%202024-01-29%209.44.36%20AM.png)

### Query 

![Qeury 1](./images/Screenshot%202024-01-29%203.30.16%20PM.png)

#### Which methods in your code are called?
* `url.getPath()`
* `url.getQuery()`
* `List.add(E element)`
* `String.split(String regex)`
* `String.split(String regex, int limits)`
* `String.equals(Object obj)`
* `String.contains(CharSequence sequence)`
* `String.join(CharSequence delimiter, CharSequence... elements)`
* `Files.readAllBytes(Path path)`
* `responseHandler(String response)`
* `handleRequest(URI url)`

#### What are the relevant arguments to those methods, and the values of any relevant fields of the class?
* `url.getPath()`: Takes no arguments.
* `url.getQuery()`: Takes no arguments.
* `List.add(E element)`: Argument is a message to add to the `messages` list. The only time the `messages` field is updated.
* `String.split(String regex)`: Arguments are the regex expression `"\n"`.
* `String.split(String regex, int limit)`: Arguments are a regex expression `":"` or `"&` and limit 2.
* `String.equals(Object obj)`: Argument is `"System"`. It compares the message type with the string "System" to determine the formatting of the message.
* `String.contains(CharSequence sequence)`: Argument is `"<!-- messages -->"`. It checks if each line contains the specified marker.
* `String.join(CharSequence delimiter, CharSequence... elements)`: Arguments are `""` (empty string) and the modified lines. It joins the modified lines back into a single string with no delimiter.
* `Files.readAllBytes(Path path)`: Takes a Path argument representing the file path.
* `responseHandler(String response)`: Takes a String argument representing the HTTP response.
* `handleRequest(URI url)`: Takes a URI object argument representing the requested url.

#### How do the values of any relevant fields of the class change from this specific request? If no values got changed, explain why.
In the `handleRequest` method

*`messages`is the only relevant field. It is modified when the URL path is "/add-message". A new message, constructed from the "user" and "s" query parameters, is added to the messages list. Messages becomes `["System:Welcome to the chat room!", "jpolitz:Hello"]`

### Query 2

![Query 2](./images/Screenshot%202024-01-29%203.30.34%20PM.png)

The exact same behavior as Query 1. The new URL is parsed for the values of the "user" and "s" query parameters and is added to the messages list. Messages becomes `["System:Welcome to the chat room!", "jpolitz:Hello", "yash:How are you"]`

## Part 2
![Private Key](./images/Screenshot%202024-01-30%204.57.10%20PM.png)

The absolute path to my private key is then `/home/yariazen/.ssh/id_rsa`

![Public Key](./images/Screenshot%202024-01-30%205.01.56%20PM.png)

The absolute path to my private key is then `/home/linux/ieng6/oce/4f/ziz084/.ssh/authorized_keys`

![Login](./images/Screenshot%202024-01-30%205.00.47%20PM.png)

## Part 3
I learned that students are able to have a website hosted on acsweb.ucsd.edu while doing research into what is the ieng6 server, but it's useless because students don't have access to websites on that server as far as I can tell.

I also learned that `curl` doesn't work with urls that contain spaces anymore, as of Oct 11, 2021. Personal opinion, but if a "bug" exists and isn't fixed for a long period of time, that's not a bug anymore. Its just a feature.

Another cool thing I learned was when an html file references files on a server, java servers see that as a html request. I didn't handle it properly in my code, but I can see how adjusting relative paths to absolute ones are a pain so might as well as do something like react. That is construct the html serverside entirely.

I also took a detour and learned how to set a cookie in java because I wanted to uniquely identify clients. It ended up easier to just set the cookie client side though, since setting the cookie in java required modifying the given Server.java file.

In regards to the actual content of the lab, nothing is new to me.
<style>
    code {
        white-space : pre-wrap !important
        word-break: break-word;
    }
</style>