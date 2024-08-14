package uiMain;

import java.time.LocalTime;
import java.util.ArrayList;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.establecimientos.Crematorio;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.establecimientos.Iglesia;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.inventario.Tumba;
import gestorAplicacion.inventario.Urna;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Familiar;

public class PruebaEntierro {
	
	public static void main(String[] args) {
		
		CuentaBancaria cuenta = new CuentaBancaria(123, "Alfredo", 1000000, "Ala");
		Funeraria funita = new Funeraria("funita", cuenta,cuenta);
		Funeraria fumita = new Funeraria("fumita", cuenta,cuenta);
		Funeraria fulanita = new Funeraria("fulanita", cuenta,cuenta);
		
		Familiar b= new Familiar("Mario",123,45,"345",cuenta,"padre",17);
		Familiar e= new Familiar("Alberto",123,45,"345",cuenta,"conyugue",17);
		
		
		
		ArrayList<Familiar> familiar=new ArrayList<Familiar>();
		familiar.add(b);
		familiar.add(e);
		
		Cliente a1 = new Cliente("a1",123,17,null,"oro",familiar);
		Cliente b1 = new Cliente("b1",123,17,null,"oro",familiar);
		Cliente c1 = new Cliente("c1",123,17,null,"oro",familiar);
		Cliente d1 = new Cliente("d1",123,17,null,"oro",familiar);
		Cliente e1 = new Cliente("e1",123,17,null,"oro",familiar);
		
		funita.agregarCliente(e1);
		fumita.agregarCliente(d1);
		fulanita.agregarCliente(c1);
		fumita.agregarCliente(a1);
		
		//Objetos crematorio-cementerio
		Crematorio crematorio = new Crematorio ("crematorio","0054",100,null,"oro", null,funita); 
		Crematorio creno = new Crematorio ("creno","0089",78,null,"oro", null,fumita); 
		Crematorio cremita = new Crematorio ("cremita","0098",78,null,"oro", null,fulanita); 
		
		Cementerio cementerio = new Cementerio ("cementerio","2090",78,null,"oro", null,"cenizas",fulanita); 
		Cementerio cemi = new Cementerio ("cemi","9089",78,null,"oro", null,"cenizas",fumita); 
		Cementerio cemito = new Cementerio ("cemito","5490",78,null,"oro", null,"cenizas",funita); 
		
		Cementerio cementerio1 = new Cementerio ("cementerio1","2090",78,null,"oro", null,"cuerpos",fulanita); 
		Cementerio cemi1 = new Cementerio ("cemi1","9089",78,null,"oro", null,"cuerpos",fumita); 
		Cementerio cemito1 = new Cementerio ("cemito1","5490",78,null,"oro", null,"cuerpos",funita);
		
		//Objetos de la clase Empleado
		Empleado empleado1 = new Empleado("Alberto",12345,"3456",null,"mañana","sepulturero",900000);
		Empleado empleado2 = new Empleado("Maria",12345,"3456",null,"noche","sepulturero",900000);
		Empleado empleado3 = new Empleado("Anastasia",12345,"3456",null,"noche","cremador",900000);
		Empleado empleado4 = new Empleado("Gilberto",12345,"3456",null,"mañana","cremador",900000);
		Empleado empleado5 = new Empleado("Pepito",12345,"3456",null,"mañana","sepulturero",900000);
		Empleado empleado6 = new Empleado("Camila",12345,"3456",null,"tarde","cremador",900000);
		Empleado empleado7 = new Empleado("Santiago",12345,"3456",null,"tarde","sepulturero",900000);
		Empleado empleado8 = new Empleado("Anastasio",12345,"3456",null,"tarde","cremador",900000);
		
		
		//Urnas vacías
		
		Urna urna1=new Urna("Urnita1",cemi,70,1,"fija");
		Urna urna2=new Urna("Urnita2",cemi,90,0,"ordinaria");
		Urna urna3=new Urna("Urnita3",cemi,50,0,"ordinaria");
		Urna urna4=new Urna("Urnita4",cemi,60,1,"fija");
		
		Urna urna5=new Urna("default",cementerio,70,2,"fija");
		Urna urna6=new Urna("default",cementerio,50,2,"ordinaria");
		Urna urna7=new Urna("Urnita7",cementerio,40,2,"ordinaria");
		Urna urna8=new Urna("Urnita8",cementerio,60,1,"fija");
		
		Urna urna9=new Urna("default",cemito,70,0,"fija");
		Urna urna10=new Urna("Urnita10",cemito,50,1,"fija");
		Urna urna11=new Urna("Urnita11",cemito,40,2,"ordinaria");
		Urna urna12=new Urna("default",cemito,60,1,"fija");
		
		
		
		funita.agregarEmpleado(empleado1);
		funita.agregarEmpleado(empleado2);
		funita.agregarEmpleado(empleado3);
		
		fumita.agregarEmpleado(empleado5);
		fumita.agregarEmpleado(empleado6);
		
		fulanita.agregarEmpleado(empleado4);
		fulanita.agregarEmpleado(empleado7);
		fulanita.agregarEmpleado(empleado8);
		
		
		//Funcionalidad Exhumacion
		
		Cliente n1 = new Cliente("n1",123,35,null,"oro",familiar);
		Cliente n2 = new Cliente("n2",123,37,null,"oro",familiar);
		Cliente n3 = new Cliente("n3",123,39,null,"oro",familiar);
		Cliente n4 = new Cliente("n4",123,41,null,"oro",familiar);
		Cliente n5 = new Cliente("n5",123,25,null,"oro",familiar);
		
		Cliente n6 = new Cliente("n6",123,35,null,"oro",familiar);
		Cliente n7 = new Cliente("n7",123,37,null,"oro",familiar);
		Cliente n8 = new Cliente("n8",123,39,null,"oro",familiar);
		Cliente n9 = new Cliente("n9",123,41,null,"oro",familiar);
		Cliente n10= new Cliente("n10",123,25,null,"oro",familiar);
		
		Urna urna13=new Urna("Urnita13",cemi,70,1,"fija");
		Urna urna14=new Urna("Urnita14",cemi,90,0,"ordinaria");
		Urna urna15=new Urna("Urnita15",cemi,50,0,"ordinaria");
		
		
		Urna urna17=new Urna("default",cementerio,70,2,"fija");
		Urna urna18=new Urna("default",cementerio,50,2,"ordinaria");
		Urna urna19=new Urna("Urnita719",cementerio,40,2,"ordinaria");
		
		
		Urna urna21=new Urna("default",cemito,70,0,"fija");
		Urna urna22=new Urna("Urnita22",cemito,50,1,"fija");
		Urna urna23=new Urna("Urnita23",cemito,40,2,"ordinaria");

		
		Tumba tumba1=new Tumba("Tumbita1",cemi1,1.70,0);
		Tumba tumba2=new Tumba("Tumbita2",cemi1,1.50,0);
		Tumba tumba3=new Tumba("Tumbita3",cemito1,1.60,2);
		
		Tumba tumba4=new Tumba("Tumbita4",cemito1,1.50,1);
		Tumba tumba5=new Tumba("Tumbita5",cementerio1,1.70,2);
		Tumba tumba6=new Tumba("Tumbita6",cementerio1,1.70,2);
		
		Tumba tumba7=new Tumba("Tumbita7",cementerio1,1.70,2);
		Tumba tumba8=new Tumba("Tumbita8",cementerio1,1.40,2);
		Tumba tumba9=new Tumba("Tumbita9",cementerio1,1.40,2);
		
		
		System.out.println(funita.gestionEntierro(c1, Iglesia.BUDISMO, 1.5));
		System.out.println(cemito1.getEmpleado());
		System.out.println(cemito1.getIglesia());
		System.out.println(cemito1.getHoraEvento());
		
		System.out.println(c1.getEdad());
		System.out.println("");
		System.out.println(fulanita.buscarEmpleados(LocalTime.of(15, 10), "sepulturero"));
		cementerio1.generarHoras();
		System.out.println(cementerio1.getHorarioEventos());
		System.out.println(fulanita.buscarEmpleados(cementerio1.getHorarioEventos().get(0), "sepulturero"));
		
		System.out.println(cemi1.disponibilidadInventario("tumba", 1.5, 17));
		System.out.println(cementerio1.getInventario());
		
		System.out.println(tumba7.getCategoria()==tumba7.determinarCategoria(c1.getEdad()));
		
		Tumba tumbita1= new Tumba("default",cementerio1,1.5,17);
		System.out.println(tumbita1.getCategoria());
		
		//tumba7.generarAdornos("material");
		c1.setInventario(tumba7);
		System.out.println("Tumba "+tumba7.getInventarioFlores());
		System.out.println(c1.pagoInmediato("flores"));
		
	}

}
