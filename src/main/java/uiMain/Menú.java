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
					indice+=1;
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
					indice+=1;
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
					indice+=1;
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
		
		//Empleados sepultureros
		
		Empleado empleadoF11S= new Empleado("Adrián Vargas",null,"mañana","sepulturero",1000000,funeraria1);
		Empleado empleadoF12S= new Empleado("Benjamín Díaz",null,"mañana","sepulturero",1000000,funeraria1);
		Empleado empleadoF13S= new Empleado("Cristian Herrera",null,"tarde","sepulturero",1000000,funeraria1);
		Empleado empleadoF14S= new Empleado("Diana Moreno",null,"tarde","sepulturero",1000000,funeraria1);
		Empleado empleadoF15S= new Empleado("Gabriela Arias",null,"noche","sepulturero",1000000,funeraria1);
		
		
		//Empleados cremador
		
		Empleado empleadoF11C= new Empleado("David Soto",null,"mañana","cremador",1000000,funeraria1);
		Empleado empleadoF12C= new Empleado("Esteban Cordero",null,"mañana","cremador",1000000,funeraria1);
		Empleado empleadoF13C= new Empleado("Federico Gil",null,"tarde","cremador",1000000,funeraria1);
		Empleado empleadoF14C= new Empleado("Elena Vázquez",null,"noche","cremador",1000000,funeraria1);
		Empleado empleadoF15C= new Empleado("Isabela López",null,"noche","cremador",1000000,funeraria1);
		
		
		
		Funeraria funeraria2 = new Funeraria("Caminos de Luz", cuenta,cuenta);
		
		//Empleados sepultureros
		
		Empleado empleadoF21S= new Empleado("Guillermo Romero",null,"mañana","sepulturero",1000000,funeraria2);
		Empleado empleadoF22S= new Empleado("Jorge Álvarez",null,"tarde","sepulturero",1000000,funeraria2);
		Empleado empleadoF23S= new Empleado("Florencia Pérez",null,"tarde","sepulturero",1000000,funeraria2);
		Empleado empleadoF24S= new Empleado("Jazmín Navarro",null,"tarde","sepulturero",1000000,funeraria2);
		Empleado empleadoF25S= new Empleado("Alicia Moreno",null,"noche","sepulturero",1000000,funeraria2);
				
				
		//Empleados cremador
				
		Empleado empleadoF21C= new Empleado("Marco Ruiz",null,"noche","cremador",1000000,funeraria2);
		Empleado empleadoF22C= new Empleado("Natalia Ortega",null,"mañana","cremador",1000000,funeraria2);
		Empleado empleadoF23C= new Empleado("Casey Morales",null,"tarde","cremador",1000000,funeraria2);
		Empleado empleadoF24C= new Empleado("Karla Soto",null,"noche","cremador",1000000,funeraria2);
		Empleado empleadoF25C= new Empleado("Dakota Torres",null,"noche","cremador",1000000,funeraria2);
		
		
		
		
		Funeraria funeraria3 = new Funeraria("Recuerdos Eternos", cuenta,cuenta);
		
		Empleado empleadoF31S= new Empleado("Nicolás Fernández",null,"mañana","sepulturero",1000000,funeraria3);
		Empleado empleadoF32S= new Empleado("Alex Rivera",null,"tarde","sepulturero",1000000,funeraria3);
		Empleado empleadoF33S= new Empleado("Elliot Cruz",null,"tarde","sepulturero",1000000,funeraria3);
		Empleado empleadoF34S= new Empleado("Camila Silva",null,"mañana","sepulturero",1000000,funeraria3);
		Empleado empleadoF35S= new Empleado("Harper Fernández",null,"noche","sepulturero",1000000,funeraria3);
				
				
		//Empleados cremador
				
		Empleado empleadoF31C= new Empleado("Jesse Jiménez",null,"noche","cremador",1000000,funeraria3);
		Empleado empleadoF32C= new Empleado("Logan Mendoza",null,"mañana","cremador",1000000,funeraria3);
		Empleado empleadoF33C= new Empleado("Sam Vargas",null,"tarde","cremador",1000000,funeraria3);
		Empleado empleadoF34C= new Empleado("Karla Soto",null,"mañana","cremador",1000000,funeraria3);
		Empleado empleadoF35C= new Empleado("Taylor López",null,"noche","cremador",1000000,funeraria3);
	
		
		//Empleados generales
		
		//Empleados conductor
		
		Empleado empleadoC1= new Empleado("Bruno Salgado",null,"mañana","conductor",1000000,funeraria1);
		Empleado empleadoC2= new Empleado("Bárbara López",null,"mañana","conductor",1000000,funeraria1);
		Empleado empleadoC3= new Empleado("Óscar Morales",null,"tarde","conductor",1000000,funeraria1);
		Empleado empleadoC4= new Empleado("Dulce María Reyes",null,"tarde","conductor",1000000,funeraria1);
		Empleado empleadoC5= new Empleado("Evelyn Rodríguez",null,"noche","conductor",1000000,funeraria1);
		Empleado empleadoC6= new Empleado("Kevin Castillo",null,"noche","conductor",1000000,funeraria1);
		
		
		//Agregar empleados a funeraria 2
		funeraria2.agregarEmpleado(empleadoC1);
		funeraria2.agregarEmpleado(empleadoC2);
		funeraria2.agregarEmpleado(empleadoC3);
		funeraria2.agregarEmpleado(empleadoC4);
		funeraria2.agregarEmpleado(empleadoC5);
		funeraria2.agregarEmpleado(empleadoC6);
		
		//Agregar empleados a funeraria 3
		funeraria3.agregarEmpleado(empleadoC1);
		funeraria3.agregarEmpleado(empleadoC2);
		funeraria3.agregarEmpleado(empleadoC3);
		funeraria3.agregarEmpleado(empleadoC4);
		funeraria3.agregarEmpleado(empleadoC5);
		funeraria3.agregarEmpleado(empleadoC6);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Familiares Mujeres
		Familiar a1= new Familiar("Mario",711,50,cuenta,"padre",17);
		Familiar a2= new Familiar("Alberto",712,32,cuenta,"conyuge",13);
		Familiar a3= new Familiar("Carlos",713,37,cuenta,"hermano",17);
		Familiar a4= new Familiar("Samantha",714,50,cuenta,"padre",17);
		
		//Familiares para todos
		Familiar b1= new Familiar("Samuel",715,60,cuenta,"padre",17);
		Familiar b2= new Familiar("Alma",716,60,cuenta,"padre",13);
		Familiar b3= new Familiar("Eduardo",717,37,cuenta,"hermano",17);
		Familiar b4= new Familiar("Maria",5, "hermano",b3);
		
		//Familiares Hombres 
		Familiar c1= new Familiar("Armando",718,50,cuenta,"padre",17);
		Familiar c2= new Familiar("Catalina",719,32,cuenta,"conyuge",13);
		Familiar c3= new Familiar("Sebastian",7110,37,cuenta,"hermano",17);
		Familiar c4= new Familiar("Alba",7111,25,cuenta,"hijo",17);
		
		
		//Listas de familiares A
		ArrayList<Familiar> familiarA=new ArrayList<Familiar>();
		familiarA.add(a1);
		familiarA.add(a2);
		familiarA.add(a3);
		familiarA.add(a4);
		
		//Listas de familiares B
		ArrayList<Familiar> familiarB=new ArrayList<Familiar>();
		familiarB.add(b1);
		familiarB.add(b2);
		familiarB.add(b3);
		familiarB.add(b4);
		
		//Listas de familiares C
		ArrayList<Familiar> familiarC=new ArrayList<Familiar>();
		familiarC.add(c1);
		familiarC.add(c2);
		familiarC.add(c3);
		familiarC.add(c4);
		
		
		
		
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
		
		//Objetos Cementerio 1 Cenizas 
		
		Urna urnaF1C11=new Urna("Urnita Eterna Paz",cementerioF11Ce,70,1,"fija");
		Urna urnaF1C12=new Urna("Urnita Memoria Serene",cementerioF11Ce,80,0,"ordinaria");
		Urna urnaF1C13=new Urna("Urnita Descanso Sagrado",cementerioF11Ce,60,0,"ordinaria");
		Urna urnaF1C14=new Urna("Urnita Luz Eterna",cementerioF11Ce,60,1,"fija");
		
		
		//Objetos Cementerio 2 Urna Cenizas
		
		Urna urnaF1C21=new Urna("Urnita Tranquilidad Infinita",cementerioF12Ce,70,1,"fija");
		Urna urnaF1C22=new Urna("Urnita Homenaje Perpetuo",cementerioF12Ce,80,0,"ordinaria");
		Urna urnaF1C23=new Urna("Urnita Amanecer Sereno",cementerioF12Ce,70,0,"ordinaria");
		Urna urnaF1C24=new Urna("Urnita Refugio del Alma",cementerioF12Ce,60,1,"fija");
		
		//Objetos Cementerio 3 Urna Cenizas
		
		Urna urnaF1C31=new Urna("Urnita Oasis de Recuerdo",cementerioF13Ce,70,1,"fija");
		Urna urnaF1C32=new Urna("Urnita Sombra Amada",cementerioF13Ce,80,0,"ordinaria");
		Urna urnaF1C33=new Urna("Urnita Caja de la Verdad",cementerioF13Ce,10,0,"ordinaria");
		Urna urnaF1C34=new Urna("Urnita Urna de la Democracia",cementerioF13Ce,60,1,"fija");
		
		//Objetos Cementerio 4 Urna Cenizas
		
		Urna urnaF1C41=new Urna("Urnita Voz del Pueblo",cementerioF14Ce,70,1,"fija");
		Urna urnaF1C42=new Urna("Urnita Cámara de Decisiones",cementerioF14Ce,80,0,"ordinaria");
		Urna urnaF1C43=new Urna("Urnita Bóveda de Opiniones",cementerioF14Ce,10,0,"ordinaria");
		Urna urnaF1C44=new Urna("Urnita Recinto Electoral",cementerioF14Ce,60,1,"fija");
		
		//Objetos Cementerio 5 Urna Cenizas
		
		Urna urnaF1C51=new Urna("Urnita Contenedor de Voluntades",cementerioF15Ce,70,1,"fija");
		Urna urnaF1C52=new Urna("Urnita Caja de Equidad",cementerioF15Ce,80,0,"ordinaria");
		Urna urnaF1C53=new Urna("Urnita de la Justicia",cementerioF15Ce,10,0,"ordinaria");
		Urna urnaF1C54=new Urna("Urnita Escudo Electoral",cementerioF15Ce,60,1,"fija");
		
		//Objetos Cementerio 6 Urna Cenizas
		
		Urna urnaF1C61=new Urna("Urnita Cápsula de Sueños",cementerioF16Ce,70,1,"fija");
		Urna urnaF1C62=new Urna("Urnita Templo de Belleza",cementerioF16Ce,80,0,"ordinaria");
		Urna urnaF1C63=new Urna("Urnita Misterio Dorado",cementerioF16Ce,10,0,"ordinaria");
		Urna urnaF1C64=new Urna("Urnita Joyero de Recuerdos",cementerioF16Ce,60,1,"fija");
		
		
		/////////////////////////////// 
		
		//Objetos Cementerio 1 Tumba Cuerpos
		
		Tumba tumbaF1C11=new Tumba("Tumbita Aquí Reposa un Corazón Noble",cementerioF11Cu,1.70,0);
		Tumba tumbaF1C12=new Tumba("Tumbita Amado por Siempre",cementerioF11Cu,1.10,0);
		Tumba tumbaF1C13=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF11Cu,1.60,1);
		Tumba tumbaF1C14=new Tumba("Tumbita Un Alma Inmortal",cementerioF11Cu,1.20,1);
		Tumba tumbaF1C15=new Tumba("Tumbita Tu Luz Nos Guía",cementerioF11Cu,1.75,2);
		Tumba tumbaF1C16=new Tumba("Tumbita Querido y Recordado",cementerioF11Cu,1,2);
	
		//Objetos Cementerio 2 Tumba Cuerpos
		
		Tumba tumbaF1C21=new Tumba("Tumbita Descansa en Paz, Amado",cementerioF12Cu,1.70,0);
		Tumba tumbaF1C22=new Tumba("Tumbita Tu Memoria Vive en Nosotros",cementerioF12Cu,1.10,0);
		Tumba tumbaF1C23=new Tumba("Tumbita El Amor Trasciende",cementerioF12Cu,1.60,1);
		Tumba tumbaF1C24=new Tumba("Tumbita Una Vida Lleno de Amor",cementerioF12Cu,1.20,1);
		Tumba tumbaF1C25=new Tumba("Tumbita Copa del Encanto",cementerioF12Cu,1.75,2);
		Tumba tumbaF1C26=new Tumba("Tumbita Portal de Arte",cementerioF12Cu,1,2);
		
		//Objetos Cementerio 3 Tumba Cuerpos
		
		Tumba tumbaF1C31=new Tumba("Tumbita Esfera de Serenidad",cementerioF13Cu,1.70,0);
		Tumba tumbaF1C32=new Tumba("Tumbita Reflejo de Elegancia",cementerioF13Cu,1.10,0);
		Tumba tumbaF1C33=new Tumba("Tumbita Caja de Maravillas",cementerioF13Cu,1.60,1);
		Tumba tumbaF1C34=new Tumba("Tumbita Jardín del Recuerdo",cementerioF13Cu,1.20,1);
		Tumba tumbaF1C35=new Tumba("Tumbita Refugio del Alma",cementerioF13Cu,1.75,2);
		Tumba tumbaF1C36=new Tumba("Tumbita Lugar de Serenidad",cementerioF13Cu,1,2);
		
		//Objetos Cementerio 4 Tumba Cuerpos
		
		Tumba tumbaF1C41=new Tumba("Tumbita Eterna Luz",cementerioF14Cu,1.70,0);
		Tumba tumbaF1C42=new Tumba("Tumbita Sombra Sagrada",cementerioF14Cu,1.10,0);
		Tumba tumbaF1C43=new Tumba("Tumbita Cámara del Silencio",cementerioF14Cu,1.60,1);
		Tumba tumbaF1C44=new Tumba("Tumbita Rincón de Paz",cementerioF14Cu,1.20,1);
		Tumba tumbaF1C45=new Tumba("Tumbita Hogar de Paz",cementerioF14Cu,1.75,2);
		Tumba tumbaF1C46=new Tumba("Tumbita Sendero de Tranquilidad",cementerioF14Cu,1,2);
		
		//Objetos Cementerio 5 Tumba Cuerpos
		
		Tumba tumbaF1C51=new Tumba("Tumbita Velo de Recuerdo",cementerioF15Cu,1.70,0);
		Tumba tumbaF1C52=new Tumba("Tumbita Cascada de Paz",cementerioF15Cu,1.10,0);
		Tumba tumbaF1C53=new Tumba("Tumbita Refugio Perpetuo",cementerioF15Cu,1.60,1);
		Tumba tumbaF1C54=new Tumba("Tumbita Sombra de Amor",cementerioF15Cu,1.20,1);
		Tumba tumbaF1C55=new Tumba("Tumbita Eterna Quietud",cementerioF15Cu,1.75,2);
		Tumba tumbaF1C56=new Tumba("Tumbita Altar de Recuerdos",cementerioF15Cu,1,2);
	
		//Objetos Cementerio 6 Tumba Cuerpos
		
		Tumba tumbaF1C61=new Tumba("Tumbita Una Vida de Amor y Bondad",cementerioF16Cu,1.70,0);
		Tumba tumbaF1C62=new Tumba("Tumbita Siempre en Nuestro Corazón y Pensamiento",cementerioF16Cu,1.10,0);
		Tumba tumbaF1C63=new Tumba("Tumbita En Tu Ausencia",cementerioF16Cu,1.60,1);
		Tumba tumbaF1C64=new Tumba("Tumbita Tu Presencia es Más Fuerte",cementerioF16Cu,1.20,1);
		Tumba tumbaF1C65=new Tumba("Tumbita Una Vida Dedicada al Amor",cementerioF16Cu,1.75,2);
		Tumba tumbaF1C66=new Tumba("Tumbita Un Alma Valiente",cementerioF16Cu,1,2);
		
		
		//Objetos Crematorio
		
		//crematorios pertenecientes a F1 --> Funeraria 1
		Crematorio crematorioF11 = new Crematorio ("Crematorio Luz y Paz",100,null,"oro", null,funeraria1); 
		Crematorio crematorioF12 = new Crematorio ("Hogar de la Ascensión",78,null,"oro", null,funeraria1); 
		
		Crematorio crematorioF13 = new Crematorio ("Fuego de la Memoria",78,null,"plata", null,funeraria1); 
		Crematorio crematorioF14 = new Crematorio ("Crematorio Serenidad Eterna",78,null,"plata", null,funeraria1);
		
		Crematorio crematorioF15 = new Crematorio ("Llama de la Eternidada",78,null,"bronce", null,funeraria1); 
		Crematorio crematorioF16 = new Crematorio ("Refugio del Alba",78,null,"bronce", null,funeraria1);
		
		//Clientes F1 - Mayores de edad
		
		Cliente clienteF11 = new Cliente("Alejandro Rodríguez",123,30,null,"oro",familiarC);
		Cliente clienteF12 = new Cliente("Diego Martínez",1234,25,null,"oro",familiarC);
		
		Cliente clienteF13 = new Cliente("Carlos Fernández",1235,90,null,"plata",familiarB);
		Cliente clienteF14 = new Cliente("María González",1236,57,null,"plata",familiarB);
		
		Cliente clienteF15 = new Cliente("Laura Fernández",1237,21,null,"bronce",familiarC);
		Cliente clienteF16 = new Cliente("Isabel Rodríguez",1238,50,null,"bronce",familiarC);
	
		
		//Clientes F1 - Menores de edad
		Cliente clienteF17 = new Cliente("Javier Gómez",5,"oro",familiarB);
		Cliente clienteF18 = new Cliente("Sofía Martínez",17,"oro",familiarB);
		
		Cliente clienteF19 = new Cliente("Carolina López",15,"plata",familiarB);
		Cliente clienteF110 = new Cliente("Manuel López",13,"plata",familiarB);
		
	
		//AgregarClientes
		funeraria1.agregarCliente(clienteF11);
		funeraria1.agregarCliente(clienteF12);
		funeraria1.agregarCliente(clienteF13);
		funeraria1.agregarCliente(clienteF14);
		funeraria1.agregarCliente(clienteF15);
		funeraria1.agregarCliente(clienteF16);
		funeraria1.agregarCliente(clienteF17);
		funeraria1.agregarCliente(clienteF18);
		funeraria1.agregarCliente(clienteF19);
		funeraria1.agregarCliente(clienteF110);
		
		
		//Funeraraia 2
		
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
		
		//Objetos Cementerio 1 Cenizas 
		
		Urna urnaF2C11=new Urna("Urnita Eterna Paz",cementerioF21Ce,70,1,"fija");
		Urna urnaF2C12=new Urna("Urnita Memoria Serene",cementerioF21Ce,80,0,"fija");
		Urna urnaF2C13=new Urna("Urnita Descanso Sagrado",cementerioF21Ce,10,0,"ordinaria");
		Urna urnaF2C14=new Urna("Urnita Luz Eterna",cementerioF21Ce,60,1,"fija");
		
		
		//Objetos Cementerio 2 Urna Cenizas
		
		Urna urnaF2C21=new Urna("Urnita Tranquilidad Infinita",cementerioF22Ce,50,1,"fija");
		Urna urnaF2C22=new Urna("Urnita Homenaje Perpetuo",cementerioF22Ce,80,0,"ordinaria");
		Urna urnaF2C23=new Urna("Urnita Amanecer Sereno",cementerioF22Ce,10,0,"ordinaria");
		Urna urnaF2C24=new Urna("Urnita Refugio del Alma",cementerioF22Ce,60,1,"ordinaria");
		
		//Objetos Cementerio 3 Urna Cenizas
		
		Urna urnaF2C31=new Urna("Urnita Oasis de Recuerdo",cementerioF23Ce,30,1,"fija");
		Urna urnaF2C32=new Urna("Urnita Sombra Amada",cementerioF23Ce,20,0,"ordinaria");
		Urna urnaF2C33=new Urna("Urnita Caja de la Verdad",cementerioF23Ce,60,2,"ordinaria");
		Urna urnaF2C34=new Urna("Urnita Urna de la Democracia",cementerioF23Ce,60,1,"fija");
		
		//Objetos Cementerio 4 Urna Cenizas
		
		Urna urnaF2C41=new Urna("Urnita Voz del Pueblo",cementerioF24Ce,70,1,"fija");
		Urna urnaF2C42=new Urna("Urnita Cámara de Decisiones",cementerioF24Ce,80,0,"ordinaria");
		Urna urnaF2C43=new Urna("Urnita Bóveda de Opiniones",cementerioF24Ce,70,0,"ordinaria");
		Urna urnaF2C44=new Urna("Urnita Recinto Electoral",cementerioF24Ce,60,1,"fija");
		
		//Objetos Cementerio 5 Urna Cenizas
		
		Urna urnaF2C51=new Urna("Urnita Contenedor de Voluntades",cementerioF25Ce,70,1,"fija");
		Urna urnaF2C52=new Urna("Urnita Caja de Equidad",cementerioF25Ce,80,1,"ordinaria");
		Urna urnaF2C53=new Urna("Urnita de la Justicia",cementerioF25Ce,10,2,"ordinaria");
		Urna urnaF2C54=new Urna("Urnita Escudo Electoral",cementerioF25Ce,60,1,"fija");
		
		//Objetos Cementerio 6 Urna Cenizas
		
		Urna urnaF2C61=new Urna("Urnita Cápsula de Sueños",cementerioF26Ce,70,1,"fija");
		Urna urnaF2C62=new Urna("Urnita Templo de Belleza",cementerioF26Ce,80,0,"ordinaria");
		Urna urnaF2C63=new Urna("Urnita Misterio Dorado",cementerioF26Ce,10,0,"ordinaria");
		Urna urnaF2C64=new Urna("Urnita Joyero de Recuerdos",cementerioF26Ce,60,1,"fija");
		
		
		/////////////////////////////// 
		
		//Objetos Cementerio 1 Tumba Cuerpos
		
		Tumba tumbaF2C11=new Tumba("Tumbita Aquí Reposa un Corazón Noble",cementerioF21Cu,1.70,1);
		Tumba tumbaF2C12=new Tumba("Tumbita Amado por Siempre",cementerioF21Cu,1.10,0);
		Tumba tumbaF2C13=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF21Cu,1.60,1);
		Tumba tumbaF2C14=new Tumba("Tumbita Un Alma Inmortal",cementerioF21Cu,1.20,1);
		Tumba tumbaF2C15=new Tumba("Tumbita Tu Luz Nos Guía",cementerioF21Cu,1.55,2);
		Tumba tumbaF2C16=new Tumba("Tumbita Querido y Recordado",cementerioF21Cu,1,2);
	
		//Objetos Cementerio 2 Tumba Cuerpos
		
		Tumba tumbaF2C21=new Tumba("Tumbita Descansa en Paz, Amado",cementerioF22Cu,1.70,0);
		Tumba tumbaF2C22=new Tumba("Tumbita Tu Memoria Vive en Nosotros",cementerioF22Cu,1.10,0);
		Tumba tumbaF2C23=new Tumba("Tumbita El Amor Trasciende",cementerioF22Cu,1.80,1);
		Tumba tumbaF2C24=new Tumba("Tumbita Una Vida Lleno de Amor",cementerioF22Cu,1.20,1);
		Tumba tumbaF2C25=new Tumba("Tumbita Copa del Encanto",cementerioF22Cu,1.75,2);
		Tumba tumbaF2C26=new Tumba("Tumbita Portal de Arte",cementerioF22Cu,1.20,2);
		
		//Objetos Cementerio 3 Tumba Cuerpos
		
		Tumba tumbaF2C31=new Tumba("Tumbita Esfera de Serenidad",cementerioF23Cu,1.70,0);
		Tumba tumbaF2C32=new Tumba("Tumbita Reflejo de Elegancia",cementerioF23Cu,1.10,0);
		Tumba tumbaF2C33=new Tumba("Tumbita Caja de Maravillas",cementerioF23Cu,1.60,1);
		Tumba tumbaF2C34=new Tumba("Tumbita Jardín del Recuerdo",cementerioF23Cu,1.20,1);
		Tumba tumbaF2C35=new Tumba("Tumbita Refugio del Alma",cementerioF23Cu,1.35,1);
		Tumba tumbaF2C36=new Tumba("Tumbita Lugar de Serenidad",cementerioF23Cu,1,2);
		
		//Objetos Cementerio 4 Tumba Cuerpos
		
		Tumba tumbaF2C41=new Tumba("Tumbita Eterna Luz",cementerioF24Cu,1.70,1);
		Tumba tumbaF2C42=new Tumba("Tumbita Sombra Sagrada",cementerioF24Cu,1.10,0);
		Tumba tumbaF2C43=new Tumba("Tumbita Cámara del Silencio",cementerioF24Cu,1.60,1);
		Tumba tumbaF2C44=new Tumba("Tumbita Rincón de Paz",cementerioF24Cu,1,1);
		Tumba tumbaF2C45=new Tumba("Tumbita Hogar de Paz",cementerioF24Cu,1.75,2);
		Tumba tumbaF2C46=new Tumba("Tumbita Sendero de Tranquilidad",cementerioF24Cu,1,2);
		
		//Objetos Cementerio 5 Tumba Cuerpos
		
		Tumba tumbaF2C51=new Tumba("Tumbita Velo de Recuerdo",cementerioF25Cu,1.70,0);
		Tumba tumbaF2C52=new Tumba("Tumbita Cascada de Paz",cementerioF25Cu,1.10,0);
		Tumba tumbaF2C53=new Tumba("Tumbita Refugio Perpetuo",cementerioF25Cu,1.60,1);
		Tumba tumbaF2C54=new Tumba("Tumbita Sombra de Amor",cementerioF25Cu,1.20,0);
		Tumba tumbaF2C55=new Tumba("Tumbita Eterna Quietud",cementerioF25Cu,1.40,2);
		Tumba tumbaF2C56=new Tumba("Tumbita Altar de Recuerdos",cementerioF25Cu,1,2);
	
		//Objetos Cementerio 6 Tumba Cuerpos
		
		Tumba tumbaF2C61=new Tumba("Tumbita Una Vida de Amor y Bondad",cementerioF26Cu,1.50,0);
		Tumba tumbaF2C62=new Tumba("Tumbita Siempre en Nuestro Corazón y Pensamiento",cementerioF26Cu,1.10,0);
		Tumba tumbaF2C63=new Tumba("Tumbita En Tu Ausencia",cementerioF26Cu,1.30,1);
		Tumba tumbaF2C64=new Tumba("Tumbita Tu Presencia es Más Fuerte",cementerioF26Cu,1.20,1);
		Tumba tumbaF2C65=new Tumba("Tumbita Una Vida Dedicada al Amor",cementerioF26Cu,1.75,2);
		Tumba tumbaF2C66=new Tumba("Tumbita Un Alma Valiente",cementerioF26Cu,1.2,2);
		
		
		
		//crematorios pertenecientes a F2 --> Funeraria 2
		Crematorio crematorioF21 = new Crematorio ("Crematorio del Silencio",100,null,"oro", null,funeraria2); 
		Crematorio crematorioF22 = new Crematorio ("Ascenso y Tranquilidad",78,null,"oro", null,funeraria2); 
		
		Crematorio crematorioF23 = new Crematorio ("Brasa de Paz",78,null,"plata", null,funeraria2); 
		Crematorio crematorioF24 = new Crematorio ("Eterna Luz Crematorio",78,null,"plata", null,funeraria2);
		
		Crematorio crematorioF25 = new Crematorio ("Crematorio del Renacer",78,null,"bronce", null,funeraria2); 
		Crematorio crematorioF26 = new Crematorio ("Fuego y Serenidad",78,null,"bronce", null,funeraria2);		
	
		//Clientes F2 - Mayores de edad
		
		Cliente clienteF21 = new Cliente("Valeria Sánchez",231,30,null,"oro",familiarA);
		Cliente clienteF22 = new Cliente("Patricia Morales",232,25,null,"oro",familiarA);
								
		Cliente clienteF23 = new Cliente("Gabriela García",233,90,null,"plata",familiarA);
		Cliente clienteF24 = new Cliente("Andrés Vargas",234,57,null,"plata",familiarC);
								
		Cliente clienteF25 = new Cliente("Sergio Pérez",235,35,null, "bronce",familiarC);
		Cliente clienteF26 = new Cliente("Luis García",236,50,null, "bronce",familiarC);
						
						
		//Clientes F2 - Menores de edad
		Cliente clienteF27 = new Cliente("Rafael Morales",5,"oro",familiarB);
		Cliente clienteF28 = new Cliente("Pablo Sánchez",17,"oro",familiarB);
								
		Cliente clienteF29 = new Cliente("Ana Belén Ruiz",15,"bronce",familiarB);
		Cliente clienteF210 = new Cliente("Claudia Romero",13,"bronce",familiarB);
				
		
		//AgregarClientes
		funeraria2.agregarCliente(clienteF21);
		funeraria2.agregarCliente(clienteF22);
		funeraria2.agregarCliente(clienteF23);
		funeraria2.agregarCliente(clienteF24);
		funeraria2.agregarCliente(clienteF25);
		funeraria2.agregarCliente(clienteF26);
		funeraria2.agregarCliente(clienteF27);
		funeraria2.agregarCliente(clienteF28);
		funeraria2.agregarCliente(clienteF29);
		funeraria2.agregarCliente(clienteF210);
		
		
		
		//Funeraria 3
		
		//objetos cementerio
		
		Cementerio cementerioF31Ce = new Cementerio ("Jardín de los Ángeles",78,cuenta,"oro", null,"cenizas",funeraria3); 
		Cementerio cementerioF32Ce = new Cementerio ("Campo de la Tranquilidad",85,cuenta,"oro", null,"cenizas",funeraria3); 
				
		Cementerio cementerioF33Ce = new Cementerio ("Oasis de Paz",79,cuenta,"plata", null,"cenizas",funeraria3); 
		Cementerio cementerioF34Ce = new Cementerio ("Colinas del Recuerdo",78,cuenta,"plata", null,"cenizas",funeraria3);
				 
		Cementerio cementerioF35Ce = new Cementerio ("Rincón del Silencio",50,cuenta,"bronce", null,"cenizas",funeraria3); 
		Cementerio cementerioF36Ce = new Cementerio ("Eterna Armonía",78,cuenta,"bronce", null,"cenizas",funeraria3); 
		
		
		//cementerios pertenecientes a F3 --> Funeraria 3 - cuerpos
		Cementerio cementerioF31Cu = new Cementerio ("Bosque de los Sueños",78,cuenta,"oro", null,"cuerpos",funeraria3); 
		Cementerio cementerioF32Cu = new Cementerio ("Pradera del Descanso",85,cuenta,"oro", null,"cuerpos",funeraria3); 
						
		Cementerio cementerioF33Cu = new Cementerio ("Refugio de la Memoria",50,cuenta,"plata", null,"cuerpos",funeraria3); 
		Cementerio cementerioF34Cu = new Cementerio ("Sendero de la Memoria",78,cuenta,"plata", null,"cuerpos",funeraria3);
						 
		Cementerio cementerioF35Cu = new Cementerio ("Valle del Reposo",78,cuenta,"bronce", null,"cuerpos",funeraria3); 
		Cementerio cementerioF36Cu = new Cementerio ("Jardines del Refugio",78,cuenta,"bronce", null,"cuerpos",funeraria3);
		
		//Objetos Cementerio 1 Cenizas 
		
		Urna urnaF3C11=new Urna("Urnita Eterna Paz",cementerioF31Ce,20,1,"fija");
		Urna urnaF3C12=new Urna("Urnita Memoria Serene",cementerioF31Ce,40,0,"fija");
		Urna urnaF3C13=new Urna("Urnita Descanso Sagrado",cementerioF31Ce,10,0,"ordinaria");
		Urna urnaF3C14=new Urna("Urnita Luz Eterna",cementerioF31Ce,60,1,"fija");
		
		
		//Objetos Cementerio 2 Urna Cenizas
		
		Urna urnaF3C21=new Urna("Urnita Tranquilidad Infinita",cementerioF32Ce,50,1,"fija");
		Urna urnaF3C22=new Urna("Urnita Homenaje Perpetuo",cementerioF32Ce,80,0,"ordinaria");
		Urna urnaF3C23=new Urna("Urnita Amanecer Sereno",cementerioF32Ce,10,0,"fija");
		Urna urnaF3C24=new Urna("Urnita Refugio del Alma",cementerioF32Ce,60,1,"fija");
		
		//Objetos Cementerio 3 Urna Cenizas
		
		Urna urnaF3C31=new Urna("Urnita Oasis de Recuerdo",cementerioF33Ce,30,1,"fija");
		Urna urnaF3C32=new Urna("Urnita Sombra Amada",cementerioF33Ce,10,0,"ordinaria");
		Urna urnaF3C33=new Urna("Urnita Caja de la Verdad",cementerioF33Ce,60,2,"ordinaria");
		Urna urnaF3C34=new Urna("Urnita Urna de la Democracia",cementerioF33Ce,60,1,"fija");
		
		//Objetos Cementerio 4 Urna Cenizas
		
		Urna urnaF3C41=new Urna("Urnita Voz del Pueblo",cementerioF34Ce,70,1,"fija");
		Urna urnaF3C42=new Urna("Urnita Cámara de Decisiones",cementerioF34Ce,80,0,"ordinaria");
		Urna urnaF3C43=new Urna("Urnita Bóveda de Opiniones",cementerioF34Ce,50,0,"ordinaria");
		Urna urnaF3C44=new Urna("Urnita Recinto Electoral",cementerioF34Ce,60,1,"fija");
		
		//Objetos Cementerio 5 Urna Cenizas
		
		Urna urnaF3C51=new Urna("Urnita Contenedor de Voluntades",cementerioF35Ce,70,1,"fija");
		Urna urnaF3C52=new Urna("Urnita Caja de Equidad",cementerioF35Ce,80,1,"ordinaria");
		Urna urnaF3C53=new Urna("Urnita de la Justicia",cementerioF35Ce,10,2,"fija");
		Urna urnaF3C54=new Urna("Urnita Escudo Electoral",cementerioF35Ce,60,1,"fija");
		
		//Objetos Cementerio 6 Urna Cenizas
		
		Urna urnaF3C61=new Urna("Urnita Cápsula de Sueños",cementerioF36Ce,70,1,"fija");
		Urna urnaF3C62=new Urna("Urnita Templo de Belleza",cementerioF36Ce,60,0,"ordinaria");
		Urna urnaF3C63=new Urna("Urnita Misterio Dorado",cementerioF36Ce,10,0,"ordinaria");
		Urna urnaF3C64=new Urna("Urnita Joyero de Recuerdos",cementerioF36Ce,60,1,"fija");
		
		
		/////////////////////////////// 
		
		//Objetos Cementerio 1 Tumba Cuerpos
		
		Tumba tumbaF3C11=new Tumba("Tumbita Aquí Reposa un Corazón Noble",cementerioF31Cu,1.70,1);
		Tumba tumbaF3C12=new Tumba("Tumbita Amado por Siempre",cementerioF31Cu,1.10,0);
		Tumba tumbaF3C13=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF31Cu,1.60,1);
		Tumba tumbaF3C14=new Tumba("Tumbita Un Alma Inmortal",cementerioF31Cu,1.20,1);
		Tumba tumbaF3C15=new Tumba("Tumbita Tu Luz Nos Guía",cementerioF31Cu,1.35,2);
		Tumba tumbaF3C16=new Tumba("Tumbita Querido y Recordado",cementerioF31Cu,1,2);
	
		//Objetos Cementerio 2 Tumba Cuerpos
		
		Tumba tumbaF3C21=new Tumba("Tumbita Descansa en Paz, Amado",cementerioF32Cu,1.70,0);
		Tumba tumbaF3C22=new Tumba("Tumbita Tu Memoria Vive en Nosotros",cementerioF32Cu,1.10,0);
		Tumba tumbaF3C23=new Tumba("Tumbita El Amor Trasciende",cementerioF32Cu,1.80,1);
		Tumba tumbaF3C24=new Tumba("Tumbita Una Vida Lleno de Amor",cementerioF32Cu,1.20,1);
		Tumba tumbaF3C25=new Tumba("Tumbita Copa del Encanto",cementerioF32Cu,1.75,2);
		Tumba tumbaF3C26=new Tumba("Tumbita Portal de Arte",cementerioF32Cu,1.20,2);
		
		//Objetos Cementerio 3 Tumba Cuerpos
		
		Tumba tumbaF3C31=new Tumba("Tumbita Esfera de Serenidad",cementerioF23Cu,1.70,0);
		Tumba tumbaF3C32=new Tumba("Tumbita Reflejo de Elegancia",cementerioF23Cu,1.10,0);
		Tumba tumbaF3C33=new Tumba("Tumbita Caja de Maravillas",cementerioF23Cu,1.60,1);
		Tumba tumbaF3C34=new Tumba("Tumbita Jardín del Recuerdo",cementerioF23Cu,1.20,1);
		Tumba tumbaF3C35=new Tumba("Tumbita Refugio del Alma",cementerioF23Cu,1.35,1);
		Tumba tumbaF3C36=new Tumba("Tumbita Lugar de Serenidad",cementerioF23Cu,1,2);
		
		//Objetos Cementerio 4 Tumba Cuerpos
		
		Tumba tumbaF3C41=new Tumba("Tumbita Eterna Luz",cementerioF34Cu,1.70,1);
		Tumba tumbaF3C42=new Tumba("Tumbita Sombra Sagrada",cementerioF34Cu,1.10,0);
		Tumba tumbaF3C43=new Tumba("Tumbita Cámara del Silencio",cementerioF34Cu,1.60,1);
		Tumba tumbaF3C44=new Tumba("Tumbita Rincón de Paz",cementerioF34Cu,1,1);
		Tumba tumbaF3C45=new Tumba("Tumbita Hogar de Paz",cementerioF34Cu,1.75,2);
		Tumba tumbaF3C46=new Tumba("Tumbita Sendero de Tranquilidad",cementerioF34Cu,1,0);
		
		//Objetos Cementerio 5 Tumba Cuerpos
		
		Tumba tumbaF3C51=new Tumba("Tumbita Velo de Recuerdo",cementerioF35Cu,1.70,0);
		Tumba tumbaF3C52=new Tumba("Tumbita Cascada de Paz",cementerioF35Cu,1.10,0);
		Tumba tumbaF3C53=new Tumba("Tumbita Refugio Perpetuo",cementerioF35Cu,1.60,1);
		Tumba tumbaF3C54=new Tumba("Tumbita Sombra de Amor",cementerioF35Cu,1.40,2);
		Tumba tumbaF3C55=new Tumba("Tumbita Eterna Quietud",cementerioF35Cu,1.40,2);
		Tumba tumbaF3C56=new Tumba("Tumbita Altar de Recuerdos",cementerioF35Cu,1,2);
	
		//Objetos Cementerio 6 Tumba Cuerpos
		
		Tumba tumbaF3C61=new Tumba("Tumbita Una Vida de Amor y Bondad",cementerioF36Cu,1.50,0);
		Tumba tumbaF3C62=new Tumba("Tumbita Siempre en Nuestro Corazón y Pensamiento",cementerioF36Cu,1.10,0);
		Tumba tumbaF3C63=new Tumba("Tumbita En Tu Ausencia",cementerioF36Cu,1.30,1);
		Tumba tumbaF3C64=new Tumba("Tumbita Tu Presencia es Más Fuerte",cementerioF36Cu,1.20,1);
		Tumba tumbaF3C65=new Tumba("Tumbita Una Vida Dedicada al Amor",cementerioF36Cu,1.75,2);
		Tumba tumbaF3C66=new Tumba("Tumbita Un Alma Valiente",cementerioF36Cu,1.2,2);
		
		
		
		//crematorios pertenecientes a F3 --> Funeraria 3
		Crematorio crematorioF31 = new Crematorio ("Crematorio del Horizonte",100,null,"oro", null,funeraria3); 
		Crematorio crematorioF32 = new Crematorio ("Cenizas de la Eternidad	",78,null,"oro", null,funeraria3); 
				
		Crematorio crematorioF33 = new Crematorio ("Refugio de la Luz",78,null,"plata", null,funeraria3); 
		Crematorio crematorioF34 = new Crematorio ("Fuego de Serenidad",78,null,"plata", null,funeraria3);
				
		Crematorio crematorioF35 = new Crematorio ("Crematorio del Horizonte Eterno",78,null,"bronce", null,funeraria3); 
		Crematorio crematorioF36 = new Crematorio ("Crematorio Luz de la Eternidad",78,null,"bronce", null,funeraria3);	
		
		//Clientes F3 - Mayores de edad
		
		Cliente clienteF31 = new Cliente("Ana Torres",3111,30,null,"oro",familiarA);
		Cliente clienteF32 = new Cliente("Beatriz Sánchez",3112,25,null,"oro",familiarA);
										
		Cliente clienteF33 = new Cliente("Alex Cruz",3113,90,null,"plata",familiarC);
		Cliente clienteF34 = new Cliente("Dani Morales",3114,57,null,"plata",familiarC);
										
		Cliente clienteF35 = new Cliente("Lucía González",3115,50,null, "bronce",familiarB);
		Cliente clienteF36 = new Cliente("Jordan Silva",3115,30,null, "bronce",familiarA);
								
								
		//Clientes F3 - Menores de edad
		Cliente clienteF37 = new Cliente("Fernanda Salazar",5,"oro",familiarB);
		Cliente clienteF38 = new Cliente("Carmen Vega",17,"oro",familiarB);
										
		Cliente clienteF39 = new Cliente("Kim Hernández",15,"bronce",familiarB);
		Cliente clienteF310 = new Cliente("Morgan López",13,"bronce",familiarB);
		
		
		//AgregarClientes
		funeraria3.agregarCliente(clienteF31);
		funeraria3.agregarCliente(clienteF32);
		funeraria3.agregarCliente(clienteF33);
		funeraria3.agregarCliente(clienteF34);
		funeraria3.agregarCliente(clienteF35);
		funeraria3.agregarCliente(clienteF36);
		funeraria3.agregarCliente(clienteF37);
		funeraria3.agregarCliente(clienteF38);
		funeraria3.agregarCliente(clienteF39);
		funeraria3.agregarCliente(clienteF310);
		
		
		Vehiculo veh1= new Vehiculo(TipoVehiculo.BERLINA,funeraria1,"azul", "2345",1);
		Vehiculo veh2= new Vehiculo(TipoVehiculo.BUS,funeraria1,"rojo", "2345",2);
		Vehiculo veh3= new Vehiculo(TipoVehiculo.CARROZA,funeraria1,"verde", "2345",3);
		
		Vehiculo veh4= new Vehiculo(TipoVehiculo.FAETON,funeraria1,"amarillo", "2345",3);
		Vehiculo veh5= new Vehiculo(TipoVehiculo.BUS,funeraria1,"amarillo", "2345",3);
		Vehiculo veh6= new Vehiculo(TipoVehiculo.COCHERESPETO,funeraria1,"rojo", "2345",3);
		
		Vehiculo veh7= new Vehiculo(TipoVehiculo.CUPE,funeraria1,"rosado", "2345",3);
		Vehiculo veh8= new Vehiculo(TipoVehiculo.BERLINA,funeraria1,"azul", "2345",3);
		Vehiculo veh9= new Vehiculo(TipoVehiculo.CARROZA,funeraria1,"azul", "2345",3);
		
		//Agregar vehiculos en  funeraria 2
		funeraria2.agregarVehiculo(veh1);
		funeraria2.agregarVehiculo(veh2);
		funeraria2.agregarVehiculo(veh3);
		funeraria2.agregarVehiculo(veh4);
		funeraria2.agregarVehiculo(veh5);
		funeraria2.agregarVehiculo(veh6);
		funeraria2.agregarVehiculo(veh7);
		
		//Agregar vehiculos en  funeraria 3
		funeraria3.agregarVehiculo(veh1);
		funeraria3.agregarVehiculo(veh2);
		funeraria3.agregarVehiculo(veh3);
		funeraria3.agregarVehiculo(veh9);
		funeraria3.agregarVehiculo(veh2);
		funeraria3.agregarVehiculo(veh8);
		funeraria3.agregarVehiculo(veh7);		
		
		
		
		
		
		
		//Funcionalidad Exhumacion
		
		boolean validacion=true;
		Scanner scanner = new Scanner(System.in);
		
		
		while(validacion) {
			
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
				validacion=false;
				break;
			}
			
			
		}
		
		
		
		
		
	}
	

	

}
