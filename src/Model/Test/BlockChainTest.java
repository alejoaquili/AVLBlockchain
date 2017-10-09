package Model.Test;

import Model.DataStructures.Blockchain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockChainTest
{
    private static Blockchain<Integer> b;

    @BeforeAll
    public static void before(){
        try {
            b = new Blockchain<Integer>(3);
        }catch (NoSuchAlgorithmException e){
            e.getStackTrace();
        } catch (CloneNotSupportedException e){
            e.getStackTrace();
        }
    }

    @Test
    public void verifyWithoutAddingTest(){
        assertTrue(b.verify());
    }

    @Test
    public void addTest(){
        b.add(new Integer(1));
        assertTrue(b.verify());
    }

    @Test
    public void addMultipleNodeTest(){
        for(int i = 0; i < 10; i++){
            b.add(new Integer(i));
        }
        assertTrue(b.verify());
    }
}
