import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CarController implements ICarController {
    private static final int MAX_CARS = 10;

    private final int delay = 50;
    private final Timer timer = new Timer(delay, new TimerListener());

    private final CarManager manager = new CarManager();
    private final MovementSystem movementSystem = new MovementSystem();
    private final CollisionSystem collisionSystem = new CollisionSystem();

    //Workshop för ENDAST Volvo
    private final Workshop<Volvo240> volvo240Workshop = new Workshop<>(3);
    private final int workshopX = 300;
    private final int workshopY = 300;
    private final CollisionSystem collisionsystem =
            new CollisionSystem(workshopX, workshopY, volvo240Workshop, manager);
    private final MovementSystem movementsystem = new MovementSystem();
    private final WorkshopCollisionSystem workshopCollisionSystem =
            new WorkshopCollisionSystem(workshopX, workshopY, 80.0, volvo240Workshop);


    CarView frame;

    public static void main(String[] args) {
        CarController cc = new CarController();

        // Startbilar
        cc.manager.addCar(CarFactory.createVolvo(0, 300));
        cc.manager.addCar(CarFactory.createSaab (0, 100));
        cc.manager.addCar(CarFactory.createScania(0, 200));

        cc.frame = new CarView("CarSim 1.0", cc);
        cc.timer.start();
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            movementSystem.update(manager.getCars());

            int panelW = frame.drawPanel.getWidth();
            // int panelH = frame.drawPanel.getHeight();
            collisionSystem.check(manager.getCars(), panelW, 100);
            workshopCollisionSystem.check(manager);

            buildSnapshotAndRepaint();
        }
    }

    private void buildSnapshotAndRepaint() {
        var snapshot = new ArrayList<CarDTO>(manager.size());
        for (AbstractCar c : manager.getCars()) snapshot.add(CarDTO.from(c));
        frame.drawPanel.setSnapshot(snapshot);
        frame.drawPanel.repaint();
    }

    // ICarController
    @Override public void gas(int amount)   { manager.gasAll(amount / 100.0); }
    @Override public void brake(int amount) { manager.brakeAll(amount / 100.0); }
    @Override public void startAllCars()    { manager.startAll(); }
    @Override public void stopAllCars()     { manager.stopAll(); }

    @Override
    public void turboOn() {
        for (AbstractCar car : manager.getCars()) if (car instanceof Saab95 s) s.setTurboOn();
    }
    @Override
    public void turboOff() {
        for (AbstractCar car : manager.getCars()) if (car instanceof Saab95 s) s.setTurboOff();
    }
    @Override
    public void liftBed() {
        for (AbstractCar car : manager.getCars()) if (car instanceof Scania s) s.flank(10);
    }
    @Override
    public void lowerBed() {
        for (AbstractCar car : manager.getCars()) if (car instanceof Scania s) s.flank(-10);
    }

    // Uppgift 5: Add / Remove
    @Override
    public void addCarRandom() {
        if (manager.size() >= MAX_CARS) return; // ingen effekt om fullt

        // Placering: x=0, y i 100-steg. Om panelen inte init: default 600 px.
        int panelH = (frame != null) ? frame.drawPanel.getHeight() : 600;
        int slots = Math.max(1, panelH / 100);
        int idx   = manager.size();
        int y     = Math.max(100, (idx % slots) * 100);
        int x     = 0;

        AbstractCar newCar = CarFactory.createRandom(x, y);
        manager.addCar(newCar);
        buildSnapshotAndRepaint();
    }

    @Override
    public void removeCar() {
        if (manager.size() == 0) return; // ingen effekt om tomt
        manager.removeLast();            // policy: ta bort sist tillagd
        buildSnapshotAndRepaint();
    }
}
