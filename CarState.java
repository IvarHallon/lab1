// State-interface för bilens rörelsetillstånd.
// Context = AbstractCar. States anropar metoder på bilen för den faktiska logiken.
public interface CarState {
    void move();
    void gas(double amount);
    void startEngine();
    void stopEngine();

    // I båda states tillåter vi brake (säker åtgärd)
    default void brake(AbstractCar car, double amount) {
        car.coreBrake(amount);
    }

    // Hjälp för statebyte (kan kallas från states)
    default void store(AbstractCar car) {
        car.setState(new StoredState(car));
    }

    default void unstore(AbstractCar car) {
        car.setState(new MovingState(car));
    }
}