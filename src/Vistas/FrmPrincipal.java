package Vistas;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Modelo.Modelo;
import SGP.Stock.Pieza;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class FrmPrincipal extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private Modelo m;
	private JPanel contentPane;
	private	JButton btnResolverPedidos;
	private JButton btnExportar;
	private JButton btnSalir;
	private JButton btnPedidos;
	private JLabel lblNewLabel_1;
	private JTable table;
	private DefaultTableModel tableModel;
	private int columnNumber = 2;

	public FrmPrincipal(Modelo m) {
		setResizable(false);
		m.addObserver(this);
		this.m=m;
		inicializarVista();
	}

	private void inicializarVista() {
		try {
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e) {
			  System.out.println("Se produjo el siguiente error: " + e);
			}
		
		setTitle("Sistema de Gesti\u00F3n de Piezas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 605, 248);
		contentPane.add(scrollPane);
		
		btnResolverPedidos = new JButton("Resolver pedidos");
		btnResolverPedidos.setBounds(199, 344, 119, 23);
		contentPane.add(btnResolverPedidos);
		
		JLabel lblNewLabel = new JLabel("Sistema de Gesti\u00F3n de Piezas");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 24));
		lblNewLabel.setBounds(10, 0, 298, 38);
		contentPane.add(lblNewLabel);
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBounds(427, 344, 89, 23);
		contentPane.add(btnExportar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(526, 344, 89, 23);
		contentPane.add(btnSalir);
		
		btnPedidos = new JButton("Ver pedidos");
		btnPedidos.setBounds(328, 344, 89, 23);
		contentPane.add(btnPedidos);
		
		lblNewLabel_1 = new JLabel("Informaci\u00F3n del Stock");
		lblNewLabel_1.setBounds(10, 60, 119, 14);
		contentPane.add(lblNewLabel_1);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Pieza");
		tableModel.addColumn("Fecha de vencimiento");
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		
		JScrollPane js=new JScrollPane(table);
		scrollPane.setViewportView(js);	
		update(null,null);
	}
	
	public JButton getBtn() {
		return btnResolverPedidos;
	}

	public void setBtn(JButton btn) {
		this.btnResolverPedidos = btn;
	}
	
	public JButton getBtnExportar() {
		return btnExportar;
	}

	public void setBtnExportar(JButton btnExportar) {
		this.btnExportar = btnExportar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public JButton getBtnPedidos() {
		return btnPedidos;
	}

	public void setBtnPedidos(JButton btnPedidos) {
		this.btnPedidos = btnPedidos;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("Llamando al update");
		List<Pieza> p = m.getGestorStock().getStock();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		for(Pieza aux: p) {
			tableModel.addRow(new Object[]{aux.getTipoPieza(), fecha.format(aux.getFechaVencimiento())});
			table.setModel(tableModel);
		}
		
		
	}
}
