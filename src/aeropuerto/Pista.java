package aeropuerto;

/**
 *
 * @author Carlos Contreras
 */
public class Pista {

    public boolean estaDisponible;
    public Semaforo semaforo;
    
    public Pista() {
        estaDisponible = true;
        semaforo = new Semaforo(1);
    }

}
