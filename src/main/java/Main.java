package main.java;
public class Main {

	public static void main(String[] args) {
		//Creo el modelo
		Modelo m = new Modelo();
		
		//Creo la vista
		FrmPrincipal frmP = new FrmPrincipal(m);
		CtrPrincipal ctrP = new CtrPrincipal(m,frmP);
		
		//Agrego los observadores al modelo
		//m.addObserver(ctrP);
		//m.addObserver(frmP);
		
		//Inicio ventanas
		frmP.setVisible(true);;
	}

}