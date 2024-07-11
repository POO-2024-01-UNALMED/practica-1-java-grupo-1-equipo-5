package gestorAplicacion.inventario;

import java.util.ArrayList;
public class Inventario {
	
	private ArrayList<Urna> listadoUrnas =new ArrayList<Urna>();
	private ArrayList<Vehiculo> listadoVehiculos= new ArrayList<Vehiculo>();
	
	//metodos get y set
	
	public ArrayList<Urna> getListadoUrnas(){
		return listadoUrnas;
	}
	public void setListadoUrnas(ArrayList<Urna> listadoUrnas) {
		this.listadoUrnas=listadoUrnas;
	}
	public ArrayList<Vehiculo> getListadoVehiculos(){
		return listadoVehiculos;
	}
	public void setListadoVehiculos(ArrayList<Vehiculo> listadoVehiculos) {
		this.listadoVehiculos=listadoVehiculos;
	}
	
}
