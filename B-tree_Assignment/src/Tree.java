import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Tree {

    private Node root;
    private int degree;

    Tree(){}

    Tree(int degree){
        this.root = new LeafNode(degree, 0);
        this.degree = degree;
    }

    Tree(Node root, int degree){
        this.root = root;
        this.degree = degree;
    }

    public int getDegree(){
        return degree;
    }

    public Node getRoot(){
        return this.root;
    }

    public void insertNonFull(Node node, int key, int value){

        if(root instanceof LeafNode){ //노드 1개 있는 경우(꽉 차지x)
            ((LeafNode) root).leafInsert(key, value);
        }

        else{ //노드 3개 이상
            LeafNode leafNodeToBeInserted = (LeafNode)nonLeafSearch(root, key);
            if(leafNodeToBeInserted.searchInNode(key) >= 0) {
                System.out.println("Duplicate key.");
                return;
            }
            if(leafNodeToBeInserted.getNumOfKeys() < degree - 1){ //leafNode 꽉 차지x
                leafNodeToBeInserted.leafInsert(key, value);
            }
            else{ //leafNode 꽉 찬 경우
                NonLeafNode parent = leafNodeToBeInserted.getParent();
                int index = parent.searchInNode(key);
                leafNodeToBeInserted.leafInsert(key, value);
                leafNodeToBeInserted.splitChild(parent, degree, index);

                while(parent.getNumOfKeys() == degree){
                    index = parent.getParent().searchInNode(key);
                    parent.splitChild(parent.getParent(), degree, index);
                    parent = parent.getParent();
                }
            }
        }
    }

    public void insert(int key, int value){

        if(root.getNumOfKeys() == degree - 1){ //root가 꽉 찼을 때
            NonLeafNode newRoot = new NonLeafNode(degree, 0);
            root.rootSplitChild(newRoot, degree);
            root = newRoot;
            insertNonFull(root, key, value);
        }

        else{ //root가 꽉 차지 않았을 때
            insertNonFull(root, key, value);
        }
    }

    public Node nonLeafSearch(Node root, int key){ //찾는 key가 있을 leafnode의 후보 리턴

        int index = root.searchInNode(key);
        Node tempNode;

        if(index == root.getNumOfKeys()){
            tempNode = ((NonLeafNode)root).getRightmostChildNode();
        }
        else{
            tempNode = ((NonLeafNode)root).getNonLeafPair()[index].second();
        }

        if(tempNode.getIsLeafNode() == 1){
            return tempNode;
        }
        else{
            return nonLeafSearch(tempNode, key);
        }
    }

    public Node nonLeafSearchPrint(Node root, int key){ //찾는 key가 있을 leafnode의 후보 리턴

        int index = root.searchInNode(key);
        Node tempNode;

        if(index == root.getNumOfKeys()){
            tempNode = ((NonLeafNode)root).getRightmostChildNode();
        }
        else{
            tempNode = ((NonLeafNode)root).getNonLeafPair()[index].second();
        }

        if(tempNode.getIsLeafNode() == 1){
            return tempNode;
        }
        else{
            for(int i = 0; i < tempNode.getNumOfKeys()-1; i++) {
                System.out.print(((NonLeafNode) tempNode).getNonLeafPair()[i].first() + ",");
            }
            System.out.println(((NonLeafNode) tempNode).getNonLeafPair()[tempNode.getNumOfKeys()-1].first());
            return nonLeafSearchPrint(tempNode, key);
        }
    }

    public void singleSearch(int key){ //single search

        LeafNode leafNode = null;

        if(root.getIsLeafNode() == 1){
            leafNode = (LeafNode) root;
        }
        else {
            leafNode = (LeafNode) nonLeafSearchPrint((NonLeafNode) root, key);
        }

        int index = leafNode.searchInNode(key);
        if(index < 0) {
            System.out.println("NOT FOUND");
            return ;
        }

        int value = leafNode.getLeafPair()[index].second();

        System.out.println(value);
    }

    public void rangeSearchInLeaf(LeafNode startLeafNode, int startKey, int endKey){ //leaf노드에서 range search
        LeafNode tempLeafNode = startLeafNode;

        while(tempLeafNode != null){

            int tempKey = 0;
            for(int i = 0; i < tempLeafNode.getNumOfKeys(); i++){

                tempKey = tempLeafNode.getLeafPair()[i].first();

                if(tempKey > endKey){
                    break;
                }
                else if(tempKey >= startKey){
                    System.out.println(tempKey + "," + tempLeafNode.getLeafPair()[i].second());
                }
            }

            if(tempKey > endKey) return ;

            tempLeafNode = tempLeafNode.getRightSiblingNode();
        }
    }

    public void rangeSearch(int startKey, int endKey){ //range search

        LeafNode leafNode = (LeafNode)nonLeafSearch((NonLeafNode)root, startKey);

        rangeSearchInLeaf(leafNode, startKey, endKey);
    }

    public void delete(int key){
        int index = 0;
        //If root is leaf
        if(root instanceof LeafNode){
            index = root.searchInNode(key);
            if(index < 0) System.out.println("There not exists such key");
            else ((LeafNode)root).leafDelete(index);
        }

        //if root is non-leaf
        else {
            LeafNode leafNodeToDelete = (LeafNode) nonLeafSearch(root, key);
            index = leafNodeToDelete.searchInNode(key);

            if (index < 0) System.out.println("There not exists such key");
            else {
                leafNodeToDelete.leafDelete(index);
                if (leafNodeToDelete.getNumOfKeys() < (degree - 1) / 2) {
                    leafNodeToDelete.fill(root, degree, key);
                }
                else return;
            }
        }

        if(root.getNumOfKeys() == 0){
            if(root.getIsLeafNode() == 0) {
                Node child = ((NonLeafNode) root).getRightmostChildNode();
                root = child;
                child.setParent(null);
            }
        }
    }

    public void writeTree(String fileName){

        PrintWriter pw = null;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            pw = new PrintWriter(bw);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pw.println(degree);

        Queue<Node> q = new LinkedList<>();

        //root노드밖에 없을 때
        if(root.getIsLeafNode() == 1 && root.getNumOfKeys() >= 1){
            for(int i = 0; i < root.getNumOfKeys()-1; i++){
                pw.print(((LeafNode)root).getLeafPair()[i].first() + "," + ((LeafNode)root).getLeafPair()[i].second() + "/");
            }
            pw.print(((LeafNode)root).getLeafPair()[root.getNumOfKeys()-1].first() + "," + ((LeafNode)root).getLeafPair()[root.getNumOfKeys()-1].second());
            pw.close();
            return;
        }
        else if(root.getIsLeafNode() == 1){
            pw.close();
            return;
        }
        //root write
        NonLeafNode startNode = (NonLeafNode)root;

        for(int i = 0; i < startNode.getNumOfKeys()-1; i++){
            pw.print(startNode.getNonLeafPair()[i].first() + "/");
        }
        pw.print(startNode.getNonLeafPair()[startNode.getNumOfKeys()-1].first());
        pw.println();

        for(int i = 0; i < startNode.getNumOfKeys(); i++){
            q.offer(startNode.getNonLeafPair()[i].second());
        }
        q.offer(startNode.getRightmostChildNode());
        int n = 0;
        int k = 0;
        int thisLevelChildren = root.getNumOfKeys()+1;
        int nextLevelChildren = 0;
        while(!q.isEmpty()){
            k++;
            //non-leaf node
            if(q.peek().getIsLeafNode() == 0){
                NonLeafNode nonLeafPopNode = (NonLeafNode)q.poll();
                NonLeafNode parent = nonLeafPopNode.getParent();

                for(int i = 0; i < nonLeafPopNode.getNumOfKeys(); i++){
                    q.offer(nonLeafPopNode.getNonLeafPair()[i].second());
                }
                q.offer(nonLeafPopNode.getRightmostChildNode());

                for(int i = 0; i < nonLeafPopNode.getNumOfKeys()-1; i++){
                    pw.print(nonLeafPopNode.getNonLeafPair()[i].first() + "/");
                }
                pw.print(nonLeafPopNode.getNonLeafPair()[nonLeafPopNode.getNumOfKeys()-1].first());

                nextLevelChildren += (nonLeafPopNode.getNumOfKeys()+1);
                if(k == thisLevelChildren) {
                    pw.println();
                    k = 0;
                    thisLevelChildren = nextLevelChildren;
                    nextLevelChildren = 0;
                }
                else{
                    pw.print(" ");
                }

            }
            //leaf node
            else{
                if(n == 0) pw.println();
                n++;
                LeafNode leafPopNode = (LeafNode)q.poll();

                    for(int i = 0; i < leafPopNode.getNumOfKeys()-1; i++){
                        pw.print(leafPopNode.getLeafPair()[i].first() + "," + leafPopNode.getLeafPair()[i].second() + "/");
                    }
                    pw.print(leafPopNode.getLeafPair()[leafPopNode.getNumOfKeys()-1].first() +
                            "," + leafPopNode.getLeafPair()[leafPopNode.getNumOfKeys()-1].second());
                    if(!q.isEmpty()) {
                        pw.print(" ");
                    }
            }
        }
        pw.close();
    }

    public void buildTree(String fileName){

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            this.degree = Integer.parseInt(br.readLine());

            String rootLine = br.readLine();
            //root도 없을 때
            if(rootLine == null){
                this.root = new LeafNode(degree, 0);
                ((LeafNode)root).setRightSiblingNode(null);
                root.setParent(null);
                return;
            }
            String[] rootKeys = rootLine.split("/");

            String parentLine = br.readLine();
            ArrayList<Node> parentNodes = new ArrayList<>();
            ArrayList<Node> childNodes = new ArrayList<>();

            //root만 있을 때
            if(parentLine == null){
                LeafNode root = new LeafNode(degree, rootKeys.length);

                for (int i = 0; i < rootKeys.length; i++) {
                    String[] key = rootKeys[i].split(",");
                    root.getLeafPair()[i] = new Pair<>(Integer.parseInt(key[0]), Integer.parseInt(key[1]));

                }
                root.setRightSiblingNode(null);
                root.setParent(null);
                this.root = root;
                return;
            }
            else if(parentLine.length() == 0){
                parentLine = br.readLine();
                NonLeafNode root = new NonLeafNode(degree, rootKeys.length);
                root.setParent(null);

                String[] nodes = parentLine.split(" ");

                for(int i = 0; i < nodes.length; i++){

                    String[] keys = nodes[i].split("/");

                    LeafNode singleNode = new LeafNode(degree, keys.length);
                    for(int j = 0; j < keys.length; j++){
                        String[] key = keys[j].split(",");
                        singleNode.getLeafPair()[j] = new Pair<>(Integer.parseInt(key[0]), Integer.parseInt(key[1]));
                    }
                    singleNode.setParent(root);
                    childNodes.add(singleNode);
                    if(i != 0) {
                        ((LeafNode) childNodes.get(i-1)).setRightSiblingNode((LeafNode) childNodes.get(i));
                    }
                }

                for(int i = 0; i < root.getNumOfKeys(); i++){
                    root.getNonLeafPair()[i] = new Pair<>(Integer.parseInt(rootKeys[i]), childNodes.get(i));
                }
                root.setRightmostChildNode(childNodes.get(root.getNumOfKeys()));
                this.root = root;
                return;
            }

            //level 3 이상
            else{
                NonLeafNode root = new NonLeafNode(degree, rootKeys.length);
                root.setParent(null);

                String[] nodes = parentLine.split(" ");

                for(int i = 0; i < nodes.length; i++){

                    String[] keys = nodes[i].split("/");

                    NonLeafNode singleNode = new NonLeafNode(degree, keys.length);
                    for(int j = 0; j < keys.length; j++){
                        singleNode.getNonLeafPair()[j] = new Pair<>();
                        singleNode.getNonLeafPair()[j].setFirst(Integer.parseInt(keys[j]));
                    }
                    singleNode.setParent(root);
                    childNodes.add(singleNode);
                }

                for(int i = 0; i < root.getNumOfKeys(); i++){
                    root.getNonLeafPair()[i] = new Pair<>(Integer.parseInt(rootKeys[i]), childNodes.get(i));
                }
                root.setRightmostChildNode(childNodes.get(root.getNumOfKeys()));
                this.root = root;
            }

            boolean isLeaf = false;
            while(!isLeaf){

                parentNodes = childNodes;
                childNodes = new ArrayList<>();
                String childLine = br.readLine();

                if(childLine.length() == 0){
                    childLine = br.readLine();
                    isLeaf = true;
                }

                String[] nodes = childLine.split(" ");

                //child
                for(int i = 0; i < nodes.length; i++){

                    String[] keys = nodes[i].split("/");

                    Node singleNode = null;
                    if(isLeaf){
                        singleNode = new LeafNode(degree, keys.length);

                        for(int j = 0; j < keys.length; j++){
                            String[] leafKey = keys[j].split(",");
                            ((LeafNode)singleNode).getLeafPair()[j] = new Pair<>(Integer.parseInt(leafKey[0]), Integer.parseInt(leafKey[1]));
                        }
                    }
                    else {
                        singleNode = new NonLeafNode(degree, keys.length);
                        for (int j = 0; j < keys.length; j++) {
                            ((NonLeafNode)singleNode).getNonLeafPair()[j] = new Pair<>();
                            ((NonLeafNode)singleNode).getNonLeafPair()[j].setFirst(Integer.parseInt(keys[j]));
                        }
                    }
                    childNodes.add(singleNode);
                    if(isLeaf && i != 0){
                        ((LeafNode)childNodes.get(i-1)).setRightSiblingNode(((LeafNode)childNodes.get(i)));
                    }
                }
                int totalChildren = 0;

                //parent
                for(int i = 0; i < parentNodes.size(); i++) {

                    NonLeafNode parent = (NonLeafNode) parentNodes.get(i);

                    for (int j = 0; j < parent.getNumOfKeys(); j++) {
                        parent.getNonLeafPair()[j].setSecond(childNodes.get(totalChildren + j));
                        childNodes.get(totalChildren + j).setParent(parent);
                    }
                    parent.setRightmostChildNode(childNodes.get(totalChildren + parent.getNumOfKeys()));
                    childNodes.get(totalChildren + parent.getNumOfKeys()).setParent(parent);

                    totalChildren += (parent.getNumOfKeys() + 1);
                }
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}