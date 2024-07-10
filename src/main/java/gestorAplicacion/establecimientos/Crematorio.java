package gestorAplicacion.establecimientos;

import java.util.ArrayList;

import gestorAplicacion.personas.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.inventario.*;


public class Crematorio extends Establecimiento{
	//Atributos

	private Funeraria funeraria; 
	
	
	//Constructor
	public Crematorio(String nombre, int ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,Funeraria funeraria) {
		super(nombre,ubicacion,capacidad,cuentaCorriente,afiliacion,empleado);
		this.funeraria=funeraria;
	}
	
	
	
	
	
	
	
	
	
	
}
