//Lagrad bil – får inte gasa eller förflyttas.
public class StoredState implements CarState {
    private final AbstractCar car;

    public StoredState(AbstractCar car) {
        this.car = car;
    }

    @Override public void move() {
        // Blockera rörelse
    }

    @Override public void gas(double amount) {
        throw new IllegalStateException("Lagrad bil kan ej accelerera.");
    }

    @Override public void startEngine() {
        // Tillåtet att starta motor utan att bilen får röra sig
        car.coreStartEngine();
    }

    @Override public void stopEngine() {
        car.coreStopEngine();
    }

    // brake() default
}
