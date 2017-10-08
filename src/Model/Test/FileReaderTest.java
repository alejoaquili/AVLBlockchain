package Model.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

class FileReaderTest{

    private FileReader fr;

    @BeforeEach
    public void BeforeEach() throws IOException{
        fr = null;
        try{
            fr = new FileReader("./src/Model/Test/FileReaderTestData");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


    @Test
    void getLineTest() throws IOException {
        String actual = fr.getLine();
        assertEquals("My Text", actual);
    }

    @Test
    void fileReaderIteratorTest(){
        int count = 0;
        for(String each : fr)
            count ++;
        assertEquals(9, count);
    }

    @Test
    void feofTest() throws IOException {
        for (int i = 0 ; i < 9 ; i ++)
            fr.getLine();
        assertEquals(fr.feof(), true);
    }

    @Test
    void getNLineTest() throws IOException {
        String actual = null;
        try {
            actual = fr.getLine(-2);
        }catch (IndexOutOfBoundsException e){
            assertNull(actual);
        }
    }
}