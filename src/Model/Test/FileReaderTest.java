package Model.Test;

import Model.FileReader;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class FileReaderTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getLine() throws Exception {

        String testFileName = "Model/Test/FileReaderTest";
        String expected = "My Test";
        FileReader searchPhrase = new FileReader(testFileName);
        String result = searchPhrase.getLine();
        assertEquals(expected, result);
    }
    @Test
    public void getLine1() throws Exception {
    }

    @Test
    public void resetFilePointer() throws Exception {
    }

    @Test
    public void feof() throws Exception {
    }

    @Test
    public void iterator() throws Exception {

    }
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Model.FileReader.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
