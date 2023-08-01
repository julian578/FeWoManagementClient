package Frames;

import Model.Booking;
import Model.BookingLabel;
import Model.Client;
import Request.ApiData;
import Request.ApiRequests;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


//Panel contains the content of the Overview Frame
public class Panel extends JLabel {

    private int scrollStatus = 0;
    private String[] headerDates = new String[7];
    Locale loc = new Locale("de", "DE");
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
    Date dt;
    Calendar c = Calendar.getInstance();

    private List<Booking> belegungen = new ArrayList<>();

    private List<BookingLabel> bookingLabels = new ArrayList<>();
    private JButton scrollRight = new JButton("->");
    private JButton scrollLeft = new JButton("<-");

    private JButton addBookingButton = new JButton("Buchung hinzufügen");
    private JButton reloadBookings = new JButton("Aktualisieren");
    private JTextArea jtaArrivalsToday = new JTextArea();
    private JTextArea jtaLeavingsToday = new JTextArea();

    private String jwt;

    public Panel(String jwt) throws ParseException, IOException, JSONException {
        this.jwt = jwt;
        initHeaderDates();

        calculateBookingLabels();
        setArrivingsToday();
        setLeavingsToday();
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        scrollLeft.setBounds(700, 500, 30, 30);
        scrollLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollStatus--;
                initHeaderDates();
                try {
                    calculateBookingLabels();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.add(scrollLeft);

        scrollRight.setBounds(735, 500, 30, 30);
        scrollRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollStatus++;
                initHeaderDates();
                try {
                    calculateBookingLabels();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.add(scrollRight);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    checkForBookingLabelClicked(e);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addBookingButton.setBounds(620, 410, 150, 50);
        addBookingButton.setVisible(true);
        addBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewBookingFrame(jwt);
            }
        });
        this.add(addBookingButton);

        reloadBookings.setBounds(10, 410, 150, 50);
        reloadBookings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ApiData.loadBookings(jwt);
                    ApiData.loadClients(jwt);
                    calculateBookingLabels();
                    setArrivingsToday();
                    setLeavingsToday();

                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        this.add(reloadBookings);

        jtaArrivalsToday.setBounds(200, 410, 100, 200);
        jtaArrivalsToday.setEnabled(false);
        this.add(jtaArrivalsToday);

