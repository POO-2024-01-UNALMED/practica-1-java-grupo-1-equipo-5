package gestorAplicacion.establecimientos;

import java.time.LocalTime;
import java.util.ArrayList;


import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;
import gestorAplicacion.establecimientos.*;

public class Funeraria extends Establecimiento{

	
	private static CuentaBancaria cuentaAhorros;
	private ArrayList<Empleado> empleados=new ArrayList<Empleado>();
	
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	private ArrayList<Factura> listadoFacturasPorPagar=new ArrayList<Factura>();
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
	
	
	
	public ArrayList<Empleado> buscarEmpleados(String jornada, String cargo){
		
		ArrayList<Empleado> disponible=new ArrayList<Empleado>();
		
		
		for(int i=0;i<empleados.size();i++) {
			Empleado empleado=empleados.get(i);
			if((empleado.getJornada()).equalsIgnoreCase(jornada) & (empleado.getCargo()).equals(cargo)) {
				disponible.add(empleados.get(i));
			}
		
			//fin if	
			
		}//fin ciclo for
		return disponible;
			
	}
	
	public ArrayList<Empleado> buscarEmpleados(LocalTime horas,String cargo){
		
		String jornada;
		if(horas.getHour()<=14 & horas.getHour()>=6 ) {
			jornada="mañana";
		}else if(horas.getHour()<=22 & horas.getHour()>=15) {
			jornada="tarde";
		}else {
			jornada="noche";
		}
		
		return this.buscarEmpleados(jornada, cargo);
	}
	
	
	
	
	
	
	//busca al cliente por funeraria y por tipo de Cementerio
	public ArrayList<Cliente> buscarCliente(String tipoCementerio, String adultoNiño){
		
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
		//Cementerios asociados a la funeraria clasificados por tipo (cenizas o cuerpo)
		ArrayList<Establecimiento> cementerios=Cementerio.cementerioPorTipo(Establecimiento.buscarPorFuneraria(this, "cementerio"), tipoCementerio);
		
		for (Establecimiento auxCementerio:cementerios) {
			Cementerio cementerio=(Cementerio) auxCementerio;
			clientes.addAll(cementerio.buscarCliente(adultoNiño));
			
		}
		
		return clientes;
	}
	
	
	
