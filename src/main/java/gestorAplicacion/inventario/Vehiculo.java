package gestorAplicacion.inventario;


import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Familiar;
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

	private ArrayList<Familiar> pasajeros = new ArrayList<Familiar>();


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
	
	
	

}
