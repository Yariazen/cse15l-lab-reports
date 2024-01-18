For the search engine, the most critical part is how data is stored. Since we want data in a list, we have a couple conventional options. 
- Arrays <br>
The downside of this approach is arrays are complicated and implementing an amortized array (an array that doubles in size each time it reaches capacity) is no easy task.
- ArrayLists <br>
ArrayLists are the traditional list in Java and are well optimized and contain many additional utilities that make them generally the best choice if there is a need for a list.
- LinkedLists <br>
LinkedLists are the type of list that's used when you want to access data sesquentially.
- HashTable <br>
This is actually the data structure that makes the most sense in my opinion (this is purely based on opinion) because it allows you to have constant time searches, which is an important characteristic for indexing large amounts of data. However, the amount of data we will be working with will not be large.

I chose to use an unconventional list called a HashSet to store the items of our search engine, because HashSets enforce the uniqueness of elements and have the union and intersection properties. 

Uniqueness of elements means a HashSet cannot contain duplicate elements. For example, if I have a HashSet that contains the string "Apple" and I add a new element that is also "Apple", the HashSet would not change, because "Apple" is already present in the Set.

The union of sets results in a set that is the combination of all the elements of the sets. The intersection of sets results in a set that contains only the elements that was in both of the sets. You can think of unions as OR and intersections as AND.

This decision is entirely up to you and how you want your list to behave. An ArrayList or an amortized array will both work just as well. I would not recommend using LinkedLists or HashTables as its not required for our purposes. 

With the choise of data structure done, the only thing left is to implement our Search Engine.

There's 4 important features we need to implement:
- Root <br>
This one is the easiest, as we simply want to print out all of the items we have stored in our list. Conveniently, the String class contains a method called join which will join the elements of a list together, seperated by a provided character. For example ```String.join("\n", items)``` returns each item on its own line. "\n" is the character for new line. You'll notice that I add an aditional "\n" character at the end of each response. The reason for that is so that curl responses look nice. Its not at all required or necessary.
- add <br>
This one is probably the most difficult. The first thing that needs to be understood is what is an URI. An URI consists of 3 major parts, the domain, the path, and the query.
For example in the URI https://yariazen.github.io/add?apple, the domain is yariazen.github.io, the path is /add, and the query is apple. You'll notice that https:// isn't part of any of these 3 parts, that's because that's the protocol, and is irrelevant to our use case of a URI. The main way to distinguish the domain, path, and query is the domain is always the first part of any URI. Anything after a slash is part of the path. The ? indicates the start of the query. With that out of the way, to implement /add, we need to make a resonable way to pass data. I chose to make my querys arguments seperated by the & symbol as that is the standard for multiple argument querys. Once a format is established, you then parse your data from your query, and add that to your list.
```java
String[] args = url.getQuery().split("&");
    HashSet<String> newItems = new HashSet<>();
    for (String item : args) {
        newItems.add(item);
    }
    items.addAll(newItems);
    return String.format("Added %s!\n", String.join(", ", newItems));
```
- search <br>
This one is fairly simple. It's a single argument query, so all that's required is to iterate through your list, and check if each item contains the argument that was passed to it. the String class contains a helpful method called ```contains``` that happens to be able to perform the check for you.
```java
String arg = url.getQuery();
    List<String> foundItems = new ArrayList<>();
    for (String item : items) {
        if (item.contains(arg)) {
            foundItems.add(item);
        }
    }
    return String.join("\n", foundItems) + "\n";
```
- 404
The one is the easiest, just return "404 Not Found!"