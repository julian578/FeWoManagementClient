package Model;

import Frames.BookingFrame;
import Frames.InvoiceOverviewFrame;
import Request.ApiData;
import Request.ApiRequests;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


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
                JSONObject body = new JSONObject();
                try {
                    body.put("bookingId", bookingId);
                    ApiRequests.postRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/invoice/"), body.toString(), jwt);
                    JOptionPane.showMessageDialog(null, "Rechnung erfolgreich erstellt");

                    ApiData.loadBookings(jwt);
                    frame.setBookingList((ArrayList<Booking>) ApiData.bookingList);
                    new InvoiceOverviewFrame(jwt, frame.getBookingList());
                    frame.dispose();
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

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
            JSONObject response = new JSONObject(ApiRequests.getRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/invoice/"+bookingId), jwt));

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
