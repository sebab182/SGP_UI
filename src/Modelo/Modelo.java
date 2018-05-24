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
import SGP.Stock.GestorStockPiezas;
import SGP.Stock.Tipo;

public class Modelo extends Observable {
	private GestorStockPiezas gestorStock;	
	private GestorPedidosCarne gestorPedidos;
	private Distribuidor distribuidor;
	private AbstractFactory datos;
	

	public Modelo() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		cargarDatos();
		cargarStock();
		cargarPedidos();
	}

	public void cargarDatos() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		DataSource ds = new DataSource("datasource.txt");
		Class<?> cls = Class.forName(ds.getFactory());
		datos = (AbstractFactory) cls.newInstance();
	}
	
	private void cargarPedidos(){
		gestorPedidos = new GestorPedidosCarne();
		gestorPedidos.agregarPedidos(datos.cargarPedidos());
	}

	public void cargarStock(){		
		gestorStock = new GestorStockPiezas();
		gestorStock.setCortesVaca(datos.cargarConjuntoVaca());
		gestorStock.agregarItems(datos.cargarPiezas());
		//Analizamos el vencimiento de las piezas
		AnalizadordeVencimiento analizadorVencimiento = new AnalizadordeVencimiento(new Date());
		analizadorVencimiento.analizarVencimientoPiezas(gestorStock);
	}

	public GestorStockPiezas getGestorStock() {
		return gestorStock;
	}
	
	public void exportarInforme(String nombre, String extension,String path) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ExporterSource es = new ExporterSource(extension);
		Class<?> cls = Class.forName(es.getFactory());
		Exporter exportador = (Exporter)cls.newInstance();
		exportador.setNombre(nombre);
		exportador.setPath(path);
		exportador.generarInforme(gestorStock);
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

	public void resolverPedidos() throws MessagingException {
		distribuidor = new Distribuidor();
		distribuidor.resolverPedidos(gestorPedidos.get_pedidos(), gestorStock);
		
		List<Pedido<Tipo>>eliminar = distribuidor.getPedidosAceptados();
		for(Pedido<Tipo>pedido: eliminar) {
			gestorPedidos.quitarPedido(pedido);
		}
		//notificarPedidosRechazados();
		this.setChanged();
		notifyObservers(this);
		}
	
	public void notificarPedidosRechazados() throws MessagingException {
		MailSender mc = new MailConcrete();
		Account datosCuenta= new Account();
		List<Pedido<Tipo>>listaPedidosRechazados = distribuidor.getPedidosRechazados();
		for(Pedido<Tipo >pedido: listaPedidosRechazados) {
			Local local = pedido.getLocal();
			String asunto= "Notificación de pedido rechazado";
			String mensaje= "Estimado gerente del local de "+local.getNombreLocal()+":\nSe le comunica que su pedido ha sido rechazado por falta de stock.\nSin más que agregar saluda atentamente. Equipo de distribución.";
			mc.enviarMail(datosCuenta, local.getEmail(), asunto, mensaje);
		}
	}
}
