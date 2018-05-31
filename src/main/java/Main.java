package main.java;

import java.util.Date;

import Controlador.CtrPrincipal;
import Modelo.Modelo;
import SGP.Stock.Pieza;
import SGP.Stock.Tipo;
import Vistas.FrmPrincipal;

public class Main {

	public static void main(String[] args) {
		try {
			//Creo el modelo
			Modelo m = new Modelo();
			//Creo la vista
			FrmPrincipal frmP = new FrmPrincipal(m);
			CtrPrincipal ctrP = new CtrPrincipal(m,frmP);
			//Agrego los observadores al modelo
			m.addObserver(frmP);
			//Inicio ventanas
			frmP.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
