package Model;

import java.util.Date;

public class Booking {

    private String id;
    private int flatNumber;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfAnimals;
    private Date arrivingDate;
    private Date leavingDate;
    private String pricePerNightTwo;
    private String pricePerNightAdditionalPerson;
    private String cleaningPrice;

    private String pricePerNightAnimal;
    private String totalPrice;
    private String listOfNames;
    private int numberOfNights;
    private String clientId;
    private int invoiceStatus;

    public Booking(String id, int flatNumber, int numberOfAdults, int numberOfChildren, int numberOfAnimals, Date arrivingDate, Date leavingDate, String pricePerNightTwo, String pricePerNightAdditionalPerson, String cleaningPrice, String pricePerNightAnimal, String totalPrice, String listOfNames, int numberOfNights, String clientId, int invoiceStatus) {
        this.id = id;
        this.flatNumber = flatNumber;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.numberOfAnimals = numberOfAnimals;
        this.arrivingDate = arrivingDate;
        this.leavingDate = leavingDate;
        this.pricePerNightTwo = pricePerNightTwo;
        this.pricePerNightAdditionalPerson = pricePerNightAdditionalPerson;
        this.cleaningPrice = cleaningPrice;
        this.pricePerNightAnimal = pricePerNightAnimal;
        this.totalPrice = totalPrice;
        this.listOfNames = listOfNames;
        this.numberOfNights = numberOfNights;
        this.clientId = clientId;
        this.invoiceStatus = invoiceStatus;
    }

    public Booking(int flatNumber, int numberOfAdults, int numberOfChildren, int numberOfAnimals, Date arrivingDate, Date leavingDate, String listOfNames, String clientId) {
        this.flatNumber = flatNumber;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.numberOfAnimals = numberOfAnimals;
        this.arrivingDate = arrivingDate;
        this.leavingDate = leavingDate;
        this.listOfNames = listOfNames;
        this.clientId = clientId;
    }

    public String getPricePerNightTwo() {
        return pricePerNightTwo;
    }

    public void setPricePerNightTwo(String pricePerNightTwo) {
        this.pricePerNightTwo = pricePerNightTwo;
    }

    public String getPricePerNightAdditionalPerson() {
        return pricePerNightAdditionalPerson;
    }

    public void setPricePerNightAdditionalPerson(String pricePerNightAdditionalPerson) {
        this.pricePerNightAdditionalPerson = pricePerNightAdditionalPerson;
    }

    public String getCleaningPrice() {
        return cleaningPrice;
    }

    public void setCleaningPrice(String cleaningPrice) {
        this.cleaningPrice = cleaningPrice;
    }

    public String getPricePerNightAnimal() {
        return pricePerNightAnimal;
    }

    public void setPricePerNightAnimal(String pricePerNightAnimal) {
        this.pricePerNightAnimal = pricePerNightAnimal;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public Date getArrivingDate() {
        return arrivingDate;
    }

    public void setArrivingDate(Date arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }



    public String getListOfNames() {
        return listOfNames;
    }

    public void setListOfNames(String listOfNames) {
        this.listOfNames = listOfNames;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
