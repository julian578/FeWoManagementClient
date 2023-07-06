package Request;

import Model.Booking;
import Model.Client;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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



    public static void loadBookings(String jwt) throws JSONException, IOException, ParseException, InterruptedException {
        JSONArray bookings = new JSONArray(ApiRequests.getRequest(new URL("http://localhost:3000/api/booking/all"), jwt).toString());
        JSONObject b;

        bookingList.clear();

        ArrayList<Booking> bList = new ArrayList<>();
        for(int i = 0; i < bookings.length(); i++) {
            b = bookings.getJSONObject(i);
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

        bookingList = bList;
    }


    public static void loadClients(String jwt) throws JSONException, IOException {
        HashMap<String, Client> clients = new HashMap<>();
        clientList.clear();
        Dotenv dotenv = Dotenv.configure().load();
        for(Booking b : bookingList) {
            JSONObject client = new JSONObject(ApiRequests.getRequest(new URL(dotenv.get("API_REQUEST_PREFIX")+"/booking/client/"+b.getClientId()), jwt).toString());
            Client c = new Client(
                    (String)client.get("fullName"),
                    (String) client.get("email"),
                    (String) client.get("street"),
                    (int) client.get("houseNumber"),
                    (String) client.get("postalCode"),
                    (String) client.get("city"),
                    (String) client.get("country")
            );
            clients.put(b.getId(), c);
        }
        clientList = clients;
    }

}
