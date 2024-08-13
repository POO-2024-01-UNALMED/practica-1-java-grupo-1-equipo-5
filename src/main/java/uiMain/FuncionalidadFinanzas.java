package uiMain; 

import java.util.ArrayList;

import java.util.Scanner;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.establecimientos.Crematorio;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.personas.Persona;
import gestorAplicacion.financiero.*;
import gestorAplicacion.financiero.Factura;


public class FuncionalidadFinanzas {
	
	
	public static void funcionalidadFinanzas() {
		
		Scanner scanner = new Scanner(System.in);
		
		ArrayList<Establecimiento> funerarias =Establecimiento.filtarEstablecimiento("funeraria");
		System.out.println("Seleccione la funeraria correspondiente");
		int indice=0;
		for(Establecimiento auxFuneraria:funerarias) {
			indice+=1;
			System.out.println("["+indice+"]"+auxFuneraria);
		}
	
		System.out.print("Ingrese el índice correspondiente: ");
		int indiceFuneraria=scanner.nextInt();
		
		
		while (indiceFuneraria<1 || indiceFuneraria>funerarias.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indiceFuneraria=scanner.nextInt();
		}
		
		Funeraria funeraria=(Funeraria) funerarias.get(indiceFuneraria-1);
		
		System.out.println("Que proceso quiere hacer ");
		System.out.println("[1] Cobro clientes");
		System.out.println("[2] Pagar facturas ");
		System.out.println("[3] Pago empleados");
		
		System.out.print("Ingrese el índice correspondiente: ");
		int indiceHacer = scanner.nextInt();
	    
		switch(indiceHacer) {
		case 1:
			ArrayList<Establecimiento> cementerios = funeraria.cementerios();
			indice=0;
			for(Establecimiento cementerio: cementerios) {
				indice+=1;
				System.out.println("["+indice+"] "+ cementerio);
			}
			
			System.out.print("Ingrese el índice del cementerio: ");
			int indiceCementerio = scanner.nextInt();
			
			while(indice<1 || indice>cementerios.size()) {
				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
				indiceCementerio=scanner.nextInt();
				
			}
			Cementerio cementerio = (Cementerio) cementerios.get(indiceCementerio - 1);
			ArrayList<Cliente> clientes = cementerio.getClientes();
			indice = 0;
			for(Cliente cliente: clientes) {
				indice+=1;
				System.out.println("["+indice+"] "+ cliente);
			}
			
			System.out.print("Ingrese el índice de los clientes: ");
			int indiceCliente = scanner.nextInt();
			
			while(indice<1 || indice>clientes.size()) {
				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
				indiceCliente=scanner.nextInt();
			}
			Cliente cliente = (Cliente) clientes.get(indiceCliente - 1);
			funeraria.cobroServiciosClientes(cliente);	
			System.out.println("Cobro de facturas del cliente: "+ cliente.getNombre()+", realizado correctamente");
			break;
		case 2:
			
		case 3:
			
	
		}
	}
	
public static void main(String[] args) {
		
	CuentaBancaria cuenta2 = new CuentaBancaria(19934, "funita", 3993, "BANCOLOMBIA");
	    CuentaBancaria cuenta1 = new CuentaBancaria(199919, "a1", 39999, "BBVA");
		Funeraria funita = new Funeraria("funita", null, null);
		Funeraria fumita = new Funeraria("fumita", null, null);
		Funeraria fulanita = new Funeraria("fulanita", null, null);
		Crematorio crematorio = new Crematorio ("crematorio","0054",100,null,"oro", null,funita); 
		Crematorio creno = new Crematorio ("creno","0089",78,null,"oro", null,fumita); 
		Crematorio cremita = new Crematorio ("cremita","0098",78,null,"oro", null,fulanita); 
		
		Cementerio cementerio = new Cementerio ("cementerio","2090",78,null,"oro", null,"cenizas",fulanita); 
		Cementerio cemi = new Cementerio ("cemi","9089",78,null,"oro", null,"cenizas",fumita); 
		Cementerio cemito = new Cementerio ("cemito","5490",78,null,"oro", null,"cenizas",funita); 
		CuentaBancaria cuenta3 = new CuentaBancaria(32323, "b", 32324, "BBVA");
		CuentaBancaria cuenta4 = new CuentaBancaria(342343, "e", 32, "BBVA");
		
		
		Cementerio cementerio1 = new Cementerio ("cementerio1","2090",78,null,"oro", null,"cuerpos",fulanita); 
		Cementerio cemi1 = new Cementerio ("cemi1","9089",78,null,"oro", null,"cuerpos",fumita); 
		Cementerio cemito1 = new Cementerio ("cemito1","5490",78,null,"oro", null,"cuerpos",funita);
		Familiar b= new Familiar("Mario",123,45,"345",cuenta3,"padre",17);
		Familiar e= new Familiar("Alberto",123,45,"345",cuenta4,"conyugue",17);
		ArrayList<Persona> familiar=new ArrayList<Persona>();
		familiar.add(b);
		familiar.add(e);

		Cliente a1 = new Cliente("a1",123,18,null,"oro",familiar);
		Cliente b1 = new Cliente("b1",123,17,null,"oro",familiar);
		funita.agregarCliente(a1);
		fumita.agregarCliente(b1);
		Funeraria.setCuentaAhorros(cuenta2);
		a1.setCuentaBancaria(cuenta1);
		cemito1.agregarCliente(a1);
		Factura factura1 = new Factura("Funeral", 1000, "2772", a1, "Establecimiento");
		a1.agregarFactura(factura1);
	funcionalidadFinanzas();
	

}
}
