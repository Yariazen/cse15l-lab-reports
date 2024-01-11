# Lab 1
## `cd`
### `cd` with no args
![Image](images/cd%20no%20arg.png) <br>
`pwd`: ~/lab1/lecture1 <br>
`cd` without args takes you to your home directory or ~ <br>
The output is the expected behavior

### `cd` with dir arg
![Image](images/cd%20dir.png)<br>
`pwd`: ~ <br>
`cd` with a directory as an argument will change your working directory to that directory <br>
The output is the expected behavior

### `cd` with file arg
![Image](images/cd%20file.png)<br>
`pwd`: ~/lab1/lecture1 <br>
`cd` with a file as an argument will result in an error <br>
The output is an error, because `cd` is for changing directories, and a file is not a directory.

## `ls`
### `ls` with no args
![Image](images/ls%20no%20arg.png)<br>
`pwd`: ~/lab1/lecture1 <br>
`ls` with no args lists the contents of the working directory. <br>
The output is the expected behavior.

### `ls` with dir arg
![Image](images/ls%20dir.png)<br>
`pwd`: ~/lab1/lecture1 <br>
`ls` with a directory as an argument lists the contents of the directory. <br>
The output is the expected behavior.

### `ls` with file arg
![Image](images/ls%20file.png)<br>
`pwd`: ~/lab1/lecture1 <br>
`ls` with a file as an argument lists the name of the file. <br>
The output is the expected behavior.

## `cat`
### `cat` with no args
![Image](images/cat%20no%20arg.png)<br>
`pwd`:~/lab1/lecture1<br>
`cat` with no args copies all text that follows. You can use cntl + c to exit. <br>
The output is the expected behavior.

### `cat` with dir arg
![Image](images/cat%20dir.png)<br>
`pwd`:~/lab1/lecture1<br>
`cat` with a dir as argument gives an error. <br>
The output is an error because `cat` copies all text, and a directory does not contain text.

### `cat` with file arg
![Image](images/cat%20file.png)<br>
`pwd`:~/lab1/lecture1<br>
`cat` with a file will output the contents of the file. <br>
The output is expected.
