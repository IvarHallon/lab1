
import java.awt.Color;

/**
 * Volvo240 – specificerar egen speedFactor via trim-factor.
 */
public class Volvo240 extends AbstractCar {

    private static final double TRIM_FACTOR = 1.25;

    public Volvo240() {
        super(4, 100, Color.black , "Volvo240");
    }

    @Override //ersätter (implementerar) den abstrakta metoden speedFactor() som finns i AbstractCar
    protected double speedFactor() {
        return getEnginePower() * 0.01 * TRIM_FACTOR;
    }
}
