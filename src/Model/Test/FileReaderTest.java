package Model.Test;

import Model.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    @org.junit.jupiter.api.Test
    void getLine() throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader("Model/Test/FileReaderTest");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            String read = fr.getLine();
            String expected = "My Text";
            assertEquals(expected, read, "getLine Test OK");
        }
    }

    @org.junit.jupiter.api.Test
    void getLine1() {
    }

    @org.junit.jupiter.api.Test
    void resetFilePointer() {
    }

    @org.junit.jupiter.api.Test
    void feof() {
    }

    @org.junit.jupiter.api.Test
    void iterator() {
    }

}