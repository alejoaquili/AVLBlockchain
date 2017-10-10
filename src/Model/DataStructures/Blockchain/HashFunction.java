package Model.DataStructures.Blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  This class represents a singleton object for a hash function.
 */
public class HashFunction {
    private MessageDigest md;
    private static HashFunction encoder;

    /**
     * Returns a new {@code HashFunction} object.
     * @param algorithm a {@code String} with the specified algorithm for the hashing.
     * @throws NoSuchAlgorithmException if the specified algorithm is invalid.
     */
    private HashFunction(String algorithm) throws NoSuchAlgorithmException {
        if (algorithm == null) throw new IllegalArgumentException("Invalid algorithm.");
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new NoSuchAlgorithmException();
        }
    }

    /**
     * Creates the singleton {@code HashFunction} object.
     * @param algorithm the specified algorithm used for the coding.
     * @return a reference of the singleton instance of the {@code HashFunction} object.
     * @throws NoSuchAlgorithmException if the specified algorithm were invalid.
     */
    public static HashFunction getSingletonInstance(String algorithm) throws NoSuchAlgorithmException{
        if(encoder == null) {
            encoder = new HashFunction(algorithm);
        }
        return encoder;
    }

    /**
     * Returns the singleton instance.
     * @return the reference of singleton {@code HashFunction} object.
     */
    public static HashFunction getSingletonInstance() {
        return encoder;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes)
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    /**
     * This method provided the hash code for a specified value.
     * @param value a {@code String} with specified value.
     * @return a {@code String} with the encoded value.
     */
    public String encode(String value) {
        try{
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
