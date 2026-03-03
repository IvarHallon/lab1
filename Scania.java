import java.awt.*;

public class Scania extends AbstractCar {
    public Scania () {
        super(2, 450, Color.BLUE, "Scania");
        angle = 0;
    }

    private int angle; // nuvarande vinkel på flaket (0..70)

    //Höjer/sänker flaket med v grader. Kastar exception vid regelbrott.

    public void flank (int v) {
        if (this.getCurrentSpeed() > 0.01)
            throw new IllegalArgumentException("Lastbilen rör på sig");
        if (angle + v < 0 || angle + v > 70)
            throw new IllegalArgumentException("vinkeln ska vara 0..70 grader");
        angle = angle + v;
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }


    public int getangle () {
        return angle;
    }

    @Override
    public void gas(double amount) {
        // Om flaket inte är helt nere = ignorera gas
        if (angle != 0) return;
        super.gas(amount);
    }



    //flaket helt nere (angle == 0)?
    public boolean isBedDown() {
        return angle == 0;
    }

    //Försök höja flaket 10°. Returnerar true om lyckat, annars false
    public boolean tryRaiseBed() {
        if (getCurrentSpeed() > 0.01) return false;  // måste stå still
        if (angle + 10 > 70) return false;           // max 70°
        angle += 10;
        return true;
    }

    // Försök sänka flaket 10°. Returnerar true om lyckat, annars false.
    public boolean tryLowerBed() {
        if (getCurrentSpeed() > 0.01) return false;  // måste stå still
        if (angle - 10 < 0) return false;            // min 0°
        angle -= 10;
        return true;
    }

    /**
     * Sänk flaket helt till 0° (om stillastående).
     * Får att vara säker på att bilen kan börja röra sig igen efter "Lower bed".
     */
    public boolean lowerBedFully() {
        if (getCurrentSpeed() > 0.01) return false;
        angle = 0;
        return true;
    }
}
