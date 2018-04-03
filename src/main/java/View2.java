package main.java;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import java.awt.Font;

public class View2 extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private Modelo m;


	public View2(Modelo m) {
		cargarVentana();
		this.m= m;
	}

	private void cargarVentana() {
		setTitle("Vista2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("0");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 80));
		lblNewLabel.setBounds(59, 26, 401, 261);
		contentPane.add(lblNewLabel);
	}
	
	//IMPLEMENTACIÓN DE LA INTERFAZ OBSERVER!
	public void update() {
		lblNewLabel.setText(String.valueOf(m.getR()));
	}

}