package InvoiceCreation;

import Data.ApiData;
import Data.ApiRequests;

import Frames.InvoiceOverviewFrame;
import Model.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONObject;

import javax.swing.*;

public class WordDocumentGenerator {



    public static void generateInvoice(byte[] binaryData, String name, int invoiceNumber, String jwt) throws IOException, JSONException, TemplateException, URISyntaxException {
        //String outputFolderPath = PropertiesConfig.getInvoiceFolderPath();
        JSONObject invoicePathRes = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/filePath/INVOICE_PATH"), jwt).toString());

        if(invoicePathRes.has("path")) {
            String outputFolderPath = invoicePathRes.get("path").toString();
            try (ByteArrayInputStream bis = new ByteArrayInputStream(binaryData);
                 FileOutputStream fos = new FileOutputStream(outputFolderPath+"/Rechnung(invoice)_"+name+"_"+invoiceNumber+".docx")) {

                XWPFDocument doc = new XWPFDocument(bis);
                doc.write(fos);
                File outputFile = new File("Rechnung(invoice)_"+name+"_"+invoiceNumber+".docx");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }


    public static void createInvoice(String jwt, String bookingId) {
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
                JSONObject invoiceRes = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX")+"/invoice/"+bookingId),  jwt));

                int invoiceNumber = (int) invoiceRes.get("invoiceId");

                //get the client name for the invoice file name


                String clientName = ApiData.clientList.get(bookingId).getFullName();

                WordDocumentGenerator.generateInvoice(binaryData, clientName, invoiceNumber, jwt);


                JOptionPane.showMessageDialog(null, "Rechnung erfolgreich erstellt");
                ApiData.loadBookings(jwt);




            } else {

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




    }



//dataModel.put("g", invoiceData.get("g"));
    //dataModel.put("n", invoiceData.get("n"));
    //dataModel.put("sn", invoiceData.get("sn"));
    //dataModel.put("ps", invoiceData.get("ps"));
    //dataModel.put("l", invoiceData.get("l"));
    //dataModel.put("t", invoiceData.get("t"));
    //dataModel.put("h", invoiceData.get("h"));
    //dataModel.put("em", invoiceData.get("em"));
    //dataModel.put("dt", invoiceData.get("dt"));
    //dataModel.put("an", invoiceData.get("an"));
    //dataModel.put("ab", invoiceData.get("ab"));
    //dataModel.put("zt", invoiceData.get("zt"));
    //dataModel.put("tp", invoiceData.get("tp"));
    //dataModel.put("anzE", invoiceData.get("anzE"));
    //dataModel.put("anzK", invoiceData.get("anzK"));
    //dataModel.put("p2pN", invoiceData.get("p2pN"));
    //dataModel.put("p2p", invoiceData.get("p2p"));
    //dataModel.put("pwpN", invoiceData.get("pwpN"));
    //dataModel.put("awp", invoiceData.get("awp"));
    //dataModel.put("pwp", invoiceData.get("pwp"));
    //dataModel.put("pTN", invoiceData.get("pTN"));
    //dataModel.put("anzT", invoiceData.get("anzT"));
    //dataModel.put("tpt", invoiceData.get("tpt"));
    //dataModel.put("pER", invoiceData.get("pER"));
    //dataModel.put("tPreis", invoiceData.get("tPreis"));
    //dataModel.put("mwst", invoiceData.get("mwst"));
    //dataModel.put("hwt", invoiceData.get("hwt"));
    //dataModel.put("anzN", invoiceData.get("anzN"));
    //dataModel.put("bn", invoiceData.get("bn"));



}
