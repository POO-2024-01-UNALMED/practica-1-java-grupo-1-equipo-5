package gestorAplicacion.inventario;

import java.util.ArrayList;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public abstract class Inventario {
	private Cementerio cementerio;
	private Cliente cliente;
	private double tamaño; //Número 
	private int categoria;
	
	//Constructor
	protected Inventario(Cementerio cementerio,double tamaño,int categoria) {
		this.cementerio = cementerio;
		this.tamaño=tamaño;
		this.categoria = categoria;
		 //Si el cementerio es de cenizas se agregarán Objetos de la clase Urna y si el cementerio es de cuerpos se agregarán objetos de la clase Tumba
	}

	
	public void agregarCliente(Cliente cliente) {
		this.cliente=cliente;
		this.cementerio.getClientes().add(cliente);
	}
	
	
	public abstract double determinarTamaño(double tamaño);
	
	//public abstract void setTamaño(double Tamaño);
	
	
	public Cliente getCliente() {
		return cliente;
	}
	public int getCategoria() {
		return categoria;
	}
	
	

	
}
