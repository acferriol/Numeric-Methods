import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Principal_Frame extends JFrame {

    public Principal_Frame() {
        try {
            BufferedImage ico = ImageIO.read(new File("resources\\QR_ICO.jpg"));
            setIconImage(ico);
        } catch (IOException _) {}
        setBounds(40, 0, 1380, 870);
        setResizable(true);
        add(new Principal_Panel());
    }

}