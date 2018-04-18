package main.java;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class CtrPrincipal implements Observer, ActionListener  {
	//private Modelo m;
	private FrmPrincipal v;
	
	public CtrPrincipal(Modelo m, FrmPrincipal v) {
		this.v = v;
		m.addObserver(this);
		agregarActionsListener();		
	}

	private void agregarActionsListener() {
		v.getBtn().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==v.getBtn()) {
			System.out.println("Hola");
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
