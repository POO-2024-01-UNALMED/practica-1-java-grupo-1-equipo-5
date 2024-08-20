/* Autores Violeta Gómez
 * Tiene la funcionalidad de asociar los objetos Urna y Tumba para que 
 * se puedan emplear más facilmente*/

package gestorAplicacion.inventario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public abstract class Inventario implements Serializable{
	private String nombre;
	private Cementerio cementerio;
	private Cliente cliente;
	private double tamaño; //Número 
	private int categoria;
	
	public static int precioFlores=35000;
	public static int precioMateriales=45000;
	
	private ArrayList<String> inventarioFlores = new ArrayList<String>();
	private ArrayList<String> inventarioMaterial = new ArrayList<String>();
	
	private ArrayList<String> floresSeleccionadas=new ArrayList<String>();
	private String materialSeleccionado= "por defecto";
	
	static public final String [] flores = {"Rosas", "Lirios", "Claveles", "Orquídeas", "Peonías"};
	static public final String [] material = {"Madera", "Metal", "Cerámica", "Vidrio", "Bambu","Piedra"};
	
	
	private static ArrayList<Inventario> inventarioTotal =new ArrayList<Inventario>();
	private static final long serialVersionUID = 1L;
	//Constructor
	protected Inventario(String nombre,Cementerio cementerio,double tamaño,int categoria) {
		this.nombre=nombre;
		this.cementerio = cementerio;
		this.tamaño=determinarTamaño(tamaño);
		if(categoria>2) {
			this.categoria=determinarCategoria(categoria);
		}else {this.categoria=categoria;}
		inventarioTotal.add(this);
		
	
	}

	
	public void agregarCliente(Cliente cliente) {
		this.cliente=cliente;
		this.cementerio.getClientes().add(cliente);
		this.cliente.setInventario(this);
		this.cementerio.getFuneraria().getClientes().remove(cliente);
	}
	
	//Este método sirve para comparar el parámetro que se ingresa que debe ser la edad de un Cliente
	//Retorna un dato tipo int que determina en que rango de edad se encuentra la categoria
	public int determinarCategoria(int edad) {
		int categoria=0;
		if(edad<18) {
			categoria=0;
		}
		else if(edad>=18 && edad<60) {
			categoria=1;
		}else {categoria=2;}
		
		return categoria;
	}
	
	
	//Recibe un parámetro tipo String que debe tener valor "flores" o "material"
	//Sirve para determinar los precios del arreglo flores y del arreglo material
	public static double precios(String adorno) {
		
		String[] arregloCompleto = Stream.concat(Arrays.stream(flores), Arrays.stream(material)).toArray(String[]::new);
		double precio=30000;
		int indice=0;
		
		for (int i = 0; i < arregloCompleto.length; i++) {
	            if (arregloCompleto[i].equals(adorno)) {
	                indice=i;
	            }
	            
		}
		
		return precio+indice*5000;
	        
	}
	
	
	public abstract void generarAdornos(String tipoAdorno);
	
	public abstract double determinarTamaño(double tamaño);
	
	boolean validacion=false;
	
	
	
	
	
	public void setTamaño(double Tamaño) {
		this.tamaño=determinarTamaño(tamaño);
	}
	//Recibe dos parámetros tipo String el primer parámetro debe ser algún elemento del arreglo 
	//flores y en ese caso el segundo parámetro deberá tomar el valor de "flores"
	//En otro caso el primer parámetro deberá tomar un valor de algún elemento del arreglo de material 
	//y en ese caso el segundo marámetro deberá ser "material"
	public int contarAdorno(String adorno,String floresMaterial) {
		int conteo=0;
		if(floresMaterial.equals("flores")) {
			 conteo = Collections.frequency(inventarioFlores, adorno);
		}else{conteo = Collections.frequency(inventarioMaterial, adorno);}
		
		return conteo;
	}
	
	//Retorna el precio de del precio de los arreglos de material y flores dependiendo de la cantidad
	public double precioTotal(int cantidadF ) {
		double precio = cantidadF * Inventario.getPrecioFlores() + Inventario.getPrecioMateriales();
	return precio;
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
	

	//mMtodos get y sett
	
	
	public static ArrayList<Inventario> getInventarioTotal(){
		return inventarioTotal;
	}
	
	public String getNombre() {
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
	
	public Cementerio getCementerio() {
		return cementerio;
	}
	public void setCementerio(Cementerio cementerio) {
		this.cementerio=cementerio;
	}
	
	public ArrayList<String> getFlores(){
		return inventarioFlores;
	}
	
	
	public ArrayList<String> getMaterial(){
		return inventarioMaterial;
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


	public static int getPrecioFlores() {
		return precioFlores;
	}


	public static void setPrecioFlores(int precioFlores) {
		Inventario.precioFlores = precioFlores;
	}


	public static int getPrecioMateriales() {
		return precioMateriales;
	}


	public static void setPrecioMateriales(int precioMateriales) {
		Inventario.precioMateriales = precioMateriales;
	}


	public ArrayList<String> getInventarioFlores() {
		return inventarioFlores;
	}


	public void setInventarioFlores(ArrayList<String> inventarioFlores) {
		this.inventarioFlores = inventarioFlores;
	}


	public ArrayList<String> getInventarioMaterial() {
		return inventarioMaterial;
	}


	public void setInventarioMaterial(ArrayList<String> inventarioMaterial) {
		this.inventarioMaterial = inventarioMaterial;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public void setFloresSeleccionadas(ArrayList<String> floresSeleccionadas) {
		this.floresSeleccionadas = floresSeleccionadas;
	}


	public boolean isValidacion() {
		return validacion;
	}


	public void setValidacion(boolean validacion) {
		this.validacion = validacion;
	}

	
	

	
	
	
	

	
}
