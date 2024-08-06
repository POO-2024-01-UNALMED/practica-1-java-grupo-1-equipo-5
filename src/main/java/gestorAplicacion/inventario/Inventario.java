package gestorAplicacion.inventario;

import java.util.ArrayList;

import java.util.Collections;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public abstract class Inventario {
	private String nombre;
	private Cementerio cementerio;
	private Cliente cliente;
	private double tamaño; //Número 
	private int categoria;
	
	private ArrayList<String> flores = new ArrayList<String>();
	private ArrayList<String> material = new ArrayList<String>();
	
	
	//Constructor
	protected Inventario(String nombre,Cementerio cementerio,double tamaño) {
		this.nombre=nombre;
		this.cementerio = cementerio;
		this.tamaño=determinarTamaño(tamaño);
		 //Si el cementerio es de cenizas se agregarán Objetos de la clase Urna y si el cementerio es de cuerpos se agregarán objetos de la clase Tumba
	}

	
	public void agregarCliente(Cliente cliente) {
		this.cliente=cliente;
		this.cementerio.getClientes().add(cliente);
	}
	
	
	
	public abstract void generarAdornos(String tipoAdorno);
	
	public abstract double determinarTamaño(double tamaño);
	
	public void setTamaño(double Tamaño) {
		this.tamaño=determinarTamaño(tamaño);
	}
	
	public int contarAdorno(String adorno,String floresMaterial) {
		int conteo=0;
		if(floresMaterial.equals("flores")) {
			 conteo = Collections.frequency(flores, adorno);
		}else{conteo = Collections.frequency(material, adorno);}
		
		return conteo;
	}
	
	public void eliminarAdorno(String adorno, String floresMaterial) {
		if(floresMaterial.equals("flores")) {
			flores.remove(adorno);
		}else {material.remove(adorno);} 
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria=categoria;
	}
	
	public double getTamaño() {
		return tamaño;
	}
	
	public ArrayList<String> getFlores(){
		return flores;
	}
	public void setFlores(ArrayList<String> flores){
		this.flores=flores;
	}
	
	public ArrayList<String> getMaterial(){
		return material;
	}
	public void setMateriañ(ArrayList<String> material){
		this.material=material;
	}
	
	
	

	
}
