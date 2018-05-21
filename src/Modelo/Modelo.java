package Modelo;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.mail.MessagingException;

import SGP.Datos.AbstractFactory;
import SGP.Datos.DataSource;
import SGP.Datos.DatosHardcodeados;
import SGP.Email.Account;
import SGP.Email.MailConcrete;
import SGP.Email.MailSender;
import SGP.Exportador.Exporter;
import SGP.Exportador.ExporterSource;
import SGP.Pedidos.GestorPedidosCarne;
import SGP.Pedidos.Local;
import SGP.Pedidos.Pedido;
import SGP.Stock.AgrupadordePiezas;
import SGP.Stock.AnalizadordeVencimiento;
import SGP.Stock.GestorStock;
import SGP.Stock.GestorStockPiezas;
import SGP.Stock.Pieza;
import SGP.Stock.Tipo;


public class Modelo extends Observable {
	private GestorStock<Pieza> gestorStock;	
	

	public Modelo() {
		cargarStock(); //Se carga al iniciar la app, luego se modifica para distribuir las piezas.
		notifyObservers(this);
	}
	
	public void cargarStock() {
		AbstractFactory af = new DatosHardcodeados();
		gestorStock = new GestorStockPiezas();
		((GestorStockPiezas) gestorStock).setCortesVaca(af.cargarConjuntoVaca());
		gestorStock.agregarItems(af.cargarPiezas());
		//Analizamos el vencimiento de las piezas
		AnalizadordeVencimiento analizadorVencimiento = new AnalizadordeVencimiento(new Date());
		analizadorVencimiento.analizarVencimientoPiezas((GestorStockPiezas) gestorStock);
	}
	

	public GestorStock<Pieza> getGestorStock() {
		return gestorStock;
	}

	public void setGestorStock(GestorStock<Pieza> gestorStock) {
		this.gestorStock = gestorStock;
	}
	
	public void exportarInforme(String nombre, String extension,String path) {
		ExporterSource es = new ExporterSource(extension);
		try {
			Class<?> cls = Class.forName(es.getFactory());
			Exporter exportador = (Exporter)cls.newInstance();
			exportador.setNombre(nombre);
			exportador.setPath(path);
			exportador.generarInforme(gestorStock);
		} catch (Exception e) {
			System.out.println("Se produjo el siguiente error"+e.getMessage());
		}
		
	}
	
	public Map<Tipo, Double> cargarPedidos() {
		AbstractFactory af = new DatosHardcodeados();
		List<Pedido<Tipo>>pedidos=af.cargarPedidos();
		AgrupadordePiezas ap = new AgrupadordePiezas();
		return ap.agruparPedidos(pedidos);
	}

	public void resolverPedidos() {
		//Aca debería llamar a los métodos para determinar pedidos rechazados
	}
	
	public void notificarPedidosRechazados() {
		MailSender mc = new MailConcrete();
		Account datosCuenta= new Account();
		List<Local>listaLocales = new LinkedList<Local>(); 
		for(Local local: listaLocales) {
			String asunto= "Notificación de pedido rechazado";
			String mensaje= "Estimado gerente:\nSe le comunica que su pedido ha sido rechazado por falta de stock.\n.";
			try {
				mc.enviarMail(datosCuenta, local.getEmail(), asunto, mensaje);
			} catch (Exception e) {
				System.out.println("Se produjo el siguiente error: "+e.getMessage());
			}
		}
	}

}
