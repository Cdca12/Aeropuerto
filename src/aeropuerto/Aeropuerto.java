package aeropuerto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
    private int contadorAterrizajesSinExito;
    private Integer turnoActual;

    public Aeropuerto() {
        rutaBackground = "./src/assets/hangar6.jpg";
        numeroAviones = Rutinas.nextInt(3, 5);
        contadorAterrizajesSinExito = 0;
        turnoActual = 1;
        pista = new Pista();
        initComponents();
    }

    public void initComponents() {
        setTitle("Aeropuerto");
        setSize(1200, 700);
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
        iniciarAviones();

        // Esperar a que los hilos terminen su ejecución para mostrar resultados
        boolean banderaHilosVivos = true;
        while (banderaHilosVivos) {
            banderaHilosVivos = false;
            for (int i = 0; i < aviones.length; i++) {
                if (aviones[i].isAlive()) {
                    banderaHilosVivos = true;
                    break;
                }
            }
        }

        // Mostrar resultados
        imprimirResultados();

    }

    private void generarAviones() {
        aviones = new Avion[numeroAviones];
        int posicionVueloX = 10, posicionVueloY = 2, posicionEstacionadoX = getWidth() - 590, posicionEstacionadoY = getHeight() - 200;
        for (int i = 0; i < aviones.length; i++) {
            aviones[i] = new Avion((i + 1), pista, posicionVueloX, posicionVueloY, posicionEstacionadoX, posicionEstacionadoY, this);
            glassPaneAviones.add(aviones[i].imagenAvion);
            posicionVueloY += 65;
            posicionEstacionadoX -= 30;
            posicionEstacionadoY -= 45;
        }
    }

    public void iniciarAviones() {
        // Inicialización de hilos
        for (int i = 0; i < aviones.length; i++) {
            aviones[i].start();
        }
    }

    private void imprimirResultados() {
        JDialog modalResultados = new JDialog();
//        modalResultados.setLayout(null);
        modalResultados.setTitle("Resulados");
        modalResultados.setSize(400, 400);
        modalResultados.setLocationRelativeTo(null);
        modalResultados.setVisible(true);
        
        // Labels
        JPanel glassPaneLabel = new JPanel();
        glassPaneLabel = (JPanel) modalResultados.getGlassPane();
        glassPaneLabel.setLayout(null);
        glassPaneLabel.setVisible(true);
        
        JLabel lbModalBackground = new JLabel(Rutinas.AjustarImagen("./src/assets/resultados.jpg", modalResultados.getWidth(), modalResultados.getHeight()));
        modalResultados.add(lbModalBackground);

        JLabel lbResultados = new JLabel("Aterrizajes Fallidos");
        lbResultados.setBounds(80, 50, 300, 30);
        lbResultados.setForeground(Color.WHITE);
        lbResultados.setFont(new Font("Arial", Font.BOLD, 25));
        glassPaneLabel.add(lbResultados);

        int posicionY = 150;
        for (int i = 0; i < aviones.length; i++) {
            JLabel lbAvion = new JLabel("Avión " + (i + 1) + ":    " + aviones[i].obtenerAterrizajesFallidos());
            lbAvion.setBounds(140, posicionY + (i * 25), 200, 30);
            lbAvion.setForeground(Color.WHITE);
            lbAvion.setFont(new Font("Arial", Font.PLAIN, 16));
            glassPaneLabel.add(lbAvion);
        }

        SwingUtilities.updateComponentTreeUI(glassPaneLabel);
    }
}
