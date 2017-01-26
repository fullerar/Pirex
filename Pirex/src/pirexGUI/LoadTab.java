package pirexGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import sourceProcessor.SourceProcessor;



/**
 * 
 * @author Andrew Fuller
 * 
 */
public class LoadTab extends JPanel implements ActionListener
{

  public static SourceProcessor sp;
  public static List<SourceProcessor> spList = new ArrayList<SourceProcessor>();
  static final String LOAD_TAB_BROWSE = "LOAD_TAB_BROWSE";
  static final String LOAD_TAB_PROCESS = "LOAD_TAB_PROCESS";
  static final String LOAD_TAB_CLEAR = "LOAD_TAB_CLEAR";
  private static final long serialVersionUID = 1L;

  int ordNum = 0;

  JFileChooser fileDialog;
  String pathResult;

  // Load tab components
  JButton browseBtn, btnProcess, loadTabClearBtn;
  JComboBox<String> fileType;
  JTextField textFileField, titleTextField, authorTextField;
  JTextArea processTextArea;

  /**
   * Creates a JPanel object to be used in JTabbedPane
   * 
   */
  public LoadTab()
  {
    super();
    setLayout(new BorderLayout());                 // set layout for container to BorderLayout

    JPanel north = new JPanel(new BorderLayout()); // create north JPanel w/ BorderLayout
    JPanel south = new JPanel(new BorderLayout()); // create south JPanel w/ BorderLayout
    
    JPanel n1 = new JPanel(new BorderLayout());    // create n1 JPanel w/ BorderLayout. Part of north panel
    JPanel n2 = new JPanel(new BorderLayout());    // create n2 JPanel w/ BorderLayout. Part of north panel
    JPanel n3 = new JPanel(new GridLayout(1,2));   // create n3 JPanel w/ GridLayout. Part of north panel
    
    JPanel s1 = new JPanel(new BorderLayout());    // create s1 JPanel w/ BorderLayout. Part of south panel
    JPanel s2 = new JPanel(new BorderLayout());    // create s2 JPanel w/ BorderLayout. Part of south panel

    JLabel textFileLabel = new JLabel("Text File:"); // create Text File label
    textFileField = new JTextField();                // create text field for file name
    textFileField.addActionListener(this);           // add action listener to text field

    btnProcess = new JButton("Process");             // create Process JButton
    btnProcess.setEnabled(false);                    // disable button
    btnProcess.setActionCommand(LOAD_TAB_PROCESS);   // set action command of Process button

    Document doc = textFileField.getDocument();      // create a javax.swing.text Document 
    doc.addDocumentListener(new DocumentListener()   // add/create Document listener
    {
      public void insertUpdate(DocumentEvent e)
      {
        updateEnable(e);
      }

      public void removeUpdate(DocumentEvent e)
      {
        updateEnable(e);
      }

      public void changedUpdate(DocumentEvent e)
      {
        updateEnable(e);
      }

      public void updateEnable(DocumentEvent e)      // if file name text field is populated, enable Process button
      {
        boolean enable = e.getDocument().getLength() > 0;
        btnProcess.setEnabled(enable);
      }
    });

    browseBtn = new JButton("Browse");           // create Browse JButton
    browseBtn.setActionCommand(LOAD_TAB_BROWSE); // set action command for Browse button

    JLabel titleLabel = new JLabel("Title:");    // create Title JLabel
    titleTextField = new JTextField();           // create title text field

    JLabel authorLabel = new JLabel("Author:");  // create Author JLabel
    authorTextField = new JTextField();          // create author text field

    JLabel textFileTypeLabel = new JLabel("Text File Type:"); // create Text File Type JLabel
    fileType = new JComboBox<String>();                       // create file type JComboBox
    fileType.addItem("Project Gutenberg File");               // add Project Gutenberg File type
    fileType.addActionListener(this);                         // add action listener to file type combo box

    processTextArea = new JTextArea();            // create process text area JTextArea
    processTextArea.setEditable(false);           // disable editing

    loadTabClearBtn = new JButton("Clear");           // create Clear JButton
    loadTabClearBtn.setActionCommand(LOAD_TAB_CLEAR); // set action command for Clear button
    
    n1.add(textFileLabel, BorderLayout.WEST);   // add text file label to n1, WEST
    n1.add(textFileField, BorderLayout.CENTER); // add text file field to n1, CENTER
    n1.add(browseBtn, BorderLayout.EAST);       // add Browse button to n1, EAST
    north.add(n1, BorderLayout.NORTH);          // add n1 to north, NORTH
    
    n2.add(textFileTypeLabel, BorderLayout.WEST); // add text file type label to n2, WEST
    n2.add(fileType, BorderLayout.CENTER);        // add file type combo box to n2, CENTER
    north.add(n2, BorderLayout.CENTER);           // add n2 to north, CENTER
    
    JPanel n31 = new JPanel(new BorderLayout());  // create n31 JPanel w/ BorderLayout. Part of n3
    n31.add(titleLabel, BorderLayout.WEST);       // add title label to n31, WEST
    n31.add(titleTextField, BorderLayout.CENTER); // add title text field to n31, CENTER
    n3.add(n31);                                  // add n31 to n3
    
    JPanel n32 = new JPanel(new BorderLayout());   // create n32 JPanel w/ BorderLayout. Part of n3
    n32.add(authorLabel, BorderLayout.WEST);       // add author label to n32, WEST
    n32.add(authorTextField, BorderLayout.CENTER); // add author text field to n32, CENTER
    n3.add(n32);                                   // add n32 to n3
    north.add(n3, BorderLayout.SOUTH);             // add n3 to north, SOUTH
    
    JPanel btnPanel = new JPanel(new GridLayout(1,2)); // create JPanel for Process and Clear buttons
    btnPanel.add(btnProcess);                          // add Process button to btnPanel
    btnPanel.add(loadTabClearBtn);                     // add Clear button to btnPanel
    s1.add(btnPanel, BorderLayout.WEST);               // add btnPanel to s1, WEST
    south.add(s1, BorderLayout.NORTH);                 // add s1 to south, NORTH
    
    s2.add(processTextArea, BorderLayout.CENTER);      // add process text area to s2, CENTER
    south.add(s2, BorderLayout.CENTER);                // add s2 to south, CENTER
    
    add(north, BorderLayout.NORTH);                    // add north to container, NORTH
    add(south, BorderLayout.CENTER);                   // add south to container, CENTER
  }

  /**
   * Creates JFileChooser object that lets you search your local file system for a text file.
   * Upon approved selection, automatically searches document for title and author lines.
   * 
   * @throws IOException -
   */
  public void browse() throws IOException
  {
    fileDialog = new JFileChooser();
    int returnVal = fileDialog.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      pathResult = fileDialog.getSelectedFile().getAbsolutePath();
      textFileField.setText(pathResult);

      if (fileType.getSelectedItem().equals("Project Gutenberg File"))
      {                          
        titleTextField.setText(SourceProcessor.getTitleFromFile(pathResult));
        authorTextField.setText(SourceProcessor.getAuthorFromFile(pathResult));
      }
    } 
  }
  
  /**
   * Clears text fields/area.
   */
  public void clear()
  {
    textFileField.setText("");
    titleTextField.setText("");
    authorTextField.setText("");
    processTextArea.setText("");
  }
  
  @Override
  public void actionPerformed(ActionEvent e)
  {

  }
}
