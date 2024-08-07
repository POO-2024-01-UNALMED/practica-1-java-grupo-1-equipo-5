package uiMain;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.establecimientos.Crematorio;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.establecimientos.Iglesia;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.personas.Persona;

import java.time.LocalTime;
import java.util.ArrayList;

public class PruebaCementerio {
	public static void main(String[] args) {
		
		Familiar b= new Familiar("Mario",123,45,"345",null,"padre",17);
		Familiar e= new Familiar("Alberto",123,45,"345",null,"conyugue",17);
		
		
		
		ArrayList<Persona> familiar=new ArrayList<Persona>();
		familiar.add(b);
		familiar.add(e);
		
		Cliente a1 = new Cliente("a1",123,17,null,"oro",familiar);

		Funeraria funita = new Funeraria("funita", null,null);
		Funeraria fumita = new Funeraria("fumita", null,null);
		Funeraria fulanita = new Funeraria("fulanita", null,null);
		
		LocalTime hora = LocalTime.of(14, 30);
		
		Crematorio crematorio = new Crematorio ("crematorio","0054",100,null,"oro", null,funita); 
		Crematorio creno = new Crematorio ("creno","0089",78,null,"plata", null,fumita); 
		Crematorio cremita = new Crematorio ("cremita","0098",78,null,"oro", null,fulanita); 
		
		crematorio.setHoraEvento(hora);
		creno.setHoraEvento(hora);
		cremita.setHoraEvento(hora);
		
		crematorio.setIglesia(Iglesia.BUDISMO);
		creno.setIglesia(Iglesia.BUDISMO);
		cremita.setIglesia(Iglesia.BUDISMO);
		
		Cementerio cementerio = new Cementerio ("cementerio","2090",78,null,"oro", null,"cenizas",fulanita); 
		Cementerio cemi = new Cementerio ("cemi","9089",78,null,"oro", null,"cenizas",fumita); 
		Cementerio cemito = new Cementerio ("cemito","5490",78,null,"oro", null,"cenizas",funita); 
		
		ArrayList<Establecimiento> cementerios = new ArrayList<Establecimiento>();
		
		cementerios.add(cementerio);
		cementerios.add(cemi);
		cementerios.add(cemito);
		
		crematorio.cambiarHorarios(cementerios);
		
		System.out.println(cementerio.getHorarioEventos());
		System.out.println(cemi.getHorarioEventos());
		System.out.println(cemito.getHorarioEventos());
	}
	
	
	
	
	

}
