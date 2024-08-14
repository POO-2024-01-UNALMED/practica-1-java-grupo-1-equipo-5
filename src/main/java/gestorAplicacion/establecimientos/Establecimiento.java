package gestorAplicacion.establecimientos;

import java.util.ArrayList;

import java.time.LocalTime;
import java.util.Random;

import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;

public class Establecimiento {
	private String nombre;
	private String ubicacion;
	private int capacidad;
	private CuentaBancaria cuentaCorriente;
	private String afiliacion;
	private Empleado Jefe;
	private Funeraria funeraria;
	protected ArrayList<Cliente> clientes=new ArrayList<Cliente>();  
	public static ArrayList<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
	private double calificacion=5;
	private ArrayList<Empleado> empleados=new ArrayList<Empleado>();
	private ArrayList<Vehiculo> Vehiculos=new ArrayList<Vehiculo>();
	protected ArrayList<LocalTime> horarioEventos=new ArrayList<>();
	private String descripcionCalificacion;
	private ArrayList<Vehiculo> vehiculosEnVenta =new ArrayList<>();

	private LocalTime horaEvento;
	private Iglesia iglesia;
	
	

	private ArrayList<Producto> productos = new ArrayList<Producto>();
	private ArrayList<Establecimiento> listadoProveedores=new ArrayList<Establecimiento>();
	
	
	
	
	
	
	//Constructor a ser llamado desde las clases Crematorio y Cementerio
	public Establecimiento(String nombre, String ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado, Funeraria funeraria ) {
		this.nombre=nombre;
		this.ubicacion=ubicacion;
		this.capacidad=capacidad;
		this.cuentaCorriente=cuentaCorriente;
		this.afiliacion=afiliacion;
		this.Jefe=empleado;
		this.funeraria=funeraria;
		establecimientos.add(this);
	}
	
	//Constructor a ser llamado desde clase Funeraria
	public Establecimiento (String nombre, CuentaBancaria cuentaCorriente) {
		this.nombre=nombre;
		this.cuentaCorriente=cuentaCorriente;
		establecimientos.add(this);
	}
	
	//Este método sirve para clasificar por el tipo más específico de los objetos que son o heredan de la clase Establecimiento
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
	
	//buscar por funeraria  //Devuelve el tipo de establecimiento más específico de acuerdo a la funeraria 
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
	
	
	public ArrayList<Cliente> buscarCliente(String adultoNiño) {
		
		ArrayList<Cliente> clientesEdad= new ArrayList<Cliente>();
		if(adultoNiño=="adulto") {
			for(Cliente cliente: this.clientes) {
				if(cliente.getCC()!=0) {
					clientesEdad.add(cliente);
				}//fin if
			}//fin for
		}else {
			for(Cliente cliente: this.clientes) {
				if(cliente.getCC()==0) {
					clientesEdad.add(cliente);
				}//fin if
			}//fin for
		}//fin else
		
		return clientesEdad;
	}
	
	public void agregarCliente(Cliente cliente) {
		this.clientes.add(cliente);
	}
	public void eliminarCliente(Cliente cliente) {
		this.clientes.remove(cliente);
	}
	
	
	
	//busca a un cliente en todas las funerarias por su atributo CC
	public static Cliente examinarCliente(long CC) {
		
		ArrayList<Establecimiento> funerarias= Establecimiento.filtarEstablecimiento("funeraria");
		Cliente cliente=null;
		
		for(Establecimiento auxFuneraria: funerarias) {
			Funeraria funeraria= (Funeraria) auxFuneraria;
			for(Cliente auxCliente: funeraria.buscarCliente("adulto")) {
				if(auxCliente.getCC()==CC) {
					return cliente=auxCliente;
				}//fin if
			}//fin for
			
		}
		return cliente;	
	
		
	}
	// Añadir Veiculos
	public void agregarVehiculo(Vehiculo vehiculo) {
        Vehiculos.add(vehiculo);
    }
	
	//Filtrar Vehiculos que Esten disponibles y sin conductor
	public ArrayList<Vehiculo> filtrarVehiculosSinConductorYDisponibles() {
        ArrayList<Vehiculo> vehiculosFiltrados = new ArrayList<>();
        for (Vehiculo vehiculo : Vehiculos) {
            if (vehiculo.getConductor() == null && vehiculo.isEstado()) {
                vehiculosFiltrados.add(vehiculo);
            }
        }
        return vehiculosFiltrados;
    }
	
