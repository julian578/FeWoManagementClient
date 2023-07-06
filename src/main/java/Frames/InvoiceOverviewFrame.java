package Frames;

import Model.Booking;
import Model.Client;
import Model.InvoiceLabel;
import Request.ApiData;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public InvoiceOverviewFrame(String jwt) throws JSONException, IOException, ParseException, InterruptedException {
        this.jwt = jwt;
        this.setLocationRelativeTo(null);

        this.setSize(800, 600);


        initPanel();

        jpSearch = new JPanel(new FlowLayout());
        jpSearchInvoiceNumber = new JPanel();
        jpSearchInvoiceNumber.setLayout(new BoxLayout(jpSearchInvoiceNumber, BoxLayout.Y_AXIS));
        jpSearchName = new JPanel();
        jpSearchName.setLayout(new BoxLayout(jpSearchName, BoxLayout.Y_AXIS));

        jpSearchInvoiceNumber.add(jlbSearchInvoiceNumber);
        jpSearchInvoiceNumber.add(jtfSearchInvoiceNumber);

        jpSearchName.add(jlbSearchName);
        jpSearchName.add(jtfSearchName);

        jpSearch.add(jpSearchName);
        jpSearch.add(Box.createHorizontalStrut(50));
        jpSearch.add(jpSearchInvoiceNumber);

        panel.add(jpSearch);

        panel.add(Box.createVerticalStrut(20));




        addInvoiceLabels();

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.getContentPane().add(scrollPane);
        this.setVisible(true);
    }

    public void addInvoiceLabels() throws JSONException, IOException, ParseException, InterruptedException {

        ApiData.loadBookings(jwt);

        ApiData.loadClients(jwt);


        for(Booking b : ApiData.bookingList) {


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


}
