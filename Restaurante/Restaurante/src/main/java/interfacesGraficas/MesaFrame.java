package interfacesGraficas;

import dao.MesaDAO;
import dao.RestauranteDAO;
import entidades.Mesa;
import entidades.Restaurante;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

//Frame para registrar mesas
public class MesaFrame extends JDialog {

    private MesaDAO dao = new MesaDAO();
    private RestauranteDAO restauranteDao = new RestauranteDAO();

    private JComboBox<Restaurante> cmbRestaurante = new JComboBox<>();
    private JSpinner spnNumero = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private JSpinner spnCapacidad = new JSpinner(new SpinnerNumberModel(2, 1, 999, 1));
    private JButton btnAgregar = new JButton("Agregar");
    private JButton btnActualizar = new JButton("Actualizar");
    private JButton btnEliminar = new JButton("Eliminar");

    private DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Restaurante", "Numero de mesa",
        "Capacidad"}, 0);
    private JTable tabla = new JTable(modelo);

    //Crea la ventana
    public MesaFrame(JFrame menu) {
        super(menu, "Mesas", true);
        setSize(700, 450);
        setLocationRelativeTo(menu);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        formulario.add(new JLabel("Restaurante:"));
        formulario.add(cmbRestaurante);
        formulario.add(new JLabel("Numero de mesa:"));
        formulario.add(spnNumero);
        formulario.add(new JLabel("Capacidad (personas):"));
        formulario.add(spnCapacidad);

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 5));
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        tabla.setDefaultEditor(Object.class, null);
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(evento -> agregar());
        btnActualizar.addActionListener(evento -> actualizar());
        btnEliminar.addActionListener(evento -> eliminar());
        llenarCombo();
        llenarTabla();
    }

    //Llena el combobox con los restaurantes registrados
    private void llenarCombo() {
        for (Restaurante res : restauranteDao.consultarTodos()) {
            cmbRestaurante.addItem(res);
        }
    }

    //Agrega la mesa ingresada y actualiza la tabla
    private void agregar() {
        Restaurante res1 = (Restaurante) cmbRestaurante.getSelectedItem();
        if (res1 == null) {
            JOptionPane.showMessageDialog(this, "Primero registre un restaurante");
            return;
        }
        Mesa mes1 = new Mesa((Integer) spnNumero.getValue(), (Integer) spnCapacidad.getValue(), res1);
        if (dao.insertar(mes1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la mesa");
            return;
        }
        llenarTabla();
    }

    //Actualiza la mesa seleccionada en la tabla
    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa de la tabla");
            return;
        }
        Restaurante res1 = (Restaurante) cmbRestaurante.getSelectedItem();
        if (res1 == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un restaurante.");
            return;
        }
        Mesa mes1 = dao.buscarPorId((Long) modelo.getValueAt(fila, 0));
        mes1.setRestaurante(res1);
        mes1.setNumero((Integer) spnNumero.getValue());
        mes1.setCapacidad((Integer) spnCapacidad.getValue());
        if (dao.actualizar(mes1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la mesa");
            return;
        }
        llenarTabla();
    }

    //Elimina la mesa seleccionada
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa de la tabla");
            return;
        }
        if (!dao.eliminar((Long) modelo.getValueAt(fila, 0))) {
            JOptionPane.showMessageDialog(this, "No se puede eliminar la mesa porque tiene reservaciones");
            return;
        }
        llenarTabla();
    }

    //Llena la tabla con las mesas registradas
    private void llenarTabla() {
        modelo.setRowCount(0);
        for (Mesa mes : dao.consultarTodos()) {
            modelo.addRow(new Object[]{mes.getIdMesa(), mes.getRestaurante().getNombre(), mes.getNumero(),
                mes.getCapacidad()});
        }
    }
}
