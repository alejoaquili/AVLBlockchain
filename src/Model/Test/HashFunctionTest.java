package Model.Test;

import static org.junit.jupiter.api.Assertions.*;
import Model.DataStructures.HashFunction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;


public class HashFunctionTest {

    private static HashFunction hf;
    private static String data;

    @BeforeAll
    public static void BeforeAll() throws NoSuchAlgorithmException, CloneNotSupportedException{
        data = "This is a test!";
        try {
            hf = HashFunction.getSingletonInstance("MD5");
        }catch (NoSuchAlgorithmException e){
            e.getStackTrace();
        }catch (CloneNotSupportedException e){
            e.getStackTrace();
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


}
