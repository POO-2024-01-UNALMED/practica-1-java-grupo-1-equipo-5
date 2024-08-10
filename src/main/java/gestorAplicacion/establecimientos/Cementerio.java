package gestorAplicacion.establecimientos;
import gestorAplicacion.inventario.*;

import java.time.LocalTime;
import java.util.ArrayList;
import gestorAplicacion.inventario.*;

import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;


public class Cementerio extends Establecimiento {
	
	private String tipo; //(cenizas o cuerpos)
	
	//En inventario se pueden agregar objetos de la clase Urna para los objetos cementerios de tipo cenizas y objetos Tumba para los cementerios de tipo Tumba
	private ArrayList<Inventario> inventario = new ArrayList<Inventario>();

	//Constructor
	public Cementerio(String nombre, String ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,String tipo,Funeraria funeraria) {
			super(nombre,ubicacion,capacidad,cuentaCorriente,afiliacion,empleado,funeraria);
			this.tipo=tipo;
		}
	
	
	
	public static ArrayList<Establecimiento> cementerioPorTipo(ArrayList<Establecimiento> cementerios,String tipo){
		
		ArrayList <Establecimiento> cementeriosDisponibles= new ArrayList<Establecimiento>();
		
		for(int i=0;i<cementerios.size();i++) {
			
			Cementerio cementerio = (Cementerio) cementerios.get(i);
			
			if (cementerio.getTipo()==tipo) {
				cementeriosDisponibles.add(cementerio);
			}//fin if
			
		}//fin for
		
		return cementeriosDisponibles;
	}
	
	//Recibe como parámetro un String "urna" o "tumba"
	public ArrayList<Inventario> disponibilidadInventario(String urnaTumba, double tamaño){
		
		ArrayList<Inventario> inventarioDisponible=new ArrayList<Inventario>();
		ArrayList<Inventario> auxInventario = new ArrayList<Inventario>();
		
		if(urnaTumba.equals("tumba")) {
			auxInventario=inventario;
		}else {
			auxInventario=this.tipoUrna();
		}
		
		for(Inventario auxUrnaTumba: auxInventario) {
			if (auxUrnaTumba.getCliente()==null & auxUrnaTumba.getTamaño()>=auxUrnaTumba.determinarTamaño(tamaño)){
				inventarioDisponible.add(auxUrnaTumba);
			}//Fin if
		}//Fin for 
		return inventarioDisponible;
	}
	
	
	public ArrayList<Inventario> tipoUrna(){
		
		Iglesia iglesia=this.getIglesia();
		
		if(iglesia == Iglesia.BUDISMO) {
			return inventario;
		}else if(iglesia ==Iglesia.CRISTIANISMO) {
			return inventario;
			
		}else if (iglesia ==Iglesia.HINDUISMO) {
			return urnasPorTipo("ordinaria");
		}else if(iglesia==Iglesia.TAOISMO) {
			return urnasPorTipo("fija");
		}else{return inventario;}
	
	}
	
	public ArrayList<Inventario> inventarioDefault(){
		
		ArrayList<Inventario> porDefecto=new ArrayList<Inventario>();
		ArrayList<Inventario> auxInventario=new ArrayList<Inventario>();
		
		if(this.tipo.equals("cenizas")) {auxInventario=urnasPorTipo("fija");
		
		}else {auxInventario=inventario;}
		
		for(Inventario urnaTumba: auxInventario) {
			if(urnaTumba.getNombre().equals("default")) {
				porDefecto.add(urnaTumba);
			}
			
		}//Fin for
		return porDefecto;
		
	}
	
	public ArrayList<Inventario> urnasPorTipo(String tipo){
		
		ArrayList<Inventario> urnasPorTipo =new ArrayList<Inventario>();
		
		for(Inventario auxInventario:inventario) {
			if(auxInventario instanceof Urna) {
				Urna urna = (Urna)auxInventario;
				if(urna.getTipo().equals(tipo)) {
					urnasPorTipo.add(urna);
				}//Fin if
			}//Fin if principal
		}//Fin for
		
		return urnasPorTipo;
	} 
	
	
	
	//Método para agregar al ArrayList de inventario (En caso de los cementerios de cuerpos se agregarán tumbas y en caso de los cementerios de cenizas se agregarán urnas)
	public void agregarInventario(Inventario inventario) {
		this.inventario.add(inventario);
	}	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}

	

	

}
