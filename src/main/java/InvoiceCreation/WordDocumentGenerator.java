package InvoiceCreation;

import Data.ApiData;
import Data.PropertiesConfig;
import freemarker.template.TemplateException;
import org.json.JSONException;

import java.io.*;
import java.net.URISyntaxException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordDocumentGenerator {



    public static void generateInvoice(byte[] binaryData, String name, int invoiceNumber) throws IOException, JSONException, TemplateException, URISyntaxException {
        String outputFolderPath = PropertiesConfig.getInvoiceFolderPath();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(binaryData);
             FileOutputStream fos = new FileOutputStream(outputFolderPath+"Rechnung(invoice)_"+name+"_"+invoiceNumber+".docx")) {

            XWPFDocument doc = new XWPFDocument(bis);
            doc.write(fos);
            File outputFile = new File("Rechnung(invoice)_"+name+"_"+invoiceNumber+".docx");
            System.out.println("Word document recreated and saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
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
