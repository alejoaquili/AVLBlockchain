/**
 * this class represetns the blockchain object
 * @param <T> is the type of object that each block will store.
 */
package Model.DataStructures;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the {@code Blockchain} object.
 * @param <T> is the data type that is each {@code Block} will store.
 */
public class Blockchain <T extends BlockDataInterface> {
    private Node lastNode;
    public final static String HASH_FUNCTION = "MD5";
    public final static String GENESIS = "0000000000000000000000000000000";
    private String zeros;
    public HashFunction encoder;
    private long size;

    /**
     * This constructor method will create an empty {@code Blockchain} object.
     * @param zeros are the number of zeros that the hash of each {@code Block} must have
     */
    public Blockchain(int zeros) throws NoSuchAlgorithmException, CloneNotSupportedException {
        if( zeros < 0) throw  new IllegalArgumentException();
        this.encoder = HashFunction.getSingletonInstance(HASH_FUNCTION);
        this.zeros = generateExpReg(zeros);
        this.lastNode = null;
        this.size = 0L;
    }

    /**
     * Method to create a regular expression of the type: "^@zeros.*" .
     * Example of output: Input: 3 -> Output: "^000.*" .
     */

    private static String generateExpReg(int zeros){
        StringBuffer expReg = new StringBuffer(zeros +3);
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
        if(lastNode == null){
            b = createGenesis(data);
        }else{
            Block<T> prev = lastNode.block;
            b = new Block<T>(prev.getIndex()+1, data, prev.getHash(), zeros);
            b.mine();
        }

        Node n = new Node(b);
        n.next = lastNode;
        lastNode = n;
        this.size++;
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
     * This method returns the size of the {@code Blockchain}. In other words this method return
     * the {@code Block} quantity in the chain.
     * @return a long with the {@code Blockchain} size.
     */
    public long getSize(){
        return this.size;
    }

    /**
     * This method return the node in a specified index, checking the consistency of the {@code Blockchain}
     * during the process.
     * @param index a long with the specified index.
     * @return  the specific {@code Node} required.
     * @throws InvalidBlockChainException if the {@code Blockchain} is invalid.
     */
    private Node getNode(long index) throws InvalidBlockChainException {
        if(index < 0 ) throw new IllegalArgumentException("Negative index.");
        long count = this.size - 1 - index;
        if(count < 0) throw new IndexOutOfBoundsException();
        Node current = lastNode;
        Block<T> block;
        while (count > 0 && current != null && current.next != null){
            block = current.block;
            Block<T> next = current.next.block;
            if(!block.getPrevHash().equals(next.getHash())){
                throw new InvalidBlockChainException("Impossible to get a Block, the Blockchain is invalid.");
            }
            current = current.next;
            count--;
        }
        Node aux = current;
        Block<T> auxBlock;
        while( aux != null && aux.next != null){
            auxBlock = aux.block;
            Block<T> next = aux.next.block;
            if(!auxBlock.getPrevHash().equals(next.getHash())){
             throw new InvalidBlockChainException("Impossible to get a Block, the Blockchain is invalid.");
            }
            aux = aux.next;
        }
        return current;
    }


//TERMINAR!
    public void setNode(long index, T data) throws InvalidBlockChainException {
        Node aux = getNode(index);
    }

    /**
     * This method verify the consistency of the {@code Blockchain}.
     * @return true if all the {@code Block} of the {@code Blockchain} are consistent. Otherwise return false.
     * Complexity: O(n).
     */
     public boolean verify(){
        if(lastNode == null || lastNode.block.getPrevHash().equals(GENESIS)){
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

     /**
     *This method for verify the consistency of the {@code Blockchain} has been deprecated because it works recursive
     * causing stack memory problems with chains bigger than 10 000 elements.
     * Use {@link #verify()} instead.
     * @param n the specified {@code Node}.
     * @param hash a {@code String} with the hash.
     * @return true if all the {@code Block} of the {@code Blockchain} are consistent. Otherwise return false.
     */
     @Deprecated
     private boolean verify(Node n, String hash){
        if(n == null || n.block.getPrevHash().equals(GENESIS)){
            return  true;
        }
        if(n.block.getHash().equals(hash)){
            return verify(n.next, n.block.getPrevHash());
        }
        return  false;
     }

     /**
      * This method look up for all the {@code Blocks} in the {@code Blockchain} that contains the element that has been
      * modified.
     * @param element the element of the modified node of the chain.
     * @param <S> the generic data type of the {@code Blocks}.
     * @return a {@code List<Long>} with the indices of the {@code Blocks} involved.
     */
     public <S> List<Long> findBlocks(S element){
        List<Long> indices = new ArrayList<>();
        Node current = lastNode;
        while(current != null){
            if(current.block.contains(element)){
                indices.add(current.block.getIndex());
            }
        }
        return indices;
    }

    private class Node {
        Block<T> block;
        Node next;
        public Node(Block<T> block){
            if(block == null){
                throw  new IllegalArgumentException("A block must not be null.");
            }
            this.block = block;
        }
    }

}

