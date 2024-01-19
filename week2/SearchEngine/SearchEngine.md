# Imports
```java
import java.io.IOException;
import java.net.URI;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
```
URI is the url object.

IOException is thrown by the SearchEngine as a potential exception that might be encountered.

HashSet, Set, ArrayList, and List are different structures to store data. The 2 main structures are HashSet and ArrayList. Set and List are used for flexibility and abstraction.

I chose to use an unconventional list called a HashSet to store the items of our search engine, because HashSets enforce the uniqueness of elements and have the union and intersection properties. 

Uniqueness of elements means a HashSet cannot contain duplicate elements. For example, if I have a HashSet that contains the string "Apple" and I add a new element that is also "Apple", the HashSet would not change, because "Apple" is already present in the Set.

The union of sets results in a set that is the combination of all the elements of the sets. The intersection of sets results in a set that contains only the elements that was in both of the sets. You can think of unions as OR and intersections as AND.

In general you would use Arrays or ArrayLists.
- Arrays <br>
The downside of this approach is arrays are complicated and implementing an amortized array (an array that doubles in size each time it reaches capacity) is no easy task.
- ArrayLists <br>
ArrayLists are the traditional list in Java and are well optimized and contain many additional utilities that make them generally the best choice if there is a need for a list.

# Handler
```java
class Handler implements URLHandler {
    Set<String> items = new HashSet<>();

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
```
Here we have the code that handles requests made to our URL. The key features to note are 
- "/" <br>
This is the root path and is handled by this code
```java
if (url.getPath().equals("/")) {
    return String.join("\n", items) + "\n";
}
```
```String``` has a very useful utility method for convering a list to a string called ```join```. Essentially the 1st argument is the delimiter and the 2nd argument is the list. A delimiter is a character or a sequence of characters that separates different parts of data. In this case, I use the character "\n" which is also known as new line as my delimiter. So given the list ```fruits = {"apple", "banana", "cherry"}```, ```String.join("\n", fruits)``` returns the string "apple\nbanana\ncherry".
- "/add" <br>
```java
else if (url.getPath().equals("/add")) {
    String[] args = url.getQuery().split("&");
    Set<String> newItems = new HashSet<>();
    for (String item : args) {
        newItems.add(item);
    }
    items.addAll(newItems);
    return String.format("Added %s!\n", String.join(", ", newItems));
}
```
A few things to note here is how a URI is structured. Given a URI https://yariazen.github.io/add?apple&banana&cherry, the protocol is https, the domain is yariazen.github.io, the path is /add, and the query is everything after the ?.

Another helpful utility method of the ```String``` class is ```split``` which usually has 1 argument which is what character you would like to split a string by. You can also give it a 2nd argument which is how many times you would like to split, but that's not needed here. In my case, I use ```split("&")``` because I expect queries to be presented in the form of "apple&banana&cherry&..." and the ```split``` method would return an array of the fruits {apple, banana, cherry}.

So in my code, if the Path is "/add", then I split the query by "&" and store the result in a variable ```String[] args```. Then I construct a new ```Set<String>``` called newItems to store the new items. After that, I use a loop called a foreach loop to iterate through args, and add each item into newItems. Keep in mind that an ArrayList will have the same methods. I choose to use a Set here to prevent duplicates. Finally, I use the method ```addAll``` of the ```Set``` class to add all of our new items to the items ```Set```. The ArrayList class has the same method.

Then, I use the method ```format``` and the method ```join``` of the String class to return the new items in a nice form.
- "/search" <br>
```java
else if (url.getPath().equals("/search")) {
    String arg = url.getQuery();
    List<String> foundItems = new ArrayList<>();
    for (String item : items) {
        if (item.contains(arg)) {
            foundItems.add(item);
        }
    }
    return String.join("\n", foundItems) + "\n";
}
```
Here, its very similar to the /add behavior. If the Path is "/search", we get the query and store it in a ```String arg```. The reason why I don't split the query is because the expected query is a singule arg. While we should have better input validation, I'm lazy. Then I construct a new ```List<String> found Items```, the reason why I don't use a ```Set``` here like I have before, is because uniqueness is no longer important, in addition uniqueness is already enforced by my prior decision to use sets. Then I use a for loop to iterate through the current items, and use the utility method ```contains``` of the ```String``` class to check if the query is a substring of any item in my items. If it is, its added to the foundItems list.

Then just like before, I use the ```join``` method to return the items that contain the query as a line by line list.
- "*" <br>
This is the simplest case. 
```java
else {
    return "404 Not Found!\n";
}
```
If the Path doesn't match any of the cases I defined, then simply return a 404 error.

# SearchEngine
```java
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
```
The SearchEngine is just copy pasted from NumberEngine.

A couple things to notice is it throws the IOException meaning it is possible for it to through an IOException. The rest of it is fairly simple. If no argument is passed to it, then a message is shown to provide a port number between 1024 and 49151. If an argument is passed to it, then its parsed into an int (this is bad practice because the string isn't guaranteed to be an int. Probably just the prof being lazy or perhaps a feature we'll implment in the future). A Server is then started at that port with our Handler.