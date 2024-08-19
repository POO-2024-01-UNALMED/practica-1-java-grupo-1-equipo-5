package gestorAplicacion.inventario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.personas.Cliente;

public class Tumba extends Inventario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Constructor
	public Tumba(String nombre,Cementerio cementerio,double tamaño,int categoria) {
		super(nombre,cementerio,tamaño,categoria);
		if(cementerio.getTipo().equals("cuerpos")) {
			cementerio.agregarInventario(this);
		}
	}
	//Para calcular el volumen de la tumba se deberá ingresar la estatura del Cliente 
	//y se calculará el volumen con medidas estándares
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
	//Recibe un parámetro tipo String que puede tener valor "flores" o "material"
	//No retorna nada pero redefine inventarioFlores o inventarioMaterial según corresponda
	public void generarAdornos(String tipoAdorno) {
		
		String[] arregloAuxiliar;
		Random random = new Random();
		int numero;
		
		if(tipoAdorno.equals("flores")) {
			if(getCategoria()==0) {
				numero=1;
			}else if(getCategoria()==1) {
				numero=2;
			}else {numero=3;}
			arregloAuxiliar=Inventario.flores;
		}
		else {
			arregloAuxiliar=this.material;
			int numeroAleatorio = random.nextInt(4)+1;
			getInventarioMaterial().add(arregloAuxiliar[numeroAleatorio]); return;}
		
		for(String arreglo:arregloAuxiliar) {
			
			int numeroAleatorio = random.nextInt(numero)+1;
			while(numeroAleatorio>0) {
				if(tipoAdorno.equals("flores")) {
					getInventarioFlores().add(arreglo);
				}
				numeroAleatorio-=1;
			}//Fin while
		}//Fin for
		
		
		
	}
	
	
	
	

	
	
	
}
