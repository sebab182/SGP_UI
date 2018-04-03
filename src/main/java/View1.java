package main.java;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class View1 extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	//MODELO Y CONTROLADOR
	private Modelo m;
	private Controller c;
	
	//CONTROLES
	private JPanel contentPane;
	public JTextField txtFieldN1;
	public JTextField txtFieldN2;
	public JLabel lblResultado;
	public JButton btnSumar;
	public JButton btnRestar;

	public View1(Modelo m) {
		cargarVentana();
		this.m=m;
		this.c = new Controller(this.m,this);
	}
	
	private void cargarVentana() {		
		setTitle("Vista 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtFieldN1 = new JTextField();
		txtFieldN1.setBounds(32, 31, 86, 20);
		txtFieldN1.setColumns(10);
		txtFieldN1.setText(""+0);
		contentPane.add(txtFieldN1);
		
		
		txtFieldN2 = new JTextField();
		txtFieldN2.setBounds(32, 62, 86, 20);
		txtFieldN2.setColumns(10);
		txtFieldN2.setText(""+0);
		contentPane.add(txtFieldN2);
		
		
		lblResultado = new JLabel("0");
		lblResultado.setBounds(32, 93, 46, 14);
		contentPane.add(lblResultado);
		
		btnSumar = new JButton("Sumar");
		btnSumar.setBounds(172, 30, 89, 23);
		contentPane.add(btnSumar);
		
		btnRestar = new JButton("Restar");
		btnRestar.setBounds(172, 61, 89, 23);
		contentPane.add(btnRestar);
	}
	
	//IMPLEMENTACIÓN DE LA INTERFAZ OBSERVER!
	public void update() {
		txtFieldN1.setText(""+m.getR());
		txtFieldN2.setText(""+0);
	}
}

