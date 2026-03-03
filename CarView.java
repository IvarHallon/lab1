import javax.swing.*;
import java.awt.*;

public class CarView extends JFrame {
    private static final int X = 800;
    private static final int Y = 800;

    private final ICarController controller;

    DrawPanel drawPanel = new DrawPanel(X, Y - 240);

    JPanel controlPanel = new JPanel();
    JPanel gasPanel = new JPanel();

    JSpinner gasSpinner;
    int gasAmount = 0;

    JLabel gasLabel = new JLabel("Amount of gas");
    JButton gasButton = new JButton("Gas");
    JButton brakeButton = new JButton("Brake");
    JButton turboOnButton = new JButton("Saab Turbo on");
    JButton turboOffButton = new JButton("Saab Turbo off");
    JButton liftBedButton = new JButton("Scania Lift Bed");
    JButton lowerBedButton = new JButton("Lower Lift Bed");
    JButton startButton = new JButton("Start all cars");
    JButton stopButton = new JButton("Stop all cars");

    // Uppgift 5
    JButton addCarButton = new JButton("Add car");
    JButton removeCarButton = new JButton("Remove car");

    public CarView(String framename, ICarController controller) {
        this.controller = controller;
        initComponents(framename);
    }

    private void initComponents(String title) {
        this.setTitle(title);
        this.setPreferredSize(new Dimension(X, Y));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.add(drawPanel);

        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        gasSpinner = new JSpinner(spinnerModel);
        gasSpinner.addChangeListener(e -> gasAmount = (int) gasSpinner.getValue());

        gasPanel.setLayout(new BorderLayout());
        gasPanel.add(gasLabel, BorderLayout.PAGE_START);
        gasPanel.add(gasSpinner, BorderLayout.PAGE_END);
        this.add(gasPanel);

        controlPanel.setLayout(new GridLayout(2, 6));
        controlPanel.setPreferredSize(new Dimension((X / 2) + 4, 200));
        controlPanel.setBackground(Color.CYAN);

        controlPanel.add(gasButton);
        controlPanel.add(turboOnButton);
        controlPanel.add(liftBedButton);
        controlPanel.add(brakeButton);
        controlPanel.add(turboOffButton);
        controlPanel.add(lowerBedButton);
        controlPanel.add(addCarButton);
        controlPanel.add(removeCarButton);

        this.add(controlPanel);

        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.green);
        startButton.setPreferredSize(new Dimension(X / 5 - 15, 200));
        this.add(startButton);

        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension(X / 5 - 15, 200));
        this.add(stopButton);

        // LISTENERS
        gasButton.addActionListener(e -> controller.gas(gasAmount));
        brakeButton.addActionListener(e -> controller.brake(gasAmount));
        startButton.addActionListener(e -> controller.startAllCars());
        stopButton.addActionListener(e -> controller.stopAllCars());
        turboOnButton.addActionListener(e -> controller.turboOn());
        turboOffButton.addActionListener(e -> controller.turboOff());
        liftBedButton.addActionListener(e -> controller.liftBed());
        lowerBedButton.addActionListener(e -> controller.lowerBed());
        addCarButton.addActionListener(e -> controller.addCarRandom());
        removeCarButton.addActionListener(e -> controller.removeCar());


        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}