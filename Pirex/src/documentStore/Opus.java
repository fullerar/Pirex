package documentStore;

import java.io.Serializable;
import java.util.*;

/**
 * An encapsulation of an Opus (i.e., a Collection of paragraphs) in Pirex.
 * 
 * Note: This implementation is untested, is not robust, and may be incomplete.
 * 
 * @author Scrum Master
 */
public class Opus implements Serializable
{
  private String title, author;
  private List<Document> documents;
  private int docCounter;
  private static final long serialVersionUID = 1L;

  /**
   * Explicit Value Constructor.
   * 
   * @param title
   *          The title of the Opus.
   * @param author
   *          The author of the Opus.
   */
  public Opus(String title, String author)
  {
    this.title = title;
    this.author = author;

    documents = new LinkedList<Document>();
  }

  /**
   * Getter for the docCounter attribute of an Opus, used by the source processor.
   * 
   * @return the number of documents in the Opus
   */
  public int getDocCounter()
  {
    return docCounter;
  }

  /**
   * Setter for the docCounter attribute of an Opus, used by the source processor.
   * 
   * @param docCounter
   *          the new number to change the docCounter to.
   */
  public void setDocCounter(int docCounter)
  {
    this.docCounter = docCounter;
  }

  /**
   * Add a Document to this Opus.
   * 
   * @param document
   *          The Document to add
   * @return The ordinal number of the Document
   */
  public int add(Document document)
  {
    documents.add(document);
    return documents.size() - 1;
  }

  /**
   * Get the author of this Opus.
   * 
   * @return The author
   */
  public String getAuthor()
  {
    return author;
  }

  /**
   * Sets the author attribute of an Opus object. If the author field is null, doesn't change the
   * author.
   * 
   * @param author
   *          The new author to add
   */
  public void setAuthor(String author)
  {
    if (author != null)
      this.author = author;
  }

  /**
   * Get a particular Document. If the index is invalid (outside the bounds of the docs list, or
   * negative) returns null.
   * 
   * @param index
   *          The ordinal number of the Document
   * @return The Document of interest
   */
  public Document getDocument(int index)
  {
    if (index >= 0 && index < documents.size())
      return documents.get(index);
    else
      return null;
  }

  /**
   * Get the title of this Opus.
   * 
   * @return The title
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Sets the title attribute of an Opus object. If new String is null, doesn't change the title.
   * 
   * @param title
   *          The new title to add
   */
  public void setTitle(String title)
  {
    if (title != null)
      this.title = title;
  }

  /**
   * Get the ordinal number of a particular Document. Returns -1 if the Document is not contained in
   * this Opus.
   * 
   * @param document
   *          The Document of interest
   * @return The ordinal number of the Document
   */
  public int indexOf(Document document)
  {
    if (document != null)
      return documents.indexOf(document);
    else
      return -1;
  }

  /**
   * Remove a Document from this Opus. If Document is not in this Opus, does nothing.
   * 
   * @param document
   *          The Document to remove
   */
  public void remove(Document document)
  {
    if (documents.contains(document))
      documents.remove(document);
  }
}
