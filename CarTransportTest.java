import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTransportTest {

    @Test
    void RampLoweredOnlyIfStopped() {
        CarTransport t = new CarTransport();
        t.startEngine();
        t.gas(1);
        assertThrows(IllegalStateException.class, t::lowerRamp);

        t.stopEngine();
        assertDoesNotThrow(t::lowerRamp);
    }

    @Test
    void CannotDriveWithRampDown() {
        CarTransport t = new CarTransport();
        t.lowerRamp();

        assertThrows(IllegalStateException.class, t::startEngine);
        assertThrows(IllegalStateException.class, () -> t.gas(0.5));

        t.raiseRamp();
        assertDoesNotThrow(t::startEngine);
    }

    @Test
    void loadOnlyIfRampDownStillAndInDistance() {
        CarTransport t = new CarTransport();
        t.lowerRamp();
        Volvo240 v = new Volvo240();

        // Bilen för långt bort → får inte lastas
        v.x = t.x + 5;
        v.y = t.y;
        assertThrows(IllegalStateException.class, () -> t.load(v));

        // Flytta nära → OK
        v.x = t.x;
        v.y = t.y;
        assertDoesNotThrow(() -> t.load(v));
        assertEquals(1, t.getLoadCount());
    }

    @Test
    void CannotLoadTransport() {
        CarTransport t1 = new CarTransport();
        CarTransport t2 = new CarTransport();

        t1.lowerRamp();
        t2.x = t1.x; t2.y = t1.y;

        assertThrows(IllegalArgumentException.class, () -> t1.load(t2));
    }

    @Test
    void CapacityLimitedToSix() {
        CarTransport t = new CarTransport();
        t.lowerRamp();

        // Lasta 6 bilar (max)
        for (int i = 0; i < 6; i++) {
            AbstractCar c = new Volvo240();
            c.x = t.x; c.y = t.y;
            t.load(c);
        }
        assertEquals(6, t.getLoadCount());

        // En sjunde bil → ej tillåtet
        Volvo240 extra = new Volvo240();
        extra.x = t.x; extra.y = t.y;
        assertThrows(IllegalStateException.class, () -> t.load(extra));
    }

    @Test
    void FILOAndCarPlacedBehindTransport() {
        CarTransport t = new CarTransport();
        t.lowerRamp();

        Volvo240 first = new Volvo240();
        Saab95 second = new Saab95();
        first.x = t.x; first.y = t.y;
        second.x = t.x; second.y = t.y;

        t.load(first);
        t.load(second);

        // Först ut: second
        assertTrue(t.unload() instanceof Saab95);

        // Nästa: first
        AbstractCar out = t.unload();
        assertTrue(out instanceof Volvo240);

        // Defaultriktning NORTH → y - 1
        assertEquals(t.getX(), out.getX(), 1e-9);
        assertEquals(t.getY() - 1, out.getY(), 1e-9);
    }

    @Test
    void LoadedCarsFollowTransport() {
        CarTransport t = new CarTransport();
        t.lowerRamp();

        Volvo240 v = new Volvo240();
        v.x = t.x; v.y = t.y;
        t.load(v);

        t.raiseRamp();
        t.startEngine();
        t.gas(1);
        t.move();

        // Lastad bil ska ha samma position
        assertEquals(t.getX(), v.getX(), 1e-9);
        assertEquals(t.getY(), v.getY(), 1e-9);
    }
}