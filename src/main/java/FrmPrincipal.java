package main.java;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class FrmPrincipal extends JFrame implements Observer {

	private JPanel contentPane;
	private	JButton btn;


	public FrmPrincipal(Modelo m) {
		m.addObserver(this);
		inicializarVista();
	}


	private void inicializarVista() {
		setTitle("App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn = new JButton("Bot\u00F3n");
		btn.setBounds(64, 57, 89, 23);
		contentPane.add(btn);
	}

	
	public JButton getBtn() {
		return btn;
	}

	public void setBtn(JButton btn) {
		this.btn = btn;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
