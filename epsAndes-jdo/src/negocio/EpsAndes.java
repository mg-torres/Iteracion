package negocio;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import persistencia.PersistenciaEpsAndes;




public class EpsAndes {
	
	private static Logger log = Logger.getLogger(EpsAndes.class.getName());
	
	private PersistenciaEpsAndes pe;
	
	
	public EpsAndes()
	{
		pe= PersistenciaEpsAndes.getInstance();
	}
	
	public EpsAndes(JsonObject tableConfig)
	{
		pe= PersistenciaEpsAndes.getInstance(tableConfig);
	}
	
	
	public void cerrarUnidadPersistencia()
	{
		pe.cerrarUnidadPersistencia();
	}
	
	
	
	public Eps agregarEps(String nombre, String gerente)
	{
		log.info ("Adicionando eps " + nombre);
		Eps eps = pe.adicionarEps(nombre, gerente);
        log.info ("Adicionando eps: " + eps);
        return eps;
	}
	
	public Usuario agregarUsuario(String correo, String nombre, String apellido, String tipoId, String nombreEps)
	{
		log.info ("Adicionando usuario " + nombre);
		Usuario usuario = pe.adicionarUsuario(correo, nombre, apellido, tipoId, nombreEps);
        log.info ("Adicionando usuario: " + usuario);
        return usuario;
	}
	
	public long eliminarUsuario(long idUsuario)
	{
		log.info ("eliminando usuario por id: " + idUsuario);
		long resp = pe.eliminarUsuarioPorId(idUsuario);
        log.info ("eliminando usuario: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	public List<Usuario> darUsuarios ()
	{
        log.info ("Consultando Usuarios");
        List<Usuario> usuarios = pe.darUsuarios ();	
        log.info ("Consultando Usuarios: " + usuarios.size() + " usuarios existentes");
        return usuarios;
	}
	
	public Afiliado agregarAfiliado(Date pFecha, String pOrden)
	{
		log.info ("Adicionando Afiliado " + pOrden);
		Afiliado afiliado = pe.adicionarAfiliado(pFecha, pOrden);
        log.info ("Adicionando Afiliado: " + afiliado);
        return afiliado;
	}
	
	public long eliminarAfiliado(long idAfiliado)
	{
		log.info ("eliminando Afiliado por id: " + idAfiliado);
		long resp = pe.eliminarAfiliadoPorId(idAfiliado);
        log.info ("eliminando Afiliado: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	
	public List<Afiliado> darAfiliados ()
	{
        log.info ("Consultando Afiliados");
        List<Afiliado> afiliados = pe.darAfiliados ();	
        log.info ("Consultando Afiliados: " + afiliados.size() + " Afiliados existentes");
        return afiliados;
	}
	
	public Medico agregarMedico(String pRegistro, String pIps)
	{
		log.info ("Adicionando Medico " + pRegistro);
		Medico medico = pe.adicionarMedico(pRegistro, pIps);
        log.info ("Adicionando Medico: " + medico);
        return medico;
	}
	
	public long eliminarMedico(long idMedico)
	{
		log.info ("eliminando Medico por id: " + idMedico);
		long resp = pe.eliminarMedicoPorId(idMedico);
        log.info ("eliminando Medico: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	public List<Medico> darMedicos ()
	{
        log.info ("Consultando Medicos");
        List<Medico> medicos = pe.darMedicos ();	
        log.info ("Consultando Medicos: " + medicos.size() + " Medicos existentes");
        return medicos;
	}
	
	public MedicoGeneral agregarMedicoGeneral(String pNombre, String pApellido)
	{
		log.info ("Adicionando MedicoGeneral " + pNombre);
		MedicoGeneral medicoGeneral = pe.adicionarMedicoGeneral(pNombre, pApellido);
        log.info ("Adicionando MedicoGeneral: " + medicoGeneral);
        return medicoGeneral;
	}
	
	
	public long eliminarMedicoGeneral(long idMedicoGeneral)
	{
		log.info ("eliminando MedicoGeneral por id: " + idMedicoGeneral);
		long resp = pe.eliminarMedicoGeneralPorId(idMedicoGeneral);
        log.info ("eliminando MedicoGeneral: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	public List<MedicoGeneral> darMedicosGenerales ()
	{
        log.info ("Consultando MedicoGenerals");
        List<MedicoGeneral> medicoGenerals = pe.darMedicosGenerales ();	
        log.info ("Consultando MedicoGenerals: " + medicoGenerals.size() + " MedicosGenerales existentes");
        return medicoGenerals;
	}
	
	
	public MedicoEspecialista agregarMedicoEspecialista(String pNombre, String pApellido, String pEspeci)
	{
		log.info ("Adicionando MedicoEspecialista " + pNombre);
		MedicoEspecialista MedicoEspecialista = pe.adicionarMedicoEspecialista(pNombre, pApellido, pEspeci);
        log.info ("Adicionando MedicoEspecialista: " + MedicoEspecialista);
        return MedicoEspecialista;
	}
	
	public long eliminarMedicoEspecialista(long idMedicoEspecialista)
	{
		log.info ("eliminando MedicoEspecialista por id: " + idMedicoEspecialista);
		long resp = pe.eliminarMedicoEspecialistaPorId(idMedicoEspecialista);
        log.info ("eliminando MedicoEspecialista: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	public List<MedicoEspecialista> darMedicosEspecialistas ()
	{
        log.info ("Consultando MedicoEspecialistas");
        List<MedicoEspecialista> medicosEspecialistas = pe.darMedicosEspecialistas ();	
        log.info ("Consultando MedicoEspecialistas: " + medicosEspecialistas.size() + " MedicosEspecialistas existentes");
        return medicosEspecialistas;
	}
	
}