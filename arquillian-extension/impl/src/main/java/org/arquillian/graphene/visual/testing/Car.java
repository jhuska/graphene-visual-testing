package org.arquillian.graphene.visual.testing;

/**
 *
 * @author jhuska
 */
public class Car {
    
    private int numberOfPassangers;
    
    public static final int CONSTANT = 5;
    
    public Car(int amount) {
        this.numberOfPassangers = amount;
    }
    
    public int passangerGetOut() {
        this.numberOfPassangers = this.numberOfPassangers - 1;
        return numberOfPassangers;
    }
    
    public static void main(String[] args) {
        Car porsche = new Car(4);
        int currentNumber  = porsche.passangerGetOut();
        System.out.println(currentNumber);
    }
}
