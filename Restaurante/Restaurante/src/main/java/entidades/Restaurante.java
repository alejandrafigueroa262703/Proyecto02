package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//Restaurante, con su direccion y sus mesas
@Entity
@Table(name = "restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestaurante;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 10)
    private String telefono;

    @Column(nullable = false)
    private String tipoCocina;

    //Cuando se elimina el restaurante tambien se elimina su direccion
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idDireccion", nullable = false)
    private Direccion direccion;

    //Cuando se elimina el restaurante tambien se eliminan sus mesas
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mesa> mesas = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_cliente",
            joinColumns = @JoinColumn(name = "idRestaurante"),
            inverseJoinColumns = @JoinColumn(name = "idCliente"))
    private List<Cliente> clientes = new ArrayList<>();

    public Restaurante() {
    }

    //Crea un restaurante con sus datos
    public Restaurante(String nombre, String telefono, String tipoCocina, Direccion direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipoCocina = tipoCocina;
        this.direccion = direccion;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public void setTipoCocina(String tipoCocina) {
        this.tipoCocina = tipoCocina;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    //Texto que se muestra en los combobox
    @Override
    public String toString() {
        return nombre;
    }
}
