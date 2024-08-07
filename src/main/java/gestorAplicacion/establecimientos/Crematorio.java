package gestorAplicacion.establecimientos;

import java.util.ArrayList;
import java.time.LocalTime;

import gestorAplicacion.personas.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.inventario.*;


public class Crematorio extends Establecimiento{
	//Atributos
	
	
	
	//Constructor
	public Crematorio(String nombre, String ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,Funeraria funeraria) {
		super(nombre,ubicacion,capacidad,cuentaCorriente,afiliacion,empleado,funeraria);
		}
	
	public void cambiarHorarios(ArrayList<Establecimiento> cementerios){
		
		LocalTime horaFin =getIglesia().duracionEvento(getHoraEvento());
		
		for(Establecimiento auxCementerio:cementerios) {
			
			int randomNumber = (int)(Math.random() * (3)) + 1;
			Cementerio cementerio =(Cementerio) auxCementerio; 
			
			while(randomNumber>0) {
				int min = horaFin.getHour();
				int max = 23;
				// Genera un número aleatorio entre min (inclusive) y max (inclusive)
				int horas = (int)(Math.random() * (max - min + 1)) + min;
				int minutos = (int)(Math.random() * (60));

				LocalTime horaGenerada = LocalTime.of(horas, minutos);
	            auxCementerio.horarioEventos.add(horaGenerada);
		
			}
		
	
		
		
	}
		
		
	}
	

}
	
	
	
	
	
	
	
	
	

