package Model;

import java.awt.*;

public class BookingLabel  {

    private Booking belegung;
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    private Color labelColor;

    public BookingLabel(Booking belegung, int xPos, int yPos, int width, int height) {

        this.belegung = belegung;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public Color getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
    }

    public Booking getBelegung() {
        return belegung;
    }

    public void setBelegung(Booking belegung) {
        this.belegung = belegung;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
