package Frames;

import Data.ApiData;
import org.json.JSONException;

import javax.swing.*;

import java.io.IOException;
import java.text.ParseException;

public class OverviewFrame extends JFrame {

    Panel panel;
    public OverviewFrame(String jwt) throws ParseException, IOException, JSONException, InterruptedException {

        panel = new Panel(jwt);
        ApiData.loadBookings(jwt);
        ApiData.loadClients(jwt);
        this.setTitle("FeWo Management");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setContentPane(panel);

        this.setVisible(true);

    }



}
