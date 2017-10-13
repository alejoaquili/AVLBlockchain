package Test;

import Model.DataStructures.Blockchain.Blockchain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class BlockChainTest
{
    // ATTENTION ALL TEST MUST BE RUN IN ORDER, DO NOT USE RUN ALL

    private static Blockchain<Integer> b;

    @BeforeAll
    public static void beforeall(){
        try {
            b = new Blockchain<Integer>(3);
        }catch (NoSuchAlgorithmException e){
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


    @Test
    public void reHashTest(){
        assertTrue(b.reHashNounce());
    }

    @Test
    public void setTest(){
        for(int i = 0; i < 3; i++){
            b.add(new Integer(i));
        }
        b.setBlock(0, new Integer(9));
        assertFalse(b.verify());
    }

    @Test
    public void iteratorExistanceTest(){
        Iterator<Integer> it;
        it = b.iterator();
        assertNotNull(it);
    }

}
