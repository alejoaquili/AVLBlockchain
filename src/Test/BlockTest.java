package Test;

import Model.DataStructures.Blockchain.Block;
import Model.DataStructures.Blockchain.Blockchain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {

    private static Blockchain<Integer> chain;
    private static Block<Integer> b;

    @BeforeAll
    public static void beforeAll() throws NoSuchAlgorithmException, CloneNotSupportedException{
        chain = new Blockchain<>(3);
        b = new Block(0,1,"0000000000000000000000000000000", "^000.*");
    }

    @Test
    public void blockExistanceTest(){
        assertNotNull(b);
    }

    @Test
    public void asdTest(){
        assertFalse(b.isValidHash());
    }

    @Test
    public void miningTest(){
        boolean initial = b.isValidHash();
        b.mine();
        assertTrue(!initial && b.isValidHash());
    }

}
