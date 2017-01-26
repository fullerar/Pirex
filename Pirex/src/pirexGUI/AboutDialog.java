package pirexGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author Andrew Fuller
 *
 */
public class AboutDialog extends JDialog
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  Container contentPane;
  JTextArea aboutTextArea;
  JPanel panel;



  /**
   * 
   * Creates a JDialog object that displays information about Pirex
   * 
   * @param owner - owning frame
   */
  public AboutDialog(Frame owner)
  {
    super(owner);

    setModalityType(DEFAULT_MODALITY_TYPE);     // set modality
    contentPane = getContentPane();             // get content pane
    contentPane.setLayout(new BorderLayout());  // set to BorderLayout
    setDefaultCloseOperation(DISPOSE_ON_CLOSE); // set close operation

    aboutTextArea = new JTextArea();    // create new JTextArea
    
    // populate text area with information
    aboutTextArea.setText("");          
    aboutTextArea.append("Pirex\n");
    aboutTextArea.append("Team 5\n\n");
    aboutTextArea.append("Search for words or phrases in the Search Documents tab.\n\n");
    aboutTextArea.append("Load an opus in the Load Documents tab.\n\n");
    aboutTextArea.append("See a summarized list of opera in the Summarize Documents tab.\n");

    panel = new JPanel(new BorderLayout());         // create JPanel with BorderLayout
    panel.add(aboutTextArea, BorderLayout.CENTER);  // add text area to panel
    contentPane.add(panel, BorderLayout.CENTER);    // add panel to content pane
  }
}
