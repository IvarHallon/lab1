import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawPanel extends JPanel {

    private List<CarDTO> snapshot = Collections.emptyList();

    private BufferedImage volvoImage;
    private BufferedImage saabImage;
    private BufferedImage scaniaImage;
    private BufferedImage workshopImage;

    private final Point workshopPoint = new Point(300, 300);

    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);
        try {
            volvoImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg"));
            saabImage  = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg"));
            scaniaImage= ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg"));
            workshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Anropas av controller varje tick, pushar in en read-only bild av modellens state.
    public void setSnapshot(List<CarDTO> snapshot) {
        this.snapshot = (snapshot == null) ? Collections.emptyList() : snapshot;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (snapshot != null) {
            for (CarDTO car : snapshot) {
                BufferedImage image = switch (car.modelName) {
                    case "Volvo240" -> volvoImage;
                    case "Saab95"   -> saabImage;
                    case "Scania"   -> scaniaImage;
                    default -> null;
                };
                if (image != null) {
                    g.drawImage(image, car.x, car.y, null);
                }
            }
        }
        // Workshop
        g.drawImage(workshopImage, workshopPoint.x, workshopPoint.y, null);
    }
}