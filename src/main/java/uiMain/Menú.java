package uiMain;

import java.util.Scanner;
import java.util.ArrayList;
import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;

public class Menú {
	
	public static void funcionalidadCrematorio() {
		
		Scanner scanner = new Scanner(System.in);
		
		//Se escoge la funeraria con la que se va a realizar el procedimiento
		ArrayList<Establecimiento> funerarias =Establecimiento.filtarEstablecimiento("funeraria");
		System.out.println("Seleccione la funeraria correspondiente");
		int indice=0;
		for(Establecimiento funeraria:funerarias) {
			indice+=1;
			System.out.println("["+indice+"]"+funeraria);
		}
	
		System.out.print("Ingrese el índice correspondiente: ");
		int indiceFuneraria=scanner.nextInt();
		
		//Se valida que se ingrese un índice adecuado para continuar el proceso
		while (indiceFuneraria<1 & indiceFuneraria>funerarias.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indiceFuneraria=scanner.nextInt();
		}
		
		//Se realiza especialización de tipos(Establecimiento - clase padre a Funeraria - clase hija) y se asigna la funeraria correspondiente
		Funeraria funeraria=(Funeraria) funerarias.get(indiceFuneraria-1);
		
		
		//Proceso de selección de cliente
		System.out.println("[1] Buscar cliente mayor de edad");
		System.out.println("[2] Buscar cliente menor de edad");
		System.out.println("[3] Registrar cliente");
		
		System.out.print("Ingrese el índice correspondiente: ");
		int indiceCliente = scanner.nextInt();
		
		//Validación de índice
		while (indiceCliente<1 & indiceCliente>3) {
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
				indiceCliente = scanner.nextInt();
				//Validación de índice
				while (indiceCliente<1 & indiceCliente>2) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indiceCliente=scanner.nextInt();
				}
				
				switch(indiceCliente) {
					case 1:
						System.out.print("Ingrese el CC del cliente: ");
						long IDcliente=scanner.nextLong();
						Cliente cliente =funeraria.buscarCliente(IDcliente);
						//Validar CC correcto
						while(cliente==null) {
							System.out.println("Numero de CC incorrecto. Vuelve a ingresar CC del cliente: ");
							IDcliente=scanner.nextLong();
							cliente =funeraria.buscarCliente(IDcliente);
						}
						break;
					
					case 2:
						funeraria.buscarCliente("adulto");
						break;	
				}// Fin switch secundario
				break;
					
				
			case 2:
				funeraria.buscarCliente("niño");
				break;
			case 3:
				
				//Proceso de registro nuevo cliente
				System.out.println("[1] Cliente mayor de edad");
				System.out.println("[2] Cliente menor de edad");
				
				System.out.print("Ingrese el índice correspondiente: ");
				indiceCliente = scanner.nextInt();
				
				//Validación de índice
				while (indiceCliente<1 & indiceCliente>2) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indiceCliente=scanner.nextInt();
				}
				//Validación de datos cliente
				System.out.print("Ingrese nombre del cliente: ");
				String nombre= scanner.nextLine();
				System.out.print("Ingrese la edad del cliente: ");
				int edad=scanner.nextInt();
				//Si el cliente es mayor de edad se añade atributo CC y su edad debe ser mayor a 18 años
				if(indiceCliente==1) {
					//Validación de edad mayor a 18 años 
					while(edad<18) {
						System.out.print("La edad del cliente debe ser mayor de 18 años. Ingrese nuevamente la edad del cliente: ");
						edad=scanner.nextInt();
					}//Fin while
					System.out.print("Ingrese CC del cliente: ");
					long CC=scanner.nextLong();
					while(Establecimiento.examinarCliente(CC)!= null) {
						System.out.print("El CC del cliente ya está registrado. Ingrese CC del cliente: ");
						CC=scanner.nextLong();
					}
					
				}else {
					
				}
					
				Cliente cliente= new Cliente(nombre, 123, 14, null,"oro", null);
				funeraria.agregarCliente(null);
			default:
				System.out.println("Número fuera de rango");
		}
		
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("[1] Funcionalidad crematorio");
		
		int opcion =scanner.nextInt();
		
		switch (opcion) {
			
		case 1:
			
		default:
			System.out.println("Número fuera de rango");
		}
			
		
		
		
		
	}

}
