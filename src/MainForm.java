import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * Created by nera_gatta on 06.12.2016.
 */
public class MainForm extends JFrame {
    
    private JFrame mainFrame;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu submenu;
    private JMenuItem menuItem;
    private JPanel mainPanel;
    private JTextPane textPane1;
    private JPanel statusPanel;
    private JLabel statusPanelLabel;
    
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
        
        //appendDataForm = new AppendDataForm(mainFrame);
        //keyForm = new KeyForm(mainFrame);
        
        menuBar = new JMenuBar();
        
        menu = new JMenu("File");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("New...");
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Open");
        menu.add(menuItem);
        menu.addSeparator();
        
        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);
        
        menu = new JMenu("Commands");
        menuBar.add(menu);
        
        menu.setEnabled(false);
        
        menuItem = new JMenuItem("Append Data");
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Append data, compress every record");
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Delete all data");
        menu.add(menuItem);
    
        menuItem = new JMenuItem("Print data unsorted");
        menu.add(menuItem);
    
        submenu = new JMenu("Delete data by key");
        menu.add(submenu);
        addFieldsButtons();
    
        submenu = new JMenu("Print data sorted (by key)");
        menu.add(submenu);
        addFieldsButtons();
    
        submenu = new JMenu("Print data reverse sorted (by key)");
        menu.add(submenu);
        addFieldsButtons();
    
        submenu = new JMenu("Find records by key");
        menu.add(submenu);
        addFieldsButtons();
    
        submenu = new JMenu("Find records > key");
        menu.add(submenu);
        addFieldsButtons();
    
        submenu = new JMenu("Find records < key");
        menu.add(submenu);
        menuItem = new JMenuItem("numberHouse");
        addFieldsButtons();
    
       //menu = new JMenu("About");
        //menuBar.add(menu);
        
        for (Component menu : menuBar.getComponents()) {
            menu.addMouseMotionListener(new menuItemMouseMotionListener());
            
            for (Component menuItem : ((JMenu) menu).getMenuComponents()) {
                if (!(menuItem instanceof JMenu) && !(menuItem instanceof JPopupMenu.Separator)) {
                    menuItem.addMouseMotionListener(new menuItemMouseMotionListener());
                    
                    ((JMenuItem) menuItem).setActionCommand(((JMenuItem) menuItem).getText());
                    ((JMenuItem) menuItem).addActionListener(new menuItemListener());
                } else {
                    if (!(menuItem instanceof JPopupMenu.Separator)) {
                        menuItem.addMouseMotionListener(new menuItemMouseMotionListener());
                        
                        for (Component submenuItem : ((JMenu) menuItem).getMenuComponents()) {
                            ((JMenuItem) submenuItem).setActionCommand(((JMenu) menuItem).getText() + " " + ((JMenuItem) submenuItem).getText());
                            ((JMenuItem) submenuItem).addActionListener(new menuItemListener());
                            submenuItem.addMouseMotionListener(new menuItemMouseMotionListener());
                        }
                    }
                }
            }
        }
    
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(mainPanel);
    }
    
    private void addFieldsButtons() {
        menuItem = new JMenuItem("BuildingNumber");
        submenu.add(menuItem);
        menuItem = new JMenuItem("ApartmentNumber");
        submenu.add(menuItem);
        menuItem = new JMenuItem("Owner");
        submenu.add(menuItem);
        menuItem = new JMenuItem("PaymentDate");
        submenu.add(menuItem);
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane1 = new JTextPane();
        textPane1.setBackground(new Color(-9344069));
        scrollPane1.setViewportView(textPane1);
        statusPanel = new JPanel();
        statusPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(statusPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statusPanelLabel = new JLabel();
        statusPanelLabel.setText("Label");
        statusPanel.add(statusPanelLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
    
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
    
    private class menuItemListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "New...":
                    try {
                        String filename = new NewFileForm().getFilename();
                        if (!filename.isEmpty()) {
                            if (checkFiles()) {
                                JOptionPane.showMessageDialog(mainFrame, "Your files will be backed up and deleted!");
                                Bills.delete_file();
                            }
                            for (Component menu : menuBar.getComponents()) {
                                if (!menu.isEnabled()) {
                                    menu.setEnabled(true);
                                }
                            }
                        } else {
                            throw new IllegalArgumentException("You don't write a filename!");
                        }
                        setFilenamePaths(new File(filename).getAbsolutePath());
                    } catch (IllegalArgumentException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
                case "Open":
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(mainFrame);
                    
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        setFilenamePaths(fileChooser.getSelectedFile().getAbsolutePath());
                        
                        try {
                            Bills.printFile(textPane1); //filepath
                        } catch (IOException | ClassNotFoundException | BadLocationException e1) {
                            JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                        }
                    }
                    for (Component menu : menuBar.getComponents()) {
                        if (!menu.isEnabled()) {
                            menu.setEnabled(true);
                        }
                    }
                    break;
                
                case "Exit":
                    mainFrame.dispose();
                    break;
    
                /*case "About":
                    JOptionPane.showMessageDialog(mainFrame, "Made by Uliana Kobzar");
                    break;*/
    
                case "Append Data":
                    try {
                        Bills.append_file(false, new AppendDataForm().getBill());
                        Bills.printFile(textPane1);
                        appendString(textPane1, "\n");
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException | BadLocationException | DateTimeParseException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
                case "Append data, compress every record":
                    try {
                        Bills.append_file(true, new AppendDataForm().getBill());
                        Bills.printFile(textPane1);
                        appendString(textPane1, "\n");
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException | BadLocationException | DateTimeParseException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
                case "Delete all data":
                    try {
                        Bills.delete_file();
                        JOptionPane.showMessageDialog(mainFrame, "All data was deleted");
                        Bills.printFile(textPane1);
                    } catch (IOException | ClassNotFoundException | BadLocationException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
    
                case "Print data unsorted":
                    try {
                        Bills.printFile(textPane1);
                        appendString(textPane1, "\n");
                    } catch (IOException | ClassNotFoundException | BadLocationException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
                case "Delete data by key BuildingNumber":
                case "Delete data by key ApartmentNumber":
                case "Delete data by key Owner":
                case "Delete data by key PaymentDate":
                    try {
                        Bills.delete_file(e.getActionCommand().substring(e.getActionCommand().lastIndexOf("key") + 4), new KeyForm().getkey());
                        MainForm.appendString(textPane1, "Record was deleted. List of bills now is:\n");
                        Bills.printFile(textPane1);
                        appendString(textPane1, "\n");
                    } catch (KeyNotUniqueException | ClassNotFoundException | IOException | BadLocationException | IllegalArgumentException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
                
                case "Print data sorted (by key) BuildingNumber":
                case "Print data sorted (by key) ApartmentNumber":
                case "Print data sorted (by key) Owner":
                case "Print data sorted (by key) PaymentDate":
                    try {
                        Bills.printFileSorted(textPane1, e.getActionCommand().substring(e.getActionCommand().lastIndexOf("key") + 5), false);
                        appendString(textPane1, "\n");
                    } catch (BadLocationException | ClassNotFoundException | IOException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
    
                case "Print data reverse sorted (by key) BuildingNumber":
                case "Print data reverse sorted (by key) ApartmentNumber":
                case "Print data reverse sorted (by key) Owner":
                case "Print data reverse sorted (by key) PaymentDate":
                    try {
                        Bills.printFileSorted(textPane1, e.getActionCommand().substring(e.getActionCommand().lastIndexOf("key") + 5), true);
                        appendString(textPane1, "\n");
                    } catch (BadLocationException | ClassNotFoundException | IOException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
                case "Find records by key BuildingNumber":
                case "Find records by key ApartmentNumber":
                case "Find records by key Owner":
                case "Find records by key PaymentDate":
                    try {
                        String key = new KeyForm().getkey();
                        if (!key.isEmpty()) {
                            Bills.findByKey(textPane1, e.getActionCommand().substring(e.getActionCommand().lastIndexOf("key") + 4), key);
                            appendString(textPane1, "\n");
                        }
                    } catch (ClassNotFoundException | IOException | BadLocationException | IllegalArgumentException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
    
                case "Find records > key BuildingNumber":
                case "Find records > key ApartmentNumber":
                case "Find records > key Owner":
                case "Find records > key PaymentDate":
                    try {
                        String key = new KeyForm().getkey();
                        if (!key.isEmpty()) {
                            Bills.findByKey(textPane1, e.getActionCommand().substring(e.getActionCommand().lastIndexOf("key") + 4), key, new KeyCompReverse());
                        }
                        appendString(textPane1, "\n");
                    } catch (BadLocationException | ClassNotFoundException | IOException | IllegalArgumentException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
    
                case "Find records < key BuildingNumber":
                case "Find records < key ApartmentNumber":
                case "Find records < key Owner":
                case "Find records < key PaymentDate":
                    try {
                        String key = new KeyForm().getkey();
                        if (!key.isEmpty()) {
                            Bills.findByKey(textPane1, e.getActionCommand().substring(e.getActionCommand().lastIndexOf("key") + 4), key, new KeyComp());
                            appendString(textPane1, "\n");
                        }
                    } catch (BadLocationException | ClassNotFoundException | IOException | IllegalArgumentException e1) {
                        JOptionPane.showMessageDialog(mainFrame, e1.getMessage());
                    }
                    break;
            }
        }
        
        private void setFilenamePaths(String filePath) {
            filenamePath = filePath;
            filenameBackPath = filenamePath.substring(0, filenamePath.lastIndexOf(".")) + ".~dat";
            filenameIdxPath = filenamePath.substring(0, filenamePath.lastIndexOf(".")) + ".idx";
            filenameIdxBackPath = filenamePath.substring(0, filenamePath.lastIndexOf(".")) + ".~idx";
        }
        
        private boolean checkFiles() {
            return !(filenamePath == null && filenameIdxPath == null);
        }
        
        private void appendString(JTextPane textPane, String str) throws BadLocationException {
            StyledDocument styledDocument = textPane.getStyledDocument();
            styledDocument.insertString(styledDocument.getLength(), str, null);
        }
    }
    
    private class menuItemMouseMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            statusPanelLabel.setText(((JMenuItem) e.getComponent()).getText());
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
            statusPanelLabel.setText(((JMenuItem) e.getComponent()).getText());
        }
    }
    
}

