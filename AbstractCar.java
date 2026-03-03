import java.awt.Color;

/**
 * Abstrakt basklass för gemensam bilfunktionalitet.
 * implementerar Movable och sanity checks för gas/brake.
 * + State:
 *  - Public-metoder (move, gas, startEngine, stopEngine) delegeras till ett CarState-objekt.
 *  - Den gamla logiken ligger kvar i core-metoder som State-klasserna använder.
 *  - Möjliga tillstånd (States): MovingState, StoredState.
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
    protected Direction direction = Direction.EAST;

    // State-ändring
    // Varje bil har ett tillstånd. Default: rörlig (MovingState).
    // State-objektet har tillgång till bilen och kan ändra dess beteende.
    private CarState state = new MovingState(this);

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
    public int getNrDoors() { return nrDoors; }
    public double getEnginePower() { return enginePower; }
    public double getCurrentSpeed() { return currentSpeed; }
    public String getModelName() { return modelName; }
    public Color getColor() { return color; }
    public void setColor(Color clr) { this.color = clr; }
    public double getX() { return x; }
    public double getY() { return y; }
    public Direction getDirection() { return direction; }

    // State-ändring
    // Exponeras för CargoBay / Workshop:
    public void store()   { this.state = new StoredState(this); }
    public void unstore() { this.state = new MovingState(this); }
    void setState(CarState newState) {this.state = newState; }



    // Delegerar till state
    @Override
    public void move() {
        // istället för att flytta direkt. State bestämmer om bilen får röra sig
        state.move();
    }

    @Override
    public void turnLeft()  { direction = direction.left(); }
    @Override
    public void turnRight() { direction = direction.right(); }

    public void startEngine() {
        // Om bilen är lagrad (StoredState) får den starta men inte flytta
        state.startEngine();
    }

    public void stopEngine() {
        state.stopEngine();
    }

    public void gas(double amount) {
        validateAmount(amount);
        // i StoredState sker ingen acceleration
        state.gas(amount);
    }

    public void brake(double amount) {
        validateAmount(amount);
        // State tillåter brake i båda states
        state.brake(this, amount);
    }

    // Dessa metoder motsvarar exakt hur bilen beter sig i rörelse, och anropas inifrån State-klasserna.
    void coreMove() {
        switch (direction) {
            case NORTH -> y += currentSpeed;
            case SOUTH -> y -= currentSpeed;
            case EAST -> x += currentSpeed;
            case WEST -> x -= currentSpeed;

        }
    }

    void coreStartEngine() {
        currentSpeed = 0.1;
    }

    void coreStopEngine() {
        currentSpeed = 0.0;
    }

    void coreGas(double amount) {
        incrementSpeed(amount);
    }

    void coreBrake(double amount) {
        decrementSpeed(amount);
    }

    //Farthantering
    protected abstract double speedFactor();


    protected void incrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() + speedFactor() * amount);
    }

    protected void decrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() - speedFactor() * amount);
    }

    private void setCurrentSpeed(double speed) {
        this.currentSpeed = clamp(0.0, enginePower, speed);
    }

    private static double clamp(double min, double max, double value) {
        return Math.min(max, Math.max(min, value));
    }

    private static void validateAmount(double amount) {
        if (amount < 0.0 || amount > 1.0)
            throw new IllegalArgumentException("amount måste vara i intervallet [0,1]");
    }
}