package gestorAplicacion.inventario;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public class Tumba extends Producto{
	private Cementerio cementerio;
	private Cliente cliente;
	private int tamaño;//"pequeño", "mediano", "grande"
	private int categoria;
	
	//Constructor
	public Tumba(String nombre, double precio, int cantidad, Cementerio cementerio, Cliente cliente, int tamaño,
			int categoria) {
		super(nombre, precio, cantidad);
		this.cementerio = cementerio;
		this.cliente = cliente;
		this.tamaño = tamaño;
		this.categoria = categoria;
		cementerio.agregarTumba(this); //Si el cementerio es de cenizas se agregarán urnas y si el cementerio es de cuerpos se agregarán tumbas
	}

	
	public void agregarCliente(Cliente cliente) {
		this.cliente=cliente;
		this.cementerio.getFuneraria().getClientes().add(cliente);
		this.cementerio.getClientes().add(cliente);
	}

	
	
	
}
