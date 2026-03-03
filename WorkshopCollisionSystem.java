import java.util.ArrayList;
import java.util.List;

// Workshop-kollision för Workshop<Volvo240>.
public class WorkshopCollisionSystem {

    private final int workshopX;
    private final int workshopY;
    private final double radius;
    private final Workshop<Volvo240> workshop;

    public WorkshopCollisionSystem(int workshopX, int workshopY, double radius, Workshop<Volvo240> workshop) {
        this.workshopX = workshopX;
        this.workshopY = workshopY;
        this.radius = radius;
        this.workshop = workshop;
    }

    public void check(CarManager manager) {
        // Skapa en kopia av listan för iteration
        List<AbstractCar> carsSnapshot = new ArrayList<>(manager.getCars());
        List<AbstractCar> toRemove = new ArrayList<>();

        for (AbstractCar car : carsSnapshot) {
            if (car instanceof Volvo240 v) {
                double dx = v.getX() - workshopX;
                double dy = v.getY() - workshopY;
                double dist = Math.hypot(dx, dy);
                if (dist < radius) {
                    v.stopEngine();
                    v.store(); // bilen är nu lagrad i verkstadens regi
                    workshop.addCar(v);
                    toRemove.add(v); // markera för borttagning
                }
            }
        }
        // Ta bort via CarManager
        for (AbstractCar car : toRemove) {manager.removeCar(car);
        }
    }
}