/**
 * This class representes the nodes of a @code{Model.Blockchain}
 * @param <T> The parameter is a generic for the type of the stored data.
 */
public class Block <T>{

    private long index;// index of the current block
    private T data;
    private long nounce;// the magic number which keeps the hash below a certain number
    private String prevHash;// previous block hash;
    private String hash;

    /**
     *
     * @param index index of this node on the blockchain
     * @param data infortation to store in this block
     * @param prevHash the hash in ecode SHA-256 of the previous block
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
     * Finds the nounce that valid the hash of this block
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
     * Allow to know if the hash of this block begins whit 0000*
     * @return true if the hash of this block begins whit 0000*
     */
    public boolean isValidHash() {
        return hash.matches("^0000.*");
    }

    public long getIndex(){
        return index;
    }

    public String getHash(){
        return hash;
    }

    public T getData(){
        return data;
    }

    public String getPrevHash(){
        return prevHash;
    }

}
