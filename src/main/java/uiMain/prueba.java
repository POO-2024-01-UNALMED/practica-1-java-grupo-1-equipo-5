package uiMain;

import java.util.ArrayList;


import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.establecimientos.*;
public class prueba {
	
	public static void main(String[] args) {
		
		CuentaBancaria c= new CuentaBancaria();
		
		Familiar b= new Familiar("mario",123,45,"345",c,"padre",17);
		Familiar e= new Familiar("mario",123,45,"345",c,"conyugue",17);
		
		ArrayList<Familiar> familiar=new ArrayList<Familiar>();
		familiar.add(b);
		familiar.add(e);
		
		//Cliente a = new Cliente("yina",17,"6789","oro",familiar);
		
		//System.out.println(a.autorizar());
		
		Familiar[] family= new Familiar[0];
		
		family=new Familiar[2];
		
		
		int eh=family.length;
		System.out.println(eh);
		
		//pruebas metodo buscarEstablecimientos
		
		CuentaBancaria cuenta = new CuentaBancaria();
		Empleado empleadito = new Empleado("empli", 123, 34, "345", cuenta ,"noche", "de todo un poco");
		
		Funeraria funita = new Funeraria("funita", cuenta,cuenta);
		Funeraria fumita = new Funeraria("fumita", cuenta,cuenta);
		
		Crematorio crematorio = new Crematorio ("cremi","56",35,cuenta,"oro", empleadito,funita); 
		Crematorio creno = new Crematorio ("cremi",56,78,cuenta,"oro", empleadito,fumita); 
		Crematorio cremita = new Crematorio ("cremi",56,78,cuenta,"oro", empleadito,funita); 
		
		Cementerio cementerio = new Cementerio ("cremi",56,78,cuenta,"oro", empleadito,"cenizas",fumita); 
		Cementerio cemi = new Cementerio ("cremi",56,78,cuenta,"oro", empleadito,"cuerpos",fumita); 
		Cementerio cemito = new Cementerio ("cremi",56,78,cuenta,"oro", empleadito,"cenizas",funita); 

		System.out.println(fumita.buscarCementerios("cuerpos", a));

		
		
	
		
	
		
		//String nombre,int edad, String ubicacion, String plan,ArrayList<Familiar> familiares
		//String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria, String parentesco, int acompañantes
	}
	

}
