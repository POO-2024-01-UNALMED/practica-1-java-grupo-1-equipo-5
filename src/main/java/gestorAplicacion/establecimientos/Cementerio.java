package gestorAplicacion.establecimientos;
import gestorAplicacion.inventario.*;

import java.util.ArrayList;
import gestorAplicacion.inventario.*;

import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;


public class Cementerio extends Establecimiento {
	
	private String tipo; //(cenizas o cuerpos)
	//public static ArrayList<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
	private ArrayList<Urna> urnas = new ArrayList<Urna>();
	private ArrayList<Tumba> tumbas = new ArrayList<Tumba>();
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
	
	
	//Método para agregar al ArrayList de inventario (En caso de los cementerios de cuerpos se agregarán tumbas y en caso de los cementerios de cenizas se agregarán urnas)
	
		
	public void agregarUrnas(Urna urna) {
		this.urnas.add(urna);
	}	
	public void agregarTumba(Tumba tumba) {
		this.tumbas.add(tumba);
	}	
	
	
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}
	

	

}
