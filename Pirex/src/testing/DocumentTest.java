package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import documentStore.Document;

/**
 * JUnit test class for the Document class. Objective: 100% code coverage.
 * 
 * @author Sam Kiwus
 *
 */
public class DocumentTest
{

  /**
   * Test method for default constructor.
   */
  @Test
  public void testDocumentConstructors()
  {
    Document doc = new Document();
    assertNotNull(doc);
    assertNotNull(doc.getLines());

    Document doc2 = new Document();
    assertNotNull(doc2);
    assertNotNull(doc2.getLines());
    assertNotSame(doc, doc2);

    doc2 = doc;
    assertSame(doc, doc2);
  }

  /**
   * Test method for addLine(String).
   */
  @Test
  public void testAddLine()
  {
    Document doc = new Document();
    doc.addLine("The mitochondria is the powerhouse of the cell");

    List<String> out = new ArrayList<String>();
    out = doc.getLines();

    assertEquals(out.size(), 1); // Should be a single line
    doc.addLine("Ooh");
    doc.addLine("Eeh");
    doc.addLine("Ooh");
    doc.addLine("Ah-ah");
    assertEquals(out.size(), 5); // Should be five lines
    doc.addLine("Ting tang, walla-walla-bing-bang");
    assertEquals(out.size(), 6); // Should be six lines
    doc.addLine(null);
    assertEquals(out.size(), 6);// Should not add null lines
  }

  /**
   * Test method for getShortForm().
   */
  @Test
  public void testGetShortForm()
  {
    Document doc = new Document();
    assertEquals("", doc.getShortForm());

    String s = "Somebody once told me";
    doc.addLine(s);

    assertEquals(s, doc.getShortForm());
  }

  /**
   * Test method for toString().
   */
  @Test
  public void testToString()
  {
    Document doc = new Document();
    assertEquals("", doc.toString());
    doc.addLine("bingo");
    assertEquals("bingo\n", doc.toString()); // Document appends \n to each line
  }

}
