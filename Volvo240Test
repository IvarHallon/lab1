
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class Volvo240Test {

    @Test
    void construction() {
        Volvo240 v = new Volvo240();

        //Verify construction
        assertEquals(4, v.getNrDoors());
        assertEquals(100.0, v.getEnginePower());
        assertEquals(0.0, v.getCurrentSpeed());
        assertEquals("Volvo240", v.getModelName());
        assertEquals(Color.black, v.getColor());

        //Start position and direction if movable/diretion is used
        assertEquals(0.0, v.getX());
        assertEquals(0.0, v.getY());
        assertEquals(Direction.NORTH, v.getDirection());
    }

    @Test
    void setColor_changesColor() {
        Volvo240 v = new Volvo240();

        //Change color and verify
        v.setColor(Color.white);
        assertEquals(Color.white, v.getColor());
    }

    @Test
    void startEngine_and_stopEngine() {
        Volvo240 v = new Volvo240();

        //startEngine should be a small speed, (0,1)
        v.startEngine();
        assertEquals(0.1, v.getCurrentSpeed(), 1e-12); // 1e-12, double-precision

        // stopEngine should always put speed to 0
        v.stopEngine();
        assertEquals(0.0, v.getCurrentSpeed(), 1e-12);
    }

    @Test
    void gas_EnginePower_Validation() {
        Volvo240 v = new Volvo240();
        v.startEngine();
        double before = v.getCurrentSpeed();

        //Gas should increse the speed, never decreese
        v.gas(1.0);
        assertTrue(v.getCurrentSpeed() >= before);

        //Repeeted gas can not exceed enginePower
        for (int i = 0; i < 1000; i++) v.gas(1.0);
        assertTrue(v.getCurrentSpeed() <= v.getEnginePower());

        //gas() should throw IllegalArgumentException if amount not in [0,1]
        assertThrows(IllegalArgumentException.class, () -> v.gas(-0.01));
        assertThrows(IllegalArgumentException.class, () -> v.gas(1.01));
    }

    @Test
    void brake_decreasesSpeed_toZero_andValidation() {
        Volvo240 v = new Volvo240();
        v.startEngine();
        v.gas(1.0); //get a speed so we can brake

        double before = v.getCurrentSpeed();

        //brake should decreese the speed, never increese
        v.brake(0.5);
        assertTrue(v.getCurrentSpeed() <= before);

        //Repeeted brake can't go under 0
        for (int i = 0; i < 1000; i++) v.brake(1.0);
        assertEquals(0.0, v.getCurrentSpeed(), 1e-12);

        //brake() should also throw IllegalArgumentException if amount not in [0,1]
        assertThrows(IllegalArgumentException.class, () -> v.brake(-0.01));
        assertThrows(IllegalArgumentException.class, () -> v.brake(1.01));
    }

    @Test
    void move_and_turn_changesPosition_andDirection() {
        Volvo240 v = new Volvo240();
        v.startEngine();

        double startX = v.getX();
        double startY = v.getY();
        double s = v.getCurrentSpeed();

        //move() in NORTH should increese y with currentSpeed
        v.move();
        assertEquals(startX, v.getX(), 1e-12);
        assertEquals(startY + s, v.getY(), 1e-12);

        //turnRight: NORTH -> EAST
        v.turnRight();
        assertEquals(Direction.EAST, v.getDirection());

        //move() i EAST ska öka x med currentSpeed
        double beforeX = v.getX();
        v.move();
        assertEquals(beforeX + v.getCurrentSpeed(), v.getX(), 1e-12);

        //turnRight: EAST -> SOUTH
        v.turnRight();
        assertEquals(Direction.SOUTH, v.getDirection());

        //move() in SOUTH should decreese y with currentSpeed
        double beforeY = v.getY();
        v.move();
        assertEquals(beforeY - v.getCurrentSpeed(), v.getY(), 1e-12);

        // turnLeft: SOUTH -> EAST
        v.turnLeft();
        assertEquals(Direction.EAST, v.getDirection());
    }
}
