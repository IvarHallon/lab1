import java.awt.*;

public class Scania extends AbstractCar {
    public Scania () {
        super(2, 450, Color.BLUE, "Scania");// constructor
        angle = 0;
    }
    private int angle; //current positon på flanken
    public void flank (int v) { //ändringen i flaket
        if (this.getCurrentSpeed() > 0.01) throw new
                IllegalArgumentException("Lastbilen rör på sig!!!");
        if ( angle + v < 0 || angle + v > 70) throw new
                IllegalArgumentException("vinkeln ska var v > 0 men v < 70 grader");
        angle = (angle + v); // den uppdaterade vinkeln
    }
    @Override //ersätter (implementerar) den abstrakta metoden speedFactor() som finns i AbstractCar
    protected double speedFactor() { // method
        return getEnginePower() * 0.01;
    }
    public int getangle () { //method
        return angle;
    }
    @Override // får inte gasa när flanket är raised
    public void gas(double amount) {
        if (angle != 0) throw new IllegalArgumentException( "Flaket är höjt!!");
        super.gas(amount); // den fortsätter gave om kvaret inte uppfylls!
    }
//Uppgift 1: Extensibility : KLAR!!!!!
//method: flank som kan höjas och sänkas
// rör sig i y axeln y + och y- eller kan man ha en enum upp och ner
// läge: flaken som kan endast få upp och ner
//method: logik för att räkna ut vinkeln
/* villkor för vinkeln :
-> vinkeln på flaken begränsas, kan endast vara mellan 0 eller höögre än 70
grader
-> vinkeln ändra endast när lastbilen är stilla, då v > 0
-> skicka en illegal expception för om bilen rör sig och v > 0
->
*/
// Uppgift 2: Mer Extensibilty
}