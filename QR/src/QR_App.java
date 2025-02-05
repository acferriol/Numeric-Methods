import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class QR_App {

    public static void main(String[] args) {
        try{
        UIManager.setLookAndFeel(new FlatMacLightLaf() );
        }catch(UnsupportedLookAndFeelException _){

        }
        Principal_Frame pf = new Principal_Frame();
        pf.setDefaultCloseOperation(Principal_Frame.EXIT_ON_CLOSE);
        pf.setVisible(true);
    }
}