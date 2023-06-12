import java.util.Date;
class City{
    private String postalCode;
    private String name;

    City(String postalCode, String name){
        this.postalCode = postalCode;
        this.name = name;
    }
    public String getPostalCode(){
        return this.postalCode;
    }
    public String getName(){
        return this.name;
    }
}
class Person{
    String name;
    String surname;
    String phoneNumber;

    Person(String name, String surname, String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }
    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}
class Ticket{
    private City from;
    private City to;
    private java.util.Date date;
    private int seat;

    Ticket(City from, City to, java.util.Date date, int seat ){
        this.from = from;
        this.to = to;
        this.date = date;
        this.seat = seat;
    }
    Ticket(City from, City to, int seat){
        this.from = from;
        this.to = to;
        this.seat = seat;
        this.date = new Date();
        date.setTime(date.getTime() + (1000*60*60*24));
    }
    public City getFrom() {
        return from;
    }
    public City getTo() {
        return to;
    }
    public java.util.Date getDate() {
        return date;
    }
    public void setDate(java.util.Date date) {
        this.date = date;
    }
    public int getSeat() {
        return seat;
    }
    public void setSeat(int seat) {
        this.seat = seat;
    }
}