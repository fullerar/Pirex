package pirexGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author Andrew Fuller
 * 
 */
public class IndexDialog extends JDialog
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  Container contentPane;
  JTextArea aboutTextArea;
  JPanel panel;

  /**
   * Creates JDialog object that displays information about Pirex functionality
   * 
   * @param owner - owning frame
   */
  public IndexDialog(Frame owner)
  {
    super(owner);

    setModalityType(DEFAULT_MODALITY_TYPE);     // set modality
    contentPane = getContentPane();             // get content pane
    contentPane.setLayout(new BorderLayout());  // set layout to BorderLayout
    setDefaultCloseOperation(DISPOSE_ON_CLOSE); // set close operation

    aboutTextArea = new JTextArea();            // create JTextArea
    
    // populate text area with information on Pirex
    aboutTextArea.setText("");
    aboutTextArea.append("Pirex\n");
    aboutTextArea.append("Team 5\n\n");
    aboutTextArea.append("Search for words or phrases in the Search Documents tab.\n");
    aboutTextArea
        .append("\t-The Search button takes the input from the "
            + "Query field and searches through the Document Store.\n");
    aboutTextArea
        .append("\t-If the Combo Box under the Query field is "
            + "selected, the Search button will search for that term.\n");
    aboutTextArea
        .append("\t-Search results are displayed in the upper "
            + "text area, giving details about the book author, title, \n "
            + "\t\tdocument number, and a breif display of the first line of the document.\n");
    aboutTextArea
        .append("\t-You may then click on one of the listings "
            + "and the full document containing the search word will \n"
            + "\t\tappear in the lower text area.\n");

    aboutTextArea.append("Load an opus in the Load Documents tab.\n");
    aboutTextArea
        .append("\t-Begin by clicking the browse button and selecting"
            + " a valid .txt file to be loaded in.\n");
    aboutTextArea
        .append("\t-Upon selecting a valid file, Pirex will automatically"
            + " retrieve the Title and Author information from the file, \n"
            + "\t\tfilling in the Title and Author text fields accordingly.\n");
    aboutTextArea
        .append("\t-Clicking the Process button will then create documents "
            + "for each paragraph, a posting for each word, and add the information \n"
            + "\t\tto the corresponding DocumentStore and InvertedIndex.\n");

    aboutTextArea.append("See a summarized list of opera in the Summarize Documents tab.\n");
    aboutTextArea
        .append("\t-Clicking the Summarize button will display a list of all"
            + " loaded Opera, the Title/Author information of each, \n"
            + "\t\tand the number of documents/idexed terms for each.");
    
    //aboutTextArea.append(str);

    panel = new JPanel(new BorderLayout());         // create JPanel
    panel.add(aboutTextArea, BorderLayout.CENTER);  // add text area to panel
    contentPane.add(panel, BorderLayout.CENTER);    // add panel to content pane
  }
}
