
/* Autores Violeta Gómez
 * Esta clase tiene la funcionalidad de simular una Urna que está asociada a 
 * un cementerio y puede o no estar asociada a un cliente*/

package gestorAplicacion.inventario;

import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.financiero.*;
import gestorAplicacion.personas.Cliente;

public class Urna extends Inventario implements Serializable{

	private String tipo; // "fija" u "ordinaria" 
	private ArrayList<String> floresUrna;
	private ArrayList<String> materialUrna;
	private static final long serialVersionUID = 1L;
	
	//Constructor
	public Urna(String nombre,Cementerio cementerio,double peso,int categoria,String tipo) {
		super(nombre,cementerio,peso, categoria);
		this.tipo=tipo;
		if(cementerio.getTipo().equals("cenizas")) {
			cementerio.agregarInventario(this);
		}
	}
		
		//Recibe como parámetro un String que puede tomar valor de "flores" o "material"
		//Se emplea para modificar el arreglo de flores o material del objeto tipo Urna
		@Override
		public void generarAdornos(String tipoAdorno){
			
			String[] floresUrnas=Inventario.flores;
			
			String[] arregloAuxiliar;
			boolean validacion=false;
			
			if(tipoAdorno.equals("flores")) {
				arregloAuxiliar=floresUrnas;
				if(getFlores().size()==0) {validacion=true;}
		
			}else {
				arregloAuxiliar=tipoMaterial();
				if(getMaterial().size()==0) {validacion=true;}
			}
			
			if(validacion) {
				 Random random = new Random();
				
				
				for(String arreglo:arregloAuxiliar) {
					int numeroAleatorio = random.nextInt(2)+1;
					while(numeroAleatorio>0) {
						if(tipoAdorno.equals("flores")) {
							this.getFlores().add(arreglo);
						}else {this.getMaterial().add(arreglo);}
						numeroAleatorio-=1;
					}//Fin while
				}//Fin for
			}//Fin if validacion
			
		}
		//Se ingresa un parámetro tipo double y la idea es que ese parámetro sea el peso del cliente 
		//Devuelve el volumen del objeto de tipo Urna de acuedo con el peso del cliente 
		@Override
		public double determinarTamaño(double peso) {
			//Se estima que se necesitan alrededor de 1 pulgada cúbica (aproximadamente 16.4 cm³) de 
			//espacio en la urna por cada libra (0.45 kg) del peso del cuerpo antes de la cremación.
			//Se necesita calcular el volumen de acuerdo al peso y al tamaño base en cm^3
			//El peso estará dado en kg 
			
			double tamañoBase=16.4;
			double volumenNecesario=16.4*peso; //En centimetros cúbicos
			
			return volumenNecesario;	
			
		}
		
		//Dependiendo del atributo tipo del mismo Objeto se filtran los materiales para la urna 
		public String[] tipoMaterial(){
			String[] material= {"Madera", "Metal", "Cerámica"};
			
			if(tipo.equals("fija")) {
				String [] materialAux= {"Vidrio", "Bambu","Piedra"};
				return materialAux;
			}
			
			return material;
			
		} 
		
		@Override
		//Recibe un parámetro tipo int que recibe la cantidad de adornos para determinar 
		//la categoria que debe tener el objeto Urna
		public int determinarCategoria(int numAdornos) {
			int categoria=0;
			if(numAdornos==1) {
				categoria=1;
			}else if(numAdornos==2) {
				categoria=2;
			}
			
			return categoria;
		}
		
		
		
		
		
		@Override
		public String toString() {
			return getNombre()+" de tipo "+tipo;
		}
		
		public String getTipo() {
			return tipo;
		}


		public ArrayList<String> getFloresUrna() {
			return floresUrna;
		}


		public void setFloresUrna(ArrayList<String> floresUrna) {
			this.floresUrna = floresUrna;
		}


		public ArrayList<String> getMaterialUrna() {
			return materialUrna;
		}


		public void setMaterialUrna(ArrayList<String> materialUrna) {
			this.materialUrna = materialUrna;
		}


		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		
		
		
	}
	
	
	

	
	
	
	
	
	
	
	


