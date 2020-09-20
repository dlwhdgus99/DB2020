public abstract class Node {

    private int numOfKeys;
    private int isLeafNode;
    private NonLeafNode parent;

    Node(int numOfKeys){
        this.numOfKeys = numOfKeys;
        this.isLeafNode = 0;
        parent = null;
    }

    public int getNumOfKeys(){
        return this.numOfKeys;
    }

    public void setNumOfKeys(int numOfKeys){
        this.numOfKeys = numOfKeys;
    }

    public int getIsLeafNode(){
        return this.isLeafNode;
    }

    public void setIsLeafNode(int isLeafNode){
        this.isLeafNode = isLeafNode;
    }

    public NonLeafNode getParent(){
        return this.parent;
    }

    public void setParent(NonLeafNode parent){
        this.parent = parent;
    }

    public abstract int searchInNode(int key);

    public abstract void rootSplitChild(NonLeafNode parent, int degree);

    public abstract void splitChild(NonLeafNode parent, int degree, int index);

    public abstract void borrowFromLeft(Node leftSibling, int index);

    public abstract void borrowFromRight(Node rightSibling, int index);

    public abstract void mergeWithLeft(Node root, Node leftSibling, int degree, int key);

    public abstract void mergeWithRight(Node root, Node rightSibling, int degree, int key);

    public abstract void fill(Node root, int degree, int key);
}
