package gestorAplicacion.inventario;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.financiero.*;
import gestorAplicacion.personas.Cliente;

public class Urna extends Producto {

	//private Factura factura;
	private Cementerio cementerio;
	private Cliente cliente;
	private int tamaño;//"pequeño", "mediano", "grande"
	private int categoria;
	private String tipo; // "fija" "ordinaria"
	
	//Constructor
	public Urna(String nombre, double precio, int cantidad, Cementerio cementerio, Cliente cliente, int tamaño,
			int categoria, String tipo) {
		super(nombre, precio, cantidad);
		this.cementerio = cementerio;
		this.cliente = cliente;
		this.tamaño = tamaño;
		this.categoria = categoria;
		this.tipo = tipo;
		cementerio.agregarUrnas(this); //Si el cementerio es de cenizas se agregarán urnas y si el cementerio es de cuerpos se agregarán tumbas
	}
	
	
	public void agregarCliente(Cliente cliente) {
		this.cliente=cliente;
		this.cementerio.getFuneraria().getClientes().add(cliente);
		this.cementerio.getClientes().add(cliente);
	}

	
	
	
	
	
	//metodos get y set
	
	
	
	//public Factura getFactura() {
		//return factura;
	//}
	//public void setFactura(Factura factura) {
		//this.factura=factura;
	//}
	
	
	

}
