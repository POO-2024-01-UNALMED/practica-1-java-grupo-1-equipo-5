package gestorAplicacion.inventario;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.financiero.*;
import gestorAplicacion.personas.Cliente;

public class Urna extends Inventario {

	private String tipo; // "fija" u "ordinaria" 

	//Constructor
	public Urna(Cementerio cementerio,double tamaño,int categoria,String tipo) {
		super(cementerio,tamaño,categoria);
		this.tipo=tipo;
		if(cementerio.getTipo().equals("cenizas")) {
			cementerio.agregarInventario(this);
		}
	}
		
		
		public double determinarTamaño(double peso) {
			//Se estima que se necesitan alrededor de 1 pulgada cúbica (aproximadamente 16.4 cm³) de espacio en la urna por cada libra (0.45 kg) del peso del cuerpo antes de la cremación.
			//Se necesita calcular el volumen de acuerdo al peso y al tamaño base en cm^3
			//El peso estará dado en kg 
			
			double tamañoBase=16.4;
			double volumenNecesario=16.4*peso; //En centimetros cúbicos
			
			return volumenNecesario;	
			
		}
		
		public String getTipo() {
			return tipo;
		}
		
		
	}
	
	
	

	
	
	
	
	
	
	
	


