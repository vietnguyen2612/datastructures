import java.util.*;

public class BinarySearchTree<T extends Comparable<T>, V>{
    private Node root; //root of BST

    private class Node{
        private T key;
        private V value;
        Node left;
        Node right;

        private Node(T k, V v){
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    //method that inserts a new Node with key and value from parameter into BST
    public void insert(T key, V value){
        Node parent = null; //node for parent of node to be inserted
        Node trav = root; //node for traversing
        while(trav != null){ //as long as traverse node is not null, trav is not at a leaf node
            parent = trav;
            if(key.compareTo(trav.key) < 0){ //left of node
                trav = trav.left;
            }
            else{ //right of node
                trav = trav.right;
            }
        }

        Node insNode = new Node(key, value);
        if(parent == null){ //empty tree
            root = insNode; //root is inserted node
        }
        else if(key.compareTo(parent.key) < 0){ // node belongs on left subtree of its parent
            parent.left = new Node(key, value); //left child
        }
        else{
            parent.right = new Node(key, value); //right child
        }

    }

    //method that searches if parameter T key is found in BST, returns value at key node if found
    //returns null if not found
    public V search(T key){
        Node trav = root; //Node that will traverse through tree
        while(trav != null){ //as long as trav does not reach end of a leaf node
            if(key.compareTo(trav.key) == 0){ //if key matches the node at trav
                return trav.value; //key is found, return out of method
            }
            else if(key.compareTo(trav.key) < 0){ //key is less than node at trav
                trav = trav.left; //traverse down left subtree
            }
            else{
                trav = trav.right; //traverse down right subtree
            }
        }
        return null; //trav reached the end of tree, match was not found
    }

    //helper method that explicitly deletes nodes that contain 0, 1, or two children
    private void deleteNode(Node toDelete, Node parent){
        if(toDelete.left == null || toDelete.right == null){
            //Cases 1 and 2, node to be deleted has one or no children
            Node toDeleteChild = null;
            if(toDelete.left != null){ //if node to be deleted has a left child
                toDeleteChild = toDelete.left; //toDeleteChild takes on key and value of said left child
            }
            else{ //node to be deleted has a right child
                toDeleteChild = toDelete.right; //toDeleteChild takes on key and value of right child, if there is not child such as in case 1 toDeleteChild will be null
            }

            if(toDelete == root){ //if the node to be deleted is the root
                root = toDeleteChild; //toDeleteChild becomes the root
            }
            else if(toDelete.key.compareTo(parent.key) < 0){ //if node to be deleted is a left child of its parent
                parent.left = toDeleteChild; //left child of parent becomes child of the deleted node, effectively removing it
            }
            else{ //node to be deleted is a right child of its parent
                parent.right = toDeleteChild; //right child of parent becomes child of the deleted node, effectively removing it
            }

        }
        else{ //Case 3, node has two children
            //get smallest item in right subtree
            Node replacementParent = toDelete; //replacement's parent is toDelete bc replacement will start off as toDelete's right child
            Node replacement = toDelete.right; //replacement node becomes right child of node to be deleted
            while(replacement.left != null){ //traverse down all of toDelete's right subtree's left children
                parent = replacement;
                replacement = replacement.left; //replacement keeps going down the left child until it no longer has a left child, effectively becoming the smallest item in the right subtree
            }
            toDelete.key = replacement.key; //replaces toDelete's key
            toDelete.value = replacement.value; //replaces toDelete's data

            deleteNode(replacement, replacementParent); //recursively calls deleteNode to delete original smallest item in right subtree, this line will run into case 1 or 2
        }
    }

    //method that deletes node with specified parameter key from BST
    public void delete(T key){
        Node parent = null; //node for parent of node to be inserted
        Node trav = root; //node for traversing

        while(trav != null && trav.key != key){ //traverses through tree until either trav passes leaf node or has matched keys
            parent = trav;
            if(key.compareTo(trav.key) < 0){
                trav = trav.left; //traverses down trav's left subtree
            }
            else{
                trav = trav.right; //traverses down trav's right subtree
            }
        }

        if(trav != null){ //no matching key found
            deleteNode(trav, parent); //deletes Node using helper method deleteNode
        }

    }

    //helper method that recursively creates list of values in inorder traversal
    private List<V> inorderList(Node root, List<V> list){
        if(root == null){
            return list;
        }
        if(root.left != null){ //if root has left child
            inorderList(root.left, list); //recursively calls inorderList with parameter as left child of root
        }
        list.add(root.value);
        if(root.right != null){ //if root has right child
            inorderList(root.right, list); //recursively calls inorderList with parameter as right child of root
        }

        return list;
    }

    //returns a list of values in inorder traversal using recursion helper method inorderList
    public List<V> inorderRec(){
        ArrayList<V> list = new ArrayList<V>(); //creates new ArrayList
        return inorderList(root, list); //calls recursive helper method inorderList to put list inorder
    }

    //method that returns the kth smallest item in the BST
    public V kthSmallest(int k){
        ArrayList<V> listTree = (ArrayList<V>)inorderRec(); //creates list inorder
        if(k > 0 && (k-1) <= listTree.size() ){ //validates k as a legitimate number to be searched, greater than 0 and less than size of tree
            return listTree.get(k-1); //returns value at key k-1 to return 1 as smallest, 2 as second smallest, etc.
        }

        return null; //kth not valid index or not found in tree
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer, Integer> intTree = new BinarySearchTree<>();
        intTree.insert(2, 2);
        intTree.insert(1, 1);
        intTree.insert(4, 4);
        intTree.insert(5, 5);
        intTree.insert(9, 9);
        intTree.insert(3, 3);
        intTree.insert(6, 6);
        intTree.insert(7, 7);
        intTree.insert(10, 10);
        intTree.insert(12, 12);
        intTree.insert(11, 11);
        intTree.delete(4);
        intTree.delete(9);
        System.out.println("Inserted 2, 1, 4, 5, 9, 3, 6, 7, 10, 12, & 11 in that order to int-int BST");
        System.out.println("Delete nodes 4 then 9");
        System.out.println("intTree BST (printed using inorder traversal): " + intTree.inorderRec().toString());
        System.out.println("Value of key 12 in BST (using search): " + intTree.search(12));
        System.out.println("Value of key 4 in BST (using search): " + intTree.search(4));
        System.out.println("3rd smallest element in tree (using kth smallest): " + intTree.kthSmallest(3));
        System.out.println();


        BinarySearchTree<Character, String> charTree = new BinarySearchTree<>();
        charTree.insert('a', "hi");
        charTree.insert('b', "my");
        charTree.insert('c', "name");
        charTree.insert('d', "really");
        charTree.insert('e', "is");
        charTree.insert('f', "Viet");
        charTree.insert('g', "how");
        charTree.insert('h', "are");
        charTree.insert('i', "they");
        charTree.insert('j', "you");
        charTree.insert('k', "doing?");
        charTree.delete('d');
        charTree.delete('i');
        System.out.println("Inserted nodes with keys a-k in the character-String (key-value pair) BST tree");
        System.out.println("Deleted node d then i");
        System.out.println("charTree BST (printed using inorder traversal): " + charTree.inorderRec().toString());
        System.out.println("Value of key f in BST (using search): " + charTree.search('f'));
        System.out.println("Value of key d in BST (using search): " + charTree.search('d'));
        System.out.println("4th smallest element in tree (using kth smallest): " + charTree.kthSmallest(4));
        System.out.println();


        BinarySearchTree<String, Integer> strTree = new BinarySearchTree<>();
        strTree.insert("alfa", 0);
        strTree.insert("bravo", 1);
        strTree.insert("charlie", 2);
        strTree.insert("delta", 3);
        strTree.insert("echo", 4);
        strTree.insert("foxtrot", 5);
        strTree.insert("golf", 6);
        strTree.insert("hotel", 7);
        strTree.insert("india", 8);
        strTree.insert("juliett", 9);
        strTree.insert("kilo", 10);
        strTree.delete("charlie");
        strTree.delete("hotel");
        System.out.println("Inserted nodes with NATO phonetic Strings a-k in the String-Integer BST tree");
        System.out.println("Deleted node charlie then hotel");
        System.out.println("strTree BST (printed using inorder traversal): " + strTree.inorderRec().toString());
        System.out.println("Value of key india in BST (using search): " + strTree.search("india"));
        System.out.println("Value of key charlie in BST (using search): " + strTree.search("charlie"));
        System.out.println("6th smallest element in tree (using kth smallest): " + strTree.kthSmallest(6));

    }
}
