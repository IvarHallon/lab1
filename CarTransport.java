import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;


 // CarTransport – en lastbil som kan transportera bilar.

public class CarTransport extends AbstractCar {

    //Max antal bilar transporten kan ta. Vårt eget antagande enligt labben är 6st
    private static final int MAX_CAPACITY = 6;

    //Max avstånd för lastning/lossning
    private static final double LOAD_RADIUS = 2.0;

    // Rampens läge. true = nere, false = uppe
    private boolean rampDown = false;

    // Lastade bilar First-in -> last-out
    private final Deque<AbstractCar> cargo = new ArrayDeque<>();

    // Konstruktor
    /**
     *  - 2 dörrar
     *  - 300 hk
     *  - vit färg
     *  - modellnamn "CarTransport"
     */
    public CarTransport() {
        super(2, 600, Color.white, "CarTransport");
    }

    // Transportens acceleration
    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }

    // Ramp-logik
    //Är rampen nere?
    public boolean isRampDown() {
        return rampDown;
    }

    // Fäll ner rampen. (Får endast göras när transporten står helt stilla)
    public void lowerRamp() {
        if (getCurrentSpeed() != 0.0)
            throw new IllegalStateException("Kan inte fälla ner rampen när transporten rör sig.");

        rampDown = true;
    }

    // Fäll upp rampen (ingen fartregel krävs)
    public void raiseRamp() {
        rampDown = false;
    }


    // Lastning
    // Antal lastade bilar just nu
    public int getLoadCount() {
        return cargo.size();
    }

    //Maxkapacitet (konstant=6)
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }

    /**
     * REFLER VID LASTNING AV BIL:
     *  - rampen måste vara nere
     *  - transporten måste stå still
     *  - bilen måste vara nära
     *  - bilen får inte vara en annan transport
     *  - bilen får inte vara samma objekt som denna transport
     *  - får inte lasta fler än MAX_CAPACITY
     */
    public void load(AbstractCar car) {
        if (!rampDown)
            throw new IllegalStateException("Rampen måste vara nere för att lasta.");

        if (getCurrentSpeed() != 0.0)
            throw new IllegalStateException("Transporten måste stå still för att lasta.");

        if (car == this)
            throw new IllegalArgumentException("Kan inte lasta sig själv.");

        if (car instanceof CarTransport)
            throw new IllegalArgumentException("Kan inte lasta en annan biltransport.");

        if (cargo.size() >= MAX_CAPACITY)
            throw new IllegalStateException("Transporten är full (" + MAX_CAPACITY + " bilar).");

        // Avståndskrav
        double dx = car.getX() - this.getX();
        double dy = car.getY() - this.getY();
        double dist = Math.hypot(dx, dy); // Pythagoras: korrekt avstånd i 2D så bilen verkligen är nära.

        if (dist > LOAD_RADIUS)
            throw new IllegalStateException("Bilen är för långt bort för att lastas.");

        // Synka bilens position och riktning med transportens
        car.stopEngine(); // absolut säkerhetsregel, inte gasa av misstag på flaket
        car.x = this.x;
        car.y = this.y;
        car.direction = this.direction; // inte röra sig på flaket

        cargo.push(car); // FILO – senaste bilen överst
    }


    //Lossar bilen som lastades sist. Bilen placeras 1 enhet bakom transporten.

    public AbstractCar unload() {
        if (!rampDown)
            throw new IllegalStateException("Rampen måste vara nere för att lossa.");

        if (getCurrentSpeed() != 0.0)
            throw new IllegalStateException("Transporten måste stå still för att lossa.");

        if (cargo.isEmpty())
            throw new IllegalStateException("Det finns inga bilar att lossa.");

        AbstractCar car = cargo.pop(); // pop = ta senaste lastade

        // Placera bilen bakom transporten beroende på riktning
        switch (this.direction) {
            case NORTH -> { car.x = this.x;     car.y = this.y - 1; } //Bilen placeras ett steg nedåt
            case SOUTH -> { car.x = this.x;     car.y = this.y + 1; } //Bilen placeras ett steg uppåt
            case EAST  -> { car.x = this.x - 1; car.y = this.y;     } //Bilen placeras ett steg åt vänster
            case WEST  -> { car.x = this.x + 1; car.y = this.y;     } //Bilen placeras ett steg åt höger
        }

        car.stopEngine(); // lossad bil bör alltid stå still
        return car;
    }


    // Körlogik
    // Starta motor –> rampen måste vara uppe
    @Override
    public void startEngine() {
        if (rampDown)
            throw new IllegalStateException("Rampen måste vara uppe för att köra.");
        super.startEngine();
    }

    // Gas –> rampen måste vara uppe
    @Override
    public void gas(double amount) {
        if (rampDown)
            throw new IllegalStateException("Rampen måste vara uppe för att köra.");
        super.gas(amount);
    }

    //flytta transporten. Alla lastade bilar följer med exakt → deras position och riktning uppdateras.
    @Override
    public void move() {
        super.move(); // flyttar transporten

        for (AbstractCar car : cargo) {
            car.x = this.x;
            car.y = this.y;
            car.direction = this.direction;
            car.currentSpeed = 0.0; // lastade bilar ska aldrig röra sig själva
        }
    }
}