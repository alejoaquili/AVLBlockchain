package Model.DataStructures;

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




    public boolean remove(T value) {
        if (head == null)
            return false;
        else {
            if (cmp.compare(head.element, value) == 0) {
                Node auxRoot = new Node(null);
                auxRoot.left = head;
                boolean result = head.remove(value, auxRoot);
                head = auxRoot.left;
                return result;
            } else {
                return head.remove(value, null);
            }
        }
    }





    private class Node {

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
                    this.element = left.minValue();
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
            if (right == null)
                return element;
            else
                return left.minValue();
        }

    }

}
