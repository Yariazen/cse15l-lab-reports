# Lab 3
## Part 1 - Bugs

### Failure inducing input
```java
@Test
public void testReverseInPlaceFail() {
    int[] input1 = { 1, 2, 3, 4, 5 };
    ArrayExamples.reverseInPlace(input1);
    assertArrayEquals(new int[]{ 5, 4, 3, 2, 1 }, input1);
}
```

### Successful input
```java
@Test 
public void testReverseInPlace() {
    int[] input1 = { 3 };
    ArrayExamples.reverseInPlace(input1);
    assertArrayEquals(new int[]{ 3 }, input1);
}
```

### The symptom
![Image](images/Screenshot%202024-02-11%209.30.44%20AM.png)

### The fix

Original code
```java
static void reverseInPlace(int[] arr) {
    for(int i = 0; i < arr.length; i += 1) {
        arr[i] = arr[arr.length - i - 1];
    }
}
```

Fixed code
```java
static void reverseInPlaceFix(int[] arr) {
    for(int i = 0; i < arr.length/2; i += 1) { 
        int tmp = arr[i]; 
        arr[i] = arr[arr.length - i - 1];
        arr[arr.length - i - 1] = tmp;
    }
}
```

The original provided code makes several mistakes. Mosts glaringly, it swaps elements at index i with elements at length - i, without preserving the original element at i. So when inputs such as `[1, 2, 3, 4, 5]` are passed in, `[5, 4, 3, 4, 5]` is outputted. Another glaring mistake is the iteration. Since we're looking to reverse an array in place, we don't need to iterate over the entire array, only half of it. This works because for an even length array, iterating over half of the array will allow us to cover all elements. For an odd length array, we save 1 iterating because the middle element does not need to be changed.

My fix addresses these issues by storing the original value of the element at i in a local variable called `tmp` before swapping the element at i with the element at length - i. Then I set the element at length - i to `tmp` completing the reversal process. I also change the iteration to only iterate from 0 to `arr.length/2` which works because the value of `arr.length/2` is implicitely cast to int resulting in a ceil of the value of this operation allowing us to only iterate just as many times as necessary.

## Part 2

### a flag
```bash
find ./technical -type d -a -type f
```
```
```
This is finding all the items in `./technical` that are both a directory and a file. This isn't particularly useful.
```bash
find ./technical -type f -a -name "cvm*.txt"
```
```
./technical/biomed/cvm-2-1-038.txt
./technical/biomed/cvm-2-4-180.txt
./technical/biomed/cvm-2-4-187.txt
./technical/biomed/cvm-2-6-278.txt
./technical/biomed/cvm-2-6-286.txt
```
This is finding all the items in `./technical` that are both a file and has a name that matches `cvm*.txt"`. This also isn't particularly useful as the `-a` flag can be omited for identical behavior.

While the `-a` flag is interesting, its used implicitly when chaining tests. Its only real use is to explicitely denote a logical AND operation.

### o flag
```bash
find ./technical -type d -o -type f
```
```
./technical
./technical/911report
./technical/911report/chapter-1.txt
./technical/911report/chapter-10.txt
./technical/911report/chapter-11.txt
./technical/911report/chapter-12.txt
./technical/911report/chapter-13.1.txt
./technical/911report/chapter-13.2.txt
./technical/911report/chapter-13.3.txt
./technical/911report/chapter-13.4.txt
./technical/911report/chapter-13.5.txt
./technical/911report/chapter-2.txt
./technical/911report/chapter-3.txt
./technical/911report/chapter-5.txt
./technical/911report/chapter-6.txt
./technical/911report/chapter-7.txt
./technical/911report/chapter-8.txt
./technical/911report/chapter-9.txt
./technical/911report/preface.txt
...
```
This is finding all the items in `./technical` that are either a file or a directory. This combination isn't particularly useful by itself as `find` with no arguments would achieve the same result. The main use of the `-or` flag is to chain tests together.

```bash
find ./technical -name "chapter*.txt" -o -name "cvm*.txt"
```
```
./technical/911report/chapter-1.txt
./technical/911report/chapter-10.txt
./technical/911report/chapter-11.txt
./technical/911report/chapter-12.txt
./technical/911report/chapter-13.1.txt
./technical/911report/chapter-13.2.txt
./technical/911report/chapter-13.3.txt
./technical/911report/chapter-13.4.txt
./technical/911report/chapter-13.5.txt
./technical/911report/chapter-2.txt
./technical/911report/chapter-3.txt
./technical/911report/chapter-5.txt
./technical/911report/chapter-6.txt
./technical/911report/chapter-7.txt
./technical/911report/chapter-8.txt
./technical/911report/chapter-9.txt
./technical/biomed/cvm-2-1-038.txt
./technical/biomed/cvm-2-4-180.txt
./technical/biomed/cvm-2-4-187.txt
./technical/biomed/cvm-2-6-278.txt
./technical/biomed/cvm-2-6-286.txt
```
This finds all the items in `./technical` that has a name that matches `chapter*.txt` or `cvm*.txt`. This shows how the `-or` flag can be used more effectively. Something to note is these logical operator flags exist to create more advanced logic, and to make searching more efficient. In this case, instead of searching through the items in `./technical` twice to find the desired result, it's able to do it in just 1 loop.

### not flag
```bash
find ./technical -type f -not -name "*.txt"
```
```
```
This finds all the items in `./technical` that are files and doesn't have a name that matches `*.txt`. This is another flag that is useful for creating more advanced logic.

```bash
find ./technical -not -type f
```
```
./technical
./technical/911report
./technical/biomed
./technical/government
./technical/government/About_LSC
./technical/government/Alcohol_Problems
./technical/government/Env_Prot_Agen
./technical/government/Gen_Account_Office
./technical/government/Media
./technical/government/Post_Rate_Comm
./technical/plos
```
This finds all the items in `./technical` that aren't files. The purpose of this flag is more clearly shown here. While filtering types aren't particularly useful, its useful to have this type of logic.

### false flag

```bash
find ./technical -false
```
```
```
This finds nothing. Ths purpose of the false flag is to always return false.
```bash
find ./technical -false -type f
```
```
```
This command is utterly useless. Since the `-a` flag is implicitely used, this evaluates to `false and -type f` which evaluates to `false`. Creating a command that makes use of `-false` effectively is pretty difficult. It is however, another logical operator flag that is useful for creating more advanced logic.

<!-- Page Break -->
<hr style="page-break-after: always;">

# Reference

I used this as my reference for all the info provided on find.

find(1). (2022). In Linux Manual Pages (5.13.0). Retrieved from https://man7.org/linux/man-pages/man1/find.1.html