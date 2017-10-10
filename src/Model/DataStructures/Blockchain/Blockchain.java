package Model.DataStructures.Blockchain;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents the {@code Blockchain} object.
 * @param <T> is the data type that is each {@code Block} will store.
 */
public class Blockchain <T extends Serializable> implements Iterable<T>, Serializable {
    private transient List<Block<T>> blocks;
    private final transient static String HASH_FUNCTION = "MD5";
    private final transient String GENESIS = "0000000000000000000000000000000";
    private  transient String zeros;


    /**
     * This constructor method will create an empty {@code Blockchain} object.
     * @param zeros are the number of zeros that the hash of each {@code Block} must have
     */
    public Blockchain(long zeros) throws NoSuchAlgorithmException, CloneNotSupportedException {
        if( zeros < 0) throw  new IllegalArgumentException();
        HashFunction encoder = HashFunction.getSingletonInstance(HASH_FUNCTION);
        this.zeros = generateExpReg(zeros);
        this.blocks = new ArrayList<>();
    }

    /**
     * Method to create a regular expression of the type: "^@zeros.*" .
     * Example of output: Input: 3 -> Output: "^000.*" .
     */
    private static String generateExpReg(long zeros){
        StringBuffer expReg = new StringBuffer((int)(zeros) + 3);
        expReg.append('^');
        for (int i = 0 ; i < zeros ; i++)
            expReg.append('0');
        expReg.append('.');
        expReg.append('*');
        return new String(expReg);
    }

    /**
     * This method adds data to the {@code Blockchain} in a new {@code Block}.
     * @param data the data to be inserted.
     */
    public void add(T data){
        Block<T> b = null;
        if(blocks.size() == 0){
            b = createGenesis(data);
        }else{
            Block<T> prev = blocks.get(blocks.size() -1);
            b = new Block<>(prev.getIndex()+1, data, prev.getHash(), zeros);
            b.mine();
        }
        blocks.add(b);
    }

    /**
     * This method set the data of a specified {@code Block} in the {@code Blockchain}.
     * @param index the specified index.
     * @param data the new data the be set.
     */
    public void setBlock(int index, T data){
        if(index < 0 || index >= blocks.size()) throw  new IndexOutOfBoundsException("Invalid index.");
        Block<T> oldBlock = blocks.get(index);
        Block<T> newBlock = new Block<>(oldBlock.getIndex(), data, oldBlock.getPrevHash(), zeros);
        blocks.add(index, newBlock);
    }

    /**
     * This method creates a GENESIS (The first {@code Block} of the {@code Blockchain}) {@code Block} for
     * the {@code Blockchain}.
     * @param data the data to be insert.
     * @return a new GENESIS {@code Block}.
     */
    private Block<T> createGenesis(T data){
        Block<T> b = new Block<T>(0, data, GENESIS, zeros);
        b.mine();
        return b;
    }

    /**
     * This method verify the consistency of the {@code Blockchain}.
     * @return true if all the {@code Block} of the {@code Blockchain} are consistent. Otherwise return false.
     * Complexity: O(n).
     */
     public boolean verify(){
        if(blocks.size() == 0 || blocks.get(blocks.size() - 1).getPrevHash().equals(GENESIS)){
            return true;
        }
        for(int i = 0 ; i < blocks.size() - 1 ; i++){
            if(!blocks.get(i).getHash().equals(blocks.get(i+1).getPrevHash()))
                return  false;
        }
        return true;
     }

    public void saveFile(String path) throws IOException {
        if(path == null) throw new IllegalArgumentException("Wrong path.");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(blocks);
        oos.flush();
    }

    private boolean readFile(String path) throws IOException, ClassNotFoundException {
        if(path == null) throw new IllegalArgumentException("Wrong path.");
        ObjectInputStream oos = new ObjectInputStream(new FileInputStream(path));
        Object  obj = oos.readObject();
        if(!(obj instanceof List)) return false;

        List<?> list = (List<?>) obj;

        for(Object o: list) {
            if(!(o instanceof Block)) return false;
            Block block = (Block) o;
            this.blocks.add(block);

        }
        this.blocks = (List<Block<T>>) obj;
        return  true;
    }

    /**
     * Returns a Custom  Data Iterator, allows a {@code Blockchain} Object to be the target of
     * the "for-each loop" statement.
     * @return an {@code Iterator<T>}.
     */
    public Iterator<T> iterator() {
        return new DataIterator(blocks.iterator());
    }

    private class DataIterator implements Iterator<T> {

        private Iterator<Block<T>> iterator;

        public DataIterator(Iterator<Block<T>> iterator) {
            this.iterator = iterator;
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public T next() {
            return iterator.next().getData();
        }
    }

}

