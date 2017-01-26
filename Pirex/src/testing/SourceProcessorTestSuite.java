package testing;

import static org.junit.Assert.*;
import indexer.InvertedIndex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import documentStore.DocumentStore;
import sourceProcessor.SourceProcessor;


public class SourceProcessorTestSuite
{

  /**
   * Tests SourceProcessor process method for invalid file input string
   * @throws FileNotFoundException
   * @throws IOException
   */
  @Test (expected = FileNotFoundException.class)
  public void testForInvalidFile() throws FileNotFoundException, IOException
  {
    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    String actual = "File";
    
    sp.process(actual);
  }
  
  /**
   * Test SourceProcessor process method for valid file input string
   * @throws FileNotFoundException
   * @throws IOException
   */
  @Test
  public void testForValidFile() throws FileNotFoundException, IOException
  {
    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    String actual = "/Users/andrew/Desktop/Alice's_Adventures_in_Wonderland.txt";
    
    sp.process(actual);
  }
  
  
  /**
   * Tests the SourceProcessof readUntilNonBlank method with a normal txt file.
   * @throws IOException
   */
  @Test
  public void testReadUntilNonBlankVALID() throws IOException
  {
    String file = "/Users/andrew/Desktop/Alice's_Adventures_in_Wonderland.txt";
    
    BufferedReader br = new BufferedReader(new FileReader(file));

    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    
    sp.readUntilNonBlank(br);
  }
  

  

  
  /**
   * Tests SourceProcessor readUntilNonBlank method with an invalid file path
   * @throws IOException
   */
  @Test(expected = IOException.class)
  public void testReadUntilNonBlankINVALID() throws IOException
  {
    String test = "";
    BufferedReader br = new BufferedReader(new FileReader(test));

    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    
    sp.readUntilNonBlank(br);
  }
  
  
  /**
   * Tests SourceProcessor getAuthorFromFile method with an invalid file path
   * @throws IOException
   */
  @Test(expected = IOException.class)
  public void testGetAuthorFromFileINVLAID() throws IOException
  {
    String test = "";

    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    
    sp.getAuthorFromFile(test);
  }
  
  /**
   * Tests SourceProcessor getTitleFromFile method with an invalid file path
   * @throws IOException
   */
  @Test(expected = FileNotFoundException.class)
  public void testGetTitleFromFileINVLAID() throws IOException
  {
    String test = "";

    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    
    sp.getTitleFromFile(test);
  }
  
  /**
   * Tests SourceProcessor getIV method
   * @throws FileNotFoundException
   * @throws IOException
   */
  @Test
  public void testGetIV() throws FileNotFoundException, IOException
  {
    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    
    SourceProcessor sp2 = new SourceProcessor(iv, ds);
    assertEquals(sp2.getIV(), sp.getIV());
  }
  
  /**
   * Tests SourceProcessor getFileList method
   * @throws FileNotFoundException
   * @throws IOException
   */
  @Test
  public void testGetFileList() throws FileNotFoundException, IOException
  {
    List<String> test = new ArrayList<String>();
    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    assertEquals(test, sp.getFileList());
  }
  
  /**
   * Tests SourceProcessor getDs method
   * @throws FileNotFoundException
   * @throws IOException
   */
  @Test
  public void testGetDs() throws FileNotFoundException, IOException
  {
    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    
    assertEquals(ds, sp.getDs());
  }
}
