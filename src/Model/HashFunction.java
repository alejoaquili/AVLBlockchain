package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {

    private MessageDigest md;
    private static HashFunction encoder;

    private HashFunction(String algorithm) throws NoSuchAlgorithmException {
        if (algorithm == null) throw new IllegalArgumentException("Invalid algorithm");
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new NoSuchAlgorithmException();
        }
    }

    public static HashFunction getSingletonInstance(String algorithm) throws NoSuchAlgorithmException, CloneNotSupportedException {
        if(encoder == null) {
            encoder = new HashFunction(algorithm);
        }
        else throw new CloneNotSupportedException();
        return encoder;
    }

    public static HashFunction getSingletonInstance() {
        return encoder;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes)
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public String encode(String value) {
        try{
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }



    public static void main(String[] args) throws Exception {

        long magic = 0;
        String data = "hello";
        System.out.println("Data: " + data);

        HashFunction.getSingletonInstance("SHA-256");
        String hash = HashFunction.getSingletonInstance().encode(data + Long.toString(magic));

        long alg1 = System.currentTimeMillis();

        while(!hash.matches("^0000.*")) {
            magic++;
            hash = HashFunction.getSingletonInstance().encode(data + Long.toString(magic));
        }
        System.out.println("Hash: " + hash);
        System.out.println("Time: " + (System.currentTimeMillis() - alg1));

    }

}
