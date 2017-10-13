package Model.DataStructures.AVL;


import Model.Exceptions.InvalidAVLOperationDataException;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class represents an AVL Tree, that is a binary tree which verify the AVL balance in the structure.
 * @param <T> the type of data to be store in each node of the tree
 */
public class AVLTree<T extends Serializable> {

    private AVLNode head;
    private Comparator<T> cmp;

    /**
     * Returns a new {@code AVLTree} object.
     * @param cmp a {@code Comparator<T>} that represents the sorting criteria of the tree.
     */
    public AVLTree(Comparator<T> cmp){

        if(cmp == null) throw new IllegalArgumentException("Comparator should not be null.");
        head = null;
        this.cmp = cmp;
    }

    private static int max(int a, int b) {
        return (a > b)? a : b;
    }

    private int weight(AVLNode node) {
        return (node == null) ? -1 : node.weight;
    }

    private int getBalance(AVLNode node) {
        return weight(node.right) - weight(node.left);
    }


    /**
     * This method return the {@code Comparator<T>} used in the {@code AVLTree}.
     * @return a {@code Comparator<T>} object.
     */
    public Comparator<T> getComparator() {
        return cmp;
    }

    /**
     * This method insert a generic T element in the tree.
     * @param element an specified element to be inserted in the tree.
     * @return a {@code AVLOperationData<T>} that specified the operation.
     */
    public AVLOperationData<T> insert(T element){
        AVLOperationData<T> data = new AVLOperationData<T>();
        data.setElement(element);
        data.setOperation(AVLOperationData.INSERT);

        if (element != null) {
            head = insert(head, element, data);
        }
        return data;
    }

    private AVLNode insert(AVLNode node, T element, AVLOperationData<T> data) {
        if(node == null){
            AVLNode aux = new AVLNode(element);
            data.setResult(true);
            return aux;
        }
        int result = cmp.compare(node.element, element);

        if(result > 0){
            node.left = insert(node.left, element, data);
            if (getBalance(node) == -2) {
                if (getBalance(node.left) < 0) {
                    node = singleRotateLeftChild(node, data);
                } else {
                    node = doubleRotateLeftChild(node, data);
                }
            }
        }else if (result < 0){
            node.right = insert(node.right, element, data);
            if (getBalance(node) == 2) {
                if (getBalance(node.right) > 0) {
                    node = singleRotateRightChild(node, data);
                } else {
                    node = doubleRotateRightChild(node, data);
                }
            }
        }
        node.weight = max(weight(node.right), weight(node.left)) + 1;
        return node;
    }

    private AVLNode singleRotateLeftChild(AVLNode node, AVLOperationData<T> data) {
        AVLNode aux = node.left;
        node.left = aux.right;
        aux.right = node;
        node.weight = max(weight(node.right), weight(node.left)) + 1;
        aux.weight = max(weight(node.right), weight(node.left)) + 1;
        data.addModified(aux.element);
        if(aux.left != null) {
            data.addModified(aux.left.element);
        }
        if(node.left != null) {
            data.addModified(node.left.element);
        }
        return aux;
    }

    private AVLNode singleRotateRightChild(AVLNode node, AVLOperationData<T> data) {
        AVLNode aux = node.right;
        node.right = aux.left;
        aux.left = node;
        node.weight = max(weight(node.right), weight(node.left)) + 1;
        aux.weight = max(weight(node.right), weight(node.left)) + 1;
        data.addModified(aux.element);
        if(aux.left != null) {
            data.addModified(aux.left.element);
        }
        if(node.right != null) {
            data.addModified(node.right.element);
        }
        return aux;
    }

    private AVLNode doubleRotateRightChild(AVLNode node, AVLOperationData<T> data) {
        node.right = singleRotateLeftChild(node.right, data);
        return singleRotateRightChild(node, data);
    }

    private AVLNode doubleRotateLeftChild(AVLNode node, AVLOperationData<T> data) {
        node.left = singleRotateRightChild(node.left, data);
        return singleRotateLeftChild(node, data);
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

    /**
     * This method REMOVE a generic T element of the {@code AVLTree} object.
     * @param element a generic element to be removed.
     * @return a {@code AVLOperationData<T>} that specified the operation.
     */
    public AVLOperationData<T> remove(T element) {
        AVLOperationData<T> data = new AVLOperationData<T>();
        data.setElement(element);
        data.setOperation(AVLOperationData.REMOVE);
        if (head != null) {
            head = remove(head, element, data);
        }
        return data;
    }

    private AVLNode remove(AVLNode node, T element, AVLOperationData<T> data) {
        if (node == null) {
            return node;
        }

        int result = cmp.compare(node.element, element);

        if (result > 0) {
            node.left = remove(node.left, element, data);
            if (getBalance(node) == 2) {
                if (getBalance(node.right) < 0) {
                    doubleRotateRightChild(node, data);
                } else {
                    singleRotateRightChild(node, data);
                }
            }
        }
        else if (result < 0) {
            node.right = remove(node.right, element, data);
            if (getBalance(node) == -2) {
                if (getBalance(node.left) > 0) {
                    doubleRotateLeftChild(node, data);
                } else {
                    singleRotateLeftChild(node, data);
                }
            }
        }
        else {
            data.setResult(true);
            if (node.left != null && node.right != null) {
                AVLNode aux = maxValueNode(node.left);
                node.element = aux.element;
                node.left = remove(node.left, aux.element,data);
            } else if (node.left == null) {
                return node.right;
            } else {
                return node.left;
            }
        }
        node.weight = max(weight(node.right), weight(node.left)) + 1;
        return node;
    }

    /**
     * This method SEARCH a generic type element in the {@code AVLTree} Object.
     * @param element a generic type element to be searched.
     * @return a {@code AVLOperationData<T>} that specified the operation.
     */
    public AVLOperationData<T> search(T element){
        return search(head, element);
    }

    private AVLOperationData<T> search(AVLNode node, T element){
        AVLOperationData<T> data = new AVLOperationData<T>();
        data.setElement(element);
        data.setOperation(AVLOperationData.SEARCH);
        if(node == null){
            return data;
        }

        int result = cmp.compare(node.element, element);

        if(result > 0 ){
            return search(node.left, element);
        }
        if(result < 0 ){
            return search(node.right, element);
        }

        data.setResult(true);
        return  data;
    }

    public AVLOperationData<T> apply(AVLOperationData<T> operation) throws InvalidAVLOperationDataException {
        if(operation == null) throw  new IllegalArgumentException("Operation cannot be null");
        switch (operation.getOperation()) {
            case AVLOperationData.INSERT:
                return this.insert(operation.getElement());
            case AVLOperationData.REMOVE:
                return this.remove(operation.getElement());
            case AVLOperationData.SEARCH:
                return this.search(operation.getElement());
            default:
                throw new InvalidAVLOperationDataException("The operation were invalid");
        }
    }

    /**
     * This method print the {@code AVLTree} inOrder.
     */
    public void printInOrder() {
        printInOrder(head);
    }

    private void printInOrder(AVLNode node) {
        if(node != null) {
            printInOrder(node.left);
            System.out.println("Element: " + node.element + " - Weight: " + (node.weight));
            printInOrder(node.right);
        }
    }

    private class AVLNode {

        T element;
        AVLNode left;
        AVLNode right;
        int weight;

        public AVLNode(T element) {
            this.element = element;
            left = null;
            right = null;
            weight = 0;
        }

    }

}
