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
	private ArrayList<Persona> clientes=new ArrayList<Persona>();
	ArrayList<Inventario> inventario=new ArrayList<Inventario>();  
	
	
	private ArrayList<Establecimiento> listadoProveedores=new ArrayList<Establecimiento>();
	
	
	//Constructor para funerarias, crematorios y cementerios
	public Establecimiento(String nombre, int ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado ) {
		this.nombre=nombre;
		this.ubicacion=ubicacion;
		this.capacidad=capacidad;
		this.cuentaCorriente=cuentaCorriente;
		this.afiliacion=afiliacion;
		this.empleado=empleado;
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

}
