
class Author{
    String name;
    String surname;
    String mail;

    Author(String name, String surname, String mail){
        this.name = name;
        this.surname = surname;
        this.mail = mail;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
}
class Book{
    String isbn;
    String title;
    Author author;
    double price;

    Book(String isbn, String title, Author author, double price){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }
    Book(String isbn, String title, Author author){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = 15.25;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }
    public Author getAuthor() {
        return author;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
class EBook extends Book{
    String downloadUrl;
    double sizeMb;

    EBook(String isbn, String title, Author author, double price, String downloadUrl, double sizeMb){
        super(isbn, title, author, price);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }
    EBook(String isbn, String title, Author author, String downloadUrl, double sizeMb){
        super(isbn, title, author);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public double getSizeMb() {
        return sizeMb;
    }
}
class PaperBook extends Book{
    int shippingWeight;
    Boolean inStock;

    PaperBook(String isbn, String title, Author author, double price, int shippingWeight, boolean inStock){
        super(isbn, title, author, price);
        this.shippingWeight = shippingWeight;
        this.inStock = inStock;
    }
    PaperBook(String isbn, String title, Author author, boolean inStock){
        super(isbn, title, author);
        this.inStock = inStock;
        shippingWeight = (int)(Math.random() * 10 + 5);
    }
    public int getShippingWeight() {
        return shippingWeight;
    }
    public Boolean getInStock() {
        return inStock;
    }
    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }
}
