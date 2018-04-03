package main.java;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener, Observer {
	
	private Modelo m;
	private View1 v1;

	public Controller (Modelo m, View1 v1) {
		this.m=m;
		this.v1=v1;
		agregarActionListener(v1);
		m.atachObserver(this);
	}

	private void agregarActionListener(View1 v1) {
		v1.btnSumar.addActionListener(this);
		v1.txtFieldN1.addActionListener(this);
		v1.txtFieldN2.addActionListener(this);
		v1.btnRestar.addActionListener(this);
	}
	
	public void asignarVentana1(View1 v1) {
		this.v1=v1;
	}
	
	public void update() {
		this.v1.update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==v1.btnSumar) {
			int n1 = 0 + Integer.valueOf(v1.txtFieldN1.getText());
			int n2 = 0 + Integer.valueOf(v1.txtFieldN2.getText());
			m.sumar(n1, n2);
			int r = m.getR();
			v1.lblResultado.setText(String.valueOf(r));
		}
		
		if(e.getSource()==v1.btnRestar) {
			int n1 = 0 + Integer.valueOf(v1.txtFieldN1.getText());
			int n2 = 0 + Integer.valueOf(v1.txtFieldN2.getText());
			m.restar(n1, n2);
			int r = m.getR();
			v1.lblResultado.setText(String.valueOf(r));
		}
	}
}
