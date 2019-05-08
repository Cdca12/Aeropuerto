package aeropuerto;

import javax.swing.JFrame;

/**
 *
 * @author Carlos Contreras
 */
public class Aeropuerto extends JFrame {

    public Aeropuerto() {
        initComponents();
        setVisible(true);
    }

    public void initComponents() {
        setTitle("Aeropuerto");
        setSize(650, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    
}
