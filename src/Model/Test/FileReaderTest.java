package Model.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Model.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

class FileReaderTest{

    @Test
    void getLineTest() throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader("./src/Model/Test/FileReaderTestData");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            String actual = fr.getLine();
            assertEquals("My Text", actual);
        }
    }

    @Test
    void fileReaderIteratorTest(){
        FileReader fr = null;
        try {
            fr = new FileReader("./src/Model/Test/FileReaderTestData");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            int count = 0;
            for(String each : fr)
                count ++;
            assertEquals(9, count);
        }
    }

    @Test
    void feofTest() throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader("./src/Model/Test/FileReaderTestData");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            for (int i = 0 ; i < 9 ; i ++)
                fr.getLine();
            assertEquals(fr.feof(), true);
        }
    }

    @Test
    void getNLineTest() throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader("./src/Model/Test/FileReaderTestData");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            String actual = null;
            try {
                actual = fr.getLine(-2);
            }
            catch (IndexOutOfBoundsException e){
                assertEquals(actual, null);
            }

        }
    }
}