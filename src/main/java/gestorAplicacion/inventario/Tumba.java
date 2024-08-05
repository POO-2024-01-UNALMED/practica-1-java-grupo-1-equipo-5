package gestorAplicacion.inventario;

import java.util.ArrayList;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public class Tumba extends Inventario{
	
	
	//Constructor
	public Tumba(Cementerio cementerio,double tamaño,int categoria) {
		super(cementerio,tamaño,categoria);
		if(cementerio.getTipo().equals("cuerpo")) {
			cementerio.agregarInventario(this);
		}
	}
	
	
	public double determinarTamaño(double largo) {
		
		//Medidas estándares en metros 
		double ancho=1.20;
		double profundidad =1.5;
		
		//Tamaño en metros
		double volumenTumba=ancho*profundidad*(largo+0.5);
		return volumenTumba;
	}

	
	

	
	
	
}
