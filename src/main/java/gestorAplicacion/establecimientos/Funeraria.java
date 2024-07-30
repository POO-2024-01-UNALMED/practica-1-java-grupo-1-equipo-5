package gestorAplicacion.establecimientos;

import java.util.ArrayList;


import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;
import gestorAplicacion.establecimientos.*;

public class Funeraria extends Establecimiento{

	
	private static CuentaBancaria cuentaAhorros;
	private ArrayList<Empleado> empleados=new ArrayList<Empleado>();
	
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	private ArrayList<Vehiculo> vehiculos=new ArrayList<Vehiculo>();
    
    
	//hereda inventario
	
	
	//Constructor
	public Funeraria(String nombre, CuentaBancaria cuentaCorriente, CuentaBancaria cuentaAhorros) {
		super(nombre,cuentaCorriente);
		this.cuentaAhorros=cuentaAhorros;
		
	}
	
	//Sirve para buscar establecimientos de tipo Cementerio o Crematorio que estén asociados a la funeraria y que concuerden con el atributo afiliacón y acompañantes del cliente 
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
	
	//Sirve para buscar el cementerio adecuado según su atributo tipo que puede ser "cenizas" o "cuerpo"
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
				if(cliente.getTumba() != null) {
					clientes.add(cliente);
				}//fin if
			}//fin for
			}else {
				for(Cliente cliente: clientesFiltrados) {
					if(cliente.getUrna() != null ) {
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
	
	public Cliente buscarCliente(long IDcliente) {
		for(Cliente auxCliente: clientes) {
			if(auxCliente.getCC()==IDcliente) {
				return auxCliente;
			}//Fin if
		}//Fin for
	return null;
	}
	
	
	
public ArrayList<Vehiculo> asignarVehiculo(String clienteFamiliar) {
		
		ArrayList<Vehiculo> vehiculosDisponibles=new ArrayList<Vehiculo>();
		
		if(clienteFamiliar=="cliente") {
			for(Vehiculo auxVehiculo: vehiculos) {
				TipoVehiculo vehiculo=auxVehiculo.getTipoVehiculo();
				if(vehiculo.getCliente()) {
					vehiculosDisponibles.add(auxVehiculo);
				}
			}
		}else {
			for(Vehiculo auxVehiculo: vehiculos) {
				TipoVehiculo vehiculo=auxVehiculo.getTipoVehiculo();
				if(vehiculo.getFamiliar()) {
					vehiculosDisponibles.add(auxVehiculo);
				}//fin if
		}//fin for
	}//fin else
		
		return vehiculosDisponibles;
		
	}

	public void agregarVehiculo(Vehiculo vehiculo) {
		vehiculos.add(vehiculo);
	}
	
	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
	}
	
public void cobroServiciosClientes(Funeraria funeraria) {
		
		for(Factura factura: listadoFacturas) {
			
			double totalFactura = factura.getTotal();
			Persona cliente = factura.getCliente(); 
			
			if(totalFactura <= cliente.getCuentaBancaria().getSaldo() && cliente.getEdad() >= 18) {
				
				cliente.getCuentaBancaria().transaccion(totalFactura, Funeraria.cuentaAhorros);
				((Cliente) cliente).getListadoFacturas().remove(factura);
			}else {
				for(Persona persona : ((Cliente) cliente).getFamiliares()) {
					
					if (persona instanceof Familiar) {
						Familiar familiar = (Familiar) persona;
				        if (familiar.getParentesco() != null) {
				            if("conyugue".equals(familiar.getParentesco()) && familiar.getEdad() >= 18){
				            	if(totalFactura <= familiar.getCuentaBancaria().getSaldo()) {
				            		familiar.getCuentaBancaria().transaccion(totalFactura, Funeraria.cuentaAhorros);
				    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				            	
				        }else if("hijo".equals(familiar.getParentesco()) || "hija".equals(familiar.getParentesco())  && familiar.getEdad() >= 18) {
				        	if(totalFactura <= familiar.getCuentaBancaria().getSaldo()) {
			            		familiar.getCuentaBancaria().transaccion(totalFactura, Funeraria.cuentaAhorros);
			    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				        	
				        }else if("padre".equals(familiar.getParentesco()) || "madre".equals(familiar.getParentesco()) && familiar.getEdad() >= 18) {
				        	if(totalFactura <= familiar.getCuentaBancaria().getSaldo()) {
			            		familiar.getCuentaBancaria().transaccion(totalFactura, Funeraria.cuentaAhorros);
			    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				      
				        }else if("hermano".equals(familiar.getParentesco()) || "hermana".equals(familiar.getParentesco()) && familiar.getEdad() >= 18) {
				        	if(totalFactura <= familiar.getCuentaBancaria().getSaldo()) {
			            		familiar.getCuentaBancaria().transaccion(totalFactura, Funeraria.cuentaAhorros);
			    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				        
				        }else { if(totalFactura <= familiar.getCuentaBancaria().getSaldo()) {
		            		familiar.getCuentaBancaria().transaccion(totalFactura, Funeraria.cuentaAhorros);
		    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				        	
				        }
					}
				}
			}
		}
	}
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
