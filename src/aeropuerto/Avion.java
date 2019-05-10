package aeropuerto;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utils.Rutinas;

/**
 *
 * @author Carlos Contreras
 */
public class Avion extends Thread {

    private final String rutaImagen;
    private Pista pista;
    public JLabel imagenAvion;
    private int velocidad;

    public Avion(Pista pista) {
        this.pista = pista;
        rutaImagen = "./src/assets/ship.png";
        imagenAvion = new JLabel(Rutinas.AjustarImagen(rutaImagen, 100, 80));
        imagenAvion.setBounds(10, 5, 100, 80);
        velocidad = 3;

    }

    @Override
    public void run() {
        while (true) {
            avanza();
            retrocede();
            if (pista.estaDisponible) {
                pista.semaforo.Espera();
                pista.estaDisponible = false;
                aterriza();
                pista.estaDisponible = true;
                pista.semaforo.Libera();
                esperar(100);
                
            }
        }

    }

    private void avanza() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen, 100, 80));
        while (imagenAvion.getX() <= 730) {
            imagenAvion.setBounds(imagenAvion.getX() + velocidad, imagenAvion.getY(), imagenAvion.getWidth(), imagenAvion.getHeight());
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
        }
    }

    private void retrocede() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen.substring(0, rutaImagen.length() - 4) + "-rotated.png", 100, 80));
        while (imagenAvion.getX() >= 20) {
            imagenAvion.setBounds(imagenAvion.getX() - velocidad, imagenAvion.getY(), imagenAvion.getWidth(), imagenAvion.getHeight());
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
        }
    }

    private void aterriza() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen.substring(0, rutaImagen.length() - 4) + "-landing.png", 100, 80));
        while (imagenAvion.getY() <= 350) {
            imagenAvion.setBounds(imagenAvion.getX(), imagenAvion.getY() + 2, 85, 80);
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
        }
    }

    private void esperar(int milisegundos) {
        try {
            sleep(milisegundos);
        } catch (InterruptedException ex) {
            Logger.getLogger(Avion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
