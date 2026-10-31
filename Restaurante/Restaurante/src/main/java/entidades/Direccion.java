package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
//Direccion de un restaurante
@Entity
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;

    @Column(length = 100)
    private String calle;

    @Column(length = 100)
    private String estado;

    @Column(nullable = false)
    private int cp;

    @Column(length = 100)
    private String ciudad;

    @Column(nullable = false)
    private int numero;

    public Direccion() {
    }

    //Crea una direccion con sus datos
    public Direccion(String calle, String estado, int cp, String ciudad, int numero) {
        this.calle = calle;
        this.estado = estado;
        this.cp = cp;
        this.ciudad = ciudad;
        this.numero = numero;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
