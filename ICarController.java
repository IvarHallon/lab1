public interface ICarController {
    void gas(int amount);
    void brake(int amount);
    void startAllCars();
    void stopAllCars();
    void turboOn();
    void turboOff();
    void liftBed();
    void lowerBed();

    // (Uppgift 5
    void addCarRandom();
    void removeCar();
}