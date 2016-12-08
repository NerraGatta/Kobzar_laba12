import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by nera_gatta on 06.12.2016.
 */
public class MainForm extends JFrame {
    
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu submenu;
    private JMenuItem menuItem;
    private JPanel panel1;
    
    public JTextPane getTextPane1() {
        return textPane1;
    }
    
    private JTextPane textPane1;
    
    private static AppendDataForm appendDataForm = null;
    private static KeyForm keyForm = null;
    
    private static String filenamePath;
    private static String filenameBackPath;
    private static String filenameIdxPath;
    private static String filenameIdxBackPath;
    
    public static String getFilenamePath() {
        return filenamePath;
    }
    
    public static void setFilenamePath(String filenamePath) {
        MainForm.filenamePath = filenamePath;
    }
    
    public static String getFilenameIdxPath() {
        return filenameIdxPath;
    }
    
    public static String getFilenameBackPath() {
        return filenameBackPath;
    }
    
    public static void setFilenameBackPath(String filenameBackPath) {
        MainForm.filenameBackPath = filenameBackPath;
    }
    
    public static void setFilenameIdxPath(String filenameIdxPath) {
        MainForm.filenameIdxPath = filenameIdxPath;
    }
    
    public static String getFilenameIdxBackPath() {
        return filenameIdxBackPath;
    }
    
    public static void setFilenameIdxBackPath(String filenameIdxBackPath) {
        MainForm.filenameIdxBackPath = filenameIdxBackPath;
    }
    
    public MainForm() {
        $$$setupUI$$$();
        prepateGUI();
    }
    
    public static AppendDataForm getAppendDateForm() {
        return appendDataForm;
    }
    
    public static KeyForm getKeyForm() {
        return keyForm;
    }
    
    
    private void prepateGUI() {
        mainFrame = new JFrame("Bills Application");
        mainFrame.setSize(640, 480);
        
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        appendDataForm = new AppendDataForm(mainFrame);
        keyForm = new KeyForm(mainFrame);
        
        menuBar = new JMenuBar();
        
        menu = new JMenu("File");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Open");
        menu.add(menuItem);
       /* menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
    
                int returnVal = chooser.showOpenDialog(mainFrame);
    
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                }
            }
        });*/
        menu.addSeparator();
        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);
        
