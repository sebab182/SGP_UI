package Vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Modelo.Modelo;
import SGP.Stock.Tipo;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class FrmPedidos extends JDialog {
	private Modelo m;
	private JPanel contentPanel = new JPanel();
	private JButton btnCerrar;
	private JTable table;
	private DefaultTableModel tableModel;
	private int columnNumber = 2;


	public FrmPedidos(Modelo m) {
		super();
		setResizable(false);
		this.m=m;
		inicializarVista();
	}


	private void inicializarVista() {
		setTitle("Pedidos");
		setModal(true);
		setBounds(100, 100, 642, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(527, 327, 89, 23);
		contentPanel.add(btnCerrar);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Tipo de pieza");
		tableModel.addColumn("Cantidad pedida");
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		contentPanel.add(table);
		table.setModel(tableModel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 605, 269);
		contentPanel.add(scrollPane);
		JScrollPane js=new JScrollPane(table);
		scrollPane.setViewportView(js);	
		
		JLabel lblPedidos = new JLabel("Pedidos");
		lblPedidos.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblPedidos.setBounds(10, 11, 73, 29);
		contentPanel.add(lblPedidos);
		cargarPedidos();
	}


	public JButton getBtnCerrar() {
		return btnCerrar;
	}


	public void setBtnCerrar(JButton btnCerrar) {
		this.btnCerrar = btnCerrar;
	}
	
	public void cargarPedidos() {
		Map<Tipo, Double> aux = m.cargarPedidosAgrupados();
		for(Entry<Tipo, Double> entryAux: aux.entrySet()) {
			tableModel.addRow(new Object[]{entryAux.getKey(),entryAux.getValue()});
		}
	}
	
	
}
