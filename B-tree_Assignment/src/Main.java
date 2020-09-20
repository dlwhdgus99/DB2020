import java.io.*;

public class Main {

    public static void main(String[] args) {

        String indexFileName = args[1];

        if(args[0].equals("-c")){
            int degree = Integer.parseInt(args[2]);
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(indexFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.println(degree);
            writer.close();
            System.out.println("Tree is created. Degree is " + degree);
            System.exit(0);
        }

        Tree bpTree = new Tree();
        bpTree.buildTree(indexFileName);

        if(args[0].equals("-i")){
            try {
                String fileName = args[2];
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                String KeyValue = br.readLine();
                while(KeyValue != null){
                    String[] keyValueArray = KeyValue.split(",");
                    bpTree.insert(Integer.parseInt(keyValueArray[0]), Integer.parseInt(keyValueArray[1]));
                    KeyValue = br.readLine();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            bpTree.writeTree(indexFileName);
            System.out.println("Insert complete.");
        }

        else if(args[0].equals("-d")) {
            try {
                String fileName = args[2];
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                String deleteKey = br.readLine();

                while (deleteKey != null) {
                    bpTree.delete(Integer.parseInt(deleteKey));
                    deleteKey = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bpTree.writeTree(indexFileName);
            System.out.println("Delete complete.");
        }
        else if(args[0].equals("-s")) bpTree.singleSearch(Integer.parseInt(args[2]));

        else if(args[0].equals("-r")) bpTree.rangeSearch(Integer.parseInt(args[2]), Integer.parseInt((args[3])));
    }
}