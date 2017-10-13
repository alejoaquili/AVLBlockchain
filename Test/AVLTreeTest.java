package Test;

import Model.DataStructures.AVL.AVLOperationData;
import Model.DataStructures.AVL.AVLTree;
import Model.Exceptions.InvalidAVLOperationDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

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
    public void applyTest1(){

        AVLOperationData<Integer> operation = null, result = null;
        Exception ex = null;
        try {
            result = tree.apply(operation);
        }catch(NullPointerException e){
            ex = e;
        }catch(InvalidAVLOperationDataException e){
            ex = e;
        }catch(IllegalArgumentException e){
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

    @Test
    public void inOrderPrintTest1() throws FileNotFoundException, IOException{
        FileOutputStream f = new FileOutputStream("src/Test/inOrderTest1.txt");
        System.setOut(new PrintStream(f));
        tree.insert(new Integer(2));
        tree.insert(new Integer(1));
        tree.insert(new Integer(3));
        tree.printInOrder();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        FileReader reader = new FileReader("src/Test/inOrderTest1.txt");
        BufferedReader lineReader = new BufferedReader(reader);
        String expected = "123";
        StringBuffer inOrder = new StringBuffer();
        for(int i = 0; i < 3; i++) {
            String s[] = lineReader.readLine().split("\\D+");
            inOrder.append(s[1]);
        }
        String result = inOrder.toString();
        assertEquals(expected, result);
    }

    @Test
    public void inOrderPrintTest2() throws FileNotFoundException, IOException{
        FileOutputStream f = new FileOutputStream("src/Test/inOrderTest2.txt");
        System.setOut(new PrintStream(f));
        tree.insert(new Integer(3));
        tree.insert(new Integer(2));
        tree.insert(new Integer(1));
        tree.printInOrder();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        FileReader reader = new FileReader("src/Test/inOrderTest2.txt");
        BufferedReader lineReader = new BufferedReader(reader);
        String expected = "123";
        StringBuilder inOrder = new StringBuilder();
        for(int i = 0; i < 3; i++) {
            String s[] = lineReader.readLine().split("\\D+");
            inOrder.append(s[1]);
        }
        String result = inOrder.toString();
        assertEquals(expected, result);
    }


}
