package uiMain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;

public class FuncionalidadExhumacion {
	
	public static void funcionalidadExhumacion() {
		
		Scanner scanner = new Scanner(System.in);
		
		
		Cliente cliente=null;
		Inventario urnaTumba=null;
		Cementerio cementerio =null;
		Inventario nuevaUrnaTumba=null;
		
		System.out.println();
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
				}else{
					if(cliente.getInventario() == null) {
						System.out.println("El cliente está registrado pero no es apto para la exhumación"); cliente=null;
				}
			}
			}	
			
				
				break;
				
		
		case 2:
			
			//Se escoge la funeraria con la que se va a realizar el procedimiento
			ArrayList<Establecimiento> funerarias =Establecimiento.filtarEstablecimiento("funeraria");
			System.out.println();
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
			System.out.println();
			System.out.println("[1] Buscar cliente en cementerios de cuerpos");
			System.out.println("[2] Buscar clientes con urna fija o tumba marcada como 'default'");
			
			System.out.print("Ingrese el índice correspondiente: ");
			indice = scanner.nextInt();
			
			//Validación de índice
			while (indice<1 || indice>2) {
				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
				indice=scanner.nextInt();
			}
			
			switch (indice) {
				case 1:
					System.out.println();
					System.out.println("Clientes mayor de edad");
					//Busca en la funeraria seleccionada los cementerios de cuerpos 
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
					System.out.println();
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
							mensaje2="Tumbas";
							break;
						
						case 2: 
							tipo="cenizas";
							mensaje1="Cementerios de cenizas";
							mensaje2="Urnas";
							break;
								
					}//Fin switch secundario
					
					//Se traen todos los cementerios de la funeraria con el atributo de tipo correspondiente ("cuerpos" o "cenizas")
					ArrayList<Establecimiento> cementerios=Cementerio.cementerioPorTipo(cementeriosPorFuneraria, tipo);
					
					System.out.println(mensaje1);
					indice=1;
					for(Establecimiento auxCementerio:cementerios) {
						//Se muestran los cementerios correspondientes y se busca la cantidad que se encuentra en el ArrayList de inventario que tiene como valor "default" en su atributo nombre
						int cantidadDefault=((Cementerio) auxCementerio).inventarioDefault().size();
						System.out.println("["+indice+"] "+auxCementerio+" - Cantidad de "+mensaje2+" marcadas como default: "+cantidadDefault);
						indice+=1;
					}
					
					System.out.print("Ingrese el índice correspondiente: ");
					indice = scanner.nextInt();
					
					//Validación de índice
					while (indice<1 || indice>cementerios.size()) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
					}
					//Asignación de cementerio escogido
					cementerio=(Cementerio)cementerios.get(indice-1);
					System.out.println(mensaje2+" marcadas como default");
					
					//Búsqueda de inventario que tenga como valor "default" en su atributo nombre
					ArrayList<Inventario> inventarioDefault=cementerio.inventarioDefault();
					
					indice=1;
					for(Inventario auxTumbaUrna:inventarioDefault) {
						System.out.println("["+indice+"] "+mensaje2+" marcadas como "+auxTumbaUrna+" - Cliente: "+auxTumbaUrna.getCliente());
						indice+=1;
					}
					
					System.out.print("Ingrese el índice correspondiente: ");
					indice = scanner.nextInt();
					
					//Validación de índice
					while (indice<1 || indice>inventarioDefault.size()) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
					}
					
					//Asignación del cliente
					cliente=inventarioDefault.get(indice-1).getCliente();
					
						
				
				break;
				
			}//Fin switch		
		
		}//Fin switch principal
		
		//Se asigna el objeto de tipo Urna o Tumba que tenga agregado el cliente
		urnaTumba=cliente.getInventario();
		//Asignar funeraria
		cementerio=cliente.getInventario().getCementerio();
		
		
		//Proceso exhumación del cuerpo
		
		//Datos para la exhumacion
		
		double pesoEstatura=0;
		int edad=0;
		String tipo1=null;
		String tipo2=null;
				
				
		//Iglesias disponibles
		ArrayList<Iglesia> iglesias = new ArrayList<Iglesia>();
		
		System.out.println();
		
		System.out.println("Opciones para la exhumación del cuerpo del cliente "+cliente.getNombre());
		
		int max=1;
		System.out.println("[1] Trasladar al cliente a una Urna fija en otro cementerio de cenizas");
		String mensaje="Urna";
		//Si el cliente tiene agregado un objeto de tipo Tumba es porque esta en un cementerio de cuerpos y puede ser llevado a otro, 
		//pero si tiene agregado un objeto de tipo Urna no puede ser trasladado a un cementerio de cuerpos
		if(urnaTumba instanceof Tumba) {
			System.out.println("[2] Trasladar al cliente a una Tumba en otro cementerio de cuerpos"); 
			mensaje="Tumba";
			max=2;
			}
		
		System.out.print("Ingrese el índice correspondiente: ");
		indice = scanner.nextInt();
		
		
		//Validación de índice
		while (indice<1 || indice>max) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
		}
		
	
		switch(indice) {
		
		case 1:
			
			System.out.print("Ingrese el peso del cliente: ");
			pesoEstatura=scanner.nextDouble();
			tipo1="cenizas";
			tipo2="urna";
			
			//Establecer iglesia para determinar religion del cliente 
			System.out.println("Seleccione la religión con la que se va a realizar la ceremonia del cliente");
			indice=1;
			max=0;
			for(Iglesia auxIglesia:Iglesia.values()) {
				//Se imprimen y añaden a la lista solo las iglesias que permiten la cremación como acto final de la vida
				if (auxIglesia.getCremacion()) {
					iglesias.add(auxIglesia);
					System.out.println("["+indice+"] "+auxIglesia);
					indice+=1;
					max+=1;
				}
			}
			

			
			
			break;
		
		case 2:
			
			System.out.print("Ingrese la estatura del cliente: ");
			pesoEstatura=scanner.nextDouble();
			tipo1="cuerpos";
			tipo2="urna";
			
			//Establecer iglesia para determinar religion del cliente 
			System.out.println("Seleccione la religión con la que se va a realizar la ceremonia del cliente");
			indice=1;
			max=0;
			for(Iglesia auxIglesia:Iglesia.values()) {
				System.out.println("["+indice+"] "+auxIglesia);
				indice+=1;
				max+=1;
			}
			
			break;
		
		}//Fin switch principal
		
		
		System.out.print("Indique el índice de la religión escogida: ");
		indice=scanner.nextInt();
		//Validación 
		while(indice<1 || indice>max) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			}
		
		cementerio.setIglesia(Iglesia.values()[indice-1]);
	
		edad=cliente.getEdad();
		
		
		System.out.println("La afiliación del cliente es "+cliente.getAfiliacion()+" se buscarán cementerios "+cliente.getAfiliacion()+" para su traslado");
		
		//Busco los cementerios del tipo solicitado en la funeraria que cumplan con las restricciones solicitadas 
		ArrayList<Establecimiento> cementeriosPorTipo = cementerio.getFuneraria().buscarCementerios(tipo1, cliente);
		
		//Elimino el cementerio en el que actualmente está el cliente
		cementeriosPorTipo.remove(cementerio);
		
		ArrayList<Establecimiento> cementerios=new ArrayList<Establecimiento>();
		
		for(Establecimiento auxCementerio:cementeriosPorTipo) {
			Cementerio auxCementerio2=(Cementerio)auxCementerio;
			if(auxCementerio2.disponibilidadInventario(tipo2,pesoEstatura,edad).size()!=0) {
				cementerios.add(auxCementerio2);
			}
		}
		
		System.out.println();
		
		
		if(cementerios.size()==0) {
			System.out.println("No se encontró inventario disponible");
			System.out.println("Se deberá añadir inventario tipo default");
			for(Establecimiento auxCementerio:cementeriosPorTipo) {
				if (tipo1.equals("cenizas")) {
					new Urna("default",(Cementerio)auxCementerio,pesoEstatura,edad,"fija");
				}else {new Tumba("default",(Cementerio)auxCementerio,pesoEstatura,edad);}
				
				cementerios.add(auxCementerio);
			}
			
		}
		
		//System.out.println("cementerios: "+ cementerios);
		
		indice=1;
		for(Establecimiento auxCementerio: cementerios) {
			Cementerio auxCementerio2 = (Cementerio) auxCementerio;
			System.out.println("["+indice+"] "+auxCementerio2+" Inventario disponible: ("+auxCementerio2.disponibilidadInventario(tipo2,pesoEstatura,edad).size()+")");
			indice+=1;
		}
		
		System.out.print("Ingrese el indice del cementerio: ");
		indice=scanner.nextInt();
		
		while(indice<1 || indice>cementerios.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			}
		
		////////////////////////////////////////////////////////////////////////////////////////////
		cementerio.getClientes().remove(cliente);
		Cementerio nuevoCementerio=(Cementerio) cementerios.get(indice-1);
		
		//Escoger opción más adecuada para cliente en cuánto a tamaño de la tumba comparado con estatura del cliente
		System.out.println("[1] Opción más adecuada en cuanto a tamaño: "+ nuevoCementerio.inventarioRecomendado(nuevoCementerio.disponibilidadInventario(tipo2,pesoEstatura,edad)));
		System.out.println("[2] Buscar entre las otras opciones");
		
		System.out.print("Ingrese el índice correspondiente: ");
		indice = scanner.nextInt();
		
		//Validación de índice
		while (indice<1 || indice>2) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////7
		
		switch(indice) {
			case 1:
				nuevaUrnaTumba=nuevoCementerio.inventarioRecomendado(nuevoCementerio.disponibilidadInventario(tipo2,pesoEstatura,edad));
				break;
			
			case 2: 
				ArrayList<Inventario> disponible=nuevoCementerio.disponibilidadInventario(tipo2, pesoEstatura, edad);
				disponible.remove(nuevoCementerio.inventarioRecomendado(nuevoCementerio.disponibilidadInventario(tipo2,pesoEstatura,edad)));
				indice=1;
				for(Inventario auxInventario: disponible ) {
					System.out.println("["+indice+"] "+auxInventario);
					indice+=1;
				}
				
				System.out.print("Ingrese el índice correspondiente: ");
				indice = scanner.nextInt();
				
				//Validación de índice
				while (indice<1 || indice>disponible.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
				}
				
				nuevaUrnaTumba=disponible.get(indice-1);
				
				break;
				
				
		}//Fin switch
		
		//Asignacion de tumba
		nuevaUrnaTumba.agregarCliente(cliente);
		System.out.println();
		System.out.println("Se realizó correctamente el cambio al cementerio "+nuevoCementerio);
		
		urnaTumba.setCliente(null);
		
		//Asignación de equipo de empleados 
		
		//Seleccionar la hora
		
		//Establecer horas de acuerdo a empleado Sepulturero disponibles  
		cementerio.generarHoras();
		
		System.out.println("Seleccione el horario en el será realizada la actividad");
		indice=1;
		for(LocalTime hora: cementerio.getHorarioEventos()) {
			String indicador;
			
			if(hora.getHour()>12) {
				indicador="Pm";
			}else {indicador = "Am";}
			
			System.out.println("["+indice+"] "+hora+" "+indicador);
			indice++;
		}
		System.out.print("Ingrese el índice para escoger el horario: ");
		indice=scanner.nextInt();
		
		//Validación
		while(indice<1 || indice>cementerio.getHorarioEventos().size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			}
		
		//Se asigna la hora en el atributo horaEvento para trabajar con esa hora el resto de la funcionalidad
		cementerio.setHoraEvento(cementerio.getHorarioEventos().get(indice-1));
		//Se elimina esa hora del ArrayList de HorarioEvento 
		cementerio.eliminarHorario(cementerio.getHorarioEventos().get(indice-1));
		
		
		//Seleccionar sepulturero
		
		System.out.println();
		System.out.println("Se inciará con el proceso de selección de empleados");
			
		System.out.println();
		System.out.println("Seleccione el empleado sepulturero disponible");
		ArrayList<Empleado> empleados=cementerio.getFuneraria().buscarEmpleados(cementerio.getHoraEvento(), "sepulturero");
		indice=1;
		for(Empleado auxEmpleado:empleados) {
			System.out.println("["+indice+"] "+auxEmpleado);
			indice+=1;
		}
		System.out.print("Ingrese el índice para escoger el empleado: ");
		indice=scanner.nextInt();
		
		//Validación
		while(indice<1 || indice>empleados.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			}
		
		//Asignar empleado a cementerio
		cementerio.setEmpleado(empleados.get(indice-1));
		
		//Seleccionar Forense
		
		empleados=cementerio.getFuneraria().buscarEmpleados(cementerio.getHoraEvento(), "forense");
		indice=1;
		for(Empleado auxEmpleado:empleados) {
			System.out.println("["+indice+"] "+auxEmpleado);
			indice+=1;
		}
		System.out.print("Ingrese el índice para escoger el empleado forense: ");
		indice=scanner.nextInt();
		
		//Validación
		while(indice<1 || indice>empleados.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			indice+=1;
			}
		
		
		
		//Seleccionar al padre u obispo 
		int categoria=cliente.getInventario().getCategoria();
		String empleado=null;
		if(categoria==0) {empleado="obispo";}
		else {empleado="padre";}
		
		System.out.println("Dada la categoria ["+categoria+"] su ceremonia puede ser celebrada por los siguientes religiosos");
		
		empleados = cementerio.getFuneraria().buscarEmpleados(cementerio.getHoraEvento().plusHours(3), empleado);
		
		indice=1;
		for(Empleado auxEmpleado:empleados) {
			if(categoria==0) {
				System.out.println(cementerio.getIglesia().getReligiosoAltoRango()+" "+auxEmpleado);
				indice+=1;
			}else {
				System.out.println(cementerio.getIglesia().getReligioso()+" "+auxEmpleado);
				indice+=1;
			}
		}
		
		System.out.print("Ingrese el índice para escoger al religioso: ");
		indice=scanner.nextInt();
		
		//Validación
		while(indice<1 || indice>empleados.size()) {
			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
			indice=scanner.nextInt();
			}
		
		System.out.println();
		
		System.out.println("Dados los datos se organizará como estarán distribuidos los familiares en la Iglesia");
		System.out.println(cementerio.organizarIglesia(cliente));
		
		
		
		
		
		
			
		
		
		
	}//Fin metodo funcionalidadExhumacion
	

}//Fin clase
