package entidades;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author HP
 */
//Reservacion de una mesa hecha por un cliente
@Entity
@Table(name = "reservacion")
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservacion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date hora;

    @ManyToOne
    @JoinColumn(name = "idMesa", nullable = false)
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    public Reservacion() {
    }

    //Crea una reservacion con sus datos
    public Reservacion(Date fecha, Date hora, Mesa mesa, Cliente cliente) {
        this.fecha = fecha;
        this.hora = hora;
        this.mesa = mesa;
        this.cliente = cliente;
    }

    public Long getIdReservacion() {
        return idReservacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
