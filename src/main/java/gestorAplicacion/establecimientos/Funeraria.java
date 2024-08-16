package gestorAplicacion.establecimientos;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

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
	
	
	
	public String asignarVehiculo() {
		
		String vehiculosDisponibles="";
		ArrayList<TipoVehiculo> tipoVehiculos = new ArrayList<TipoVehiculo>();
		
		for(Vehiculo vehiculo:vehiculos) {
			
			if(vehiculo.isEstado() && vehiculo.getTipoVehiculo().getFamiliar()) {
				tipoVehiculos.add(vehiculo.getTipoVehiculo());
			}
		}
		
		for(TipoVehiculo vehiculo:TipoVehiculo.values()) {
			
			int cantidadVehiculos = Collections.frequency(tipoVehiculos, vehiculo);
			if(cantidadVehiculos>0) {
				vehiculosDisponibles+="Nombre: "+vehiculo.name()+" Disponibles ("+cantidadVehiculos+") capacidad - "+vehiculo.getCapacidad()+"\n";	
			}
			
		}
		
		if(vehiculosDisponibles.equals("")) {
			return null;
		}
		return vehiculosDisponibles;
		
	}
	
	public Vehiculo buscarTipoVehiculo(TipoVehiculo tipoVehiculo) {
		
		for(Vehiculo vehiculo:vehiculos){
			if(vehiculo.isEstado() && vehiculo.getTipoVehiculo()==tipoVehiculo) {
				vehiculo.setEstado(false);
				return vehiculo;
			}
		}
		
		return null;
	}

	
	
	public void agregarVehiculo(Vehiculo vehiculo) {
		vehiculos.add(vehiculo);
	}
	
	public void agregarEmpleado(Empleado empleado) {
		this.empleados.add(empleado);
	}
	
	public ArrayList<Establecimiento> cementerios(){
		ArrayList<Establecimiento> todosCementerios = filtarEstablecimiento("cementerio");
		ArrayList<Establecimiento> cementerios = new ArrayList<Establecimiento>();
		for(int i = todosCementerios.size() - 1; i >= 0; i--) {
			Establecimiento cementerio = todosCementerios.get(i);
			if(this == cementerio.getFuneraria()) {
				cementerios.add(cementerio);
			}
		}
		return cementerios;
		}
	
	
	
