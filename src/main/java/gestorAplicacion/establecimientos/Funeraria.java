package gestorAplicacion.establecimientos;

import java.util.ArrayList;


import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;
import gestorAplicacion.establecimientos.*;

public class Funeraria extends Establecimiento{

	
	private static CuentaBancaria cuentaAhorros;
	private ArrayList<Empleado> empleados=new ArrayList<Empleado>();
	//private ArrayList<Crematorio> crematorios=new ArrayList<Crematorio>();
	//private ArrayList<Cementerio> cementerios=new ArrayList<Cementerio>();
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	private List<Producto> inventario;
    private List<Proveedor> proveedores;
    
	//hereda inventario
	
	
	//Constructor
	public Funeraria(String nombre, CuentaBancaria cuentaCorriente, CuentaBancaria cuentaAhorros) {
		super(nombre,cuentaCorriente);
		this.cuentaAhorros=cuentaAhorros;
		
	}
	
	
	public ArrayList<Establecimiento> buscarEstablecimientos(String tipoEstablecimiento,Cliente cliente){
		
		ArrayList<Establecimiento> establecimientosEvaluar= Establecimiento.buscarPorFuneraria(this, tipoEstablecimiento);
		
		ArrayList<Establecimiento> establecimientosDisponibles=new ArrayList<Establecimiento>();
		
		for (int i=0; i<establecimientosEvaluar.size(); i++) {
			
			
			Establecimiento establecimiento=establecimientosEvaluar.get(i);
			
			
			if(establecimiento.getAfiliacion() == cliente.getAfiliacion() & establecimiento.getCapacidad() >= cliente.cantidadFamiliares()) {
				establecimientosDisponibles.add(establecimiento);
			}//fin ciclo if
			
		}//fin ciclo for
		
		return establecimientosDisponibles;
	}
	
	
	public ArrayList<Establecimiento> buscarCementerios(String tipoCementerio,Cliente cliente){
		
		ArrayList <Establecimiento> cementerios = this.buscarEstablecimientos("cementerio", cliente);
		
		ArrayList <Establecimiento> cementeriosDisponibles= Cementerio.cementerioPorTipo(cementerios, tipoCementerio);
		
		return cementeriosDisponibles;
	}
	
	
	
	public ArrayList<Empleado> buscarEmpleados(String cargo, String jornada){
		
		ArrayList<Empleado> disponible=new ArrayList<Empleado>();
		
		
		for(int i=0;i<empleados.size();i++) {
			if(empleados.get(i).getCargo()==cargo & empleados.get(i).getJornada()==jornada) {
	
				disponible.add(empleados.get(i));		
				
			}//fin if	
			
		}//fin ciclo for
		return disponible;
			
	}
	
	//busca al cliente por funeraria y por tipo de Cementerio
	public ArrayList<Cliente> buscarCliente(String tipoCementerio, String adultoNiño){
		
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		ArrayList<Cliente> clientesFiltrados=this.buscarCliente(adultoNiño);
		
		if(tipoCementerio=="cuerpos") {
			for(Cliente cliente: clientesFiltrados) {
				if(cliente.getInventario() instanceof Tumba) {
					clientes.add(cliente);
				}//fin if
			}//fin for
			}else {
				for(Cliente cliente: clientesFiltrados) {
					if(cliente.getInventario() instanceof Urna) {
						clientes.add(cliente);
					}//fin if
				}//fin for
				
			}//fin else
		
		return clientes;
	}
	
	public ArrayList<Cliente> buscarCliente(String adultoNiño) {
		
		ArrayList<Cliente> clientesEdad= new ArrayList<Cliente>();
		if(adultoNiño=="adulto") {
			for(Cliente cliente: clientes) {
				if(cliente.getCC()!=0) {
					clientesEdad.add(cliente);
				}//fin if
			}//fin for
		}else {
			for(Cliente cliente: clientes) {
				if(cliente.getCC()==0) {
					clientesEdad.add(cliente);
				}//fin if
			}//fin for
		}//fin else
		
		return clientesEdad;
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
	
	public ArrayList<Factura> getlistadoFacturas() {
		return listadoFacturas;
	} 
	public void setlistadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
	
	
}
