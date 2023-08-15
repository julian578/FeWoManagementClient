package Frames;

import Model.Booking;
import Model.Client;
import Model.InvoiceLabel;
import Data.ApiData;
import Data.ApiRequests;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


//InvoiceOverviewFrame invoice status of all bookings and allows the user to generate an invoice for a booking
public class InvoiceOverviewFrame extends JFrame {


    private String jwt;
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private HashMap<String, Client> clients;
    private JPanel panel;

    private JPanel jpSearch;
    private JPanel jpSearchInvoiceNumber;
    private JPanel jpSearchName;
    private JLabel jlbSearchInvoiceNumber = new JLabel("Suchen nach Rechnungsnummer");

    private JLabel jlbSearchName = new JLabel("Suchen nach Name");
    private JTextField jtfSearchInvoiceNumber = new JTextField();
    private JTextField jtfSearchName = new JTextField();

    private JButton jbtSearchname = new JButton("Suchen");

    private JButton jbtSearchInvoiceNumber = new JButton("Suchen");
    public InvoiceOverviewFrame(String jwt, ArrayList<Booking> bookings) throws JSONException, IOException, ParseException, InterruptedException {
        this.jwt = jwt;
        this.setLocationRelativeTo(null);

        this.setSize(800, 600);

        this.bookingList = bookings;

        initPanel();

        jpSearch = new JPanel(new FlowLayout());
        jpSearchInvoiceNumber = new JPanel();
        jpSearchInvoiceNumber.setLayout(new BoxLayout(jpSearchInvoiceNumber, BoxLayout.Y_AXIS));
        jpSearchName = new JPanel();
        jpSearchName.setLayout(new BoxLayout(jpSearchName, BoxLayout.Y_AXIS));

        jpSearchInvoiceNumber.add(jlbSearchInvoiceNumber);
        jpSearchInvoiceNumber.add(jtfSearchInvoiceNumber);
        jpSearchInvoiceNumber.add(jbtSearchInvoiceNumber);

        jbtSearchInvoiceNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!jtfSearchInvoiceNumber.getText().isEmpty() && !jtfSearchInvoiceNumber.getText().contains(" ")) {
                        searchByInvoiceNumber(jtfSearchInvoiceNumber.getText());
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        jbtSearchname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!jtfSearchName.getText().isEmpty()) {
                        searchByName(jtfSearchName.getText());
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        jpSearchName.add(jlbSearchName);
        jpSearchName.add(jtfSearchName);
        jpSearchName.add(jbtSearchname);


        jpSearch.add(jpSearchName);
        jpSearch.add(Box.createHorizontalStrut(50));
        jpSearch.add(jpSearchInvoiceNumber);

        panel.add(jpSearch);

        panel.add(Box.createVerticalStrut(20));




        addInvoiceLabels(bookings);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.getContentPane().add(scrollPane);
        this.setVisible(true);
    }

    public void addInvoiceLabels(ArrayList<Booking> bookings) throws JSONException, IOException, ParseException, InterruptedException {

        //ApiData.loadBookings(jwt);

        ApiData.loadClients(jwt);


        for(Booking b : bookings) {

            Client c = ApiData.clientList.get(b.getId());
            panel.add(new InvoiceLabel(b.getId(), b.getArrivingDate(), b.getLeavingDate(), b.getInvoiceStatus(), c ,jwt, this));
            panel.add(Box.createVerticalStrut(50));
        }
        this.repaint();
    }


    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    }

    private void searchByName(String name) throws IOException, JSONException, ParseException, InterruptedException {


        Dotenv dotenv = Dotenv.configure().load();
        JSONObject body = new JSONObject();
        body.put("name", name);
        JSONArray bookings = new JSONArray(ApiRequests.postRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/booking/name"), body.toString(), jwt).toString());

        new InvoiceOverviewFrame(jwt,ApiData.jsonArrayToBookingList(bookings));
        this.dispose();
    }

    private void searchByInvoiceNumber(String invoiceNumber) throws IOException, JSONException, ParseException, InterruptedException {
        Dotenv dotenv = Dotenv.configure().load();

        JSONArray bookings = new JSONArray(ApiRequests.getRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/booking/invoice/"+invoiceNumber),  jwt).toString());

        new InvoiceOverviewFrame(jwt,ApiData.jsonArrayToBookingList(bookings));
        this.dispose();
    }

    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(ArrayList<Booking> bookingList) {
        this.bookingList = bookingList;
    }
}
