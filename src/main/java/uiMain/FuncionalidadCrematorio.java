package uiMain;

import java.util.ArrayList;
import java.util.Scanner;

import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.personas.Cliente;

public class FuncionalidadCrematorio {
	

	
public static void funcionalidadCrematorio() {
		
		Scanner scanner = new Scanner(System.in);
		
		Funeraria funeraria;
		Cliente cliente=null;
		
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
		while (indiceFuneraria<1 & indiceFuneraria>funerarias.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indiceFuneraria=scanner.nextInt();
		}
		
		//Se realiza especialización de tipos(Establecimiento - clase padre a Funeraria - clase hija) y se asigna la funeraria correspondiente
		funeraria=(Funeraria) funerarias.get(indiceFuneraria-1);
		
		
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
							System.out.print("["+indice+"] "+ auxCliente);
						}
						
						System.out.print("Ingrese el índice del cliente: ");
						indice=scanner.nextInt();
						//Validacion
						while(indice<1 & indice>funeraria.buscarCliente("adulto").size()) {
							System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
							indice=scanner.nextInt();
						}
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
				while(indice<1 & indice>funeraria.buscarCliente("adulto").size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
				}
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
				long CC =0;
				int edad=scanner.nextInt();
				//Si el cliente es mayor de edad se añade atributo CC y su edad debe ser mayor a 18 años
				if(indiceCliente==1) {
					//Validación de edad mayor a 18 años 
					while(edad<18) {
						System.out.print("La edad del cliente debe ser mayor de 18 años. Ingrese nuevamente la edad del cliente: ");
						edad=scanner.nextInt();
					}//Fin while
					//Si el cliente es mayor de edad se asigna un CC distinto de 0
					System.out.print("Ingrese CC del cliente: ");
					CC=scanner.nextLong();
					//Se valida que el CC del cliente no esté registrado en ninguna funeraria
					while(Establecimiento.examinarCliente(CC)!= null) {
						System.out.print("El CC del cliente ya está registrado. Ingrese CC del cliente: ");
						CC=scanner.nextLong();
					}//Fin while
					System.out.print("Ingrese edad del cliente: ");
				}else {
					while(edad>18) {
						System.out.print("La edad del cliente debe ser menor de 18 años. Ingrese nuevamente la edad del cliente: ");
						edad=scanner.nextInt();
					}//Fin while
					
				}//Fin else
				//Asignar al cliente nuevo con uno ya registrado para obtener datos de cuenta bancaria, afiliación y familiares
				System.out.println("Asocie el nuevo cliente con uno ya registrado en la funeraria: ");
				
				indice=0;
				//Buscar cliente en funeraria
				for(Cliente auxCliente:funeraria.buscarCliente("adulto")) {
					indice+=1;
					System.out.println("["+indice+"] "+auxCliente);
				}
				//Asignar Cliente ya registrado como familiar de cliente nuevo
				int indiceFamiliar=scanner.nextInt();
				while (indiceFamiliar<1 & indiceFamiliar>funeraria.buscarCliente("adulto").size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indiceFamiliar=scanner.nextInt();
				}
				Cliente familiarAsociado=funeraria.buscarCliente("adulto").get(indiceFamiliar-1);
				
				//Se crea la instancia del nuevo cliente y con el que se desarrollará el proceso
				cliente= new Cliente(nombre, CC, edad, null,familiarAsociado.getAfiliacion(),null);
				//Se asignan los familiares de acuerdo al parentesco 
				System.out.println("Indique el índice del parentesco del cliente nuevo con el antiguo");
				System.out.println("[1] Padre/Madre");
				System.out.println("[2] Hijo/Hija");
				System.out.println("[3] Hermano/Hermana");
				System.out.println("[4] Cónyuge");
				indiceFamiliar=scanner.nextInt();
				//Validación de indice correcto
				while(indiceFamiliar<1 & indiceFamiliar>4) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indiceFamiliar=scanner.nextInt();
				}
				//Se asigna de acuerdo a parentescos disponibles
				String parentesco=null;
				if(indiceFamiliar==1) {
					parentesco="padre";
				}else if(indiceFamiliar==2) {
					parentesco="hijo";
				}else if(indiceFamiliar==3) {
					parentesco="hermano";
				}else {
					parentesco="conyuge";
				}
				//Se usa el método para agregar a familiares directos si no los hay igual se agrega como familiar al Cliente Antiguo
				cliente.asignarParentesco(familiarAsociado,parentesco); 
				funeraria.agregarCliente(cliente);
			
			default:
				System.out.println("Número fuera de rango");
		}
		
		System.out.print(cliente);
		System.out.print(funeraria);
		
	}
	
}


