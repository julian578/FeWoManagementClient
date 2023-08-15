package Frames;

import Data.ApiData;
import Data.ApiRequests;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONException;
import org.json.JSONObject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;

import static InvoiceCreation.EMLFileGenerator.createEMLFile;

public class LoginFrame extends JFrame {

    private JLabel jlbTitle = new JLabel("Login");

    private JLabel jlbName = new JLabel("Name");
    private JTextField jtfName = new JTextField();
    private JLabel jlbPassword = new JLabel("Passwort");
    private JPasswordField jtfPassword = new JPasswordField();

    private JButton jbtLogin = new JButton("Login");

    public LoginFrame() throws IOException, URISyntaxException {
        this.setSize(400, 150);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Login");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        File file = new File("/Users/julianjacobs/Programming/Fullstack/FeWoManagement/src/main/resources/template.docx");
        String recipientEmail = "lina.98522@gmail.com";
        String subject = "Test Email with Attachment";
        String body = "Hello, Please find the attached file.";
        String attachmentFilePath = "/Users/julianjacobs/Programming/Fullstack/FeWoManagement/src/main/resources/template.docx"; // Replace with the actual path of the .docx file
        String fromEmail = "julian.jacobs2611@gmail.com";

        //createEMLFile(fromEmail, recipientEmail, subject, body, attachmentFilePath);
        //Desktop.getDesktop().open(new File("mail.eml"));


        jlbTitle.setBounds(10, 10, 100, 40);
        jlbTitle.setFont(new Font("Serif", Font.PLAIN, 20));
        this.add(jlbTitle);

        jlbName.setBounds(10, 55, 100, 20);
        jlbName.setVisible(true);
        this.add(jlbName);

        jtfName.setBounds(10, 80, 120, 20);
        jtfName.setVisible(true);
        this.add(jtfName);

        jlbPassword.setBounds(140, 55, 100, 20);
        this.add(jlbPassword);

        jtfPassword.setBounds(140, 80, 120, 20);
        this.add(jtfPassword);

        jbtLogin.setBounds(280, 60, 80, 30);
        Dotenv dotenv = Dotenv.configure().load();
        jbtLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                String password;
                if(!(name =jtfName.getText()).equals("") && !(password = String.valueOf(jtfPassword.getPassword())).equals("")) {
                    JSONObject body = new JSONObject();
                    try {
                        body.put("name", name);
                        body.put("password", password);
                        JSONObject response = new JSONObject(ApiRequests.postRequestWithoutAuthentication(new URL(dotenv.get("API_REQUEST_PREFIX")+"/user/login"), body.toString()));
                        //new OverviewFrame(response.getString("jwt"));
                        //new NewBookingFrame(response.getString("jwt"));


                        new Dashboard(response.getString("jwt"));

                        dispose();




                    } catch (JSONException | MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        jlbName.setForeground(Color.RED);
                        jlbPassword.setForeground(Color.RED);
                        throw new RuntimeException(ex);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }


            }
        });
        this.add(jbtLogin);

        this.setVisible(true);

    }
}
