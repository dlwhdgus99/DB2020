public class LeafNode extends Node{

    private Pair<Integer>[] leafPair;
    private LeafNode rightSiblingNode;

    LeafNode(int degree, int numOfKeys){
        super(numOfKeys);
        setIsLeafNode(1);
        leafPair = new Pair[degree];
        rightSiblingNode = null;
    }

    public Pair<Integer>[] getLeafPair(){
        return this.leafPair;
    }

    LeafNode getRightSiblingNode(){ return this.rightSiblingNode; }

    public void setRightSiblingNode(LeafNode rightSiblingNode){
        this.rightSiblingNode = rightSiblingNode;
    }

    public int searchInNode(int key) {
        for(int i = 0; i < getNumOfKeys(); i++){
            if(leafPair[i].first() == key){
                return i;
            }
        }
        return -1;
    }

    public void leafInsert(int key, int value){

        int index = this.getNumOfKeys();
        this.getLeafPair()[index] = new Pair<>();
        while(index > 0 && this.getLeafPair()[index-1].first() > key){
            this.getLeafPair()[index] = this.getLeafPair()[index-1];
            index--;
        }
        this.getLeafPair()[index] = new Pair<>(key, value);

        this.setNumOfKeys(getNumOfKeys() + 1);
    }

    public void rootSplitChild(NonLeafNode parent, int degree){
        int splitUnit = (degree-1)/2;
        LeafNode tempNode = new LeafNode(degree, (degree - 1 - splitUnit));

        //child
        for(int i = 0 ; i < tempNode.getNumOfKeys(); i++) {
            int key = leafPair[i + splitUnit].first();
            int value = leafPair[i + splitUnit].second();
            tempNode.leafPair[i] = new Pair<>(key, value);
        }

        tempNode.rightSiblingNode = rightSiblingNode;
        rightSiblingNode = tempNode;

        setNumOfKeys(splitUnit);

        //parent
        parent.getNonLeafPair()[parent.getNumOfKeys()] = new Pair<>();

        int key = leafPair[splitUnit].first();
        parent.getNonLeafPair()[0].setFirst(key);
        parent.getNonLeafPair()[0].setSecond(this);

        parent.setRightmostChildNode(tempNode);

        parent.setNumOfKeys(1);

        //parent setting
        this.setParent(parent);
        tempNode.setParent(parent);
    }

    public void splitChild(NonLeafNode parent, int degree, int index) {

        int splitUnit = degree/2;
        LeafNode tempNode = new LeafNode(degree, (degree - splitUnit));

        //child
        for(int i = 0 ; i < tempNode.getNumOfKeys(); i++) {
            int key = leafPair[i + splitUnit].first();
            int value = leafPair[i + splitUnit].second();
            tempNode.leafPair[i] = new Pair<>(key, value);
        }

        tempNode.rightSiblingNode = rightSiblingNode;
        rightSiblingNode = tempNode;

        setNumOfKeys(splitUnit);

        //parent
        parent.getNonLeafPair()[parent.getNumOfKeys()] = new Pair<>();

        if(parent.getNumOfKeys() == 0){
            parent.getNonLeafPair()[0].setSecond(this);
        }

        for(int i = parent.getNumOfKeys(); i > index; i--){
            parent.getNonLeafPair()[i] = parent.getNonLeafPair()[i-1];
        }

        int key = leafPair[splitUnit].first();
        parent.getNonLeafPair()[index] = new Pair<>(key, this);

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

    public void leafDelete(int index){

        for(int i = index; i < this.getNumOfKeys(); i++){
            getLeafPair()[i] = getLeafPair()[i+1];
        }
        setNumOfKeys(getNumOfKeys() - 1);
    }

    //왼쪽에서 빌려옴
    public void borrowFromLeft(Node leftSibling, int index){

        LeafNode left = (LeafNode)leftSibling;
        NonLeafNode parent = getParent();

        int key = left.getLeafPair()[left.getNumOfKeys()-1].first();
        int value = left.getLeafPair()[left.getNumOfKeys()-1].second();

        left.leafPair[left.getNumOfKeys()-1] = null;
        left.setNumOfKeys(left.getNumOfKeys()-1);

        for(int i = this.getNumOfKeys(); i > 0; i--){
            int key_ = this.leafPair[i-1].first();
            int value_ = this.leafPair[i-1].second();
            this.leafPair[i] = new Pair<>(key_, value_);
        }
        this.leafPair[0] = new Pair<>(key,value);
        this.setNumOfKeys(this.getNumOfKeys()+1);

        parent.getNonLeafPair()[index].setFirst(key);

    }

    //오른쪽에서 빌려옴
    public void borrowFromRight(Node rightSibling, int index){

        LeafNode right = (LeafNode)rightSibling;
        NonLeafNode parent = getParent();

        int key = right.getLeafPair()[0].first();
        int value = right.getLeafPair()[0].second();

        this.leafPair[this.getNumOfKeys()] = new Pair<>(key, value);
        this.setNumOfKeys(this.getNumOfKeys()+1);

        for(int i = 0; i < right.getNumOfKeys()-1; i++){
            int key_ = right.leafPair[i+1].first();
            int value_ = right.leafPair[i+1].second();
            right.leafPair[i] = new Pair<>(key_, value_);
        }
        right.leafPair[right.getNumOfKeys()-1] = null;
        right.setNumOfKeys(right.getNumOfKeys()-1);

        parent.getNonLeafPair()[index].setFirst(right.leafPair[0].first());
    }

    //왼쪽과 병합
    public void mergeWithLeft(Node root, Node leftSibling, int degree, int key){

        LeafNode left = (LeafNode)leftSibling;
        NonLeafNode parent = getParent();

        int leftKey = left.getNumOfKeys();
        int rightKey = this.getNumOfKeys();
        int index = parent.searchInNode(key)-1;

        //merge
        for(int i = 0; i < rightKey; i++){
            int key_ = this.getLeafPair()[i].first();
            int value = this.getLeafPair()[i].second();
            left.getLeafPair()[i + leftKey] = new Pair<>(key_, value);
        }
        left.setNumOfKeys(leftKey+rightKey);
        this.setNumOfKeys(0);
        left.rightSiblingNode = this.rightSiblingNode;

        //split key가 맨 마지막 key 일때
        if(index == parent.getNumOfKeys()-1){
            parent.getNonLeafPair()[index] = null;
            parent.setRightmostChildNode(left);
        }
        //split key가 맨 마지막보다 왼쪽에 있을 때
        else{
            for(int i = index; i < parent.getNumOfKeys() - 1; i++){
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

        LeafNode right = (LeafNode)rightSibling;
        NonLeafNode parent = getParent();

        int leftKey = this.getNumOfKeys();
        int rightKey = right.getNumOfKeys();
        int index = parent.searchInNode(key);

        //merge
        for(int i = 0; i < rightKey; i++){
            int key_ = right.getLeafPair()[i].first();
            int value = right.getLeafPair()[i].second();
            this.getLeafPair()[i + leftKey] = new Pair<>(key_, value);
        }
        this.setNumOfKeys(leftKey + rightKey);
        right.setNumOfKeys(0);
        this.rightSiblingNode = right.rightSiblingNode;

        //split key가 맨 마지막 key 일때
        if(index == parent.getNumOfKeys()-1){
            //parent.getNonLeafPair()[index] = null;
            parent.setRightmostChildNode(this);
        }
        //split key가 맨 마지막보다 왼쪽에 있을 때
        else{
            for(int i = index; i < parent.getNumOfKeys() - 1; i++){
                int key__ = parent.getNonLeafPair()[i+1].first();
                Node value__ = parent.getNonLeafPair()[i+1].second();
                parent.getNonLeafPair()[i] = new Pair<>(key__, value__);
            }
            parent.getNonLeafPair()[index].setSecond(this);
        }
        parent.setNumOfKeys(parent.getNumOfKeys()-1);
    }

    //leaf가 underflow 났을때
    public void fill(Node root, int degree, int key){

        NonLeafNode parent = this.getParent();
        int index = parent.searchInNode(key);
        int minNumberOfKey = (degree-1)/2;

        LeafNode leftSibling = null;
        LeafNode rightSibling = null;

        if(index != 0) {
            leftSibling = (LeafNode) parent.getNonLeafPair()[index - 1].second();
        }

        if(index != parent.getNumOfKeys()){
            rightSibling = rightSiblingNode;
        }

        //child is rightmost node
        if(index == parent.getNumOfKeys()){
            if(leftSibling.getNumOfKeys() > minNumberOfKey){
                borrowFromLeft(leftSibling, index-1);
            }
            else{
                mergeWithLeft(root, leftSibling, degree, key);
            }
        }

        //child is leftmost node
        else if(index == 0){
            if(rightSibling.getNumOfKeys() > minNumberOfKey){
                borrowFromRight(rightSibling, index);
            }
            else{
                mergeWithRight(root, rightSibling, degree, key);
            }
        }

        //child is mid node
        else{
            if(rightSibling.getNumOfKeys() > minNumberOfKey){
                borrowFromRight(rightSibling, index);
            }
            else if(leftSibling.getNumOfKeys() > minNumberOfKey){
                borrowFromLeft(leftSibling, index-1);
            }
            //양쪽 다 부족하면 right sibling과 병합
            else{
                mergeWithRight(root, rightSibling, degree, key);
            }
        }

        int minKeyNum = (degree-1)/2;
        if(parent.getNumOfKeys() < minKeyNum && !parent.equals(root)){
            parent.fill(root, degree, key);
        }
    }
}
