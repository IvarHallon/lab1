public final class CarFactory {
    private CarFactory() {}

    public static AbstractCar createVolvo(int x, int y) {
        Volvo240 v = new Volvo240();
        v.x = x; v.y = y;
        return v;
    }

    public static AbstractCar createSaab(int x, int y) {
        Saab95 s = new Saab95();
        s.x = x; s.y = y;
        return s;
    }

    public static AbstractCar createScania(int x, int y) {
        Scania sc = new Scania();
        sc.x = x; sc.y = y;
        return sc;
    }

    // Slumpa en biltyp och placera på (x,y).
    public static AbstractCar createRandom(int x, int y) {
        int r = (int)(Math.random() * 3);
        return switch (r) {
            case 0 -> createVolvo(x, y);
            case 1 -> createSaab(x, y);
            default -> createScania(x, y);
        };
    }
}
