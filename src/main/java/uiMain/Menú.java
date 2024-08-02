package uiMain;

import java.util.Scanner;
import java.util.ArrayList;
import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;

public class Menú {
	
	
	
	public static void funcionalidadCrematorio() {
		
		Scanner scanner = new Scanner(System.in);
		
		Funeraria funeraria=null;
		Cliente cliente=null;
		Crematorio crematorio=null;
		
		//Se escoge la funeraria con la que se va a realizar el procedimiento
		ArrayList<Establecimiento> funerarias =Establecimiento.filtarEstablecimiento("funeraria");
		System.out.println("Seleccione la funeraria correspondiente");
		int indice=0;
		for(Establecimiento auxFuneraria:funerarias) {
			indice+=1;
			System.out.println("["+indice+"]"+auxFuneraria);
		}
	
		System.out.print("Ingrese el índice correspondiente: ");
		int indiceFuneraria=scanner.nextInt();
		
		//Se valida que se ingrese un índice adecuado para continuar el proceso
		while (indiceFuneraria<1 || indiceFuneraria>funerarias.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indiceFuneraria=scanner.nextInt();
		}
		
		//Se realiza especialización de tipos(Establecimiento - clase padre a Funeraria - clase hija) y se asigna la funeraria correspondiente
		funeraria=(Funeraria) funerarias.get(indiceFuneraria-1);
		
		
		//Proceso de selección de cliente
		System.out.println("[1] Buscar cliente mayor de edad");
		System.out.println("[2] Buscar cliente menor de edad");
		
		System.out.print("Ingrese el índice correspondiente: ");
		int indiceCliente = scanner.nextInt();
		
		//Validación de índice
		while (indiceCliente<1 || indiceCliente>2) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indiceCliente=scanner.nextInt();
		}
		
		switch (indiceCliente) {
			
			case 1:
				//Para clientes mayores de edad se puede buscar al cliente de dos formas
				//Busca a los clientes de la funeraria seleccionada por su atributo CC
				System.out.println("[1] Buscar cliente por su CC");
				//Devuelve una lista de los clientes de la funeraria seleccionada anteriormente
				System.out.println("[2] Buscar cliente por funeraria");
				System.out.print("Ingrese índice: ");
				indiceCliente = scanner.nextInt();
				//Validación de índice
				while (indiceCliente<1 || indiceCliente>2) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indiceCliente=scanner.nextInt();
				}
				
				switch(indiceCliente) {
					case 1:
						System.out.print("Ingrese el CC del cliente: ");
						long IDcliente=scanner.nextLong();
						cliente =funeraria.buscarCliente(IDcliente);
						//Validar CC correcto
						while(cliente==null) {
							System.out.println("Numero de CC incorrecto. Vuelve a ingresar CC del cliente: ");
							IDcliente=scanner.nextLong();
							cliente =funeraria.buscarCliente(IDcliente);
						}
						break;
					
					case 2:
						//Buscar los clientes adultos (que tengan atributo CC distinto de 0) por la funeraria escogida
						indice=0;
						for(Cliente auxCliente:funeraria.buscarCliente("adulto")) {
							indice+=1;
							System.out.println("["+indice+"] "+ auxCliente);
						}
						
						System.out.print("Ingrese el índice del cliente: ");
						indice=scanner.nextInt();
						//Validacion
						while(indice<1 || indice>funeraria.buscarCliente("adulto").size()) {
							System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
							indice=scanner.nextInt();
						}
						cliente=funeraria.buscarCliente("adulto").get(indice-1);
						break;	
						
				}// Fin switch secundario
				break;
					
				
			case 2:
				//Buscar los clientes menores de edad por la funeraria escogida
				indice=0;
				for(Cliente auxCliente:funeraria.buscarCliente("niño")) {
					indice+=1;
					System.out.print("["+indice+"] "+ auxCliente);
				}
				
				System.out.print("Ingrese el índice del cliente: ");
				indice=scanner.nextInt();
				//Validacion
				while(indice<1 || indice>funeraria.buscarCliente("adulto").size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
				}
				cliente=funeraria.buscarCliente("niño").get(indice-1);
				break;
		}
		
		//Disponibilidad en el crematorio
		
		//Crematorios que coincidan con la capacidad de acompañantes del cliente y con la afiliación del cliente
		ArrayList<Establecimiento> crematorios=funeraria.buscarEstablecimientos("crematorio", cliente);
		
		System.out.println("Su afiliación es de tipo "+cliente.getAfiliacion());
		System.out.print("Los crematorios disponibles para la afiliación "+cliente.getAfiliacion()+" son:");
		
		indice=1;
		for(Establecimiento auxCrematorio:crematorios) {
			System.out.println("["+indice+"] "+auxCrematorio);
		}
		System.out.print("Ingrese el índice del crematorio deseado: ");
		indice=scanner.nextInt();
		
		//Validación 
		while(indice<1 || indice>crematorios.size()-1) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
		}
		
		crematorio=(Crematorio)crematorios.get(indice-1);
		
		//Se debe establecer el empleado del crematorio
		
		System.out.print("Las jornadas disponibles son: ");
		
		//Se escoge empleado por jornada
		String[] jornadas = {"Mañana","Tarde","Noche"};
		
		for(int i=0;i<3;i++) {
			System.out.println("["+(i+1)+"] "+jornadas[i]);
		}
		
		System.out.print("Ingrese el índice de la jornada deseada: ");
		indice=scanner.nextInt();
		
		//Validación 
		while(indice<1 || indice>3) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			}
		//Este método devuelve todos los empleados con atributo cargo cremador, con el atributo de jornada adecuado y que tengan su stributo disponible como true
		ArrayList<Empleado> empleados = funeraria.buscarEmpleados("cremador", jornadas[indice-1]);
		
		System.out.println("Empleados disponibles en la jornada seleccionada");
		indice=1;
		for (Empleado auxEmpleado:empleados) {
			System.out.println("["+indice+"] "+auxEmpleado);
		}
		
		System.out.print("Ingrese el índice del empleado deseado: ");
		indice=scanner.nextInt();
		
		//Validación 
		while(indice<1 || indice>empleados.size()-1) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			}
		
		Empleado empleadoCrematorio=empleados.get(indice-1);
		
		if(cliente.getCC()==0) {
			
		}
		
		
		
	}
	
	public static void main(String[] args) {
		
		//Objetos de prueba
		//Funerarias
		Funeraria funita = new Funeraria("funita", null,null);
		Funeraria fumita = new Funeraria("fumita", null,null);
		Funeraria fulanita = new Funeraria("fulanita", null,null);
		Funeraria funcita = new Funeraria("funcita", null,null);
		
		//Cliente
		Familiar b= new Familiar("Mario",123,45,"345",null,"padre",17);
		Familiar e= new Familiar("Alberto",123,45,"345",null,"conyugue",17);
		
		
		
		ArrayList<Persona> familiar=new ArrayList<Persona>();
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
		funcita.agregarCliente(b1);
		fumita.agregarCliente(a1);
		
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("[1] Funcionalidad crematorio");
		
		System.out.print("Ingrese el índice de la funcionalidad: ");
		int opcion =scanner.nextInt();
		
		switch (opcion) {
			
		case 1:
			funcionalidadCrematorio();
			break;
			
		default:
			System.out.println("Número fuera de rango");
		}
		
		
		
	}
	

	

}
