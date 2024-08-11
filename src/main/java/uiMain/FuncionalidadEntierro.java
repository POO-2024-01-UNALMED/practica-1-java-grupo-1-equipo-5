package uiMain;

import java.util.ArrayList;
import java.util.Scanner;

import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Persona;

public class FuncionalidadEntierro {
	
	public static void funcionalidadEntierro() {
		
		Scanner scanner = new Scanner(System.in);
		
		Funeraria funeraria;
		Cliente cliente;
		
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
				System.out.println("[2] Buscar cliente menor de edad por el acudiente responsable");
				
				System.out.print("Ingrese el índice correspondiente: ");
				int indiceCliente = scanner.nextInt();
				
				//Validación de índice
				while (indiceCliente<1 || indiceCliente>2) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indiceCliente=scanner.nextInt();
				}
				
				switch(indice) {
					case 1:
						//Buscar los clientes adultos (que tengan atributo CC distinto de 0) por la funeraria escogida
						indice=0;
						for(Cliente auxCliente:funeraria.buscarCliente("adulto")) {
							indice+=1;
							System.out.println("["+indice+"] "+ auxCliente);
						}
					
						//Validacion cliente adulto
						if(funeraria.buscarCliente("adulto").size()!=0) {
							System.out.print("Ingrese el índice del cliente: ");
							indice=scanner.nextInt();
							//Validación número
							while(indice<1 || indice>funeraria.buscarCliente("adulto").size()) {
								System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
								indice=scanner.nextInt();
							}
							cliente=funeraria.buscarCliente("adulto").get(indice-1);
							
						}//Validación existencia cliente adulto
						
						break;
						
					case 2:
						//Buscar los clientes niños (que tengan atributo CC igual a 0) por la funeraria escogida
						
						indice=0;
						System.out.println("Se mostrarán los acudientes responsables de los clientes");
						for(Cliente auxCliente:funeraria.buscarCliente("niño")) {
							indice+=1;
							Persona familiar =auxCliente.designarFamiliar(auxCliente.getFamiliares());
							System.out.println("["+indice+"] Responsable: "+ familiar);
						}
						//Validacion cliente adulto
						if(funeraria.buscarCliente("niño").size()!=0) {
							
							System.out.print("Ingrese el índice del cliente: ");
							indice=scanner.nextInt();
							//Validación número
							while(indice<1 || indice>funeraria.buscarCliente("niño").size()) {
								System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
								indice=scanner.nextInt();
							}
							cliente=funeraria.buscarCliente("niño").get(indice-1);
							System.out.println("Cliente menor de edad: "+cliente);
							
						}//Fin if validación existencia de clientes menores de edad
						
						break;
						
				}//Fin switch
				
				
		
				
				
				
				
				
	}//Fin metodo funcionalidadEntierro
}//Fin clase
