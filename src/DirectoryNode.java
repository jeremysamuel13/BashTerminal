/*
 * @author Jeremy Samuel
 * E-mail: jeremy.samuel@stonybrook.edu
 * Stony Brook ID: 113142817
 * CSE 214
 * Recitation Section 3
 * Recitation TA: Dylan Andres
 * HW #5
 */

public class DirectoryNode {

    static final int MAX_ELEMENTS = 10;

    DirectoryNode[] children;
    DirectoryNode parent = null;

    String directory = "";

    String name;
    boolean isFile;

    int amountOfChildren;

    //the depth of the node in the tree
    int level;

    /**
     * Add a child to the directory
     * @param newChild
     * the child to be added
     * @throws FullDirectoryException
     * Throws exception if the children array is full
     * @throws NotADirectoryException
     * Throws exception when the node is a file
     */
    public void addChild(DirectoryNode newChild) throws FullDirectoryException,
            NotADirectoryException{
        if(!newChild.isFile) {
            try {
                addNew(newChild);
            }catch (FullDirectoryException e){
                throw new FullDirectoryException("ERROR: Directory is full");
            }
        }else{
            throw new NotADirectoryException("ERROR: The node is a file");
        }

    }

    /**
     * Adds a new child to the children array
     * @param newChild
     * the child to be added
     * @throws FullDirectoryException
     * Throws an exception if the directory is full
     */
    private void addNew(DirectoryNode newChild) throws FullDirectoryException {
        try {
            children[size()] = newChild;
            children[size()].setParent(this);
            children[size()].updateDirectory();
            children[size()].setLevel(children[size()].getParent().
                    getLevel() + 1);
            amountOfChildren++;

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FullDirectoryException("ERROR: Present directory is " +
                    "full.");
        }
    }

    /**
     * Adds a file to the children array
     * @param newFile
     * The file to be added
     * @throws FullDirectoryException
     * Throws an exception if the directory is full
     */
    public void addFile(DirectoryNode newFile) throws FullDirectoryException{
        if(!this.getIsFile()) {
            addNew(newFile);
        }else{
            throw new NotADirectoryException("ERROR: The node is a file");
        }
    }

    /**
     * Sets the level of the node
     * @param level
     * the level of the node
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the level of the node
     * @return
     * Returns the level of the node
     */
    public int getLevel() {
        return level;
    }

    /**
     * Default constructor
     */
    public DirectoryNode(){
        children = new DirectoryNode[MAX_ELEMENTS];
    }

    /**
     * Gets the path of the node
     * @return
     * Returns the path of the node
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Gets the children of the node
     * @return
     * Returns the children array of the node
     */
    public DirectoryNode[] getChildren() {
        return children;
    }

    /**
     * Constructor with parameters to set node name and if the node is a file
     * or not
     * @param name
     * The node's name
     * @param isFile
     * Whether the node is a file or not
     */
    public DirectoryNode(String name, boolean isFile){
        setIsFile(isFile);
        setName(name);
        children = new DirectoryNode[MAX_ELEMENTS];
    }

    /**
     * Gets the size of the children array
     * @return
     * Returns the size of the children array
     */
    public int size(){
        return amountOfChildren;
    }

    /**
     * Checks if the node has any children
     * @return
     * Returns true if the node has children, false if otherwise
     */
    public boolean hasChildren(){
        return amountOfChildren != 0;
    }

    /**
     * Changes if the node is a file or not
     * @param file
     * True if the node is a file, false if otherwise
     */
    public void setIsFile(boolean file) {
        isFile = file;
    }

    /**
     * Gets if the node is a file
     * @return
     * Returns true if the node is a file, false if otherwise
     */
    public boolean getIsFile() {
        return isFile;
    }

    /**
     * Gets the name of the node
     * @return
     * Returns the name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the node
     * @param name
     * The name of the node
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the parent node
     * @param parent
     * the parent node
     */
    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent node
     * @return
     * Returns the parent node
     */
    public DirectoryNode getParent() {
        return parent;
    }

    /**
     * Finds the child with the given name
     * @param name
     * The name of the child to be found
     * @return
     * Returns the child node
     * @throws NotADirectoryException
     * Throws exception when the child is a file, or when it doesnt exist
     */
    public DirectoryNode findChild(String name) throws NotADirectoryException{
        if(hasChildren()) {
            for (DirectoryNode g : children) {
                if(g != null) {
                    if (g.getName().trim().equals(name)) {
                        if (!g.isFile) {
                            return g;
                        } else {
                            throw new NotADirectoryException("ERROR: Cannot " +
                                    "change directory into a file.");
                        }
                    }
                }
            }
        }

        throw new NotADirectoryException("ERROR: No such directory named "
                + name);
    }

    /**
     * Updates the directory path of the node
     */
    public void updateDirectory(){
        DirectoryNode c = this;
        directory = "";
        while(c.getParent() != null){
            directory = "/" + c.getName() + directory;
            c = c.getParent();
        }
        directory = "root" + directory;
    }

    /**
     * Updates the info of the node (directory and node level)
     */
    public void updateInfo(){
        if(parent != null) {
            if (level != getParent().level + 1)
                level = parent.getLevel() + 1;
        }

        for (DirectoryNode child: children) {
            if(child != null){
                updateDirectory();
                child.updateInfo();
            }
        }
    }

    /**
     * Finds and removes given node from children array
     * @param c
     * The node to be removed
     * @return
     * The removed node
     */
    public DirectoryNode removeChild(DirectoryNode c){
        int g;
        int n = children.length;
        DirectoryNode removed = null;
        boolean found = false;
        for(g = 0; g<n; g++){
            if(children[g] == c) {
                removed = children[g];
                found = true;
                break;
            }
        }

        if(found) {
            n = n - 1;
            if (n - g >= 0)
                System.arraycopy(children, g + 1, children, g,
                        n - g);
            return removed;
        }

        return null;
    }

}
