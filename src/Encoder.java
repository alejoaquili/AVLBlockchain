import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

    public static String getSha256(String value) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes)
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public static byte[] getSha256(byte[] value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value);
            return md.digest();
        } catch(NoSuchAlgorithmException ex){
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("wokring");
        long magic = 0;
        String ex = "hello";
        ex = getSha256(ex);
        ex = getSha256(ex+magic);

        while(!ex.matches("^0000.*")){
            magic++;
            ex = getSha256(ex+Long.toString(magic));
        }
        System.out.println(ex);

        long magic2 = 0;
        byte[] ex2 = new byte[32];
        ex2[3] = 4;
        ex2 = getSha256(ex2);
        ex = getSha256(ex+magic);

        while(!ex.matches("^0000.*")){
            magic++;
            //ex = getSha256(ex2+magic);
        }
        System.out.println(ex);
}

}
