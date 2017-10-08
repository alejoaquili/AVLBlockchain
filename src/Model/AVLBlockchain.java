package Model;

import Model.DataStructures.AVLTree;
import Model.DataStructures.BlockData;
import Model.DataStructures.Blockchain;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;

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

    public void modify(long index, String filePath) throws FileNotFoundException {
        FileReader fr = new FileReader(filePath);
        StringBuffer info = new StringBuffer("");
        for(String each : fr)
            info.append(each);
        String data = new String(info);
        // FALTA ALGUN METODO Q DEVUELVA EL NODO N DE LA BLOCKCHAIN. -> Ya se hizo? es getNode() ? o falta hacer?
    }
    public void modify(long index){

    }
}
