package gestorAplicacion.establecimientos;

import java.util.ArrayList;

import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;

public class Cementerio extends Establecimiento {
	
	private String tipo; //(cenizas o cuerpos)
	private Funeraria funeraria;
	
	//Constructor
	public Cementerio(String nombre, int ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,String tipo,Funeraria funeraria) {
			super(nombre,ubicacion,capacidad,cuentaCorriente,afiliacion,empleado);
			this.tipo=tipo;
			this.funeraria=funeraria;
		}
	
	
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}
	

	

}
