package gestorAplicacion.inventario;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public class Tumba extends Inventario{
	
	
	//Constructor
	public Tumba(Cementerio cementerio, int tamaño, int categoria) {
		super(cementerio,tamaño,categoria);
	}
	
}
