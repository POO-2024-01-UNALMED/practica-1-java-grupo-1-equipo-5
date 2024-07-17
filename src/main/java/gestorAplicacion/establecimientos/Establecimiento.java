package gestorAplicacion.establecimientos;

import java.util.ArrayList;

import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;

public class Establecimiento {
	private String nombre;
	private int ubicacion;
	private int capacidad;
	private CuentaBancaria cuentaCorriente;
	private String afiliacion;
	private Empleado empleado;
	private Funeraria funeraria;
	protected ArrayList<Cliente> clientes=new ArrayList<Cliente>();
	ArrayList<Inventario> inventario=new ArrayList<Inventario>();  
	public static ArrayList<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
	
	
	
	private ArrayList<Establecimiento> listadoProveedores=new ArrayList<Establecimiento>();
	
	
	//Constructor a ser llamado desde las clases Crematorio y Cementerio
	public Establecimiento(String nombre, int ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado, Funeraria funeraria ) {
		this.nombre=nombre;
		this.ubicacion=ubicacion;
		this.capacidad=capacidad;
		this.cuentaCorriente=cuentaCorriente;
		this.afiliacion=afiliacion;
		this.empleado=empleado;
		this.funeraria=funeraria;
		establecimientos.add(this);
	}
	
	//Constructor a ser llamado desde para clase Funeraria
	public Establecimiento (String nombre, CuentaBancaria cuentaCorriente) {
		this.nombre=nombre;
		this.cuentaCorriente=cuentaCorriente;
		establecimientos.add(this);
	}
	
	
	public static ArrayList<Establecimiento> filtarEstablecimiento(String establecimiento){
		ArrayList<Establecimiento> filtrados=new ArrayList<Establecimiento>();
		
		if(establecimiento=="cementerio") {
			for(int i=0;i<establecimientos.size();i++) {
				if(establecimientos.get(i) instanceof Cementerio) {
					filtrados.add(Establecimiento.establecimientos.get(i));
				}
			}
		}else if(establecimiento=="crematorio") {
			for(int i=0;i<establecimientos.size();i++) {
				if(establecimientos.get(i) instanceof Crematorio) {
					filtrados.add(Establecimiento.establecimientos.get(i));
				}
			}
		}else if(establecimiento=="funeraria") {
			for(int i=0;i<establecimientos.size();i++) {
				if(establecimientos.get(i) instanceof Funeraria) {
					filtrados.add(Establecimiento.establecimientos.get(i));
				}//fin if
			}//fin for
		}//fin else if
		
		return filtrados;
	}
	
	//buscar por funeraria  //Devuelve el establecimiento de acuerdo a la funeraria 
	public static ArrayList<Establecimiento> buscarPorFuneraria(Funeraria funeraria, String tipoEstablecimiento) {
		
		ArrayList<Establecimiento> establecimientosFuneraria = new ArrayList<Establecimiento>();
		ArrayList<Establecimiento> establecimientosEvaluar = Establecimiento.filtarEstablecimiento(tipoEstablecimiento);
		
		//if (tipoEstablecimiento=="cementerio") {
			//establecimientosEvaluar=Cementerio.establecimientos;
		//}else if (tipoEstablecimiento=="crematorio") {
			//establecimientosEvaluar=Crematorio.establecimientos;
		//}
		
		for (int i=0;i<establecimientosEvaluar.size();i++) {
			
			if (establecimientosEvaluar.get(i).getFuneraria()==funeraria) {
				establecimientosFuneraria.add(establecimientosEvaluar.get(i));
			} //Fin if
		} //Fin for
		
		
		return establecimientosFuneraria;
		
	}
	
	//busca a un cliente en toda las funerarias
	public static Cliente buscarCliente(long CC) {
		
		ArrayList<Establecimiento> funerarias= Establecimiento.filtarEstablecimiento("funeraria");
		
		for(int i=0; i<funerarias.size();i++) {
			Funeraria funeraria = (Funeraria)funerarias.get(i);
			for (int a=0;a<funeraria.clientes.size();a++) {
				if(funeraria.clientes.get(a).getCC()==CC) {
					return funeraria.clientes.get(a);
				}
			}
		}
	return null;
	
	}
	
	//public ArrayList()
	
	
	public static ArrayList<Cliente> buscarCliente(){
		
		
		return null;
	}
	
	
	
	
	
	//metodos get y set
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public CuentaBancaria getCuentaCorriente() {
		return cuentaCorriente;
	}
	public void setCuentaCorriente(CuentaBancaria cuentaCorriente) {
		this.cuentaCorriente=cuentaCorriente;
	}
	public String getAfiliacion() {
		return afiliacion;
	}
	public void setAfiliacion(String afiliacion) {
		this.afiliacion=afiliacion;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad=capacidad;
	}
	public Funeraria getFuneraria() {
		return funeraria;
	}
	public void setFuneraria(Funeraria funeraria) {
		this.funeraria=funeraria;
	}
	public ArrayList<Cliente> getClientes(){
		return clientes;
	} 
	

}