	public Cliente buscarCliente(long IDcliente) {
		for(Cliente auxCliente: this.clientes) {
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
	
	public void agregarEmpleado(Empleado empleado) {
		this.empleados.add(empleado);
	}
	
	
public void cobroServiciosClientes(Cliente cliente) {
		
		for(Factura factura: cliente.getListadoFacturas()) {
			
			double totalFactura = factura.getTotal();
			 
			if(totalFactura <= cliente.getCuentaBancaria().obtenerSaldo() && cliente.getEdad() >= 18) {
				
				cliente.getCuentaBancaria().transaccionCuentaAhorros(totalFactura, Funeraria.cuentaAhorros);
				((Cliente) cliente).getListadoFacturas().remove(factura);
			}else {
				for(Persona persona : ((Cliente) cliente).getFamiliares()) {
					
					if (persona instanceof Familiar) {
						Familiar familiar = (Familiar) persona;
				        if (familiar.getParentesco() != null) {
				            if("conyuge".equals(familiar.getParentesco()) && familiar.getEdad() >= 18){
				            	if(totalFactura <= familiar.getCuentaBancaria().obtenerSaldo()) {
				            		familiar.getCuentaBancaria().transaccionCuentaAhorros(totalFactura, Funeraria.cuentaAhorros);
				    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				            	
				        }else if("hijo".equals(familiar.getParentesco()) || "hija".equals(familiar.getParentesco())  && familiar.getEdad() >= 18) {
				        	if(totalFactura <= familiar.getCuentaBancaria().obtenerSaldo()) {
			            		familiar.getCuentaBancaria().transaccionCuentaAhorros(totalFactura, Funeraria.cuentaAhorros);
			    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				        	
				        }else if("padre".equals(familiar.getParentesco()) || "madre".equals(familiar.getParentesco()) && familiar.getEdad() >= 18) {
				        	if(totalFactura <= familiar.getCuentaBancaria().obtenerSaldo()) {
			            		familiar.getCuentaBancaria().transaccionCuentaAhorros(totalFactura, Funeraria.cuentaAhorros);
			    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				      
				        }else if("hermano".equals(familiar.getParentesco()) || "hermana".equals(familiar.getParentesco()) && familiar.getEdad() >= 18) {
				        	if(totalFactura <= familiar.getCuentaBancaria().obtenerSaldo()) {
			            		familiar.getCuentaBancaria().transaccionCuentaAhorros(totalFactura, Funeraria.cuentaAhorros);
			    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				        
				        }else { if(totalFactura <= familiar.getCuentaBancaria().obtenerSaldo() && familiar.getEdad() >= 18) {
		            		familiar.getCuentaBancaria().transaccionCuentaAhorros(totalFactura, Funeraria.cuentaAhorros);
		    				((Cliente) cliente).getListadoFacturas().remove(factura);}
				        	
				        }
					}
				}
			}
		}
	}
}

     public void cobroFacturas() {
    	 
    	 for(Factura factura : listadoFacturas) {
    		 
    		 String tipoServicio = factura.getServicio();
    		 double totalFactura = factura.getTotal();
    		 
    			 Producto producto = factura.getListaProductos().get(0);
    			 Establecimiento establecimiento = producto.getEstablecimiento();
    			  this.getCuentaCorriente().transaccion(totalFactura, establecimiento.getCuentaCorriente(), "bolsilloInventario");}
    			 
    		
    	 
    	 
     }
public void pagoTrabajadores(Empleado empleado) {
    	 
    	 int trabajos = empleado.getTrabajosHechos();
    	 
    	 int calificacion = empleado.getCalificacion();
    	 
    	 double paga = empleado.getSalario();
    	 if(trabajos >= 2 && 5 >= trabajos) {
    		  paga *= 1;
    	}   else if(trabajos > 5 && trabajos <= 9) {
    	     paga += (paga*0.02);}
    	 else if(trabajos > 9 ) {
    	     paga +=(paga*0.04);
    	     
     }if(calificacion == 5) {
    	 paga += (paga*0.05);
     }
     
     this.getCuentaCorriente().transaccion(paga, empleado.getCuentaBancaria(), "bolsilloTrabajadores");
    	 
     

}

     public static Producto[] identificarProductosFaltantes(Funeraria funeraria) {
         Producto[] productosVendidos = calcularProductosVendidos(funeraria);
         Producto[] productosFaltantes = new Producto[0];

         for (Producto producto : productosVendidos) {
             if (producto.getCantidadVendida() < 10) {
                 productosFaltantes = agregarProducto(productosFaltantes, producto);
             }
         }

         return productosFaltantes;
     }
     
     public static Producto[] calcularProductosVendidos(Funeraria fun) {
         Producto[] productosVendidos = new Producto[0];
         for (Factura factura : fun.getListadoFacturas()) {
             for (Producto producto : factura.getListaProductos()) {
                 productosVendidos = agregarProducto(productosVendidos, producto);
             }
         }
         return productosVendidos;
     }
     
     
     public static Producto[] agregarProducto(Producto[] productosVendidos, Producto nuevoProducto) {
    	    // Recorre el array de productos vendidos para encontrar si el producto ya existe
    	    for (int i = 0; i < productosVendidos.length; i++) {
    	        // Compara el nombre del producto existente con el nuevo producto
    	        if (productosVendidos[i].getNombre().equals(nuevoProducto.getNombre())) {
    	            // Si el producto ya existe, actualiza la cantidad vendida
    	        	int cantidadActual = productosVendidos[i].getCantidadVendida();
    	            productosVendidos[i].setCantidadVendida(cantidadActual + nuevoProducto.getCantidadVendida());
    	            return productosVendidos;
    	        }
    	    }

    	    // Si el producto no existe en el array, se crea un nuevo array con un tamaño mayor
    	    Producto[] nuevosProductosVendidos = new Producto[productosVendidos.length + 1];
    	    // Copia todos los productos del array original al nuevo array
    	    System.arraycopy(productosVendidos, 0, nuevosProductosVendidos, 0, productosVendidos.length);
    	    // Añade el nuevo producto al final del nuevo array
    	    nuevosProductosVendidos[productosVendidos.length] = new Producto(nuevoProducto.getNombre(), nuevoProducto.getCantidadVendida());
    	    // Retorna el nuevo array con el producto añadido
    	    return nuevosProductosVendidos;
    	}
     
     
     public ArrayList<Establecimiento> gestionEntierro(Cliente cliente,Iglesia iglesia,LocalTime hora,double estatura) {
    	 
    	 ArrayList<Establecimiento> cementerios =this.buscarCementerios("cuerpos", cliente);     
    	 
    	 
    	 for(Establecimiento cementerio:cementerios) {
    		 Cementerio auxCementerio=(Cementerio)cementerio;
    		 //Se crean máximo 3 horarios para cada cementerio 
    		 auxCementerio.generarHoras();
    		 //Se recorre por cada uno de los horarios generados para filtrar qué horarios están después de la hora que se decidió hacer la misa
    		 for (LocalTime auxHora:auxCementerio.getHorarioEventos() ) {
    			 //Si la hora es antes de que la ceremonia termine se elimina el horario
    			 if(auxHora.isBefore(iglesia.duracionEvento(hora))){
    				 auxCementerio.eliminarHorario(auxHora);
    			 }//Fin if
    		 }//Fin for
    		 //Si no hay horarios disponibles o no hay tumbas que cumplan los filtros de disponibilidaInventario el cementerio se elimina
    		 if(auxCementerio.getHorarioEventos().size()==0 || auxCementerio.disponibilidadInventario("tumba", estatura, cliente.getEdad()).size()==0) {
    			 cementerios.remove(auxCementerio);
    		 }
    		 
    	 }//Fin for principal
    	 
    	 //Se recorre cada cementerio filtrado y se cambia el horario del evento, la iglesia y se busca a un empleado para agregarlo
    	 for(Establecimiento cementerio:cementerios) {
    		 Cementerio auxCementerio=(Cementerio)cementerio;
    		 auxCementerio.setHoraEvento(getHorarioEventos().get(0));
    		 //busca empleado de acuerdo a la hora 
    		 auxCementerio.setEmpleado(this.buscarEmpleados(cementerio.getHoraEvento(), "sepulturero").get(0));
    		 auxCementerio.setIglesia(iglesia);
    	 }//Fin For
    	 
    	 
    	 return cementerios;
    	 
     }
     
     
     public void gestionarTrasnsporte(Cliente cliente) {
 		ArrayList<Vehiculo> vehiculos =this.vehiculos;
 		ArrayList<Persona> familiaresMenores = new ArrayList<Persona>();
 		ArrayList<Persona> familiaresMayores = new ArrayList<Persona>();
 		
 		for(Persona familiar: cliente.getFamiliares()) {
 			if(familiar.getCC()==0) {
 				familiaresMenores.add(familiar);
 			}else {familiaresMayores.add(familiar);}
 			
 		}//Fin for
 		
 		
 		
 		
 		
 		
     
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
	
	public ArrayList<Factura> getListadoFacturas() {
		return listadoFacturas;
	} 
	public void setlistadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
	public  ArrayList<Factura> getListadoFacturasPorPagar() {
		return listadoFacturasPorPagar;
	} 
	public void setlistadoFacturasPorPagar(ArrayList<Factura> listadoFacturasPorPagar) {
		this.listadoFacturasPorPagar=listadoFacturasPorPagar;
	}

	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}

	public static void setCuentaAhorros(CuentaBancaria cuentaAhorros) {
		Funeraria.cuentaAhorros = cuentaAhorros;
	}

	
}
