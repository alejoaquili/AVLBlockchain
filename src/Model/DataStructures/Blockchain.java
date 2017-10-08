package Model.DataStructures;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the {@code Blockchain} object.
 * @param <T> is the data type that is each {@code Block} will store.
 */
public class Blockchain <T extends BlockDataInterface> {
    private List<Block<T>> blocks;
    private final static String HASH_FUNCTION = "MD5";
    private final static String GENESIS = "0000000000000000000000000000000";
    private String zeros;


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
        if(blocks == null){
            b = createGenesis(data);
        }else{
            Block<T> prev = blocks.get(blocks.size() - 1);
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
    public void setBlock(long index, T data){
        if(index < 0 || index >= blocks.size()) throw  new IndexOutOfBoundsException("Wrong index.");
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



        Node current = lastNode;
        Block<T> b ;
        while( current != null && current.next != null){
            b = current.block;
            Block<T> next = current.next.block;
            if(!b.getPrevHash().equals(next.getHash())){
                return false;
            }
            current = current.next;
        }
        return true;
     }

}

