package gestorAplicacion.inventario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public abstract class Inventario {
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
	
	static public String [] flores = {"Rosas", "Lirios", "Claveles", "Orquídeas", "Peonías"};
	static public String [] material = {"Madera", "Metal", "Cerámica", "Vidrio", "Bambu","Piedra"};
	//Constructor
	protected Inventario(String nombre,Cementerio cementerio,double tamaño,int categoria) {
		this.nombre=nombre;
		this.cementerio = cementerio;
		this.tamaño=determinarTamaño(tamaño);
		this.categoria=categoria;		 //Si el cementerio es de cenizas se agregarán Objetos de la clase Urna y si el cementerio es de cuerpos se agregarán objetos de la clase Tumba
	}

	
	public void agregarCliente(Cliente cliente) {
		this.cliente=cliente;
		this.cementerio.getClientes().add(cliente);
		this.cliente.setInventario(this);
	}
	
	public int determinarCategoria(int edad) {
		if(edad<30) {
			categoria=0;
		}
		else if(edad>30 && edad<60) {
			categoria=1;
		}else {categoria=2;}
		
		return categoria;
	}
	
	
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
	

	//mMtodos get y sett
	
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


	public static void setFlores(String[] flores) {
		Inventario.flores = flores;
	}


	public static void setMaterial(String[] material) {
		Inventario.material = material;
	}
	
	

	
	
	
	

	
}
