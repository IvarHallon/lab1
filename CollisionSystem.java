import java.util.List;

//Lite oklara delar i denna klass. Vi försökte få volvo att åka till
// verkstaden om den körde in i en vägg.


public class CollisionSystem {

    // (Aktivera workshop-teleport för Volvo via denna ctor, annars använd default)
    private final boolean workshopEnabled;
    private final int workshopX;
    private final int workshopY;
    private final Workshop<Volvo240> workshop;
    private final CarManager manager;

    // Default: ingen workshop‑teleport (vanlig studs på båda väggar)
    public CollisionSystem() {
        this.workshopEnabled = false;
        this.workshopX = 0;
        this.workshopY = 0;
        this.workshop = null;
        this.manager = null;
    }

    // Workshop-variant: möjliggör att skicka Volvo till verkstad
    public CollisionSystem(int workshopX, int workshopY,
                           Workshop<Volvo240> workshop, CarManager manager) {
        this.workshopEnabled = true;
        this.workshopX = workshopX;
        this.workshopY = workshopY;
        this.workshop = workshop;
        this.manager = manager;
    }

    public void check(List<AbstractCar> cars, int panelWidth, int spriteSize) {
        AbstractCar volvoToRemove = null; // markera för borttag om vi skickar in i workshop

        for (AbstractCar car : cars) {
            boolean hitLeft  = false;
            boolean hitRight = false;

            // Vänster vägg
            if (car.getX() < 0) {
                car.x = 0; // anti-hack position, hoppade bara på väggen innan
                hitLeft = true;
            }

            // Höger vägg
            if (car.getX() > panelWidth - spriteSize) {
                car.x = panelWidth - spriteSize; // anti-hack position
                hitRight = true;
            }

            // Ingen vägg träffad
            if (!hitLeft && !hitRight) continue;

            // Volvo + (vänster ELLER höger) vägg → in i verkstad:
            if (workshopEnabled && car instanceof Volvo240 v) {
                // stoppa + markera som lagrad
                v.stopEngine();
                v.store();

                // lägg Volvo på verkstadens koordinat i View
                v.x = workshopX;
                v.y = workshopY;

                // in i workshop + flagga för borttag ur simuleringen
                workshop.addCar(v);
                volvoToRemove = v;

                // hoppa över studs, ska inte vända bilen tillbaka
                continue;
            }

            // Standardbeteende för övriga biltyper: studs tillbaka (180°) ===
            car.stopEngine();
            car.turnLeft();  // 90°
            car.turnLeft();  // 180°
            car.startEngine();
        }

        // Ta bort Volvo som skickats till workshop från simuleringen
        if (workshopEnabled && volvoToRemove != null && manager != null) {
            manager.removeCar(volvoToRemove);
        }
    }
}