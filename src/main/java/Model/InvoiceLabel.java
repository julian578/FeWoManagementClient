package Model;

import Frames.BookingFrame;
import Frames.InvoiceOverviewFrame;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
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




                try(CloseableHttpClient httpClient = HttpClients.createDefault()) {

                    HttpPost httpPost = new HttpPost(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/invoice/");
                    httpPost.addHeader("Content-Type", "application/json");
                    httpPost.addHeader("Authorization", "Bearer "+jwt);

                    // Set the dynamic data as the request body
                    Map<String, Object> dynamicData = new HashMap<>();
                    dynamicData.put("bookingId", bookingId);



                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonData = objectMapper.writeValueAsString(dynamicData);
                    StringEntity requestEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
                    httpPost.setEntity(requestEntity);

                    // Send the request and get the response
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity responseEntity = response.getEntity();




                    // Check if the response status is successful
                    if (response.getStatusLine().getStatusCode() == 200) {

                        // Convert the response entity to a byte array
                        byte[] binaryData = EntityUtils.toByteArray(responseEntity);

                        //get invoice number for the invoice file name
                        JSONObject invoiceRes = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/invoice/"+bookingId), jwt));

                        int invoiceNumber = (int) invoiceRes.get("invoiceId");

                        //get the client name for the invoice file name


                        String clientName = ApiData.clientList.get(bookingId).getFullName();

                        WordDocumentGenerator.generateInvoice(binaryData, clientName, invoiceNumber);


                        JOptionPane.showMessageDialog(null, "Rechnung erfolgreich erstellt");
                        ApiData.loadBookings(jwt);




                    } else {
                        System.out.println("Failed to receive the Word document. Status code: " + response.getStatusLine().getStatusCode());
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen.");
                    }



                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (TemplateException ex) {
                    throw new RuntimeException(ex);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
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


            }
        });
        jbtInvoice.setVisible(false);

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
            org.json.JSONObject response = new org.json.JSONObject(ApiRequests.getRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/invoice/"+bookingId), jwt));

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
