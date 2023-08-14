package Frames;

import InvoiceCreation.WordDocumentGenerator;
import Model.Booking;
import Model.Client;
import Data.ApiData;
import Data.ApiRequests;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    private JLabel jlbNameOfBookingPerson = new JLabel("Name der buchenden Person:");
    private JTextField nameOfBookingPerson;

    private JLabel jlbMobilePhone = new JLabel("Mobil:");
    private JTextField mobilePhone;

    private JLabel jlbPhone = new JLabel("Tel.:");
    private JTextField phone;

    private JLabel jlbEmail = new JLabel("Email:");
    private JTextField email;
    private JLabel jlbStreet = new JLabel("Straße + Hausnummer:");
    private JTextField street;
    private JTextField jtfHouseNumber;

    private JLabel jlbPostalCodeAndCity = new JLabel("PLZ + Stadt:");
    private JTextField postalCode;
    private JTextField city;

    private JLabel jlbCounty = new JLabel("Land:");
    private JTextField country;

    private JLabel jlbTaxId = new JLabel("Steuer ID:");
    private JTextField taxId;

    private Locale loc = new Locale("de", "DE");
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);

    private JButton jbtUpdateClient = new JButton("Daten aktualisieren");
    private JButton jbtDelteBooking = new JButton("Buchung löschen");


    private JButton jbtCreateInvoice = new JButton("Rechnung erstellen");

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


        jlbNameOfBookingPerson.setBounds(501, 30, 200, 20);
        nameOfBookingPerson = new JTextField(client.getFullName());
        nameOfBookingPerson.setBounds(501, 50, 200, 20);
        panel.add(nameOfBookingPerson);
        panel.add(jlbNameOfBookingPerson);


        jlbMobilePhone.setBounds(350, 100, 150, 20);
        mobilePhone = new JTextField(client.getMobilePhone());
        mobilePhone.setBounds(501, 100, 200, 20);
        panel.add(mobilePhone);
        panel.add(jlbMobilePhone);

        jlbPhone.setBounds(350, 150, 150, 20);
        phone = new JTextField(client.getPhone());
        phone.setBounds(501, 150, 200, 20);
        panel.add(phone);
        panel.add(jlbPhone);

        jlbEmail.setBounds(350, 200, 150, 20);
        email = new JTextField(client.getEmail());
        email.setBounds(501, 200, 200, 20);
        panel.add(email);
        panel.add(jlbEmail);


        jlbStreet.setBounds(350, 250, 150, 20);
        street = new JTextField(client.getStreet());
        street.setBounds(501, 250, 150, 20);
        jtfHouseNumber = new JTextField(client.getHouseNumber());
        jtfHouseNumber.setBounds(652, 250, 48, 20);
        panel.add(jtfHouseNumber);
        panel.add(street);
        panel.add(jlbStreet);



        jlbPostalCodeAndCity.setBounds(350, 271, 150, 20);
        postalCode = new JTextField(client.getPostalCode());
        postalCode.setBounds(501, 271, 100, 20);
        city = new JTextField(client.getCity());
        city.setBounds(602, 271, 98, 20);
        panel.add(city);
        panel.add(postalCode);
        panel.add(jlbPostalCodeAndCity);

        jlbCounty.setBounds(350, 292, 150, 20);
        country = new JTextField(client.getCountry());
        country.setBounds(501, 292, 200, 20);
        panel.add(country);
        panel.add(jlbCounty);

        jlbTaxId.setBounds(350, 320, 150, 20);
        taxId = new JTextField(client.getTaxId());
        taxId.setBounds(501, 320, 200, 20);
        panel.add(taxId);
        panel.add(jlbTaxId);

        jbtUpdateClient.setBounds(300, 400,150, 50);
        jbtUpdateClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Dotenv dotenv = Dotenv.configure().load();

                    System.out.println(jwt);
                    validateUpdateInputs();
                    JSONObject body = new JSONObject();
                    body.put("fullName", nameOfBookingPerson.getText());
                    body.put("mobilePhone", mobilePhone.getText());
                    body.put("phone", phone.getText());
                    body.put("email", email.getText());
                    body.put("street", street.getText());
                    body.put("houseNumber", jtfHouseNumber.getText());
                    body.put("postalCode", postalCode.getText());
                    body.put("city", city.getText());
                    body.put("country", country.getText());
                    body.put("taxId", taxId.getText());

                    ApiRequests.postRequestReturningStatusCode(new URL(dotenv.get("API_REQUEST_PREFIX")+"/booking/client/"+client.getId()), body.toString(), jwt);
                    JOptionPane.showMessageDialog(null, "Erfolgreich aktualisiert!");
                    ApiData.loadBookings(jwt);
                    ApiData.loadClients(jwt);



                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        panel.add(jbtUpdateClient);


        //remove booking by clicking the button
        jbtDelteBooking.setBounds(125, 400, 150, 50);
        jbtDelteBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Dotenv dotenv = Dotenv.configure().load();

                    if(ApiRequests.hasCertainRole(jwt, "ADVANCED_USER")) {
                        int resCode = ApiRequests.deleteRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/booking/delete/"+belegung.getId()),  jwt);
                        if(resCode == 200) {
                            JOptionPane.showMessageDialog(null, "Buchung erfolgreich gelöscht");
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Etwas ist schief gelaufen");
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
        panel.add(jbtDelteBooking);


        jbtCreateInvoice.setBounds(475, 400, 150, 50);
        jbtCreateInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ApiRequests.hasCertainRole(jwt, "ADVANCED_USER")) {
                        WordDocumentGenerator.createInvoice(jwt, booking.getId());
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

        panel.add(jbtCreateInvoice);

        this.add(panel);
        this.setVisible(true);
    }


    private void validateUpdateInputs() {
        if(nameOfBookingPerson.getText().equals("")) nameOfBookingPerson.setText("-");
        if(mobilePhone.getText().equals("")) mobilePhone.setText("-");
        if(phone.getText().equals("")) phone.setText("-");
        if(email.getText().equals("")) email.setText("-");
        if(street.getText().equals("")) street.setText("-");
        if(jtfHouseNumber.getText().equals("")) jtfHouseNumber.setText("-");
        if(postalCode.getText().equals("")) postalCode.setText("-");
        if(city.getText().equals("")) city.setText("-");
        if(country.getText().equals("")) country.setText("-");
        if(taxId.getText().equals("")) taxId.setText("-");
    }

}
