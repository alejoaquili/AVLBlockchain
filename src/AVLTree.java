import java.util.Comparator;

/**
 * represents a binary tree which verify the AVL structure
 * @param <T> the type of data to be store in each node of the tree
 */
public class AVLTree<T> {


    private Node head;
    private Comparator<T> cmp;

    public AVLTree(Comparator<T> cmp){

        if(cmp == null){
            throw new IllegalArgumentException(" comparator should not be null");
        }
        head = null;
        this.cmp = cmp;
    }

    public void  insert(T element){
        if(element != null){
            head = insert(head, element);
        }
    }


    private Node insert(Node n, T element){
        if( n == null){
            Node a = new Node(element);
            return a;
        }

        int result = cmp.compare(n.element, element);
        if(result > 0){
            n.left = insert(n.left, element);
        }else if (result < 0){
            n.right = insert(n.right, element);
        }
        return n;
    }

    public void delete(T element){
        if(element != null){
           head = delete(head, element );
        }


    }




    }

    private Node findReplace(Node n){
        if(n.left == null && n.right == null){
            return n;
        }


    }

    public boolean contains(T element){
        return true;
    }



    private class Node{

        T element;
        Node left;
        Node right;

        public Node(T element){
            this.element = element;
            left = null;
            right = null;
        }

        public boolean remove(T value, Node parent){
            int result = cmp.compare(element, value);
            if (result > 0) {
                if (left != null)
                    return left.remove(value, this);
                else
                    return false;
            } else if (result < 0) {
                if (right != null)
                    return right.remove(value, this);
                else
                    return false;
            } else {
                if (left != null && right != null) {
                    this.element = right.minValue();
                    right.remove(this.element, this);
                } else if (parent.left == this) {
                    parent.left = (left != null) ? left : right;
                } else if (parent.right == this) {
                    parent.right = (left != null) ? left : right;
                }
                return true;
            }
        }

        public T minValue() {
            if (left == null)
                return element;
            else
                return left.minValue();
        }

    }

}
