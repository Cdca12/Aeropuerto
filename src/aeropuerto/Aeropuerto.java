package aeropuerto;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.Rutinas;

/**
 *
 * @author Carlos Contreras
 */
public class Aeropuerto extends JFrame {

    private JPanel glassPaneAviones;
    private JLabel lbBackground;
    private final String rutaBackground;
    
    
    
    public Aeropuerto() {
        rutaBackground = "./src/assets/aeropuerto.jpg";
        initComponents();
        setVisible(true);
    }

    public void initComponents() {
        setTitle("Aeropuerto");
        setSize(650, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        // GlassPane utilizado para los aviones
        glassPaneAviones = (JPanel) getGlassPane();
        glassPaneAviones.setLayout(null);
        glassPaneAviones.setVisible(true);
        
        // Añadir imagen de fondo
        lbBackground = new JLabel(Rutinas.AjustarImagen(rutaBackground, getContentPane().getWidth(), getContentPane().getHeight()));
        add(lbBackground);
        
        
        
        
        
    }
    
    
}
