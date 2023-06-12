interface Common<T> {
    boolean isEmpty();

    T peek();

    int size();
}

interface Stack<T> extends Common<T> {
    boolean push(T item);

    T pop();
}

interface Node<T> {
    int DEFAULT_CAPACITY = 2;

    double getPriority();

    T getNext();

    void setNext(T item);

}

interface PriorityQueue<T> extends Common<T> {
    int FLEET_CAPACITY = 3;

    boolean enqueue(T item);

    T dequeue();
}

interface Sellable {
    double getPrice();

    String getName();
}

interface Package<T> {
    T extract();

    boolean pack(T item);

    boolean isEmpty();

    double getPriority();
}

interface Wrappable extends Sellable {
}

public class Ex9_20220808042 {

}

//isEmpty,peek,size @ Container and Cargo Fleet
public class Ex9_20220808042 {
}

abstract class Product implements Sellable {
    private String name;
    private double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "(" + name + ", " + price + ")";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }
}

class Mirror extends Product {
    private int width;
    private int height;

    Mirror(int width, int height) {
        super("Mirror", 2);
        this.width = width;
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

    public <T> T reflect(T item) {
        System.out.println("Reflecting " + item);
        return item;
    }

    @Override
    public String getName() {
        return "Mirror";
    }

    @Override
    public double getPrice() {
        return 2;
    }

}

class Paper extends Product implements Wrappable {
    private String note;

    Paper(String note) {
        super("A4", 3);
        this.note = note;
    }

    @Override
    public double getPrice() {
        return 3;
    }

    @Override
    public String getName() {
        return "A4";
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class Matroschka<T extends Wrappable> extends Product implements Wrappable, Package<T> {
    private T item;

    Matroschka(T item) {
        super("Doll", 5 + item.getPrice());
    }

    @Override
    public String toString() {
        return super.toString() + "{" + item + "}";
    }

    @Override
    public double getPrice() {
        return 5 + item.getPrice();
    }

    @Override
    public String getName() {
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

    @Override
    public double getPriority() {
        throw new UnsupportedOperationException("Not implemented");
    }
}

class Box<T extends Sellable> implements Package<T> {
    private T item;
    private boolean seal;
    private int distanceToAddress;

    Box() {
        this.item = null;
        this.seal = false;
    }

    Box(T item, int distanceToAddress) {
        this.item = item;
        this.distanceToAddress = distanceToAddress;
        this.seal = true;
    }

    public int getDistanceToAddress() {
        return distanceToAddress;
    }

    @Override
    public T extract() {
        if (!isEmpty()) {
            T temp = item;
            item = null;
            return temp;
        } else {
            return null;
        }
    }


    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean isEmpty() {
        if (item == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getPriority() {
        return distanceToAddress / item.getPrice();
    }

    @Override
    public String toString() {
        return "" + "{" + item + "}";
    }
}

class Container implements Stack<Box>, Node<Container>, Comparable<Container> {
    private Box[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;

    Container() {
        boxes = new Box[DEFAULT_CAPACITY];
        top = -1;
        next = null;
        priority = 0;
    }

    public String toString() {
        return "Container with priority " + priority;
    }

    @Override
    public boolean push(Box item) {
        if (top == boxes.length - 1) {
            Box[] temp = new Box[boxes.length * 2];
            for (int i = 0; i < boxes.length; i++) {
                temp[i] = boxes[i];
            }
            boxes = temp;
        }
        boxes[++top] = item;
        size++;
        return true;
    }

    @Override
    public Box pop() {
        if (top == -1) {
            return null;
        }
        Box temp = boxes[top--];
        size--;
        return temp;
    }

    @Override
    public Container getNext() {
        return next;
    }

    @Override
    public void setNext(Container item) {
        next = item;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Container o) {

        double thisPrio = 0;
        double oPrio = 0;
        double thisDistance = 0;
        double oDistance = 0;

        for (int i = 0; i < size; i++) {
            thisPrio += boxes[i].getPriority();
        }

        for (int i = 0; i < o.size; i++) {
            oPrio += o.boxes[i].getPriority();
        }

        for (int i = 0; i < size; i++) {
            thisDistance += boxes[i].getDistanceToAddress();
        }

        for (int i = 0; i < o.size; i++) {
            oDistance += o.boxes[i].getDistanceToAddress();
        }


        if (thisPrio > oPrio) {
            return 1;
        } else if (thisPrio < oPrio) {
            return -1;
        } else {
            if (thisDistance > oDistance) {
                return 1;
            } else if (thisDistance < oDistance) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        if (top == -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Box peek() {
        if (top == -1) {
            return null;
        }
        return boxes[top];
    }

    @Override
    public int size() {
        return size;
    }
}

class CargoFleet implements PriorityQueue<Container> {
    private Container head;
    private int size;

    CargoFleet() {
        head = null;
        size = 0;
    }

    @Override
    public boolean enqueue(Container item) {
        if (head == null) {
            head = item;
            size++;
            return true;
        } else {
            Container temp = head;
            Container prev = null;
            while (temp != null) {
                if (temp.compareTo(item) == 1) {
                    if (prev == null) {
                        item.setNext(head);
                        head = item;
                        size++;
                        return true;
                    } else {
                        prev.setNext(item);
                        item.setNext(temp);
                        size++;
                        return true;
                    }
                } else {
                    prev = temp;
                    temp = temp.getNext();
                }
            }
            prev.setNext(item);
            size++;
            return true;
        }
    }

    @Override
    public Container dequeue() {
        if (head == null) {
            return null;
        } else {
            Container temp = head;
            head = head.getNext();
            size--;
            return temp;
        }
    }

    @Override
    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Container peek() {
        return head;
    }

    @Override
    public int size() {
        return size;
    }
}

class CargoCompany {
    private Container stack;
    private CargoFleet queue;

    CargoCompany() {
        stack = new Container();
        queue = new CargoFleet();
    }

    public void add(Box<?> box) {
        if (stack.isEmpty()) {
            stack.push(box);
        } else {
            if (queue.enqueue(stack)) {
                stack = new Container();
                add(box);
            } else {
                ship(queue);
            }
        }
    }

    private void ship(CargoFleet fleet) {
        while (!fleet.isEmpty()) {
            Container container = fleet.dequeue();
            empty(container);
        }
    }

    private void empty(Container container) {
        while (!container.isEmpty()) {
            Box<?> box = container.pop();
            Object result = deliver(box);
            System.out.println(result);
        }
    }

    private <T extends Sellable> Sellable deliver(Box<T> box) {
        if (!box.isEmpty()) {
            return box.extract();
        } else {
            return null;
        }
    }
}

