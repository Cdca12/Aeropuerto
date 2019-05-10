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
    private Avion[] aviones;
    private int numeroAviones;
    private Pista pista;
    
    
    
    public Aeropuerto() {
        rutaBackground = "./src/assets/hangar6.jpg";
//        numeroAviones = Rutinas.nextInt(3, 5);
        numeroAviones = 1; // TEST:
        pista = new Pista();
        initComponents();
    }

    public void initComponents() {
        setTitle("Aeropuerto");
        setSize(850, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        // GlassPane utilizado para los aviones
        glassPaneAviones = (JPanel) getGlassPane();
        glassPaneAviones.setLayout(null);
        glassPaneAviones.setVisible(true);
        
        // Añadir imagen de fondo
        lbBackground = new JLabel(Rutinas.AjustarImagen(rutaBackground, getContentPane().getWidth(), getContentPane().getHeight()));
        add(lbBackground);
        
        generarAviones();
        
        
        
        
    }
    
    private void generarAviones() {
        aviones = new Avion[numeroAviones];
        for (int i = 0; i < aviones.length; i++) {
            aviones[i] = new Avion(pista);
            glassPaneAviones.add(aviones[i].imagenAvion);
        }
        // Inicialización de hilos
        for (int i = 0; i < aviones.length; i++) {
            aviones[i].start();
        }
        
        
    }
    
    
}
