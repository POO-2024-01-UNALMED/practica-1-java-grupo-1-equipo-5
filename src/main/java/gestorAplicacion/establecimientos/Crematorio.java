package gestorAplicacion.establecimientos;

import java.util.ArrayList;
import java.util.Random;
import java.time.LocalTime;

import gestorAplicacion.personas.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.inventario.*;


public class Crematorio extends Establecimiento{
	//Atributos
	
	
	
	//Constructor
	public Crematorio(String nombre, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,Funeraria funeraria) {
		super(nombre,capacidad,cuentaCorriente,afiliacion,empleado,funeraria);
		}
	
	//Recibe un arreglo de tipo Establecimiento y cada elemento del arreglo también debe ser de tipo Cementerio 
	//Este método sirve para modificar el arreglo de horarios de Cementerio
	//no devuelve nada, pero modifica el arreglo de horarioEventos de cada Cementerio del arreglo ingresado como parámetro
	public void cambiarHorarios(ArrayList<Establecimiento> cementerios){
		
		
		LocalTime horaFin =getIglesia().duracionEvento(getHoraEvento());
		Random random=new Random();
		
		for(Establecimiento auxCementerio:cementerios) {
		
			int randomNumber = (int)(Math.random() * (3)) + 1;
			
			while(randomNumber>0) {
				int min = horaFin.getHour();
				int max = 23;
				// Genera un número aleatorio entre min (inclusive) y max (inclusive)
				int horas = (int)(Math.random() * (max - min + 1)) + min;
				int minutos = (int)(Math.random() * (60));

				LocalTime horaGenerada = LocalTime.of(horas, minutos);
	            auxCementerio.horarioEventos.add(horaGenerada);
	            randomNumber-=1;
		
			}
	
		
	}
		
		
	}
	

}
	
	
	
	
	
	
	
	
	

