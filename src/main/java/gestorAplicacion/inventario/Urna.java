package gestorAplicacion.inventario;

import gestorAplicacion.establecimientos.Cementerio;
import java.util.ArrayList;

import gestorAplicacion.financiero.*;
import gestorAplicacion.personas.Cliente;

public class Urna extends Inventario {

	private String tipo; // "fija" u "ordinaria" 
	private ArrayList<String> floresUrna;
	private ArrayList<String> materialUrna;

	//Constructor
	public Urna(String nombre,Cementerio cementerio,double peso,int categoria,String tipo) {
		super(nombre,cementerio,peso);
		this.tipo=tipo;
		if(cementerio.getTipo().equals("cenizas")) {
			cementerio.agregarInventario(this);
		}
	}
		
	
		@Override
		public void generarAdornos(String tipoAdorno){
			
			String[] floresUrnas={"Rosas", "Lirios", "Claveles", "Orquídeas", "Peonías"};
			
			String[] arregloAuxiliar;
			
			if(tipoAdorno.equals("flores")) {
				arregloAuxiliar=floresUrnas;
			}else {
				arregloAuxiliar=tipoMaterial();
			}
			
			int numeroAleatorio = (int) (Math.random() * 4);
			
			for(String arreglo:arregloAuxiliar) {
				while(numeroAleatorio>0) {
					getFlores().add(arreglo);
				}
				
			}
				
		}
	
		@Override
		public double determinarTamaño(double peso) {
			//Se estima que se necesitan alrededor de 1 pulgada cúbica (aproximadamente 16.4 cm³) de espacio en la urna por cada libra (0.45 kg) del peso del cuerpo antes de la cremación.
			//Se necesita calcular el volumen de acuerdo al peso y al tamaño base en cm^3
			//El peso estará dado en kg 
			
			double tamañoBase=16.4;
			double volumenNecesario=16.4*peso; //En centimetros cúbicos
			
			return volumenNecesario;	
			
		}
		
		public String[] tipoMaterial(){
			String[] material= {"Madera", "Metal", "Cerámica"};
			
			if(tipo.equals("fija")) {
				String [] materialAux= {"Vidrio", "Bambu","Piedra"};
				return materialAux;
			}
			
			return material;
			
		} 
		
		public String getTipo() {
			return tipo;
		}
		
		
	}
	
	
	

	
	
	
	
	
	
	
	


