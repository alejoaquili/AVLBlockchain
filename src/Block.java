/**
 * This class represents the nodes of a {@code Blockchain}
 * @param <T> The parameter is a generic for the type of the stored data.
 */
public class Block <T>{

    private long index;
    private T data;
    private long nounce;
    private String prevHash;
    private String hash;

    /**
     *Creates a {@code Block} object for a {@code Blockchain}.
     * @param index index of this node on the blockchain.
     * @param data information to store in this block.
     * @param prevHash the hash encoded in SHA-256 of the previous block.
     */
    public Block(long index, T data, String prevHash){
        if(index <= 0 || prevHash == null){
            throw new IllegalArgumentException("index or previous hash were incorrect");
        }

        this.data = data;
        this.index = index;
        this.prevHash = prevHash;
        nounce = 0;
        hash = Encoder.getSha256(Long.toString(index) + data.toString() + prevHash);   //checkear como se hace
    }

    /**
     * This method  figured out the nounce number that valid the hash of this block
     */
    public void mine() {
        long nounce = 0;
        hash = Encoder.getSha256(hash + Long.toString(nounce));
        while(!hash.matches("^0000.*")){
            nounce++;
            hash = Encoder.getSha256(hash + Long.toString(nounce));
        }
    }

    /**
     *This method validate the hash of the {@code Block} object
     * @return true if the hash of this block begins with the specified number of zeros, false otherwise.
     */
    public boolean isValidHash() {
        return hash.matches("^0000.*");
    }

    /**
     *This method return the index of the {@code Block}.
     * @return a copy of the {@code Block} index in a {@code long}  integer.
     */
    public long getIndex(){
        return index;
    }

    /**
     * This method return the hash of the {@code Block}.
     * @return a {@code String} with the hash of the block expressed in hexadecimal.
     */
    public String getHash(){
        return hash;
    }

    /**
     *This method return the data of the {@code Block}.
     * @return a T object with the stored data in the {@code Block}.
     */
    public T getData(){
        return data;
    }

    /**
     *This method return the hash of the previous {@code Block}.
     * @return a {@code String} with the hash of the previous {@code Block}.
     */
    public String getPrevHash(){
        return prevHash;
    }

}