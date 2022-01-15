package pl.piotrszczachor;

import java.util.Arrays;

public class ElevatorCar extends Thread{
    int floor=0;

    public int getFloor() {
        return floor;
    }

    enum Tour {UP, DOWN};
    Tour tour = Tour.UP;
    enum Movement {STOP,MOVING};
    Movement movementState = Movement.STOP;

    public void run(){
        for(;;){
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /* Winda stoi na piętrze i ma kierunek ustawiony w górę, powyżej są piętra na które ma dojechać
               więc wprawiamy ją w ruch bez zmiany aktualnego kierunku*/
            if(tour == Tour.UP && movementState == Movement.STOP && ElevatorStops.get().hasStopAbove(floor)){
                movementState = Movement.MOVING;
            }
            /* Winda stoi na piętrze i ma kierunek ustawiony w dół, poniżej są piętra na które ma dojechać
               więc wprawiamy ją w ruch bez zmiany aktualnego kierunku*/
            if(tour == Tour.DOWN && movementState == Movement.STOP && ElevatorStops.get().hasStopBelow(floor)){
                movementState = Movement.MOVING;
            }
            /* Winda stoi na piętrze i ma kierunek ustawiony w górę, powyżej nie ma pięter na które ma dojechać
               ale poniżej są piętra na które ma dojechać więc wprawiamy ją w ruch i zmieniamy aktualny kierunek*/
            if(tour == Tour.UP && movementState == Movement.STOP &&
                    !ElevatorStops.get().hasStopAbove(floor) && ElevatorStops.get().hasStopBelow(floor)) {
                tour = Tour.DOWN;
                movementState = Movement.MOVING;
            }
            /* Winda stoi na piętrze i ma kierunek ustawiony w dół, poniżej nie ma pięter na które ma dojechać
               ale powyżej są piętra na które ma dojechać więc wprawiamy ją w ruch i zmieniamy aktualny kierunek*/
            if(tour == Tour.DOWN && movementState == Movement.STOP &&
                    ElevatorStops.get().hasStopAbove(floor) && !ElevatorStops.get().hasStopBelow(floor)) {
                tour = Tour.UP;
                movementState = Movement.MOVING;
            }
            //Winda jedzie do góry
            if(tour == Tour.UP && movementState == Movement.MOVING){
                boolean stopped = false;
                /* Winda dojezda na piętro na ktorym ma sie zatrzymac, była w ruchu więc ją zatrzymujemy
                   a piętro na które dojezdzamy usuwamy z listy pięter na ktorych winda ma sie zatrzymac jadac w gore*/
                if (ElevatorStops.get().whileMovingUpSholudStopAt(floor)) {
                    movementState = Movement.STOP;
                    ElevatorStops.get().clearStopUp(floor);
                    stopped = true;
                    System.out.printf("Litf stops at %d\n", floor);
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(floor<ElevatorStops.get().getMaxSetFloor() && !stopped){
                    floor += 1;
                }

            }
            // Winda jedzie w dół
            if(tour == Tour.DOWN && movementState == Movement.MOVING){
                boolean stopped = false;
                /* Winda dojezda na piętro na ktorym ma sie zatrzymac, była w ruchu więc ją zatrzymujemy
                   a piętro na które dojezdzamy usuwamy z listy pięter na ktorych winda ma sie zatrzymac jadac w gore*/
                if (ElevatorStops.get().whileMovingDownSholudStopAt(floor)) {
                    movementState = Movement.STOP;
                    ElevatorStops.get().clearStopDown(floor);
                    stopped = true;
                    System.out.printf("Litf stops at %d\n", floor);
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(floor>ElevatorStops.get().getMinSetFloor() && !stopped){
                    floor -= 1;
                }
            }
            System.out.println(floor);
        }
    }
}
