package interfacesGraficas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPrincipal extends JFrame {

    private JMenuItem itemRestaurantes = new JMenuItem("Restaurantes");
    private JMenuItem itemMesas = new JMenuItem("Mesas");
    private JMenuItem itemClientes = new JMenuItem("Clientes");
    private JMenuItem itemReservaciones = new JMenuItem("Reservaciones");
    private JMenuItem itemSalir = new JMenuItem("Salir");

    private JButton btnRestaurantes = new JButton("Restaurantes");
    private JButton btnMesas = new JButton("Mesas");
    private JButton btnClientes = new JButton("Clientes");
    private JButton btnReservaciones = new JButton("Reservaciones");
    private JButton btnSalir = new JButton("Salir");

    //Crea el menu principal
    public MenuPrincipal() {
        setTitle("Sistema de Reservacion de Restaurantes");
        setSize(650, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Barra de menu
        JMenu menuOpciones = new JMenu("Opciones");
        menuOpciones.add(itemRestaurantes);
        menuOpciones.add(itemMesas);
        menuOpciones.add(itemClientes);
        menuOpciones.add(itemReservaciones);
        menuOpciones.add(itemSalir);
        JMenuBar barra = new JMenuBar();
        barra.add(menuOpciones);
        setJMenuBar(barra);

        //Titulo
        JLabel titulo = new JLabel("Sistema de Reservacion de Restaurantes", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panelOpciones = new JPanel(new GridLayout(2, 2, 15, 15));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(25, 40, 10, 40));
        panelOpciones.add(btnRestaurantes);
        panelOpciones.add(btnMesas);
        panelOpciones.add(btnClientes);
        panelOpciones.add(btnReservaciones);
        add(panelOpciones, BorderLayout.CENTER);

        JPanel panelSalir = new JPanel(new BorderLayout());
        panelSalir.setBorder(BorderFactory.createEmptyBorder(5, 40, 25, 40));
        panelSalir.add(btnSalir, BorderLayout.CENTER);
        add(panelSalir, BorderLayout.SOUTH);

        //Eventos de la barra de menu
        itemRestaurantes.addActionListener(evento -> new RestauranteFrame(this).setVisible(true));
        itemMesas.addActionListener(evento -> new MesaFrame(this).setVisible(true));
        itemClientes.addActionListener(evento -> new ClienteFrame(this).setVisible(true));
        itemReservaciones.addActionListener(evento -> new ReservacionFrame(this).setVisible(true));
        itemSalir.addActionListener(evento -> System.exit(0));

        //Eventos de los botones
        btnRestaurantes.addActionListener(evento -> new RestauranteFrame(this).setVisible(true));
        btnMesas.addActionListener(evento -> new MesaFrame(this).setVisible(true));
        btnClientes.addActionListener(evento -> new ClienteFrame(this).setVisible(true));
        btnReservaciones.addActionListener(evento -> new ReservacionFrame(this).setVisible(true));
        btnSalir.addActionListener(evento -> System.exit(0));
    }
}
