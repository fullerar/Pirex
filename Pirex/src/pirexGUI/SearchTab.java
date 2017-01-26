package pirexGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * @author Andrew Fuller
 *
 */
public class SearchTab extends JPanel implements ActionListener, ListSelectionListener
{
  /**
   * 
   */
  public static final String SEARCH_TAB_CLEAR  = "SEARCH_TAB_CLEAR";
  public static final String SEARCH_TAB_SEARCH = "SEARCH_TAB_SEARCH";
  private static final long serialVersionUID   = 1L;

  //Search tab components
  JButton                  searchBtn, searchTabClearBtn;
  JTextField               queryTextField;
  DefaultListModel<String> dfm;
  JList<String>            queryResultList;
  JTextArea                queryResultTextArea, selectedTextArea;
  JComboBox<String>        previousSearches;
  JLabel                   resultNumLabel;
  
  /**
   * Create JPanel object to be used in  JTabbedPane
   */
  public SearchTab()
  {
    super();
    setLayout(new BorderLayout());                 // set container layout to BorderLayout

    JPanel north = new JPanel(new BorderLayout()); // create north JPanel w/ BorderLayout
    JPanel mid   = new JPanel(new BorderLayout()); // create mid JPanel w/ BorderLayout. Part of bot
    JPanel south = new JPanel(new BorderLayout()); // create south JPanel w/ BorderLayout. Part of bot
    
    JPanel bot = new JPanel(new GridLayout(2,1));  // create bot JPanel w/ GridLayout
    
    JLabel queryLabel     = new JLabel("Query");   // create Query JLabel
    searchTabClearBtn     = new JButton("Clear");  // create Clear search
    searchTabClearBtn.setActionCommand(SEARCH_TAB_CLEAR); // set action command for Clear button
    
    queryResultTextArea  = new JTextArea();        // create query result text area
    queryResultTextArea.setEditable(false);        // disable editing

    resultNumLabel = new JLabel("Retrieved 0 documents."); // create Retrieved documents label
    
    dfm = new DefaultListModel<String>();     // create default list model for query result list
    queryResultList = new JList<String>(dfm); // create query result JList object 

    queryResultList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); // set JList selection model
    queryResultList.setLayoutOrientation(JList.VERTICAL);                           // set JList orientation

    JScrollPane scroll = new JScrollPane(queryResultList);                           // create JScrollPane for JList
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);     // set vertical scroll bar policy
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // set horizontal scroll bar policy
    
    selectedTextArea     = new JTextArea();                                            // create selected text area JTextArea
    selectedTextArea.setEditable(false);                                               // disable editing
    JScrollPane scroller = new JScrollPane(selectedTextArea);                          // create JScrollPane for text area
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);     // set vertical scroll bar policy
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // set horizontal scroll bar policy
    
    searchBtn            = new JButton("Search");            // create Search JButton
    searchBtn.setActionCommand(SEARCH_TAB_SEARCH);           // set action command for Search button
    queryTextField       = new JTextField();
    
    previousSearches     = new JComboBox<String>();          // create previous search JComboBox
    previousSearches.addActionListener(this);                // add action listener to combo box
    previousSearches.addItem("");                            // add default item ""
        
    JPanel btnPanel      = new JPanel(new GridLayout(1,2));  // create button panel JPanel w/ GridLayout
    btnPanel.add(searchBtn);                                 // add Search button to btnPanel
    btnPanel.add(searchTabClearBtn);                         // add Clear button to btnPanel

    north.add(queryLabel, BorderLayout.WEST);                // add queryLabel to north, WEST
    north.add(queryTextField, BorderLayout.CENTER);          // add queryTextField to north, CENTER
    north.add(btnPanel, BorderLayout.EAST);                  // add btnPanel to north, EAST
    north.add(previousSearches, BorderLayout.SOUTH);         // add previousSearches to north, SOUTH
    
    mid.add(scroll, BorderLayout.CENTER);                    // add scroll to mid, CENTER
    mid.add(resultNumLabel, BorderLayout.SOUTH);             // add resultNumLabel to mid, SOUTH
    
    south.add(scroller, BorderLayout.CENTER);                // add scroller to south, CENTER
    
    bot.add(mid);                                            // add mid to bot
    bot.add(south);                                          // add south to bot
    
    add(north, BorderLayout.NORTH);                          // add north to container, NORTH
    add(bot, BorderLayout.CENTER);                           // add bot to container, CENTER
  }

  /**
   *  Helper method to clear text field/areas
   */
  public void clear()
  {
    queryTextField.setText("");
    queryResultTextArea.setText("");
    selectedTextArea.setText("");
  } 

  @Override
  public void actionPerformed(ActionEvent e)
  {

  }

  @Override
  public void valueChanged(ListSelectionEvent e)
  {
    // TODO Auto-generated method stub
    
  }
}
