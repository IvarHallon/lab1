
import java.awt.Color;

/**
 * Abstrakt basklass för gemensam bilfunktionalitet.
 * implementerar Movable och sanity checks för gas/brake.
 */
public abstract class AbstractCar implements Movable {

    protected final int nrDoors;
    protected final double enginePower;
    protected double currentSpeed;
    protected Color color;
    protected final String modelName;

    // Position & riktning
    protected double x;
    protected double y;
    protected Direction direction = Direction.NORTH;


    /**
     * Konstruktor för alla bilar som ärver från AbstractCar.
     * Här sätts alla grundvärden och vi gör validering så att objektet aldrig skapas i "ogiltigt läge".
     */
    protected AbstractCar(int nrDoors, double enginePower, Color color, String modelName) {
        if (nrDoors <= 0) throw new IllegalArgumentException("nrDoors måste vara > 0");
        if (enginePower <= 0) throw new IllegalArgumentException("enginePower måste vara > 0");
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        stopEngine(); // currentSpeed = 0
    }

    // (getters)
    public int getNrDoors() {
        return nrDoors;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public String getModelName() {
        return modelName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color clr) {
        this.color = clr;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    // Motor
    public void startEngine() {
        currentSpeed = 0.1;
    }

    public void stopEngine() {
        currentSpeed = 0.0;
    }

    // Rörelse (Movable)
    /**
     * move() kommer från Movable och överskrivs här.
     * Den uppdaterar bilens position beroende på riktning och currentSpeed.
     */
    @Override
    public void move() {
        switch (direction) {
            case NORTH -> y += currentSpeed;
            case SOUTH -> y -= currentSpeed;
            case EAST -> x += currentSpeed;
            case WEST -> x -= currentSpeed;
        }
    }

    @Override
    public void turnLeft() {
        direction = direction.left();
    }

    @Override
    public void turnRight() {
        direction = direction.right();
    }

    // Farthantering
    protected abstract double speedFactor();

    protected void incrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() + speedFactor() * amount);
    }

    protected void decrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() - speedFactor() * amount);
    }

    private void setCurrentSpeed(double speed) {
        // currentSpeed alltid i [0, enginePower]
        this.currentSpeed = clamp(0.0, enginePower, speed);
    }

    private static double clamp(double min, double max, double value) {
        return Math.min(max, Math.max(min, value));
    }

    private static void validateAmount(double amount) {
        if (amount < 0.0 || amount > 1.0) {
            throw new IllegalArgumentException("amount måste vara i intervallet [0,1]");
        }
    }


    public void gas(double amount) {
        validateAmount(amount);
        incrementSpeed(amount);
    }


    public void brake(double amount){
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("amount måste vara mellan 0 och 1");
        }
        decrementSpeed(amount);
    }


}




