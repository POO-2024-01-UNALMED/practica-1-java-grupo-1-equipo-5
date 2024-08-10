package uiMain;

import java.util.ArrayList;
import java.util.Scanner;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.inventario.*;

public class FuncionalidadExhumacion {
	
	public static void funcionalidadExhumacion() {
		
		Scanner scanner = new Scanner(System.in);
		
		
		Cliente cliente=null;
		Tumba tumba=null;
		Cementerio cementerio =null;
		
		//Breve descripción de la funcionalidad para los usuarios
		System.out.println("La exhumación es el proceso de retirar un cuerpo de su lugar de sepultura");
		System.out.println();
		
		//Buscar cliente 
		
		System.out.println("[1] Buscar cliente por su CC");
		System.out.println("[2] Buscar cliente por cementerio");
		
		
		System.out.print("Ingrese el índice correspondiente: ");
		int indice = scanner.nextInt();
		
		//Validación de índice
		while (indice<1 || indice>2) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
		}
		
		switch(indice) {
			
		case 1:
			//Validación cliente
			while(cliente==null) {
				System.out.print("Ingrese CC del cliente: ");
				long CC = scanner.nextLong();
				//Busca al cliente en todas funerarias 
				cliente =Establecimiento.examinarCliente(CC);
				
				//Opción en caso de no estar registrado
				if(cliente==null) {
					System.out.println("El cliente no se encuentra registrado");
					System.out.print("Ingrese CC del cliente: ");
					CC = scanner.nextLong();
				}
			}
				if(!(cliente.getInventario() instanceof Tumba)) {
					System.out.println("El cliente está registrado pero no es apto para la exhumación"); cliente=null;
				}
				
				break;
				
		
		case 2:
			
			//Se escoge la funeraria con la que se va a realizar el procedimiento
			ArrayList<Establecimiento> funerarias =Establecimiento.filtarEstablecimiento("funeraria");
			System.out.println("Seleccione la funeraria correspondiente");
			indice=0;
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
			Funeraria funeraria=(Funeraria) funerarias.get(indiceFuneraria-1);
			
			System.out.println("[1] Buscar cliente en cementerios de cuerpos");
			System.out.println("[2] Buscar clientes con urna fija o tumba marcada como 'default')");
			
			System.out.print("Ingrese el índice correspondiente: ");
			indice = scanner.nextInt();
			
			//Validación de índice
			while (indice<1 || indice>2) {
				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
				indice=scanner.nextInt();
			}
			
			switch (indice) {
				case 1:
					
					System.out.println("Clientes mayor de edad");
					
					ArrayList<Cliente> clientes= funeraria.buscarCliente("cuerpos", "adulto");
					
					indice=1;
					for(Cliente auxCliente:clientes) {
						System.out.println("["+indice+"] "+auxCliente);
						indice+=1;
					}
					System.out.println();
					System.out.println("Clientes menor de edad");
					
					ArrayList<Cliente> clientesNiño= funeraria.buscarCliente("cuerpos", "niño");
					
					for(Cliente auxCliente:clientesNiño) {
						System.out.println("["+indice+"] "+auxCliente);
						indice+=1;
					}
					
					clientes.addAll(clientesNiño);
					
					System.out.print("Ingrese el índice correspondiente: ");
					indice = scanner.nextInt();
					
					//Validación de índice
					while (indice<1 || indice>clientes.size()) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
					}
					
					cliente=clientes.get(indice-1);		
					
					break;
					
				case 2:
					
					System.out.println("[1] Buscar tumbas marcadas como 'default'");
					System.out.println("[2] Buscar urnas marcadas como 'default'");
					
					System.out.print("Ingrese el índice correspondiente: ");
					indice = scanner.nextInt();
					
					//Validación de índice
					while (indice<1 || indice>2) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
					}
					
					
					//Se traen todos los cementerios por funeraria
					ArrayList<Establecimiento> cementeriosPorFuneraria=Establecimiento.buscarPorFuneraria(funeraria, "cementerio");
					
					String tipo=null;
					String mensaje1=null;
					String mensaje2=null;
					
					switch(indice) {
					
						case 1:
							tipo="cuerpos";
							mensaje1="Cementerios de cuerpos";
							mensaje2="Cantidad tumbas default";
							break;
						
						case 2: 
							tipo="cenizas";
							mensaje1="Cementerios de cenizas";
							mensaje2="Cantidad urnas default";
							break;
								
					}//Fin switch secundario
					
					//Se traen todos los cementerios de la funeraria con el atributo de tipo correspondiente ("cuerpos" o "cenizas")
					ArrayList<Establecimiento> cementerios=Cementerio.cementerioPorTipo(cementeriosPorFuneraria, tipo);
					
					System.out.println(mensaje1);
					indice=1;
					for(Establecimiento auxCementerio:cementerios) {
						int cantidadDefault=((Cementerio) auxCementerio).inventarioDefault().size();
						System.out.println("["+indice+"] "+auxCementerio+" - "+mensaje2+": "+cantidadDefault);
						indice+=1;
					}
					
					System.out.print("Ingrese el índice correspondiente: ");
					indice = scanner.nextInt();
					
					//Validación de índice
					while (indice<1 || indice>cementerios.size()) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
					}
					
					System.out.println(")
					
				
				break;
				
			}//Fin switch
			
		
			
		
		}
		
		
		
	}
	

}
