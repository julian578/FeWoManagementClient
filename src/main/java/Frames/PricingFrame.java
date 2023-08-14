package Frames;

import Data.ApiData;
import Data.ApiRequests;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PricingFrame extends JFrame {

    private JPanel mainPanel = new JPanel();

    private JPanel tablePanel = new JPanel();
    private JPanel tableLeft = new JPanel();
    private JPanel tableMid = new JPanel();
    private JPanel tableRight = new JPanel();

    private JPanel rightColumn = new JPanel();




    private JLabel jlbFlatA = new JLabel("Wohnung A");
    private JLabel jlbFlatB = new JLabel("Wohnung B");
    private JLabel jlbFlatC = new JLabel("Wohnung C");
    private JLabel jlbFlatD = new JLabel("Wohnung D");
    private JLabel jlbFlatE = new JLabel("Wohnung E");
    private JLabel jlbFlatK = new JLabel("Wohnung K");

    private JLabel jlbPriceForTwo = new JLabel("Preis für bis 2 Personen / Nacht");
    private JLabel jlbPriceForAddtionalePerson = new JLabel("Preis für jede Weitere Person / Nacht");
    private JLabel jlbPriceAnimal = new JLabel("Preis pro Tier / Nacht");
    private JTextField jtfPriceAnimal = new JTextField();
    private JLabel jlbPriceCleaningWithoutAnimal = new JLabel("Preis Reininung ohne Tier");
    private JTextField jtfPriceCleaningWithoutAnimal = new JTextField();
    private JLabel jlbPriceCleaningWithAnimal = new JLabel("Preis Reininung mit Tier");
    private JTextField jtfPriceCleaningWithAnimal = new JTextField();

    private JButton jbtSubmitUpdates = new JButton("Preise aktualisieren");


    public PricingFrame(String jwt) throws JSONException, IOException {
        this.setSize(800, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        mainPanel.setLayout(new FlowLayout());



        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));


        String[] columnNames = {"Wohnung", "Preis für bis 2 Personen / Nacht", "Preis für jede Weitere Person / Nacht"};
        Object[][] data = loadPrices(jwt);

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create table and add it to a scroll pane
        JTable table = new JTable(tableModel);
        table.setCellSelectionEnabled(true); // Allow individual cell selection
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.add(scrollPane);

        jtfPriceAnimal.setText(loadAnimalPrice(jwt));
        jtfPriceCleaningWithAnimal.setText(loadCleaningPriceWithAnimal(jwt));
        jtfPriceCleaningWithoutAnimal.setText(loadCleaningPriceWithoutAnimal(jwt));

        rightColumn.add(jlbPriceAnimal);
        rightColumn.add(jtfPriceAnimal);
        rightColumn.add(jlbPriceCleaningWithAnimal);
        rightColumn.add(jtfPriceCleaningWithAnimal);
        rightColumn.add(jlbPriceCleaningWithoutAnimal);
        rightColumn.add(jtfPriceCleaningWithoutAnimal);

        rightColumn.add(Box.createVerticalStrut(20));

        jbtSubmitUpdates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if(ApiRequests.hasCertainRole(jwt, "ADMIN")) {
                        try {


                            JSONObject body = new JSONObject();
                            body.put("newPrice", Double.valueOf(jtfPriceAnimal.getText()));


                            ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/price/PRICE_PER_ANIMAL_PER_NIGHT"), body.toString(), jwt);

                            body.put("newPrice", Double.valueOf(jtfPriceCleaningWithAnimal.getText()));
                            ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/price/CLEANING_WITH_ANIMAL"), body.toString(), jwt);
                            body.put("newPrice", Double.valueOf(jtfPriceCleaningWithoutAnimal.getText()));
                            ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/price/CLEANING_WITHOUT_ANIMAL"), body.toString(), jwt);

                            body.put("newPrice", Double.valueOf(table.getValueAt(5, 1).toString()));
                            ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/price/K_PRICE_PER_NIGHT_TWO"), body.toString(), jwt);

                            for (int i = 0; i < table.getRowCount() - 1; i++) {

                                double valueTwo = Double.valueOf(table.getValueAt(i, 1).toString());
                                double valueAdditional = Double.valueOf(table.getValueAt(i, 2).toString());

                                body.put("newPrice", valueTwo);
                                ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/price/" + (char) (i + 65) + "_PRICE_PER_NIGHT_TWO"), body.toString(), jwt);

                                body.put("newPrice", valueAdditional);
                                ApiRequests.putRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") + "/price/" + (char) (i + 65) + "_PRICE_PER_FURTHER_PERSON"), body.toString(), jwt);
                            }

                            JOptionPane.showMessageDialog(null, "Preise erfolgreich aktualisiert!");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        } catch(NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Einer der Preise ist keine Zahl!");
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
        rightColumn.add(jbtSubmitUpdates);



        mainPanel.add(tablePanel);
        mainPanel.add(rightColumn);
        this.getContentPane().add(mainPanel);
        this.setVisible(true);
    }



    private Object[][] loadPrices(String jwt) throws IOException, JSONException {



        Object[][] data = new Object[6][3];

        for(int i = 0; i < 5; i++) {
            String subjectPriceTwo = (char)(i+65)+"_PRICE_PER_NIGHT_TWO";
            String subjectPriceAddtional = (char)(i+65)+"_PRICE_PER_FURTHER_PERSON";
            JSONObject responsePriceTwo = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") +"/price/"+subjectPriceTwo),jwt));
            JSONObject responsePriceAddtional = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") +"/price/"+subjectPriceAddtional),jwt));
            data[i][0] = String.valueOf((char)(i+65));
            data[i][1] = responsePriceTwo.get("price").toString();
            data[i][2] = responsePriceAddtional.get("price").toString();
        }

        JSONObject responsePriceTwo = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") +"/price/K_PRICE_PER_NIGHT_TWO"),jwt));

        data[5][0] = "K";
        data[5][1] = responsePriceTwo.get("price");
        data[5][2] = "-";


        return data;
    }

    private String loadAnimalPrice(String jwt) throws JSONException, IOException {
        JSONObject response = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") +"/price/PRICE_PER_ANIMAL_PER_NIGHT"),jwt));
        return response.get("price").toString();
    }

    private String loadCleaningPriceWithoutAnimal(String jwt) throws IOException, JSONException {
        JSONObject response = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") +"/price/CLEANING_WITHOUT_ANIMAL"),jwt));
        return response.get("price").toString();
    }

    private String loadCleaningPriceWithAnimal(String jwt) throws JSONException, IOException {
        JSONObject response = new JSONObject(ApiRequests.getRequest(new URL(ApiData.dotenv.get("API_REQUEST_PREFIX") +"/price/CLEANING_WITH_ANIMAL"),jwt));
        return response.get("price").toString();
    }


}
