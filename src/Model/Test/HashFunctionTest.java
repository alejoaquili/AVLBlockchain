package Model.Test;

import static org.junit.jupiter.api.Assertions.*;
import Model.DataStructures.HashFunction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;


public class HashFunctionTest {

    private static HashFunction hf;
    private static String data, hash;
    private static long nounce;

    @BeforeAll
    public static void BeforeAll() throws NoSuchAlgorithmException, CloneNotSupportedException{
        data = "This is a test!";
        nounce = 0;
        try {
            hf = HashFunction.getSingletonInstance("MD5");
        }catch (NoSuchAlgorithmException e){
            e.getStackTrace();
        }finally{
            hash = hf.encode(data + Long.toString(nounce));
        }
    }

    @Test
    public void existencyTest()throws NoSuchAlgorithmException{
        System.out.println(hf);
        assertNotNull(hf);
    }

    @Test
    public void hashTest(){
        assertNotEquals(data, hf.encode(data));
    }


    //We consider that if mining 3 Zeros with MD5 takes more than 1000ms, there is something wrong with the hash
    @Test
    public void dehashingTest(){
        assertTimeout(Duration.ofMillis(1000), () -> {
            while(!hash.matches("^000.*")) {
                nounce++;
                hash = HashFunction.getSingletonInstance().encode(data + Long.toString(nounce));
            }
        });
    }


}
