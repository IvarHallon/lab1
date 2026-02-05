import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;
public class ScaniaTest {
    @Test // test if the construction is correct, the expected value should be equal value given
    void construction() {
        Scania s = new Scania();
//Verify construction
        assertEquals(2, s.getNrDoors());
        assertEquals(450, s.getEnginePower());
        assertEquals(0.0, s.getCurrentSpeed());
        assertEquals("Scania", s.getModelName());
        assertEquals(Color.blue, s.getColor());
//Start position and direction if movable/diretion is used
        assertEquals(0.0, s.getX());
        assertEquals(0.0, s.getY());
        assertEquals(Direction.NORTH, s.getDirection());
    }
    @Test // test the angle of the raise bed
    void shouldNotBeRaised () {
        Scania s = new Scania (); // s är det nya scania
        s.gas(1); //
        boolean succeeded = false; //antagande
        try {
            s.flank(10); // ökar med v = 10 grader
        }
        catch (IllegalArgumentException e) {
            succeeded = true;
        }
        assertTrue (succeeded);
    }
    @Test // test the angle of the raise bed
    void shouldBeRaised (){
        Scania s = new Scania(); // s är det nya scania
        s.brake(0);
        int before = s.getangle();
        s.flank( 20); //hur mycket den ska höja med alltså 20 grader
        int after = s.getangle();
        assertEquals( 20, after);
        assertEquals (0, before);
    }
    @Test // test the angle of the raise bed
    void shouldNotBeRaised2 () {
        Scania s = new Scania (); // s är det nya scania
        boolean succeeded = false; //antagande
        try {
            s.flank(100); // ökar med v = 100 grader
        }
        catch (IllegalArgumentException e) {
            succeeded = true;
        }
        assertTrue (succeeded);
    }
// inte köra när
}
