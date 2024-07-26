package gestorAplicacion.inventario;


import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Persona;
import java.util.ArrayList;
import gestorAplicacion.establecimientos.Funeraria;


public class Vehiculo {
	private TipoVehiculo tipoVehiculo; //Es un Enum
	private Funeraria funeraria;
	private String marca;
	// private String modelo; Este se puede reemplazar pot tipoVehículo
    private String color;
    private Boolean estado=true; // true si está disponible, false si no lo está
	private String placa;
	private Empleado conductor;
	private ArrayList<String> ruta=new ArrayList<String>();
	private ArrayList<Persona> pasajeros = new ArrayList<Persona>();
	
	
	// Constructor
	public Vehiculo(TipoVehiculo tipoVehiculo,Funeraria funeraria,String marca, String color, String placa) {
		this.tipoVehiculo=tipoVehiculo;
		this.funeraria=funeraria;
		this.marca = marca;
		//this.modelo = modelo;
		this.color = color;
		this.placa = placa;
		funeraria.agregarVehiculo(this);
		}
	

	
	
	
	
	
	
	
	
	
	public void agregarPasajero(Persona pasajero) {
		pasajeros.add(pasajero);
	}
	
	public String toString() {
		return tipoVehiculo.toString();
	}
	
	
	
	//metodos get y set 
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
	public boolean isDisponible() {
        return estado;
    }
	public ArrayList<Persona> getPasajeros(){
		return pasajeros;
	}
	

}
