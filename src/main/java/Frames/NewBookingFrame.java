package Frames;

import Data.ApiData;
import Data.ApiRequests;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewBookingFrame extends JFrame {

    //Client data

    private JLabel jlbClientData = new JLabel("Kundendaten");

    private JRadioButton jrbMale = new JRadioButton("Herr");
    private JRadioButton jrbFemale = new JRadioButton("Frau");
    ButtonGroup btgGender = new ButtonGroup();

    private JLabel jblNameOfBookingPerson = new JLabel("Name der buchenden Person:");
    private JTextField jtfNameOfBookingPerson = new JTextField();

    private JLabel jlbPhoneNumber = new JLabel("Telefon:");
    private JTextField jtfPhoneNumber = new JTextField();

    private JLabel jlbMobilePhoneNumber = new JLabel("Mobil:");
    private JTextField jtfMobilePhoneNumber = new JTextField();

    private JLabel jlbEmail = new JLabel("E-Mail Addresse:");
    private JTextField jtfEmail = new JTextField();

    private JLabel jlbStreetAndHousenumber = new JLabel("Straße + Hausnummer:");
    private JTextField jtfStreet = new JTextField();
    private JTextField jtfHouseNumber = new JTextField();

    private JLabel jlbPostalCode = new JLabel("PLZ:");
    private JTextField jtfPostalCode = new JTextField();

    private JLabel jlbCity = new JLabel("Stadt");
    private JTextField jtfCity = new JTextField();

    private JLabel jlbCountry = new JLabel("Land:");
    private JTextField jtfCountry = new JTextField("Deutschland");

    private JLabel jlbTaxId = new JLabel("Steuer ID (optional):");
    private JTextField jtfTaxId = new JTextField();

    //booking details

    private JLabel jlbBookingDetails = new JLabel("Buchungsdetails");

    private JLabel jlbFlatNumber = new JLabel("Wohung (A,B,C,D,E,K):");
    private JTextField jtfFlatNumber = new JTextField();




    private JLabel jblNumberOfAdults = new JLabel("Anzahl Erwachsene:");
    private JTextField jtfNumberOfAdults = new JTextField();
    private JLabel jblNumberOfChildren = new JLabel("Anzahl Kinder:");
    private JTextField jtfNumberOfChildren = new JTextField();

    private JLabel jlbNumberOfAnimals = new JLabel("Anzahl Tiere:");
    private JTextField jtfNumberOfAnimals = new JTextField();
    private JLabel jblArrival = new JLabel("Anreisedatum (dd-MM-yyyy):");
    private JTextField jtfArrival = new JTextField();
    private JLabel jblLeaving = new JLabel("Abreisedatum (dd-MM-yyyy):");
    private JTextField jtfLeaving = new JTextField();

    private JLabel jlbDiscount = new JLabel("Rabatt (optional):");
    private JTextField jtfDiscount = new JTextField();

    private JLabel jlbListOfNames = new JLabel("Namen der weiteren Personen (mit , getrennt)");
    private JTextField jtfListOfNames = new JTextField();

    private JButton jbtSubmit = new JButton("Buchung bestätigen");


    private String jwt;
    public NewBookingFrame(String jwt) {
        this.jwt = jwt;
        this.setTitle("Neue Buchung hinzufügen");
        this.setSize(600, 600);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        jlbClientData.setBounds(5, 5, 250, 30);
        jlbClientData.setFont(new Font("Serif", Font.PLAIN, 20));
        this.add(jlbClientData);

        btgGender.add(jrbMale);
        btgGender.add(jrbFemale);

        jrbMale.setBounds(10, 40, 60, 20);
        jrbMale.setSelected(true);
        this.add(jrbMale);

        jrbFemale.setBounds(10, 60, 60, 20);
        this.add(jrbFemale);

        jblNameOfBookingPerson.setBounds(10, 90, 200, 20);
        jtfNameOfBookingPerson.setBounds(5, 110, 200, 20);
        this.add(jtfNameOfBookingPerson);
        this.add(jblNameOfBookingPerson);

        jlbPhoneNumber.setBounds(10, 140, 200, 20);
        jtfPhoneNumber.setBounds(5, 160, 200, 20);
        this.add(jlbPhoneNumber);
        this.add(jtfPhoneNumber);

        jlbMobilePhoneNumber.setBounds(10, 190, 200, 20);
        jtfMobilePhoneNumber.setBounds(5, 210, 200, 20);
        this.add(jlbMobilePhoneNumber);
        this.add(jtfMobilePhoneNumber);

        jlbEmail.setBounds(10, 240, 200, 20);
        jtfEmail.setBounds(5, 260, 200, 20);
        this.add(jlbEmail);
        this.add(jtfEmail);

        jlbStreetAndHousenumber.setBounds(10, 290, 200, 20);
        jtfStreet.setBounds(5, 310, 160, 20);
        jtfHouseNumber.setBounds(170, 310, 30, 20);
        this.add(jlbStreetAndHousenumber);
        this.add(jtfStreet);
        this.add(jtfHouseNumber);

        jlbPostalCode.setBounds(10, 340, 90, 20);
        jtfPostalCode.setBounds(5, 360, 95, 20);
        this.add(jlbPostalCode);
        this.add(jtfPostalCode);

        jlbCity.setBounds(110, 340, 90, 20);
        jtfCity.setBounds(105, 360, 95, 20);
        this.add(jlbCity);
        this.add(jtfCity);

        jlbCountry.setBounds(10, 390, 200, 20);
        jtfCountry.setBounds(5, 410, 200, 20);
        this.add(jlbCountry);
        this.add(jtfCountry);


        jlbTaxId.setBounds(10, 440, 200, 20);
        jtfTaxId.setBounds(5, 460, 200, 20);
        this.add(jlbTaxId);
        this.add(jtfTaxId);



        jlbBookingDetails.setBounds(390, 5, 200, 30);
        jlbBookingDetails.setFont(new Font("Serif", Font.PLAIN, 20));
        this.add(jlbBookingDetails);


        jlbFlatNumber.setBounds(390, 40, 200, 20);
        jtfFlatNumber.setBounds(385, 60, 200, 20);
        this.add(jlbFlatNumber);
        this.add(jtfFlatNumber);


        jblNumberOfAdults.setBounds(390, 90, 200, 20);
        jtfNumberOfAdults.setBounds(385, 110, 200, 20);
        this.add(jtfNumberOfAdults);
        this.add(jblNumberOfAdults);


        jblNumberOfChildren.setBounds(390, 140, 200, 20);
        jtfNumberOfChildren.setBounds(385, 160, 200, 20);
        this.add(jtfNumberOfChildren);
        this.add(jblNumberOfChildren);

        jlbNumberOfAnimals.setBounds(390, 190, 200, 20);
        jtfNumberOfAnimals.setBounds(385, 210, 200, 20);
        this.add(jlbNumberOfAnimals);
        this.add(jtfNumberOfAnimals);


        jblArrival.setBounds(390, 240, 200, 20);
        jtfArrival.setBounds(385, 260, 200, 20);
        this.add(jtfArrival);
        this.add(jblArrival);

        jblLeaving.setBounds(390, 290, 200, 20);
        jtfLeaving.setBounds(385, 310, 200, 20);
        this.add(jtfLeaving);
        this.add(jblLeaving);

        jlbDiscount.setBounds(390, 340, 200, 20);
        jtfDiscount.setBounds(385, 360, 200, 20);
        this.add(jlbDiscount);
        this.add(jtfDiscount);

        jlbListOfNames.setBounds(390, 390, 200, 20);
        jtfListOfNames.setBounds(385, 410, 200, 20);
        this.add(jlbListOfNames);
        this.add(jtfListOfNames);



        jbtSubmit.setBounds(300, 450, 200, 50);
        jbtSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean mistake = false;
                jlbFlatNumber.setForeground(Color.BLACK);
                jblNameOfBookingPerson.setForeground(Color.BLACK);
                jlbPhoneNumber.setForeground(Color.BLACK);
                jlbMobilePhoneNumber.setForeground(Color.BLACK);
                jlbEmail.setForeground(Color.BLACK);
                jtfCountry.setForeground(Color.BLACK);
                jblNumberOfAdults.setForeground(Color.BLACK);
                jblNumberOfChildren.setForeground(Color.BLACK);
                jlbNumberOfAnimals.setForeground(Color.BLACK);
                jblArrival.setForeground(Color.BLACK);
                jblLeaving.setForeground(Color.BLACK);



                if(!validateFlatNumber(jtfFlatNumber)) {
                    jlbFlatNumber.setForeground(Color.RED);
                    mistake = true;
                }
                if(!validateStringField(jtfNameOfBookingPerson)) {
                    jblNameOfBookingPerson.setForeground(Color.RED);
                    mistake = true;
                }
                if(!validateStringField(jtfNameOfBookingPerson)) {
                    jblNameOfBookingPerson.setForeground(Color.RED);
                    mistake = true;
                }
                //if(!validatePhoneNumbers(jtfPhoneNumber, jtfMobilePhoneNumber)) {
                    //jlbPhoneNumber.setForeground(Color.RED);
                    //jlbMobilePhoneNumber.setForeground(Color.RED);
                    //mistake = true;
                //}
                //if(!validateStringField(jtfEmail)) {
                    //jlbEmail.setForeground(Color.RED);
                   // mistake = true;
                //}
                //if(!validateStringField(jtfStreet) || !validateNumericField(jtfHouseNumber)) {
                    //jlbStreetAndHousenumber.setForeground(Color.RED);
                    //mistake = true;
                //}
                if(!validateStringField(jtfCountry)) {
                    jtfCountry.setForeground(Color.RED);
                    mistake = true;
                }
                if(!validateNumericField(jtfNumberOfAdults)) {
                    jblNumberOfAdults.setForeground(Color.RED);
                    mistake = true;
                }
                if(!validateNumericField(jtfNumberOfChildren)) {
                    jblNumberOfChildren.setForeground(Color.RED);
                    mistake = true;
                }
                if(!validateNumericField(jtfNumberOfAnimals)) {
                    jlbNumberOfAnimals.setForeground(Color.RED);
                    mistake = true;
                }
                if(!validateDate(jtfArrival)) {
                    jblArrival.setForeground(Color.RED);
                    mistake = true;
                }
                if(!validateDate(jtfLeaving)) {
                    jblLeaving.setForeground(Color.RED);
                    mistake = true;
                }

                if(!validateFlatK(jtfFlatNumber, jtfNumberOfAdults, jtfNumberOfChildren)) {
                    JOptionPane.showMessageDialog(null, "Die maximale Gästeanzahl in Wohnung K beträgt 2.");
                    mistake = true;
                }
                if(!arrivalDateIsBeforeLeavingDate(jtfArrival.getText(), jtfLeaving.getText())) {
                    jblArrival.setForeground(Color.RED);
                    jblLeaving.setForeground(Color.RED);
                    mistake = true;
                }



                if(!mistake) {
                    JSONObject clientBody = new JSONObject();
                    JSONObject bookingBody = new JSONObject();
                    int flatNumber = ((int) jtfFlatNumber.getText().charAt(0))-64;

                    String gender;
                    if(jrbFemale.isSelected()) gender = "Frau";
                    else gender = "Herr";
                    try {
                        clientBody.put("gender", gender);
                        clientBody.put("fullName", jtfNameOfBookingPerson.getText());
                        if(validateStringField(jtfEmail)) {
                            clientBody.put("email", jtfEmail.getText());
                        } else clientBody.put("email", "-");
                        if(validateStringField(jtfStreet)) {
                            clientBody.put("street", jtfStreet.getText());
                        } else clientBody.put("street", "-");
                        if(validateStringField(jtfHouseNumber)) {
                            clientBody.put("houseNumber", jtfHouseNumber.getText());
                        } else clientBody.put("houseNumber", "-");

                        if(validateStringField(jtfPostalCode)) {
                            clientBody.put("postalCode", jtfPostalCode.getText());
                        } else clientBody.put("postalCode", "-");
                        if(validateStringField(jtfCity)) {
                            clientBody.put("city", jtfCity.getText());
                        } else clientBody.put("city", "-");
                        clientBody.put("country", jtfCountry.getText());

                        if(validateStringField(jtfPhoneNumber)) {
                            clientBody.put("phone", jtfPhoneNumber.getText());
                        } else clientBody.put("phone", "-");
                        if(validateStringField(jtfMobilePhoneNumber)) {
                            clientBody.put("mobilePhone",jtfMobilePhoneNumber.getText());
                        } else clientBody.put("mobilePhone", "-");
                        if(validateStringField(jtfTaxId)) {
                            clientBody.put("taxId", jtfTaxId.getText());
                        } else clientBody.put("taxId", "-");

                        JSONObject availabilityBody = new JSONObject();
                        availabilityBody.put("arrivalDate", jtfArrival.getText());
                        availabilityBody.put("leavingDate", jtfLeaving.getText());
                        availabilityBody.put("flatNumber", flatNumber);
                        JSONObject availabilityResponse = new JSONObject(ApiRequests.postRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/booking/available"), availabilityBody.toString(), jwt));

                        if((boolean)availabilityResponse.get("available")) {
                            String clientResponse = ApiRequests.postRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/booking/client"), clientBody.toString(), jwt);
                            JSONObject client = new JSONObject(clientResponse);

                            bookingBody.put("flatNumber", ((int)jtfFlatNumber.getText().charAt(0))-64);
                            bookingBody.put("numberOfAdults", Integer.parseInt(jtfNumberOfAdults.getText()));
                            bookingBody.put("numberOfChildren", Integer.parseInt(jtfNumberOfChildren.getText()));
                            bookingBody.put("numberOfAnimals", Integer.parseInt(jtfNumberOfAnimals.getText()));
                            bookingBody.put("arrivalDate", jtfArrival.getText());
                            bookingBody.put("leavingDate", jtfLeaving.getText());
                            bookingBody.put("clientId", client.get("_id"));

                            //List of names (optional)
                            if(validateStringField(jtfListOfNames)) {
                                String listOfNames = jtfNameOfBookingPerson.getText() + ", "+jtfListOfNames.getText();
                                bookingBody.put("listOfNames", listOfNames);
                            } else bookingBody.put("listOfNames", "-");
                            ApiRequests.postRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/booking/create"), bookingBody.toString(), jwt);

                            JOptionPane.showMessageDialog(null, "Neue Buchung erfolgreich erstellt");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Die ausgewählte Wohnung ist zu diesem Zeitraum bereits belegt.");
                        }


                    } catch (JSONException ex) {
                        throw new RuntimeException(ex);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }
        });
        this.add(jbtSubmit);

        this.setVisible(true);
    }

    private boolean validateStringField(JTextField input) {
        return !input.getText().equals("");
    }

    private boolean validateNumericField(JTextField input) {

        if(!input.getText().equals("")) {
            try {
                int i = Integer.parseInt(input.getText());
            } catch (NumberFormatException nfe) {
                return false;
            }

            return true;
        }
        return false;
    }

    private boolean validateDate(JTextField input) {
        return !input.getText().equals("") && input.getText().contains("-") && input.getText().length() == 10 && !input.getText().startsWith(" ");
    }

    private boolean validateFlatNumber(JTextField input) {
        return input.getText().equals("A") || input.getText().equals("B") || input.getText().equals("C") || input.getText().equals("D") || input.getText().equals("E") || input.getText().equals("K");
    }

    //flat K only allows two guests
    private boolean validateFlatK(JTextField jtfFlatNumber, JTextField jtfNumberOfAdults, JTextField jtfNumberOfChildren) {

        try {
            return !jtfFlatNumber.getText().equals("K") || Integer.parseInt(jtfNumberOfAdults.getText()) + Integer.parseInt(jtfNumberOfChildren.getText()) <= 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    private boolean validatePhoneNumbers(JTextField phone, JTextField mobile) {
        return !phone.getText().equals("") || !mobile.getText().equals("");
    }

    private boolean arrivalDateIsBeforeLeavingDate(String arrStr, String leavStr) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        System.out.println(arrStr);
        System.out.println(leavStr);
        try {
            Date arrivalDate = sdf.parse(arrStr);
            Date leavingDate = sdf.parse(leavStr);
            if(arrivalDate.before(leavingDate)) return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
