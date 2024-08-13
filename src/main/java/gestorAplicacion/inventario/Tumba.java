package gestorAplicacion.inventario;

import java.util.ArrayList;
import java.util.Random;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public class Tumba extends Inventario{
	
	
	//Constructor
	public Tumba(String nombre,Cementerio cementerio,double tamaño,int categoria) {
		super(nombre,cementerio,tamaño,categoria);
		if(cementerio.getTipo().equals("cuerpos")) {
			cementerio.agregarInventario(this);
		}
	}
	
	@Override
	public double determinarTamaño(double largo) {
		
		//Medidas estándares en metros 
		double ancho=1.20;
		double profundidad =1.5;
		
		//Tamaño en metros
		double volumenTumba=ancho*profundidad*(largo+0.5);
		return volumenTumba;
	}
	
	@Override
	public void generarAdornos(String tipoAdorno) {
		
		ArrayList<String> arregloAuxiliar;
		int numero;
		
		if(tipoAdorno.equals("flores")) {
			if(getCategoria()==0) {
				numero=1;
			}else if(getCategoria()==1) {
				numero=2;
			}else {numero=3;}
			arregloAuxiliar=this.getInventarioFlores();
		}
		else {
			arregloAuxiliar=this.getMaterial();
			numero=1;}
		
		Random random = new Random();
		
		for(String arreglo:arregloAuxiliar) {
			
			int numeroAleatorio = random.nextInt(numero)+1;
			while(numeroAleatorio>0) {
				if(tipoAdorno.equals("flores")) {
					getFlores().add(arreglo);
				}else {getMaterial().add(arreglo);}
				numeroAleatorio-=1;
			}//Fin while
		}//Fin for
		
		
		
	}
	
	
	

	
	
	
}
