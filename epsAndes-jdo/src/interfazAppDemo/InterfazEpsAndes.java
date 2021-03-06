package interfazAppDemo;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import negocio.Campa�aPrevencion;
import negocio.Eps;
import negocio.EpsAndes;
import negocio.Servicio;




@SuppressWarnings("serial")
public class InterfazEpsAndes extends JFrame implements ActionListener{

	private static Logger log = Logger.getLogger(InterfazEpsAndes.class.getName());
	
	private static final String CONFIG_INTERFAZ = "./resources/config/interfaceConfigApp.json";
	
	private static final String CONFIG_INTERFAZ_Admin = "./resources/config/interfaceConfigAdmin.json";
	
	private static final String CONFIG_INTERFAZ_Medico = "./resources/config/interfaceConfigDemo.json";
	
	private static final String CONFIG_INTERFAZ_Recepcionista = "./resources/config/interfaceConfigRecepcionista.json";
	
	private static final String CONFIG_INTERFAZ_Gerente = "./resources/config/interfaceConfigGerente.json";
	
	private static final String CONFIG_INTERFAZ_Admin_Campa�a = "./resources/config/interfaceConfigAdminCampa�a.json";
	
	
	private static final String CONFIG_TABLAS = "./resources/config/TablasBD_A.json"; 
	
	private JsonObject tableConfig;
	
	private EpsAndes epsAndes;
	
	private JsonObject guiConfig;
	
	private PanelDatos panelDatos;
	
