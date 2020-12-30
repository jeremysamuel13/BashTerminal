# BashTerminal
 
A basic bash shell with simple functions that allow a user to interact with a file system. Uses a 10-ary tree structure. 

Commands:

pwd	- Print the "present working directory" of the cursor node (e.g root/home/user/Documents).
ls	- List the names of all the child directories or files of the cursor.
ls -R	- Recursive traversal of the directory tree. Prints the entire tree starting from the cursor in
pre-order traversal.
cd {dir}	- Moves the cursor to the child directory with the indicated name (Only consider the direct children of the cursor).
cd /	- Moves the cursor to the root of the tree.
mkdir {name}	- Creates a new directory with the indicated name as a child of the cursor, as long as there is room.
touch {name}	- Creates a new file with the indicated name as a child of the cursor, as long as there is room.
exit	- Terminates the program.
find {name}	- Finds the node in the tree with the indicated name and prints the path.
cd ..	- Moves the cursor up to its parent directory (does nothing at root).
(e.g. cd root/home/user/Documents)
cd {path}	- Moves the cursor to the directory with the indicated path.
(e.g. cd root/home/user/Documents)
mv {src} {dst}	- Moves a file or directory specified by src to dst, including all children.
(Note that src and dst are absolute paths).
