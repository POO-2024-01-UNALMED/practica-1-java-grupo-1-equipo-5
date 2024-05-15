package gestorAplicacion;

import java.util.ArrayList;

public class Funeraria {
	private String nombre;
	private Inventario inventario;
	private CuentaBancaria cuentaBancaria;
	private ArrayList<Empleado> listadoEmpleados=new ArrayList<Empleado>();
	private ArrayList<Crematorio> listadoCrematorios=new ArrayList<Crematorio>();
	private ArrayList<Cementerio> listadoCementerios=new ArrayList<Cementerio>();
	private ArrayList<Establecimiento> listadoProveedores=new ArrayList<Establecimiento>();
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	
	
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
	
	public String getNombre() {
		return nombre;
	} 
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public Inventario getInventario() {
		return inventario;
	} 
	public void setInventario(Inventario inventario) {
		this.inventario=inventario;
	}
	public CuentaBancaria getCuentaBancaria() {
		return cuentaBancaria;
	} 
	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria=cuentaBancaria;
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
	public ArrayList<Establecimiento> getlistadoEstablecimientos() {
		return listadoProveedores;
	} 
	public void setlistadoEstablecimientos(ArrayList<Establecimiento> listadoProveedores) {
		this.listadoProveedores=listadoProveedores;
	}
	public ArrayList<Factura> getlistadoFacturas() {
		return listadoFacturas;
	} 
	public void setlistadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
	
	
}
