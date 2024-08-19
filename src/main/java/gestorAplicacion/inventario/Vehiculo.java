package gestorAplicacion.inventario;


import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.personas.Persona;

import java.io.Serializable;
import java.util.ArrayList;
import gestorAplicacion.establecimientos.Funeraria;


public class Vehiculo implements Serializable{
	private TipoVehiculo tipoVehiculo; //Es un Enum
	private Funeraria funeraria;
	// private String modelo; Este se puede reemplazar pot tipoVehículo
    private String color;
    private Boolean estado=true; // true si está disponible, false si no lo está
	private String placa;
	private Empleado conductor;
	private ArrayList<String> ruta=new ArrayList<String>();
	private int Precio;
	private static final long serialVersionUID = 1L;

	private ArrayList<Familiar> pasajeros = new ArrayList<Familiar>();
	
	private static ArrayList<Vehiculo> vehiculos =new ArrayList<Vehiculo>();


	private int Capacidad;// Es para la funcionalidad inventario y me sirve como espacio para unas cosas


	
	
	// Constructor
	public Vehiculo(TipoVehiculo tipoVehiculo,Funeraria funeraria,String color, String placa, int Precio, int Capacidad) {
		this.tipoVehiculo=tipoVehiculo;
		this.funeraria=funeraria;
		this.color = color;
		this.placa = placa;
		this.Precio= Precio;
		this.Capacidad= Capacidad;
		funeraria.agregarVehiculo(this);
		vehiculos.add(this);
		}
	// Constructor con capacidad por si nesecitan el otro constructor

	public Vehiculo(TipoVehiculo tipoVehiculo,Funeraria funeraria,String color, String placa, int capacidad) {
		this.tipoVehiculo=tipoVehiculo;
		this.funeraria=funeraria;
		this.color = color;
		this.placa = placa;
		funeraria.agregarVehiculo(this);
		this.Capacidad = capacidad;
		vehiculos.add(this);
		}


	
	
	
	
	
	public ArrayList<Familiar> agregarPasajeros(ArrayList<Familiar> familiares) {
		ArrayList<Familiar> familiaresMenores = Cliente.familiaresPorEdad("niño",familiares);
 		ArrayList<Familiar> familiaresMayores = Cliente.familiaresPorEdad("adulto",familiares);
 		
 		ArrayList<Familiar> pasajeros =new ArrayList<Familiar>();
 		
 		int capacidad =tipoVehiculo.getCapacidad();
 	
 		while (capacidad>0) {
 	
 			if(capacidad%2==0 && familiaresMenores.size()!=0) {
 				
 				Familiar familiarMenor=familiaresMenores.get(0);
 				pasajeros.add(familiarMenor);
 				pasajeros.add(familiarMenor.getResponsable());
 				
 				familiaresMenores.remove(familiarMenor);
 				familiaresMayores.remove(familiarMenor.getResponsable());
 				capacidad-=2;
 		
 				}
 			
 			else if (familiaresMayores.size()!=0 && capacidad!=0) {
 				    Familiar familiarMayor=familiaresMayores.get(0);
 					pasajeros.add(familiarMayor);
 					
 					familiaresMayores.remove(familiarMayor);
 					capacidad-=1;
 				}
 			else {break;}
 			
 			
 		}
 	
 		setPasajeros(pasajeros);
 		return pasajeros;
 		
 				
 				
 			//Fin for
 			
 			
 		}
 		
	public String productoVehiculo(ArrayList<Familiar> pasajeros) {
		String producto="Vehículo "+tipoVehiculo.name()+" - color: "+color+" - placa: "+placa+"\n";
		
		ArrayList<Familiar> auxPasajeros=pasajeros;
		
		while(auxPasajeros.size()>0) {
			Familiar pasajero= auxPasajeros.get(0);
			if(pasajero.getCC()==0) {
				producto+="Familiar menor de edad: "+pasajero+" Acudiente: "+pasajero.getResponsable()+"\n";
			}else {producto+="Familiar: "+pasajero+"\n";} 
			auxPasajeros.remove(pasajero);
		}
	    	return producto;
	    }
	

	
	
	public void agregarPasajero(Familiar pasajero) {
		
		pasajeros.add(pasajero);
		
		estado=false;
		
	}
	
	public String toString() {
		return tipoVehiculo.toString();
	}
	
	
	
	//metodos get y set 
	
	public static ArrayList<Vehiculo> getVehiculos(){
		return vehiculos;
	}
	
	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa=placa;
	}
	public Empleado getConductor() {
		return conductor;
	}
	public void setConductor(Empleado conductor) {
		this.conductor=conductor;
	}
	public ArrayList<String> getRuta() {
		return ruta;
	}
	public void setRuta(ArrayList<String> ruta) {
		this.ruta=ruta;
	}
	public ArrayList<Familiar> getPasajeros(){
		return this.pasajeros;
	}
	public Funeraria getFuneraria() {
		return funeraria;
	}
	public void setFuneraria(Funeraria funeraria) {
		this.funeraria = funeraria;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Boolean isEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public void setPasajeros(ArrayList<Familiar> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public Boolean getEstado() {
		return estado;
	}
	public int getCapacidad() {
		return Capacidad;
	}
	public void setCapacidad(int capacidad) {
		Capacidad = capacidad;
	}
	public int getPrecio() {
		return Precio;
	}
	public void setPrecio(int precio) {
		Precio = precio;
	}
	
	
	
	
	

}
