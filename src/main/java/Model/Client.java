package Model;

public class Client {

    private String id;
    private String fullName;
    private String mobilePhone;
    private String phone;
    private String email;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private String taxId;

    public Client(String fullName, String mobilePhone, String phone, String email, String street, String houseNumber, String postalCode, String city, String country, String taxId) {
        this.fullName = fullName;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.email = email;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.taxId = taxId;
    }

    public Client(String fullName, String email, String street, String houseNumber, String postalCode, String city, String country) {
        this.fullName = fullName;
        this.email = email;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public Client(String id, String fullName, String mobilePhone, String phone, String email, String street, String houseNumber, String postalCode, String city, String country, String taxId) {
        this.id = id;
        this.fullName = fullName;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.email = email;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.taxId = taxId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
