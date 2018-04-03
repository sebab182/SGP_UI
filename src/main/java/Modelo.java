package main.java;
import java.util.LinkedList;
import java.util.List;

//TODO: Eliminar!
public class Modelo {
	
	private Integer r;
	private List<Observer>observadores;

	public Modelo() {
		this.observadores= new LinkedList<Observer>();
	}
	
	public void sumar(int n1, int n2) {
		this.r= n1+n2;
		notificar();
	}
	
	public void restar(int n1, int n2) {
		this.r=n1-n2;
		notificar();
	}


	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}
	
	public void atachObserver(Observer o) { //agregarObservador
		this.observadores.add(o);
	}
	
	public void detachObserver(Observer o) {//quitarObservador
		this.observadores.remove(o);
	}
	
	private void notificar() {
		//Agarro a todas las clases suscriptas a observador y le hago un update!
		for(Observer o: this.observadores) {
			o.update();
		}
	}
}
