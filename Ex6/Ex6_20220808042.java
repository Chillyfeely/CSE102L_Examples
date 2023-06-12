import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

abstract class Product implements Comparable<Product> {
    private String name;
    private double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    public int compareTo(Product other) {
        return Double.compare(price, other.price);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[name=" + name + ", price=" + price + "]";
    }
}
abstract class Book extends Product{
    private String author;
    private int pageCount;

    Book(String name, double price, String author, int pageCount) {
        super(name, price);
        this.author = author;
        this.pageCount = pageCount;
    }
    public String getAuthor() {
        return author;
    }
    public int getPageCount() {
        return pageCount;
    }
}
class ReadingBook extends Book{
    private String genre;
    ReadingBook(String name, double price, String author, int pageCount, String genre) {
        super(name, price, author, pageCount);
        this.genre = genre;
    }
    public String getGenre() {
        return genre;
    }
}
class ColoringBook extends Book implements Colorable{
    private String genre;
    ColoringBook(String name, double price, String author, int pageCount, String genre) {
        super(name, price, author, pageCount);
        this.genre = genre;
    }
    public String getGenre() {
        return genre;
    }
    @Override
    public void paint(String color) {}
}
class ToyHorse extends Product implements Rideable{
    ToyHorse(String name, double price) {
        super(name, price);
    }
    @Override
    public void ride() {}
}
class Bicycle extends Product implements Colorable, Rideable{
    private String color;
    Bicycle(String name, double price, String color) {
        super(name, price);
        this.color = color;
    }
    public String getColor() {
        return color;
    }
    @Override
    public void paint(String color) {}
    @Override
    public void ride() {}
}
class User{
    private String username;
    private String email;
    private PaymentMethod payment;
    private List<Product> cart;
    User(String username, String email){
        this.username = username;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }
    public Product getProduct(int index) {
        return cart.get(index);
    }
    public void addProduct(Product product) {
        cart.add(product);
    }
    public void removeProduct(int index) {
        cart.remove(index);
    }
    public void purchase(){
        double total = 0;
        for(Product product : cart){
            total += product.getPrice();
        }
        if(payment.pay(total)){
            cart.clear();
        }
    }
}
class CreditCard implements PaymentMethod{
    private long cardNumber;
    private String cardHolderName;
    private java.util.Date expirationDate;
    private int cvv;
    CreditCard(long cardNumber, String cardHolderName, java.util.Date expirationDate, int cvv){
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }
    @Override
    public boolean pay(double amount) {
        return false;
    }
}
class PayPal implements PaymentMethod{
    private String username;
    private String password;
    PayPal(String username, String password) throws NoSuchAlgorithmException{
        this.username = username;
        SHA256EncryptionMethod sha256 = new SHA256EncryptionMethod();
        this.password = sha256.hash(password);
    }
    @Override
    public boolean pay(double amount) {
        return false;
    }
}
class SHA256EncryptionMethod {
    public String hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md5.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
interface Colorable {
    void paint(String color);
}
interface Rideable{
    void ride();
}
interface PaymentMethod{
    boolean pay(double amount);
}