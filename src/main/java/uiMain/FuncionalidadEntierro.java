package uiMain;

import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Scanner;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.establecimientos.Iglesia;
import gestorAplicacion.inventario.Inventario;
import gestorAplicacion.inventario.Tumba;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Persona;


public class FuncionalidadEntierro {
	
	public static void funcionalidadEntierro() {
		
		Scanner scanner = new Scanner(System.in);
		
		Funeraria funeraria;
		Cliente cliente=null;
		Iglesia iglesia;
		Tumba tumba=null;
		
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
				
				switch(indiceCliente) {
					case 1:
						//Buscar los clientes adultos (que tengan atributo CC distinto de 0) por la funeraria escogida
						indice=0;
						for(Cliente auxCliente:funeraria.buscarCliente("adulto")) {
							indice+=1;
							System.out.println("["+indice+"] "+ auxCliente);
						}
						System.out.println(cliente);
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
							System.out.println(cliente);
							
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
				
				
				System.out.println("Indique su religión para celebrar el entierro");
				
				//Iglesias disponibles
				//ArrayList<Iglesia> iglesias = new ArrayList<Iglesia>();
				indice=1;
				for(Iglesia auxIglesia:Iglesia.values()) {
					System.out.println("["+indice+"] "+auxIglesia);
					indice+=1;
				}
				
				System.out.print("Indique el índice de la religión escogida: ");
				indice=scanner.nextInt();
				//Validación 
				while(indice<1 || indice>Iglesia.values().length) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
					
				}
				
				iglesia=Iglesia.values()[indice-1];
				
				System.out.print("Ingrese una hora para realizar la ceremonia religiosa (0-23): ");
				int hora=scanner.nextInt();
				//Validación 
				while(hora<0 || hora>23) {
					System.out.print("Hora fuera de rango. Ingrese una hora para realizar la ceremonia religiosa (0-23): : ");
					hora=scanner.nextInt();
					
				}
				System.out.print("Ingrese un número que indique los minutos (0-59): ");
				int minutos=scanner.nextInt();
				//Validación 
				while(minutos<0 || minutos>59) {
					System.out.print("Minutos fuera de rango. Indique los minutos (0-59): ");
					hora=scanner.nextInt();
			
				}
				
				LocalTime auxHora = LocalTime.of(hora, minutos);
				
				//////////////////////////////////////////Invitación 
				
				System.out.print("Para organizar los detalles del entierro. Indique la estatura del cliente: ");
				double estatura =scanner.nextDouble();
				
				while(estatura<0 || estatura>2.50) {
					System.out.print("Estatura fuera de rango. Ingrese nuevamente la estatura del cliente: ");
					estatura=scanner.nextDouble();
			
				}
				
				System.out.println("Cementerios afiliación "+cliente.getAfiliacion());
				ArrayList<Establecimiento> cementerios =funeraria.gestionEntierro(cliente, iglesia, auxHora, estatura);
				
				indice=1;
				for(Establecimiento cementerio:cementerios) {
		    		 Cementerio auxCementerio=(Cementerio)cementerio;
		    		 System.out.println("["+indice+"] "+auxCementerio+" con ("+auxCementerio.disponibilidadInventario("tumba", estatura, cliente.getEdad()).size()+") tumbas disponibles");
				}
				
				System.out.print("Indique el índice del cementerio escogido: ");
				indice=scanner.nextInt();
				
				while(indice<1 || indice>cementerios.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
				}
				
				
				Cementerio cementerio = (Cementerio) cementerios.get(indice-1);
				
				ArrayList<Inventario> inventarioDisponible=cementerio.disponibilidadInventario("tumba", estatura, cliente.getEdad());
				
				indice=1;
				for(Inventario urnaTumba:inventarioDisponible) {

		    		 System.out.println("["+indice+"] "+urnaTumba);
				}
				
				System.out.print("Indique el índice de la tumba escogida: ");
				indice=scanner.nextInt();
				
				while(indice<1 || indice>inventarioDisponible.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
				}
				
				tumba=(Tumba)inventarioDisponible.get(indice-1);
				
				cliente.pagoInmediato("flores");
				
				
				
	}//Fin metodo funcionalidadEntierro
}//Fin clase
