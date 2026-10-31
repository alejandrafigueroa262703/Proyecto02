package interfacesGraficas;

import dao.RestauranteDAO;
import entidades.Direccion;
import entidades.Restaurante;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
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

//Frame para registrar restaurantes
public class RestauranteFrame extends JDialog {

    private RestauranteDAO dao = new RestauranteDAO();

    private JTextField txtNombre = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private JTextField txtCocina = new JTextField();
    private JTextField txtCalle = new JTextField();
    private JSpinner spnNumero = new JSpinner(new SpinnerNumberModel(1, 1, 99999, 1));
    private JTextField txtCiudad = new JTextField();
    private JTextField txtEstado = new JTextField();
    private JSpinner spnCp = new JSpinner(new SpinnerNumberModel(83000, 0, 99999, 1));
    private JButton btnAgregar = new JButton("Agregar");
    private JButton btnActualizar = new JButton("Actualizar");
    private JButton btnEliminar = new JButton("Eliminar");

    private DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Telefono", "Cocina",
        "Calle", "Numero", "Ciudad", "Estado", "C.P."}, 0);
    private JTable tabla = new JTable(modelo);

    //Crea la ventana
    public RestauranteFrame(JFrame menu) {
        super(menu, "Restaurantes", true);
        setSize(850, 550);
        setLocationRelativeTo(menu);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Telefono:"));
        formulario.add(txtTelefono);
        formulario.add(new JLabel("Tipo de cocina:"));
        formulario.add(txtCocina);
        formulario.add(new JLabel("Calle:"));
        formulario.add(txtCalle);
        formulario.add(new JLabel("Numero:"));
        formulario.add(spnNumero);
        formulario.add(new JLabel("Ciudad:"));
        formulario.add(txtCiudad);
        formulario.add(new JLabel("Estado:"));
        formulario.add(txtEstado);
        formulario.add(new JLabel("Codigo postal:"));
        formulario.add(spnCp);

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
        String tel = txtTelefono.getText().trim();
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio");
            return false;
        }
        if (!tel.isEmpty() && !tel.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "*El telefono debe tener 10 digitos sin guiones. Ejemplo: 6444010101");
            return false;
        }
        if (txtCocina.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El tipo de cocina no puede estar vacio");
            return false;
        }
        if (txtCalle.getText().trim().length() > 100) {
            JOptionPane.showMessageDialog(this, "La calle no puede tener mas de 100 caracteres");
            return false;
        }
        if (txtCiudad.getText().trim().length() > 100) {
            JOptionPane.showMessageDialog(this, "La ciudad no puede tener mas de 100 caracteres");
            return false;
        }
        if (txtEstado.getText().trim().length() > 100) {
            JOptionPane.showMessageDialog(this, "El estado no puede tener mas de 100 caracteres");
            return false;
        }
        return true;
    }

    /**
     * Agrega el restaurante ingresados y actualiza la tabla
     */
    private void agregar() {
        if (!validar()) {
            return;
        }
        String tel = txtTelefono.getText().trim();
        String calle = txtCalle.getText().trim();
        String ciudad = txtCiudad.getText().trim();
        String estado = txtEstado.getText().trim();
        Direccion dir1 = new Direccion(calle.isEmpty() ? null : calle, estado.isEmpty() ? null : estado,
                (Integer) spnCp.getValue(), ciudad.isEmpty() ? null : ciudad, (Integer) spnNumero.getValue());
        Restaurante res1 = new Restaurante(txtNombre.getText().trim(), tel.isEmpty() ? null : tel,
                txtCocina.getText().trim(), dir1);
        if (dao.insertar(res1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el restaurante");
            return;
        }
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCocina.setText("");
        txtCalle.setText("");
        txtCiudad.setText("");
        txtEstado.setText("");
        llenarTabla();
    }

    //Actualiza el restaurante seleccionado en la tabla
    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un restaurante de la tabla");
            return;
        }
        if (!validar()) {
            return;
        }
        String tel = txtTelefono.getText().trim();
        String calle = txtCalle.getText().trim();
        String ciudad = txtCiudad.getText().trim();
        String estado = txtEstado.getText().trim();
        Restaurante res1 = dao.buscarPorId((Long) modelo.getValueAt(fila, 0));
        res1.setNombre(txtNombre.getText().trim());
        res1.setTelefono(tel.isEmpty() ? null : tel);
        res1.setTipoCocina(txtCocina.getText().trim());
        res1.getDireccion().setCalle(calle.isEmpty() ? null : calle);
        res1.getDireccion().setNumero((Integer) spnNumero.getValue());
        res1.getDireccion().setCiudad(ciudad.isEmpty() ? null : ciudad);
        res1.getDireccion().setEstado(estado.isEmpty() ? null : estado);
        res1.getDireccion().setCp((Integer) spnCp.getValue());
        if (dao.actualizar(res1) == null) {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el restaurante");
            return;
        }
        llenarTabla();
    }

    //Elimina el restaurante seleccionado y sus mesas y direccion
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un restaurante de la tabla");
            return;
        }
        if (!dao.eliminar((Long) modelo.getValueAt(fila, 0))) {
            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar el restaurante porque sus mesas tienen reservaciones");
            return;
        }
        llenarTabla();
    }

    //Llena la tabla con los restaurantes registrados
    private void llenarTabla() {
        modelo.setRowCount(0);
        for (Restaurante res : dao.consultarTodos()) {
            Direccion dir = res.getDireccion();
            modelo.addRow(new Object[]{res.getIdRestaurante(), res.getNombre(), res.getTelefono(),
                res.getTipoCocina(), dir.getCalle(), dir.getNumero(), dir.getCiudad(), dir.getEstado(), dir.getCp()});
        }
    }
}
