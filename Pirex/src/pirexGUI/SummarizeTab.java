package pirexGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;


/**
 * 
 * @author Andrew Fuller
 * 
 */
public class SummarizeTab extends JPanel implements ActionListener
{
  /**
   * 
   */
  public static final String SUMMARIZE_TAB_SUMMARIZE = "SUMMARIZE_TAB_SUMMARIZE";
  private static final long serialVersionUID = 1L;
  
  // Summarize tab components
  JTextArea summarizeTextArea;
  JButton btnSummarize;

  /**
   *  Constructs a JPanel object to be used in a JTabbedPane.
   */
  public SummarizeTab()
  {
    super();

    setLayout(new BorderLayout());                  // set container layout to BorderLayout

    JPanel north = new JPanel(new BorderLayout());  // create north JPanel

    btnSummarize = new JButton("Summarize");                // create Summarize JButton
    btnSummarize.setActionCommand(SUMMARIZE_TAB_SUMMARIZE); // set Summarize button action command
    north.add(btnSummarize, BorderLayout.EAST);             // add Summarize button to north, EAST
    add(north, BorderLayout.NORTH);                         // add north to container, NORTH
    
    summarizeTextArea    = new JTextArea();                     // create summarize text JTextArea
    summarizeTextArea.setEditable(false);                       // disable editing
    
    JScrollPane scroller = new JScrollPane(summarizeTextArea);                          // create JScrollPane for summarize text area
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);      // set vertical scroll bar policy
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  // set horizontal scroll bar policy
    
    add(scroller, BorderLayout.CENTER);   // add scroller to container, CENTER
  }
  
  @Override
  public void actionPerformed(ActionEvent e)
  {

  }
}