public void cobroServiciosClientes(Cliente cliente) {
		
		for(int i = cliente.getListadoFacturas().size() - 1; i >= 0; i--) {
			Factura factura = cliente.getListadoFacturas().get(i);
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
				            	if(totalFactura <= familiar.getCuentaBancaria().obtenerSaldo()) 
				            		familiar.getCuentaBancaria().transaccionCuentaAhorros(totalFactura, Funeraria.cuentaAhorros);
				    				((Cliente) cliente).getListadoFacturas().remove(factura);
				            	    }
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

public ArrayList<String> cobroFacturas(ArrayList<Factura> facturas) {
    ArrayList<String> resultados = new ArrayList<>();
    int indice = 0;
    for (int i = facturas.size() - 1;i >= 0;i--) {
    	Factura factura = facturas.get(i);
        String tipoServicio = factura.getServicio();
        double totalFactura = factura.getTotal();
       indice ++;;
        String resultado = "";

        if (tipoServicio.equals("vehiculo")) {
            if (totalFactura <= this.getCuentaCorriente().getBolsilloTransporte()) {
                Producto producto = factura.getListaProductos().get(0);
                Establecimiento establecimiento = producto.getEstablecimiento();
                this.getCuentaCorriente().transaccion(totalFactura, establecimiento.getCuentaCorriente(), "bolsilloTransporte");
                System.out.println(totalFactura + indice);
                this.listadoFacturas.add(0,factura);
                this.listadoFacturasPorPagar.remove(i);
                resultado = "Factura #: " + (indice) + ", ID: " + factura.getID() + " pagada con éxito";
                
            } else {
                resultado = "No hay dinero suficiente para pagar la factura #: " + (indice) + ", ID: " + factura.getID();
               
            }
        } else if (tipoServicio.equals("establecimiento")) {       	
            if (totalFactura <= this.getCuentaCorriente().getBolsilloEstablecimientos()) {
                Producto producto = factura.getListaProductos().get(0);
                Establecimiento establecimiento = producto.getEstablecimiento();
                this.getCuentaCorriente().transaccion(totalFactura, establecimiento.getCuentaCorriente(), "bolsilloEstablecimientos");
                this.listadoFacturas.add(0,factura);
                this.listadoFacturasPorPagar.remove(i);
                resultado = "Factura #: " + (indice) + ", ID: " + factura.getID() + " pagada con éxito";
                
            } else {
                resultado = "No hay dinero suficiente para pagar la factura #: " + (indice) + ", ID: " + factura.getID();
                
            }
        } else if (tipoServicio.equals("empleado")) {
            if (totalFactura <= this.getCuentaCorriente().getBolsilloTrabajadores()) {
                Producto producto = factura.getListaProductos().get(0);
                Establecimiento establecimiento = producto.getEstablecimiento();
                this.getCuentaCorriente().transaccion(totalFactura, establecimiento.getCuentaCorriente(), "bolsilloTrabajadores");
                this.listadoFacturas.add(0,factura);
                this.listadoFacturasPorPagar.remove(i);
                resultado = "Factura #: " + (indice) + ", ID: " + factura.getID() + " pagada con éxito";
               
            } else {
                resultado = "No hay dinero suficiente para pagar la factura #: " + (indice) + ", ID: " + factura.getID();
           
            }
        } else if (tipoServicio.equals("inventario")) {
            if (totalFactura <= this.getCuentaCorriente().getBolsilloInventario()) {
                Producto producto = factura.getListaProductos().get(0);
                Establecimiento establecimiento = producto.getEstablecimiento();
                this.getCuentaCorriente().transaccion(totalFactura, establecimiento.getCuentaCorriente(), "bolsilloInventario");
                this.listadoFacturas.add(0,factura);
                this.listadoFacturasPorPagar.remove(i);
                resultado = "Factura #: " + (indice) + ", ID: " + factura.getID() + " pagada con éxito";
            
            } else {
                resultado = "No hay dinero suficiente para pagar la factura #: " + (indice) + ", ID: " + factura.getID();
                }
        }

        resultados.add(resultado);
    }
    return resultados;
}
    			 
    public String informeGastosFacturas() {
    	double bolsilloInventario = 0;
    	int facturasInventario = 0;
    	double bolsilloTransporte = 0;
    	int facturasTransporte = 0;
    	double bolsilloEstablecimientos = 0;
    	int facturasEstablecimientos = 0;
    	double bolsilloPagoCredito = 0;
    	int facturasPagoCredito = 0;
    	double bolsilloTrabajadores = 0;
    	int facturasTrabajadores = 0;
    	for(int i = 0;i < this.listadoFacturas.size();i++) {
    		Factura factura = this.listadoFacturas.get(i);
    		if(factura.getServicio().equals("bolsilloInventario")) {
    			bolsilloInventario += factura.getTotal();
    			facturasInventario++;
    		}else if(factura.getServicio().equals("bolsilloTransporte")) {
    			bolsilloTransporte += factura.getTotal();
    			facturasTransporte++;
    		}else if(factura.getServicio().equals("bolsilloEstablecimientos")) {
    			bolsilloEstablecimientos += factura.getTotal();
    			facturasEstablecimientos++;
    		}else if(factura.getServicio().equals("bolsilloTrabajadores")) {
    			bolsilloTrabajadores += factura.getTotal();
    			bolsilloTrabajadores++;
    		}if(factura.getServicio().equals("bolsilloPagoCredito")) {
    			bolsilloPagoCredito += factura.getTotal();
    			facturasPagoCredito++;
    		}
    		
    	} return "Informe de gastos:"+"\n"+
    	"Facturas inventario: " + facturasInventario+"\n"+
    	"Gastos inventario: " + bolsilloInventario+"\n"+
    	"Facturas inventario: " + facturasTransporte+"\n"+
    	 "Gastos inventario: " + bolsilloTransporte+"\n"+
         "Facturas inventario: " + facturasEstablecimientos+"\n"+
         "Gastos inventario: " + bolsilloEstablecimientos+"\n"+
    	 "Facturas inventario: " + facturasTrabajadores+"\n"+
    	 "Gastos inventario: " + bolsilloTrabajadores+"\n"+
         "Facturas inventario: " + facturasPagoCredito+"\n"+
    	 "Gastos inventario: " + bolsilloPagoCredito;
    }
    	 
    	 

public String pagoTrabajadores(Empleado empleado) {
    	 
    	 int trabajos = empleado.getTrabajosHechos();
    	 
    	 int calificacion = empleado.getCalificacion();
    	 
    	 double paga = empleado.getSalario();
    	 if(trabajos >= 2 && 5 >= trabajos) {
    		  paga *= 1;
    		  if(calificacion == 5) {
    		    	 paga += (paga*0.05);
    		     }
    		     
    		     this.getCuentaCorriente().transaccion(paga, empleado.getCuentaBancaria(), "bolsilloTrabajadores");
    		     empleado.setTrabajosHechos(0);
    		     return "El trabajador ha hecho: " + trabajos +" trabajos,"+ "\n" +
    		     "Y tiene una calificacion de: "  + calificacion + "\n" +
    		     "por lo que obtuvo una paga de: " + paga;
    		  
    	}   else if(trabajos > 5 && trabajos <= 9) {
    	     paga += (paga*0.02);
    	     if(calificacion == 5) {
    	    	 paga += (paga*0.05);
    	     }
    	     
    	     this.getCuentaCorriente().transaccion(paga, empleado.getCuentaBancaria(), "bolsilloTrabajadores");
    	     empleado.setTrabajosHechos(0);
    	     return "El trabajador ha hecho: " + trabajos +" trabajos,"+ "\n" +
    	     "Y tiene una calificacion de: "  + calificacion + "\n" +
		     "por lo que obtuvo una paga de: " + paga;}
    	 else if(trabajos > 9 ) {
    	     paga +=(paga*0.04);
    	     if(calificacion == 5) {
    	    	 paga += (paga*0.05);
    	     }
    	     
    	     this.getCuentaCorriente().transaccion(paga, empleado.getCuentaBancaria(), "bolsilloTrabajadores");
    	     empleado.setTrabajosHechos(0);
    	     return "El trabajador ha hecho: " + trabajos +" trabajos,"+ "\n" +
    	     "Y tiene una calificacion de: "  + calificacion + "\n" +
		     "por lo que obtuvo una paga de: " + paga;
     }else {
    	 return "El trabajador ha hecho: " + trabajos +","+ "\n" +
    		     "por lo que no obtuvo una paga";
     }
  
}
public String pedirCredito() {
	if(this.getCuentaCorriente().getCredito() == null || this.getCuentaCorriente().getCredito().getPorcentajeCreditoPorPagar() <= 0.5) {
	ArrayList<Establecimiento> establecimientos = Funeraria.buscarPorFuneraria(this, "cementerio");
	ArrayList<Establecimiento> establecimient = Funeraria.buscarPorFuneraria(this, "crematorio");
	establecimientos.addAll(establecimient);
	int oro = 0;
	int plata = 0;
	int bronce = 0;
	double montoCredito = 0;
	for(int i = establecimientos.size() - 1; i >= 0; i--) {
		Establecimiento establecimiento = establecimientos.get(i);
		if(establecimiento.getAfiliacion() == "oro") {
			oro++;
		}else if(establecimiento.getAfiliacion() == "plata") {
			plata++;
		}else if(establecimiento.getAfiliacion() == "bronce") {
			bronce++;
		}
	} 
		montoCredito += (oro * 50000);
		montoCredito += (plata * 30000);
		montoCredito += (bronce *10000);
		
		double div = montoCredito / 4;
		this.getCuentaCorriente().depositar(div, "bolsilloTrabajadores");
		this.getCuentaCorriente().depositar(div, "bolsilloTransporte");
		this.getCuentaCorriente().depositar(div, "bolsilloInventario");
		this.getCuentaCorriente().depositar(div, "bolsilloEstablecimientos");
		montoCredito += (this.getCuentaCorriente().getInteres() * montoCredito);
		Factura credito = new Factura("credito", montoCredito, "2024", this, "bolsilloPagoCredito");
		this.getListadoFacturas();
		this.getCuentaCorriente().setCredito(credito);
		return "Credito aceptado";}
	else {
		return "Credito rechazado";}
	}

public String pagarCredito(double porcentaje) {
    if (esPorcentajeValido(porcentaje)) {
        if (tieneCreditoPendiente()) {
            double porcentajeFaltante = getPorcentajeFaltante();
            double valorFaltante = getValorFaltante();

            if (porcentaje <= porcentajeFaltante) {
                return procesarPago(porcentaje, porcentajeFaltante, valorFaltante);
            } else {
                return "El porcentaje es mayor a lo que falta por pagar";
            }
        } else {
            return "No hay crédito que pagar";
        }
    } else {
        return "El porcentaje no es válido";
    }
}

private boolean esPorcentajeValido(double porcentaje) {
    return porcentaje == 1.0 || porcentaje == 0.8 || porcentaje == 0.6 || porcentaje == 0.4 || porcentaje == 0.2;
}

private boolean tieneCreditoPendiente() {
    return this.getCuentaCorriente().getCredito() != null;
}

private double getPorcentajeFaltante() {
    return this.getCuentaCorriente().getCredito().getPorcentajeCreditoPorPagar();
}

private double getValorFaltante() {
    return this.getCuentaCorriente().getCredito().getPrecio();
}

private String procesarPago(double porcentaje, double porcentajeFaltante, double valorFaltante) {
    if (this.getCuentaCorriente().getBolsilloPagoCredito() >= valorFaltante) {
        double pago = calcularPago(porcentaje, valorFaltante);
        this.getCuentaCorriente().retirar(pago, "bolsilloPagoCredito");
        actualizarCredito(porcentajeFaltante, valorFaltante, pago);
        return "Pago exitoso";
    } else {
        return "Dinero insuficiente";
    }
}

private double calcularPago(double porcentaje, double valorFaltante) {
    return valorFaltante * porcentaje;
}

private void actualizarCredito(double porcentajeFaltante, double valorFaltante, double pago) {
    double nuevoPorcentajeFaltante = porcentajeFaltante - pago / valorFaltante;
    double nuevoValorFaltante = valorFaltante - pago;

    if (nuevoPorcentajeFaltante == 0) {
    	Factura credito = this.getCuentaCorriente().getCredito();
    	this.getListadoFacturas().add(credito);
        this.getCuentaCorriente().setCredito(null);
    } else {
        this.getCuentaCorriente().getCredito().setPorcentajeCreditoPorPagar(nuevoPorcentajeFaltante);
        this.getCuentaCorriente().getCredito().setPrecio(nuevoValorFaltante);
    }
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
     
     
     public ArrayList<Establecimiento> gestionEntierro(Cliente cliente,Iglesia iglesia,double estatura) {
    	 
    	 ArrayList<Establecimiento> cementerios =this.buscarCementerios("cuerpos", cliente); 
    	 ArrayList<Establecimiento> cementeriosFiltrados = new ArrayList<Establecimiento>(); 
    	
    		 
    		 for(Establecimiento cementerio:cementerios) {
        		 Cementerio auxCementerio=(Cementerio)cementerio;
        		 //Se crean máximo 3 horarios para cada cementerio 
        		 auxCementerio.generarHoras();
        	
        		 
        		 //Si no hay horarios disponibles o no hay tumbas que cumplan los filtros de disponibilidaInventario el cementerio se elimina
        		 if(auxCementerio.disponibilidadInventario("tumba", estatura, cliente.getEdad()).size()!=0) {
        			 cementeriosFiltrados.add(auxCementerio);
        		 }
        		 
        		 
        	 }//Fin for principal
    		 
    		 if(cementeriosFiltrados.size()==0) {
    			 new Tumba("default",(Cementerio) cementerios.get(0),estatura,cliente.getEdad());
    			 cementeriosFiltrados.add(cementerios.get(0));
    		 }
        	 
        	 //Se recorre cada cementerio filtrado y se cambia el horario del evento, la iglesia y se busca a un empleado para agregarlo
        	 for(Establecimiento cementerio:cementeriosFiltrados) {
        		 Cementerio auxCementerio=(Cementerio)cementerio;
        		 auxCementerio.setHoraEvento(auxCementerio.getHorarioEventos().get(0));
        		 //busca empleado de acuerdo a la hora 
        		 auxCementerio.setEmpleado(this.buscarEmpleados(auxCementerio.getHoraEvento(), "sepulturero").get(0));
        		 auxCementerio.setIglesia(iglesia);
        	 }//Fin For
        	 
     
    		 
    	
    	 
    	 
    	 return cementeriosFiltrados;
    	 
     }
     
     
     public String gestionarTrasnsporte(Cliente cliente, ArrayList<Vehiculo> vehiculos, LocalTime hora) {

 		ArrayList<Familiar> familiares = cliente.getFamiliares();
 		ArrayList<Empleado> conductores = this.buscarEmpleados(hora, "conductor");
 		String gestionTransporte ="Resumen de su transporte - Hora de llegada transporte: "+hora+"\n";
 		
 		int indice=1;
 		while(familiares.size()!=0 & vehiculos.size()!=0) {
 			
 			
 			Vehiculo vehiculoAsignar=vehiculos.get(0);
 			ArrayList<Familiar> pasajeros = vehiculoAsignar.agregarPasajeros(familiares);
 			
 			vehiculoAsignar.setConductor(conductores.get(0));
 		
 			gestionTransporte+="\nVehiculo ["+indice+"]";
 			gestionTransporte+=vehiculoAsignar.productoVehiculo(familiares)+"\n";
 			
 			gestionTransporte+="Conductor: "+vehiculoAsignar.getConductor();
 			
 			if(conductores.size()!=1) {
 				conductores.remove(0);
 			}
 			
 			for(Familiar familiar:pasajeros) {
 				familiares.remove(familiar);
 				
 			}
 			vehiculoAsignar.setEstado(true);
 			vehiculos.remove(0);
 			indice+=1;
 		}
 		
 		return gestionTransporte;
 		

 		
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



	public void setListadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas = listadoFacturas;
	}



	public void setListadoFacturasPorPagar(ArrayList<Factura> listadoFacturasPorPagar) {
		this.listadoFacturasPorPagar = listadoFacturasPorPagar;
	}
	

	
}
