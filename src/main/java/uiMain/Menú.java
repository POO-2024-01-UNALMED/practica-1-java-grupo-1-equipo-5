package uiMain;

import java.util.Scanner;


import java.time.LocalTime;
import java.util.ArrayList;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;
import gestorAplicacion.inventario.Inventario;
import gestorAplicacion.inventario.Producto;
import gestorAplicacion.inventario.Urna;

public class Menú {
	
	
	
	public static void funcionalidadCrematorio() {
		
		Scanner scanner = new Scanner(System.in);
		
		Funeraria funeraria=null;
		Cliente cliente=null;
		Crematorio crematorio=null;
		Cementerio cementerio =null;
		Iglesia iglesia=null;
		ArrayList<Producto> productos=new ArrayList<Producto>();
		
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
		
		//Validación para asegurar existencia de un cliente y que la funcionalidad pueda continuar
		while(cliente==null) {
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
								
							}//Fin if validación existencia cliente adulto
							
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
					//Validación existencia cliente menor de edad
					if(funeraria.buscarCliente("niño").size()!=0) {
					
						System.out.print("Ingrese el índice del cliente: ");
						indice=scanner.nextInt();
						//Validacion
						while(indice<1 || indice>funeraria.buscarCliente("niño").size()) {
							System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
							indice=scanner.nextInt();
						}
						cliente=funeraria.buscarCliente("niño").get(indice-1);
						
					}//Fin if validación existencia cliente menor de edad
				
					break;
			}
			
			
			if(cliente==null) {System.out.println("No hay clientes disponibles en esta opción vuelva a digitar un índice");}
		}//Fin while existencia cliente
		
		//Separación entre partes
		System.out.println();
		
		//Disponibilidad en el crematorio

		
		//Validación asignación de crematorio
		while(crematorio==null) {
			
			//Crematorios que coincidan con la capacidad de acompañantes del cliente y con la afiliación del cliente
			ArrayList<Establecimiento> crematorios=funeraria.buscarEstablecimientos("crematorio", cliente);
			
			if(crematorios.size()!=0) {
				System.out.println("Su afiliación es de tipo "+cliente.getAfiliacion());
				System.out.println("Los crematorios disponibles para la afiliación "+cliente.getAfiliacion()+" son:");
				
				indice=1;
				for(Establecimiento auxCrematorio:crematorios) {
					System.out.println("["+indice+"] "+auxCrematorio);
				}
				
				System.out.print("Ingrese el índice del crematorio deseado: ");
				indice=scanner.nextInt();
				
				//Validación 
				while(indice<1 || indice>crematorios.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
				}
				
				//asignación de crematorio
				crematorio=(Crematorio)crematorios.get(indice-1);
				
			
				
				//Establecer horario crematorio
				System.out.println("Horarios disponibles del crematorio:");
				//Se establecen 3 horarios de acuerdo a la jornada de los empleados con cargo "cremador"
				crematorio.generarHoras();
			
				
				indice=1;
				for(LocalTime hora: crematorio.getHorarioEventos()) {
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
				while(indice<1 || indice>crematorio.getHorarioEventos().size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
					}
				
				//Se asigna la hora en el atributo horaEvento para trabajar con esa hora el resto de la funcionalidad
				crematorio.setHoraEvento(crematorio.getHorarioEventos().get(indice-1));
				//Se elimina esa hora del ArrayList de HorarioEvento 
				crematorio.eliminarHorario(crematorio.getHorarioEventos().get(indice-1));
				
				
				//Se debe establecer el empleado del crematorio y se da como parámetro la hora escogida
				ArrayList<Empleado> empleados =funeraria.buscarEmpleados(crematorio.getHoraEvento(), "cremador");
				
				
				//Se escoge al empleado que cumpla con el atributo de cargo cremador y la jornada específica según la hora seleccionada
				System.out.println("Empleados disponibles en la jornada seleccionada");
				indice=1;
				for (Empleado auxEmpleado:empleados) {
					System.out.println("["+indice+"] "+auxEmpleado);
				}
				
				System.out.print("Ingrese el índice del empleado deseado: ");
				indice=scanner.nextInt();
				
				//Validación 
				while(indice<1 || indice>empleados.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
					}
				
				//Asignar Empleado en crematorio
				crematorio.setEmpleado(empleados.get(indice-1));
				
				//Salto
				System.out.println();
			
				
				//Establecer iglesia para determinar religion del cliente 
				
				System.out.println("Seleccione la religión con la que se va a realizar la ceremonia del cliente");
				
				//Iglesias disponibles
				ArrayList<Iglesia> iglesias = new ArrayList<Iglesia>();
				indice=1;
				for(Iglesia auxIglesia:Iglesia.values()) {
					//Se imprimen y añaden a la lista solo las iglesias que permiten la cremación como acto final de la vida
					if (auxIglesia.getCremacion()) {
						iglesias.add(auxIglesia);
						System.out.println("["+indice+"] "+auxIglesia);
						indice+=1;
					}
				}
				System.out.print("Indique el índice de la religión escogida: ");
				indice=scanner.nextInt();
				//Validación 
				while(indice<1 || indice>iglesias.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
					}
				
				//Salto 
				System.out.println();
				
				
				//Iglesia o religión escogida
				iglesia=iglesias.get(indice-1);
				
				//Se asigna la iglesia en el atributo iglesia en el crematorio designado para trabajar con este atributo el resto de la funcionalidad
				crematorio.setIglesia(iglesia);
				
				//se crea el productoCrematorio para guardar registro de lo que se debe cobrar en la clase Factura respecto a crematorio 
				Producto productoCrematorio= new Producto(crematorio);
				//Se guardarán todos los productos que se empleen para organizar las facturas
				productos.add(productoCrematorio);
				
				//Se imprimirá la invitación del evento
				System.out.println(productoCrematorio.evento(cliente));
				
				//Salto 
				System.out.println();
				
				//Definir el cementerio, de acuerdo a la hora fin del evento de cremación, afiliación del cliente y el cementerio debe tener como atributo tipo el valor "cenizas"
				ArrayList<Establecimiento> cementerios =funeraria.buscarCementerios("cenizas", cliente);
				//Se establecen los horarios del cementerio de acuerdo a la finalización de ceremonia de cremación
				crematorio.cambiarHorarios(cementerios);
				
				indice=1;
				//Se imprimen los cementerios
				System.out.println("Cementerios disponibles");
				
				for(Establecimiento auxCementerio:cementerios) {
					System.out.println("["+indice+"] "+auxCementerio+" - Horarios disponibles "+ auxCementerio.getHorarioEventos().size());
				}
				System.out.print("Indique el índice del cementerio: ");
				indice=scanner.nextInt();
				//Validación 
				while(indice<1 || indice>cementerios.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indice=scanner.nextInt();
					}
				//Se agrega el cementerio seleccionado
				cementerio=(Cementerio)cementerios.get(indice-1);
				//Se añade la iglesia seleccionada a cementerio
				cementerio.setIglesia(iglesia);
				
				//Escoger horario para el cementerio
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
				crematorio.setHoraEvento(cementerio.getHorarioEventos().get(indice-1));
				//Se elimina esa hora del ArrayList de HorarioEvento 
				crematorio.eliminarHorario(cementerio.getHorarioEventos().get(indice-1));
				
				//Salto
				System.out.println();
				
				//Seleccionar Urna
				
				System.out.println("El tipo de urnas disponibles para su religión son: ");
				for(String tipo:crematorio.getIglesia().getTipoUrnas()) {
					System.out.println(tipo);
				}
				
				//Se necesita el peso del cliente para asignar la urna
				System.out.print("Ingrese un número de 0 a 120 que indique el peso en kg del cliente: ");
				double peso=scanner.nextDouble();
				//Validación
				while(peso<0 || peso>120) {
					System.out.println("El número ingresado está fuera de rango. Ingrese nuevamente el peso en kg: ");
					peso=scanner.nextDouble();
				}
				//Se filtran las urnas para que correspondan con el peso del cliente
				ArrayList<Inventario> urnas =cementerio.disponibilidadInventario("urna", peso);
				
				Urna urna=null;
				
				if(urnas.size()==0) {
					System.out.println("No se encontraron urnas disponibles para el cliente, se deberá añadir una provisional");
					String tipo=cementerio.getIglesia().getTipoUrnas()[0];
					urna=new Urna("default",cementerio,peso,tipo);
					System.out.println("Urna "+urna+ " añadida");
					
					//Cliente agregado en la urna correspondiente
					urna.agregarCliente(cliente);
					
				}else {
					//Se imprimen las urnas disponibles
					System.out.println("Escoja la urna de su preferencia: ");
					indice=1;
					for(Inventario auxUrna:urnas) {
						System.out.println("["+indice+"] "+auxUrna);
						indice+=1;
					}
					
					System.out.print("Indique el índice de la Urna: ");
					indice=scanner.nextInt();
					//Validación 
					while(indice<1 || indice>urnas.size()) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
						}
					//Urna designada para asignar al cliente
					urna =(Urna)urnas.get(indice-1);
					//Cliente agregado en la urna correspondiente
					urna.agregarCliente(cliente);
					
				}
				
				
				//Tipo de categoria para la urna
				System.out.println("Seleccione el tipo de categoría para la urna del cliente");
				System.out.println("[0] Se puede escoger dos arreglos florales");
				System.out.println("[1] Se pueden escoger dos arreglos florales y material para la Urna");
				System.out.println("[2] Se pueden escoger cuatro arreglos florales y material para la Urna");
				
				System.out.print("Indique el índice de la categoría deseada: ");
				int categoria =scanner.nextInt();
				
				
				while(categoria<0 || categoria>2) {
					System.out.println("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					categoria=scanner.nextInt();
				}
				//Se agrega la categoría de la urna
				urna.setCategoria(categoria);
				//Se genera la cantidad de adornos según corresponda 
				urna.generarAdornos("flores");
				urna.generarAdornos("material");
				
				
				//Se trae el inventario de flores y materiales disponibles
				String [] flores = Inventario.flores;
				String [] materiales =Inventario.material;
				
				System.out.println("Seleccione las flores que adornarán la urna");
				
				int numero=0;
		
				//Si la categoria es 0 solo se podrán escoger 2 flores del arreglo
				if(categoria==0) {
					numero=2;
				}else {numero=4; urna.setMaterialSeleccionado(null);} //Se cambia el materialSeleccionado con valor null para poder agregar uno
				while(numero>0) {
					indice=1;
					for(String flor:flores) {
						//Se cuenta la cantidad que hay de cada una de las flores
						System.out.println("["+indice+"] "+flor+" cantidad disponible: "+urna.contarAdorno(flor, "flores"));
						indice+=1;
					}
					System.out.print("Indique el índice de las flores que quiere agregar: ");
					indice=scanner.nextInt();
					//Validación
					while(indice<1 || indice>flores.length) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
						}
					//Se agregan las flores seleccionadas y se eliminan del inventario
					urna.agregarAdorno(flores[indice-1], "flores");
					
					numero-=1;
					
					//Salto
					System.out.println();
				}
				
				indice=1;
				if(urna.getMaterialSeleccionado()==null) {
					System.out.println("Indique el material de su preferencia");
					for(String material:materiales) {
						//Se cuenta la cantidad que hay de cada uno de los materiales disponibles en Inventario.material
						System.out.println("["+indice+"] "+material+" cantidad disponible: "+urna.contarAdorno(material, "material"));
						indice+=1;
					}
					
					System.out.print("Indique el índice del material que quiere agregar: ");
					indice=scanner.nextInt();
					
					while(indice<1 || indice>materiales.length) {
						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
						indice=scanner.nextInt();
						}
					//Se agregan las flores seleccionadas y se eliminan del inventario
					urna.agregarAdorno(materiales[indice-1], "material");
				}
				
				System.out.println("Flores seleccionadas"+ urna.getFloresSeleccionadas());
				System.out.println("Material seleccionado"+ urna.getMaterialSeleccionado());
				
			}//fin if validación existencia crematorio disponible
			
			//Validación en caso de no existir crematorios disponibles
			if(crematorio==null) {
				System.out.println("No hay crematorios en esta funeraria que puedan cumplir las especificaciones del cliente, deberá ser trasladado a otra funeraria");
				//Se vuelve a seleccionar una funeraria
				//Se vuelven a filtrar las funerarias 
				funerarias=Establecimiento.filtarEstablecimiento("funeraria");
				//Se elimina la funeraria que se acabó de escoger
				funerarias.remove(funeraria);
				indice=1;
				//Se imprimen las funerarias restantes
				System.out.println("Seleccione la funeraria correspondiente");
				for(Establecimiento auxFuneraria:funerarias) {
					System.out.println("["+indice+"] "+auxFuneraria);
					indice+=1;
				}
				
				System.out.print("Ingrese el índice correspondiente: ");
				indice=scanner.nextInt();
				
				//Se valida que se ingrese un índice adecuado para continuar el proceso
				while (indiceFuneraria<1 || indiceFuneraria>funerarias.size()) {
					System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					indiceFuneraria=scanner.nextInt();
				}//Fin while
				
				//Se elimina al cliente de la funeraria
				funeraria.eliminarCliente(cliente);
				//Se cambia el valor de la funeraria anterior por la nueva funeraria
				funeraria=(Funeraria)funerarias.get(indice-1);
				//Se agrega al cliente a la nueva funeraria
				funeraria.agregarCliente(cliente);	
				
			}//Fin if validacion existencia crematorio
				
	}//Fin while validación existencia de crematorio
			
		//Salto
		System.out.println();
			
			
		}//Fin funcionalidadCrematorio
		
		
		
	
	
	public static void main(String[] args) {
		
		//Objetos de prueba
		//Funerarias
		Funeraria funita = new Funeraria("funita", null,null);
		Funeraria fumita = new Funeraria("fumita", null,null);
		Funeraria fulanita = new Funeraria("fulanita", null,null);
		
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
		fumita.agregarCliente(a1);
		
		//Objetos crematorio-cementerio
		Crematorio crematorio = new Crematorio ("crematorio","0054",100,null,"oro", null,funita); 
		Crematorio creno = new Crematorio ("creno","0089",78,null,"oro", null,fumita); 
		Crematorio cremita = new Crematorio ("cremita","0098",78,null,"oro", null,fulanita); 
		
		Cementerio cementerio = new Cementerio ("cementerio","2090",78,null,"oro", null,"cenizas",fulanita); 
		Cementerio cemi = new Cementerio ("cemi","9089",78,null,"oro", null,"cenizas",fumita); 
		Cementerio cemito = new Cementerio ("cemito","5490",78,null,"oro", null,"cenizas",funita); 
		
		//Cementerio cementerio1 = new Cementerio ("cementerio1","2090",78,null,"oro", null,"cenizas",fulanita); 
		//Cementerio cemi1 = new Cementerio ("cemi1","9089",78,null,"oro", null,"cenizas",fumita); 
		//Cementerio cemito1 = new Cementerio ("cemito1","5490",78,null,"oro", null,"cenizas",funita);
		
		//Objetos de la clase Empleado
		Empleado empleado1 = new Empleado("Alberto",12345,"3456",null,"mañana","cremador",900000);
		Empleado empleado2 = new Empleado("Maria",12345,"3456",null,"tarde","cremador",900000);
		Empleado empleado3 = new Empleado("Anastasia",12345,"3456",null,"noche","cremador",900000);
		Empleado empleado4 = new Empleado("Gilberto",12345,"3456",null,"mañana","cremador",900000);
		Empleado empleado5 = new Empleado("Pepito",12345,"3456",null,"mañana","cremador",900000);
		Empleado empleado6 = new Empleado("Camila",12345,"3456",null,"tarde","cremador",900000);
		Empleado empleado7 = new Empleado("Santiago",12345,"3456",null,"noche","cremador",900000);
		Empleado empleado8 = new Empleado("Anastasio",12345,"3456",null,"tarde","cremador",900000);
		
		
		//Urna 
		
		Urna urna1=new Urna("Urnita1",cemi,70,"fija");
		Urna urna2=new Urna("Urnita2",cemi,90,"ordinaria");
		Urna urna3=new Urna("Urnita3",cemi,50,"ordinaria");
		Urna urna4=new Urna("Urnita4",cemi,60,"fija");
		
		Urna urna5=new Urna("Urnita5",cementerio,70,"fija");
		Urna urna6=new Urna("Urnita6",cementerio,50,"ordinaria");
		Urna urna7=new Urna("Urnita7",cementerio,40,"ordinaria");
		Urna urna8=new Urna("Urnita8",cementerio,60,"fija");
		
		Urna urna9=new Urna("Urnita9",cemito,70,"fija");
		Urna urna10=new Urna("Urnita10",cemito,50,"fija");
		Urna urna11=new Urna("Urnita11",cemito,40,"ordinaria");
		Urna urna12=new Urna("Urnita12",cemito,60,"fija");
		
		
		
		funita.agregarEmpleado(empleado1);
		funita.agregarEmpleado(empleado2);
		funita.agregarEmpleado(empleado3);
		
		fumita.agregarEmpleado(empleado5);
		fumita.agregarEmpleado(empleado6);
		
		fulanita.agregarEmpleado(empleado4);
		fulanita.agregarEmpleado(empleado7);
		fulanita.agregarEmpleado(empleado8);
		
	
		
		
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
