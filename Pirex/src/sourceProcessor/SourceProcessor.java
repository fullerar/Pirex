package sourceProcessor;

import indexer.Indexer;
import indexer.InvertedIndex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import documentStore.Document;
import documentStore.DocumentStore;
import documentStore.Opus;

/**
 * 
 * @author Andrew Fuller
 * 
 */
public class SourceProcessor implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  //to keep track of file paths
  private List<String> fileList = new ArrayList<String>();

  private int indexCounter = 0;     //individual term count 
  private int totalDocCounter = 0;  //total doc count
  private int totalIndexCounter = 0;//total term count
  private int totalPostings = 0;    //total postings count
  private Opus opus;
  private InvertedIndex iv;
  private DocumentStore ds;

  int counter = 0;

  /**
   * Creates SourceProcessor object used to process .txt files.
   * 
   * @param iv
   *          - the incoming InvertedIndex object
   * @param ds
   *          - the incoming DocumentStore object
   * @throws FileNotFoundException
   *           - possible exception
   * @throws IOException
   *           - possible exception
   */
  public SourceProcessor(InvertedIndex iv, DocumentStore ds) throws FileNotFoundException,
      IOException
  {
    this.iv = iv;
    this.ds = ds;
  }

  /**
   * Processes a .txt file, creating an Opus object, creating Document objects for each paragraph,
   * and creating Postings for each term.
   * 
   * @param file
   *          - String that represents the path to the .txt file
   * @return - the populated DocumentStore object
   * @throws IOException
   *           - possible exception
   */
  public DocumentStore process(String file) throws IOException
  {
    fileList.add(file); // keep track of file paths for each .txt file (opus)

    BufferedReader br = new BufferedReader(new FileReader(file)); // create buffered reader object

    String line;      // line holder
    Document docTemp; // temporary Document object
    
    //create a new opus and get the title and author information
    opus = new Opus(getTitleFromFile(file), getAuthorFromFile(file));
    
    //add opus to document store
    int opNum = ds.add(opus);

    Document d; // main Document object

    while (((line = br.readLine()) != null) && !line.startsWith("***")); // read until *** line

    while (((line = br.readLine()) != null) && !line.startsWith("*** END")) // start keeping track
                                                                            // of each line until *** END line
    {
      if (line.equals(""))            // if current line is blank
      {
        line = readUntilNonBlank(br); // read until populated line
        d = new Document();           // create new document
        d.addLine(line);              // add the populated line to the document
      }
      else                            // if current line is populated
      {
        d = new Document();           // create new document
        d.addLine(line);              // add current line to document
      }

      docTemp = readUntilBlank(br, d); // read populated lines till blank 
      int docNum = opus.add(docTemp);  // add document to the opus

      indexDoc(opus, opNum, docNum);   // create postings for each line of the document

      opus.setDocCounter(opus.getDocCounter() + 1); // increment Document counter
    }

    //increment total index counter
    int o = add(getIndexCounter(), getTotalIndexCounter());
    setTotalIndexCounter(o);

    //increment total document counter
    int oo = add(opus.getDocCounter(), getTotalDocCounter());
    setTotalDocCounter(oo);

    //increment total postings counter
    int ooo = add(iv.getPostingsHM().size(), getTotalPostings());
    setTotalPostings(ooo);
    
    br.close(); //close buffered reader
    return ds;
  }

  /**
   * Helper method to add each word to inverted index.
   * 
   * @param o
   *          - the incoming opus containing documents that need to be parsed
   * @param opusNum
   *          - the opus number that the InvertedIndex needs for adding an entry
   * @param docNum
   *          - the document number that the InvertedIndex needs for adding an entry
   */
  public void indexDoc(Opus o, int opusNum, int docNum)
  {
    Document d;
    List<String> lines;
    String[] indexedStrings;

    d = o.getDocument(docNum); // the document

    lines = d.getLines();      // get the List<String> of the document

    for (String s : lines)     // for each line
    {
      indexedStrings = Indexer.createIndexTerms(s); // split each string into a String[]
      for (String x : indexedStrings)               // for each new string (word)
      {
        iv.addEntry(x, opusNum, docNum);        // create a posting
        setIndexCounter(getIndexCounter() + 1); // increment invIndex counter
      }
    }
  }

  /**
   * The purpose of this method is to read through "" lines until the reader comes to a populated
   * line. Keeps track of last line read, aka the populated line.
   * 
   * @param br
   *          - the BufferedReader object that has the file to read
   * @return - the string that breaks the while loop, needs to be added to a document object
   * @throws IOException
   *           - possible IO exception
   */
  public String readUntilNonBlank(BufferedReader br) throws IOException
  {
    String result;

    while ((result = br.readLine()) != null && result.equals(""));

    return result;
  }

  /**
   * The purpose of this method is to read through populated lines until the reader comes to a ""
   * line. If line is not "", add it to the current document.
   * 
   * @param br
   *          - the BufferedReader object that has the file to read
   * @param d
   *          - the document that takes in each populated line
   * @return - the Document object that contains the populated lines
   * @throws IOException
   *           - possible IO exception
   */
  public Document readUntilBlank(BufferedReader br, Document d) throws IOException
  {
    String result;

    while ((result = br.readLine()) != null && !result.equals(""))
    {
      d.addLine(result);
    }
    return d;
  }

  /**
   * Helper method to reduce code duplication.
   * 
   * @param x
   *          - first integer
   * @param y
   *          - second integer
   * @return - the sum of x and y
   */
  public int add(int x, int y)
  {
    int result = 0;
    result = x + y;
    return result;
  }

  /**
   * Helper method to read through the .txt file and find the line that starts with "Author: ".
   * 
   * @param file
   *          - the file to read through
   * @return - the string that contains the authors name
   * @throws IOException
   *           - possible IO exception
   */
  public static String getAuthorFromFile(String file) throws IOException
  {
    String author = "Author: ";

    BufferedReader br = new BufferedReader(new FileReader(file));

    Scanner scanner = new Scanner(br);

    while (scanner.hasNextLine())
    {
      String line = scanner.nextLine();
      if (line.startsWith("Author: "))
        author = line.substring(author.length());
    }
    scanner.close();
    br.close();
    return author;
  }

  /**
   * Helper method to read through the .txt file and find the line that starts with "Title: ".
   * 
   * @param file
   *          - the file to read through
   * @return - the string that contains the title
   * @throws IOException
   *           - possible IO exception
   */
  public static String getTitleFromFile(String file) throws IOException
  {
    String title = "Title: ";

    BufferedReader br = new BufferedReader(new FileReader(file));

    Scanner scanner = new Scanner(br);
    while (scanner.hasNextLine())
    {
      String line = scanner.nextLine();
      if (line.startsWith("Title: "))
        title = line.substring(title.length());
    }
    scanner.close();
    br.close();
    return title;
  }

  /**
   * Helper method to get the InvertedIndex object.
   * 
   * @return - the InvertedIndex
   */
  public InvertedIndex getIV()
  {
    return iv;
  }

  /**
   * Helper method to get the Opus object.
   * 
   * @return - the Opus
   */
  public Opus getOpus()
  {
    return opus;
  }

  /**
   * Helper method to get the InvertedIndex counter.
   * 
   * @return - the InvertedIndex counter
   */
  public int getIndexCounter()
  {
    return indexCounter;
  }

  /**
   * Helper method to set the InvertedIndex counter.
   * 
   * @param indexCounter
   *          - the new InvertedIndex count
   */
  public void setIndexCounter(int indexCounter)
  {
    this.indexCounter = indexCounter;
  }

  /**
   * Helper method to get the DocumentStore object.
   * 
   * @return - the DocumentStore
   */
  public DocumentStore getDs()
  {
    return ds;
  }

  /**
   * Helper method to get the Total Documents counter.
   * 
   * @return - the total document count
   */
  public int getTotalDocCounter()
  {
    return totalDocCounter;
  }

  /**
   * Helper method to set the Total Documents counter.
   * 
   * @param totalDocCounter
   *          - the new Document count
   */
  public void setTotalDocCounter(int totalDocCounter)
  {
    this.totalDocCounter = totalDocCounter;
  }

  /**
   * Helper method to get the file list of a document object.
   * 
   * @return - the fileList
   */
  public List<String> getFileList()
  {
    return fileList;
  }

  /**
   * Helper method to get the Total InvertedIndex count.
   * 
   * @return - the total InvertedIndex count
   */
  public int getTotalIndexCounter()
  {
    return totalIndexCounter;
  }

  /**
   * Helper method to set the Total InvertedIndex count.
   * 
   * @param totalIndexCounter
   *          - the new InvertedIndex count
   */
  public void setTotalIndexCounter(int totalIndexCounter)
  {
    this.totalIndexCounter = totalIndexCounter;
  }

  /**
   * Helper method to get the total postings count.
   * 
   * @return - the total postings count
   */
  public int getTotalPostings()
  {
    return totalPostings;
  }

  /**
   * Helper method to set the total postings count.
   * 
   * @param totalPostings
   *          - the new total postings count
   */
  public void setTotalPostings(int totalPostings)
  {
    this.totalPostings = totalPostings;
  }
}
