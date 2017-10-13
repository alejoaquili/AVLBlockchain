package Test;

import Model.AVLBlockchain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AVLBlockchainTest {
    // ATTENTION ALL TEST MUST BE RUN IN ORDER, DO NOT USE RUN ALL

    private static AVLBlockchain<Integer> chain;

    @BeforeAll
    public static void beforeAll(){
        try {
            chain = new AVLBlockchain<>(3, (Integer i1, Integer i2) -> (i1 - i2));

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Test
    public void initialVerifyTest(){
        assertTrue(chain.verify());
    }

    @Test
    public void initialLookUpTest(){
        List<Long> result = null;
        result  = chain.lookup(null);
        assertNull(result);
    }

    @Test
    public void elementNotAddedLookUpTest(){
        List<Long> result = null;
        result = chain.lookup(new Integer(1));
        assertNull(result);
    }

    @Test
    public void emptyAVLBlockchainModifyTest(){
        Exception ex = null;
        try {
            chain.modify(0);
        }catch(IndexOutOfBoundsException e){
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    public void addNullTest(){
        chain.add(null);
        assertTrue(chain.verify());
    }

    @Test
    public void addTest(){
        chain.add(new Integer(1));
        assertTrue(chain.verify());
    }

    @Test
    public void removeTest(){
        chain.add(new Integer(2));
        chain.remove(new Integer(2));
        assertTrue(chain.verify());
    }

    @Test
    public void lookUpTest(){
        List<Long> result = null;
        chain.add(1);
        result = chain.lookup(new Integer(1));
        assertNotNull(result);
    }

    @Test
    public void saveTest(){
        Exception ex = null;
        try {
            chain.save("src/Test/AVLBLockchainTest.txt");
        }catch(IOException e){
            ex = e;
        }
        assertNull(ex);
    }

    @Test
    public void modifyInvalidTest(){
        Exception e = null;
        try {
            chain.modify(0, "src/Test/file.txt");
        }catch(Exception ex){
            e = ex;
        }
        assertNotNull(e);
    }
}
