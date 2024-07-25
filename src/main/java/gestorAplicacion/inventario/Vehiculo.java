package gestorAplicacion.inventario;


import gestorAplicacion.personas.Empleado; 
import java.util.ArrayList;


public class Vehiculo {
	private String marca;
    private String modelo;
    private String color;
    private Boolean estado; // true si está disponible, false si no lo está
	private String placa;
	private Empleado conductor;
	private ArrayList<String> ruta=new ArrayList<String>();
	
	// Constructor
	public Vehiculo(String marca, String modelo, String color, Boolean estado, String placa) {
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.estado = estado;
		this.placa = placa;
		}
	
	
	
	
	
	
	
	
	
	
	
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
	public boolean isDisponible() {
        return estado;
    }
	public String getDescripcion() {
        return "Conductor "+conductor + " " + marca + " " + modelo + " de color " + color;
    }

}
