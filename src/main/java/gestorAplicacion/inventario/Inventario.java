package gestorAplicacion.inventario;

import java.util.ArrayList;
import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public abstract class Inventario {
	
	private ArrayList<Inventario> inventario =new ArrayList<Inventario>();
	//private ArrayList<Vehiculo> listadoVehiculos= new ArrayList<Vehiculo>();
	private Cementerio cementerio;
	private Cliente cliente;
	private int tamaño;//"pequeño", "mediano", "grande"
	private int categoria;
	
	//Constructor a ser llamado desde las clases Urna y Tumba
	protected Inventario(Cementerio cementerio, int tamaño, int categoria) {
		this.cementerio=cementerio;		
		this.tamaño=tamaño;
		this.categoria=categoria;
		cementerio.agregarInventario(this); //Si el cementerio es de cenizas se agregarán urnas y si el cementerio es de cuerpos se agregarán tumbas
	}
	
	//Metodo agregar cliente 
	
	public void agregarCliente(Cliente cliente) {
		this.cliente=cliente;
		cliente.setInventario(this);
		this.cementerio.getFuneraria().getClientes().add(cliente);
		this.cementerio.getClientes().add(cliente);
	}
	
	
	
	//Métodos set y get
	public Cementerio getCementerio() {
		return cementerio;
	} 
	public void setCementerio(Cementerio cementerio) {
		this.cementerio=cementerio;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
	}
	public int getTamaño() {
		return tamaño;
	}
	public void setTamaño(int tamaño) {
		this.tamaño=tamaño;
	}
	public ArrayList<Inventario> getInventario(){
		return inventario;
	}
	public void setInventario(ArrayList<Inventario> inventario) {
		this.inventario=inventario;
	}
	
	
	
	
	
	//public ArrayList<Vehiculo> getListadoVehiculos(){
		//return listadoVehiculos;
	//}
	//public void setListadoVehiculos(ArrayList<Vehiculo> listadoVehiculos) {
		//this.listadoVehiculos=listadoVehiculos;
	//}
	
}
