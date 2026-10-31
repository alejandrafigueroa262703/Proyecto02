package interfacesGraficas;

import dao.ClienteDAO;
import entidades.Cliente;
import entidades.ClientePremium;
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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

//Frame para registrar clientes regulares y premium
public class ClienteFrame extends JDialog {

    private ClienteDAO dao = new ClienteDAO();

    private JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Regular", "Premium"});
    private JTextField txtNombre = new JTextField();
    private JTextField txtCorreo = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private JSpinner spnPuntos = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
    private JComboBox<Integer> cmbNivel = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private JButton btnAgregar = new JButton("Agregar");
    private JButton btnActualizar = new JButton("Actualizar");
    private JButton btnEliminar = new JButton("Eliminar");

    private DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Tipo", "Nombre", "Correo",
        "Telefono", "Puntos", "Nivel"}, 0);
    private JTable tabla = new JTable(modelo);

    //Crea la ventana
    public ClienteFrame(JFrame menu) {
        super(menu, "Clientes", true);
        setSize(800, 480);
        setLocationRelativeTo(menu);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        formulario.add(new JLabel("Tipo de cliente:"));
        formulario.add(cmbTipo);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Correo:"));
        formulario.add(txtCorreo);
        formulario.add(new JLabel("Telefono:"));
        formulario.add(txtTelefono);
        formulario.add(new JLabel("Puntos (solo premium):"));
        formulario.add(spnPuntos);
        formulario.add(new JLabel("Nivel (solo premium):"));
        formulario.add(cmbNivel);

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
        llenarTabla();
    }

    //Valida los datos ingresados
    private boolean validar() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String tel = txtTelefono.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio");
            return false;
        }
        if (nombre.length() > 50) {
            JOptionPane.showMessageDialog(this, "El nombre no puede tener mas de 50 caracteres");
            return false;
        }
        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El correo no puede estar vacio");
            return false;
        }
        if (!correo.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            JOptionPane.showMessageDialog(this, "El correo debe tener el formato nombre@dominio.com");
            return false;
        }
        if (!tel.isEmpty() && !tel.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "*El telefono debe tener 10 digitos sin guiones. Ejemplo: 6444010101");
            return false;
        }
        return true;
    }

    //Agrega el cliente ingresado y actualiza la tabla
    private void agregar() {
        if (!validar()) {
            return;
        }
        String tel = txtTelefono.getText().trim();
        Cliente cl1;
        if (cmbTipo.getSelectedItem().equals("Premium")) {
            cl1 = new ClientePremium(txtNombre.getText().trim(), txtCorreo.getText().trim(),
                    tel.isEmpty() ? null : tel, (Integer) spnPuntos.getValue(), (Integer) cmbNivel.getSelectedItem());
        } else {
            cl1 = new Cliente(txtNombre.getText().trim(), txtCorreo.getText().trim(), tel.isEmpty() ? null : tel);
        }
        if (dao.insertar(cl1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el cliente");
            return;
        }
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        llenarTabla();
    }

    //Actualiza el cliente seleccionado en la tabla
    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
            return;
        }
        if (!validar()) {
            return;
        }
        String tel = txtTelefono.getText().trim();
        Cliente cl1 = dao.buscarPorId((Long) modelo.getValueAt(fila, 0));
        cl1.setNombre(txtNombre.getText().trim());
        cl1.setCorreo(txtCorreo.getText().trim());
        cl1.setTelefono(tel.isEmpty() ? null : tel);
        if (cl1 instanceof ClientePremium) {
            ClientePremium clp = (ClientePremium) cl1;
            clp.setPuntos((Integer) spnPuntos.getValue());
            clp.setNivel((Integer) cmbNivel.getSelectedItem());
        }
        if (dao.actualizar(cl1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente");
            return;
        }
        llenarTabla();
    }

    //Da de baja al cliente seleccionado
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
            return;
        }
        if (!dao.eliminar((Long) modelo.getValueAt(fila, 0))) {
            JOptionPane.showMessageDialog(this, "No se pudo dar de baja al cliente");
            return;
        }
        llenarTabla();
    }

    //Llena la tabla con los clientes activos
    private void llenarTabla() {
        modelo.setRowCount(0);
        for (Cliente cl : dao.consultarTodos()) {
            if (cl instanceof ClientePremium) {
                ClientePremium clp = (ClientePremium) cl;
                modelo.addRow(new Object[]{clp.getIdCliente(), "Premium", clp.getNombre(), clp.getCorreo(),
                    clp.getTelefono(), clp.getPuntos(), clp.getNivel()});
            } else {
                modelo.addRow(new Object[]{cl.getIdCliente(), "Regular", cl.getNombre(), cl.getCorreo(),
                    cl.getTelefono(), "", ""});
            }
        }
    }
}
