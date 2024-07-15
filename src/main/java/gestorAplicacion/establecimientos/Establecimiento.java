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
	private ArrayList<Persona> clientes=new ArrayList<Persona>();
	ArrayList<Inventario> inventario=new ArrayList<Inventario>();  
	public static ArrayList<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
	
	
	
	private ArrayList<Establecimiento> listadoProveedores=new ArrayList<Establecimiento>();
	
	
	//Constructor para clases Crematorio y Cementerio
	public Establecimiento(String nombre, int ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado, Funeraria funeraria ) {
		this.nombre=nombre;
		this.ubicacion=ubicacion;
		this.capacidad=capacidad;
		this.cuentaCorriente=cuentaCorriente;
		this.afiliacion=afiliacion;
		this.empleado=empleado;
		this.funeraria=funeraria;
	}
	
	//Constructor para clase Funeraria
	public Establecimiento (String nombre, CuentaBancaria cuentaCorriente) {
		this.nombre=nombre;
		this.cuentaCorriente=cuentaCorriente;
	}
	
	
	//buscar por funeraria 
	public static ArrayList<Establecimiento> buscarPorFuneraria(Funeraria funeraria, String tipoEstablecimiento) {
		
		
		ArrayList<Establecimiento> establecimientosFuneraria = new ArrayList<Establecimiento>();
		ArrayList<Establecimiento> establecimientosEvaluar = new ArrayList<Establecimiento>();
		if (tipoEstablecimiento=="cementerio") {
			establecimientosEvaluar=Cementerio.establecimientos;
		}else if (tipoEstablecimiento=="crematorio") {
			establecimientosEvaluar=Crematorio.establecimientos;
		}
		
		for (int i=0;i<establecimientosEvaluar.size();i++) {
			
			if (establecimientosEvaluar.get(i).getFuneraria()==funeraria) {
				establecimientosFuneraria.add(establecimientosEvaluar.get(i));
			} //Fin if
		} //Fin for
		
		
		return establecimientosFuneraria;
		
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
	

}
