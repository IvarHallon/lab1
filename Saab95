
import java.awt.Color;

/**
 * Saab95 – har turbo som påverkar speedFactor.
 */
public class Saab95 extends AbstractCar {

    private boolean turboOn = false; //Håller koll om turbon är på eller av

    public Saab95() { //Anropar Abstractcars konstruction
        super(2, 125, Color.red , "Saab95");
    }

    public void setTurboOn() {
        turboOn = true;
    }

    public void setTurboOff() {
        turboOn = false;
    }

    @Override //ersätter den abstrakta metoden speedFactor() som finns i AbstractCar
    protected double speedFactor() {
        double turbo = turboOn ? 1.3 : 1.0; //Välj turbo på/av
        return getEnginePower() * 0.01 * turbo;
    }
}
