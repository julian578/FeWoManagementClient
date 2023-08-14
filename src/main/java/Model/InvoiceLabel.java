package Model;

import Data.PropertiesConfig;
import Frames.BookingFrame;
import Frames.InvoiceOverviewFrame;
import InvoiceCreation.EMLFileGenerator;
import InvoiceCreation.WordDocumentGenerator;
import Data.ApiData;
import Data.ApiRequests;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class InvoiceLabel extends JPanel {


    private JLabel jlbName;

    private JLabel jlbDate;
    private JLabel jlbInvoiceCreatedAndSent = new JLabel("Rechnung erstellt und verschickt.");

    private JLabel jlbBookingNumber = new JLabel();

    private JButton jbtInvoice = new JButton("Rechnung erstellen");
    private JButton jbtSendInvoice = new JButton("Rechnung verschicken");

    private JButton jbtShowBookingDetails = new JButton("Buchung anzeigen");

    private Locale loc = new Locale("de", "DE");
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);

    private JPanel jpBookingData = new JPanel();

    public InvoiceLabel(String bookingId, Date arrivalDate, Date leavingDate, int invoiceStatus, Client client, String jwt, InvoiceOverviewFrame frame) throws JSONException, IOException {

        this.setLayout(new FlowLayout());
        this.setSize(600, 80);

        jpBookingData.setLayout(new BoxLayout(jpBookingData, BoxLayout.Y_AXIS));

        jlbName = new JLabel(client.getFullName());
        jpBookingData.add(jlbName);



        jlbDate = new JLabel(dateFormat.format(arrivalDate) + " - " + dateFormat.format(leavingDate));
        jpBookingData.add(jlbDate);

        if(invoiceStatus > 0) {
            setJlbBookingNumber(bookingId, jwt);
        }


        this.add(jpBookingData);

        this.add(Box.createHorizontalStrut(50));

        this.add(jlbInvoiceCreatedAndSent);
        jlbInvoiceCreatedAndSent.setVisible(false);

        this.add(jbtInvoice);
        jbtInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (ApiRequests.hasCertainRole(jwt, "ADVANCED_USER")) {

                        WordDocumentGenerator.createInvoice(jwt, bookingId);

                        frame.setBookingList((ArrayList<Booking>) ApiData.bookingList);
                        try {
                            new InvoiceOverviewFrame(jwt, frame.getBookingList());
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Sie haben nicht die nötigen Berechtigungen für diese Operation!");
                    }


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
            });


        jbtInvoice.setVisible(false);


        jbtSendInvoice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (ApiRequests.hasCertainRole(jwt, "ADVANCED_USER")) {
                        if (!client.getEmail().equals("-") && !client.getEmail().equals("")) {
                            String recipientEmail = client.getEmail();
                            LocalDate currentDate = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            String formattedDate = currentDate.format(formatter);
                            String subject = "Rechnung " + jlbBookingNumber.getText().substring(17) + " vom " + formattedDate + " Haus Cornelia";
                            String body = "Guten Tag, \n anbei erhalten Sie die Rechnung zu ihrer Buchung im Haus Cornelia. \nMit freundlichen Grüßen \nKornelia Heinrici";
                            String attachmentFilePath = PropertiesConfig.getInvoiceFolderPath() + "/Rechnung(invoice)_" + client.getFullName() + "_" + jlbBookingNumber.getText().substring(17) + ".docx";
                            File attFile = new File(attachmentFilePath);
                            if (attFile.exists()) {
                                //REPLACE
                                String fromEmail = "julian.jacobs2611@gmail.com";

                                try {
                                    EMLFileGenerator.createEMLFile(fromEmail, recipientEmail, subject, body, attachmentFilePath);

                                    //update invoice status
                                    JSONObject updateBody = new JSONObject();
                                    updateBody.put("invoiceStatus", 2);
                                    System.out.println(jwt);
                                    int resCode = ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/booking/" + bookingId), updateBody.toString(), jwt);
                                    if (resCode == 200) {
                                        JOptionPane.showMessageDialog(null, "Rechnung erfolgreich verschickt");
                                    }

                                    Desktop.getDesktop().open(new File("mail.eml"));

                                    ApiData.loadBookings(jwt);
                                    frame.setBookingList((ArrayList<Booking>) ApiData.bookingList);
                                    try {
                                        new InvoiceOverviewFrame(jwt, frame.getBookingList());
                                    } catch (JSONException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (ParseException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    frame.dispose();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                } catch (JSONException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ParseException ex) {
                                    throw new RuntimeException(ex);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Rechnung konnte nicht gefunden werden. Email bitte manuell verschicken.");
                            }


                        } else {
                            JOptionPane.showMessageDialog(null, "Zu der Buchung ist keine Email-Adresse hinterlegt. Bitte hinzufügen");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Sie haben nicht die nötigen Berechtigungen für diese Operation!");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.add(jbtSendInvoice);
        jbtSendInvoice.setVisible(false);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(blackBorder);

        this.add(jbtShowBookingDetails);
        jbtShowBookingDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    new BookingFrame(ApiData.getBookingFromBookingId(bookingId), jwt);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        if(invoiceStatus == 0) {
            jbtInvoice.setVisible(true);
        } else if(invoiceStatus == 1) {
            jbtSendInvoice.setVisible(true);
        } else {
            jlbInvoiceCreatedAndSent.setVisible(true);
        }
        this.setVisible(true);

    }

    private String loadInvoiceId(String bookingId, String jwt) throws IOException, JSONException {

        try {
            Dotenv dotenv = Dotenv.configure().load();
            org.json.JSONObject response = new org.json.JSONObject(ApiRequests.getRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/invoice/"+bookingId),  jwt));

            return response.get("invoiceId").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public void rerenderPanel() {
        jpBookingData.repaint();
        this.repaint();

    }


    public void setJlbBookingNumber(String bookingId, String jwt) throws JSONException, IOException {
        jlbBookingNumber.setText("Rechnungsnummer: " + loadInvoiceId(bookingId, jwt));
        jpBookingData.add(jlbBookingNumber);
    }
}
