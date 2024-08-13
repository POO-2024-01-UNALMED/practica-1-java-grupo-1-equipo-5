package gestorAplicacion.inventario;


import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.personas.Persona;
import java.util.ArrayList;
import gestorAplicacion.establecimientos.Funeraria;


public class Vehiculo {
	private TipoVehiculo tipoVehiculo; //Es un Enum
	private Funeraria funeraria;
	// private String modelo; Este se puede reemplazar pot tipoVehículo
    private String color;
    private Boolean estado=true; // true si está disponible, false si no lo está
	private String placa;
	private Empleado conductor;
	private ArrayList<String> ruta=new ArrayList<String>();
	private ArrayList<Persona> pasajeros = new ArrayList<Persona>();
	private int Capacidad;

	
	
	// Constructor
	public Vehiculo(TipoVehiculo tipoVehiculo,Funeraria funeraria,String color, String placa) {
		this.tipoVehiculo=tipoVehiculo;
		this.funeraria=funeraria;
		this.color = color;
		this.placa = placa;
		funeraria.agregarVehiculo(this);
		}
	// Constructor con capacidad por si nesecitan el otro constructor
	public Vehiculo(TipoVehiculo tipoVehiculo,Funeraria funeraria,String color, String placa, int capacidad) {
		this.tipoVehiculo=tipoVehiculo;
		this.funeraria=funeraria;
		this.color = color;
		this.placa = placa;
		funeraria.agregarVehiculo(this);
		this.Capacidad = capacidad;
		}
	
	
	
	public ArrayList<String> generarTrayectoria(Establecimiento establecimientoInicio,ArrayList<Persona> pasajeros,Establecimiento establecimientoFinal){
		
		ArrayList<String> ruta=new ArrayList<String>();
		String ubicacionInicial=establecimientoInicio.getUbicacion();
		ruta.add(ubicacionInicial);
		
		for(Persona pasajero:pasajeros) {
			
			String ubicacionActual=ruta.get(ruta.size()-1);
			String cardinal=ubicacionActual.substring(2,4);
			
			String ubicacionPasajero=pasajero.getUbicacion();
			String cardinalPasajero=ubicacionPasajero.substring(2,4);
			String puntoCardinal=ubicacionPasajero.substring(0,2);
			
			if(!cardinal.equals(cardinalPasajero)) {
				ruta.add("00"+cardinalPasajero);	
			}
			ruta.add(puntoCardinal+cardinalPasajero);
		}
		
		String ubicacionFinal=establecimientoFinal.getUbicacion();
		String cardinalFinal=ubicacionFinal.substring(2,4);
		String puntoCardinal=ubicacionFinal.substring(0,2);
		
		String ubicacionActual=ruta.get(ruta.size()-1);
		String cardinal=ubicacionActual.substring(2,4);
		
		
		if(!cardinalFinal.equals(cardinal)) {
			ruta.add("00"+cardinalFinal);
		}
		ruta.add(puntoCardinal+cardinalFinal);
		
		return ruta;
	}
	
	

	
	
	public void agregarPasajero(Persona pasajero) {
		
		pasajeros.add(pasajero);
		
		estado=false;
		
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
	public ArrayList<Persona> getPasajeros(){
		return pasajeros;
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
	public void setPasajeros(ArrayList<Persona> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public Boolean getEstado() {
		return estado;
	}
	
	
	

}
