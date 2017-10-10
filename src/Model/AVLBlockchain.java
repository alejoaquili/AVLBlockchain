package Model;

import Model.DataStructures.AVL.AVLOperationData;
import Model.DataStructures.AVL.AVLTree;
import Model.Exceptions.InvalidAVLOperationDataException;
import Model.DataStructures.Blockchain.Blockchain;

import java.io.IOException;
import java.io.Serializable;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * This class represents a {@code AVLTree} with a {@code Blockchain} to register all the operations in
 * the {@code AVLTree}.
 * @param <T> the generic type of data for the tree.
 */
public class AVLBlockchain<T extends Serializable> {

    private Blockchain<AVLOperationData<T>> blockchain;
    private AVLTree<T> tree;

    /**
     * This method returns a new {@code AVLBlockchain} Object.
     * @param zeros this parameter is the specified quantity of zeros for the {@code Blockchain}.
     * @param cmp the {@code Comparator<T>} for the elements in the {@code AVLTree}.
     * @throws NoSuchAlgorithmException if a new instance of {@code HashFunction} is required.
     */
    public AVLBlockchain(int zeros, Comparator<T> cmp) throws NoSuchAlgorithmException {
        this.blockchain = new Blockchain<>(zeros);
        this.tree = new AVLTree<>(cmp);
    }

    /**
     * This method add a new element to the {@code AVLTree}.
     * @param element a new element.
     */
    public void add(T element) {
        AVLOperationData<T> data = tree.insert(element);
        blockchain.add(data);
    }

    /**
     * This method remove a element from the {@code AVLTree}.
     * @param element a element to remove.
     */
    public void remove(T element) {
        AVLOperationData<T> data = tree.remove(element);
        blockchain.add(data);
    }

    /**
     * This method lookup a specified element in the {@code AVLTree} and return all the indices of the
     * {@code Block} in the {@code Blockchain} that include any operation with a specified element.
     * @param element a specified element for lookup.
     * @return a new {@code List<Long>} with the required indices.
     */
    public List<Long> lookup(T element) {
        AVLOperationData<T> data = tree.search(element);
        blockchain.add(data);

        if (!data.getResult()) {
            return null;
        }
        return findBlocks(element);
    }

    /**
     * This method return a {@code List<Long>} with the indices of the {@code Block} in
     * the {@code Blockchain} that include any operation with a specified element.
     * @param element a specified element.
     * @return a new {@code List<Long>} with the required indices.
     */
    private List<Long> findBlocks(T element) {
        List<Long> list = new ArrayList<>();
        long index = 0;
        for (AVLOperationData<T> data : blockchain) {
            if (data.contains(element)) {
                list.add(index);
            }
            index++;
        }
        return list;
    }

    /**
     * This method modify a {@code Block} that has a specified index in the {@code Blockchain}
     * and replace the data with the data of a specified file.
     * @param index the index of the {@code Block} in the {@code Blockchain}.
     * @param filePath the specify absolute path of the file.
     * @throws IOException if an I/O error occurs.
     * @throws ClassCastException if the read object is not compatible.
     */
    public void modify(int index, String filePath) throws IOException, ClassNotFoundException {
        if(filePath == null) throw new IllegalArgumentException("Wrong path.");
        ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filePath));
        Object  obj = oos.readObject();
        if(!(obj instanceof AVLOperationData)){
            throw new ClassCastException("The object read is not a AVLOperationData.");
        }
        AVLOperationData<T> newdata = (AVLOperationData<T>)obj;
        blockchain.setBlock(index, newdata);

    }

    /**
     * This method modify a {@code Block} that has a specified index in the {@code Blockchain}
     * and replace the data with empty data.
     * @param index the index of the {@code Block} in the {@code Blockchain}.
     */
    public void modify(int index){
        AVLOperationData<T> voidSentinelAVLData = new AVLOperationData<>();
        blockchain.setBlock(index, voidSentinelAVLData);
    }

    private void rebaseTree(Comparator<T> cmp) throws InvalidAVLOperationDataException {
        AVLTree<T> tree = new AVLTree<>(cmp);
        for(AVLOperationData<T> opData: blockchain) {
            tree.apply(opData);
        }
    }


    /**
     * This method return true if the {@code Blockchain} integrity was preserved.
     * @return return the result fo a {@code Blockchain} validation.
     */
    public boolean validate(){
        return blockchain.verify();
    }

    /**
     * This method save the {@code Blockchain} in a specific file.
     * @param path the absolute path of the file.
     * @throws IOException if an I/O error occurs.
     */
    public void save(String path) throws IOException {
        blockchain.saveFile(path);
    }

    /**
     * This method read a {@code Blockchain} from a specific file.
     * @param path the absolute path of the file.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if an invalid cast occurs.
     */
    public void read(String path) throws IOException, ClassNotFoundException {
        blockchain.readFile(path);
    }

    //Cosas para borrar

    public static void main(String[] args){
        /*
        try {
            AVLBlockchain<Integer> b = new AVLBlockchain<>(4, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });

            Random rand = new Random();
            List<Integer> numbers = new ArrayList<>();
            int max = 100;
            for (int i = 0; i < max; i++) {
                numbers.add(rand.nextInt());
                b.add(numbers.get(i));
                if(i % (max / 10) == 0) {
                    System.out.println(i / 10 + "%");
                }

            }
            List<Long> l = b.lookup(numbers.get(2));

            System.out.println(l);

            for(AVLOperationData<Integer> d : b.blockchain){
                System.out.println(d);
            }

        }catch (NoSuchAlgorithmException e){
            System.out.println("no algorithm matches the request");
        }
        */
        // este es para ver solo blockchain


        try {

            Blockchain<Integer> b = new Blockchain<Integer>(4);

            Random rand = new Random();
            List<Integer> numbers = new ArrayList<>();
            int max = 100;
            for (int i = 0; i < max; i++) {
                numbers.add(rand.nextInt());
                b.add(numbers.get(i));
                if(i % (max / 10) == 0) {
                    System.out.println(i / (max/100) + "%");
                }

            }

            b.saveFile("/Users/franciscosanguineti/Desktop/archivo.txt");

            b = new Blockchain<>(4);
            System.out.println(b.readFile("/Users/franciscosanguineti/Desktop/archivo.txt"));

            for(Integer i : b){
                System.out.println(i);
            }

        }catch (NoSuchAlgorithmException e){
            System.out.println("no algorithm matches the request");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    }