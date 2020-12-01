/*
 * @author Jeremy Samuel
 * E-mail: jeremy.samuel@stonybrook.edu
 * Stony Brook ID: 113142817
 * CSE 214
 * Recitation Section 3
 * Recitation TA: Dylan Andres
 * HW #5
 */

/**
 * DirectoryTree class
 * Holds all file and directory nodes in a 10-ary tree
 */
public class DirectoryTree {
    DirectoryNode root;
    DirectoryNode cursor;

    final String INDENT = "    ";

    /**
     * default constructor
     */
    public DirectoryTree(){
        root = new DirectoryNode("root", false);
        root.setLevel(0);
        cursor = root;
    }

    /**
     * Resets cursor to the root
     */
    public void resetCursor(){
        cursor = root;
    }

    /**
     * Changes directory to a different path based on the given input
     * @param name
     * the given input. it can be an absolute path, relative path, or it can
     * be a "/" or ".." to go to root and go up one parent directory,
     * respectively.
     * @throws NotADirectoryException
     * Throws exception if the given directory is not an existing directory
     * or if it is a file
     */
    public void changeDirectory(String name) throws NotADirectoryException {

        if (name.equals("..")){
            moveCursorUp();
        }else if (name.equals("/")){
            resetCursor();
        }else if(name.contains("/")){
            String[] path = name.split("/");
            for (String x: path) {
                if(x.equals("root") || path[0].isEmpty()){
                    cursor = root;
                }else{
                    try {
                        cursor = cursor.findChild(x);
                    }catch (NotADirectoryException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                }
            }
        }else{
            try{
                cursor = cursor.findChild(name);
            }catch (NotADirectoryException e){
                throw new NotADirectoryException(e.getMessage());
            }
        }

    }

    /**
     * Moves cursor to its parent
     * Precondition: The cursor's parent is not null
     */
    public void moveCursorUp(){
        if(cursor.parent!=null){
            cursor = cursor.parent;
        }else{
            System.out.println("ERROR: Already at root directory.");
        }
    }

    /**
     * Gets the path of the cursor
     * @return
     * Returns the path of the cursor
     */
    public String presentWorkingDirectory(){
        cursor.updateDirectory();
        return cursor.getDirectory();
    }

    /**
     * Puts the children of the cursor in a single line
     * @return
     * Returns a string that contains all the children of the cursor on a
     * single line with each child being separated by a space.
     */
    public String listDirectory(){
        String x = "";
        for (DirectoryNode g: cursor.getChildren()) {
            if(g != null)
                x = x + g.getName() + " ";
        }

        return x;

    }

    /**
     * Prints the entire directory in a tree format, starting from the cursor
     */
    public void printDirectoryTree(){
        String p;
        p = printDirectoryHelper(cursor, cursor.getLevel());
        System.out.println(p);
    }

    /**
     * Helper class for printDirectoryTree(). Recursively traverses through
     * tree and eventually creates a string that contains the directory tree
     * @param node
     * the node to be traversed through
     * @param level
     * the depth of the directory in the directory tree (determines the
     * amount of indents for the line.
     * @return
     * a string containing the entire directory in a tree format, starting
     * from the given node
     */
    public String printDirectoryHelper(DirectoryNode node, int level){
        String p = "";

        for (int i = 0; i < node.getLevel() - level; i++) {
            p = p + INDENT;
        }

        if(node.getIsFile()){
            p = p + "- " + node.getName() + "\n";
        }else{
            p = p + "|- " + node.getName() + "\n";
        }


        for(DirectoryNode x: node.getChildren()){
            if(x != null){
                p = p + printDirectoryHelper(x, level);
            }
        }

        return p;
    }

    /**
     * Creates a directory at the cursor
     * @param name
     * the name of the directory (cannot include spaces or backslashes)
     * @throws IllegalArgumentException
     * Throws exception when the name includes a space or a backslash
     * @throws FullDirectoryException
     * Throws an exception when the children array of the cursor is at the
     * maximum capacity
     */
    public void makeDirectory(String name) throws IllegalArgumentException,
            FullDirectoryException{
        if(!name.contains(" ") && !name.contains("/")){
            try {
                cursor.addChild(new DirectoryNode(name, false));
            }catch(FullDirectoryException e){
                throw new FullDirectoryException(e.getMessage());
            }
        }else{
            throw new IllegalArgumentException("ERROR: Directory cannot have "
                    + "spaces or a '/'");
        }

    }

    /**
     * Creates a file at the cursor
     * @param name
     * the name of the file (cannot include spaces or backslashes)
     * @throws IllegalArgumentException
     * Throws exception when the name includes a space or a backslash
     * @throws FullDirectoryException
     * Throws an exception when the children array of the cursor is at the
     * maximum capacity
     * @throws NotADirectoryException
     * Throws exception when the cursor is pointing to a file
     */
    public void makeFile(String name) throws IllegalArgumentException,
            FullDirectoryException, NotADirectoryException{
        if(!name.contains(" ") && !name.contains("/")){
            try {
                cursor.addFile(new DirectoryNode(name, true));
            }catch(FullDirectoryException e){
                throw new FullDirectoryException(e.getMessage());
            }catch(NotADirectoryException e){
                throw new NotADirectoryException(e.getMessage());
            }
        }else{
            throw new IllegalArgumentException("ERROR: Files cannot have " +
                    "spaces or a '/'");
        }

    }

    /**
     * Prints all the paths of nodes that have the same name as the given name
     * @param name
     * the name of the node/nodes to look for
     * @param node
     * the initial node (the root)
     * @param f
     * boolean variable that updates to true when a single node has been found
     * @return
     * Returns true if at least one node has been found, false if otherwise.
     * @throws NotADirectoryException
     * Throws exception when found is false at the root node
     */
    public boolean findNode(String name, DirectoryNode node, boolean f) throws
            NotADirectoryException{
        boolean found = f;

        for(DirectoryNode x: node.getChildren()){
            if(x != null){
                if(x.getName().equals(name)){
                    x.updateDirectory();
                    System.out.println(x.getDirectory());
                    found = true;
                }

                if(!x.getIsFile())
                    if(!found)
                        found = findNode(name, x, false);
                    else
                        findNode(name, x, true);
            }
        }

        return found;
    }

    /**
     * Moves node from source path to the child of the node at the destination
     * path
     * @param f
     * The path of the source
     * @param t
     * The path of the destination
     */
    public void moveNodeTo(String f, String t){
        try {
            DirectoryNode initial = cursor;
            changeDirectory(f);
            DirectoryNode from = cursor;
            changeDirectory(t);
            if(cursor.size() < 10) {
                from.getParent().removeChild(from);
                changeDirectory(t);
                cursor.addChild(from);
                cursor.updateInfo();
                cursor = initial;
            }else{
                cursor = initial;
                throw new FullDirectoryException("ERROR: Not enough space in " +
                        "destination");
            }
        }catch(NotADirectoryException e){
            System.out.println(e.getMessage());
        }
    }

}
