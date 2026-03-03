public class CarDTO {
    public final String modelName;
    public final int x;
    public final int y;

    public CarDTO(String modelName, int x, int y) {
        this.modelName = modelName;
        this.x = x;
        this.y = y;
    }

    public static CarDTO from(AbstractCar car) {
        return new CarDTO(car.getModelName(), (int)car.getX(), (int)car.getY());
    }
}