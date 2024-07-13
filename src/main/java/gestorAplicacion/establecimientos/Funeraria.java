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
	
	public ArrayList<Establecimiento> disponibilidadEstablecimiento(String tipoEstablecimiento,Cliente cliente){
		
		ArrayList<Establecimiento> disponible=new ArrayList<>();
	
		int tamaño=0;
		if(tipoEstablecimiento=="cementerio") { 
			tamaño=cementerios.size();
		}else if(tipoEstablecimiento=="crematorio") {
			tamaño=crematorios.size();
		}
	
				for (int i=0; i<tamaño; i++) {
					if (tipoEstablecimiento=="cementerio") {
						disponible.add(cementerios.get(i));
					}else if (tipoEstablecimiento=="crematorio") {
						disponible.add(crematorios.get(i));
					}
					
					Establecimiento establecimiento=disponible.get(disponible.size()-1);
					
					if(establecimiento.getAfiliacion() != cliente.getAfiliacion() || establecimiento.getCapacidad() != cliente.cantidadFamiliares()) {
						disponible.remove(disponible.get(disponible.size()-1));
					}//fin cilo if
					
				}//fin ciclo for
				
				return disponible;
		
	}
	
	public ArrayList<Empleado> disponibilidadEmpleados(String cargo, String jornada){
		
		ArrayList<Empleado> disponible=new ArrayList<Empleado>();
		
		
		for(int i=0;i<empleados.size();i++) {
			if(empleados.get(i).getCargo()==cargo & empleados.get(i).getJornada()==jornada) {
	
				disponible.add(empleados.get(i));		
				
			}//fin if	
			
		}//fin ciclo for
		return disponible;
			
	}
	
	
	//metodos get y set
	
	

	public CuentaBancaria getCuentaAhorros() {
		return cuentaAhorros;
	} 
	public void setCuentaBancaria(CuentaBancaria cuentaAhorros) {
		this.cuentaAhorros=cuentaAhorros;
	}
	public ArrayList<Empleado> getEmpleados() {
		return empleados;
	} 
	public void setEmpleados(ArrayList<Empleado> empleados) {
		this.empleados=empleados;
	}
	public ArrayList<Crematorio> getCrematorios() {
		return crematorios;
	} 
	public void setCrematorios(ArrayList<Crematorio> crematorios) {
		this.crematorios=crematorios;
	}
	public ArrayList<Cementerio> getCementerios() {
		return cementerios;
	} 
	public void setCementerios(ArrayList<Cementerio> cementerios) {
		this.cementerios=cementerios;
	}
	
	public ArrayList<Factura> getlistadoFacturas() {
		return listadoFacturas;
	} 
	public void setlistadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
	
	
}
