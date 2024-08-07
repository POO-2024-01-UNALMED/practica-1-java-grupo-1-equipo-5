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
	
	private ArrayList<String> inventarioFlores = new ArrayList<String>();
	private ArrayList<String> inventarioMaterial = new ArrayList<String>();
	
	private ArrayList<String> floresSeleccionadas;
	private String materialSeleccionado= "por defecto";
	
	static public String [] flores = {"Rosas", "Lirios", "Claveles", "Orquídeas", "Peonías"};
	static public String [] material = {"Madera", "Metal", "Cerámica", "Vidrio", "Bambu","Piedra"};
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
			 conteo = Collections.frequency(inventarioFlores, adorno);
		}else{conteo = Collections.frequency(inventarioMaterial, adorno);}
		
		return conteo;
	}
	
	public void agregarAdorno(String adorno, String floresMaterial) {
		if(floresMaterial.equals("flores")) {
			floresSeleccionadas.add(adorno);
			inventarioFlores.remove(adorno);
		}else {inventarioMaterial.remove(adorno); materialSeleccionado=adorno;} 
	}
	
	@Override
	public String toString() {
		return nombre;
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
		return inventarioFlores;
	}
	public void setFlores(ArrayList<String> flores){
		this.inventarioFlores=flores;
	}
	
	public ArrayList<String> getMaterial(){
		return inventarioMaterial;
	}
	public void setMaterial(ArrayList<String> material){
		this.inventarioMaterial=material;
	}
	
	public ArrayList<String> getFloresSeleccionadas(){
		return floresSeleccionadas;
	}
	
	public String getMaterialSeleccionado() {
		return materialSeleccionado;
	}
	
	public void setMaterialSeleccionado(String material){
		this.materialSeleccionado=material;
	}
	

	
	
	
	

	
}
