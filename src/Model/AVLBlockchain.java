package Model;

import Model.DataStructures.AVL.AVLOperationData;
import Model.DataStructures.AVL.AVLTree;
import Model.Exceptions.InvalidAVLOperationDataException;
import Model.DataStructures.Blockchain.Blockchain;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class represents a {@code AVLTree} with a {@code Blockchain} to register all the operations in
 * the {@code AVLTree}.
 * @param <T> the generic type of data for the tree.
 */
public class AVLBlockchain<T extends Serializable> implements Serializable {
    static final long serialVersionUID = 42L;
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
    public AVLOperationData add(T element) {
        AVLOperationData<T> data = tree.insert(element);
        blockchain.add(data);
        return data;
    }

    /**
     * This method remove a element from the {@code AVLTree}.
     * @param element a element to remove.
     */
    public AVLOperationData remove(T element) {
        AVLOperationData<T> data = tree.remove(element);
        blockchain.add(data);
        return  data;
    }

    /**
     * This method lookup a specified element in the {@code AVLTree} and return all the indices of the
     * {@code Block} in the {@code Blockchain} that include any operation with a specified element.
     * @param element a specified element for lookup.
     * @return a new {@code List<Long>} with the required indices.
     */
    public List<Long> lookup(T element) {
        AVLOperationData<T> data = tree.search(element);


        if (!data.getResult()) {
            return null;
        }
        List<Long> result = findBlocks(element);
        blockchain.add(data);
        return  result;
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

    private void rebaseTree() throws InvalidAVLOperationDataException {
        this.tree = new AVLTree<>(this.tree.getComparator());
        for(AVLOperationData<T> opData: blockchain) {
            tree.apply(opData);
        }
    }

    /**
     * This method return true if the {@code Blockchain} integrity was preserved.
     * @return return the result fo a {@code Blockchain} validation.
     */
    public boolean verify(){
        return blockchain.verify();
    }

    /**
     * This method save the {@code Blockchain} which is {@code Serializable} in an output steam file.
     * @param path the file path to save the {@code Blockchain}.
     * @throws IOException if an I/O error occurs.
     */
    public void save(String path) throws IOException {
        if(path == null) throw new IllegalArgumentException("Wrong path.");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(blockchain);

        oos.flush();
    }

    /**
     * This method read a {@code Blockchain} from a specific text file.
     * @param path the file path.
     * @return true if the {@code Blockchain} was read without error. Return false otherwise.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the read object is not compatible.
     */
    public boolean read(String path) throws IOException, ClassNotFoundException, InvalidAVLOperationDataException {
        if(path == null) throw new IllegalArgumentException("Wrong path.");
        ObjectInputStream oos = new ObjectInputStream(new FileInputStream(path));

        Object blockchainAux = oos.readObject();
        if(!(blockchainAux instanceof Blockchain)) return false;

        this.blockchain = (Blockchain) blockchainAux;

        if(!blockchain.reHashNounce() || !verify()) {
            return false;
        }
        rebaseTree();

        return true;
    }

    public void printTree() {
        tree.printInOrder();
    }

    public void printBlockchain() {
        blockchain.print();
    }

}