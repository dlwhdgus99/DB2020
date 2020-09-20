public class NonLeafNode extends Node{

    private Pair<Node>[] nonLeafPair;
    private Node rightmostChildNode; //실제로는 NonLeafNode나 LeafNode 객체

    NonLeafNode(int degree, int numOfKeys){
        super(numOfKeys);
        nonLeafPair = new Pair[degree];
        rightmostChildNode = null;
    }

    public Pair<Node>[] getNonLeafPair(){
        return this.nonLeafPair;
    }

    public void setNonLeafPair(String[] keys, Node[] nodes){
        for(int i = 0; i < keys.length; i++){
            nonLeafPair[i] = new Pair<>(Integer.parseInt(keys[i]), nodes[i]);
        }
    }

    public Node getRightmostChildNode(){
        return this.rightmostChildNode;
    }

    public void setRightmostChildNode(Node rightmostChildNode){
        this.rightmostChildNode = rightmostChildNode;
    }

    public int searchInNode(int key){

        for(int i = 0; i < getNumOfKeys(); i++){
            if(nonLeafPair[i].first() > key){
                return i;
            }
        }
        return getNumOfKeys();
    }

    public void rootSplitChild(NonLeafNode parent, int degree){

        int splitUnit = (degree-1)/2;
        NonLeafNode tempNode = new NonLeafNode(degree, (degree - 2 - splitUnit));

        //child
        for(int i = 0 ; i < tempNode.getNumOfKeys(); i++) {
            int key = nonLeafPair[i + splitUnit + 1].first();
            Node value = nonLeafPair[i + splitUnit + 1].second();
            tempNode.nonLeafPair[i] = new Pair<>(key, value);
            tempNode.nonLeafPair[i].second().setParent(tempNode);
        }

        tempNode.rightmostChildNode = rightmostChildNode;
        tempNode.rightmostChildNode.setParent(tempNode);
        rightmostChildNode = nonLeafPair[splitUnit].second();

        setNumOfKeys(splitUnit);

        //parent
        parent.getNonLeafPair()[parent.getNumOfKeys()] = new Pair<>();

        int key = nonLeafPair[splitUnit].first();
        parent.getNonLeafPair()[0].setFirst(key);
        parent.getNonLeafPair()[0].setSecond(this);

        parent.setRightmostChildNode(tempNode);
        parent.setParent(null);
        parent.setNumOfKeys(1);

        //parent setting
        this.setParent(parent);
        tempNode.setParent(parent);
    }

    public void splitChild(NonLeafNode parent, int degree, int index) { //B-tree와 동일

        int splitUnit = degree/2;
        setNumOfKeys(splitUnit);
        NonLeafNode tempNode = new NonLeafNode(degree, ((degree-1) - splitUnit));

        //child
        for(int i = 0 ; i < tempNode.getNumOfKeys(); i++) {
            int key = nonLeafPair[i + splitUnit + 1].first();
            Node value = nonLeafPair[i + splitUnit + 1].second();
            tempNode.nonLeafPair[i] = new Pair<>(key, value);
            tempNode.nonLeafPair[i].second().setParent(tempNode);
        }
        tempNode.rightmostChildNode = rightmostChildNode;
        tempNode.rightmostChildNode.setParent(tempNode);
        rightmostChildNode = nonLeafPair[splitUnit].second();

        //parent
        parent.getNonLeafPair()[parent.getNumOfKeys()] = new Pair<>();
        for(int i = parent.getNumOfKeys(); i > index; i--){
            parent.getNonLeafPair()[i] = parent.getNonLeafPair()[i-1];
        }
        int key = nonLeafPair[splitUnit].first();
        parent.getNonLeafPair()[index] = new Pair<>(key, this);

        //제일 오른쪽에 들어갈 때
        if(parent.getNumOfKeys() == index){
            parent.setRightmostChildNode(tempNode);
        }
        else {
            parent.getNonLeafPair()[index + 1].setSecond(tempNode);
        }
        parent.setNumOfKeys(parent.getNumOfKeys()+1);

        //parent setting
        this.setParent(parent);
        tempNode.setParent(parent);
    }

    //왼쪽에서 빌려옴
    @Override
    public void borrowFromLeft(Node leftSibling , int index) {

        NonLeafNode left = (NonLeafNode)leftSibling;
        NonLeafNode parent = getParent();

        int key = left.getNonLeafPair()[left.getNumOfKeys()-1].first();
        int parentKey = parent.getNonLeafPair()[index].first();
        Node child = left.getRightmostChildNode();

        //right
        for(int i = this.getNumOfKeys(); i > 0; i --){
            this.nonLeafPair[i] = this.nonLeafPair[i-1];
        }
        this.nonLeafPair[0] = new Pair<>(parentKey, child);
        this.setNumOfKeys(this.getNumOfKeys()+1);

        //left
        left.rightmostChildNode.setParent(this);
        left.rightmostChildNode = left.nonLeafPair[left.getNumOfKeys()-1].second();
        left.nonLeafPair[left.getNumOfKeys()-1] = null;
        left.setNumOfKeys(left.getNumOfKeys()-1);

        //parent
        parent.nonLeafPair[index].setFirst(key);

    }

    //오른쪽에서 빌려옴
    @Override
    public void borrowFromRight(Node rightSibling, int index) {

        NonLeafNode right = (NonLeafNode)rightSibling;
        NonLeafNode parent = getParent();

        int key = right.getNonLeafPair()[0].first();
        int parentKey = parent.getNonLeafPair()[index].first();
        Node child = right.getNonLeafPair()[0].second();

        //left
        child.setParent(this);
        this.nonLeafPair[this.getNumOfKeys()] = new Pair<>(parentKey, this.rightmostChildNode);
        this.rightmostChildNode = child;
        this.setNumOfKeys(this.getNumOfKeys()+1);

        //right
        for(int i = 0; i < right.getNumOfKeys()-1; i++){
            right.nonLeafPair[i] = right.nonLeafPair[i+1];
        }
        right.setNumOfKeys(right.getNumOfKeys()-1);

        //parent
        parent.getNonLeafPair()[index].setFirst(key);
    }

    //왼쪽과 병합
    @Override
    public void mergeWithLeft(Node root, Node leftSibling, int degree, int key) {

        NonLeafNode left = (NonLeafNode)leftSibling;
        NonLeafNode parent = getParent();

        int leftKey = left.getNumOfKeys();
        int rightKey = this.getNumOfKeys();
        int index = parent.searchInNode(key)-1;
        int parentKey = parent.nonLeafPair[index].first();

        //merge
        this.rightmostChildNode.setParent(left);
        left.nonLeafPair[leftKey] = new Pair<>(parentKey, left.rightmostChildNode);
        left.rightmostChildNode = this.rightmostChildNode;
        for(int i = 0; i < rightKey; i++){
            this.nonLeafPair[i].second().setParent(left);
            left.nonLeafPair[i+leftKey+1] = this.nonLeafPair[i];
        }
        left.setNumOfKeys(leftKey+rightKey+1);
        this.setNumOfKeys(0);

        //split key가 맨 마지막 key 일때
        if(index == parent.getNumOfKeys()-1){
            //parent.getNonLeafPair()[index] = null;
            parent.setRightmostChildNode(left);
        }
        //split key가 맨 마지막보다 왼쪽에 있을 때
        else{
            for(int i = index; i < parent.getNumOfKeys()-1; i++){
                int key__ = parent.getNonLeafPair()[i+1].first();
                Node value__ = parent.getNonLeafPair()[i+1].second();
                parent.getNonLeafPair()[i] = new Pair<>(key__, value__);
            }
            parent.getNonLeafPair()[index].setSecond(left);
        }
        parent.setNumOfKeys(parent.getNumOfKeys()-1);
    }

    //오른쪽과 병합
    public void mergeWithRight(Node root, Node rightSibling, int degree, int key){

        NonLeafNode right = (NonLeafNode)rightSibling;
        NonLeafNode parent = getParent();

        int leftKey = this.getNumOfKeys();
        int rightKey = right.getNumOfKeys();
        int index = parent.searchInNode(key);
        int parentKey = parent.nonLeafPair[index].first();

        //merge
        right.rightmostChildNode.setParent(this);
        this.nonLeafPair[leftKey] = new Pair<>(parentKey, this.rightmostChildNode);
        this.rightmostChildNode = right.rightmostChildNode;
        for(int i = 0; i < rightKey; i++){
            right.nonLeafPair[i].second().setParent(this);
            this.nonLeafPair[i+leftKey+1] = right.nonLeafPair[i];
        }
        this.setNumOfKeys(leftKey+rightKey+1);
        right.setNumOfKeys(0);

        //split key가 맨 마지막 key 일때
        if(index == parent.getNumOfKeys()-1){
            //parent.getNonLeafPair()[index] = null;
            parent.setRightmostChildNode(this);
        }
        //split key가 맨 마지막보다 왼쪽에 있을 때
        else{
            for(int i = index; i < parent.getNumOfKeys()-1; i++){
                int key__ = parent.getNonLeafPair()[i+1].first();
                Node value__ = parent.getNonLeafPair()[i+1].second();
                parent.getNonLeafPair()[i] = new Pair<>(key__, value__);
            }
            parent.getNonLeafPair()[index].setSecond(this);
        }
        parent.setNumOfKeys(parent.getNumOfKeys()-1);
    }

    //non-leaf가 underflow 났을때
    public void fill(Node root, int degree, int key){

        NonLeafNode parent = getParent();
        int index = parent.searchInNode(key);
        int minKeyNum = (degree-1)/2;

        if(index == parent.getNumOfKeys() - 1){
            if(parent.rightmostChildNode.getNumOfKeys() > minKeyNum){
                borrowFromRight(parent.rightmostChildNode, index);
            }
            else if(index != 0 && parent.nonLeafPair[index-1].second().getNumOfKeys() > minKeyNum){
                borrowFromLeft(parent.nonLeafPair[index-1].second(), index-1);
            }
            else{
                mergeWithRight(root, parent.rightmostChildNode, degree, key);
            }
        }
        else if(index == parent.getNumOfKeys()){
            if(parent.nonLeafPair[index-1].second().getNumOfKeys() > minKeyNum){
                borrowFromLeft(parent.nonLeafPair[index-1].second(), index-1);
            }
            else{
                mergeWithLeft(root, parent.nonLeafPair[index-1].second(), degree, key);
            }
        }
        else{
            if(index == 0 && parent.nonLeafPair[index+1].second().getNumOfKeys() > minKeyNum){
                borrowFromRight(parent.nonLeafPair[index+1].second(), index);
            }
            else if(index == 0){
                mergeWithRight(root, parent.nonLeafPair[index+1].second(), degree, key);
            }
            else if(parent.nonLeafPair[index+1].second().getNumOfKeys() > minKeyNum){
                borrowFromRight(parent.nonLeafPair[index+1].second(), index);
            }
            else if(parent.nonLeafPair[index-1].second().getNumOfKeys() > minKeyNum){
                borrowFromLeft(parent.nonLeafPair[index-1].second(), index-1);
            }
            else{
                mergeWithRight(root, parent.nonLeafPair[index+1].second(), degree, key);
            }
        }
        if(this.getParent().equals(root) && root.getNumOfKeys() == 0){
            root.setParent(null);
            return;
        }

        if(this.getParent().getNumOfKeys() < minKeyNum && !this.getParent().equals(root)){
            this.getParent().fill(root, degree, key);
        }
    }
}