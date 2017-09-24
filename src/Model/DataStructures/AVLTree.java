package Model.DataStructures;

import java.util.Comparator;
import java.util.Random;

/**
 * represents a binary tree which verify the AVL structure
 * @param <T> the type of data to be store in each node of the tree
 */
public class AVLTree<T> {


    private AVLNode head;
    private Comparator<T> cmp;

    public AVLTree(Comparator<T> cmp){

        if(cmp == null){
            throw new IllegalArgumentException("Comparator should not be null");
        }
        head = null;
        this.cmp = cmp;
    }

    private static int max(int a, int b) {
        return (a > b)? a : b;
    }

    private int height(AVLNode node) {
        return (node == null) ? -1 : node.height;
    }

    private int getBalance(AVLNode node) {
        return height(node.right) - height(node.left);
    }

    public void  insert(T element){
        if(element != null){
            head = insert(head, element);
        }
    }

    private AVLNode insert(AVLNode node, T element){
        if(node == null){
            AVLNode aux = new AVLNode(element);
            return aux;
        }
        int result = cmp.compare(node.element, element);

        if(result > 0){
            node.left = insert(node.left, element);
            if (getBalance(node) == -2) {
                if (getBalance(node.left) < 0) {
                    node = singleRotateLeftChild(node);
                } else {
                    node = doubleRotateLeftChild(node);
                }
            }
        }else if (result < 0){
            node.right = insert(node.right, element);
            if (getBalance(node) == 2) {
                if (getBalance(node.right) > 0) {
                    node = singleRotateRightChild(node);
                } else {
                    node = doubleRotateRightChild(node);
                }
            }
        }
        node.height = max(height(node.right), height(node.left)) + 1;
        return node;
    }

    private AVLNode singleRotateLeftChild(AVLNode node) {
        AVLNode aux = node.left;
        node.left = aux.right;
        aux.right = node;
        node.height = max(height(node.right), height(node.left)) + 1;
        aux.height = max(height(node.right), height(node.left)) + 1;
        return aux;
    }

    private AVLNode singleRotateRightChild(AVLNode node) {
        AVLNode aux = node.right;
        node.right = aux.left;
        aux.left = node;
        node.height = max(height(node.right), height(node.left)) + 1;
        aux.height = max(height(node.right), height(node.left)) + 1;
        return aux;
    }

    private AVLNode doubleRotateRightChild(AVLNode node) {
        node.right = singleRotateLeftChild(node.right);
        return singleRotateRightChild(node);
    }

    private AVLNode doubleRotateLeftChild(AVLNode node) {
        node.left = singleRotateRightChild(node.left);
        return singleRotateLeftChild(node);
    }

    private AVLNode maxValueNode(AVLNode node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return maxValueNode(node.right);
        }
        return node;
    }

    public void remove(T element) {
        if (head != null) {
            head = remove(head, element);
        }
    }

    private AVLNode remove(AVLNode node, T element) {
        if (node == null) {
            return node;
        }

        int result = cmp.compare(node.element, element);

        if (result > 0) {
            node.left = remove(node.left, element);
            if (getBalance(node) == 2) {
                if (getBalance(node.right) < 0) {
                    doubleRotateRightChild(node);
                } else {
                    singleRotateRightChild(node);
                }
            }
        }
        else if (result < 0) {
            node.right = remove(node.right, element);
            if (getBalance(node) == -2) {
                if (getBalance(node.left) > 0) {
                    doubleRotateLeftChild(node);
                } else {
                    singleRotateLeftChild(node);
                }
            }
        }
        else {
            if (node.left != null && node.right != null) {
                AVLNode aux = maxValueNode(node.left);
                node.element = aux.element;
                node.left = remove(node.left, aux.element);
            } else if (node.left == null) {
                return node.right;
            } else {
                return node.left;
            }
        }
        node.height = max(height(node.right), height(node.left)) + 1;
        return node;
    }

    public String toString(AVLNode node){
        if(node == null){
            return "";
        }
        String l = toString(node.left);
        String r = toString(node.right);
        return l + node.element.toString() + r;
    }
    public void print(AVLNode node){
        if(node != null){
            print(node.left);
            System.out.println(node.element + " height = "+ node.height);
            print(node.right);
        }
    }

    public boolean verifyTree(){
        return verifyTree(head);
    }

    private boolean verifyTree(AVLNode node){
        if(node == null){
            return true;
        }

        int resultLeft = 1;
        int resultRight = -1;

        if(node.left != null) {
            resultLeft = cmp.compare(node.element, node.left.element);
        }
        if(node.right != null) {
            resultRight = cmp.compare(node.element, node.right.element);
        }

        boolean imVerified = resultLeft > 0 && resultRight < 0;
        return (  imVerified && verifyTree(node.left) && verifyTree(node.right));

    }

    public static void main(String[] args){
        AVLTree<Integer> b = new AVLTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        Random rand = new Random();

        for (int j = 0; j < 10000; j++) {
            int i = 0;
            for (i = 0; i < 1000; i++) {
                if (i % 2 == 0) {
                    b.insert(rand.nextInt() % 1000);
                } else {
                    b.remove(rand.nextInt() % 1000);
                }
            }

        }

        b.print(b.head);
        System.out.println(b.head.element);
        System.out.println(b.verifyTree());
    }

    private class AVLNode {

        T element;
        AVLNode left;
        AVLNode right;
        int height;

        public AVLNode(T element){
            this.element = element;
            left = null;
            right = null;
            height = 0;
        }
    }

}