	public void generarHoras() {
		
		Random random=new Random();
		String cargo =null;
		int horaMin=0;
		int horaMax=0;
		
		if(this instanceof Crematorio) {
			cargo="cremador";
		}else if(this instanceof Cementerio) {
			cargo="sepulturero";
		}
		
		if(this.getFuneraria().buscarEmpleados("mañana", cargo).size()!=0) {
			horaMin=6;
			horaMax=14;
		}else if(this.getFuneraria().buscarEmpleados("tarde", cargo).size()!=0){
			if(horaMin==0) {horaMin=15; horaMax=22;}
			else {horaMax=22;}
		}else if(this.getFuneraria().buscarEmpleados("noche", cargo).size()!=0) {
			if(horaMin==0) {horaMax=30; horaMin=23;}
			else if(horaMin==15) {horaMax=30;}
			else if(horaMax==14) {horaMin=23; horaMax=38;}
			else {horaMin=0; horaMax=23;}
			
		}
		
	
		for(int a=0;a<3;a++) {
			// Generar horas y minutos aleatorios 
        	int horas= random.nextInt(horaMax - horaMin + 1) + horaMin; // Horas entre horasMin y horasMax
            if(horas>23) {horas-=24;}
            int minutos = random.nextInt(60); // Minutos entre 0 y 59
	        
	        // Crear una instancia de LocalTime con la hora y minutos aleatorios
            LocalTime horaGenerada = LocalTime.of(horas, minutos);
            
            
            this.horarioEventos.add(horaGenerada);
	            
			}
	
	}
	
	public void agregarIglesia(Iglesia iglesia) {
		this.iglesia=iglesia;
	}
	public void eliminarHorario(LocalTime hora) {
		this.horarioEventos.remove(hora);
	}
	// Método para verificar si el establecimiento tiene un producto por nombre
    public boolean tieneProducto(String nombreProducto) {
        for (Producto producto : productos) {
            if (producto.getNombre().equals(nombreProducto)) {
                return true;
            }
        }
        return false;
    }
	
	
	public String toString() {
		return nombre;
	}
	
	
	

	
	//metodos get y set
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setubicacion(String ubicacion) {
		this.ubicacion=ubicacion;
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
	public Empleado getEmpleado() {
		return Jefe;
	}
	public void setEmpleado(Empleado empleado) {
		Jefe=empleado;
	}
	public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void agregarEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
    }
	public ArrayList<LocalTime> getHorarioEventos(){
		return horarioEventos;
	}
	
	public LocalTime getHoraEvento() {
		return horaEvento;
	}
	public void setHoraEvento(LocalTime horaEvento) {
		this.horaEvento=horaEvento;
	}
	public Iglesia getIglesia() {
		return iglesia;
	}
	public void setIglesia(Iglesia iglesia) {
		this.iglesia=iglesia;
	}

	public ArrayList<Vehiculo> getVehiculos() {
		return Vehiculos;
	}

	public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
		Vehiculos = vehiculos;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public ArrayList<Establecimiento> getListadoProveedores() {
		return listadoProveedores;
	}

	public void setListadoProveedores(ArrayList<Establecimiento> listadoProveedores) {
		this.listadoProveedores = listadoProveedores;
	}

	public Empleado getJefe() {
		return Jefe;
	}

	public void setJefe(Empleado jefe) {
		Jefe = jefe;
	}

	public static ArrayList<Establecimiento> getEstablecimientos() {
		return establecimientos;
	}

	public static void setEstablecimientos(ArrayList<Establecimiento> establecimientos) {
		Establecimiento.establecimientos = establecimientos;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public void setEmpleados(ArrayList<Empleado> empleados) {
		this.empleados = empleados;
	}

	public void setHorarioEventos(ArrayList<LocalTime> horarioEventos) {
		this.horarioEventos = horarioEventos;
	}

	public String getDescripcionCalificacion() {
		return descripcionCalificacion;
	}

	public void setDescripcionCalificacion(String descripcionCalificacion) {
		this.descripcionCalificacion = descripcionCalificacion;
	}
	public ArrayList<Vehiculo> getVehiculosEnVenta() {
        return vehiculosEnVenta;
    }

    public void agregarVehiculoEnVenta(Vehiculo vehiculo) {
        vehiculosEnVenta.add(vehiculo);
    }

    public void removerVehiculoEnVenta(Vehiculo vehiculo) {
        vehiculosEnVenta.remove(vehiculo);
    }
	
	
	
	

}
