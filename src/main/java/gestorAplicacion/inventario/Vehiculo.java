package gestorAplicacion.inventario;


import gestorAplicacion.personas.Empleado; 
import java.util.ArrayList;


public class Vehiculo {
	private String placa;
	private Empleado conductor;
	private ArrayList<String> ruta=new ArrayList<String>();
	
	//metodos get y set 
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

}
