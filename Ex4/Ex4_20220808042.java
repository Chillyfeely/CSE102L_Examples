import java.util.*;

class Computer{
    protected CPU cpu;
    protected RAM ram;

    Computer(CPU cpu, RAM ram){}
    public void run(){
        int bounds = getCapacity();
        sumDiagonal = 0;
        for(int i = 0; i < bounds; i++){
            sumDiagonal = compute(getValue(bounds,bounds), sumDiagonal);
        }
        setValue(0,0,sumDiagonal);
    }
    public String toString(){
        return "Computer;" + cpu + " " + ram;
    }
}
class Laptop extends Computer{
    int milliAmp;
    int battery;
    Laptop(CPU cpu, RAM ram, int milliAmp){
        super(cpu, ram);
        this.milliAmp = milliAmp;
        this.battery = (int) (milliAmp * 0.3);;
    }
    public int batteryPercentage(){
        return (int) ((float) battery / milliAmp * 100);
    }
    public void charge(){
        while(battery < 90){
            battery += 2;
        }
        if (battery == 91){
            battery = 90;
        }
    }
    @Override
    public void run(){
        if(battery > 5){
            battery -= 3;
        }
        else{
            charge();
        }
    }
    @Override
    public String toString() {
        return super.toString() + " " + battery;
    }
}
class Desktop extends Computer {

    private ArrayList<String> peripherals;

    public Desktop(String cpu, int ram, String... peripherals) {
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
    }

    @Override
    public void run() {
        super.run();
        int bounds2 = getCapacity();
        int sumAll = 0;
        int[][] emptyArr = new int[bounds2][bounds2];
        for (int i = 0; i < emptyArr.length; i++){
            for (int j = 0; j < emptyArr[i].length; j++){
                sumAll = compute(getValue(i,j), sumAll);
            }
        }
        
    }

    public void plugIn(String peripheral) {
        peripherals.add(peripheral);
    }

    public String plugOut() {
        return peripherals.remove(peripherals.size() - 1);
    }

    public String plugOut(int index) {
        return peripherals.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        for (String peripheral : peripherals) {
            sb.append(" ").append(peripheral);
        }
        return sb.toString();
    }
}
class CPU{
    private String name;
    private double clock;

    CPU(String name, double clock){
        this.name = name;
        this.clock = clock;
    }

    public double getClock() {
        return clock;
    }
    public String getName() {
        return name;
    }
    public int compute(int a, int b){
        return a+b;
    }
    public String toString(){
        return "CPU:" + name + " " + clock + "Ghz";
    }
}
class RAM{
    private String type;
    private int capacity;
    private int[][] memory;

    RAM(String type, int capacity){
        this.type = type;
        this.capacity = capacity;
        initMemory();
    }

    public int getCapacity() {
        return capacity;
    }
    public String getType() {
        return type;
    }
    private void initMemory(){
        Random random = new Random();
        this.memory = new int[capacity][capacity];
        for(int i=0; i<memory.length; i++){
            for(int j=0; j<memory[i].length; j++){
                memory[i][j] = random.nextInt(11);
            }
        }
    }
    private boolean check(int i, int j){
        int rows = memory;
        int cols = memory;

        if(i < 0 || i >= rows || j < 0 || j >= cols){
            return false;
        }
        else{
            return true;
        }    
    }
    public int getValue(int i, int j){
        if (check(i, j)){
            return memory[i][j];
        }
        else{
            return -1;
        }
    }
    public void setValue(int i, int j, int value){
        if(check(i,j)){
            memory[i][j] = value;
        }
    }
    public String toString(){
        return "RAM:" + type + " " + capacity + " GB";
    }

}