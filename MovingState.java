//Rörlig bil – allt fungerar normalt.
public class MovingState implements CarState {
    private final AbstractCar car;

    public MovingState(AbstractCar car) {
        this.car = car;
    }

    @Override public void move()         { car.coreMove(); }
    @Override public void gas(double a)  { car.coreGas(a); }
    @Override public void startEngine()  { car.coreStartEngine(); }
    @Override public void stopEngine()   { car.coreStopEngine(); }

}