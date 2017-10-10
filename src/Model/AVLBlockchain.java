package Model;

import Model.DataStructures.AVL.AVLOperationData;
import Model.DataStructures.AVL.AVLTree;
import Model.DataStructures.Blockchain.Blockchain;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * This class represents a {@code AVLTree} with a {@code Blockchain} to register all the operations in
 * the {@code AVLTree}.
 * @param <T> the generic type of data for the tree.
 */
public class AVLBlockchain<T> {

    private Blockchain<AVLOperationData<T>> blockchain;
    private AVLTree<T> tree;

    /**
     * This method returns a new {@code AVLBlockchain} Object.
     * @param zeros this parameter is the specified quantity of zeros for the {@code Blockchain}.
     * @param cmp the {@code Comparator<T>} for the elements in the {@code AVLTree}.
     * @throws CloneNotSupportedException if the specified algorithm were invalid.
     * @throws NoSuchAlgorithmException if a new instance of {@code HashFunction} is required.
     */
    public AVLBlockchain(int zeros, Comparator<T> cmp) throws CloneNotSupportedException, NoSuchAlgorithmException {
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
     * @returna new {@code List<Long>} with the required indices.
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
     * @throws FileNotFoundException if the specified path is empty.
     */
    public void modify(int index, String filePath) throws FileNotFoundException {
        FileReader fr = new FileReader(filePath);
        StringBuffer info = new StringBuffer("");
        for (String each : fr)
            info.append(each);
        String data = new String(info);
        AVLOperationData<T> newdata = new AVLOperationData<>();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!REVISAR CONSIGNA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        newdata.addModified(((T)data));
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



    public static void main(String[] args){
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

        }catch(CloneNotSupportedException e){
            System.out.println("clone");
        }catch (NoSuchAlgorithmException e){
            System.out.println("no algorithm matches the request");
        }
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


            for(Integer i : b){
                System.out.println(i);
            }

        } catch(CloneNotSupportedException e){
            System.out.println("clone");
        }catch (NoSuchAlgorithmException e){
            System.out.println("no algorithm matches the request");
        }
    }

}