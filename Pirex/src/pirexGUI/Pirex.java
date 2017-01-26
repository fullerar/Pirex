package pirexGUI;

import indexer.InvertedIndex;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import searchEngine.SearchEngine;
import searchEngine.SearchResult;
import sourceProcessor.SourceProcessor;
import documentStore.DocumentStore;
import documentStore.Opus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.List;

/**
 * 
 * @author Andrew Fuller
 * 
 */
public class Pirex extends JFrame implements ActionListener, ListSelectionListener
{
  // Action commands for JButtons
  public static final String EXIT = "EXIT";
  public static final String ABOUT = "ABOUT";
  // public static final String DOCUMENTS = "DOCUMENTS";
  // public static final String SOURCES = "SOURCES";
  public static final String INDEX = "INDEX";
  public static final String SAVESP = "SAVESP";
  public static final String SAVEQ = "SAVEQ";
  public static final String LOADSP = "LOADSP";
  public static final String LOADQ = "LOADQ";
  public static final String EXPORT = "EXPORT";

  // tab objects for Pirex
  public SearchTab searchTab;
  public LoadTab loadTab;
  public SummarizeTab summarizeTab;

  // serialize
  private static final long serialVersionUID = 1L;

  // parameters to pass to Pirex object
  private SourceProcessor sp;
  private DocumentStore ds;
  private InvertedIndex iv;

  // Frame components
  JFrame frame;
  JTabbedPane tabbedPane;
  JFileChooser fileDialog;

  // Menu bar components
  JMenuBar menuBar;
  JMenu fileMenu, helpMenu, optionsMenu;
  JMenuItem exit, about, documents, sources, index, save, load, export;

  // Dialog boxes
  JDialog aboutDialog;

