package Frames;

import Data.PropertiesConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {



    private JPanel jpMain = new JPanel();
    private JPanel jpLeft = new JPanel();
    private JPanel jpRight = new JPanel();

    private JLabel jlbInvoiceFilePath = new JLabel("Ordner für Rechnungen:");
    private JTextField jtfSelectedInvoiceFolder = new JTextField(PropertiesConfig.getInvoiceFolderPath(), 40);
     private JButton chooseInvoiceFolder = new JButton("Ordner wählen");




    public SettingsFrame(String jwt) {


        this.setSize(800, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        jpLeft.setLayout(new BoxLayout(jpLeft, BoxLayout.Y_AXIS));
        jpRight.setLayout(new BoxLayout(jpRight, BoxLayout.Y_AXIS));
        jpMain.setLayout(new FlowLayout());

        chooseInvoiceFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int result = fileChooser.showDialog(SettingsFrame.this, "Folder Selected");
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFolderPath = fileChooser.getSelectedFile().getAbsolutePath();

                    PropertiesConfig.setInvoiceFolderPath(selectedFolderPath);

                    jtfSelectedInvoiceFolder.setText(selectedFolderPath);
                }
            }
        });

        jpLeft.add(jlbInvoiceFilePath);
        jpLeft.add(chooseInvoiceFolder);
        jpRight.add(jtfSelectedInvoiceFolder);

        jpLeft.add(Box.createVerticalStrut(20));
        jpRight.add(Box.createVerticalStrut(20));





        jpMain.add(jpLeft);
        jpMain.add(jpRight);

        this.getContentPane().add(jpMain);
        this.setVisible(true);

    }

}
