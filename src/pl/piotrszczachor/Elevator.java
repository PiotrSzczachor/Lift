package pl.piotrszczachor;

public class Elevator {
    // tworzymy 3 wątki
    static ElevatorCar car = new ElevatorCar();
    static ExternalPanelsAgent externalPanelAgent = new ExternalPanelsAgent(car);
    static InternalPanelAgent internalPanelAgent = new InternalPanelAgent(car);

    // symulacja przywołania windy z panelu zewnętrznego
    static void makeExternalCall(int atFloor,boolean directionUp){
        try {
            externalPanelAgent.input.put(new ExternalPanelsAgent.ExternalCall(atFloor,directionUp));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // symulacja wyboru pietra w panelu wewnętrznym
    static void makeInternalCall(int toFloor){
        try {
            internalPanelAgent.input.put(new InternalPanelAgent.InternalCall(toFloor));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // uruchomienie wątków
    static void init(){
        car.start();
        externalPanelAgent.start();
        internalPanelAgent.start();
    }

    static void test1() throws InterruptedException {
        init();
        makeExternalCall(4,false);
        Thread.currentThread().sleep(1000);
        makeInternalCall(2);
        Thread.currentThread().sleep(1000);
        makeInternalCall(7);
        Thread.currentThread().sleep(1000);
        makeInternalCall(1);
    }

    // miesjce na kod testowy
    public static void main(String[] args) throws InterruptedException {
        test1();
    }
}