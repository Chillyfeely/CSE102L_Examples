//mashallah java programming


interface Sellable{
    double getPrice();
    String getName();
}

interface Package<T> {
    T extract();
    boolean pack(T item);
    boolean isEmpty();
}

interface Wrappable extends Sellable {}

abstract class Product implements Sellable{
    private String name;
    private double price;

    Product(String name, double price){
        this.name = name;
        this.price = price;
    }
    @Override
    public String toString(){
        return "(" + name + ", " + price + ")";
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public double getPrice(){
        return price;
    }
}

class Mirror extends Product{
    private int width;
    private int height;

    Mirror(int width, int height){
        super("Mirror", 2);
        this.width = width;
        this.height = height;
    }

    public int getArea(){
        return width * height;
    }

    public <T> T reflect(T item){
        System.out.println("Reflecting " + item);
        return item;
    }

    @Override
    public String getName(){
        return "Mirror";
    }

    @Override
    public double getPrice(){
        return 2;
    }

}

class Paper extends Product implements Wrappable{
    private String note;

    Paper(String note){
        super("A4", 3);
        this.note = note;
    }

    @Override
    public double getPrice(){
        return 3;
    }

    @Override
    public String getName(){
        return "A4";
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class Matroschka<T extends Wrappable> extends Product implements Wrappable, Package<T>{
    private T item;

    Matroschka(T item){
        super("Doll", 5+item.getPrice());
    }

    @Override
    public String toString(){
        return super.toString() + "{" + item + "}";
    }

    @Override
    public double getPrice(){
        return 5+item.getPrice();
    }

    @Override
    public String getName(){
        return "Doll";
    }

    @Override
    public T extract() {
        return null;
    }

    @Override
    public boolean pack(T item) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

class Box<T extends Sellable> implements Package<T>{
    private T item;
    private boolean seal;

    Box(){
        this.item = null;
        this.seal = false;
    }

    Box(T item){
        this.item = item;
        this.seal = true;
    }

    @Override
    public T extract(){
        if(!isEmpty()){
            T temp = item;
            item = null;
            return temp;
        }else{
            return null;
        }
    }



    @Override
    public boolean pack (T item){
        if(isEmpty()){
            this.item = item;
            return true;
        }else{
            return false;
        }
    }




    @Override
    public boolean isEmpty(){
        if(item == null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return "" + "{" + item + "}";
    }
}