/**
 * this class represetns the blockchain object
 * @param <T> is the type of object that each block will store.
 */
package Model.DataStructures;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
     * This class represents the {@code Blockcahin} object.
     * @param <T> is the data type that is each {@code Block} will store.
     */

public class Blockchain <T> {

    private Node lastNode;
    public final static String HASH_FUNCTION = "SHA-256";
    public final static String GENESIS = "0000000000000000000000000000000";
    private String zeros;
    public HashFunction encoder;

    /**
     * This constructor method will create an empty {@code Blockchain} object.
     * @param zeros are the number of zeros that the hash of each {@code Block} must have
     */

    public Blockchain(int zeros) throws NoSuchAlgorithmException, CloneNotSupportedException {
        if( zeros < 0){
            throw  new IllegalArgumentException();
        }
        this.encoder = HashFunction.getSingletonInstance(HASH_FUNCTION);
        this.zeros = generateExpReg(zeros);
        lastNode = null;
    }

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
     * This method adds data to the {@BlockChain} in a new {@code Block}.
     * @param data the data to ve insert.
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
    }

    /**
     * This method creates a GENESIS (The first {@code Block} of the {@code Blockchain}){@code Block} for the {@code Blockchain}.
     * @param data the data to be insert.
     * @return a new GENESIS {@code Block}.
     */

    public Block<T> createGenesis(T data){
        Block<T> b = new Block<T>(0, data, GENESIS, zeros);
        b.mine();
        return b;
    }

    /**
     * This method verify the consistency of the {@Blockchain}.
     * @return true if all the {@code Block} of the {@code Blockchain} are consistent. Otherwise return false.
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

    public <S> List<Long> findBlocks(S element){
        List<Long> indeces = new ArrayList<>();

        Node current = lastNode;
        while(current != null){
            if(current.block.contains(element)){
                indeces.add(current.block.getIndex());
            }
        }
        return indeces;
    }


    public static void  main(String[] args) throws NoSuchAlgorithmException, CloneNotSupportedException {
        Blockchain<Integer> b = new Blockchain<>(4);
        for (int i = 0; i < 10; i++) {
            b.add(i);
            System.out.println("finish proccessing "+ i);
        }
        System.out.println(b.verify());
    }

    private class Node {
        Block<T> block;
        Node next;

        public Node(Block<T> block){
            if(block == null){
                throw  new IllegalArgumentException("a block must not be null");
            }
            this.block = block;
        }
    }

}