  /**
   * Main Pirex GUI.
   * 
   * @param sp
   *          - the new SourceProcessor object
   * @param ds
   *          - the new DocumentStore object
   * @param iv
   *          - the new InvertedIndex object
   */
  public Pirex(SourceProcessor sp, DocumentStore ds, InvertedIndex iv)
  {
    super();
    this.sp = sp;
    this.ds = ds;
    this.iv = iv;

    // create JFrame object
    frame = new JFrame("Pirex");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(650, 650);

    // create a JTabbedPane object
    tabbedPane = new JTabbedPane();

    // create a JMenuBar object, fill with File and Help menu items
    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    helpMenu = new JMenu("Help");
    // optionsMenu = new JMenu("Options");

    // create About menu item inside the Help menu item
    about = new JMenuItem("About");
    about.addActionListener(this);
    about.setActionCommand(ABOUT);
    about.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    helpMenu.add(about);

    // create Index menu item inside the Help menu item
    index = new JMenuItem("Index");
    index.addActionListener(this);
    index.setActionCommand(INDEX);
    index.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    helpMenu.add(index);

    // new JMenuItem items (in progress)
    // documents = new JMenuItem("Documents");
    // documents.addActionListener(this);
    // documents.setActionCommand(DOCUMENTS);
    // documents.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit()
    // .getMenuShortcutKeyMask()));
    // optionsMenu.add(documents);
    //
    // sources = new JMenuItem("Sources");
    // sources.addActionListener(this);
    // sources.setActionCommand(SOURCES);
    // sources.setAccelerator(KeyStroke.getKeyStroke('s', Toolkit.getDefaultToolkit()
    // .getMenuShortcutKeyMask()));
    // optionsMenu.add(sources);

    // create Save Source Processor menu item inside the File menu item
    save = new JMenuItem("Save SP");
    save.addActionListener(this);
    save.setActionCommand(SAVESP);
    save.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    fileMenu.add(save);

    // create Save Queries menu item inside the File menu item
    save = new JMenuItem("Save Queries");
    save.addActionListener(this);
    save.setActionCommand(SAVEQ);
    save.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    fileMenu.add(save);

    // create Load Source Processor menu item inside the File menu item
    load = new JMenuItem("Load SP");
    load.addActionListener(this);
    load.setActionCommand(LOADSP);
    load.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    fileMenu.add(load);

    // create Load Queries menu item inside the File menu item
    load = new JMenuItem("Load Queries");
    load.addActionListener(this);
    load.setActionCommand(LOADQ);
    load.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    fileMenu.add(load);

    // create Export menu item inside the File menu item
    export = new JMenuItem("Export");
    export.addActionListener(this);
    export.setActionCommand(EXPORT);
    export.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    fileMenu.add(export);

    // create Exit menu item inside the File menu item
    exit = new JMenuItem("Exit");
    exit.addActionListener(this);
    exit.setActionCommand(EXIT);
    exit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit()
        .getMenuShortcutKeyMask()));
    fileMenu.add(exit);

    // add File and Help menu items to the menu bar
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    // menuBar.add(optionsMenu);
    frame.setJMenuBar(menuBar);

    // instantiate Search Tab object
    // create/add action listeners for Search Tab JButtons, JList
    searchTab = new SearchTab();
    searchTab.searchBtn.addActionListener(this);
    searchTab.searchTabClearBtn.addActionListener(this);

    // Search JButton clickable by pressing the Enter button
    frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "click");
    frame.getRootPane().getActionMap().put("click", new AbstractAction()
    {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      public void actionPerformed(ActionEvent ae)
      {
        searchTab.searchBtn.doClick();
      }
    });

    // Search Tab Query Result JList selection listener
    // Used to highlight search word when displayed in long form
    searchTab.queryResultList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if (!searchTab.queryResultList.isSelectionEmpty())
        {
          String selected = searchTab.queryResultList.getSelectedValue().toString();
          String[] selectedArrayStrings = selected.split(", ");

          searchTab.selectedTextArea.setText(getDs().getStore()
              .get(Integer.parseInt(selectedArrayStrings[0]))
              .getDocument(Integer.parseInt(selectedArrayStrings[3])).toString());

          String text = searchTab.selectedTextArea.getText();
          String word = searchTab.queryTextField.getText();

          Highlighter highlighter = searchTab.selectedTextArea.getHighlighter();
          HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
          int p0 = text.indexOf(word);
          int p1 = p0 + word.length();
          try
          {
            highlighter.addHighlight(p0, p1, painter);
          }
          catch (BadLocationException e1)
          {
            e1.printStackTrace();
          }
        }
      }
    });

    // instantiate Load Tab object
    // create/add action listeners for JButtons, JTextFields, JComboBox
    loadTab = new LoadTab();
    loadTab.browseBtn.addActionListener(this);
    loadTab.textFileField.addActionListener(this);
    loadTab.fileType.addActionListener(this);
    loadTab.btnProcess.addActionListener(this);
    loadTab.loadTabClearBtn.addActionListener(this);

    // instantiate Summarize Tab object
    // create/add action listeners for JButtons
    summarizeTab = new SummarizeTab();
    summarizeTab.btnSummarize.addActionListener(this);

    // add each tab object to JTabbedPane
    tabbedPane.addTab("Search for Documents", searchTab);
    tabbedPane.addTab("Load Documents", loadTab);
    tabbedPane.addTab("Summarize Documents", summarizeTab);

    // add JTabbedPane to JFrame
    frame.getContentPane().add(tabbedPane);

    // Title bar icon code (in progress)
    // URL url = this.getClass().getResource("../resources/Pirex_Icon_16x16.png");
    // System.out.println(url);
    // ImageIcon img = new ImageIcon(url);
    //
    // frame.setIconImage(img.getImage());

    frame.setVisible(true);
  }

  /**
   * 
   * @param args
   *          - unused
   * @throws IOException
   *           -
   * @throws FileNotFoundException
   *           -
   */
  public static void main(String[] args) throws FileNotFoundException, IOException
  {
    InvertedIndex iv = new InvertedIndex();
    DocumentStore ds = new DocumentStore();
    SourceProcessor sp = new SourceProcessor(iv, ds);
    Pirex p = new Pirex(sp, ds, iv);
  }

  // action handlers for all JButtons
  @Override
  public void actionPerformed(ActionEvent e)
  {
    String command = e.getActionCommand();
    int counter = 0;

    /****** SEARCH BUTTON ******/

    if (command.equals(searchTab.searchBtn.getActionCommand())) // Search button pressed
    {
      searchTab.dfm.removeAllElements(); // clear default list model
      counter = 0;
      // display retrieved documents counter
      searchTab.resultNumLabel.setText("Retrieved " + counter + " documents.");
      
      // if previous search box is not selected. else use the selection
      if ("".equals(searchTab.previousSearches.getSelectedItem()))
      {
        if (ds.getStore().size() > 0) // if document store is populated, perform search. else do not
        {
          String searchWord = searchTab.queryTextField.getText(); // get the search word entered by
                                                                  // user

          if (!searchWord.equals("")) // if search word is blank, do not add it to the previous
                                      // searches
          {
            searchTab.previousSearches.addItem(searchWord); // else add to previous searches
          }

          if (iv.get(searchWord) == null) // check for null, else search for word
          {
            searchTab.selectedTextArea.setText("Word not found.");
          }
          else
          {
            SearchEngine se = new SearchEngine(ds, iv); // create SearchEngine object
            List<SearchResult> list = se.find(searchWord); // find all documents containing search
                                                           // word

            for (SearchResult x : list) // for each document in list
            {
              // create string with opus/document information
              String res = (x.getOpusNumber() + ", " + x.getOpus().getAuthor() + ", "
                  + x.getOpus().getTitle() + ", " + x.getDocumentNumber() + ", "
                  + x.getDocument().getShortForm() + "\n");

              // add string to default model
              searchTab.dfm.addElement(res);

              // retrieved documents counter
              counter++;
            }
            // display retrieved documents counter
            searchTab.resultNumLabel.setText("Retrieved " + counter + " documents.");
          }
        }
        else
        {
          searchTab.selectedTextArea.setText("Document Store is empty, cannot perform search.");
        }
      }
      else
      // previous search box filled, search by that selection
      {
        // set query text field to match previous search selection
        searchTab.queryTextField.setText((String) searchTab.previousSearches.getSelectedItem());

        // get search word
        String searchWord = searchTab.queryTextField.getText();

        if (iv.get(searchWord) == null) // check for null
        {
          searchTab.selectedTextArea.setText("Word not found.");
        }
        else
        {
          SearchEngine se = new SearchEngine(ds, iv); // create search engine
          List<SearchResult> list = se.find(searchWord); // find all documents containing search
                                                         // word

          for (SearchResult x : list) // for each document in list
          {
            // create string with opus/document information
            String res = (x.getOpusNumber() + ", " + x.getOpus().getAuthor() + ", "
                + x.getOpus().getTitle() + ", " + x.getDocumentNumber() + ", "
                + x.getDocument().getShortForm() + "\n");

            // add string to default model
            searchTab.dfm.addElement(res);

            // documents retrieved counter
            counter++;
          }
          // display documents retrieved counter
          searchTab.resultNumLabel.setText("Retrieved " + counter + " documents.");
        }
      }
    }
    else if (command.equals(searchTab.searchTabClearBtn.getActionCommand())) // search tab Clear
                                                                             // button pressed
    {
      searchTab.clear(); // call helper method to clear text field/areas
      counter = 0; // reset documents retrieved counter
      searchTab.resultNumLabel.setText("Retrieved " + counter + " documents."); // display documents
                                                                                // retrieved
      searchTab.queryResultList.clearSelection(); // clear JList selection
      searchTab.dfm.removeAllElements(); // remove all elements from default model
    }

    /****** BROWSE BUTTON ******/

    else if (command.equals(loadTab.browseBtn.getActionCommand())) // Browse button pressed
    {
      try
      {
        loadTab.browse(); // call helper method to let user browse local file system
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }

    /****** PROCESS BUTTON ******/

    else if (command.equals(loadTab.btnProcess.getActionCommand())) // Process button pressed
    {
      try
      {
        ds = sp.process(loadTab.textFileField.getText()); // instantiate Document Store

        loadTab.processTextArea.setText(""); // make sure process text area is clear

        // add following Document Store information
        loadTab.processTextArea.append("Opus: " + loadTab.textFileField.getText() + "\n");
        loadTab.processTextArea.append("Title: " + loadTab.titleTextField.getText() + "\n");
        loadTab.processTextArea.append("Author: " + loadTab.authorTextField.getText() + "\n");
        loadTab.processTextArea.append("Opus size: " + sp.getOpus().getDocCounter() + "\n");
        loadTab.processTextArea.append("Opus number: " + ds.getStoreSize() + "\n");
        loadTab.processTextArea.append("New index terms: " + sp.getIndexCounter() + "\n");
        loadTab.processTextArea.append("New postings: " + sp.getIV().getPostingsHM().size() + "\n");
        loadTab.processTextArea.append("Total index terms: " + sp.getTotalIndexCounter() + "\n");
        loadTab.processTextArea.append("Total postings: " + sp.getTotalPostings() + "\n");

        // clear text file, title, and author text fields
        loadTab.textFileField.setText("");
        loadTab.titleTextField.setText("");
        loadTab.authorTextField.setText("");
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    else if (command.equals(loadTab.loadTabClearBtn.getActionCommand())) // Load tab Clear button
                                                                         // pressed
    {
      loadTab.clear(); // call helper method to clear text fields/area
    }

    /****** SUMMARIZE BUTTON ******/

    else if (command.equals(summarizeTab.btnSummarize.getActionCommand())) // Summarize button
                                                                           // pressed
    {
      summarizeTab.summarizeTextArea.setText(""); // clear text area

      for (Opus o : ds.getStore()) // for each Opus in the Document Store
      {
        // create string ...
        String space = "       ";
        String res = String.format("Opus %d: %s %s %15s %15d documents\n" + "%s %15s\n\n", ds
            .indexOf(o), o.getAuthor(), space, o.getTitle(), o.getDocCounter(), space, sp
            .getFileList().get(ds.indexOf(o)));

        // add string to text area
        summarizeTab.summarizeTextArea.append(res);
      }

      // create string to represent total terms/postings
      String res = String.format("Index terms: %d\nPostings: %d", sp.getTotalIndexCounter(),
          sp.getTotalPostings());

      // add to text area
      summarizeTab.summarizeTextArea.append(res);
    }

    else if (command.equals(EXIT)) // Menu item Exit pressed
    {
      frame.setVisible(false); // set visibility
      frame.dispose(); // close JFrame
    }
    else if (command.equals(SAVESP)) // Menu item Save SP pressed
    {
      try
      {
        JFileChooser jfc = new JFileChooser(); // search local file system for save location
        int returnVal = jfc.showSaveDialog(this); // open Save Dialog
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // only show directories

        if (returnVal == JFileChooser.APPROVE_OPTION) // approve option pressed
        {
          File file = jfc.getSelectedFile(); // get the file to save
          FileOutputStream fileOut = new FileOutputStream(file); // create file output stream
          ObjectOutputStream out = new ObjectOutputStream(fileOut); // create object writer
          out.writeObject(getSP()); // write current SourceProcessor object to file
          out.close(); // object writer closed
          fileOut.close(); // close file output stream
        }
      }
      catch (IOException i)
      {
        i.printStackTrace();
      }
    }

    else if (command.equals(SAVEQ)) // Menu item Save Q pressed
    {
      try
      {
        JFileChooser jfc = new JFileChooser(); // search local file system for save location
        int returnVal = jfc.showSaveDialog(this); // open Save Dialog
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // only show directories

        if (returnVal == JFileChooser.APPROVE_OPTION) // approve option pressed
        {
          File file = jfc.getSelectedFile(); // get the file to save
          FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile()); // create file
                                                                                   // output stream

          PrintWriter out = new PrintWriter(file); // create writer object
          for (int i = 1; i < searchTab.previousSearches.getItemCount(); i++) // for each term in
                                                                              // previous searches
          {
            out.println(searchTab.previousSearches.getItemAt(i).toString()); // print to output file
          }
          fileOut.close(); // close file output stream
          out.close(); // close writer object
        }
      }
      catch (IOException i)
      {
        i.printStackTrace();
      }
    }

    else if (command.equals(LOADSP)) // Menu item Load SP pressed
    {
      SourceProcessor s = null; // temporary SourceProcessor object
      try
      {
        JFileChooser jfc = new JFileChooser(); // search local file system for .ser file
        int returnVal = jfc.showOpenDialog(this); // show Open dialog

        if (returnVal == JFileChooser.APPROVE_OPTION) // approve option pressed
        {
          File file = jfc.getSelectedFile(); // get the file to open

          FileInputStream fileIn = new FileInputStream(file); // create file input stream
          ObjectInputStream in = new ObjectInputStream(fileIn); // create object input stream
          s = (SourceProcessor) in.readObject(); // read in SourceProcessor object
          setSP(s); // set new SourceProcessor to current SourceProcessor
          setDs(s.getDs()); // set new DocumentStore to current DocumentStore
          setIv(s.getIV()); // set new InvertedIndex to current InvertedIndex
          in.close(); // close object input stream
          fileIn.close(); // close file input stream
        }

      }
      catch (IOException i)
      {
        i.printStackTrace();
      }
      catch (ClassNotFoundException e1)
      {
        e1.printStackTrace();
      }
    }
    else if (command.equals(LOADQ)) // Menu item Load Q pressed
    {
      try
      {
        JFileChooser jfc = new JFileChooser(); // search local file system for .txt file
        int returnVal = jfc.showOpenDialog(this); // show Open dialog

        if (returnVal == JFileChooser.APPROVE_OPTION) // approve option pressed
        {
          File file = jfc.getSelectedFile(); // get file to open
          BufferedReader br = new BufferedReader(new FileReader(file)); // create buffered reader
                                                                        // object
          String line; // readLine holder
          while ((line = br.readLine()) != null) // while input file line does not equal null
          {
            searchTab.previousSearches.addItem(line); // add line to previous searches combo box
          }
          br.close(); // close buffered reader
        }
      }
      catch (IOException i)
      {
        i.printStackTrace();
      }
    }
    else if (command.equals(EXPORT)) // Menu item Export pressed
    {
      // Current implementation outputs a .txt file to the current working directory.
      // Come back in future and implement JFileChooser

      try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
          "SearchResults.txt"), "utf-8")))
      // create buffered writer object
      {
        for (int i = 0; i < searchTab.dfm.getSize(); i++) // for each line in the default model
        {
          writer.write(searchTab.dfm.get(i).toString()); // write to output file
        }
        writer.close(); // close writer
      }
      catch (IOException ex)
      {
        ex.printStackTrace();
      }
    }
    // else if (command.equals(SOURCES))
    // {
    //
    // }
    // else if (command.equals(DOCUMENTS))
    // {
    //
    // }
    else if (command.equals(INDEX)) // Menu item Index pressed
    {
      IndexDialog i = new IndexDialog(this); // create Index JDialog object
      i.setBounds(10, 10, 900, 500); // set display area
      i.setVisible(true); // set visibility
    }
    else if (command.equals(ABOUT)) // Menu item About pressed
    {
      AboutDialog a = new AboutDialog(this); // create About JDialog object
      a.setBounds(10, 10, 600, 300); // set display area
      a.setVisible(true); // set visibility
    }
  }

  /**
   * 
   * @return sp - SourceProcessor object
   */
  public SourceProcessor getSP()
  {
    return sp;
  }

  /**
   * 
   * @param sp
   *          - set current SourceProcessor object
   */
  public void setSP(SourceProcessor sp)
  {
    this.sp = sp;
  }

  /**
   * 
   * @return ds - DocumentStore object
   */
  public DocumentStore getDs()
  {
    return ds;
  }

  /**
   * 
   * @param ds
   *          - set current DocumentStore object
   */
  public void setDs(DocumentStore ds)
  {
    this.ds = ds;
  }

  /**
   * 
   * @return iv - InvertedIndex object
   */
  public InvertedIndex getIv()
  {
    return iv;
  }

  /**
   * 
   * @param iv
   *          - set current InvertedIndex object
   */
  public void setIv(InvertedIndex iv)
  {
    this.iv = iv;
  }

  @Override
  public void valueChanged(ListSelectionEvent e)
  {
    // TODO Auto-generated method stub
  }
}
