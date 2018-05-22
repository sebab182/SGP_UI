package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.Modelo;
import Vistas.FrmPedidos;

	public class CtrPedidos implements ActionListener{
		private FrmPedidos p;
		
	public CtrPedidos(FrmPedidos fp) {
		this.p = fp;
		agregarActionsListener();		
	}
	
	private void agregarActionsListener() {
		p.getBtnCerrar().addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==p.getBtnCerrar()) {
			p.dispose();
		}
	}
	}