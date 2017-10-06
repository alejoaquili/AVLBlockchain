package Model.DataStructures;

import java.util.Comparator;

public class BinarySearchTree<T>{

    Comparator<T> cmp;
    T element;
    BinarySearchTree<T> right;
    BinarySearchTree<T> left;

    public BinarySearchTree(T data, Comparator<T> cmp){
        if(data == null){
            throw new IllegalArgumentException();
        }
        if(cmp == null){
            throw new IllegalArgumentException();
        }
        right = null;
        left = null;
        this.cmp = cmp;
        this.element = data;
    }




    public void  add(T data){
        int result = cmp.compare(this.element, data);

        if (result < 0) {
            if(right != null) {
                right.add(data);
            }else{
                BinarySearchTree<T> aux = new BinarySearchTree<T>(data, cmp);
                right = aux;
            }

        }
        if (result > 0) {
            if(left != null) {
                left.add(data);
            }else{
                BinarySearchTree<T> aux = new BinarySearchTree<T>(data, cmp);
                left = aux;
            }

        }

    }



    public String toString(){
        String result = element.toString() + " ";
        if(left != null){
           result = result + left.toString();
        }
        if(right != null){
            result = result + right.toString();
        }
        return result;
    }

    public static void main(String[] args){
        BinarySearchTree<Integer> b = new BinarySearchTree<>(8, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        b.add(3);
        b.add(19);
        b.add(20);
        b.add(4);

        System.out.println(b);
    }

}
