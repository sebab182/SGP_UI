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
import javax.swing.JTextField;

public class FrmPrincipal extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private	JButton btnResolverPedidos;
	private JButton btnExportar;
	private JButton btnSalir;
	private JButton btnPedidos;
	private JLabel lblNewLabel_1;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtFiltrar;
	private String filtro;
	
	public FrmPrincipal(Modelo m) {
		setResizable(false);
		m.addObserver(this);
		inicializarVista();
		filtro="";
		cargarTabla(m,filtro);
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

		JScrollPane js=new JScrollPane(table);
		scrollPane.setViewportView(js);	
		
		txtFiltrar = new JTextField();
		txtFiltrar.setBounds(474, 57, 141, 20);
		contentPane.add(txtFiltrar);
		txtFiltrar.setColumns(10);
		
		JLabel lblFiltrar = new JLabel("Filtrar:");
		lblFiltrar.setBounds(429, 60, 37, 14);
		contentPane.add(lblFiltrar);
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
	
	public JTextField getTxtFiltrar() {
		return txtFiltrar;
	}

	public void setTxtFiltrar(JTextField txtFiltrar) {
		this.txtFiltrar = txtFiltrar;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	@Override
	public void update(Observable o, Object m) {
		cargarTabla(m,"");
		/*List<Pieza> p = ((Modelo) m).getGestorStock().getStock();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		for(Pieza aux: p) {
			tableModel.addRow(new Object[]{aux.getTipoPieza(), fecha.format(aux.getFechaVencimiento())});
			table.setModel(tableModel);
		}*/		
	}
	
	public void cargarTabla(Object m, String f) {
		tableModel.fireTableDataChanged();
		List<Pieza> piezas = ((Modelo) m).filtrarPorNombrePieza(f);
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
           
        Object[][] datosFilas = new Object[piezas.size()][2];
        Object nombresColumnas[] = {"Pieza", "Fecha de vencimiento"};
        
        for(int i=0; i<piezas.size();i++) {
        	Pieza aux = piezas.get(i);
            datosFilas[i][0] = aux.getTipoPieza().toString();
            datosFilas[i][1] = fecha.format(aux.getFechaVencimiento());
        }
        DefaultTableModel tm = new DefaultTableModel(datosFilas, nombresColumnas);
        table.setModel(tm);  	
	}
}
