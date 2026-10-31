package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
//Mesa de un restaurante
@Entity
@Table(name = "mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private int capacidad;

    @ManyToOne
    @JoinColumn(name = "idRestaurante", nullable = false)
    private Restaurante restaurante;

    public Mesa() {
    }

    //Crea una mesa con sus datos
    public Mesa(int numero, int capacidad, Restaurante restaurante) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.restaurante = restaurante;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    //Texto que se muestra en los combobox
    @Override
    public String toString() {
        return "Mesa " + numero + " (capacidad " + capacidad + ")";
    }
}
