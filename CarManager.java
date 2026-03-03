import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarManager {
    private final List<AbstractCar> cars = new ArrayList<>();

    public void addCar(AbstractCar car) { cars.add(car); }
    public void removeCar(AbstractCar car) { cars.remove(car); }

    public boolean removeLast() {  // Hjälpmetod för policy “ta bort sist tillagd” (används i Uppgift 5)
        if (cars.isEmpty()) return false;
        cars.remove(cars.size() - 1);
        return true;
    }

    public List<AbstractCar> getCars() { return Collections.unmodifiableList(cars); }
    public int size() { return cars.size(); }

    // Gruppoperationer
    public void gasAll(double amount)   { for (var c : cars) c.gas(amount); }
    public void brakeAll(double amount) { for (var c : cars) c.brake(amount); }
    public void startAll()              { for (var c : cars) c.startEngine(); }
    public void stopAll()               { for (var c : cars) c.stopEngine(); }
}
