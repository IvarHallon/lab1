import java.util.List;

public class MovementSystem {
    public void update(List<AbstractCar> cars) {for (AbstractCar car : cars) {car.move();
        }
    }
}
