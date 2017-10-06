package Model.DataStructures;

/**
 * This class represents the nodes of a {@code Blockchain}
 * @param <S> The parameter is a generic for the  type of data stored.
 */
public class Block < S extends BlockDataInterface> {

    private long index;
    private S data;
    private long nounce;
    private String prevHash;
    private String hash;
    private String zeros;

    /**
     *Creates a {@code Block} object for a {@code Blockchain}.
     * @param index index of this node on the Blockchain.
     * @param data information to store in this Block.
     * @param prevHash the hash encoded in SHA-256 of the previous Block.
     */
    public Block(long index, S data, String prevHash, String zeros){
        if(index < 0 ) throw new IllegalArgumentException("index were incorrect");
        if(prevHash == null ) throw new IllegalArgumentException("previous hash were incorrect");
        if(zeros == null) throw new IllegalArgumentException("zeros were incorrect");
        this.data = data;
        this.index = index;
        this.prevHash = prevHash;
        this.zeros = zeros;
        nounce = 0;
        hash = HashFunction.getSingletonInstance().encode(Long.toString(index) + data.toString() + prevHash);
    }

    /**
     * This method figured out the nounce number that valid the hash of this Block.
     */
    public void mine() {
        nounce = 0;
        String word = Long.toString(index) + data.toString() + prevHash;
        hash = HashFunction.getSingletonInstance().encode(word + Long.toString(nounce));
        while(!hash.matches(this.zeros)){
            nounce++;
            hash = HashFunction.getSingletonInstance().encode(word + Long.toString(nounce));
        }
    }

    /**
     *This method validate the hash of the {@code Block} object.
     * @return true if the hash of this block begins with the specified number of zeros, false otherwise.
     */
    public boolean isValidHash() {
        String word = Long.toString(index) + data.toString() + prevHash;
        hash = HashFunction.getSingletonInstance().encode(word + Long.toString(nounce));
        return hash.matches(this.zeros);
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
     *This method return the data generic object of the {@code Block}.
     * @return a object with the stored data in the {@code Block}.
     */
    public S getData(){
        return data;
    }

    /**
     *This method return the hash of the previous {@code Block}.
     * @return a {@code String} with the hash of the previous {@code Block}.
     */
    public String getPrevHash(){
        return prevHash;
    }

    /**
     * This method return true if the data (which is a Object that implements {@code BlockDataInterface})
     * of the {@code Block} contains element.
     * @param element an {@code Object} to verify
     * @return a boolean to evaluate if the element is contained.
     */
    public boolean contains(Object element){
        return this.data.contains(element);
    }
}