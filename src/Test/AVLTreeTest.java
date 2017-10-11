package Test;

import Model.DataStructures.AVL.AVLOperationData;
import Model.DataStructures.AVL.AVLTree;
import Model.Exceptions.InvalidAVLOperationDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by lautaro on 11/10/17.
 */
public class AVLTreeTest {

    private AVLTree tree;

    @BeforeEach
    public void before(){
        tree = new AVLTree((Object o1, Object o2)->((Integer)o1- (Integer)o2));
    }

    @Test
    public void insertTest(){
        Object o = null;
        o = tree.insert(new Integer(3));
        assertNotNull(o);
    }

    @Test
    public void searchTest(){
        AVLOperationData<Integer> operation = null;
        tree.insert(new Integer(1));
        operation = tree.search(new Integer(1));
        assertEquals(new Integer(1), operation.getElement());
    }

    @Test
    public void removeTest(){
        AVLOperationData<Integer> operation = null;
        Object o = null;
        tree.insert(new Integer(1));
        tree.remove(new Integer(1));
        operation = tree.remove(new Integer(1));
        o = tree.search(new Integer(1));
        assertTrue(new Integer(1).equals(operation.getElement()) && o != null);
    }

    @Test
    public void inOrderPrintTest(){
        String expected = "123";
        tree.insert(new Integer(2));
        tree.insert(new Integer(1));
        tree.insert(new Integer(3));
    }

    @Test
    public void applyTest1(){
        AVLOperationData<Integer> operation = null, result = null;
        Exception ex = null;
        try {
            result = tree.apply(operation);
        }catch(Exception e){ //cambiar
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    public void applyTest2() throws InvalidAVLOperationDataException{
        AVLOperationData<Integer> operation = new AVLOperationData<>();
        operation.setOperation("Added");
        AVLOperationData<Integer> result = null;
        result = tree.apply(operation);
        assertNotNull(result);
    }


}
