package uiMain;

import java.util.ArrayList;

import gestorAplicacion.personas.Cliente;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.Familiar;
public class prueba {
	
	public static void main(String[] args) {
		
		CuentaBancaria c= new CuentaBancaria();
		
		Familiar b= new Familiar("mario",123,45,"345",c,"padre",17);
		Familiar e= new Familiar("mario",123,45,"345",c,"conyugue",17);
		
		ArrayList<Familiar> familiar=new ArrayList<Familiar>();
		familiar.add(b);
		familiar.add(e);
		
		Cliente a = new Cliente("yina",17,"6789","oro",familiar);
		
		System.out.println(a.autorizar());
		
		
		//String nombre,int edad, String ubicacion, String plan,ArrayList<Familiar> familiares
		//String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria, String parentesco, int acompañantes
	}
	

}
