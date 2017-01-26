package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import documentStore.Document;
import documentStore.Opus;

/**
 * JUnit test class for the Opus class. Objective: 100% code coverage.
 * 
 * @author Sam Kiwus
 *
 */
public class OpusTest
{
  public static final String TITLE = "The Cat in the Hat";
  public static final String AUTHOR = "Dr. Seuss";
  public static final String TITLE2 = "Goosebumps";
  public static final String AUTHOR2 = "R. L. Stein";

  /**
   * Test method for Opus constructor.
   */
  @Test
  public void testOpus()
  {
    Opus opus = new Opus(TITLE, AUTHOR);
    assertNotNull(opus);

    Opus opus2 = new Opus(TITLE2, AUTHOR2);
    assertNotNull(opus2);

    assertNotSame(opus, opus2);

    opus2 = opus;
    assertSame(opus, opus2);
  }

  /**
   * Test method for add(), which adds documents to an Opus.
   */
  @Test
  public void testAdd()
  {
    Opus opus = new Opus(TITLE, AUTHOR);
    Document doc = new Document();
    doc.addLine("blah");
    opus.add(doc);
    opus.add(doc);
    assertEquals(2, opus.add(doc));
    assertEquals(3, opus.add(doc));
  }

  /**
   * Test method for the getters and setters of the docCounter attribute.
   */
  @Test
  public void testGetSetDocCounter()
  {
    Opus opus = new Opus(TITLE, AUTHOR);
    Document doc = new Document();
    opus.setDocCounter(opus.add(doc) + 1);
    assertEquals(1, opus.getDocCounter());

    opus.setDocCounter(opus.add(doc) + 1);
    assertEquals(2, opus.getDocCounter());

    opus.setDocCounter(opus.add(doc) + 1);
    assertEquals(3, opus.getDocCounter());
  }

  /**
   * Test method for the getters and setters of the Title attribute.
   */
  @Test
  public void testGetSetTitle()
  {
    Opus opus = new Opus(TITLE, AUTHOR);
    assertEquals(TITLE, opus.getTitle());
    String newTitle = "New Title!";
    opus.setTitle(newTitle);
    assertEquals(newTitle, opus.getTitle());
    opus.setTitle(null);
    assertEquals(newTitle, opus.getTitle()); // Should ignore null values
  }

  /**
   * Test method for the getters and setters of the Author attribute.
   */
  @Test
  public void testGetSetAuthor()
  {
    Opus opus = new Opus(TITLE, AUTHOR);
    assertEquals(AUTHOR, opus.getAuthor());
    String newAuthor = "New Author!";
    opus.setAuthor(newAuthor);
    assertEquals(newAuthor, opus.getAuthor());
    opus.setAuthor(null);
    assertEquals(newAuthor, opus.getAuthor()); // Should ignore null values
  }

  /**
   * Test method for the retrieval of Documents from an Opus.
   */
  @Test
  public void testGetDocument()
  {
    Opus opus = new Opus(TITLE, AUTHOR);

    Document doc = new Document();
    doc.addLine("blah");
    opus.add(doc);

    Document doc2 = new Document();
    doc.addLine("yadda yadda");
    opus.add(doc2);

    assertEquals(doc, opus.getDocument(0));
    assertEquals(doc2, opus.getDocument(1));

    assertNull(opus.getDocument(-5)); // Invalid parameters should return null.
    assertNull(opus.getDocument(2));
  }

  /**
   * Test method for the indexOf(Document) method, which should return the index of a requested
   * Document object in an Opus.
   */
  @Test
  public void testIndexOf()
  {
    Opus opus = new Opus(TITLE, AUTHOR);

    Document doc = new Document();
    doc.addLine("blah");
    opus.add(doc);

    Document doc2 = new Document();
    doc.addLine("yadda yadda");
    opus.add(doc2);

    assertEquals(0, opus.indexOf(doc));
    assertEquals(1, opus.indexOf(doc2));

    assertEquals(-1, opus.indexOf(null)); // Invalid parameter should return -1.
  }

  /**
   * Test method for removing a Document from an Opus.
   */
  @Test
  public void testRemove()
  {
    Opus opus = new Opus(TITLE, AUTHOR);

    Document doc = new Document();
    doc.addLine("blah");
    opus.add(doc);

    Document doc2 = new Document();
    doc.addLine("yadda yadda");

    assertEquals(0, opus.indexOf(doc));
    assertEquals(-1, opus.indexOf(doc2));

    opus.remove(doc);
    opus.remove(doc2);

    assertEquals(-1, opus.indexOf(doc));
    assertEquals(-1, opus.indexOf(doc2)); // Both docs should not be in the Opus.
    assertNull(opus.getDocument(0)); // No documents, so should return null.
  }

}