        jtaLeavingsToday.setBounds(320, 410, 100, 200);
        jtaLeavingsToday.setEnabled(false);
        this.add(jtaLeavingsToday);

    }


    private void setArrivingsToday() {
        jtaArrivalsToday.setText("Anreisen heute:");
        for(Booking b:belegungen) {

            if( dateFormat.format(b.getArrivingDate()).equals(dateFormat.format(new Date()))) {

                jtaArrivalsToday.append("\n- Wohnung "+ (char)(64+b.getFlatNumber()));
            }
        }
    }

    private void setLeavingsToday() {
        jtaLeavingsToday.setText("Abreisen heute:");
        for(Booking b:belegungen) {
            if(dateFormat.format(b.getLeavingDate()).equals(dateFormat.format(new Date()))) {
                jtaLeavingsToday.append("\n- Wohnung "+ (char)(64+b.getFlatNumber()));
            }
        }
    }

    private void initHeaderDates() {
        dt = new Date();
        c.setTime(dt);
        c.add(Calendar.DATE, scrollStatus);
        dt = c.getTime();
        String date = dateFormat.format(dt);
        headerDates[0] = date;
        for(int i = 1; i < 7; i++) {
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            date = dateFormat.format(dt);
            headerDates[i] = date;
        }
    }

    public void checkForBookingLabelClicked(MouseEvent e) throws JSONException, IOException {
        int mouseX = e.getX();
        int mouseY = e.getY();
        for(BookingLabel bl: bookingLabels) {
            if(mouseX >= bl.getxPos() && mouseX <= bl.getxPos()+bl.getWidth()
            && mouseY >= bl.getyPos() && mouseY <= bl.getyPos()+bl.getHeight()) {
                new BookingFrame(bl.getBelegung(), jwt);
            }
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTable(g);
        drawCalender(g);
        drawBookingLabels(g);
        repaint();
    }

    //creates the background table for the calendar view
    public void drawTable(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        for(int i = 1; i <= 5; i++) {
            g.drawLine(100*i, 0, 100*i, 350);
            g.drawLine(0, i*50, 800, i*50);
            g.drawString("Wohnung " + (char)(64+i), 5, i*50 + 30);
        }
        g.drawString("Wohnung K", 5, 330);
        g.drawLine(600, 0, 600, 350);
        g.drawLine(700, 0, 700, 350);
        g.drawLine(800, 0, 800, 350);
        g.drawLine(0, 300, 800, 300);

    }

    public void drawCalender(Graphics g) {


        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));


        for(int i = 0; i < 7; i++) {

            g.drawString(headerDates[i], 105 + i*100, 30);
        }
    }

    //adds labels with different colors to the calendar for the time a flat is booked, by clicking on the labels you can see the booking details
    private void calculateBookingLabels() throws ParseException {
        bookingLabels.clear();
        BookingLabel bl;

        for(Booking b: ApiData.bookingList) {
            int startIndex = -1;
            int endIndex = -1;
            int arrivingIndex = -1;
            int leavingIndex = -1;
            if (!b.getArrivingDate().after(new SimpleDateFormat("dd.MM.yyyy").parse(headerDates[6]))) {
                for (int i = 0; i < 7; i++) {
                    if (b.getArrivingDate().equals(new SimpleDateFormat("dd.MM.yyyy").parse(headerDates[i]))) {
                        arrivingIndex = i;

                    }
                    if (b.getLeavingDate().equals(new SimpleDateFormat("dd.MM.yyyy").parse(headerDates[i]))) {
                        leavingIndex = i;

                    }
                }

                int yPos;
                if(b.getFlatNumber() == 11) {
                    yPos = 300;
                } else {
                    yPos = b.getFlatNumber() * 50;
                }


                if (arrivingIndex != -1 && leavingIndex != -1) {


                    bl = new BookingLabel(b, 150 + arrivingIndex * 100, yPos, (leavingIndex - arrivingIndex) * 100, 50);
                    bl.setLabelColor(getLabelColor(b));
                    bookingLabels.add(bl);
                }

                else if(arrivingIndex != -1 && leavingIndex == -1) {

                    endIndex = 6;
                    bl = new BookingLabel(b, 150 + arrivingIndex * 100, yPos, (endIndex - arrivingIndex) * 100 + 50, 50);
                    bl.setLabelColor(getLabelColor(b));
                    bookingLabels.add(bl);
                }

                else if(arrivingIndex == -1 && leavingIndex != -1) {

                    bl = new BookingLabel(b, 100, yPos, (leavingIndex+1) * 100 - 50, 50);
                    bl.setLabelColor(getLabelColor(b));
                    bookingLabels.add(bl);
                }
                else if (b.getArrivingDate().before(new SimpleDateFormat("dd.MM.yyyy").parse(headerDates[0])) && b.getLeavingDate().after(new SimpleDateFormat("dd.MM.yyyy").parse(headerDates[6]))) {
                    bl = new BookingLabel(b, 100, yPos, 800, 50);
                    bl.setLabelColor(getLabelColor(b));
                    bookingLabels.add(bl);
                }
            }



        }
    }

    private Color getLabelColor(Booking b) {

        List<BookingLabel> labelsWithSameNumber = bookingLabels.stream().filter(bl -> bl.getBelegung().getFlatNumber()==b.getFlatNumber()).collect(Collectors.toList());
        for(BookingLabel bl:labelsWithSameNumber) {
            if(bl.getBelegung().getLeavingDate().equals(b.getArrivingDate()) || bl.getBelegung().getArrivingDate().equals(b.getLeavingDate())) {
                return bl.getLabelColor().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter();
            }
        }
        switch (b.getFlatNumber()) {
            case(1): return Color.BLACK;
            case(2): return Color.BLUE;
            case(3): return Color.YELLOW;
            case(4): return Color.CYAN;
            case(5): return Color.RED;
            case(6): return Color.GRAY;
            case(7): return Color.GREEN;

        }
        return Color.BLACK;

    }

    public void drawBookingLabels(Graphics g) {

       for(BookingLabel bl: bookingLabels) {
            g.setColor(bl.getLabelColor());
            g.fillRect(bl.getxPos(), bl.getyPos(), bl.getWidth(), bl.getHeight());
        }
    }





}