	private JMenuBar menuBar;
	
	
	public InterfazEpsAndes()
	{
		
		String [] usuarios= {"Afiliado", "Medico", "Recepcionista", "Administrador", "Gerente", "Organizador de campa�a"};
		
		ImageIcon icono= new ImageIcon("./resources/config/icono.jpg");
		
		String resp = (String) JOptionPane.showInputDialog(this, "Seleccione su perfil de usuario", "Carrera", JOptionPane.DEFAULT_OPTION,icono, usuarios, usuarios[0]);
		
		if(resp.equals("Afiliado"))
		{
			guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
		}
		else if (resp.equals("Administrador")) {
			guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ_Admin);
		}
		else if (resp.equals("Medico")) {
			guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ_Medico);
		}
		else if (resp.equals("Recepcionista")) {
			guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ_Recepcionista);
		}
		else if (resp.equals("Gerente")) {
			guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ_Gerente);
		}
		else if (resp.equals("Organizador de campa�a")) {
			guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ_Admin_Campa�a);
		}
		
        
        // Configura la apariencia del frame que contiene la interfaz gr�fica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        epsAndes = new EpsAndes (tableConfig);
        
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );
        ImageIcon imagen=new ImageIcon (path);
        JLabel img=new JLabel(imagen);
        img.setPreferredSize(new Dimension(200,200));
        

        setLayout (new BorderLayout());
        add (img, BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER ); 
	}
	
	private JsonObject openConfig(String tipo, String archConfig) {
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontr� un archivo de configuraci�n v�lido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de interfaz v�lido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
	}
	
	private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creaci�n de la barra de men�s
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creaci�n de cada uno de los men�s
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creaci�n de cada una de las opciones del men�
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
	
	private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuraci�n por defecto" );			
			titulo = "EpsAndes APP ";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuraci�n indicada en el archivo de configuraci�n" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }
	
	
	public void adicionarEps()
	{
		try 
    	{
    		String nombreEps = JOptionPane.showInputDialog (this, "Nombre de la eps?", "Adicionar Eps", JOptionPane.QUESTION_MESSAGE);
    		String nombreGerente=JOptionPane.showInputDialog (this, "Nombre del Gerente?", "Adicionar Gerente", JOptionPane.QUESTION_MESSAGE);
    		if (nombreEps != null&& nombreGerente != null)
    		{
    			Eps tb = epsAndes.agregarEps(nombreEps, nombreGerente);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una eps con este nombre: " + nombreEps);
        		}
        		String resultado = "En adicionar Eps\n\n";
        		resultado += "Eps adicionada exitosamente: " + tb;
    			resultado += "\n Operaci�n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void cancelarServiciosCampa�a()
	{
		try 
    	{
    		String servicios = JOptionPane.showInputDialog (this, "Ingrese los id de los servicios que desea cancelar separados por comas", "Cancelar Servicios", JOptionPane.QUESTION_MESSAGE);
    		String codigoCampa�a=JOptionPane.showInputDialog (this, "Ingrese el id de la campa�a", "Id de campa�a de la cual se van a eliminar servicios", JOptionPane.QUESTION_MESSAGE);
    		if (servicios != null&& codigoCampa�a != null)
    		{
    			String [] listaServicios = servicios.split(",");
    			for (int i = 0; i < listaServicios.length; i++) {
					long servicioEliminado= epsAndes.cancelarServicioCampa�a(Long.parseLong(listaServicios[i]));
					if (servicioEliminado == 0)
	        		{
	        			throw new Exception ("No se pudo cancelar el servicio con este id: " + listaServicios[i]);
	        		}
    			}
    			
        		String resultado = "En cancelar servicios\n\n";
        		resultado += "Se eliminaron los servicios";
    			resultado += "\n Operaci�n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void deshabilitarServicios()
	{
		try 
    	{
    		String servicios = JOptionPane.showInputDialog (this, "Ingrese los id de los servicios que desea deshabilitar separados por comas", "Cancelar Servicios", JOptionPane.QUESTION_MESSAGE);
    		String FechaI=JOptionPane.showInputDialog (this, "Ingrese la fecha inicial desde la cual se va a deshabilitar el servicio siguiendo el formato yyyy-mm-dd. Ej: 2019-12-24", "Deshabilitar servicio", JOptionPane.QUESTION_MESSAGE);
    		String FechaF=JOptionPane.showInputDialog (this, "Ingrese la fecha hasta la cual se va a deshabilitar el servicio siguiendo el formato yyyy-mm-dd. Ej: 2019-12-24", "Deshabilitar servicio", JOptionPane.QUESTION_MESSAGE);
    		if (servicios != null&& FechaI != null&& FechaF != null)
    		{
    			String [] listaServicios = servicios.split(",");
    			for (int i = 0; i < listaServicios.length; i++) {
					long servicioEliminado= epsAndes.deshabilitarServicio(Long.parseLong(listaServicios[i]));
					if (servicioEliminado == 0)
	        		{
	        			throw new Exception ("No se pudo deshabilitar el servicio con este id: " + listaServicios[i]);
	        		}
    			}
    			
        		String resultado = "En cancelar servicios\n\n";
        		resultado += "Se eliminaron los servicios";
    			resultado += "\n Operaci�n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void rehabilitarServicios()
	{
		try 
    	{
    		String servicios = JOptionPane.showInputDialog (this, "Ingrese los id de los servicios que desea rehabilitar separados por comas", "Cancelar Servicios", JOptionPane.QUESTION_MESSAGE);
    		if (servicios != null)
    		{
    			String [] listaServicios = servicios.split(",");
    			for (int i = 0; i < listaServicios.length; i++) {
					long servicioEliminado= epsAndes.rehabilitarServicio(Long.parseLong(listaServicios[i]));
					if (servicioEliminado == 0)
	        		{
	        			throw new Exception ("No se pudo rehabilitar el servicio con este id: " + listaServicios[i]);
	        		}
    			}
    			
        		String resultado = "En cancelar servicios\n\n";
        		resultado += "Se eliminaron los servicios";
    			resultado += "\n Operaci�n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	
	
	
	
	public void registrarCampa�a()
	{
		try 
    	{
    		String afiliadosEsperados = JOptionPane.showInputDialog (this, "ingrese el numero de afiliados esperados en la campa�a?", "Registrar Campa�a", JOptionPane.QUESTION_MESSAGE);
    		String fechaInicial=JOptionPane.showInputDialog (this, "Ingrese la fecha inicial de la campa�a siguiendo el formato yyyy-mm-dd. Ej: 2019-12-24", "Registrar Campa�a", JOptionPane.QUESTION_MESSAGE);
    		String fechaFinal=JOptionPane.showInputDialog (this, "Ingrese la fecha final de la campa�a siguiendo el formato yyyy-mm-dd. Ej: 2019-12-24", "Registrar Campa�a", JOptionPane.QUESTION_MESSAGE);
    		String servicios=JOptionPane.showInputDialog (this, "Ingrese los servicios que desea para la campa�a separados por comas", "Registrar Campa�a", JOptionPane.QUESTION_MESSAGE);
    		String capacidades=JOptionPane.showInputDialog (this, "Ingrese los servicios que desea para la campa�a separados por comas", "Registrar Campa�a", JOptionPane.QUESTION_MESSAGE);
    		ArrayList<Servicio> serviciosRegistrados= new ArrayList<Servicio>();
    		ArrayList<Servicio> servciosNoRegistrados= new ArrayList<Servicio>();
    		if (afiliadosEsperados != null&& fechaFinal != null&& fechaInicial != null && servicios !=null && capacidades !=null)
    		{
    			String []listaServicios= servicios.split(",");
    			String [] listaCapacidades= capacidades.split(",");
    			

    			for (int i = 0; i < listaServicios.length; i++) {
					
    				Servicio buscado= epsAndes.darServiciosporNombre(listaServicios[i]);
    				if(buscado!=null)
    				{
    					if(buscado.getCapacidad()<=Integer.parseInt(listaCapacidades[i]))
    					{
    						serviciosRegistrados.add(buscado);
    					}
    					else
    					{
    						servciosNoRegistrados.add(buscado);
    					}
    				}
    				else
    				{
    					servciosNoRegistrados.add(buscado);
    				}
				}
    			if(!servciosNoRegistrados.isEmpty())
    			{
    				String resp="No se pudo crear esta campa�a porque estos servicios no estan disponibles: ";
    				for (int i = 0; i < servciosNoRegistrados.size(); i++) {
						resp+=servciosNoRegistrados.get(i).getTipo();
					}
    				
    				throw new Exception(resp);
    			}
    			else
    			{
    				for (int i = 0; i < serviciosRegistrados.size(); i++) {
						epsAndes.reservarServicio(serviciosRegistrados.get(i).getCodigoServicio(), Integer.parseInt(listaCapacidades[i]));
					}
    				
    			
    			Campa�aPrevencion tb = epsAndes.registrarCampa�aPrevencion(Integer.parseInt(afiliadosEsperados), Date.valueOf(fechaInicial), Date.valueOf(fechaFinal));
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear esta campa�a");
        		}
        		String resultado = "En registrar una campa�a\n\n";
        		resultado += "Campa�a adicionada exitosamente: " + tb+"con estos servicios:";
        		for (int j = 0; j < serviciosRegistrados.size(); j++) {
        			resultado+= serviciosRegistrados.get(j).getTipo()+"en este horario"+ serviciosRegistrados.get(j).getHorario()+"\n";
					
				}
    			resultado += "\n Operaci�n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    			}
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	
	
	
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecuci�n\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		return resultado;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String evento=  arg0.getActionCommand();
		
		if(evento.equals("registrarEps"))
		{
			this.adicionarEps();
		}
		if(evento.equals("reservarCampa�a"))
		{
			this.registrarCampa�a();
		}
		if(evento.equals("cancelarServiciosCampa�a"))
		{
			this.cancelarServiciosCampa�a();
		}
		if(evento.equals("rehabilitarServicios"))
		{
			this.rehabilitarServicios();;
		}
		
	}
	
	
	 public static void main( String[] args )
	    {
	        try
	        {
	        	
	            // Unifica la interfaz para Mac y para Windows.
	            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
	            InterfazEpsAndes interfaz = new InterfazEpsAndes( );
	            interfaz.setVisible( true );
	        }
	        catch( Exception e )
	        {
	            e.printStackTrace( );
	        }
	    }
	

}
