package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
//Cliente premium: hereda de Cliente y agrega puntos y nivel
@Entity
@Table(name = "clientepremium")
public class ClientePremium extends Cliente {

    @Column(nullable = false)
    private int puntos;

    //El nivel va de 1 a 5
    @Column(nullable = false)
    private int nivel;

    public ClientePremium() {
    }

    //Crea un cliente premium con sus datos
    public ClientePremium(String nombre, String correo, String telefono, int puntos, int nivel) {
        super(nombre, correo, telefono);
        this.puntos = puntos;
        this.nivel = nivel;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    //Texto que se muestra en los combobox
    @Override
    public String toString() {
        return nombre + " (Premium)";
    }
}
