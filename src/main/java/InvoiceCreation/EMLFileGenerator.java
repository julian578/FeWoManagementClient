package InvoiceCreation;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class EMLFileGenerator {

    public static void createEMLFile(String fromEmail, String recipientEmail, String subject, String body, String attachmentFilePath) throws IOException {
        String emlFilePath = "mail.eml"; // Replace with the desired .eml file path
        // Replace with the path of the file you want to attach

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(emlFilePath)))) {
            // Write the MIME headers for the email message


            outputStream.write(("From: "+fromEmail+"\r\n").getBytes());
            outputStream.write(("To: "+recipientEmail+"\r\n").getBytes());
            outputStream.write(("Subject: "+subject+"\r\n").getBytes());

            // Start of the MIME content
            outputStream.write("Content-Type: multipart/mixed; boundary=\"separator\"\r\n".getBytes());
            outputStream.write("\r\n".getBytes());

            // Write the text part of the email
            outputStream.write("--separator\r\n".getBytes());
            outputStream.write("Content-Type: text/plain; charset=\"UTF-8\"\r\n".getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write((body+"\r\n").getBytes());
            outputStream.write("\r\n".getBytes());

            // Write the attachment part
            File attachmentFile = new File(attachmentFilePath);
            byte[] attachmentData = Files.readAllBytes(attachmentFile.toPath());

            outputStream.write("--separator\r\n".getBytes());
            outputStream.write(("Content-Type: application/octet-stream; name=\"" + attachmentFile.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Disposition: attachment; filename=\"" + attachmentFile.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Transfer-Encoding: base64\r\n").getBytes());
            outputStream.write("\r\n".getBytes());

            // Encode and write the attachment data in base64
            String encodedAttachmentData = Base64.getEncoder().encodeToString(attachmentData);
            outputStream.write(encodedAttachmentData.getBytes());
            outputStream.write("\r\n".getBytes());

            // End of the MIME content
            outputStream.write("--separator--\r\n".getBytes());

            System.out.println("EML file with attachment created successfully: " + emlFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
