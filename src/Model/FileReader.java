package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * The {@code FileReader} class represents a reader Object for files.
 */
public class FileReader implements Iterable<String>  {
    private String path;
    private RandomAccessFile fileReader;

    /**
     * Creates a {@code FileReader} with the specified path for the file.
     * @param path The path of the file.
     * @throws FileNotFoundException if the specified path is empty.
     */
     public FileReader(String path) throws FileNotFoundException {
        if(path == null) throw  new IllegalArgumentException("The path argument is null");
        this.path = path;
        this.fileReader = new RandomAccessFile(path, "r");
     }

    /**
     * Returns a {@code String} with the data read from the line where file reader is pointing to.
     * @return a new {@code String} with the data read from the line where file reader is pointing to.
     * @throws IOException if an I/O error occurs.
     */
     public String getLine() throws IOException {
        if(!feof()){
            String line = fileReader.readLine();
            return line;
        }
        throw new RuntimeException("There are not more lines to read");
     }

    /**
     * Returns a {@code String} with the data read from the specified line of the file.
     * The line is counted from the beginning of the file, number zero stands for the first line.
     * @param lineNumber the number of the line of the file.
     * @return a new {@code String} with the data read from the specified line.
     * @throws IOException if an I/O error occurs.
     */
     public String getLine(long lineNumber) throws IOException {
       if(lineNumber < 0) throw  new IndexOutOfBoundsException();
        while(--lineNumber != 0) {
            fileReader.readLine();
        }
        if(!feof()){
            String line = fileReader.readLine();
            resetFilePointer();
            return line;
        }
        resetFilePointer();
        throw new RuntimeException("There are not more lines to read");
     }

    /**
     * Puts the file reader pointer to the beginning of the file.
     * @throws IOException if the pos parameter in seek method is less than 0 or if an I/O error occurs.
     */
    public void resetFilePointer() throws IOException {
        fileReader.seek(0L);
    }

    /**
     * Returns false if there are more lines to read. Otherwise return true.
     * @return a boolean that represent if there are more lines to read (true) or not (false).
     * @throws IOException if an I/O error occurs.
     */
     public boolean feof() throws IOException {
        boolean isEof = true;
        long lastPointer = fileReader.getFilePointer();

        if(fileReader.readLine() != null)
            isEof = false;

        fileReader.seek(lastPointer);

        return isEof;
     }

    /**
     * Returns a {@code FileReader} Custom Iterator, allows a {@code FileReader} Object to be the target of
     * the "for-each loop" statement.
     * @return an {@code Iterator<String>}.
     */
    @Override
    public Iterator<String> iterator() {
        return new FileReaderIterator();
    }

    class FileReaderIterator implements Iterator<String> {
        protected FileReaderIterator() {
            try {
                resetFilePointer();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        @Override
        public boolean hasNext() {
            try {
                boolean hasNext = !feof();
                if(!hasNext)
                    resetFilePointer();
                return hasNext;
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        @Override
        public String next() {
            try {
                return getLine();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }


    //cosas para borrar

    public static void  main(String[] args) throws IOException {

        FileReader fr = new FileReader("/Users/alejoaquili/IdeaProjects/Blockchain/src/Model/FileReaderTest");
        for(String each : fr){
            System.out.println(each);
        }
        System.out.println(fr.getLine(8));
    }
}
