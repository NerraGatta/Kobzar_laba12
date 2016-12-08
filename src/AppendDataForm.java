import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppendDataForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField OwnertextField;
    private JTextField HouseNumbertextField;
    private JLabel HouseNumberLabel;
    private JLabel OwnerLabel;
    private JTextField FlatNumbertextField;
    private JLabel FlatNumberLabel;
    private JTextField AdresstextField;
    private JLabel AdressLabel;
    private JLabel PaymentDayLabel;
    private JTextField PaymentDaytextField;
    private JLabel PaymentSumLabel;
    private JTextField PaymentSumtextField;
    private JLabel DayExpiredLabel;
    private JTextField DayExpiredtextField;
    private JLabel PenaltyPercentLabel;
    private JTextField PenaltyPercenttextField;
    private JPanel labelsTextEditors;
    
    private static Bill bill = null;
    
    public AppendDataForm(JFrame parent) {
        super(parent, true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    
        pack();
        //setVisible(true);
    }
    
    private void onOK() {
        // add your code here
    
        try {
            if (HouseNumbertextField.getText().isEmpty() ||
                    FlatNumbertextField.getText().isEmpty() ||
                    AdresstextField.getText().isEmpty() ||
                    OwnertextField.getText().isEmpty() ||
                    PaymentDaytextField.getText().isEmpty() ||
                    PaymentSumtextField.getText().isEmpty() ||
                    PenaltyPercenttextField.getText().isEmpty() ||
                    DayExpiredtextField.getText().isEmpty()) {
                throw new IllegalArgumentException("Some fields are empty!");
            }
        
            int houseNumber = Integer.parseInt(HouseNumbertextField.getText());
            int apartmentNumber = Integer.parseInt(FlatNumbertextField.getText());
            String address = AdresstextField.getText();
        
            String owner = OwnertextField.getText();
            String[] strings = owner.split(" ");
            StringBuilder sB = new StringBuilder();
            if (strings.length == 3) {
                owner = sB.append(strings[0]).append(" ")
                        .append(strings[1]).append(" ")
                        .append(strings[2]).toString();
            } else {
                throw new IllegalArgumentException("Invalid full name data!");
            }
        
            LocalDate paymentDate = LocalDate.parse(PaymentDaytextField.getText(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            double paymentSum = Double.parseDouble(PaymentSumtextField.getText());
        
            double penaltyPercent;
            strings = PenaltyPercenttextField.getText().split("%");
            if (strings.length == 1) {
                penaltyPercent = Double.parseDouble(strings[0]);
            } else {
                throw new IllegalArgumentException("Invalid penalty data!");
            }
        
            int daysExpired = Integer.parseInt(DayExpiredtextField.getText());
        
            bill = new Bill(
                    owner,
                    houseNumber,
                    apartmentNumber,
                    address,
                    paymentDate,
                    paymentSum,
                    penaltyPercent,
                    daysExpired);
        
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
       /*
        bill = new Bill(OwnertextField.getText(), Integer.parseInt(FlatNumbertextField.getText()),
                Integer.parseInt(HouseNumbertextField.getText()), AdresstextField.getText(),
                LocalDate.parse(PaymentDaytextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                Double.parseDouble(PaymentSumtextField.getText()), Double.parseDouble(PenaltyPercenttextField.getText()),
                Integer.parseInt(DayExpiredtextField.getText()));*/
        Component[] panels = this.getContentPane().getComponents();
        Component[] labels_editors = ((JPanel) panels[1]).getComponents();
        for (int i = 0; i < labels_editors.length; i++) {
            if (labels_editors[i] instanceof JTextField) {
                ((JTextField) labels_editors[i]).setText("");
            }
        }
        /*for (Component textField : ((JPanel) panels[1]).getComponents()) {
            if (textField instanceof JTextField) {
                ((JTextField) textField).setText("");
            }
        }*/
        dispose();
    }
    
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    
    public Bill getBill() {
        return bill;
    }
    
    public static void main(String[] args) {
        /*AppendDataForm dialog = new AppendDataForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);*/
    }
    
    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }
    
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        labelsTextEditors = new JPanel();
        labelsTextEditors.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(labelsTextEditors, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        labelsTextEditors.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        labelsTextEditors.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel1.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel1.add(buttonCancel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(12, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        OwnerLabel = new JLabel();
        OwnerLabel.setText("Owner");
        panel2.add(OwnerLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        OwnertextField = new JTextField();
        panel2.add(OwnertextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        HouseNumberLabel = new JLabel();
        HouseNumberLabel.setText("House number");
        panel2.add(HouseNumberLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        HouseNumbertextField = new JTextField();
        panel2.add(HouseNumbertextField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        FlatNumbertextField = new JTextField();
        panel2.add(FlatNumbertextField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        AdressLabel = new JLabel();
        AdressLabel.setText("Adress");
        panel2.add(AdressLabel, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PaymentDayLabel = new JLabel();
        PaymentDayLabel.setText("Payment day");
        panel2.add(PaymentDayLabel, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AdresstextField = new JTextField();
        panel2.add(AdresstextField, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        FlatNumberLabel = new JLabel();
        FlatNumberLabel.setText("Flat number");
        panel2.add(FlatNumberLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PaymentDaytextField = new JTextField();
        panel2.add(PaymentDaytextField, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PaymentSumtextField = new JTextField();
        panel2.add(PaymentSumtextField, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PaymentSumLabel = new JLabel();
        PaymentSumLabel.setText("Payment sum");
        panel2.add(PaymentSumLabel, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        DayExpiredLabel = new JLabel();
        DayExpiredLabel.setText("Days Expired");
        panel2.add(DayExpiredLabel, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        DayExpiredtextField = new JTextField();
        panel2.add(DayExpiredtextField, new com.intellij.uiDesigner.core.GridConstraints(11, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PenaltyPercentLabel = new JLabel();
        PenaltyPercentLabel.setText("Penalty percent (%)");
        panel2.add(PenaltyPercentLabel, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PenaltyPercenttextField = new JTextField();
        panel2.add(PenaltyPercenttextField, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }
    
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
