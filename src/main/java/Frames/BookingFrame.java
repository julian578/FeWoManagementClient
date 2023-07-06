package Frames;

import Model.Booking;
import Model.Client;
import Request.ApiData;
import Request.ApiRequests;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Locale;


//BookingFrame contains all information of a booking
public class BookingFrame extends JFrame {
    private Booking booking;
    private String jwt;
    private Client client;
    private JPanel panel;
    private JLabel numberOfAdults = new JLabel("<html> Anzahl Erwachsene: <br>");
    private JLabel numberOfChildren;
    private JLabel numberOfDogs;

    private JLabel arrivalDate;
    private JLabel leavingDate;
    private JLabel totalPrice;

    private JLabel nameOfBookingPerson;

    private JLabel mobilePhone;
    private JLabel phone;
    private JLabel email;
    private JLabel street;
    private JLabel postalCode;
    private JLabel country;
    private JLabel taxId;

    private Locale loc = new Locale("de", "DE");
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);

    private JButton jbtDelteBooking = new JButton("Buchung löschen");

    public BookingFrame(Booking belegung,String jwt) throws JSONException, IOException {
        this.booking = belegung;
        this.jwt = jwt;
        this.client = ApiData.clientList.get(belegung.getId());
        //this.setTitle("Buchung von " + booking.getNameOfBookingPerson());
        this.setSize(800, 600);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setLayout(null);
        panel.setVisible(true);

        numberOfAdults.setText("Anzahl Erwachsene: "+ booking.getNumberOfAdults());
        numberOfAdults.setBounds(100, 50, 200, 50);
        panel.add(numberOfAdults);

        numberOfChildren = new JLabel("Anzahl Kinder: " + booking.getNumberOfChildren());
        numberOfChildren.setBounds(100, 100, 200, 50);
        panel.add(numberOfChildren);

        numberOfDogs = new JLabel("Anzahl Hunde: " + booking.getNumberOfAnimals());
        numberOfDogs.setBounds(100, 150, 200, 50);
        panel.add(numberOfDogs);

        arrivalDate = new JLabel("Anreise: " + dateFormat.format(booking.getArrivingDate()));
        arrivalDate.setBounds(100, 200, 200, 50);
        panel.add(arrivalDate);

        leavingDate = new JLabel("Abreise: " + dateFormat.format(booking.getLeavingDate()));
        leavingDate.setBounds(100, 250, 200, 50);
        panel.add(leavingDate);

        totalPrice = new JLabel("Gesamtpreis: " + booking.getTotalPrice() + "€");
        totalPrice.setBounds(100, 300, 200, 50);
        panel.add(totalPrice);

        nameOfBookingPerson = new JLabel(client.getFullName());
        nameOfBookingPerson.setBounds(420, 50, 200, 50);
        panel.add(nameOfBookingPerson);


        mobilePhone = new JLabel("Mobil: "+client.getMobilePhone());
        if(client.getMobilePhone() == null) {
            mobilePhone.setText("Mobil: ");
        }
        mobilePhone.setBounds(420, 100, 200, 50);
        panel.add(mobilePhone);

        phone = new JLabel("Tel.: "+client.getPhone());
        if(client.getPhone() == null) phone.setText("Tel.:");
        phone.setBounds(420, 150, 200, 50);
        panel.add(phone);

        email = new JLabel("Email: "+client.getEmail());
        email.setBounds(420, 200, 200, 50);
        panel.add(email);



        street = new JLabel(client.getStreet() + " " + client.getHouseNumber());
        street.setBounds(420, 250, 200, 20);
        panel.add(street);

        postalCode = new JLabel(client.getPostalCode() + " " + client.getCity());
        postalCode.setBounds(420, 271, 200, 20);
        panel.add(postalCode);

        country = new JLabel(client.getCountry());
        country.setBounds(420, 292, 200, 20);
        panel.add(country);

        taxId = new JLabel("SteurId: " + client.getTaxId());
        if(client.getTaxId() == null) taxId.setText("Steuer Id: ");
        taxId.setBounds(420, 320, 200, 20);
        panel.add(taxId);


        //remove booking by clicking the button
        jbtDelteBooking.setBounds(125, 400, 150, 50);
        jbtDelteBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int resCode = ApiRequests.deleteRequest(new URL("http://localhost:3000/api/booking/delete/"+belegung.getId()), jwt);
                    if(resCode == 200) {
                        JOptionPane.showMessageDialog(null, "Buchung erfolgreich gelöscht");
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(jbtDelteBooking);

        this.add(panel);
        this.setVisible(true);
    }



}
