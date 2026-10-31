package interfacesGraficas;

import com.toedter.calendar.JDateChooser;
import dao.ClienteDAO;
import dao.MesaDAO;
import dao.ReservacionDAO;
import dao.RestauranteDAO;
import entidades.Cliente;
import entidades.Mesa;
import entidades.Reservacion;
import entidades.Restaurante;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

//Frame para registrar reservaciones
public class ReservacionFrame extends JDialog {

    private ReservacionDAO dao = new ReservacionDAO();
    private RestauranteDAO restauranteDao = new RestauranteDAO();
    private MesaDAO mesaDao = new MesaDAO();
    private ClienteDAO clienteDao = new ClienteDAO();

    private JComboBox<Restaurante> cmbRestaurante = new JComboBox<>();
    private JComboBox<Mesa> cmbMesa = new JComboBox<>();
    private JComboBox<Cliente> cmbCliente = new JComboBox<>();
    private JDateChooser selectorFecha = new JDateChooser();
    private JSpinner spnHora = new JSpinner(new SpinnerDateModel());
    private JButton btnAgregar = new JButton("Agregar");
    private JButton btnActualizar = new JButton("Actualizar");
    private JButton btnEliminar = new JButton("Eliminar");
    private JComboBox<String> cmbHora = new JComboBox<>();

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    private DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Restaurante", "Mesa", "Cliente",
        "Fecha", "Hora"}, 0);
    private JTable tabla = new JTable(modelo);

    //Crea la ventana
    public ReservacionFrame(JFrame menu) {
        super(menu, "Reservaciones", true);
        setSize(850, 500);
        setLocationRelativeTo(menu);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        selectorFecha.setDateFormatString("dd/MM/yyyy");
        //spnHora.setEditor(new JSpinner.DateEditor(spnHora, "HH:mm"));

        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        formulario.add(new JLabel("Restaurante:"));
        formulario.add(cmbRestaurante);
        formulario.add(new JLabel("Mesa:"));
        formulario.add(cmbMesa);
        formulario.add(new JLabel("Cliente:"));
        formulario.add(cmbCliente);
        formulario.add(new JLabel("Fecha:"));
        formulario.add(selectorFecha);
        formulario.add(new JLabel("Hora:"));
        formulario.add(cmbHora);
        //formulario.add(spnHora);

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 5));
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        tabla.setDefaultEditor(Object.class, null);
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        //Cuando se elige un restaurante el combobox de mesas muestra solo sus mesas
        cmbRestaurante.addActionListener(evento -> llenarMesas());
        btnAgregar.addActionListener(evento -> agregar());
        btnActualizar.addActionListener(evento -> actualizar());
        btnEliminar.addActionListener(evento -> eliminar());
        llenarHoras();
        llenarCombos();
        llenarTabla();
    }

    //llenar comboBox para la hora de las reservaviones
    private void llenarHoras() {
        cmbHora.addItem(String.format("12:00"));
        cmbHora.addItem(String.format("12:30"));
        cmbHora.addItem(String.format("13:30"));
        cmbHora.addItem(String.format("15:00"));
        cmbHora.addItem(String.format("15:30"));
        cmbHora.addItem(String.format("16:00"));
        cmbHora.addItem(String.format("17:30"));
        cmbHora.addItem(String.format("18:00"));
        cmbHora.addItem(String.format("19:00"));
        cmbHora.addItem(String.format("19:30"));
        cmbHora.addItem(String.format("20:30"));
        cmbHora.addItem(String.format("21:00"));
        cmbHora.addItem(String.format("21:30"));
        cmbHora.addItem(String.format("22:30"));
    }

    //Convierte la hora elegida en el combobox de llenarHoras de las reservaciones a Date
    private Date obtenerHora() {
        try {
            return formatoHora.parse((String) cmbHora.getSelectedItem());
        } catch (ParseException e) {
            return null;
        }
    }

    //Llenar los combobox de restaurantes y clientes
    private void llenarCombos() {
        for (Restaurante res : restauranteDao.consultarTodos()) {
            cmbRestaurante.addItem(res);
        }
        for (Cliente cl : clienteDao.consultarTodos()) {
            cmbCliente.addItem(cl);
        }
    }

    //Llena el combobox de mesas con las mesas del restaurante elegido
    private void llenarMesas() {
        cmbMesa.removeAllItems();
        Restaurante res1 = (Restaurante) cmbRestaurante.getSelectedItem();
        if (res1 == null) {
            return;
        }
        for (Mesa mes : mesaDao.consultarTodos()) {
            if (mes.getRestaurante().getIdRestaurante().equals(res1.getIdRestaurante())) {
                cmbMesa.addItem(mes);
            }
        }
    }

    //Valida los datos ingresados
    private boolean validar() {
        if (cmbRestaurante.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un restaurante");
            return false;
        }
        if (cmbMesa.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa");
            return false;
        }
        if (cmbCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return false;
        }
        if (cmbHora.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha");
            return false;
        }
        return true;
    }

    //Agrega la reservacion ingresada y actualiza la tabla
    private void agregar() {
        if (!validar()) {
            return;
        }
        Reservacion rsv1 = new Reservacion(selectorFecha.getDate(), obtenerHora(),
                (Mesa) cmbMesa.getSelectedItem(), (Cliente) cmbCliente.getSelectedItem());
        if (dao.insertar(rsv1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la reservacion");
            return;
        }
        selectorFecha.setDate(null);
        llenarTabla();
    }

    //Actualiza la reservacion seleccionada en la tabla
    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reservacion de la tabla");
            return;
        }
        if (!validar()) {
            return;
        }
        Reservacion rsv1 = dao.buscarPorId((Long) modelo.getValueAt(fila, 0));
        rsv1.setMesa((Mesa) cmbMesa.getSelectedItem());
        rsv1.setCliente((Cliente) cmbCliente.getSelectedItem());
        rsv1.setFecha(selectorFecha.getDate());
        rsv1.setHora(obtenerHora());
        //rsv1.setHora((Date) spnHora.getValue());
        if (dao.actualizar(rsv1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la reservacion");
            return;
        }
        llenarTabla();
    }

    //Elimina la reservacion seleccionada
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reservacion de la tabla.");
            return;
        }
        if (!dao.eliminar((Long) modelo.getValueAt(fila, 0))) {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la reservacion.");
            return;
        }
        llenarTabla();
    }

    //Llena la tabla con las reservaciones registradas
    private void llenarTabla() {
        modelo.setRowCount(0);
        for (Reservacion rsv : dao.consultarTodos()) {
            modelo.addRow(new Object[]{rsv.getIdReservacion(), rsv.getMesa().getRestaurante().getNombre(),
                "Mesa " + rsv.getMesa().getNumero(), rsv.getCliente().getNombre(),
                formatoFecha.format(rsv.getFecha()), formatoHora.format(rsv.getHora())});
        }
    }
}
