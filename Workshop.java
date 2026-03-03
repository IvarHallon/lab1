import java.util.ArrayList;
import java.util.List;

public class Workshop<T extends AbstractCar> {

    private final int capacity;
    private final List<T> cars;

    public Workshop(int capacity) {
        this.capacity = capacity;
        this.cars = new ArrayList<>();
    }

    // Lämna in bil
    public void addCar(T car) {
        if (cars.size() >= capacity) {
            throw new IllegalStateException("Workshop is full");
        }
        cars.add(car);
    }

    // Hämta ut bil
    public void removeCar(T car) {
        if (cars.isEmpty()) {
            throw new IllegalStateException("Workshop is empty");
        }
        cars.remove(car);
    }

    public int getNumberOfCars() {
        return cars.size();
    }

    public boolean contains(T volvo) {

        return false;
    }


    //Workshop<Volvo240> volvoWorkshop = new Workshop<>(3);


}