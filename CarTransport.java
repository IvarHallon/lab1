import java.awt.Color;

public class CarTransport extends AbstractCar {

    private final Ramp ramp = new Ramp();
    private final CargoBay cargo = new CargoBay();

    public CarTransport() {
        super(2, 600, Color.white, "CarTransport");
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }

    // Ramp
    public boolean isRampDown() { return ramp.isDown(); }

    public void lowerRamp() { ramp.lower(getCurrentSpeed()); }

    public void raiseRamp() { ramp.raise(); }

    // Cargo-API
    public int getLoadCount() { return cargo.getLoadCount(); }

    public int getMaxCapacity() { return cargo.getMaxCapacity(); }

    public void load(AbstractCar car) {
        if (car == this) throw new IllegalArgumentException("Kan inte lasta sig själv.");
        cargo.load(car, ramp.isDown(), getCurrentSpeed(), this.x, this.y);
        // synka direkt efter lastning
        cargo.syncWithTransport(this.x, this.y, this.direction);
    }

    public AbstractCar unload() {
        return cargo.unload(ramp.isDown(), getCurrentSpeed(), this.x, this.y, this.direction);
    }

    // Körlogik – rampregler
    @Override
    public void startEngine() {
        if (ramp.isDown())
            throw new IllegalStateException("Rampen måste vara uppe för att köra.");
        super.startEngine();
    }

    @Override
    public void gas(double amount) {
        if (ramp.isDown())
            throw new IllegalStateException("Rampen måste vara uppe för att köra.");
        super.gas(amount);
    }

    @Override
    public void move() {
        super.move();
        cargo.syncWithTransport(this.x, this.y, this.direction);
    }
}