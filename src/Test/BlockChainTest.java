package Test;

import Model.DataStructures.Blockchain.Blockchain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class BlockChainTest
{
    private static Blockchain<Integer> b;

    @BeforeAll
    public static void beforeall(){
        try {
            b = new Blockchain<Integer>(3);
        }catch (NoSuchAlgorithmException e){
            e.getStackTrace();
        }
    }

    /*@Test
    public void singleBlockchainTest() throws NoSuchAlgorithmException{
        Blockchain<Integer> b2;
        Exception ex = null;
        try{
            b2 = new Blockchain<>(3);
        }catch (CloneNotSupportedException e){
            ex = e;
        }
        assertNotNull(ex);
    }*/

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

    @Test
    public void iteratorTest(){
        Object array[] = new Object[18];
        for(int i = 0 ; i < 3; i++){
            b.add(new Integer(i));
        }
        Iterator<Integer> it = b.iterator();
        int j = 0;
        while(it.hasNext()){
            array[j++] = it.next();
        }
        assertTrue(array[17] != null && array[16] != null &&
        array[15] != null );
    }

    @Test
    public void saveTest(){
        Exception ex = null;
        try{
            b.saveFile("src/Model/ads.txt");
        }catch(IOException e) {
            ex = e;
        }
        assertNull(ex);
    }

    @Test
    public void openFileTest(){
        boolean bool = false;
        Exception ex = null;
        try{
            bool = b.readFile("src/Model/ads.txt");
        }catch (IOException e){
            ex = e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(ex == null && bool == true);
    }


}
