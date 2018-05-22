package Modelo;
import java.util.Date;
import java.util.HashSet;
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
import SGP.Stock.Distribuidor;
import SGP.Stock.GestorStock;
import SGP.Stock.GestorStockPiezas;
import SGP.Stock.Pieza;
import SGP.Stock.Tipo;


public class Modelo extends Observable {
	private GestorStockPiezas gestorStock;	
	private GestorPedidosCarne gestorPedidos;
	private Distribuidor distribuidor;
	private AbstractFactory af;
	

	public Modelo() {
		cargarDatos(); //Cargo los datos: Consultar problema con el abstract factory!
		try {
			cargarStock();
			cargarPedidos(); //Se cargan los pedidos
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		notifyObservers(this);
		
	}

	public void cargarDatos() {
		DataSource ds = new DataSource("datasource.txt");
		try {
			Class<?> cls = Class.forName(ds.getFactory());
			AbstractFactory af = (AbstractFactory) cls.newInstance();
		} catch (Exception e) {
			System.out.println("Se produjo el siguiente error: "+e.getMessage());
		}
	}
	
	private void cargarPedidos() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		DataSource ds = new DataSource("datasource.txt");
		Class<?> cls = Class.forName(ds.getFactory());
		AbstractFactory af = (AbstractFactory) cls.newInstance();
		gestorPedidos = new GestorPedidosCarne();
		gestorPedidos.agregarPedidos(af.cargarPedidos());
	}

	public void cargarStock() throws ClassNotFoundException, InstantiationException, IllegalAccessException {		
		DataSource ds = new DataSource("datasource.txt");
		Class<?> cls = Class.forName(ds.getFactory());
		AbstractFactory af = (AbstractFactory) cls.newInstance();
		gestorStock = new GestorStockPiezas();
		((GestorStockPiezas) gestorStock).setCortesVaca(af.cargarConjuntoVaca());
		gestorStock.agregarItems(af.cargarPiezas());
		//Analizamos el vencimiento de las piezas
		AnalizadordeVencimiento analizadorVencimiento = new AnalizadordeVencimiento(new Date());
		analizadorVencimiento.analizarVencimientoPiezas(gestorStock);
	}

	public GestorStockPiezas getGestorStock() {
		return gestorStock;
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
	
	public Map<Tipo, Double> cargarPedidosAgrupados() {
		List<Pedido<Tipo>>pedidosAgrupados= new LinkedList<Pedido<Tipo>>();
		HashSet<Pedido<Tipo>>pedidos=gestorPedidos.get_pedidos();
		for(Pedido<Tipo>p: pedidos) {
			pedidosAgrupados.add(p);
		}
		AgrupadordePiezas ap = new AgrupadordePiezas();
		return ap.agruparPedidos(pedidosAgrupados);
	}

	public void resolverPedidos() {
		distribuidor = new Distribuidor();
		distribuidor.resolverPedidos(gestorPedidos.get_pedidos(), gestorStock);
		System.out.println("Aceptados: "+distribuidor.getPedidosAceptados().size());
		System.out.println("Rechazados: "+distribuidor.getPedidosRechazados().size());
		
		List<Pedido<Tipo>>eliminar = distribuidor.getPedidosAceptados();
		for(Pedido<Tipo>pedido: eliminar) {
			gestorPedidos.quitarPedido(pedido);
		}
		//notificarPedidosRechazados();
		//¿Donde se hace la llamada al notificarPedidosRechazados? Aca o en el controlador?
		//Se modifica el stock, le dijo a los observadores que se actualizen:
		notifyObservers();
		}
	
	public void notificarPedidosRechazados() {
		MailSender mc = new MailConcrete();
		Account datosCuenta= new Account();
		List<Pedido<Tipo>>listaPedidosRechazados = distribuidor.getPedidosRechazados();
		for(Pedido<Tipo >pedido: listaPedidosRechazados) {
			Local local = pedido.getLocal();
			String asunto= "Notificación de pedido rechazado";
			String mensaje= "Estimado gerente del local de "+local.getNombreLocal()+":\nSe le comunica que su pedido ha sido rechazado por falta de stock.\nSin más que agregar saluda atentamente. Equipo de distribución.";
			try {
				mc.enviarMail(datosCuenta, local.getEmail(), asunto, mensaje);
			} catch (Exception e) {
				System.out.println("Se produjo el siguiente error: "+e.getMessage());
			}
		}
	}
}
