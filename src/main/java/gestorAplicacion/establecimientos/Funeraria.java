package gestorAplicacion.establecimientos;

import java.util.ArrayList;

import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;

public class Funeraria extends Establecimiento{

	private static CuentaBancaria cuentaAhorros;
	private ArrayList<Empleado> empleados=new ArrayList<Empleado>();
	private ArrayList<Crematorio> crematorios=new ArrayList<Crematorio>();
	private ArrayList<Cementerio> cementerios=new ArrayList<Cementerio>();
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	
	//hereda inventario
	
	
	//Constructor
	public Funeraria(String nombre, CuentaBancaria cuentaAhorros) {
		super(nombre,0,0,null,null,null);
		this.cuentaAhorros=cuentaAhorros;
		
	}
	
	
	
	
	
	public ArrayList<Crematorio> disponibilidadCrematorios(String afiliacion,ArrayList<Familiar> listadoFamiliares){
		ArrayList<Crematorio> crematoriosDisponibles=new ArrayList<Crematorio>();
		
		//Contar la cantidad de familiares y acompañantes
		int cantidadFamiliares=listadoFamiliares.size();
		
		for (int i=0; i<listadoFamiliares.size();i++) {
			if(listadoFamiliares.get(i).getEdad()>17) {
				int cantidad=listadoFamiliares.get(i).getAcompañantes();
				cantidadFamiliares=cantidadFamiliares+cantidad;
			}
		}
		
		for (int i=0; i<listadoCrematorios.size(); i++) {
			if (listadoCrematorios.get(i).getAfiliacion()==afiliacion & listadoCrematorios.get(i).getSillas()==cantidadFamiliares) {
				crematoriosDisponibles.add(listadoCrematorios.get(i));
			}
		}
		return crematoriosDisponibles;
	}
	
	
	
	
	
	//metodos get y set
	
	

	public CuentaBancaria getCuentaAhorros() {
		return cuentaAhorros;
	} 
	public void setCuentaBancaria(CuentaBancaria cuentaAhorros) {
		this.cuentaAhorros=cuentaAhorros;
	}
	public ArrayList<Empleado> getlistadoEmpleados() {
		return listadoEmpleados;
	} 
	public void setlistadoEmpleados(ArrayList<Empleado> listadoEmpleados) {
		this.listadoEmpleados=listadoEmpleados;
	}
	public ArrayList<Crematorio> getlistadoCrematorios() {
		return listadoCrematorios;
	} 
	public void setlistadoCrematorios(ArrayList<Crematorio> listadoCrematorios) {
		this.listadoCrematorios=listadoCrematorios;
	}
	public ArrayList<Cementerio> getlistadoCementerios() {
		return listadoCementerios;
	} 
	public void setlistadoCementerios(ArrayList<Cementerio> listadoCementerios) {
		this.listadoCementerios=listadoCementerios;
	}
	
	public ArrayList<Factura> getlistadoFacturas() {
		return listadoFacturas;
	} 
	public void setlistadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
	
	
}
