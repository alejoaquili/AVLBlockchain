package Model.DataStructures;

import java.util.Comparator;

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
        return (node == null) ? 0 : node.height;
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
                if (cmp.compare(node.left.element, element) > 0) {
                    node = simpleRotateL(node);
                } else {
                    node = doubleRotateL(node);
                }
            }
        }else if (result < 0){
            node.right = insert(node.right, element);
            if (getBalance(node) == 2) {
                if (cmp.compare(node.right.element, element) < 0) {
                    node = simpleRotateR(node);
                } else {
                    node = doubleRotateR(node);
                }
            }
        }
        node.height = max(height(node.right), height(node.left)) + 1;
        return node;
    }

    private AVLNode simpleRotateL(AVLNode node) {
        AVLNode aux = node.left;
        node.left = aux.right;
        aux.right = node;
        node.height = max(height(node.right), height(node.left)) + 1;
        aux.height = max(height(node.right), height(node.left)) + 1;
        return aux;
    }

    private AVLNode simpleRotateR(AVLNode node) {
        AVLNode aux = node.right;
        node.right = aux.left;
        aux.left = node;
        node.height = max(height(node.right), height(node.left)) + 1;
        aux.height = max(height(node.right), height(node.left)) + 1;
        return aux;
    }

    private AVLNode doubleRotateR(AVLNode node) {
        node.right = simpleRotateL(node.right);
        return simpleRotateR(node);
    }

    private AVLNode doubleRotateL(AVLNode node) {
        node.left = simpleRotateR(node.left);
        return simpleRotateL(node);
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
                if(getBalance(node.right) < 0) {
                    doubleRotateR(node);
                } else {
                    simpleRotateR(node);
                }
            }
        }
        else if (result < 0) {
            node.right = remove(node.right, element);
            if (getBalance(node) == -2) {
                if(getBalance(node.left) > 0) {
                    doubleRotateL(node);
                } else {
                    simpleRotateL(node);
                }
            }
        }
        else {
            if (node.left == null || node.right == null) {
                AVLNode aux = null;
                if (node.left == null) {
                    aux = node.right;
                } else {
                    aux = node.left;
                }
                node = aux;
            } else {
                AVLNode aux = maxValueNode(node.left);
                node.element = aux.element;
                node.left = remove(node.left, aux.element);
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
            System.out.println(node.element);
            print(node.right);
        }
    }
    public static void main(String[] args){
        AVLTree<Integer> b = new AVLTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        b.insert(3);
        b.insert(4);
        b.insert(7);
        b.insert(2);
        b.remove(5);
        b.remove(4);
        b.insert(2);
        b.insert(10);
        b.remove(7);
        b.insert(200);
        b.insert(300);
        b.insert(400);
        b.insert(100);
        b.insert(40);
        b.remove(100);

        b.print(b.head);
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
