package aeropuerto;

import java.awt.Color;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utils.Rutinas;

/**
 *
 * @author Carlos Contreras
 */
public class Avion extends Thread {

    private Aeropuerto frameAeropuerto; // Cuestiones de visibilidad y ajustar dimensiones
    private int avionID;
    private final String rutaImagen;
    private Pista pista;
    public JLabel imagenAvion;
    private int velocidad, velocidadAterrizaje;
    private int posicionVueloX, posicionVueloY, posicionEstacionadoX, posicionEstacionadoY;
    private static int turnoActual;
    private int contadorAterrizajesFallidos;

    public Avion(int avionID, Pista pista, int posicionVueloX, int posicionVueloY, int posicionEstacionadoX, int posicionEstacionadoY, Aeropuerto frameAeropuerto) {
        this.avionID = avionID;
        this.pista = pista;
        this.posicionVueloX = posicionVueloX;
        this.posicionVueloY = posicionVueloY;
        this.posicionEstacionadoX = posicionEstacionadoX;
        this.posicionEstacionadoY = posicionEstacionadoY;
        this.turnoActual = 1;
        this.contadorAterrizajesFallidos = 0;
        this.frameAeropuerto = frameAeropuerto;
        rutaImagen = "./src/assets/ship.png";
        imagenAvion = new JLabel(Rutinas.AjustarImagen(rutaImagen, 100, 80));
        imagenAvion.setBounds(posicionVueloX, posicionVueloY, 100, 80);
        velocidad = 5; // TEST:
        velocidadAterrizaje = 3;
    }

    @Override
    public void run() {
        primeraVuelta();
        while (true) {
            avanza();
            retrocede();
            if (pista.estaDisponible) {
                pista.semaforo.Espera();
                pista.estaDisponible = false;
                aterriza(frameAeropuerto.getHeight() - 300); // Intento de aterrizaje
                pista.semaforo.Libera();
                if (turnoActual != avionID) { // Cuando no es su turno, se regresa
                    contadorAterrizajesFallidos++;
                    pista.estaDisponible = true;
                    regresa();
                    // DUDA: Al intentar aterrizar también tiene que esperarse de 5 - 10 min (?
//                    esperar(Rutinas.nextInt(500, 1000)); // Equivalente a 5-10 min 
                    continue;
                }
                // Aterrizaje exitoso
                aterriza(frameAeropuerto.getHeight() - 140);
                esperar(300);
                estacionar();
                turnoActual++;
                esperar(Rutinas.nextInt(500, 1000)); // Equivalente a 5-10 min 
                pista.estaDisponible = true;
                break;
            }
        }
    }

    private void avanza() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen, imagenAvion.getWidth(), imagenAvion.getHeight()));
        while (imagenAvion.getX() <= frameAeropuerto.getWidth() - 200) {
            imagenAvion.setBounds(imagenAvion.getX() + velocidad, imagenAvion.getY(), imagenAvion.getWidth(), imagenAvion.getHeight());
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
            if (Rutinas.nextInt(100) == 1) {
                break;
            }
        }
    }

    private void retrocede() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen.substring(0, rutaImagen.length() - 4) + "-rotated.png", imagenAvion.getWidth(), imagenAvion.getHeight()));
        while (imagenAvion.getX() >= 50) {
            imagenAvion.setBounds(imagenAvion.getX() - velocidad, imagenAvion.getY(), imagenAvion.getWidth(), imagenAvion.getHeight());
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
            if (Rutinas.nextInt(200) == 1) {
                break;
            }
        }
    }

    private void aterriza(int valorY) {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen.substring(0, rutaImagen.length() - 4) + "-landing.png", 100, 80));
        while (imagenAvion.getY() <= valorY) {
            imagenAvion.setBounds(imagenAvion.getX(), imagenAvion.getY() + velocidadAterrizaje, 85, 80);
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
        }
    }

    private void regresa() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen.substring(0, rutaImagen.length() - 4) + "-landing.png", 100, 80));
        while (imagenAvion.getY() >= posicionVueloY) {
            imagenAvion.setBounds(imagenAvion.getX(), imagenAvion.getY() - velocidadAterrizaje, 85, 80);
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
        }
        System.out.println("Salio regresa");

    }

    private void estacionar() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen.substring(0, rutaImagen.length() - 4) + "-parked.png", 100, 80));
        imagenAvion.setBounds(posicionEstacionadoX, posicionEstacionadoY, imagenAvion.getWidth(), imagenAvion.getHeight());

    }

    private void esperar(int milisegundos) {
        try {
            sleep(milisegundos);
        } catch (InterruptedException ex) {
            Logger.getLogger(Avion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void primeraVuelta() {
        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen, imagenAvion.getWidth(), imagenAvion.getHeight()));
        while (imagenAvion.getX() <= frameAeropuerto.getWidth() - 200) {
            imagenAvion.setBounds(imagenAvion.getX() + velocidad, imagenAvion.getY(), imagenAvion.getWidth(), imagenAvion.getHeight());
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
        }

        imagenAvion.setIcon(Rutinas.AjustarImagen(rutaImagen.substring(0, rutaImagen.length() - 4) + "-rotated.png", imagenAvion.getWidth(), imagenAvion.getHeight()));
        while (imagenAvion.getX() >= 50) {
            imagenAvion.setBounds(imagenAvion.getX() - velocidad, imagenAvion.getY(), imagenAvion.getWidth(), imagenAvion.getHeight());
            SwingUtilities.updateComponentTreeUI(imagenAvion);
            esperar(10);
        }

    }

}
