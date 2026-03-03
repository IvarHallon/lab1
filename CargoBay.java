import java.util.ArrayDeque;
import java.util.Deque;

public class CargoBay {
    private static final int MAX_CAPACITY = 6;
    private static final double LOAD_RADIUS = 2.0;

    private final Deque<AbstractCar> cargo = new ArrayDeque<>();

    public int getLoadCount() { return cargo.size(); }
    public int getMaxCapacity() { return MAX_CAPACITY; }

    // Lasta bil med kompletta regler (ramp nere, stillastående, avstånd, typ, kapacitet).

    public void load(AbstractCar car,
                     boolean rampDown, double currentSpeed,
                     double tx, double ty) {

        if (!rampDown)
            throw new IllegalStateException("Rampen måste vara nere för att lasta.");

        if (currentSpeed != 0.0)
            throw new IllegalStateException("Transporten måste stå still för att lasta.");

        if (car == null)
            throw new IllegalArgumentException("Bil kan inte vara null.");

        if (car instanceof CarTransport)
            throw new IllegalArgumentException("Kan inte lasta en annan biltransport.");

        if (cargo.size() >= MAX_CAPACITY)
            throw new IllegalStateException("Transporten är full (" + MAX_CAPACITY + " bilar).");

        double dx = car.getX() - tx;
        double dy = car.getY() - ty;
        double dist = Math.hypot(dx, dy);
        if (dist > LOAD_RADIUS)
            throw new IllegalStateException("Bilen är för långt bort för att lastas.");

        car.stopEngine(); // säkerhet
        car.store(); // Markera som stored vid lastning
        cargo.push(car);  // FILO
        // Pos/dir synkas i syncWithTransport() som anropas från CarTransport.move()
    }

    //Lossa senaste lastade bilen. Placera 1 enhet "bakom" transporten (beroende på direction).
    public AbstractCar unload(boolean rampDown, double currentSpeed,
                              double tx, double ty, Direction dir) {

        if (!rampDown)
            throw new IllegalStateException("Rampen måste vara nere för att lossa.");

        if (currentSpeed != 0.0)
            throw new IllegalStateException("Transporten måste stå still för att lossa.");

        if (cargo.isEmpty())
            throw new IllegalStateException("Det finns inga bilar att lossa.");

        AbstractCar car = cargo.pop();
        switch (dir) {
            case NORTH -> { car.x = tx;     car.y = ty - 1; }
            case SOUTH -> { car.x = tx;     car.y = ty + 1; }
            case EAST  -> { car.x = tx - 1; car.y = ty;     }
            case WEST  -> { car.x = tx + 1; car.y = ty;     }
        }
        car.stopEngine();
        car.unstore(); // När bilen lossas får den åter bli rörlig
        return car;
    }

   //Synka alla lastade bilar med transportens position/riktning.
    public void syncWithTransport(double tx, double ty, Direction dir) {
        for (AbstractCar car : cargo) {
            car.x = tx;
            car.y = ty;
            car.direction = dir;
            car.currentSpeed = 0.0;
        }
    }
}
