package pirexCLI;

import indexer.InvertedIndex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import documentStore.DocumentStore;
import searchEngine.SearchEngine;
import searchEngine.SearchResult;
import sourceProcessor.SourceProcessor;

/**
 * Runs PIREX from the Command Line.
 * @author MoniAli (Entire class)
 *
 */
public class CLI
{
  static boolean admin;
  static Scanner in;
  static SourceProcessor sp;
  static final String WHICH = "Which Opus would you like to edit?";

  /**
   * Main creates the IV and DS then calls the menu loop while the user/admin doesn't exist.
   * @param args unused.
   */
  public static void main(String[] args)
  {
    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    try
    {
      sp = new SourceProcessor(iv, ds);
    }
    catch (IOException e)
    {
      System.out.println("The file was not found");
    }
    in = new Scanner(System.in);
    admin = false;
    boolean loop = true;
    while(loop)
    {
      printMenu();
      loop = menuLoop(in.nextLine());
    }
  }
  /**
   * The Main portion of code, menu loop does everything when user/admin selects it.
   * @param opt The option the user/admin has selected
   * @return Whether or not the the user/admin chose to exit
   */
  public static boolean menuLoop(String opt)
  {
    boolean ret = true;
    boolean verbose = false;
    switch(opt)
    {
      case "admin":
        admin = true;
        break;
      case "user":
        admin = false;
        break;
        
      case "lv":
        if (admin)
          verbose = true;
      case "l":
        if (admin)
        {
          System.out.println("Please specify a file path for the Opus you'd like to load");
          String fileP = in.nextLine();
          boolean suc;
          File f = new File(fileP);
          suc = f.exists();
          try
          {
            if (suc)
               sp.process(fileP);
            else
            {
              System.out.println("File does not exist");
            }
          }
          catch (IOException e)
          {
            System.out.println("File not found");
            suc = false;
          }
          if (verbose && suc)
          {
            System.out.println("\nLoad Summary:\n");
            printSummary(fileP);
          }
          verbose = false;
        }
        break;
      case "t":
        if (admin)
        {
          System.out.println(WHICH);
          String file = in.nextLine();
          System.out.println("What would you like to change the title to?");
          String title = in.nextLine();
          sp.getDs().getStore().get((sp.getFileList().indexOf(file))).setTitle(title);
        }
        break;
      case "a":
        if (admin)
        {
          System.out.println(WHICH);
          String file = in.nextLine();
          System.out.println("What would you like to change the author to?");
          String author = in.nextLine();
          sp.getDs().getStore().get((sp.getFileList().indexOf(file))).setTitle(author);
        }
        break;
      case "s":
        if (admin)
        {
          System.out.println("Which Opus would you like to view?");
          String sumFile = in.nextLine();
          if (sp.getFileList().indexOf(sumFile) != -1)
          {
            System.out.println("\nOpus Summary:\n");
            printSummary(sumFile);
          }
          else
          {
            System.out.println("That Opus does not currently exist");
          }
        }
        break;
      case "d":
        if (admin)
        {
          System.out.println("While Opus would you like to remove?");
          String rmFile = in.nextLine();
          File ff = new File(rmFile);
          if (ff.exists())
          {
            sp.getDs().remove(sp.getDs().getStore().get(
                (sp.getFileList().indexOf(rmFile))).getTitle());
            sp.getFileList().remove(rmFile);
          }
          else
          {
            System.out.println("That file does not exist or has already been deleted.");
          }
        }
        break;
      case "query":
        if (!admin && sp.getDs().getStoreSize() != -1)
        {
          SearchEngine se = new SearchEngine(sp.getDs(), sp.getIV());
          String search;
          System.out.println("What would you like to search for?");
          search = in.nextLine();
          List<SearchResult> sr = new ArrayList<SearchResult>();
          sr = se.find(search);
          if (sr != null)
          {
            final String docNum = "Document #";
            final String inOpus = " in Opus #";
            for (SearchResult s : sr)
            {
              System.out.println(docNum 
                          + s.getDocumentNumber() + inOpus + s.getOpusNumber());
            }
          
            System.out.println("Would you like to print every document" 
                      + "with your search query? (y/n)");
            String p = in.nextLine();
            if (p.equalsIgnoreCase("y"))
            {
              for (SearchResult s : sr)
              {
                System.out.println(docNum 
                    + s.getDocumentNumber() + inOpus + s.getOpusNumber());
                System.out.println(s.getDocument().getShortForm());
                System.out.println();
              }
            }
          }
          else
          {
            System.out.println("No documents to search yet.");
          }
        }
        break;
      case "dc":
        if (!admin)
        {
          System.out.println("What would you like to print the document count for?");
          String doc = in.nextLine();
          if (sp.getFileList().contains(doc))
          {
            System.out.println("This Opus has " 
                + sp.getDs().getStore().get((sp.getFileList().indexOf(doc))).getDocCounter()
                            + " documents.");
          }
          else
          {
            System.out.println("This Opus has not yet been processeed");
          }
        }
        break;
      case "e":
        if (!admin)
        {
          System.out.println("Which Opus would you like to expand?");
          String expandStr = in.nextLine();
          File exFile = new File(expandStr);
          if (exFile.exists())
          {
            for (int i = 0;
                i < sp.getDs().getStore().get((sp.getFileList().indexOf
                    (expandStr))).getDocCounter(); i++)
            {
              System.out.println(sp.getDs().getStore().get(
                    (sp.getFileList().indexOf(expandStr))).getDocument(i).toString());
            }
          }
          else
          {
            System.out.println("File was not found");
          }
        }
        break;
      case "q":
        ret = false;
        break;
      default:
        System.out.println("Invalid menu option");
        break;
    }
    
    return ret;
  }
  
  /**
   * @author Moni Ali
   * Prints the menu options.
   */
  private static void printMenu()
  {
    if (!admin)
    {
      System.out.println("User Menu Options:");
      System.out.println("  Enter a query               - query");
      System.out.println("  Display document count      - dc");
      System.out.println("  Expand short form           - e");
      System.out.println("  Admin options               - admin");
    }
    if (admin)
    {
      System.out.println("Admin Menu Options:");
      System.out.println("  Load an Opus                - l");
      System.out.println("  Load Opus with load summary - lv");
      System.out.println("  Change Opus title info      - t");
      System.out.println("  Change Opus author info     - a");
      System.out.println("  View an Opus summary        - s");
      System.out.println("  Delete an Opus              - d");
      System.out.println("  Return to regular user      - user");
    }
    System.out.println("  Save and exit system        - q");
  }

  /**
   * Print's a summary from the given file name.
   * @param file The Opus to summarize
   */
  public static void printSummary(String file)
  {
    final String newLine = "\n";
    System.out.println("Opus: " + file + newLine);
    System.out.println("Title: " + sp.getDs().getStore().get(
        (sp.getFileList().indexOf(file))).getTitle() + newLine);
    System.out.println("Author: " + sp.getDs().getStore().get(
        (sp.getFileList().indexOf(file))).getAuthor() + newLine);
    System.out.println("Opus size: " + sp.getDs().getStore().get(
        (sp.getFileList().indexOf(file))).getDocCounter() + newLine);
    System.out.println("Opus number: " + sp.getFileList().indexOf(file) + newLine);
    System.out.println("New index terms: " + sp.getIndexCounter() + newLine);
    System.out.println("New postings: " + sp.getIV().getPostingsHM().size() + newLine);
    System.out.println("Total index terms: " + sp.getTotalIndexCounter() + newLine);
    System.out.println("Total postings: " + sp.getTotalPostings() + newLine);
  }
}
