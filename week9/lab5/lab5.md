# Lab Report 5 - Putting it All Together (Week 9)

## Part 1

**Original Post from Student:**

---
**Title:** Issue with Displaying Usernames

**Description:**

Hey everyone,

I'm working on a small project where I'm trying to display a list of usernames from a database. However, I'm encountering an issue where only the first username is being displayed, and the rest seem to be ignored.

My guess is that there might be a problem with how I'm fetching and displaying the usernames, but I'm not entirely sure. Any help would be appreciated!

```
java Display.java
JohnDoe
```

---
**Response from TA:**

Hi there,

Thanks for reaching out! Can you confirm if you're looping through all the usernames properly in your code? It seems like the loop might be terminating after the first iteration. Could you also try running the command ```System.out.println(usernames)``` after fetching the data to see if you're getting all the usernames from the database?

---
**Student's Follow-up:**

Hi,

Thanks for the quick response! I tried running ```System.out.println(usernames)``` as you suggested, and it seems like I'm only getting one username printed out, which matches what's being displayed. So it looks like the issue might be with how I'm fetching the usernames from the database. Any ideas on what I might be doing wrong?

```
java Display.java
JohnDoe
```

---
**TA's Follow-up:**

From the terminal output and information provided, it's clear that the issue lies in the data fetching process. The student is only retrieving one username from the database causing only one username to be displayed despite multiple being expected.

---
### Setup Information
- File & Directory Structure
    - ```src/```
        - ```Database.java```
        - ```Display.java``` 
- ```Database.java```
```java
import java.sql.*;

public class Database {
    public static String[] fetchUsernames() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM users");
            rs.next();
            String[] usernames = {rs.getString("username")};
            conn.close();
            return usernames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
```
- ```Display.java```
```java
public class Display {
    public static void main(String[] args) {
        String[] usernames = Database.fetchUsernames();
        for (String username : usernames) {
            System.out.println(username);
        }
    }
}
```
- Full command line: ```java Display.java```
- Fixing the bug: <br> The issue appears to be that Database.fetchUsernames() is not retrieving all usernames from the database. This can be fixed by properly iterating through the usernames.
```java
import java.sql.*;

public class Database {
    public static String[] fetchUsernames() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM users");
            rs.last(); // Move cursor to the last row
            int rowCount = rs.getRow(); // Get the row count
            rs.beforeFirst(); // Move cursor back to the beginning
            String[] usernames = new String[rowCount];
            int index = 0;
            while (rs.next()) {
                usernames[index++] = rs.getString("username");
            }
            conn.close();
            return usernames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
```

## Part 2

To sum up everything I've learned from this course, I've learned that the find command has boolean operator flags. I've also learned that since 2021 as of commit ```9a8564a920188e49d5bd8c1c8573ddef97f6e03a```, curl no longer accepts non escaped spaces. Haven't used curl to any real extent since 2018 so it was quite interesting to see this rather unjustifiable change. If the standard hasn't been followed since its introduction, then its not the tooling that needs to be changed but rather the standard that needs changing. I've also learned about headless chrome, due to the rather confusing requirement for our lab reports to be done with github pages. That would be a good thing to clarify in the future. After all, my original workflow was using github pages to convert markdown to html, then pandoc to convert html to pdf. However, that was considered not "github pages". After talking with the TAs, its apparent the requirement is not for our lab reports to be done with github pages, but rather our lab reports should be rendered using chromium, which has an unhelpful bug regarding text wrap that requires me to inject some css into the html to fix.

In general, I've had a great time in labs and lectures but there's nothing for me to learn from this course. I've been daily driving linux since 2020, specifically debian bullseye in regards to the linux portion of this course. All my notes are done in vim using snippets for latex shortcuts since I started univesity. For the other major portion of the course pertaining to java, while I haven't worked in Java since 2016, I've been coding for almost a decade since, and Java makes sense now that I'm looking at it with fresh eyes.