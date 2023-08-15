package Data;

import Model.Booking;
import Model.Client;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiData {

    public static List<Booking> bookingList = new ArrayList<>();
    public static HashMap<String, Client> clientList = new HashMap<String, Client>();
    public static Dotenv dotenv = Dotenv.configure().load();



    public static void loadBookings(String jwt) throws JSONException, IOException, ParseException, InterruptedException {
        JSONArray bookings = new JSONArray(ApiRequests.getRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/booking/all"),  jwt).toString());

        bookingList.clear();


        bookingList = jsonArrayToBookingList(bookings);
    }


    public static void loadClients(String jwt) throws JSONException, IOException {
        HashMap<String, Client> clients = new HashMap<>();
        clientList.clear();

        for(Booking b : bookingList) {
            JSONObject client = new JSONObject(ApiRequests.getRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/booking/client/"+b.getClientId()),  jwt).toString());
            Client c = new Client(
                    (String)client.get("_id"),
                    (String)client.get("fullName"),
                    (String) client.get("mobilePhone"),
                    (String) client.get("phone"),
                    (String) client.get("email"),
                    (String) client.get("street"),
                    (String) client.get("houseNumber"),
                    (String) client.get("postalCode"),
                    (String) client.get("city"),
                    (String) client.get("country"),
                    (String) client.get("taxId")
            );
            clients.put(b.getId(), c);
        }
        clientList = clients;
    }

    public static ArrayList<Booking> jsonArrayToBookingList(JSONArray array) throws JSONException, ParseException {
        ArrayList<Booking> bList = new ArrayList<>();
        JSONObject b;
        for(int i = 0; i < array.length(); i++) {
            b = array.getJSONObject(i);
            bList.add(new Booking((String)b.get("_id"),
                    (int)b.get("flatNumber"),
                    (int)b.get("numberOfAdults"),
                    (int)b.get("numberOfChildren"),
                    (int)b.get("numberOfAnimals"),
                    new SimpleDateFormat("dd-MM-yyyy").parse((String)b.get("arrivalDate")),
                    new SimpleDateFormat("dd-MM-yyyy").parse((String)b.get("leavingDate")),
                    (String)b.get("pricePerNightTwo"),
                    (String)b.get("pricePerNightAdditionalPerson"),
                    (String)b.get("cleaningPrice"),
                    (String)b.get("pricePerNightAnimal"),
                    (String)b.get("totalPrice"),
                    (String)b.get("listOfNames"),
                    (int)b.get("numberOfNights"),
                    (String)b.get("clientId"),
                    (int)b.get("invoiceStatus")
            ));

        }

        return bList;
    }

    public static Booking getBookingFromBookingId(String bookingId) {
        for(Booking b: bookingList) {
            if(b.getId().equals(bookingId)) return b;
        }

        return null;
    }


    public static String getInvoiceFilePath(String jwt) {
        try {
            JSONObject invoicePathRes = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/filePath/INVOICE_PATH"), jwt).toString());
            if(invoicePathRes.has("path")) {
                return invoicePathRes.get("path").toString();
            } else {
                JOptionPane.showMessageDialog(null, "Pfad für Rechnungen konnte nicht geladen werden!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }
}
