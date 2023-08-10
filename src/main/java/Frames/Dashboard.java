package Frames;

import Model.Booking;
import Model.Client;
import Data.ApiData;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


//Dashboard is opened after log in; user can chosse between Calendar view and invoice mangement tool
public class Dashboard extends JFrame {

    private String jwt;
    private JButton jbtBookingOverview = new JButton("Kalenderansicht der Buchungen");
    private JButton jbtInvoiceOverview = new JButton("Rechnungen verwalten");

    private JButton jbtSettings = new JButton("Einstellungen");

    private ArrayList<Booking> bookingList = new ArrayList<>();
    private HashMap<String, Client> clientList = new HashMap<String, Client>();

    public Dashboard(String jwt) throws JSONException, IOException, ParseException, InterruptedException {
        this.jwt = jwt;
        ApiData.loadBookings(jwt);
        ApiData.loadClients(jwt);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        jbtBookingOverview.setBounds(10, 10, 400, 80);
        jbtBookingOverview.setFont(new Font("Arial", Font.PLAIN, 25));
        jbtBookingOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new OverviewFrame(jwt);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.add(jbtBookingOverview);

        jbtInvoiceOverview.setBounds(10, 200, 400, 80);
        jbtInvoiceOverview.setFont(new Font("Arial", Font.PLAIN, 25));
        jbtInvoiceOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ApiData.loadBookings(jwt);
                    new InvoiceOverviewFrame(jwt, (ArrayList<Booking>) ApiData.bookingList);
                } catch (JSONException ex) {
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
        this.add(jbtInvoiceOverview);

        jbtSettings.setBounds(10, 300, 400, 80);
        jbtSettings.setFont(new Font("Arial", Font.PLAIN, 25));
        jbtSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsFrame(jwt);
            }
        });

        this.add(jbtSettings);
        this.setVisible(true);
    }


}
