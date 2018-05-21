package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Modelo.Modelo;
import Vistas.FrmPedidos;
import Vistas.FrmPrincipal;

public class CtrPrincipal implements Observer, ActionListener  {
	private Modelo m;
	private FrmPrincipal v;
	
	public CtrPrincipal(Modelo m, FrmPrincipal v) {
		this.v = v;
		this.m = m;
		m.addObserver(this);
		agregarActionsListener();		
	}

	private void agregarActionsListener() {
		v.getBtn().addActionListener(this);
		v.getBtnSalir().addActionListener(this);
		v.getBtnExportar().addActionListener(this);
		v.getBtnPedidos().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==v.getBtn()) {
			System.out.println("Hola");
		}
		
		if(e.getSource()==v.getBtnSalir()) {
			mostrarConfirmacionparaSalir();
		}
		
		if(e.getSource()==v.getBtnExportar()) {
			mostrarVentanaExportar();
		}
		
		if(e.getSource()==v.getBtnPedidos()) {
			mostrarVentanaPedidos();
		}
		
	}
	
	private void mostrarVentanaPedidos() {
		FrmPedidos fp = new FrmPedidos(m);
		CtrPedidos cp = new CtrPedidos(fp);
		fp.show();
		
	}

	private void mostrarVentanaExportar() {
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filtroTXT = new FileNameExtensionFilter("txt","txt");
        FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter("csv","csv");
        FileNameExtensionFilter filtroXLS = new FileNameExtensionFilter("xls","xls");
        fc.addChoosableFileFilter(filtroXLS);
        fc.addChoosableFileFilter(filtroCSV);
        fc.addChoosableFileFilter(filtroTXT);
        fc.setDialogTitle("Exportar informe");
        fc.showSaveDialog(v);
        
        if(fc.getSelectedFile()!=null){
        	String nombreArchivo = fc.getSelectedFile().getName();
        	String extension = fc.getFileFilter().getDescription();
        	String path = fc.getSelectedFile().getAbsolutePath();
        	m.exportarInforme(nombreArchivo,extension,path);
        	}
        else{
        	JOptionPane.showMessageDialog(null, "Se canceló la exportación del informe.","Información", JOptionPane.INFORMATION_MESSAGE);
        }
		
	}

	private void mostrarConfirmacionparaSalir() {
		int seleccion = JOptionPane.showConfirmDialog (null, "¿Salir de la aplicación?","Atención", 0);
		if(seleccion == JOptionPane.YES_OPTION){
			 System.exit(0);
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
