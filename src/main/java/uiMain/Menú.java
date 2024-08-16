package uiMain;

import java.util.Scanner;


import java.time.LocalTime;
import java.util.ArrayList;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.personas.*;
import gestorAplicacion.inventario.Inventario;
import gestorAplicacion.inventario.Producto;
import gestorAplicacion.inventario.TipoVehiculo;
import gestorAplicacion.inventario.Urna;
import gestorAplicacion.inventario.Vehiculo;
import gestorAplicacion.inventario.Tumba;

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
						System.out.println("["+indice+"] "+ auxCliente);
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
				
				//Tipo de categoria para la urna
				System.out.println("Seleccione el tipo de categoría para la urna del cliente");
				System.out.println("[0] Se puede escoger un arreglo florales");
				System.out.println("[1] Se pueden escoger un arreglo floral y material para la Urna");
				System.out.println("[2] Se pueden escoger cuatro arreglos florales y material para la Urna");
				
				System.out.print("Indique el índice de la categoría deseada: ");
				int categoria =scanner.nextInt();
				
				
				while(categoria<0 || categoria>2) {
					System.out.println("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
					categoria=scanner.nextInt();
				}
				
				//Se filtran las urnas para que correspondan con el peso del cliente
				ArrayList<Inventario> urnas =cementerio.disponibilidadInventario("urna", peso,categoria);
				
				Urna urna=null;
				
				if(urnas.size()==0) {
					System.out.println("No se encontraron urnas disponibles para el cliente, se deberá añadir una provisional");
					String tipo=cementerio.getIglesia().getTipoUrnas()[0];
					urna=new Urna("default",cementerio,peso,1,tipo);
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
				
				
				//Se genera la cantidad de adornos según corresponda 
				urna.generarAdornos("flores");
				urna.generarAdornos("material");
				
				
				//Se trae el inventario de flores y materiales disponibles
				String [] flores = Inventario.flores;
				String [] materiales =Inventario.material;
				
				System.out.println("Seleccione las flores que adornarán la urna");
				
				int numero=0;
		
				//Si la categoria es 0 solo se podrán escoger 2 flores del arreglo
				if(categoria==0 || categoria==1) {
					numero=1;
				}else {numero=3; urna.setMaterialSeleccionado(null);} //Se cambia el materialSeleccionado con valor null para poder agregar uno
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
				
				System.out.println("Flores seleccionadas: "+ urna.getFloresSeleccionadas());
				System.out.println("Material seleccionado: "+ urna.getMaterialSeleccionado());
				
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
		
		//objetos cuenta Bancaria 
		CuentaBancaria cuenta = new CuentaBancaria(123, "Alfredo", 1000000, "Ala");
		
		//Objetos Funeraria
		
		Funeraria funeraria1 = new Funeraria("Eterna Paz", cuenta,cuenta);
		Funeraria funeraria2 = new Funeraria("Caminos de Luz", cuenta,cuenta);
		Funeraria funeraria3 = new Funeraria("Recuerdos Eternos", cuenta,cuenta);
		
		//Objetos Cementerio
		
		//cementerios pertenecientes a F1 --> Funeraria 1 - cenizas
		Cementerio cementerioF11Ce = new Cementerio ("Jardín de la Eternidad",78,cuenta,"oro", null,"cenizas",funeraria1); 
		Cementerio cementerioF12Ce = new Cementerio ("Colina de la Paz",85,cuenta,"oro", null,"cenizas",funeraria1); 
		
		Cementerio cementerioF13Ce = new Cementerio ("Campos de tranquilidad",79,cuenta,"plata", null,"cenizas",funeraria1); 
		Cementerio cementerioF14Ce = new Cementerio ("Valle del Silencio",78,cuenta,"plata", null,"cenizas",funeraria1);
		 
		Cementerio cementerioF15Ce = new Cementerio ("Rincón del Descanso",50,cuenta,"bronce", null,"cenizas",funeraria1); 
		Cementerio cementerioF16Ce = new Cementerio ("Jardín de los Recuerdos",78,cuenta,"bronce", null,"cenizas",funeraria1); 
		
		
		//cementerios pertenecientes a F1 --> Funeraria 1 - cuerpos
		Cementerio cementerioF11Cu = new Cementerio ("Eternidad Verde",78,cuenta,"oro", null,"cuerpos",funeraria1); 
		Cementerio cementerioF12Cu = new Cementerio ("Mirador de la Serenidad",85,cuenta,"oro", null,"cuerpos",funeraria1); 
		
		Cementerio cementerioF13Cu = new Cementerio ("Bosque de la Memoria",50,cuenta,"plata", null,"cuerpos",funeraria1); 
		Cementerio cementerioF14Cu = new Cementerio ("Cementerio del Refugi",78,cuenta,"plata", null,"cuerpos",funeraria1);
		 
		Cementerio cementerioF15Cu = new Cementerio ("Paz y Esperanza",78,cuenta,"bronce", null,"cuerpos",funeraria1); 
		Cementerio cementerioF16Cu = new Cementerio ("Sendero de la Tranquilidad",78,cuenta,"bronce", null,"cuerpos",funeraria1); 
		
		
		//crematorios pertenecientes a F1 --> Funeraria 1
		Crematorio crematorioF11 = new Crematorio ("Crematorio Luz y Paz",100,null,"oro", null,funeraria1); 
		Crematorio crematorioF12 = new Crematorio ("Hogar de la Ascensión",78,null,"oro", null,funeraria1); 
		
		Crematorio crematorioF13 = new Crematorio ("Fuego de la Memoria",78,null,"plata", null,funeraria1); 
		Crematorio crematorioF14 = new Crematorio ("Crematorio Serenidad Eterna",78,null,"plata", null,funeraria1);
		
		Crematorio crematorioF15 = new Crematorio ("Llama de la Eternidada",78,null,"bronce", null,funeraria1); 
		Crematorio crematorioF16 = new Crematorio ("Refugio del Alba",78,null,"bronce", null,funeraria1);
		
		
		
				
		//Objetos Cementerio
				
		//cementerios pertenecientes a F2 --> Funeraria 2 - cenizas
		Cementerio cementerioF21Ce = new Cementerio ("Cementerio del Silencio",78,cuenta,"oro", null,"cenizas",funeraria2); 
		Cementerio cementerioF22Ce = new Cementerio ("Campo de la Eternidad",85,cuenta,"oro", null,"cenizas",funeraria2); 
				
		Cementerio cementerioF23Ce = new Cementerio ("Bosque de la Serenidad",79,cuenta,"plata", null,"cenizas",funeraria2); 
		Cementerio cementerioF24Ce = new Cementerio ("Jardines del Descanso",78,cuenta,"plata", null,"cenizas",funeraria2);
				 
		Cementerio cementerioF25Ce = new Cementerio ("Valle de la Paz Interior",50,cuenta,"bronce", null,"cenizas",funeraria2); 
		Cementerio cementerioF26Ce = new Cementerio ("Luz del Recuerdo",78,cuenta,"bronce", null,"cenizas",funeraria2); 
				
				
		//cementerios pertenecientes a F2 --> Funeraria 2 - cuerpos
		Cementerio cementerioF21Cu = new Cementerio ("Colinas del Reposo",78,cuenta,"oro", null,"cuerpos",funeraria2); 
		Cementerio cementerioF22Cu = new Cementerio ("Jardín de la Eternidad",85,cuenta,"oro", null,"cuerpos",funeraria2); 
				
		Cementerio cementerioF23Cu = new Cementerio ("Refugio de la Memoria",50,cuenta,"plata", null,"cuerpos",funeraria2); 
		Cementerio cementerioF24Cu = new Cementerio ("Cementerio del Alba",78,cuenta,"plata", null,"cuerpos",funeraria2);
				 
		Cementerio cementerioF25Cu = new Cementerio ("Alameda de la Paz",78,cuenta,"bronce", null,"cuerpos",funeraria2); 
		Cementerio cementerioF26Cu = new Cementerio ("Jardín del Silencio Eterno",78,cuenta,"bronce", null,"cuerpos",funeraria2); 
				
		
		//crematorios pertenecientes a F2 --> Funeraria 2
		Crematorio crematorioF21 = new Crematorio ("Crematorio del Silencio",100,null,"oro", null,funeraria2); 
		Crematorio crematorioF22 = new Crematorio ("Ascenso y Tranquilidad",78,null,"oro", null,funeraria2); 
		
		Crematorio crematorioF23 = new Crematorio ("Brasa de Paz",78,null,"plata", null,funeraria2); 
		Crematorio crematorioF24 = new Crematorio ("Eterna Luz Crematorio",78,null,"plata", null,funeraria2);
		
		Crematorio crematorioF25 = new Crematorio ("Crematorio del Renacer",78,null,"bronce", null,funeraria2); 
		Crematorio crematorioF26 = new Crematorio ("Fuego y Serenidad",78,null,"bronce", null,funeraria2);		
	
		
		
		//objetos cementerio
		
		Cementerio cementerioF31Ce = new Cementerio ("Jardín de los Ángeles",78,cuenta,"oro", null,"cenizas",funeraria3); 
		Cementerio cementerioF32Ce = new Cementerio ("Campo de la Tranquilidad",85,cuenta,"oro", null,"cenizas",funeraria3); 
				
		Cementerio cementerioF33Ce = new Cementerio ("Oasis de Paz",79,cuenta,"plata", null,"cenizas",funeraria3); 
		Cementerio cementerioF34Ce = new Cementerio ("Colinas del Recuerdo",78,cuenta,"plata", null,"cenizas",funeraria3);
				 
		Cementerio cementerioF35Ce = new Cementerio ("Rincón del Silencio",50,cuenta,"bronce", null,"cenizas",funeraria3); 
		Cementerio cementerioF36Ce = new Cementerio ("Eterna Armonía",78,cuenta,"bronce", null,"cenizas",funeraria3); 
		
		
		//cementerios pertenecientes a F2 --> Funeraria 2 - cuerpos
		Cementerio cementerioF31Cu = new Cementerio ("Bosque de los Sueños",78,cuenta,"oro", null,"cuerpos",funeraria3); 
		Cementerio cementerioF32Cu = new Cementerio ("Pradera del Descanso",85,cuenta,"oro", null,"cuerpos",funeraria3); 
						
		Cementerio cementerioF33Cu = new Cementerio ("Refugio de la Memoria",50,cuenta,"plata", null,"cuerpos",funeraria3); 
		Cementerio cementerioF34Cu = new Cementerio ("Sendero de la Memoria",78,cuenta,"plata", null,"cuerpos",funeraria3);
						 
		Cementerio cementerioF35Cu = new Cementerio ("Valle del Reposo",78,cuenta,"bronce", null,"cuerpos",funeraria3); 
		Cementerio cementerioF36Cu = new Cementerio ("Jardines del Refugio",78,cuenta,"bronce", null,"cuerpos",funeraria3);
		
		
		//crematorios pertenecientes a F2 --> Funeraria 2
		Crematorio crematorioF31 = new Crematorio ("Crematorio del Horizonte",100,null,"oro", null,funeraria3); 
		Crematorio crematorioF32 = new Crematorio ("Cenizas de la Eternidad	",78,null,"oro", null,funeraria3); 
				
		Crematorio crematorioF33 = new Crematorio ("Refugio de la Luz",78,null,"plata", null,funeraria3); 
		Crematorio crematorioF34 = new Crematorio ("Fuego de Serenidad",78,null,"plata", null,funeraria3);
				
		Crematorio crematorioF35 = new Crematorio ("Crematorio del Horizonte Eterno",78,null,"bronce", null,funeraria3); 
		Crematorio crematorioF36 = new Crematorio ("Crematorio Luz de la Eternidad",78,null,"bronce", null,funeraria3);	
		
		
	
		
		
		
		
		
			
				
				
		
				
				
				
				
				
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		//Objetos de prueba
		//Funerarias
		
		cuenta = new CuentaBancaria(123, "Alfredo", 1000000, "Ala");
		Funeraria funita = new Funeraria("funita", cuenta,cuenta);
		Funeraria fumita = new Funeraria("fumita", cuenta,cuenta);
		Funeraria fulanita = new Funeraria("fulanita", cuenta,cuenta);
		
		//Cliente
		Familiar b= new Familiar("Mario",123,45,cuenta,"padre",17);
		Familiar e= new Familiar("Alberto",123,45,cuenta,"conyugue",17);
		Familiar c= new Familiar("Maria",5, "oro",e);
		
		
		ArrayList<Familiar> familiar=new ArrayList<Familiar>();
		familiar.add(b);
		familiar.add(e);
		
		Cliente a1 = new Cliente("a1",123,17,null,"oro",familiar);
		Cliente b1 = new Cliente("b1",123,17,null,"oro",familiar);
		Cliente c1 = new Cliente("c1",123,17,null,"oro",familiar);
		Cliente d1 = new Cliente("d1",123,17,null,"oro",familiar);
		Cliente e1 = new Cliente("Alma",16, "oro",familiar);
		
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
		
		Cementerio cementerio1 = new Cementerio ("cementerio1","2090",78,null,"oro", null,"cuerpos",fulanita); 
		Cementerio cemi1 = new Cementerio ("cemi1","9089",78,null,"oro", null,"cuerpos",fumita); 
		Cementerio cemito1 = new Cementerio ("cemito1","5490",78,null,"oro", null,"cuerpos",funita);
		
		//Objetos de la clase Empleado
		Empleado empleado1 = new Empleado("Alberto",12345,"3456",null,"mañana","sepulturero",900000);
		Empleado empleado2 = new Empleado("Maria",12345,"3456",null,"noche","sepulturero",900000);
		Empleado empleado3 = new Empleado("Anastasia",12345,"3456",null,"noche","cremador",900000);
		Empleado empleado4 = new Empleado("Gilberto",12345,"3456",null,"mañana","cremador",900000);
		Empleado empleado5 = new Empleado("Pepito",12345,"3456",null,"mañana","sepulturero",900000);
		Empleado empleado6 = new Empleado("Camila",12345,"3456",null,"tarde","cremador",900000);
		Empleado empleado7 = new Empleado("Santiago",12345,"3456",null,"noche","sepulturero",900000);
		Empleado empleado8 = new Empleado("Anastasio",12345,"3456",null,"tarde","cremador",900000);
		
		
		//Conductores
		Empleado empleado9 = new Empleado("Camila",12345,"3456",null,"mañana","conductor",900000);
		Empleado empleado10 = new Empleado("Santiago",12345,"3456",null,"tarde","conductor",900000);
		Empleado empleado11 = new Empleado("Anastasio",12345,"3456",null,"noche","conductor",900000);
		
		fulanita.agregarEmpleado(empleado11);
		fulanita.agregarEmpleado(empleado10);
		fulanita.agregarEmpleado(empleado9);
		
		funita.agregarEmpleado(empleado11);
		funita.agregarEmpleado(empleado10);
		funita.agregarEmpleado(empleado9);
		
		fumita.agregarEmpleado(empleado11);
		fumita.agregarEmpleado(empleado10);
		fumita.agregarEmpleado(empleado9);
		
		//AgregarVehiculos
		
		Vehiculo veh1= new Vehiculo(TipoVehiculo.BERLINA,fulanita,"azul", "2345",1);
		Vehiculo veh2= new Vehiculo(TipoVehiculo.BUS,fulanita,"azul", "2345",2);
		Vehiculo veh3= new Vehiculo(TipoVehiculo.CARROZA,fulanita,"azul", "2345",3);
		
		Vehiculo veh4= new Vehiculo(TipoVehiculo.FAETON,funita,"azul", "2345",3);
		Vehiculo veh5= new Vehiculo(TipoVehiculo.BUS,funita,"azul", "2345",3);
		Vehiculo veh6= new Vehiculo(TipoVehiculo.COCHERESPETO,funita,"azul", "2345",3);
		
		Vehiculo veh7= new Vehiculo(TipoVehiculo.CUPE,fumita,"azul", "2345",3);
		Vehiculo veh8= new Vehiculo(TipoVehiculo.BERLINA,fumita,"azul", "2345",3);
		Vehiculo veh9= new Vehiculo(TipoVehiculo.CARROZA,fumita,"azul", "2345",3);
		
		
		//Urnas vacías
		
		Urna urna1=new Urna("Urnita1",cemi,70,1,"fija");
		Urna urna2=new Urna("Urnita2",cemi,90,0,"ordinaria");
		Urna urna3=new Urna("Urnita3",cemi,50,0,"ordinaria");
		Urna urna4=new Urna("Urnita4",cemi,60,1,"fija");
		
		Urna urna5=new Urna("default",cementerio,70,2,"fija");
		Urna urna6=new Urna("default",cementerio,50,2,"ordinaria");
		Urna urna7=new Urna("Urnita7",cementerio,40,2,"ordinaria");
		Urna urna8=new Urna("Urnita8",cementerio,60,1,"fija");
		
		Urna urna9=new Urna("default",cemito,70,0,"fija");
		Urna urna10=new Urna("Urnita10",cemito,50,1,"fija");
		Urna urna11=new Urna("Urnita11",cemito,40,2,"ordinaria");
		Urna urna12=new Urna("default",cemito,60,1,"fija");
		
		
		
		funita.agregarEmpleado(empleado1);
		funita.agregarEmpleado(empleado2);
		funita.agregarEmpleado(empleado3);
		
		fumita.agregarEmpleado(empleado5);
		fumita.agregarEmpleado(empleado6);
		
		fulanita.agregarEmpleado(empleado4);
		fulanita.agregarEmpleado(empleado7);
		fulanita.agregarEmpleado(empleado8);
		
		
		//Funcionalidad Exhumacion
		
		Cliente n1 = new Cliente("n1",123,35,null,"oro",familiar);
		Cliente n2 = new Cliente("n2",123,37,null,"oro",familiar);
		Cliente n3 = new Cliente("n3",123,39,null,"oro",familiar);
		Cliente n4 = new Cliente("n4",123,41,null,"oro",familiar);
		Cliente n5 = new Cliente("n5",123,25,null,"oro",familiar);
		
		Cliente n6 = new Cliente("n6",123,35,null,"oro",familiar);
		Cliente n7 = new Cliente("n7",123,37,null,"oro",familiar);
		Cliente n8 = new Cliente("n8",123,39,null,"oro",familiar);
		Cliente n9 = new Cliente("n9",123,41,null,"oro",familiar);
		Cliente n10= new Cliente("n10",123,25,null,"oro",familiar);
		
		Urna urna13=new Urna("Urnita13",cemi,70,1,"fija");
		Urna urna14=new Urna("Urnita14",cemi,90,0,"ordinaria");
		Urna urna15=new Urna("Urnita15",cemi,50,0,"ordinaria");
		
		
		Urna urna17=new Urna("default",cementerio,70,2,"fija");
		Urna urna18=new Urna("default",cementerio,50,2,"ordinaria");
		Urna urna19=new Urna("Urnita719",cementerio,40,2,"ordinaria");
		
		
		Urna urna21=new Urna("default",cemito,70,0,"fija");
		Urna urna22=new Urna("Urnita22",cemito,50,1,"fija");
		Urna urna23=new Urna("Urnita23",cemito,40,2,"ordinaria");

		
		Tumba tumba1=new Tumba("Tumbita1",cemi1,1.70,0);
		Tumba tumba2=new Tumba("Tumbita2",cemi1,1.50,0);
		Tumba tumba3=new Tumba("Tumbita3",cemito1,1.60,0);
		
		Tumba tumba4=new Tumba("Tumbita4",cemito1,1.50,0);
		Tumba tumba5=new Tumba("Tumbita5",cementerio1,1.50,0);
		Tumba tumba6=new Tumba("Tumbita6",cementerio1,1.50,0);
		
		Tumba tumba7=new Tumba("Tumbita7",cementerio1,1.50,0);
		Tumba tumba8=new Tumba("Tumbita8",cementerio1,1.50,0);
		Tumba tumba9=new Tumba("Tumbita9",cementerio1,1.50,0);
		
		
		urna13.agregarCliente(n1);
		urna14.agregarCliente(n2);
		urna15.agregarCliente(n3);
		urna17.agregarCliente(n5);
		
		tumba1.agregarCliente(n4);
		tumba2.agregarCliente(n7);
		tumba3.agregarCliente(n8);
		tumba4.agregarCliente(n9);
		tumba5.agregarCliente(n10);
	
		
		
		
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("[1] Funcionalidad crematorio");
		System.out.println("[2] Funcionalidad Exhumación");
		System.out.println("[3] Funcionalidad Entierro");
		
		System.out.print("Ingrese el índice de la funcionalidad: ");
		int opcion =scanner.nextInt();
		
		switch (opcion) {
			
		case 1:
			funcionalidadCrematorio();
			break;
		case 2:
			FuncionalidadExhumacion.funcionalidadExhumacion();
			break;
		case 3:
			FuncionalidadEntierro.funcionalidadEntierro();
			break;
			
		default:
			System.out.println("Número fuera de rango");
		}
		
		
		
	}
	

	

}
