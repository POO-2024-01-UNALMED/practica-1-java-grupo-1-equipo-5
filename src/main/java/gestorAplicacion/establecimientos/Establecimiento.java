package gestorAplicacion.establecimientos;

import java.util.ArrayList;

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
	
	//Constructor a ser llamado desde para clase Funeraria
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
            if (vehiculo.getConductor() == null && vehiculo.isDisponible()) {
                vehiculosFiltrados.add(vehiculo);
            }
        }
        return vehiculosFiltrados;
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
	

}
