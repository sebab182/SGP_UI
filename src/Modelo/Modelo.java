package Modelo;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import SGP.Datos.AbstractFactory;
import SGP.Datos.DataSource;
import SGP.Datos.DatosHardcodeados;
import SGP.Exportador.Exporter;
import SGP.Exportador.ExporterSource;
import SGP.Pedidos.GestorPedidosCarne;
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
		AnalizadordeVencimiento analizadorVencimiento = new AnalizadordeVencimiento(new Date());
		AbstractFactory af = new DatosHardcodeados();
		gestorStock = new GestorStockPiezas();
		((GestorStockPiezas) gestorStock).setCortesVaca(af.cargarConjuntoVaca());
		gestorStock.agregarItems(af.cargarPiezas());
		analizadorVencimiento.analizarVencimientoPiezas((GestorStockPiezas) gestorStock);
		notifyObservers(this);
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
			e.printStackTrace();
		}
		
		
	}
	
	public Map<Tipo, Double> cargarPedidos() {
		AbstractFactory af = new DatosHardcodeados();
		GestorPedidosCarne gp = new GestorPedidosCarne();
		List<Pedido<Tipo>>pedidos=af.cargarPedidos();
		AgrupadordePiezas ap = new AgrupadordePiezas();
		return ap.agruparPedidos(pedidos);
	}


}
