package Model;

import Model.DataStructures.AVLTree;
import Model.DataStructures.BlockData;
import Model.DataStructures.Blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;

/**
 * Created by franciscosanguineti on 6/10/17.
 */
public class AVLBlockchain<T> {

    private Blockchain<BlockData<T>> blockchain;
    private AVLTree<T> tree;

    public AVLBlockchain(int zeros, Comparator<T> cmp) throws CloneNotSupportedException, NoSuchAlgorithmException {
        this.blockchain = new Blockchain<>(zeros);
        this.tree = new AVLTree<>(cmp);
    }

    public void add(T element) {
        BlockData<T> data = tree.insert(element);
        blockchain.add(data);
    }

    public void remove(T element) {
        BlockData<T> data = tree.remove(element);
        blockchain.add(data);
    }

    public List<Long> lookup(T element) {
        BlockData<T> data = tree.search(element);
        blockchain.add(data);

        if(data.getResult()) {
            return null;
        }
        return blockchain.findBlocks(element);
    }




}
