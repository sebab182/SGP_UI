package main.java;
import javax.swing.JFrame;

public class FrmPrincipal {

	private JFrame frame;

	/**
	 * Launch the application. --- se agrega comentario
	 */


	/**
	 * Create the application.
	 */
	public FrmPrincipal() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(50, 50, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}