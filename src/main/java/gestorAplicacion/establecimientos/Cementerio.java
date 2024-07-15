package gestorAplicacion.establecimientos;

import java.util.ArrayList;

import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;

public class Cementerio extends Establecimiento {
	
	private String tipo; //(cenizas o cuerpos)
	public static ArrayList<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
	
	//Constructor
	public Cementerio(String nombre, int ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,String tipo,Funeraria funeraria) {
			super(nombre,ubicacion,capacidad,cuentaCorriente,afiliacion,empleado,funeraria);
			this.tipo=tipo;
			establecimientos.add(this);
		}
	
	
	
	public static ArrayList<Establecimiento> cementerioPorTipo(ArrayList<Establecimiento> cementerios,String tipo){
		
		ArrayList <Establecimiento> cementeriosDisponibles= new ArrayList<Establecimiento>();
		
		for(int i=0;i<cementerios.size();i++) {
			
			Cementerio cementerio = (Cementerio) cementerios.get(i);
			
			if (cementerio.getTipo()==tipo) {
				cementeriosDisponibles.add(cementerio);
			}//fin if
			
		}//fin for
		
		return cementeriosDisponibles;
	}
	
	

		
		
		
	
	
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}
	

	

}
