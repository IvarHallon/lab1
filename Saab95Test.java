
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class Saab95Test {

    @Test
    void construction() {
        Saab95 s = new Saab95();

        //Verify construction
        assertEquals(2, s.getNrDoors());
        assertEquals(125.0, s.getEnginePower());
        assertEquals(0.0, s.getCurrentSpeed());
        assertEquals("Saab95", s.getModelName());
        assertEquals(Color.red, s.getColor());

        //Start position and direction if movable/diretion is used
        assertEquals(0.0, s.getX());
        assertEquals(0.0, s.getY());
        assertEquals(Direction.NORTH, s.getDirection());
    }

    @Test
    void setColor_changesColor() {
        Saab95 s = new Saab95();

        //Change color and verify
        s.setColor(Color.blue);
        assertEquals(Color.blue, s.getColor());
    }

    @Test
    void startStopEngine_behavesCorrectly() {
        Saab95 s = new Saab95();

        //startEngine should be a small speed, (0,1)
        s.startEngine();
        assertEquals(0.1, s.getCurrentSpeed(), 1e-12);

        // stopEngine should always put speed to 0
        s.stopEngine();
        assertEquals(0.0, s.getCurrentSpeed(), 1e-12);
    }

    @Test
    void turbo_affectsAcceleration() {
        Saab95 s = new Saab95();

        //without turbo
        s.startEngine();
        s.setTurboOff();   //Ensure turbo off
        s.stopEngine();    //Turn off engine to start
        s.startEngine();
        s.gas(1.0);
        double speedNoTurbo = s.getCurrentSpeed();

        //with turbo
        s.stopEngine();
        s.startEngine();
        s.setTurboOn();
        s.gas(1.0);
        double speedTurbo = s.getCurrentSpeed();

        //with turbo should the acceleration (speedFactor) be larger which gives more speed after same amount of gas
        assertTrue(speedTurbo > speedNoTurbo);
    }

    @Test
    void gas_EnginePower_Validation() {
        Saab95 s = new Saab95();
        s.startEngine();

        double before = s.getCurrentSpeed();

        //gas increases the speed
        s.gas(0.7);
        assertTrue(s.getCurrentSpeed() > before);

        //Speed can never exceed enginePower
        for (int i = 0; i < 1000; i++) s.gas(1.0);
        assertTrue(s.getCurrentSpeed() <= s.getEnginePower());

        //gas() should throw IllegalArgumentException if amount not in [0,1]
        assertThrows(IllegalArgumentException.class, () -> s.gas(-0.01));
        assertThrows(IllegalArgumentException.class, () -> s.gas(1.01));
    }

    @Test
    void brake_decreasesSpeed_toZero_andValidation() {
        Saab95 s = new Saab95();
        s.startEngine();
        s.gas(1.0); //get Speed

        double before = s.getCurrentSpeed();

        //Brake decrease the speed
        s.brake(0.5);
        assertTrue(s.getCurrentSpeed() <= before);

        //Never under 0
        for (int i = 0; i < 1000; i++) {
            s.brake(1.0);
            assertEquals(0.0, s.getCurrentSpeed(), 1e-12);

        }
        //Validation
        assertThrows(IllegalArgumentException.class, () -> s.brake(-0.01));
        assertThrows(IllegalArgumentException.class, () -> s.brake(1.01));



    }

    @Test
    void move_and_turn_changesPosition_andDirection() {
        Saab95 s = new Saab95();
        s.startEngine();
        double s0 = s.getCurrentSpeed();

        // move in NORTH increase y
        double y0 = s.getY();
        s.move();
        assertEquals(y0 + s0, s.getY(), 1e-12);

        // turnLeft: NORTH -> WEST
        s.turnLeft();
        assertEquals(Direction.WEST, s.getDirection());

        // move in WEST decrease x
        double xBefore = s.getX();
        s.move();
        assertEquals(xBefore - s.getCurrentSpeed(), s.getX(), 1e-12);

        // turnRight: WEST -> NORTH
        s.turnRight();
        assertEquals(Direction.NORTH, s.getDirection());
    }
}
