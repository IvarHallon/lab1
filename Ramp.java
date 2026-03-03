public class Ramp {
    private boolean down = false;

    public boolean isDown() { return down; }

    // Fäll ner – endast när transporten står helt still.
    public void lower(double currentSpeed) {
        if (currentSpeed != 0.0)
            throw new IllegalStateException("Kan inte fälla ner rampen när transporten rör sig.");
        down = true;
    }

    // Fäll upp – inget fartkrav.
    public void raise() {
        down = false;
    }
}