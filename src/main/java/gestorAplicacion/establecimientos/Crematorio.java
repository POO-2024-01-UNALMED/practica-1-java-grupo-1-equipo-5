package gestorAplicacion.establecimientos;

import java.util.ArrayList;
import java.time.LocalTime;

import gestorAplicacion.personas.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.inventario.*;


public class Crematorio extends Establecimiento{
	//Atributos
	LocalTime horaEventoActual;
	
	
	//Constructor
	public Crematorio(String nombre, String ubicacion, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,Funeraria funeraria) {
		super(nombre,ubicacion,capacidad,cuentaCorriente,afiliacion,empleado,funeraria);
		
	}
	
	public ArrayList<Cementerio> cementeriodisponible(LocalTime horaFin, Cliente cliente){
		
		ArrayList<Establecimiento> cementerios=getFuneraria().buscarCementerios("cenizas", cliente);
		ArrayList<Cementerio> cementeriosDisponibles=new ArrayList<Cementerio>(); 
		
		for(Establecimiento auxCementerio:cementerios) {
			Cementerio cementerio =(Cementerio) auxCementerio; 
			if(cementerio.horarioEventos.size()==0){
				cementerio.generarHoras();
			}
			
			for(LocalTime hora: cementerio.horarioEventos) {
				if(!(horaFin.isBefore(hora))){
					cementerio.horarioEventos.remove(hora);
				}//Fin if
			}//fon for
			
			if(cementerio.horarioEventos.size()>0) {
				cementeriosDisponibles.add(cementerio);
			}
			
		}//Fin for principal
		
		return cementeriosDisponibles;
		
	}
	
	
	
	
	
	
	
	
	
	
}
