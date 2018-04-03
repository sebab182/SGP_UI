package main.java;
public class Main {

	public static void main(String[] args) {
		//Creo el modelo
		Modelo m = new Modelo();
		
		//Creo las vistas
		View1 v1 = new View1(m);
		View2 v2 = new View2(m);
		
		//Agrego los observadores al modelo
		m.atachObserver(v1);
		m.atachObserver(v2);
		
		//Inicio ventanas
		v1.setVisible(true);
		v2.setVisible(true);
	}

}
