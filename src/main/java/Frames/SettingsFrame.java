package Frames;

import Data.ApiData;
import Data.ApiRequests;
import Data.PropertiesConfig;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SettingsFrame extends JFrame {



    private JPanel jpMain = new JPanel();
    private JPanel jpFolderChooser = new JPanel();
    private JPanel jpDeleteUser = new JPanel();
    private JPanel jpUpdateUserRoles = new JPanel();
    private JPanel jpLeft = new JPanel();
    private JPanel jpRight = new JPanel();

    private JLabel jlbInvoiceFilePath = new JLabel("Ordner für Rechnungen:");
    private JTextField jtfSelectedInvoiceFolder = new JTextField(PropertiesConfig.getInvoiceFolderPath(), 40);
    private JButton chooseInvoiceFolder = new JButton("Ordner wählen");

    private JLabel jlbHeadlineCreateNewUser = new JLabel("Neuen Benutzer erstellen");

    private JPanel jpUserCreation = new JPanel();

    private JLabel jlbName = new JLabel("Name:");
    private JTextField jtfName = new JTextField(15);

    private JLabel jlbPassword = new JLabel("Passwort:");
    private JTextField jtfPassword = new JTextField(15);

    private JCheckBox jcbAdvancedUserRole = new JCheckBox("Erweitere Benutzer Rechte");
    private JCheckBox jcbAdminRole = new JCheckBox("Admin Rechte");

    private JButton jbtCreateUser = new JButton("Benutzer erstellen");

    private JLabel jlbHeadlineDeleteUser = new JLabel("Benutzer löschen");

    private JLabel jlbDeleteName = new JLabel("Name:");
    private JTextField jtfDeleteName = new JTextField(15);

    private JButton jbtDeleteUser = new JButton("Benutzer löschen");


    private JLabel jlbHeadlineUpdateUserRoles = new JLabel("Rechte für Benutzer hinzufügen");
    private JLabel jlbUpateName = new JLabel("Name:");
    private JTextField jtfUpdateName = new JTextField(15);
    private JCheckBox jcbUpdateAdvancedUserRoles = new JCheckBox("Erweitere Benutzer Rechte");
    private JCheckBox jcbUpdateAdminRoles = new JCheckBox("Admin Rechte");
    private JButton jbtSubmitUpdate = new JButton("Bestätigen");


    public SettingsFrame(String jwt) {


        this.setSize(800, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        jpLeft.setLayout(new BoxLayout(jpLeft, BoxLayout.Y_AXIS));
        jpRight.setLayout(new BoxLayout(jpRight, BoxLayout.Y_AXIS));
        jpFolderChooser.setLayout(new FlowLayout());
        jpDeleteUser.setLayout(new FlowLayout());
        jpMain.setLayout(new BoxLayout(jpMain, BoxLayout.Y_AXIS));

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

        jlbHeadlineCreateNewUser.setFont(new Font("Arial", Font.BOLD, 20));


        jpUserCreation.setLayout(new FlowLayout());
        jpUserCreation.add(jlbName);
        jpUserCreation.add(jtfName);


        jpUserCreation.add(Box.createHorizontalStrut(10));

        jpUserCreation.add(jlbPassword);
        jpUserCreation.add(jtfPassword);

        jpUserCreation.add(Box.createHorizontalStrut(10));

        jcbAdvancedUserRole.setSelected(false);
        jcbAdminRole.setSelected(false);
        jpUserCreation.add(jcbAdvancedUserRole);
        jpUserCreation.add(Box.createHorizontalStrut(5));
        jpUserCreation.add(jcbAdminRole);
        jbtCreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if(!jtfName.getText().isEmpty() && !jtfPassword.getText().isEmpty()) {
                    JSONObject userBody = new JSONObject();
                    try {
                        userBody.put("name", jtfName.getText());
                        userBody.put("password", jtfPassword.getText());

                        int status = ApiRequests.postRequestReturningStatusCode(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/user/register"), userBody.toString(), jwt);
                        if(status == 200) {
                            if(jcbAdminRole.isSelected() || jcbAdvancedUserRole.isSelected()) {
                                JSONObject roleBody = new JSONObject();
                                roleBody.put("name", jtfName.getText());
                                String roles[];
                                if(jcbAdminRole.isSelected()) roles = new String[]{"ADMIN", "ADVANCED_USER"};

                                else roles = new String[]{"ADVANCED_USER"};

                                roleBody.put("new_roles", roles);
                                ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/user/addRole"), roleBody.toString(), jwt);
                            }

                            JOptionPane.showMessageDialog(null, "Benutzer erfolgreich erstellt!");
                            jtfName.setText("");
                            jtfPassword.setText("");

                        } else {
                            JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        }



                    } catch (JSONException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);
                    } catch (MalformedURLException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        jpUserCreation.add(jbtCreateUser);



        jpFolderChooser.add(jpLeft);
        jpFolderChooser.add(jpRight);

        jpMain.add(jpFolderChooser);


        jlbHeadlineDeleteUser.setFont(new Font("Arial", Font.BOLD, 20));
        jpDeleteUser.add(jlbDeleteName);
        jpDeleteUser.add(jtfDeleteName);
        jbtDeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!jtfDeleteName.getText().isEmpty()) {

                    JSONObject body = new JSONObject();
                    try {
                        body.put("name", jtfDeleteName.getText());

                        int resCode = ApiRequests.postRequestReturningStatusCode(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/user/deleteByName"), body.toString(), jwt);
                        if(resCode == 200) {
                            JOptionPane.showMessageDialog(null, "Benutzer erfolgreich gelöscht!");
                            jtfDeleteName.setText("");
                        } else if(resCode == 404) {
                            JOptionPane.showMessageDialog(null, "Benutzer konnte nicht gefunden werden!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        }
                    }
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);

                    } catch (JSONException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        jpDeleteUser.add(jbtDeleteUser);


        jlbHeadlineCreateNewUser.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        jpMain.add(jlbHeadlineCreateNewUser);
        jpMain.add(jpUserCreation);

        jlbHeadlineDeleteUser.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        jpMain.add(jlbHeadlineDeleteUser);
        jpMain.add(jpDeleteUser);


        jpUpdateUserRoles.add(jlbUpateName);
        jpUpdateUserRoles.add(jtfUpdateName);
        jpUpdateUserRoles.add(jcbUpdateAdvancedUserRoles);
        jpUpdateUserRoles.add(jcbUpdateAdminRoles);

        jbtSubmitUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jcbUpdateAdminRoles.isSelected() || jcbUpdateAdvancedUserRoles.isSelected() && !jtfUpdateName.getText().isEmpty()) {

                    try {
                        JSONObject body = new JSONObject();
                        body.put("name", jtfUpdateName.getText());
                        String roles[];
                        if(jcbUpdateAdminRoles.isSelected()) roles = new String[]{"ADMIN", "ADVANCED_USER"};
                        else roles = new String[]{"ADVANCED_USER"};
                        body.put("new_roles", roles);

                        int resCode = ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/user/addRole"), body.toString(), jwt);


                        if(resCode == 200) {
                            JOptionPane.showMessageDialog(null, "Rechte erfolgreich hinzugefügt. !");
                            jtfUpdateName.setText("");
                            jcbUpdateAdminRoles.setSelected(false);
                            jcbUpdateAdminRoles.setSelected(false);
                        } else if(resCode == 404) {
                            JOptionPane.showMessageDialog(null, "Benutzer wurde nicht gefunden!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        }

                    } catch (JSONException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);
                    } catch (MalformedURLException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen!");
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        jpUpdateUserRoles.add(jbtSubmitUpdate);


        jlbHeadlineUpdateUserRoles.setFont(new Font("Arial", Font.BOLD, 20));
        jlbHeadlineUpdateUserRoles.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        jpMain.add(jlbHeadlineUpdateUserRoles);

        jpMain.add(jpUpdateUserRoles);

        this.getContentPane().add(jpMain);
        this.setVisible(true);

    }

}