        menu = new JMenu("Commands");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Append Data");
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Append data, compress every record");
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Delete all data");
        menu.add(menuItem);
    
    
        submenu = new JMenu("Delete data by key");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        submenu.add(menuItem);
        menuItem = new JMenuItem("numberApartment");
        submenu.add(menuItem);
        menuItem = new JMenuItem("owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("paymentDate");
        submenu.add(menuItem);
    
        submenu = new JMenu("Print data unsorted");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        submenu.add(menuItem);
        menuItem = new JMenuItem("numberApartment");
        submenu.add(menuItem);
        menuItem = new JMenuItem("owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("paymentDate");
        submenu.add(menuItem);
    
        submenu = new JMenu("Print data sorted (by field)");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        submenu.add(menuItem);
        menuItem = new JMenuItem("numberApartment");
        submenu.add(menuItem);
        menuItem = new JMenuItem("owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("paymentDate");
        submenu.add(menuItem);
    
        submenu = new JMenu("Print data reverse sorted (by field)");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        submenu.add(menuItem);
        menuItem = new JMenuItem("numberApartment");
        submenu.add(menuItem);
        menuItem = new JMenuItem("owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("paymentDate");
        submenu.add(menuItem);
    
        submenu = new JMenu("Find records by key");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        submenu.add(menuItem);
        menuItem = new JMenuItem("numberApartment");
        submenu.add(menuItem);
        menuItem = new JMenuItem("owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("paymentDate");
        submenu.add(menuItem);
    
        submenu = new JMenu("Find records > key");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        submenu.add(menuItem);
        menuItem = new JMenuItem("numberApartment");
        submenu.add(menuItem);
        menuItem = new JMenuItem("owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("paymentDate");
        submenu.add(menuItem);
    
        submenu = new JMenu("Find records < key");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        submenu.add(menuItem);
        menuItem = new JMenuItem("numberApartment");
        submenu.add(menuItem);
        menuItem = new JMenuItem("owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("paymentDate");
        submenu.add(menuItem);
    
        menu = new JMenu("Help");
        menuBar.add(menu);
        
        for (Component menu : menuBar.getComponents()) {
            for (Component menuItem : ((JMenu) menu).getMenuComponents()) {
                if (!(menuItem instanceof JMenu) && !(menuItem instanceof JPopupMenu.Separator)) {
                    ((JMenuItem) menuItem).setActionCommand(((JMenuItem) menuItem).getText());
                    ((JMenuItem) menuItem).addActionListener(new menuItemListener());
                } else {
                    if (!(menuItem instanceof JPopupMenu.Separator)) {
                        for (Component submenuItem : ((JMenu) menuItem).getMenuComponents()) {
                            ((JMenuItem) submenuItem).setActionCommand(((JMenu) menuItem).getText() + " " + ((JMenuItem) submenuItem).getText());
                            ((JMenuItem) submenuItem).addActionListener(new menuItemListener());
                        }
                    }
                }
            }
        }
    
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(panel1);
    }
    
    private void showForm() {
        mainFrame.setVisible(true);
    }
    
    static void appendString(JTextPane textPane, String str) throws BadLocationException {
        StyledDocument styledDocument = (StyledDocument) textPane.getDocument();
        styledDocument.insertString(styledDocument.getLength(), str, null);
    }
    
    public static void main(String[] args) {
        MainForm form = new MainForm();
        form.showForm();
    }
    
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        textPane1 = new JTextPane();
        textPane1.setBackground(new Color(-9344069));
        panel1.add(textPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }
    
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
    
    private class menuItemListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Open":
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(mainFrame);
                    
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
    
                        filenamePath = file.getAbsolutePath();
    
                        try {
                            Bills.printFile(textPane1); //filepath
                        } catch (IOException | ClassNotFoundException | BadLocationException e1) {
                            System.err.println("ERROR!");
                        }
                    }
                    break;
                
                case "Exit":
                    mainFrame.dispose();
                    break;
    
                case "Append Data":
                    //AppendDataForm dataForm = new AppendDataForm();
                    MainForm.getAppendDateForm().setVisible(true);
                    try {
                        Bills.append_file(false, (MainForm.getAppendDateForm()).getBill());
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
    
                case "Append data, compress every record":
                    MainForm.getAppendDateForm().setVisible(true);
                    try {
                        Bills.append_file(true, (MainForm.getAppendDateForm()).getBill());
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "Delete data by key numberHouse":
                    MainForm.getKeyForm().setVisible(true);
                    try {
                        if (MainForm.getKeyForm().getkey() != null) {
                            Bills.delete_file("n-b", MainForm.getKeyForm().getkey());
                            MainForm.appendString(textPane1, "Record was deleted. List of bills now is:\n");
                            Bills.printFile(textPane1);
                        }
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException | BadLocationException e1) {
                        e1.printStackTrace();
                    }
                    break;
    
                case "Delete data by key numberApartment":
                    try {
                        if (MainForm.getKeyForm().getkey() != null) {
                            Bills.delete_file("n-f", MainForm.getKeyForm().getkey());
                            MainForm.appendString(textPane1, "Record was deleted. List of bills now is:\n");
                            Bills.printFile(textPane1);
                        }
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException | BadLocationException e1) {
                        e1.printStackTrace();
                    }
                    break;
    
                case "Delete data by key owner":
                    try {
                        if (MainForm.getKeyForm().getkey() != null) {
                            Bills.delete_file("f", MainForm.getKeyForm().getkey());
                            MainForm.appendString(textPane1, "Record was deleted. List of bills now is:\n");
                            Bills.printFile(textPane1);
                        }
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException | BadLocationException e1) {
                        e1.printStackTrace();
                    }
                    break;
    
                case "Delete data by key paymentDate":
                    try {
                        if (MainForm.getKeyForm().getkey() != null) {
                            Bills.delete_file("f", MainForm.getKeyForm().getkey());
                            MainForm.appendString(textPane1, "Record was deleted. List of bills now is:\n");
                            Bills.printFile(textPane1);
                        }
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException | BadLocationException e1) {
                        e1.printStackTrace();
                    }
                    break;
                
                case "Print data sorted (by field) numberHouse":
                    
            }
        }
    }
    
}

