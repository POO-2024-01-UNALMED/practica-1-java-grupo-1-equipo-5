package uiMain;

import java.util.Scanner;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.financiero.Factura;
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
	
	//______________________________________________________________________________________________________________________________________
	// funcion gestion inventario
	
		public static void FuncionalidadGestionInventario(Funeraria[] fun){
	        Funeraria[] funerarias = fun;
	        Scanner scanner = new Scanner(System.in);

	        // Paso 1: Seleccionar y mostrar información de la funeraria
	        Funeraria funerariaSeleccionada = seleccionarFuneraria(funerarias, scanner);
	        imprimirInformacion(funerariaSeleccionada);

	        // Paso 2: Realizar intercambio entre funerarias si es posible
	        if (analizarIntercambios(funerarias, funerariaSeleccionada, scanner)) {
	            // Si el intercambio es posible, procede con la asignación de recursos
	            asignarRecursos(funerariaSeleccionada, scanner);
	        }

	        // Paso 3: Comprar productos faltantes o contratar empleados
	        realizarCompras(funerariaSeleccionada, scanner);

	        // Paso 4: Realizar encuesta 
	        realizarEncuesta(funerariaSeleccionada, scanner);

	        System.out.println("Proceso completado.");
		}
		
		
		private static Funeraria seleccionarFuneraria(Funeraria[] funerarias, Scanner scanner) {
	        while (true) {
	            System.out.println("Seleccione una funeraria:");
	            for (int i = 0; i < funerarias.length; i++) {
	                System.out.println((i + 1) + ". " + funerarias[i].getNombre());
	                System.out.println("   ____");
	                System.out.println("  /    \\");
	                System.out.println(" /______\\");
	                System.out.println(" |      |  " + funerarias[i].getNombre());
	                System.out.println(" |______|");
	                System.out.println();
	            }
	            System.out.println((funerarias.length + 1) + ". Cancelar");

	            int seleccion = scanner.nextInt() - 1;
	            if (seleccion >= 0 && seleccion < funerarias.length) {
	                return funerarias[seleccion];
	            } else if (seleccion == funerarias.length) {
	                System.out.println("Proceso cancelado.");
	                System.exit(0);
	            } else {
	                System.out.println("Selección inválida. Intente de nuevo.");
	            }
	        }
	    }

		private static void imprimirInformacion(Funeraria funeraria) {
		    System.out.println("Nombre: " + funeraria.getNombre());
		    System.out.println("Calificación: " + funeraria.getCalificacion());
		    System.out.println("Cantidad de empleados: " + funeraria.getEmpleados().size());

		    Producto[] productosVendidos = Funeraria.calcularProductosVendidos(funeraria);

		    System.out.println("Productos más vendidos:");
		    Producto masVendido = null;
		    int maxVendidas = 0;
		    for (Producto producto : productosVendidos) {
		        System.out.println("- " + producto.getNombre() + ": " + producto.getCantidadVendida() + " vendidas, " );
		        if (producto.getCantidadVendida() > maxVendidas) {
		            masVendido = producto;
		            maxVendidas = producto.getCantidadVendida();
		        }
		    }
		    if (masVendido != null) {
		        System.out.println("El producto más vendido es " + masVendido.getNombre() + " con " + masVendido.getCantidadVendida() + " unidades vendidas.");
		    }

		    System.out.println("------------------------");
		}


		private static boolean analizarIntercambios(Funeraria[] funerarias, Funeraria funerariaSeleccionada, Scanner scanner) {
		    scanner.nextLine(); 
		    
		    while (true) { // Bucle para asegurar que el usuario proporcione una respuesta válida
		        System.out.println("¿Desea realizar un intercambio de productos entre funerarias? (sí/no):");
		        String respuesta = scanner.nextLine().trim().toLowerCase();

		        if (respuesta.equals("sí")) {
		            // Encuentra la funeraria con mayor stock de productos
		            Funeraria funerariaConMayorStock = null;
		            Producto productoConMayorStock = null;
		            
		            for (Funeraria f : funerarias) {
		                if (f != funerariaSeleccionada) {
		                    for (Producto p : f.getProductos()) {
		                        if (productoConMayorStock == null || p.getCantidad() > productoConMayorStock.getCantidad()) {
		                            productoConMayorStock = p;
		                            funerariaConMayorStock = f;
		                        }
		                    }
		                }
		            }

		            if (funerariaConMayorStock != null && productoConMayorStock != null) {
		                // Intercambia el producto con mayor stock entre las funerarias
		                Producto productoIntercambiado = productoConMayorStock;

		                funerariaConMayorStock.getProductos().remove(productoIntercambiado);
		                funerariaSeleccionada.getProductos().add(productoIntercambiado);

		                System.out.println("Se ha realizado un intercambio:");
		                System.out.println("Producto intercambiado: " + productoIntercambiado.getNombre());
		                System.out.println("De la funeraria: " + funerariaConMayorStock.getNombre());
		                System.out.println("A la funeraria: " + funerariaSeleccionada.getNombre());

		                return true; // Intercambio realizado
		            } else {
		                System.out.println("No se encontraron productos para intercambiar.");
		                return false; // No se realizó el intercambio
		            }
		        } else if (respuesta.equals("no")) {
		            System.out.println("No se realizará ningún intercambio.");
		            return false; 
		        } else {
		            System.out.println("Respuesta no válida. Por favor, responda 'sí' o 'no'.");
		        }
		    }
		}

		private static void realizarIntercambio(Funeraria funA, Funeraria funB, Producto productoA, Producto productoB, Scanner scanner) {
		    // Seleccionar empleados
		    System.out.println("Seleccione hasta dos empleados para realizar el intercambio:");
		    List<Empleado> empleadosSeleccionados = seleccionarEmpleados(funA, scanner);

		    // Seleccionar un vehículo
		    System.out.println("Seleccione un vehículo para realizar el intercambio:");
		    List<Vehiculo> vehiculosSeleccionados = seleccionarVehiculos(funA, scanner);

		    // Mostrar productos y realizar intercambio
		    System.out.println("Seleccione la cantidad de " + productoA.getNombre() + " para intercambiar:");
		    int cantidadIntercambio = seleccionarCantidadProducto(productoA, scanner);

		    realizarIntercambioProductos(funA, funB, productoA, productoB, cantidadIntercambio, empleadosSeleccionados, vehiculosSeleccionados);
		}

		private static List<Empleado> seleccionarEmpleados(Funeraria funeraria, Scanner scanner) {
		    List<Empleado> empleadosSeleccionados = new ArrayList<>();
		    int maxEmpleados = 1;
		    
		    for (Empleado empleado : funeraria.getEmpleados()) {
		        System.out.println(empleado.getNombre() + " (" + empleado.getCargo() + ") - Jornada: " + empleado.getJornada());
		        System.out.println("    O ");
		        System.out.println("   /|\\");
		        System.out.println("   / \\");
		        System.out.println("¿Seleccionar este empleado? (S/N)");
		        
		        String respuesta = scanner.next();
		        if (respuesta.equalsIgnoreCase("S")) {
		            if (empleadosSeleccionados.size() < maxEmpleados) {
		                empleadosSeleccionados.add(empleado);
		            } else {
		                System.out.println("Ya ha seleccionado el máximo de empleados permitidos.");
		                break;
		            }
		        }
		    }

		    while (empleadosSeleccionados.size() < maxEmpleados) {
		        System.out.println("Debe seleccionar exactamente " + maxEmpleados + " empleados. Seleccione más empleados:");
		        for (Empleado empleado : funeraria.getEmpleados()) {
		            if (!empleadosSeleccionados.contains(empleado)) {
		                System.out.println(empleado.getNombre() + " (" + empleado.getCargo() + ") - Jornada: " + empleado.getJornada());
		                System.out.println("    O ");
		                System.out.println("   /|\\");
		                System.out.println("   / \\");
		                System.out.println("¿Seleccionar este empleado? (S/N)");
		                String respuesta = scanner.next();
		                if (respuesta.equalsIgnoreCase("S")) {
		                    empleadosSeleccionados.add(empleado);
		                    if (empleadosSeleccionados.size() >= maxEmpleados) break;
		                }
		            }
		        }
		    }

		    return empleadosSeleccionados;
		}

	    private static List<Vehiculo> seleccionarVehiculos(Funeraria funeraria, Scanner scanner) {
	        List<Vehiculo> vehiculosSeleccionados = new ArrayList<>();
	        int maxVehiculos = 1;
	        
	        for (Vehiculo vehiculo : funeraria.getVehiculos()) {
	            System.out.println(vehiculo.getTipoVehiculo() + " - Capacidad: " + vehiculo.getCapacidad());

	            
	            if (vehiculo.getConductor() != null) {
	                System.out.println("Conductor: " + vehiculo.getConductor().getNombre());
	            } else {
	            }
	            
	            System.out.println("   ______");
	            System.out.println("  /|_||_\\.__");
	            System.out.println(" (   _    _ _\\");
	            System.out.println(" =-(_)--(_)-'");
	            System.out.println("¿Seleccionar este vehículo? (S/N)");
	            
	            String respuesta = scanner.next();
	            if (respuesta.equalsIgnoreCase("S")) {
	                if (vehiculosSeleccionados.size() < maxVehiculos) {
	                    vehiculosSeleccionados.add(vehiculo);
	                } else {
	                    System.out.println("Ya ha seleccionado el máximo de vehículos permitidos.");
	                    break;
	                }
	            }
	        }
	        
	        return vehiculosSeleccionados;
	    }

	    private static int seleccionarCantidadProducto(Producto producto, Scanner scanner) {
	        System.out.println("Ingrese la cantidad de " + producto.getNombre() + " para intercambiar:");
	        return scanner.nextInt();
	    }

	    private static void realizarIntercambioProductos(Funeraria funA, Funeraria funB, Producto productoA, Producto productoB, int cantidad, List<Empleado> empleados, List<Vehiculo> vehiculos) {
	        Scanner scanner = new Scanner(System.in);

	        // Paso 1: Seleccionar productos a intercambiar
	        System.out.println("Seleccione los productos que desea intercambiar:");

	        
	        List<Producto> productosASeleccionados = new ArrayList<>();
	        for (Producto producto : funA.getProductos()) {
	            if (producto.getCantidad() > 0) {
	                System.out.println("Producto: " + producto.getNombre() + ", Cantidad disponible: " + producto.getCantidad());
	                System.out.println("¿Desea intercambiar este producto? (S/N)");
	                String respuesta = scanner.next();
	                if (respuesta.equalsIgnoreCase("S")) {
	                    System.out.println("Ingrese la cantidad a intercambiar:");
	                    int cantidadAIntercambiar = scanner.nextInt();
	                    if (cantidadAIntercambiar > 0 && cantidadAIntercambiar <= producto.getCantidad()) {
	                        Producto productoSeleccionado = new Producto(producto.getNombre(), producto.getPrecio(), cantidadAIntercambiar);
	                        productosASeleccionados.add(productoSeleccionado);
	                        producto.setCantidad(producto.getCantidad() - cantidadAIntercambiar);
	                    } else {
	                        System.out.println("Cantidad inválida. El intercambio no se realizará.");
	                    }
	                }
	            }
	        }

	        

	        // Paso 2: Seleccionar empleados
	        System.out.println("Seleccione hasta dos empleados para realizar el intercambio:");
	        List<Empleado> empleadosSeleccionados = seleccionarEmpleados(funA, scanner);

	        // Paso 3: Seleccionar vehículos
	        System.out.println("Seleccione un vehículo para realizar el intercambio:");
	        List<Vehiculo> vehiculosSeleccionados = seleccionarVehiculos(funA, scanner);

	        // Paso 4: Mostrar resumen de intercambio
	        System.out.println("Resumen del intercambio:");
	        System.out.println("Productos de " + funA.getNombre() + " a intercambiar:");
	        for (Producto producto : productosASeleccionados) {
	            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidad() + " unidades");
	        }


	        System.out.println("Empleados seleccionados:");
	        for (Empleado empleado : empleadosSeleccionados) {
	            System.out.println("- " + empleado.getNombre() + " (" + empleado.getCargo() + ")");
	        }

	        System.out.println("Vehículos seleccionados:");
	        for (Vehiculo vehiculo : vehiculosSeleccionados) {
	            System.out.println("- " + vehiculo.getTipoVehiculo());
	            if (vehiculo.getConductor() != null) {
	                System.out.println("  Conductor: " + vehiculo.getConductor().getNombre());
	            } else {
	                System.out.println("  Conductor: No asignado");
	            }
	        }

	        // Confirmar el intercambio
	        System.out.println("¿Desea confirmar el intercambio? (S/N)");
	        String confirmacion = scanner.next();
	        if (confirmacion.equalsIgnoreCase("S")) {
	            // Realizar el intercambio de productos
	            for (Producto producto : productosASeleccionados) {
	                funB.agregarProducto(producto);
	            }

	            // Asignar los empleados y vehículos a las funerarias correspondientes
	            for (Empleado empleado : empleadosSeleccionados) {
	                funA.agregarEmpleado(empleado);  
	            }
	            for (Vehiculo vehiculo : vehiculosSeleccionados) {
	                funA.agregarVehiculo(vehiculo);
	                  
	            }

	            System.out.println("Intercambio realizado exitosamente.");
	        } else {
	            System.out.println("Intercambio cancelado.");
	        }
	    }

	    private static void realizarCompras(Funeraria funeraria, Scanner scanner) {
	        Producto[] productosFaltantes = Funeraria.identificarProductosFaltantes(funeraria);
	        if (productosFaltantes.length > 0) {
	            System.out.println("Productos con menos de 10 existencias:");
	            for (Producto producto : productosFaltantes) {
	                System.out.println("- " + producto.getNombre() + ": " + producto.getCantidad());
	            }

	            // Comprar productos, contratar empleados, o comprar vehículos
	            System.out.println("Seleccione una opción:");
	            System.out.println("1. Comprar productos");
	            System.out.println("2. Contratar empleados");
	            System.out.println("3. Comprar vehículos");
	            System.out.println("4. Cancelar");

	            int opcion = scanner.nextInt();
	            switch (opcion) {
	                case 1:
	                    comprarProductos(funeraria, productosFaltantes, scanner);
	                    break;
	                case 2:
	                    contratarEmpleados(funeraria, scanner);
	                    break;
	                case 3:
	                    comprarVehiculos(funeraria, scanner);
	                    break;
	                case 4:
	                    System.out.println("Operación cancelada.");
	                    return;
	                default:
	                    System.out.println("Opción inválida.");
	                    realizarCompras(funeraria, scanner);
	            }
	        } else {
	            System.out.println("No hay productos faltantes.");
	        }
	    }

	    private static void comprarProductos(Funeraria funeraria, Producto[] productosFaltantes, Scanner scanner) {
	        System.out.println("Establecimientos que pueden vender los productos faltantes:");
	        for (int i = 0; i < funeraria.getListadoProveedores().size(); i++) {
	            Establecimiento est = funeraria.getListadoProveedores().get(i);
	            System.out.println((i + 1) + ". " + est.getNombre() + " (Calificación: " + est.getCalificacion() + ")");
	        }

	        // Seleccionar establecimiento
	        System.out.println("Seleccione el establecimiento para realizar la compra:");
	        int seleccionEstablecimiento = scanner.nextInt() - 1;

	        if (seleccionEstablecimiento >= 0 && seleccionEstablecimiento < funeraria.getListadoProveedores().size()) {
	            Establecimiento proveedorSeleccionado = funeraria.getListadoProveedores().get(seleccionEstablecimiento);
	            List<Producto> productosComprados = new ArrayList<>();

	            // Mostrar productos faltantes y realizar la compra
	            for (Producto productoFaltante : productosFaltantes) {
	                if (proveedorSeleccionado.tieneProducto(productoFaltante.getNombre())) {
	                    System.out.println("¿Desea comprar " + productoFaltante.getNombre() + "? (S/N)");
	                    String respuesta = scanner.next();

	                    if (respuesta.equalsIgnoreCase("S")) {
	                        System.out.println("Ingrese la cantidad a comprar:");
	                        int cantidadCompra = scanner.nextInt();

	                        if (cantidadCompra > 0) {
	                            // Actualizar inventario
	                            productoFaltante.setCantidad(productoFaltante.getCantidad() + cantidadCompra);
	                            productosComprados.add(new Producto(productoFaltante.getNombre(), cantidadCompra));
	                            System.out.println("Compra realizada exitosamente.");
	                        } else {
	                            System.out.println("Cantidad inválida. La compra no se realizará.");
	                        }
	                    }
	                }
	            }

	            // Mostrar resumen de la compra
	            if (!productosComprados.isEmpty()) {
	                System.out.println("Resumen de la compra de productos:");
	                for (Producto producto : productosComprados) {
	                    System.out.println("  Producto: " + producto.getNombre() + ", Cantidad comprada: " + producto.getCantidad());
	                }
	            } else {
	                System.out.println("No se realizaron compras.");
	            }

	        } else {
	            System.out.println("Selección de establecimiento inválida.");
	        }
	    }

	    private static void contratarEmpleados(Funeraria funeraria, Scanner scanner) {
	        System.out.println("Establecimientos disponibles para contratación de empleados:");

	        List<Establecimiento> establecimientos = funeraria.getListadoProveedoresEmpleados();
	        for (int i = 0; i < establecimientos.size(); i++) {
	            System.out.println((i + 1) + ". " + establecimientos.get(i).getNombre() + " (Calificación: " + establecimientos.get(i).getCalificacion() + ")");
	        }

	        // Seleccionar establecimiento
	        System.out.println("Seleccione el establecimiento para ver sus empleados:");
	        int seleccionEstablecimiento = scanner.nextInt() - 1;

	        if (seleccionEstablecimiento >= 0 && seleccionEstablecimiento < establecimientos.size()) {
	            Establecimiento estSeleccionado = establecimientos.get(seleccionEstablecimiento);
	            List<Empleado> empleadosContratados = new ArrayList<>();

	            // Mostrar empleados disponibles para contratación
	            System.out.println("Empleados disponibles en " + estSeleccionado.getNombre() + ":");
	            for (Empleado empleado : estSeleccionado.getEmpleados()) {
	                System.out.println("  Empleado: " + empleado.getNombre() + ", Cargo: " + empleado.getCargo() + ", Experiencia: " + empleado.getExperiencia() + " años, Edad: " + empleado.getEdad());
	                System.out.println("¿Desea contratar este empleado? (S/N)");
	                String respuesta = scanner.next();
	                if (respuesta.equalsIgnoreCase("S")) {
	                    funeraria.agregarEmpleado(empleado);
	                    empleadosContratados.add(empleado);
	                    System.out.println("Empleado " + empleado.getNombre() + " contratado exitosamente.");
	                }
	            }

	            // Mostrar resumen de la contratación
	            if (!empleadosContratados.isEmpty()) {
	                System.out.println("Resumen de la contratación de empleados:");
	                for (Empleado empleado : empleadosContratados) {
	                    System.out.println("  Empleado: " + empleado.getNombre() + ", Cargo: " + empleado.getCargo() + ", Experiencia: " + empleado.getExperiencia() + " años, Edad: " + empleado.getEdad());
	                }
	            } else {
	                System.out.println("No se contrataron empleados.");
	            }

	        } else {
	            System.out.println("Selección inválida.");
	        }
	    }


	    private static void comprarVehiculos(Funeraria funeraria, Scanner scanner) {
	        System.out.println("Establecimientos disponibles para compra de vehículos:");

	        List<Establecimiento> establecimientos = funeraria.getListadoProveedoresVehiculos();
	        for (int i = 0; i < establecimientos.size(); i++) {
	            System.out.println((i + 1) + ". " + establecimientos.get(i).getNombre() + " (Calificación: " + establecimientos.get(i).getCalificacion() + ")");
	        }

	        // Seleccionar establecimiento
	        System.out.println("Seleccione el establecimiento para ver sus vehículos:");
	        int seleccionEstablecimiento = scanner.nextInt() - 1;

	        if (seleccionEstablecimiento >= 0 && seleccionEstablecimiento < establecimientos.size()) {
	            Establecimiento estSeleccionado = establecimientos.get(seleccionEstablecimiento);
	            List<Vehiculo> vehiculosEnVenta = new ArrayList<>(estSeleccionado.getVehiculosEnVenta()); // Hacer una copia de la lista
	            List<Vehiculo> vehiculosComprados = new ArrayList<>();

	            // Mostrar vehículos disponibles para compra
	            System.out.println("Vehículos disponibles en " + estSeleccionado.getNombre() + ":");
	            for (Vehiculo vehiculo : vehiculosEnVenta) {
	                System.out.println("  Vehículo: " + vehiculo.getTipoVehiculo() + ", Capacidad: " + vehiculo.getCapacidad() + ", Precio: " + vehiculo.getPrecio());
	                System.out.println("¿Desea comprar este vehículo? (S/N)");
	                String respuesta = scanner.next();
	                if (respuesta.equalsIgnoreCase("S")) {
	                    funeraria.agregarVehiculo(vehiculo);
	                    estSeleccionado.removerVehiculoEnVenta(vehiculo);
	                    vehiculosComprados.add(vehiculo); // Agregar al resumen
	                    System.out.println("Vehículo comprado exitosamente.");
	                }
	            }

	            // Mostrar resumen de la compra
	            if (!vehiculosComprados.isEmpty()) {
	                System.out.println("Resumen de la compra de vehículos:");
	                for (Vehiculo vehiculo : vehiculosComprados) {
	                    System.out.println("  Vehículo: " + vehiculo.getTipoVehiculo() + ", Precio: " + vehiculo.getPrecio());
	                }
	            } else {
	                System.out.println("No se realizaron compras.");
	            }
	        } else {
	            System.out.println("Selección inválida.");
	        }
	    }

	    
	    private static void realizarEncuesta(Funeraria funeraria, Scanner scanner) {
	        System.out.println("Realizando encuesta de desempeño...");

	        // Encuesta para el proceso en general
	        System.out.println("Califique el desempeño del proceso en general (1-5):");
	        int calificacion = scanner.nextInt();
	        scanner.nextLine(); 
	        System.out.println("Ingrese una descripción opcional sobre el desempeño del proceso:");
	        String descripcion = scanner.nextLine();

	        // Guardar la calificación y descripción en la funeraria
	        funeraria.setCalificacion(calificacion);
	        funeraria.setDescripcionCalificacion(descripcion);

	        System.out.println("Calificación del proceso guardada: " + calificacion);
	        if (!descripcion.isEmpty()) {
	            System.out.println("Descripción: " + descripcion);
	        }

	        System.out.println("Encuesta completada. Gracias por su retroalimentación.");
	    }

	    
	    private static void asignarRecursos(Funeraria funeraria, Scanner scanner) {
	        System.out.println("Asignación de recursos para la funeraria: " + funeraria.getNombre());

	        // Selección de empleados
	        System.out.println("Seleccione los empleados para realizar la tarea:");
	        List<Empleado> empleadosSeleccionados = seleccionarEmpleados(funeraria, scanner);

	        // Selección de vehículos
	        System.out.println("Seleccione los vehículos para realizar la tarea:");
	        List<Vehiculo> vehiculosSeleccionados = seleccionarVehiculos(funeraria, scanner);

	        // Selección de productos
	        System.out.println("Seleccione los productos a enviar:");
	        List<Producto> productosSeleccionados = new ArrayList<>();
	        for (Producto producto : funeraria.getProductos()) {
	            System.out.println(producto.getNombre() + " - Cantidad disponible: " + producto.getCantidad());
	            System.out.println("¿Cuántos desea enviar?");
	            int cantidad = scanner.nextInt();
	            if (cantidad > 0 && cantidad <= producto.getCantidad()) {
	                Producto productoSeleccionado = new Producto(producto.getNombre(), producto.getPrecio(), cantidad);
	                productosSeleccionados.add(productoSeleccionado);
	            }
	        }

	        // Mostrar resumen de recursos seleccionados
	        System.out.println("Resumen de recursos asignados:");
	        System.out.println("Empleados:");
	        for (Empleado empleado : empleadosSeleccionados) {
	            System.out.println("- " + empleado.getNombre() + " (" + empleado.getCargo() + ")");
	        }
	        System.out.println("Vehículos:");
	        for (Vehiculo vehiculo : vehiculosSeleccionados) {
	            System.out.println("- " + vehiculo.getTipoVehiculo());
	            if (vehiculo.getConductor() != null) {
	                System.out.println("  Conductor: " + vehiculo.getConductor().getNombre());
	            } else {
	                System.out.println("  Conductor: No asignado");
	            }
	        }
	        System.out.println("Productos:");
	        for (Producto producto : productosSeleccionados) {
	            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidad() + " unidades");
	        }
	    }
	
  //______________________________________________________________________________________________________________________________________
  // funcionalidad  Finanzas
    
  		public static void funcionalidadFinanzas() {
    		
    		Scanner scanner = new Scanner(System.in);
    		
    		//Filtrar funerarias 
    		ArrayList<Establecimiento> funerarias =Establecimiento.filtarEstablecimiento("funeraria");
    		
    		//Mostrar funerarias disponibles
    		System.out.println("Seleccione la funeraria correspondiente");
    		int indice=0;
    		for(Establecimiento auxFuneraria:funerarias) {
    			indice+=1;
    			System.out.println("["+indice+"]"+auxFuneraria);
    		}
    	    
    		//Seleccionar funeraria
    		System.out.print("Ingrese el índice correspondiente: ");
    		int indiceFuneraria=scanner.nextInt();
    		
    		//Vuelve a pedir indice si se sale del rango
    		while (indiceFuneraria<1 || indiceFuneraria>funerarias.size()) {
    			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    			indiceFuneraria=scanner.nextInt();
    		}
    		//Referencia a la funeraria del indice  seleccionado
    		Funeraria funeraria=(Funeraria) funerarias.get(indiceFuneraria-1);
    		
    		//Variable de condicion para el buble
    		boolean continuarY = true;
    		
    		//Bucle para volver a este punto si se desea después de terminar cualquiera de las 5 opciones siguientes
    		while(continuarY) {
    			
    		//Variable de condicion para el bucle
    		boolean valido = false;
    		int indiceHacer = 0 ;
    		
    		//Mostrar opciones disponibles con la funeraria seleccionada
    		System.out.println("Que proceso quiere hacer ");
    		System.out.println("[1] Cobro clientes");
    		System.out.println("[2] Pagar facturas ");
    		System.out.println("[3] Pago empleados");
    		System.out.println("[4] credito");
    		System.out.println("[5] reajuste de dinero");
    		
    		//Seleccion opcion
    		System.out.print("Ingrese el índice correspondiente: ");
    	    indiceHacer = scanner.nextInt();
    		
    	    //Vuelve a preguntar por indice si se salio de rango
    	    while(!valido) {
    		
    			if(indiceHacer >= 1 && indiceHacer <= 5) {
    				valido = true;
    			}else {
    				System.out.println("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceHacer = scanner.nextInt();
    			}
    		}
    	    
    		switch(indiceHacer) {
    		
    		case 1: //Cobro clientes
    			valido = true;
    			
    			//Lista con todos los cementerios de la funeraria
    			ArrayList<Establecimiento> cementerios = funeraria.cementerios();
    			indice=0;
    			
    			//Muestra los cementerios disponibles en la funeraria
    			for(Establecimiento cementerio: cementerios) {
    				indice+=1;
    				System.out.println("["+indice+"] "+ cementerio);
    			}
    			//Seleccion del cementerio
    			System.out.print("Ingrese el índice del cementerio: ");
    			int indiceCementerio = scanner.nextInt();
    			
    			//Vuelve a pedir, si el indice esta fuera de rango
    			while(indiceCementerio < 1 || indiceCementerio > cementerios.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceCementerio=scanner.nextInt();
    				
    			} 
    			//Se referenci el cementerio del indice  seleccionado
    			Cementerio cementerio = (Cementerio) cementerios.get(indiceCementerio - 1);
    			
    			//Lista con los clientes del cementerio seleccionado
    			ArrayList<Cliente> clientes = cementerio.getClientes();
    			indice = 0;
    			//Condicionador para ver si hay clientes en el cementerio
    			if(clientes.size() > 0) {
    			
    			//Recorrido por todos los clientes en el cementerio
    			for(Cliente cliente: clientes) {
    				//Condicionador para  mostrar  clientes que tengan alguna factura
    				if(cliente.getListadoFacturas().size() > 0) {
    				indice+=1;
    				System.out.println("["+indice+"] "+ cliente);}
    	
    			}}
    			//Condicionador para identificar si no hubo algun cliente con factura por pagar
    			if(indice == 0) {
    				System.out.println("No hay clientes con facturas por pagar");}
    			//Pide el indice del cliente para cobrar la o las facturas
    			else{System.out.print("Ingrese el índice de los clientes: ");
    			int indiceCliente = scanner.nextInt();
    			
    			//Vuelve a pedir indice si estuvo fuera de rango
    			while(indiceCliente<1 || indiceCliente>clientes.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceCliente=scanner.nextInt();
    			}
    			//Referencia al indice del cliente seleccionado
    			Cliente cliente = (Cliente) clientes.get(indiceCliente - 1);
    			//Cantidad de facturas del cliente
    			int cantidadFacturas = cliente.getListadoFacturas().size();
    			//Comprueba que la cantidad de factores sea mayor que 0
    			if(cantidadFacturas > 0 ) {
    			//Se llama al metodo para el cobro de servicios del cliente
    			funeraria.cobroServiciosClientes(cliente);	
    			//Se muestra por consola que el pago de facturas del cliente se concluyo de manera correcta
    			System.out.println("Cobro de  facturas del cliente: "+ cliente.getNombre()+", realizado correctamente");}
    			//Se indica que no hay facturas por cobrar
    			else { System.out.println("No hay facturas que cobrar");}}
    			break;
    			//Se pregunta si se quiere realizar otra accion con la funeraria
    		
    		case 2:  //Pago de facturas de la funeraria
    		    valido = true;
    		    boolean continuarq = true;
    		    
    		    //Buble para pedir si se quiere pagar otra factura
    			while(continuarq) {
    			
    			//Lista con todas las facturas de la funeraria
    		    ArrayList<Factura> facturas = funeraria.getListadoFacturasPorPagar();
    		    //Condicional que mira si hay alguna factura por pagar
    		    if(facturas.size() > 0) {
    		    	
    		    //Recorrido por todas las facturas de la funeraria
    		    for(int i = 0; i < facturas.size();i++) {
    		    	//Referencia a la factura que esta activa en el bucle
    		    	Factura factura = facturas.get(i);
    		    	//Se muestran las facturas disponibles con su ID
    		    	System.out.println("["+(i+1)+"]"+"Factura con ID: "+ factura.getID());}
    		    
    		    //Se pide en indice de la factura a pagar
    		    System.out.print("Ingrese el índice de las facturas: ");
    			int indiceFactura = scanner.nextInt();
    			scanner.nextLine();
    			
    			//Si el indice esta fuera de rango pide uno de nuevo
    			while(indiceFactura<1 || indiceFactura>facturas.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceFactura=scanner.nextInt();
    			}//Referencia de la factura que se selecciono
    			Factura factura1 = (Factura) facturas.get(indiceFactura - 1);
    			
    			//Se calcula el precio total de la factura con un metodo
    			factura1.totalFactura();
    			
    			//Se llama el metod cobroFacturas y se muestra su resultado
    			System.out.println(funeraria.cobroFacturas(factura1));
    			
    			//Se pregunta si se desea pagar otra Factura 
    		    System.out.println("Desea pagar otra factura? (s/n): ");
    		    String respuesta1 = scanner.next();
    		    continuarq = respuesta1.equalsIgnoreCase("s");}
    		    //Se indica que no hay facturas disponibles para pagar
    		    else{System.out.println("No hay facturas disponibles");
    		    break;}}
    		    break;
    		    //Se pregunta si se desea hacer otra accion con la funeraria
    		
    		case 3:  //Pago empleados
    			valido = true;
    			ArrayList <Empleado> empleados = funeraria.getEmpleados();
    			ArrayList <Empleado> empleadosDispo = new ArrayList<Empleado>();
    			boolean hayEmpleadosDisponibles = false;
    			int indice4 = 0;
    			for(int i = 0; i < empleados.size();i++) {
    				Empleado empleado = empleados.get(i);
    				if(empleado.getTrabajosHechos() > 0) {
    					indice4++;
    					empleadosDispo.add(empleado);
    				System.out.println("["+indice4+"] "+ empleados.get(i).getNombre());
    				hayEmpleadosDisponibles = true;
    			}}
    		    if(!hayEmpleadosDisponibles) {
    		    	System.out.println("No hay empleados disponibles a los que pagar");
    		    }else {
    			System.out.print("Ingrese el índice correspondiente: ");
    			int indiceEmpleado=scanner.nextInt();
    			while (indiceEmpleado <1 || indiceEmpleado>empleados.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceEmpleado=scanner.nextInt();
    			}
    			
    			Empleado empleado=(Empleado) empleadosDispo.get(indiceEmpleado-1);
    			
    			System.out.println(funeraria.pagoTrabajadores(empleado));}
    			break;
    		case 4:
    			valido = true;
    			boolean continuar = true;
    			while(continuar) {
    			System.out.println("Que proceso quiere hacer ");
    			System.out.println("[1] Pedir credito");
    			System.out.println("[2] Pagar credito");
    			System.out.println("[3] Ver credito");
    			
    			System.out.print("Ingrese el índice correspondiente: ");
    			int indiceCredito = scanner.nextInt();
    			while (indiceCredito < 1 || indiceCredito > 3) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceCredito=scanner.nextInt();
    			}
    			ArrayList<Factura> creditos = funeraria.getCuentaCorriente().getCredito();
    				switch(indiceCredito) {		
    			case 1:
    				System.out.println(funeraria.pedirCredito());
    				break;
    			case 2:
    				if(creditos.size() > 0) {
    				    for(int i = 0; i < creditos.size();i++) {
    				    	Factura factura = creditos.get(i);
    				    	System.out.println("["+(i+1)+"]"+"Credito con ID: "+ factura.getID());}
    				    System.out.print("Ingrese el índice del credito: ");
    					int indiceCredito1 = scanner.nextInt();
    					scanner.nextLine();
    					
    					while(indiceCredito1<1 || indiceCredito1>creditos.size()) {
    						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    						indiceCredito1=scanner.nextInt();
    					}
    				System.out.println("Que porcentaje desea pagar ");
    			System.out.println("[1] 100%");
    			System.out.println("[2] 80%");
    			System.out.println("[3] 60%");
    			System.out.println("[4] 40%");
    			System.out.println("[5] 20%");
    			System.out.print("Ingrese el índice correspondiente: ");
    			int indicePorcentaje = scanner.nextInt();
    			while (indicePorcentaje <1 || indicePorcentaje>5) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indicePorcentaje=scanner.nextInt();
    			}

    			switch(indicePorcentaje) {
    			case 1:
    				System.out.println(funeraria.pagarCredito(indiceCredito1 - 1,1.0));
    				break;
    			case 2:
    				System.out.println(funeraria.pagarCredito(indiceCredito1 - 1, 0.8));
    				break;
    			case 3:
    				System.out.println(funeraria.pagarCredito(indiceCredito1 - 1,0.6));
    				break;
    			case 4:
    				System.out.println(funeraria.pagarCredito(indiceCredito1 - 1,0.4));
    				break;
    			case 5:
    				System.out.println(funeraria.pagarCredito(indiceCredito1 - 1,0.2));
    				break;
    			}}
    			break;
    			case 3:
    				if(creditos.size() > 0) {
    				    for(int i = 0; i < creditos.size();i++) {
    				    	Factura factura = creditos.get(i);
    				    	System.out.println("["+(i+1)+"]"+"Credito con ID: "+ factura.getID());}
    				    System.out.print("Ingrese el índice del credito: ");
    					int indiceCredito2 = scanner.nextInt();
    					scanner.nextLine();
    					
    					while(indiceCredito2<1 || indiceCredito2>creditos.size()) {
    						System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    						indiceCredito2=scanner.nextInt();
    					}
    				System.out.println(funeraria.getCuentaCorriente().infoCredito(indiceCredito2 - 1));
    			break;
    			
    				}}System.out.println("Desea realizar otra accion de credito? (s/n): ");
    			String respuesta = scanner.next();
    			continuar = respuesta.equalsIgnoreCase("s");
    			}
    			break;
    		case 5:
    			valido = true;
    			boolean continuarH = true;
    			while(continuarH) {
    			System.out.println("Que proceso quiere hacer ");
    			System.out.println("[1] Ver informe gastos");
    			System.out.println("[2] Reajuste");
    			
    			System.out.print("Ingrese el índice correspondiente: ");
    			int indiceReajuste = scanner.nextInt();
    			scanner.nextLine();
    			while(indiceReajuste<1 || indiceReajuste>2) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceReajuste=scanner.nextInt();
    			}
    			switch(indiceReajuste) {
    			case 1:
    				System.out.println(funeraria.informeGastosFacturas());
    				break;
    			case 2:
    			    System.out.println(funeraria.reajusteDinero());
    			break;
    			
    			}System.out.println("Desea realizar otra accion en reajuste? (s/n): ");
    			String respuesta = scanner.nextLine();
    			continuarH = respuesta.equalsIgnoreCase("s");
    		}break;
    		}System.out.println("Desea realizar otro proceso con la funeraria? (s/n): ");
    		String respuestape = scanner.next();
    		if(respuestape.equalsIgnoreCase("s")) {
    			continuarY = true;
    		}else {
    			continuarY = false;
    			break;
    		}
    	}}
    		
		
	
	
	public static void main(String[] args) {
		
		//cuentasCorriente funerarias
		CuentaBancaria cuenta1F = new CuentaBancaria(199234234, "Eterna Paz", 4000000,4000000,4000000,4000000,4000000, "BBVA");
	    CuentaBancaria cuenta2F = new CuentaBancaria(193739239, "Caminos de luz", 4000000,4000000,4000000,4000000,4000000, "BANCOLOMBIA");
	    CuentaBancaria cuenta3F = new CuentaBancaria(384627823, "Recuerdos eternos", 4000000,4000000,4000000,4000000,4000000, "DAVIVIENDA");
		    
	    //cuentaAhorro funerarias
	    CuentaBancaria cuenta4F = new CuentaBancaria(19934, "todasLasFunerarias", 3993, "BANCO_OCCIDENTE");
	   
	    
	    
	    //cuentasBancaria empleados
	    CuentaBancaria cuenta1E = new CuentaBancaria(345354, "Adrián Vargas", 39999, "BBVA");
	    CuentaBancaria cuenta2E = new CuentaBancaria(456456, "Benjamín Díaz", 17271, "BANCOLOMBIA");
	    CuentaBancaria cuenta3E = new CuentaBancaria(575675, "Cristian Herrera", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta4E = new CuentaBancaria(678676, "Diana Moreno", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta5E = new CuentaBancaria(345345, "Gabriela Arias", 37437, "BANCO_BOGOTA");
	    CuentaBancaria cuenta6E = new CuentaBancaria(234234, "David Soto", 28348, "BBVA");
	    CuentaBancaria cuenta7E = new CuentaBancaria(324322, "Esteban Cordero", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta8E = new CuentaBancaria(567567, "Federico Gil", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta9E = new CuentaBancaria(228828, "Elena Vázquez", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta10E = new CuentaBancaria(454564, "Isabela López", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta11E = new CuentaBancaria(324342, "Guillermo Romero", 39999, "BBVA");
	    CuentaBancaria cuenta12E = new CuentaBancaria(546456, "Jorge Álvarez", 17271, "BANCOLOMBIA");
	    CuentaBancaria cuenta13E = new CuentaBancaria(345345, "Florencia Pérez", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta14E = new CuentaBancaria(645646, "Jazmín Navarro", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta15E = new CuentaBancaria(345354, "Alicia Moreno", 37437, "BANCO_BOGOTA");
	    CuentaBancaria cuenta16E = new CuentaBancaria(234234, "Marco Ruiz", 28348, "BBVA");
	    CuentaBancaria cuenta17E = new CuentaBancaria(756567, "Natalia Ortega", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta18E = new CuentaBancaria(705006, "Casey Morales", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta19E = new CuentaBancaria(245355, "Karla Soto", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta20E = new CuentaBancaria(456456, "Dakota Torres", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta21E = new CuentaBancaria(567555, "Nicolás Fernández", 23424, "BBVA");
	    CuentaBancaria cuenta22E = new CuentaBancaria(345353, "Alex Rivera", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta23E = new CuentaBancaria(243234, "Elliot Cruz", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta24E = new CuentaBancaria(456456, "Camila Silva", 57567, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta25E = new CuentaBancaria(768887, "Harper Fernández", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta26E = new CuentaBancaria(345345, "Jesse Jiménez", 39999, "BBVA");
	    CuentaBancaria cuenta27E = new CuentaBancaria(678777, "Logan Mendoza", 17271, "BANCOLOMBIA");
	    CuentaBancaria cuenta28E = new CuentaBancaria(788887, "Sam Vargas", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta29E = new CuentaBancaria(232444, "Karla Soto", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta30E = new CuentaBancaria(323445, "Taylor López", 37437, "BANCO_BOGOTA");
	    CuentaBancaria cuenta31E = new CuentaBancaria(242432, "Bruno Salgado", 28348, "BBVA");
	    CuentaBancaria cuenta32E = new CuentaBancaria(534577, "Bárbara López", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta33E = new CuentaBancaria(123456, "Óscar Morales", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta34E = new CuentaBancaria(789012, "Dulce María Reyes", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta35E = new CuentaBancaria(324323, "Evelyn Rodríguez", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta36E = new CuentaBancaria(567777, "Kevin Castillo", 23424, "BBVA");
	    CuentaBancaria cuenta37E = new CuentaBancaria(343345, "Ana García", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta38E = new CuentaBancaria(567766, "Luca Rossi", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta39E = new CuentaBancaria(990987, "Ayesha Khan", 57567, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta40E = new CuentaBancaria(678980, "Jorge Martínez", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta41E = new CuentaBancaria(456777, "Sofia Petrov", 57567, "BBVA");
	    CuentaBancaria cuenta42E = new CuentaBancaria(634443, "Haruto Tanaka", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta43E = new CuentaBancaria(745553, "Elena Ivanova", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta44E = new CuentaBancaria(567567, "Amir Reza", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta45E = new CuentaBancaria(388678, "Mia Eriksson", 37437, "BANCO_BOGOTA");
	    CuentaBancaria cuenta46E = new CuentaBancaria(279899, "Dulce María Reyes", 28348, "BBVA");
	    CuentaBancaria cuenta47E = new CuentaBancaria(597866, "Nina Jovanović", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta48E = new CuentaBancaria(124454, "Kevin Castillo", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta49E = new CuentaBancaria(745666, "Eli Cohen", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta50E = new CuentaBancaria(323445, "Bárbara López", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta51E = new CuentaBancaria(567765, "Marco Bianchi", 23424, "BBVA");
	    CuentaBancaria cuenta52E = new CuentaBancaria(333000, "Zara Ahmed", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta53E = new CuentaBancaria(450345, "Evelyn Rodríguez", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta54E = new CuentaBancaria(913134, "Raj Patel", 57567, "BANCO_OCCIDENTE");
	    
	    //Cuenta Cementerios
	    CuentaBancaria cuenta1CE = new CuentaBancaria(345344, "Jardín de la Eternidad", 28348, "BBVA");
	    CuentaBancaria cuenta2CE = new CuentaBancaria(456456, "Colina de la Paz", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta3CE = new CuentaBancaria(567567, "Campos de tranquilidad", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta4CE = new CuentaBancaria(678678, "Valle del Silencio", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta5CE = new CuentaBancaria(345346, "Rincón del Descanso", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta6CE = new CuentaBancaria(454654, "Jardín de los Recuerdos", 23424, "BBVA");
	    CuentaBancaria cuenta7CE = new CuentaBancaria(456456, "Eternidad Verde", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta8CE = new CuentaBancaria(340505, "Mirador de la Serenidad", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta9CE = new CuentaBancaria(967567, "Bosque de la Memoria", 57567, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta10CE = new CuentaBancaria(656456, "Cementerio del Refugi", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta11CE = new CuentaBancaria(567567, "Paz y Esperanza", 57567, "BBVA");
	    CuentaBancaria cuenta12CE = new CuentaBancaria(349599, "Sendero de la Tranquilidad", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta13CE = new CuentaBancaria(575688, "Cementerio del Silencio", 28348, "BBVA");
	    CuentaBancaria cuenta14CE = new CuentaBancaria(343466, "Campo de la Eternidad", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta15CE = new CuentaBancaria(678678, "Bosque de la Serenidad", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta16CE = new CuentaBancaria(345644, "Jardines del Descanso", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta17CE = new CuentaBancaria(345685, "Valle de la Paz Interior", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta18CE = new CuentaBancaria(479585, "Luz del Recuerdo", 23424, "BBVA");
	    CuentaBancaria cuenta19CE = new CuentaBancaria(367894, "Colinas del Reposo", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta20CE = new CuentaBancaria(346764, "Jardín de la Eternidad", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta21CE = new CuentaBancaria(934674, "Refugio de la Memoria", 57567, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta22CE = new CuentaBancaria(678678, "Cementerio del Alba", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta23CE = new CuentaBancaria(565675, "Alameda de la Paz", 57567, "BBVA");
	    CuentaBancaria cuenta24CE = new CuentaBancaria(345345, "Jardín del Silencio Eterno", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta25CE = new CuentaBancaria(234243, "Jardín de los Ángeles", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta26CE = new CuentaBancaria(545667, "Campo de la Tranquilidad", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta27CE = new CuentaBancaria(667878, "Oasis de Paz", 37437, "BANCO_BOGOTA");
	    CuentaBancaria cuenta28CE = new CuentaBancaria(707070, "Colinas del Recuerdo", 28348, "BBVA");
	    CuentaBancaria cuenta29CE = new CuentaBancaria(124124, "Rincón del Silencio", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta30CE = new CuentaBancaria(546065, "Eterna Armonía", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta31CE = new CuentaBancaria(634666, "Bosque de los Sueños", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta32CE = new CuentaBancaria(284658, "Pradera del Descanso", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta33CE = new CuentaBancaria(284654, "Refugio de la Memoria", 23424, "BBVA");
	    CuentaBancaria cuenta34CE = new CuentaBancaria(474742, "Sendero de la Memoria", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta35CE = new CuentaBancaria(324727, "Valle del Reposo", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta36CE = new CuentaBancaria(237474, "Jardines del Refugio", 57567, "BANCO_OCCIDENTE");
	    
	    //Cuenta Crematorios
	    CuentaBancaria cuenta1CR = new CuentaBancaria(456536, "Crematorio Luz y Paz", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta2CR = new CuentaBancaria(456456, "Hogar de la Ascensión", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta3CR = new CuentaBancaria(456456, "Fuego de la Memoria", 37437, "BANCO_BOGOTA");
	    CuentaBancaria cuenta4CR = new CuentaBancaria(456456, "Crematorio Serenidad Eterna", 28348, "BBVA");
	    CuentaBancaria cuenta5CR = new CuentaBancaria(567567, "Llama de la Eternidada", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta6CR = new CuentaBancaria(456456, "Refugio del Alba", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta7CR = new CuentaBancaria(345969, "Crematorio del Silencio", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta8CR = new CuentaBancaria(560779, "Ascenso y Tranquilidad", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta9CR = new CuentaBancaria(567070, "Brasa de Paz", 23424, "BBVA");
	    CuentaBancaria cuenta10CR = new CuentaBancaria(435552, "Eterna Luz Crematorio", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta11CR = new CuentaBancaria(345664, "Crematorio del Renacer", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta12CR = new CuentaBancaria(246705, "Fuego y Serenidad", 57567, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta13CR = new CuentaBancaria(645345, "Crematorio del Horizonte", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta14CR = new CuentaBancaria(532485, "Cenizas de la Eternidad", 57567, "BBVA");
	    CuentaBancaria cuenta15CR = new CuentaBancaria(234944, "Refugio de la Luz", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta16CR = new CuentaBancaria(275674, "Fuego de Serenidad", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta17CR = new CuentaBancaria(520542, "Crematorio del Horizonte Eterno", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta18CR = new CuentaBancaria(234849, "Crematorio Luz de la Eternidad", 37437, "BANCO_BOGOTA");
	    
	    //Cuenta familiares
	    CuentaBancaria cuenta1FA = new CuentaBancaria(345653, "Saskia", 172121, "DAVIVIENDA");
	    CuentaBancaria cuenta2FA = new CuentaBancaria(456457, "Lyra", 3478411, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta3FA = new CuentaBancaria(567577, "Vesper", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta4FA = new CuentaBancaria(567577, "Ophelia", 2834811, "BBVA");
	    CuentaBancaria cuenta5FA = new CuentaBancaria(394849, "Atticus", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta6FA = new CuentaBancaria(234053, "Kaius", 2382811, "DAVIVIENDA");
	    CuentaBancaria cuenta7FA = new CuentaBancaria(323255, "Sage", 482381, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta8FA = new CuentaBancaria(235230, "Orion", 4273411, "BANCO_BOGOTA");
	    CuentaBancaria cuenta9FA = new CuentaBancaria(345345, "Mario", 23424, "BBVA");
	    CuentaBancaria cuenta10FA = new CuentaBancaria(556466, "Alberto", 3456411, "BANCOLOMBIA");
	    CuentaBancaria cuenta11FA = new CuentaBancaria(657475, "Carlos", 3646411, "DAVIVIENDA");
	    CuentaBancaria cuenta12FA = new CuentaBancaria(256756, "Samantha", 5756711, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta13FA = new CuentaBancaria(648907, "Samuel", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta14FA = new CuentaBancaria(578070, "Alma", 57567, "BBVA");
	    CuentaBancaria cuenta15FA = new CuentaBancaria(230030, "Eduardo", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta16FA = new CuentaBancaria(348500, "Armando", 172127, "DAVIVIENDA");
	    CuentaBancaria cuenta17FA = new CuentaBancaria(545396, "Catalina", 3478455, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta18FA = new CuentaBancaria(223949, "Sebastian", 374375, "BANCO_BOGOTA");
	    CuentaBancaria cuenta19FA = new CuentaBancaria(104940, "Alba", 172121, "DAVIVIENDA");
	    CuentaBancaria cuenta20FA = new CuentaBancaria(456456, "Libia", 172121, "DAVIVIENDA");              
  	    CuentaBancaria cuenta21FA = new CuentaBancaria(878977, "Armando", 3478411, "BANCO_OCCIDENTE");     
  	    CuentaBancaria cuenta22FA = new CuentaBancaria(890890, "Andres", 374371, "BANCO_BOGOTA");       
  	    CuentaBancaria cuenta23FA = new CuentaBancaria(100001, "Catalina", 2834811, "BBVA");             
  	    CuentaBancaria cuenta24FA = new CuentaBancaria(100003, "Alma", 23484, "BANCOLOMBIA");        
  	    CuentaBancaria cuenta25FA = new CuentaBancaria(100004, "Mar", 2382811, "DAVIVIENDA");         
  	    CuentaBancaria cuenta26FA = new CuentaBancaria(322222, "Eduardo", 482381, "BANCO_OCCIDENTE");      
  	    CuentaBancaria cuenta27FA = new CuentaBancaria(100043, "Carmen", 4273411, "BANCO_BOGOTA");       
  	    CuentaBancaria cuenta28FA = new CuentaBancaria(123131, "Catalina", 23424, "BBVA");                 
  	    CuentaBancaria cuenta29FA = new CuentaBancaria(111192, "Carlos", 3456411, "BANCOLOMBIA");     
  	    CuentaBancaria cuenta30FA = new CuentaBancaria(333322, "Pablo", 3646411, "DAVIVIENDA");       
  	    CuentaBancaria cuenta31FA = new CuentaBancaria(234444, "Sol", 5756711, "BANCO_OCCIDENTE");
  	    CuentaBancaria cuenta32FA = new CuentaBancaria(123122, "Andres", 86786, "BANCO_BOGOTA");       
  	    CuentaBancaria cuenta33FA = new CuentaBancaria(534444, "Vanessa", 57567, "BBVA");                 
  	    CuentaBancaria cuenta34FA = new CuentaBancaria(232322, "Carlos", 86786, "BANCOLOMBIA");       
  	    CuentaBancaria cuenta35FA = new CuentaBancaria(332423, "Carla", 172127, "DAVIVIENDA");       
  	    CuentaBancaria cuenta36FA = new CuentaBancaria(345344, "Catalina", 3478455, "BANCO_OCCIDENTE");
  	    CuentaBancaria cuenta37FA = new CuentaBancaria(266667, "David", 374375, "BANCO_BOGOTA");   
  	    CuentaBancaria cuenta38FA = new CuentaBancaria(107888, "Esteban", 172121, "DAVIVIENDA");          
  	    CuentaBancaria cuenta39FA = new CuentaBancaria(122223, "Luisa", 172121, "DAVIVIENDA");              
	    CuentaBancaria cuenta40FA = new CuentaBancaria(102102, "Maria", 3478411, "BANCO_OCCIDENTE");     
	    CuentaBancaria cuenta41FA = new CuentaBancaria(234211, "Eduardo", 374371, "BANCO_BOGOTA");       
	    CuentaBancaria cuenta42FA = new CuentaBancaria(100441, "Mario", 2834811, "BBVA");             
	    CuentaBancaria cuenta43FA = new CuentaBancaria(102342, "Catalina", 23484, "BANCOLOMBIA");        
	    CuentaBancaria cuenta44FA = new CuentaBancaria(100454, "Hugo", 2382811, "DAVIVIENDA");         
	    CuentaBancaria cuenta45FA = new CuentaBancaria(322456, "Hugo", 482381, "BANCO_OCCIDENTE");      
	    CuentaBancaria cuenta46FA = new CuentaBancaria(100566, "Sol", 4273411, "BANCO_BOGOTA");       
	    CuentaBancaria cuenta47FA = new CuentaBancaria(345345, "Daniel", 23424, "BBVA"); 
	    CuentaBancaria cuenta48FA = new CuentaBancaria(455585, "Carla", 3456411, "BANCOLOMBIA");     
        CuentaBancaria cuenta49FA = new CuentaBancaria(100012, "Armando", 3646411, "DAVIVIENDA");       
        CuentaBancaria cuenta50FA = new CuentaBancaria(123131, "Vanesa", 5756711, "BANCO_OCCIDENTE");
        CuentaBancaria cuenta51FA = new CuentaBancaria(223423, "Alicia", 86786, "BANCO_BOGOTA");       
        CuentaBancaria cuenta52FA = new CuentaBancaria(556756, "Anastasio", 57567, "BBVA");                 
        CuentaBancaria cuenta53FA = new CuentaBancaria(678644, "Miranda", 86786, "BANCOLOMBIA");                   
        CuentaBancaria cuenta54FA = new CuentaBancaria(234235, "Samuel", 172127, "DAVIVIENDA");       
        CuentaBancaria cuenta55FA = new CuentaBancaria(245645, "Carla", 3478455, "BANCO_OCCIDENTE");
        CuentaBancaria cuenta56FA = new CuentaBancaria(656766, "Eduardo", 374375, "BANCO_BOGOTA");   
        CuentaBancaria cuenta57FA = new CuentaBancaria(245245, "Eduardo", 172121, "DAVIVIENDA");
        CuentaBancaria cuenta58FA = new CuentaBancaria(467867, "Alma", 172121, "DAVIVIENDA");              
  	    CuentaBancaria cuenta59FA = new CuentaBancaria(789789, "Eduardo", 3478411, "BANCO_OCCIDENTE");           	    
  	    CuentaBancaria cuenta60FA = new CuentaBancaria(455564, "Camila", 374371, "BANCO_BOGOTA");       
  	    CuentaBancaria cuenta61FA = new CuentaBancaria(100456, "Luis", 2834811, "BBVA");             
  	    CuentaBancaria cuenta62FA = new CuentaBancaria(145645, "Tomas", 23484, "BANCOLOMBIA");        
  	    CuentaBancaria cuenta63FA = new CuentaBancaria(105675, "Andres", 2382811, "DAVIVIENDA");               	    
  	    CuentaBancaria cuenta64FA = new CuentaBancaria(455645, "Vanesa", 482381, "BANCO_OCCIDENTE");      
  	    CuentaBancaria cuenta65FA = new CuentaBancaria(123423, "Carlos", 4273411, "BANCO_BOGOTA");       
  	    CuentaBancaria cuenta66FA = new CuentaBancaria(145655, "Juan Jose", 23424, "BBVA");                 
  	    CuentaBancaria cuenta67FA = new CuentaBancaria(654444, "Nicolas", 3456411, "BANCOLOMBIA");      	    
  	    CuentaBancaria cuenta68FA = new CuentaBancaria(344553, "Mateo", 3646411, "DAVIVIENDA");       
  	    CuentaBancaria cuenta69FA = new CuentaBancaria(456566, "Mariana", 5756711, "BANCO_OCCIDENTE");
  	    CuentaBancaria cuenta70FA = new CuentaBancaria(777566, "Esteban", 86786, "BANCO_BOGOTA");       
  	    CuentaBancaria cuenta71FA = new CuentaBancaria(545555, "David", 57567, "BBVA");                       	    
  	    CuentaBancaria cuenta72FA = new CuentaBancaria(567777, "Jireh", 86786, "BANCOLOMBIA");       
  	    CuentaBancaria cuenta73FA = new CuentaBancaria(456444, "Carlos", 172127, "DAVIVIENDA");       
  	    CuentaBancaria cuenta74FA = new CuentaBancaria(678677, "Carla", 3478455, "BANCO_OCCIDENTE");
  	    CuentaBancaria cuenta75FA = new CuentaBancaria(767888, "Carolina", 374375, "BANCO_BOGOTA");   
  	    CuentaBancaria cuenta76FA = new CuentaBancaria(887665, "Carla", 172121, "DAVIVIENDA");          
  	    CuentaBancaria cuenta77FA = new CuentaBancaria(345550, "Luis", 172121, "DAVIVIENDA");              
	    CuentaBancaria cuenta78FA = new CuentaBancaria(123423, "Tomas", 3478411, "BANCO_OCCIDENTE");     
	    CuentaBancaria cuenta79FA = new CuentaBancaria(456455, "Andres", 374371, "BANCO_BOGOTA");       
	    CuentaBancaria cuenta80FA = new CuentaBancaria(565777, "Rodrigo", 2834811, "BBVA");             
	    CuentaBancaria cuenta81FA = new CuentaBancaria(123233, "Carlos", 23484, "BANCOLOMBIA");  
	    CuentaBancaria cuenta82FA = new CuentaBancaria(145677, "Juan Jose", 2382811, "DAVIVIENDA");         
	    CuentaBancaria cuenta83FA = new CuentaBancaria(567576, "Nicolas", 482381, "BANCO_OCCIDENTE");      
	    CuentaBancaria cuenta84FA = new CuentaBancaria(177765, "Jimena", 4273411, "BANCO_BOGOTA");       
	    CuentaBancaria cuenta85FA = new CuentaBancaria(334555, "Maria Jose", 23424, "BBVA");                 
	    CuentaBancaria cuenta86FA = new CuentaBancaria(342333, "Esteban", 3456411, "BANCOLOMBIA");     
        CuentaBancaria cuenta87FA = new CuentaBancaria(222220, "David", 3646411, "DAVIVIENDA");       
        CuentaBancaria cuenta88FA = new CuentaBancaria(200002, "David", 5756711, "BANCO_OCCIDENTE");
        CuentaBancaria cuenta89FA = new CuentaBancaria(300004, "Carlos", 86786, "BANCO_BOGOTA");       
        CuentaBancaria cuenta90FA = new CuentaBancaria(400004, "Armando", 57567, "BBVA");                 
        CuentaBancaria cuenta91FA = new CuentaBancaria(500004, "Carolina", 86786, "BANCOLOMBIA");  
	    
	    
	    //Cuenta clientes
	    CuentaBancaria cuenta1CL = new CuentaBancaria(373273, "Alejandro Rodríguez", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta2CL = new CuentaBancaria(543674, "Diego Martínez", 2834811, "BBVA");
	    CuentaBancaria cuenta3CL = new CuentaBancaria(454577, "Carlos Fernández", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta4CL = new CuentaBancaria(267586, "María González", 2382811, "DAVIVIENDA");
	    CuentaBancaria cuenta5CL = new CuentaBancaria(368786, "Laura Fernández", 482381, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta6CL = new CuentaBancaria(278908, "Isabel Rodríguez", 4273411, "BANCO_BOGOTA");
	    CuentaBancaria cuenta7CL = new CuentaBancaria(334506, "Valeria Sánchez", 23424, "BBVA");
	    CuentaBancaria cuenta8CL = new CuentaBancaria(234949, "Patricia Morales", 3456411, "BANCOLOMBIA");
	    CuentaBancaria cuenta9CL = new CuentaBancaria(234949, "Gabriela García", 3646411, "DAVIVIENDA");
	    CuentaBancaria cuenta10CL = new CuentaBancaria(245747, "Andrés Vargas", 5756711, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta11CL = new CuentaBancaria(632355, "Sergio Pérez", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta12CL = new CuentaBancaria(539494, "Luis García", 57567, "BBVA");
	    CuentaBancaria cuenta13CL = new CuentaBancaria(234959, "Ana Torres", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta14CL= new CuentaBancaria(399595, "Beatriz Sánchez", 172127, "DAVIVIENDA");
	    CuentaBancaria cuenta15CL = new CuentaBancaria(645069, "Alex Cruz", 3478455, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta16CL = new CuentaBancaria(456969, "Dani Morales", 374375, "BANCO_BOGOTA");
	    CuentaBancaria cuenta17CL = new CuentaBancaria(448494, "Lucía González", 172121, "DAVIVIENDA");
	    CuentaBancaria cuenta18CL = new CuentaBancaria(345959, "Jordan Silva", 3478411, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta19CL = new CuentaBancaria(234243, "Juan Pérez", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta20CL = new CuentaBancaria(456457, "Carlos Fernández", 2834811, "BBVA");
	    CuentaBancaria cuenta21CL = new CuentaBancaria(340448, "Miguel Rodríguez", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta22CL = new CuentaBancaria(345959, "Dani Morales", 2382811, "DAVIVIENDA");
	    CuentaBancaria cuenta23CL = new CuentaBancaria(234949, "Pedro González", 482381, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta24CL = new CuentaBancaria(345950, "José Martínez", 4273411, "BANCO_BOGOTA");
	    CuentaBancaria cuenta25CL = new CuentaBancaria(359505, "Laura Morales", 23424, "BBVA");
	    CuentaBancaria cuenta26CL = new CuentaBancaria(234902, "Robert Jones", 3456411, "BANCOLOMBIA");
	    CuentaBancaria cuenta27CL = new CuentaBancaria(128318, "Olivia Miller", 3646411, "DAVIVIENDA");
	    CuentaBancaria cuenta28CL = new CuentaBancaria(221291, "Sophia Moore", 5756711, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta29CL = new CuentaBancaria(610124, "Ezequiel Andrade", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta30CL = new CuentaBancaria(139219, "Damián Vargas", 57567, "BBVA");
	    CuentaBancaria cuenta31CL = new CuentaBancaria(212499, "Octavio Salazar", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta32CL= new CuentaBancaria(124494, "Leonardo Paredes", 172127, "DAVIVIENDA");
	    CuentaBancaria cuenta33CL = new CuentaBancaria(614922, "Ulises Ortega", 3478455, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta34CL = new CuentaBancaria(231212, "Valeria Castro", 374375, "BANCO_BOGOTA");
	    CuentaBancaria cuenta35CL = new CuentaBancaria(434142, "Leo Cruz", 172121, "DAVIVIENDA");
	    CuentaBancaria cuenta36CL = new CuentaBancaria(335255, "Luna Martínez", 3478411, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta37CL = new CuentaBancaria(234090, "Lucas Moreno", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta38CL = new CuentaBancaria(232342, "Sofía Rodríguez", 2834811, "BBVA");
	    CuentaBancaria cuenta39CL = new CuentaBancaria(334554, "Juan Pérez", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta40CL = new CuentaBancaria(456969, "Carlos Fernández", 2834811, "BBVA");
	    CuentaBancaria cuenta41CL = new CuentaBancaria(404040, "Miguel Rodríguez", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta42CL = new CuentaBancaria(265466, "Dani Morales", 2382811, "DAVIVIENDA");
	    CuentaBancaria cuenta43CL = new CuentaBancaria(358596, "Pedro González", 482381, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta44CL = new CuentaBancaria(250950, "José Martínez", 4273411, "BANCO_BOGOTA");
	    CuentaBancaria cuenta45CL = new CuentaBancaria(349494, "Laura Morales", 23424, "BBVA");
	    CuentaBancaria cuenta46CL = new CuentaBancaria(358585, "Robert Jones", 3456411, "BANCOLOMBIA");
	    CuentaBancaria cuenta47CL = new CuentaBancaria(345355, "Olivia Miller", 3646411, "DAVIVIENDA");
	    CuentaBancaria cuenta48CL = new CuentaBancaria(234535, "Sophia Moore", 5756711, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta49CL = new CuentaBancaria(345533, "Ezequiel Andrade", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta50CL = new CuentaBancaria(345353, "Damián Vargas", 57567, "BBVA");
	    CuentaBancaria cuenta51CL = new CuentaBancaria(456564, "Octavio Salazar", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta52CL= new CuentaBancaria(320293, "Leonardo Paredes", 172127, "DAVIVIENDA");
	    CuentaBancaria cuenta53CL = new CuentaBancaria(243040, "Ulises Ortega", 3478455, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta54CL = new CuentaBancaria(424304, "Valeria Castro", 374375, "BANCO_BOGOTA");
	    CuentaBancaria cuenta55CL = new CuentaBancaria(238494, "Leo Cruz", 172121, "DAVIVIENDA");
	    CuentaBancaria cuenta56CL = new CuentaBancaria(354953, "Luna Martínez", 3478411, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta57CL = new CuentaBancaria(349494, "Lucas Moreno", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta58CL = new CuentaBancaria(595055, "Sofía Rodríguez", 2834811, "BBVA");
	    CuentaBancaria cuenta59CL = new CuentaBancaria(204204, "Juan Pérez", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta60CL = new CuentaBancaria(405050, "Carlos Fernández", 2834811, "BBVA");
	    CuentaBancaria cuenta61CL = new CuentaBancaria(494945, "Miguel Rodríguez", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta62CL = new CuentaBancaria(585858, "Dani Morales", 2382811, "DAVIVIENDA");
	    CuentaBancaria cuenta63CL = new CuentaBancaria(234020, "Pedro González", 482381, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta64CL = new CuentaBancaria(240400, "José Martínez", 4273411, "BANCO_BOGOTA");
	    CuentaBancaria cuenta65CL = new CuentaBancaria(324323, "Laura Morales", 23424, "BBVA");
	    CuentaBancaria cuenta66CL = new CuentaBancaria(249595, "Robert Jones", 3456411, "BANCOLOMBIA");
	    CuentaBancaria cuenta67CL = new CuentaBancaria(495959, "Olivia Miller", 3646411, "DAVIVIENDA");
	    CuentaBancaria cuenta68CL = new CuentaBancaria(239404, "Sophia Moore", 5756711, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta69CL = new CuentaBancaria(494944, "Ezequiel Andrade", 86786, "BANCO_BOGOTA");
	    CuentaBancaria cuenta70CL = new CuentaBancaria(404404, "Damián Vargas", 57567, "BBVA");
	    CuentaBancaria cuenta71CL = new CuentaBancaria(494599, "Octavio Salazar", 86786, "BANCOLOMBIA");
	    CuentaBancaria cuenta72CL= new CuentaBancaria(355050, "Leonardo Paredes", 172127, "DAVIVIENDA");
	    CuentaBancaria cuenta73CL = new CuentaBancaria(243949, "Ulises Ortega", 3478455, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta74CL = new CuentaBancaria(404043, "Valeria Castro", 374375, "BANCO_BOGOTA");
	    CuentaBancaria cuenta75CL = new CuentaBancaria(244994, "Leo Cruz", 172121, "DAVIVIENDA");
	    CuentaBancaria cuenta76CL = new CuentaBancaria(249494, "Luna Martínez", 3478411, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta77CL = new CuentaBancaria(334222, "Lucas Moreno", 374371, "BANCO_BOGOTA");
	    CuentaBancaria cuenta78CL = new CuentaBancaria(524949, "Sofía Rodríguez", 2834811, "BBVA");
	    
	    //Objetos Funeraria
		
		Funeraria funeraria1 = new Funeraria("Eterna Paz", cuenta1F,cuenta4F);
		Funeraria funeraria2 = new Funeraria("Caminos de Luz", cuenta2F,cuenta4F);
		Funeraria funeraria3 = new Funeraria("Recuerdos Eternos", cuenta3F,cuenta4F);
		
		//Empleados sepultureros
		
		Empleado empleadoF11S= new Empleado("Adrián Vargas",cuenta1E,"mañana","sepulturero",1000000,funeraria1);
		Empleado empleadoF12S= new Empleado("Benjamín Díaz",cuenta2E,"mañana","sepulturero",1000000,funeraria1);
		Empleado empleadoF13S= new Empleado("Cristian Herrera",cuenta3E,"tarde","sepulturero",1000000,funeraria1);
		Empleado empleadoF14S= new Empleado("Diana Moreno",cuenta4E,"tarde","sepulturero",1000000,funeraria1);
		Empleado empleadoF15S= new Empleado("Gabriela Arias",cuenta5E,"noche","sepulturero",1000000,funeraria1);
		
		//Empleados cremador
		
		Empleado empleadoF11C= new Empleado("David Soto",cuenta6E,"mañana","cremador",1000000,funeraria1);
		Empleado empleadoF12C= new Empleado("Esteban Cordero",cuenta7E,"mañana","cremador",1000000,funeraria1);
		Empleado empleadoF13C= new Empleado("Federico Gil",cuenta8E,"tarde","cremador",1000000,funeraria1);
		Empleado empleadoF14C= new Empleado("Elena Vázquez",cuenta9E,"noche","cremador",1000000,funeraria1);
		Empleado empleadoF15C= new Empleado("Isabela López",cuenta10E,"noche","cremador",1000000,funeraria1);
		
		//Empleados sepultureros
		
		Empleado empleadoF21S= new Empleado("Guillermo Romero",cuenta11E,"mañana","sepulturero",1000000,funeraria2);
		Empleado empleadoF22S= new Empleado("Jorge Álvarez",cuenta12E,"tarde","sepulturero",1000000,funeraria2);
		Empleado empleadoF23S= new Empleado("Florencia Pérez",cuenta13E,"tarde","sepulturero",1000000,funeraria2);
		Empleado empleadoF24S= new Empleado("Jazmín Navarro",cuenta14E,"tarde","sepulturero",1000000,funeraria2);
		Empleado empleadoF25S= new Empleado("Alicia Moreno",cuenta15E,"noche","sepulturero",1000000,funeraria2);
				
				
		//Empleados cremador
				
		Empleado empleadoF21C= new Empleado("Marco Ruiz",cuenta16E,"noche","cremador",1000000,funeraria2);
		Empleado empleadoF22C= new Empleado("Natalia Ortega",cuenta17E,"mañana","cremador",1000000,funeraria2);
		Empleado empleadoF23C= new Empleado("Casey Morales",cuenta18E,"tarde","cremador",1000000,funeraria2);
		Empleado empleadoF24C= new Empleado("Karla Soto",cuenta19E,"noche","cremador",1000000,funeraria2);
		Empleado empleadoF25C= new Empleado("Dakota Torres",cuenta20E,"noche","cremador",1000000,funeraria2);
		
		
		Empleado empleadoF31S= new Empleado("Nicolás Fernández",cuenta21E,"mañana","sepulturero",1000000,funeraria3);
		Empleado empleadoF32S= new Empleado("Alex Rivera",cuenta22E,"tarde","sepulturero",1000000,funeraria3);
		Empleado empleadoF33S= new Empleado("Elliot Cruz",cuenta23E,"tarde","sepulturero",1000000,funeraria3);
		Empleado empleadoF34S= new Empleado("Camila Silva",cuenta24E,"mañana","sepulturero",1000000,funeraria3);
		Empleado empleadoF35S= new Empleado("Harper Fernández",cuenta25E,"noche","sepulturero",1000000,funeraria3);
				
				
		//Empleados cremador
				
		Empleado empleadoF31C= new Empleado("Jesse Jiménez",cuenta26E,"noche","cremador",1000000,funeraria3);
		Empleado empleadoF32C= new Empleado("Logan Mendoza",cuenta27E,"mañana","cremador",1000000,funeraria3);
		Empleado empleadoF33C= new Empleado("Sam Vargas",cuenta28E,"tarde","cremador",1000000,funeraria3);
		Empleado empleadoF34C= new Empleado("Karla Soto",cuenta29E,"mañana","cremador",1000000,funeraria3);
		Empleado empleadoF35C= new Empleado("Taylor López",cuenta30E,"noche","cremador",1000000,funeraria3);
	
		
		//Empleados generales
		
		//Empleados conductor
		
		Empleado empleadoC1= new Empleado("Bruno Salgado",cuenta31E,"mañana","conductor",10000,funeraria1);
		Empleado empleadoC2= new Empleado("Bárbara López",cuenta32E,"mañana","conductor",10000,funeraria1);
		Empleado empleadoC3= new Empleado("Óscar Morales",cuenta33E,"tarde","conductor",10000,funeraria1);
		Empleado empleadoC4= new Empleado("Dulce María Reyes",cuenta34E,"tarde","conductor",10000,funeraria1);
		Empleado empleadoC5= new Empleado("Evelyn Rodríguez",cuenta35E,"noche","conductor",10000,funeraria1);
		Empleado empleadoC6= new Empleado("Kevin Castillo",cuenta36E,"noche","conductor",10000,funeraria1);
		
		//Empleados forense 
		
		Empleado empleadoF1= new Empleado("Ana García",cuenta37E,"mañana","forense",1000,funeraria1);
		Empleado empleadoF2= new Empleado("Luca Rossi",cuenta38E,"mañana","forense",1000,funeraria1);
		Empleado empleadoF3= new Empleado("Ayesha Khan",cuenta39E,"tarde","forense",1000,funeraria1);
		Empleado empleadoF4= new Empleado("Jorge Martínez",cuenta40E,"tarde","forense",1000,funeraria1);
		Empleado empleadoF5= new Empleado("Sofia Petrov",cuenta41E,"noche","forense",1000,funeraria1);
		Empleado empleadoF6= new Empleado("Haruto Tanaka",cuenta42E,"noche","forense",1000,funeraria1);
		
		//Empleados padre 
		
		Empleado empleadoP1= new Empleado("Elena Ivanova",cuenta43E,"mañana","padre",1000,funeraria1);
		Empleado empleadoP2= new Empleado("Amir Reza",cuenta44E,"mañana","padre",1000,funeraria1);
		Empleado empleadoP3= new Empleado("Mia Eriksson",cuenta45E,"tarde","padre",1000,funeraria1);
		Empleado empleadoP4= new Empleado("Dulce María Reyes",cuenta46E,"tarde","padre",1000,funeraria1);
		Empleado empleadoP5= new Empleado("Nina Jovanović",cuenta47E,"noche","padre",1000,funeraria1);
		Empleado empleadoP6= new Empleado("Kevin Castillo",cuenta48E,"noche","padre",1000,funeraria1);
		
		//Empleados obispo 
		
		Empleado empleadoO1= new Empleado("Eli Cohen",cuenta49E,"mañana","obispo",1000,funeraria1);
		Empleado empleadoO2= new Empleado("Bárbara López",cuenta50E,"mañana","obispo",1000,funeraria1);
		Empleado empleadoO3= new Empleado("Marco Bianchi",cuenta51E,"tarde","obispo",1000,funeraria1);
		Empleado empleadoO4= new Empleado("Zara Ahmed",cuenta52E,"tarde","obispo",1000,funeraria1);
		Empleado empleadoO5= new Empleado("Evelyn Rodríguez",cuenta53E,"noche","obispo",1000,funeraria1);
		Empleado empleadoO6= new Empleado("Raj Patel",cuenta54E,"noche","obispo",1000,funeraria1);
		
	
		
		
		//Agregar empleados a funeraria 2
		funeraria2.agregarEmpleado(empleadoC1);
		funeraria2.agregarEmpleado(empleadoC2);
		funeraria2.agregarEmpleado(empleadoC3);
		funeraria2.agregarEmpleado(empleadoC4);
		funeraria2.agregarEmpleado(empleadoC5);
		funeraria2.agregarEmpleado(empleadoC6);
		
		funeraria2.agregarEmpleado(empleadoF1);
		funeraria2.agregarEmpleado(empleadoF2);
		funeraria2.agregarEmpleado(empleadoF3);
		funeraria2.agregarEmpleado(empleadoF4);
		funeraria2.agregarEmpleado(empleadoF5);
		funeraria2.agregarEmpleado(empleadoF6);
		
		funeraria2.agregarEmpleado(empleadoP1);
		funeraria2.agregarEmpleado(empleadoP2);
		funeraria2.agregarEmpleado(empleadoP3);
		funeraria2.agregarEmpleado(empleadoP4);
		funeraria2.agregarEmpleado(empleadoP5);
		funeraria2.agregarEmpleado(empleadoP6);
		
		funeraria2.agregarEmpleado(empleadoO1);
		funeraria2.agregarEmpleado(empleadoO2);
		funeraria2.agregarEmpleado(empleadoO3);
		funeraria2.agregarEmpleado(empleadoO4);
		funeraria2.agregarEmpleado(empleadoO5);
		funeraria2.agregarEmpleado(empleadoO6);
		
		//Agregar empleados a funeraria 3
		funeraria3.agregarEmpleado(empleadoC1);
		funeraria3.agregarEmpleado(empleadoC2);
		funeraria3.agregarEmpleado(empleadoC3);
		funeraria3.agregarEmpleado(empleadoC4);
		funeraria3.agregarEmpleado(empleadoC5);
		funeraria3.agregarEmpleado(empleadoC6);
		
		funeraria3.agregarEmpleado(empleadoF1);
		funeraria3.agregarEmpleado(empleadoF2);
		funeraria3.agregarEmpleado(empleadoF3);
		funeraria3.agregarEmpleado(empleadoF4);
		funeraria3.agregarEmpleado(empleadoF5);
		funeraria3.agregarEmpleado(empleadoF6);
		
		funeraria3.agregarEmpleado(empleadoP1);
		funeraria3.agregarEmpleado(empleadoP2);
		funeraria3.agregarEmpleado(empleadoP3);
		funeraria3.agregarEmpleado(empleadoP4);
		funeraria3.agregarEmpleado(empleadoP5);
		funeraria3.agregarEmpleado(empleadoP6);
		
		funeraria3.agregarEmpleado(empleadoO1);
		funeraria3.agregarEmpleado(empleadoO2);
		funeraria3.agregarEmpleado(empleadoO3);
		funeraria3.agregarEmpleado(empleadoO4);
		funeraria3.agregarEmpleado(empleadoO5);
		funeraria3.agregarEmpleado(empleadoO6);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Familiares Mujeres
		Familiar F11= new Familiar("Mario",711,50,cuenta9FA,"padre",17);
		Familiar F12= new Familiar("Alberto",712,32,cuenta10FA,"conyuge",13);
		Familiar F13= new Familiar("Carlos",713,37,cuenta11FA,"hermano",17);
		Familiar F14= new Familiar("Samantha",714,50,cuenta12FA,"padre",17);
		
		//Familiares para todos
		Familiar F15= new Familiar("Samuel",715,60,cuenta13FA,"padre",17);
		Familiar F16= new Familiar("Alma",716,60,cuenta14FA,"padre",13);
		Familiar F17= new Familiar("Eduardo",717,37,cuenta15FA,"hermano",17);
		Familiar F18= new Familiar("Maria",5, "hermano",F17);
		
		//Familiares Hombres 
		Familiar F19= new Familiar("Armando",718,50,cuenta16FA,"padre",17);
		Familiar F110= new Familiar("Catalina",719,32,cuenta17FA,"conyuge",13);
		Familiar F111= new Familiar("Sebastian",7110,37,cuenta18FA,"hermano",17);
		Familiar F112= new Familiar("Alba",7111,25,cuenta19FA,"hijo",17);
		
		//Nuevos Familiares
		Familiar F113 = new Familiar("Saskia",16341,40,cuenta1FA,"padre",17);
		Familiar F114 = new Familiar("Orion",22356,38,cuenta8FA,"conyugue",17);
		Familiar F115 = new Familiar("Aurora",16,"hermano",F113);
		Familiar F116 = new Familiar("Kymani",10,"hijo",F114);
		
		Familiar F117 = new Familiar("Lyra",15332,38,cuenta2FA,"conyugue",17);
		Familiar F118 = new Familiar("Sage",52376,29,cuenta7FA,"padre",17);
		Familiar F119 = new Familiar("Clio",17,"hijo", F117);
		Familiar F120 = new Familiar("Zarek",9,"hermano",F118);
		
		Familiar F121 = new Familiar("Vesper",13423,25,cuenta3FA,"padre",17);
		Familiar F122 = new Familiar("Kaius",14356,20,cuenta6FA,"conyugue",17);
		Familiar F123 = new Familiar("Xanthea",3,"hermano",F121);
		Familiar F124 = new Familiar("Rowan",8,"hijo",F122);
		
		//Funeraria 2
		
		Familiar F21 = new Familiar("Ophelia",12345,18,cuenta4FA,"conyugue",17);
		Familiar F22 = new Familiar("Atticus",12375,70,cuenta5FA,"padre",17);
		Familiar F23 = new Familiar("Lyriel",12,"hijo",F21);
		Familiar F24 = new Familiar("Caspian",7,"hermano",F22);
		
		Familiar F25 = new Familiar("Libia",12345,18,cuenta20FA,"conyugue",17);
		Familiar F26 = new Familiar("Armando",12375,70,cuenta21FA,"padre",17);
		Familiar F27 = new Familiar("Geronimo",12,"hijo",F26);
		Familiar F28 = new Familiar("Alicia",7,"hermano",F25);
		
		Familiar F29 = new Familiar("Andres",12345,18,cuenta22FA,"conyugue",17);
		Familiar F210 = new Familiar("Catalina",12375,70,cuenta23FA,"padre",17);
		Familiar F211 = new Familiar("Lucas",12,"hijo",F29);
		Familiar F212 = new Familiar("Caspian",7,"hermano",F210);
		
		Familiar F213= new Familiar("Alma",715,60,cuenta24FA,"padre",17);
		Familiar F214= new Familiar("Mar",716,60,cuenta25FA,"padre",13);
		Familiar F215= new Familiar("Eduardo",717,37,cuenta26FA,"hermano",17);
		Familiar F216= new Familiar("Andres",5, "hermano",F214);
		
		Familiar F217= new Familiar("Carmen",715,60,cuenta27FA,"padre",17);
		Familiar F218= new Familiar("Catalina",716,60,cuenta28FA,"padre",13);
		Familiar F219= new Familiar("Carlos",717,37,cuenta29FA,"hermano",17);
		Familiar F220= new Familiar("Azul",5, "hermano",F217);
		
		Familiar F221= new Familiar("Pablo",715,60,cuenta30FA,"padre",17);
		Familiar F222= new Familiar("Sol",716,60,cuenta31FA,"padre",13);
		Familiar F223= new Familiar("Andres",717,37,cuenta32FA,"hermano",17);
		Familiar F224= new Familiar("Carlos",5, "hermano",F221);
		
		//Funeraria 3
		
		Familiar F31 = new Familiar("Vanessa",12345,18,cuenta33FA,"conyugue",17);
		Familiar F32 = new Familiar("Carlos",12375,70,cuenta34FA,"padre",17);
		Familiar F33 = new Familiar("Camila",12,"hijo",F21);
		Familiar F34 = new Familiar("Cuasquer",7,"hermano",F32);
		
		Familiar F35 = new Familiar("Carla",12345,18,cuenta35FA,"conyugue",17);
		Familiar F36 = new Familiar("Catalina",12375,70,cuenta36FA,"padre",17);
		Familiar F37 = new Familiar("Manuel",12,"hijo",F36);
		Familiar F38 = new Familiar("Jose",7,"hermano",F35);
		
		Familiar F39 = new Familiar("David",12345,18,cuenta37FA,"conyugue",17);
		Familiar F310 = new Familiar("Esteban",12375,70,cuenta38FA,"padre",17);
		Familiar F311 = new Familiar("Lucas",12,"hijo",F39);
		Familiar F312 = new Familiar("Carlos",7,"hermano",F310);
		
		Familiar F313= new Familiar("Luisa",715,60,cuenta39FA,"padre",17);
		Familiar F314= new Familiar("Maria",716,60,cuenta40FA,"padre",13);
		Familiar F315= new Familiar("Eduardo",717,37,cuenta41FA,"hermano",17);
		Familiar F316= new Familiar("Andres",5, "hermano",F314);
		
		Familiar F317= new Familiar("Mario",715,60,cuenta42FA,"padre",17);
		Familiar F318= new Familiar("Catalina",716,60,cuenta43FA,"padre",13);
		Familiar F319= new Familiar("Hugo",717,37,cuenta44FA,"hermano",17);
		Familiar F320= new Familiar("Azul",5, "hermano",F317);
		
		Familiar F321= new Familiar("Hugo",715,60,cuenta45FA,"padre",17);
		Familiar F322= new Familiar("Sol",716,60,cuenta46FA,"padre",13);
		Familiar F323= new Familiar("Daniel",717,37,cuenta47FA,"hermano",17);
		Familiar F324= new Familiar("Carlos",5, "hermano",F321);
		
		
		//Listas de familiares A
		ArrayList<Familiar> familiarA=new ArrayList<Familiar>();
		familiarA.add(F11);
		familiarA.add(F12);
		familiarA.add(F13);
		familiarA.add(F14);
		
		//Listas de familiares B
		ArrayList<Familiar> familiarB=new ArrayList<Familiar>();
		familiarB.add(F15);
		familiarB.add(F16);
		familiarB.add(F17);
		familiarB.add(F18);
		
		//Listas de familiares C
		ArrayList<Familiar> familiarC=new ArrayList<Familiar>();
		familiarC.add(F19);
		familiarC.add(F110);
		familiarC.add(F111);
		familiarC.add(F112);
		
		//Listas de familiares D
		ArrayList<Familiar> familiarD=new ArrayList<Familiar>();
		familiarD.add(F113);
		familiarD.add(F114);
		familiarD.add(F115);
		familiarD.add(F116);
		
		//Listas de familiares E
		ArrayList<Familiar> familiarE=new ArrayList<Familiar>();
		familiarE.add(F117);
		familiarE.add(F118);
		familiarE.add(F119);
		familiarE.add(F120);
		
		//Listas de familiares F
		ArrayList<Familiar> familiarF=new ArrayList<Familiar>();
		familiarF.add(F121);
		familiarF.add(F122);
		familiarF.add(F123);
		familiarF.add(F124);
		
		
		//Funeraria 2

		
		//Listas de familiares G
		ArrayList<Familiar> familiarG=new ArrayList<Familiar>();
		familiarG.add(F21);
		familiarG.add(F22);
		familiarG.add(F23);
		familiarG.add(F24);
		
		//Listas de familiares H 
		ArrayList<Familiar> familiarH=new ArrayList<Familiar>();
		familiarH.add(F25);
		familiarH.add(F26);
		familiarH.add(F27);
		familiarH.add(F28);
		
		//Listas de familiares I
		ArrayList<Familiar> familiarI=new ArrayList<Familiar>();
		familiarI.add(F29);
		familiarI.add(F210);
		familiarI.add(F211);
		familiarI.add(F212);
		
		//Listas de familiares J
		ArrayList<Familiar> familiarJ=new ArrayList<Familiar>();
		familiarJ.add(F213);
		familiarJ.add(F214);
		familiarJ.add(F215);
		familiarJ.add(F216);
		
		//Listas de familiares K
		ArrayList<Familiar> familiarK=new ArrayList<Familiar>();
		familiarK.add(F217);
		familiarK.add(F218);
		familiarK.add(F219);
		familiarK.add(F220);
		
		//Listas de familiares L 
		ArrayList<Familiar> familiarL=new ArrayList<Familiar>();
		familiarL.add(F221);
		familiarL.add(F222);
		familiarL.add(F223);
		familiarL.add(F224);
		
		//Funeraria 3
		
		//Listas de familiares M
		ArrayList<Familiar> familiarM=new ArrayList<Familiar>();
		familiarM.add(F31);
		familiarM.add(F32);
		familiarM.add(F33);
		familiarM.add(F34);
		
		//Listas de familiares N 
		ArrayList<Familiar> familiarN=new ArrayList<Familiar>();
		familiarN.add(F35);
		familiarN.add(F36);
		familiarN.add(F37);
		familiarN.add(F38);
		
		//Listas de familiares O
		ArrayList<Familiar> familiarO=new ArrayList<Familiar>();
		familiarO.add(F39);
		familiarO.add(F310);
		familiarO.add(F311);
		familiarO.add(F312);
		
		//Listas de familiares P
		ArrayList<Familiar> familiarP=new ArrayList<Familiar>();
		familiarP.add(F313);
		familiarP.add(F314);
		familiarP.add(F315);
		familiarP.add(F316);
		
		//Listas de familiares Q
		ArrayList<Familiar> familiarQ=new ArrayList<Familiar>();
		familiarQ.add(F317);
		familiarQ.add(F318);
		familiarQ.add(F319);
		familiarQ.add(F320);
		
		//Listas de familiares R 
		ArrayList<Familiar> familiarR=new ArrayList<Familiar>();
		familiarR.add(F321);
		familiarR.add(F322);
		familiarR.add(F323);
		familiarR.add(F324);
		
		//Familiares menores de edad con el indicativo M 
		
		//Funeraria 1
		
		Familiar F11M= new Familiar("Carla",715,60,cuenta48FA,"padre",17);
		Familiar F12M= new Familiar("Armando",716,60,cuenta49FA,"padre",13);
		Familiar F13M= new Familiar("Vanesa",717,37,cuenta50FA,"hermano",17);
		Familiar F14M= new Familiar("Maria",5, "hermano",F13M);
		
		Familiar F15M= new Familiar("Alicia",715,60,cuenta51FA,"padre",17);
		Familiar F16M= new Familiar("Anastasio",716,60,cuenta52FA,"padre",13);
		Familiar F17M= new Familiar("Miranda",717,37,cuenta53FA,"hermano",17);
		Familiar F18M= new Familiar("Maria",5, "hermano",F17M);
		
		Familiar F19M= new Familiar("Samuel",715,60,cuenta54FA,"padre",17);
		Familiar F110M= new Familiar("Carla",716,60,cuenta55FA,"padre",13);
		Familiar F111M= new Familiar("Eduardo",717,37,cuenta56FA,"hermano",17);
		Familiar F112M= new Familiar("Maria",5, "hermano",F111M);
		
		Familiar F113M= new Familiar("Eduardo",715,60,cuenta57FA,"padre",17);
		Familiar F114M= new Familiar("Alma",716,60,cuenta58FA,"padre",13);
		Familiar F115M= new Familiar("Eduardo",717,37,cuenta59FA,"hermano",17);
		Familiar F116M= new Familiar("Maria",5, "hermano",F115M);
		
		//Funeraria 2
		
		Familiar F21M= new Familiar("Camila",715,60,cuenta60FA,"padre",17);
		Familiar F22M= new Familiar("Luis",716,60,cuenta61FA,"padre",13);
		Familiar F23M= new Familiar("Tomas",717,37,cuenta62FA,"hermano",17);
		Familiar F24M= new Familiar("Andres",717,37,cuenta63FA,"hermano",17);
		
		Familiar F25M= new Familiar("Vanesa",715,60,cuenta64FA,"padre",17);
		Familiar F26M= new Familiar("Carlos",716,60,cuenta65FA,"padre",13);
		Familiar F27M= new Familiar("Juan Jose",717,37,cuenta66FA,"hermano",17);
		Familiar F28M= new Familiar("Nicolas",717,37,cuenta67FA,"hermano",17);
		
		Familiar F29M= new Familiar("Mateo",715,60,cuenta68FA,"padre",17);
		Familiar F210M= new Familiar("Mariana",716,60,cuenta69FA,"padre",13);
		Familiar F211M= new Familiar("Esteban",717,37,cuenta70FA,"hermano",17);
		Familiar F212M= new Familiar("David",717,37,cuenta71FA,"hermano",17);
		
		Familiar F213M= new Familiar("Jireh",715,60,cuenta72FA,"padre",17);
		Familiar F214M= new Familiar("Carlos",716,60,cuenta73FA,"padre",13);
		Familiar F215M= new Familiar("Carla",717,37,cuenta74FA,"hermano",17);
		Familiar F216M= new Familiar("Carolina",717,37,cuenta75FA,"hermano",17);
		
		//Funeraria 3
		
		Familiar F31M= new Familiar("Carla",715,60,cuenta76FA,"padre",17);
		Familiar F32M= new Familiar("Luis",716,60,cuenta77FA,"padre",13);
		Familiar F33M= new Familiar("Tomas",717,37,cuenta78FA,"hermano",17);
		Familiar F34M= new Familiar("Andres",717,37,cuenta79FA,"hermano",17);
		
		Familiar F35M= new Familiar("Rodrigo",715,60,cuenta80FA,"padre",17);
		Familiar F36M= new Familiar("Carlos",716,60,cuenta81FA,"padre",13);
		Familiar F37M= new Familiar("Juan Jose",717,37,cuenta82FA,"hermano",17);
		Familiar F38M= new Familiar("Nicolas",717,37,cuenta83FA,"hermano",17);
		
		Familiar F39M= new Familiar("Jimena",715,60,cuenta84FA,"padre",17);
		Familiar F310M= new Familiar("Maria Jose",716,60,cuenta85FA,"padre",13);
		Familiar F311M= new Familiar("Esteban",717,37,cuenta86FA,"hermano",17);
		Familiar F312M= new Familiar("David",717,37,cuenta87FA,"hermano",17);
		
		Familiar F313M= new Familiar("David",715,60,cuenta88FA,"padre",17);
		Familiar F314M= new Familiar("Carlos",716,60,cuenta89FA,"padre",13);
		Familiar F315M= new Familiar("Armando",717,37,cuenta90FA,"hermano",17);
		Familiar F316M= new Familiar("Carolina",717,37,cuenta91FA,"hermano",17);
		
		//Listas de familiares para menores de edad con el indicativo M
		
		ArrayList<Familiar> familiarAM=new ArrayList<Familiar>();
		familiarAM.add(F11M);
		familiarAM.add(F12M);
		familiarAM.add(F13M);
		familiarAM.add(F14M);
		
		ArrayList<Familiar> familiarBM=new ArrayList<Familiar>();
		familiarBM.add(F15M);
		familiarBM.add(F16M);
		familiarBM.add(F17M);
		familiarBM.add(F18M);
		
		ArrayList<Familiar> familiarCM=new ArrayList<Familiar>();
		familiarCM.add(F19M);
		familiarCM.add(F110M);
		familiarCM.add(F111M);
		familiarCM.add(F112M);
		
		ArrayList<Familiar> familiarDM=new ArrayList<Familiar>();
		familiarDM.add(F113M);
		familiarDM.add(F114M);
		familiarDM.add(F115M);
		familiarDM.add(F116M);
		
		//Funeraria 2
		
		ArrayList<Familiar> familiarEM=new ArrayList<Familiar>();
		familiarEM.add(F21M);
		familiarEM.add(F22M);
		familiarEM.add(F23M);
		familiarEM.add(F24M);
		
		ArrayList<Familiar> familiarFM=new ArrayList<Familiar>();
		familiarFM.add(F25M);
		familiarFM.add(F26M);
		familiarFM.add(F27M);
		familiarFM.add(F28M);
		
		ArrayList<Familiar> familiarGM=new ArrayList<Familiar>();
		familiarGM.add(F29M);
		familiarGM.add(F210M);
		familiarGM.add(F211M);
		familiarGM.add(F212M);
		
		ArrayList<Familiar> familiarHM=new ArrayList<Familiar>();
		familiarHM.add(F213M);
		familiarHM.add(F214M);
		familiarHM.add(F215M);
		familiarHM.add(F216M);
		
		
		//Funeraria 3
		
		ArrayList<Familiar> familiarIM=new ArrayList<Familiar>();
		familiarIM.add(F31M);
		familiarIM.add(F32M);
		familiarIM.add(F33M);
		familiarIM.add(F34M);
		
		ArrayList<Familiar> familiarJM=new ArrayList<Familiar>();
		familiarJM.add(F35M);
		familiarJM.add(F36M);
		familiarJM.add(F37M);
		familiarJM.add(F38M);
		
		ArrayList<Familiar> familiarKM=new ArrayList<Familiar>();
		familiarKM.add(F39M);
		familiarKM.add(F310M);
		familiarKM.add(F311M);
		familiarKM.add(F312M);
		
		ArrayList<Familiar> familiarLM=new ArrayList<Familiar>();
		familiarLM.add(F313M);
		familiarLM.add(F314M);
		familiarLM.add(F315M);
		familiarLM.add(F316M);
		
		//Objetos Cementerio
		
		//cementerios pertenecientes a F1 --> Funeraria 1 - cenizas
		Cementerio cementerioF11Ce = new Cementerio ("Jardín de la Eternidad",78,cuenta1CE,"oro", null,"cenizas",funeraria1); 
		Cementerio cementerioF12Ce = new Cementerio ("Colina de la Paz",85,cuenta2CE,"oro", null,"cenizas",funeraria1); 
		
		Cementerio cementerioF13Ce = new Cementerio ("Campos de tranquilidad",79,cuenta3CE,"plata", null,"cenizas",funeraria1); 
		Cementerio cementerioF14Ce = new Cementerio ("Valle del Silencio",78,cuenta4CE,"plata", null,"cenizas",funeraria1);
		 
		Cementerio cementerioF15Ce = new Cementerio ("Rincón del Descanso",50,cuenta5CE,"bronce", null,"cenizas",funeraria1); 
		Cementerio cementerioF16Ce = new Cementerio ("Jardín de los Recuerdos",78,cuenta6CE,"bronce", null,"cenizas",funeraria1); 
		
		
		//cementerios pertenecientes a F1 --> Funeraria 1 - cuerpos
		Cementerio cementerioF11Cu = new Cementerio ("Eternidad Verde",78,cuenta7CE,"oro", null,"cuerpos",funeraria1); 
		Cementerio cementerioF12Cu = new Cementerio ("Mirador de la Serenidad",85,cuenta8CE,"oro", null,"cuerpos",funeraria1); 
		
		Cementerio cementerioF13Cu = new Cementerio ("Bosque de la Memoria",50,cuenta9CE,"plata", null,"cuerpos",funeraria1); 
		Cementerio cementerioF14Cu = new Cementerio ("Cementerio del Refugi",78,cuenta10CE,"plata", null,"cuerpos",funeraria1);
		 
		Cementerio cementerioF15Cu = new Cementerio ("Paz y Esperanza",78,cuenta11CE,"bronce", null,"cuerpos",funeraria1); 
		Cementerio cementerioF16Cu = new Cementerio ("Sendero de la Tranquilidad",78,cuenta12CE,"bronce", null,"cuerpos",funeraria1); 
		
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
		Urna urnaF1C33=new Urna("Urnita Caja de la Verdad",cementerioF13Ce,50,0,"ordinaria");
		Urna urnaF1C34=new Urna("Urnita Urna de la Democracia",cementerioF13Ce,60,1,"fija");
		
		//Objetos Cementerio 4 Urna Cenizas
		
		Urna urnaF1C41=new Urna("Urnita Voz del Pueblo",cementerioF14Ce,70,1,"fija");
		Urna urnaF1C42=new Urna("Urnita Cámara de Decisiones",cementerioF14Ce,80,0,"ordinaria");
		Urna urnaF1C43=new Urna("Urnita Bóveda de Opiniones",cementerioF14Ce,70,0,"ordinaria");
		Urna urnaF1C44=new Urna("Urnita Recinto Electoral",cementerioF14Ce,60,1,"fija");
		
		//Objetos Cementerio 5 Urna Cenizas
		
		Urna urnaF1C51=new Urna("Urnita Contenedor de Voluntades",cementerioF15Ce,70,1,"fija");
		Urna urnaF1C52=new Urna("Urnita Caja de Equidad",cementerioF15Ce,80,0,"ordinaria");
		Urna urnaF1C53=new Urna("Urnita de la Justicia",cementerioF15Ce,70,0,"ordinaria");
		Urna urnaF1C54=new Urna("Urnita Escudo Electoral",cementerioF15Ce,60,1,"fija");
		
		//Objetos Cementerio 6 Urna Cenizas
		
		Urna urnaF1C61=new Urna("Urnita Cápsula de Sueños",cementerioF16Ce,70,1,"fija");
		Urna urnaF1C62=new Urna("Urnita Templo de Belleza",cementerioF16Ce,80,0,"ordinaria");
		Urna urnaF1C63=new Urna("Urnita Misterio Dorado",cementerioF16Ce,60,0,"ordinaria");
		Urna urnaF1C64=new Urna("Urnita Joyero de Recuerdos",cementerioF16Ce,60,1,"fija");
		
		
		/////////////////////////////// 
		
		//Objetos Cementerio 1 Tumba Cuerpos
		
		Tumba tumbaF1C11=new Tumba("Tumbita Aquí Reposa un Corazón Noble",cementerioF11Cu,1.70,0);
		Tumba tumbaF1C12=new Tumba("Tumbita Amado por Siempre",cementerioF11Cu,1.60,0);
		Tumba tumbaF1C13=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF11Cu,1.60,1);
		Tumba tumbaF1C14=new Tumba("Tumbita Un Alma Inmortal",cementerioF11Cu,1.80,1);
		Tumba tumbaF1C15=new Tumba("Tumbita Tu Luz Nos Guía",cementerioF11Cu,1.75,2);
		Tumba tumbaF1C16=new Tumba("Tumbita Querido y Recordado",cementerioF11Cu,1.50,1);
	
		//Objetos Cementerio 2 Tumba Cuerpos
		
		Tumba tumbaF1C21=new Tumba("Tumbita Descansa en Paz, Amado",cementerioF12Cu,1.70,0);
		Tumba tumbaF1C22=new Tumba("Tumbita Tu Memoria Vive en Nosotros",cementerioF12Cu,1.10,0);
		Tumba tumbaF1C23=new Tumba("Tumbita El Amor Trasciende",cementerioF12Cu,1.60,1);
		Tumba tumbaF1C24=new Tumba("Tumbita Una Vida Lleno de Amor",cementerioF12Cu,1.70,1);
		Tumba tumbaF1C25=new Tumba("Tumbita Copa del Encanto",cementerioF12Cu,1.75,2);
		Tumba tumbaF1C26=new Tumba("Tumbita Portal de Arte",cementerioF12Cu,1.8,2);
		
		//Objetos Cementerio 3 Tumba Cuerpos
		
		Tumba tumbaF1C31=new Tumba("Tumbita Esfera de Serenidad",cementerioF13Cu,1.70,0);
		Tumba tumbaF1C32=new Tumba("Tumbita Reflejo de Elegancia",cementerioF13Cu,1.90,0);
		Tumba tumbaF1C33=new Tumba("Tumbita Caja de Maravillas",cementerioF13Cu,1.60,1);
		Tumba tumbaF1C34=new Tumba("Tumbita Jardín del Recuerdo",cementerioF13Cu,1.60,1);
		Tumba tumbaF1C35=new Tumba("Tumbita Refugio del Alma",cementerioF13Cu,1.75,2);
		Tumba tumbaF1C36=new Tumba("Tumbita Lugar de Serenidad",cementerioF13Cu,1.50,2);
		
		//Objetos Cementerio 4 Tumba Cuerpos
		
		Tumba tumbaF1C41=new Tumba("Tumbita Eterna Luz",cementerioF14Cu,1.60,0);
		Tumba tumbaF1C42=new Tumba("Tumbita Sombra Sagrada",cementerioF14Cu,1.70,0);
		Tumba tumbaF1C43=new Tumba("Tumbita Cámara del Silencio",cementerioF14Cu,1.60,1);
		Tumba tumbaF1C44=new Tumba("Tumbita Rincón de Paz",cementerioF14Cu,1.80,1);
		Tumba tumbaF1C45=new Tumba("Tumbita Hogar de Paz",cementerioF14Cu,1.75,2);
		Tumba tumbaF1C46=new Tumba("Tumbita Sendero de Tranquilidad",cementerioF14Cu,1.5,2);
		
		//Objetos Cementerio 5 Tumba Cuerpos
		
		Tumba tumbaF1C51=new Tumba("Tumbita Velo de Recuerdo",cementerioF15Cu,1.70,0);
		Tumba tumbaF1C52=new Tumba("Tumbita Cascada de Paz",cementerioF15Cu,1.60,0);
		Tumba tumbaF1C53=new Tumba("Tumbita Refugio Perpetuo",cementerioF15Cu,1.60,1);
		Tumba tumbaF1C54=new Tumba("Tumbita Sombra de Amor",cementerioF15Cu,1.70,1);
		Tumba tumbaF1C55=new Tumba("Tumbita Eterna Quietud",cementerioF15Cu,1.75,2);
		Tumba tumbaF1C56=new Tumba("Tumbita Altar de Recuerdos",cementerioF15Cu,1.80,2);
	
		//Objetos Cementerio 6 Tumba Cuerpos
		
		Tumba tumbaF1C61=new Tumba("Tumbita Una Vida de Amor y Bondad",cementerioF16Cu,1.70,0);
		Tumba tumbaF1C62=new Tumba("Tumbita Siempre en Nuestro Corazón y Pensamiento",cementerioF16Cu,1.30,0);
		Tumba tumbaF1C63=new Tumba("Tumbita En Tu Ausencia",cementerioF16Cu,1.60,1);
		Tumba tumbaF1C64=new Tumba("Tumbita Tu Presencia es Más Fuerte",cementerioF16Cu,1.50,1);
		Tumba tumbaF1C65=new Tumba("Tumbita Una Vida Dedicada al Amor",cementerioF16Cu,1.75,2);
		Tumba tumbaF1C66=new Tumba("Tumbita Un Alma Valiente",cementerioF16Cu,1.60,2);
		
		
		//Objetos Crematorio
		
		//crematorios pertenecientes a F1 --> Funeraria 1
		Crematorio crematorioF11 = new Crematorio ("Crematorio Luz y Paz",100,cuenta1CR,"oro", null,funeraria1); 
		Crematorio crematorioF12 = new Crematorio ("Hogar de la Ascensión",78,cuenta2CR,"oro", null,funeraria1); 
		
		Crematorio crematorioF13 = new Crematorio ("Fuego de la Memoria",78,cuenta3CR,"plata", null,funeraria1); 
		Crematorio crematorioF14 = new Crematorio ("Crematorio Serenidad Eterna",78,cuenta4CR,"plata", null,funeraria1);
		
		Crematorio crematorioF15 = new Crematorio ("Llama de la Eternidada",78,cuenta5CR,"bronce", null,funeraria1); 
		Crematorio crematorioF16 = new Crematorio ("Refugio del Alba",78,cuenta6CR,"bronce", null,funeraria1);
		
		//Clientes F1 - Mayores de edad
		
		Cliente clienteF11 = new Cliente("Alejandro Rodríguez",123,30,cuenta1CL,"oro",familiarC);
		Cliente clienteF12 = new Cliente("Diego Martínez",1234,25,cuenta2CL,"oro",familiarE);
		
		Cliente clienteF13 = new Cliente("Carlos Fernández",1235,90,cuenta3CL,"plata",familiarD);
		Cliente clienteF14 = new Cliente("María González",1236,57,cuenta4CL,"plata",familiarB);
		
		Cliente clienteF15 = new Cliente("Laura Fernández",1237,21,cuenta5CL,"bronce",familiarA);
		Cliente clienteF16 = new Cliente("Isabel Rodríguez",1238,50,cuenta6CL,"bronce",familiarF);
	
		
		//Clientes F1 - Menores de edad
		Cliente clienteF17 = new Cliente("Javier Gómez",5,"oro",familiarAM);
		Cliente clienteF18 = new Cliente("Sofía Martínez",17,"oro",familiarBM);
		
		Cliente clienteF19 = new Cliente("Carolina López",15,"plata",familiarCM);
		Cliente clienteF110 = new Cliente("Manuel López",13,"plata",familiarDM);
		
	
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
		Cementerio cementerioF21Ce = new Cementerio ("Cementerio del Silencio",78,cuenta13CE,"oro", null,"cenizas",funeraria2); 
		Cementerio cementerioF22Ce = new Cementerio ("Campo de la Eternidad",85,cuenta14CE,"oro", null,"cenizas",funeraria2); 
				
		Cementerio cementerioF23Ce = new Cementerio ("Bosque de la Serenidad",79,cuenta15CE,"plata", null,"cenizas",funeraria2); 
		Cementerio cementerioF24Ce = new Cementerio ("Jardines del Descanso",78,cuenta16CE,"plata", null,"cenizas",funeraria2);
				 
		Cementerio cementerioF25Ce = new Cementerio ("Valle de la Paz Interior",50,cuenta17CE,"bronce", null,"cenizas",funeraria2); 
		Cementerio cementerioF26Ce = new Cementerio ("Luz del Recuerdo",78,cuenta18CE,"bronce", null,"cenizas",funeraria2); 
				
			
		//cementerios pertenecientes a F2 --> Funeraria 2 - cuerpos
		Cementerio cementerioF21Cu = new Cementerio ("Colinas del Reposo",78,cuenta19CE,"oro", null,"cuerpos",funeraria2); 
		Cementerio cementerioF22Cu = new Cementerio ("Jardín de la Eternidad",85,cuenta20CE,"oro", null,"cuerpos",funeraria2); 
				
		Cementerio cementerioF23Cu = new Cementerio ("Refugio de la Memoria",50,cuenta21CE,"plata", null,"cuerpos",funeraria2); 
		Cementerio cementerioF24Cu = new Cementerio ("Cementerio del Alba",78,cuenta22CE,"plata", null,"cuerpos",funeraria2);
				 
		Cementerio cementerioF25Cu = new Cementerio ("Alameda de la Paz",78,cuenta23CE,"bronce", null,"cuerpos",funeraria2); 
		Cementerio cementerioF26Cu = new Cementerio ("Jardín del Silencio Eterno",78,cuenta24CE,"bronce", null,"cuerpos",funeraria2); 
		
		//Objetos Cementerio 1 Cenizas 
		
		Urna urnaF2C11=new Urna("Urnita Eterna Paz",cementerioF21Ce,70,1,"fija");
		Urna urnaF2C12=new Urna("Urnita Memoria Serene",cementerioF21Ce,80,0,"fija");
		Urna urnaF2C13=new Urna("Urnita Descanso Sagrado",cementerioF21Ce,50,0,"ordinaria");
		Urna urnaF2C14=new Urna("Urnita Luz Eterna",cementerioF21Ce,60,1,"fija");
		
		
		//Objetos Cementerio 2 Urna Cenizas
		
		Urna urnaF2C21=new Urna("Urnita Tranquilidad Infinita",cementerioF22Ce,50,1,"fija");
		Urna urnaF2C22=new Urna("Urnita Homenaje Perpetuo",cementerioF22Ce,80,0,"ordinaria");
		Urna urnaF2C23=new Urna("Urnita Amanecer Sereno",cementerioF22Ce,70,0,"ordinaria");
		Urna urnaF2C24=new Urna("Urnita Refugio del Alma",cementerioF22Ce,60,1,"ordinaria");
		
		//Objetos Cementerio 3 Urna Cenizas
		
		Urna urnaF2C31=new Urna("Urnita Oasis de Recuerdo",cementerioF23Ce,60,1,"fija");
		Urna urnaF2C32=new Urna("Urnita Sombra Amada",cementerioF23Ce,50,0,"ordinaria");
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
		Urna urnaF2C53=new Urna("Urnita de la Justicia",cementerioF25Ce,50,2,"ordinaria");
		Urna urnaF2C54=new Urna("Urnita Escudo Electoral",cementerioF25Ce,60,1,"fija");
		
		//Objetos Cementerio 6 Urna Cenizas
		
		Urna urnaF2C61=new Urna("Urnita Cápsula de Sueños",cementerioF26Ce,70,1,"fija");
		Urna urnaF2C62=new Urna("Urnita Templo de Belleza",cementerioF26Ce,80,0,"ordinaria");
		Urna urnaF2C63=new Urna("Urnita Misterio Dorado",cementerioF26Ce,40,0,"ordinaria");
		Urna urnaF2C64=new Urna("Urnita Joyero de Recuerdos",cementerioF26Ce,60,1,"fija");
		
		
		/////////////////////////////// 
		
		//Objetos Cementerio 1 Tumba Cuerpos
		
		Tumba tumbaF2C11=new Tumba("Tumbita Aquí Reposa un Corazón Noble",cementerioF21Cu,1.70,1);
		Tumba tumbaF2C12=new Tumba("Tumbita Amado por Siempre",cementerioF21Cu,1.60,0);
		Tumba tumbaF2C13=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF21Cu,1.60,1);
		Tumba tumbaF2C14=new Tumba("Tumbita Un Alma Inmortal",cementerioF21Cu,1.20,1);
		Tumba tumbaF2C15=new Tumba("Tumbita Tu Luz Nos Guía",cementerioF21Cu,1.55,2);
		Tumba tumbaF2C16=new Tumba("Tumbita Querido y Recordado",cementerioF21Cu,1.5,2);
	
		//Objetos Cementerio 2 Tumba Cuerpos
		
		Tumba tumbaF2C21=new Tumba("Tumbita Descansa en Paz, Amado",cementerioF22Cu,1.70,0);
		Tumba tumbaF2C22=new Tumba("Tumbita Tu Memoria Vive en Nosotros",cementerioF22Cu,1.60,0);
		Tumba tumbaF2C23=new Tumba("Tumbita El Amor Trasciende",cementerioF22Cu,1.80,1);
		Tumba tumbaF2C24=new Tumba("Tumbita Una Vida Lleno de Amor",cementerioF22Cu,1.60,1);
		Tumba tumbaF2C25=new Tumba("Tumbita Copa del Encanto",cementerioF22Cu,1.75,2);
		Tumba tumbaF2C26=new Tumba("Tumbita Portal de Arte",cementerioF22Cu,1.70,2);
		
		//Objetos Cementerio 3 Tumba Cuerpos
		
		Tumba tumbaF2C31=new Tumba("Tumbita Esfera de Serenidad",cementerioF23Cu,1.70,0);
		Tumba tumbaF2C32=new Tumba("Tumbita Reflejo de Elegancia",cementerioF23Cu,1.65,0);
		Tumba tumbaF2C33=new Tumba("Tumbita Caja de Maravillas",cementerioF23Cu,1.60,1);
		Tumba tumbaF2C34=new Tumba("Tumbita Jardín del Recuerdo",cementerioF23Cu,1.50,1);
		Tumba tumbaF2C35=new Tumba("Tumbita Refugio del Alma",cementerioF23Cu,1.75,1);
		Tumba tumbaF2C36=new Tumba("Tumbita Lugar de Serenidad",cementerioF23Cu,1.40,2);
		
		//Objetos Cementerio 4 Tumba Cuerpos
		
		Tumba tumbaF2C41=new Tumba("Tumbita Eterna Luz",cementerioF24Cu,1.70,1);
		Tumba tumbaF2C42=new Tumba("Tumbita Sombra Sagrada",cementerioF24Cu,1.80,0);
		Tumba tumbaF2C43=new Tumba("Tumbita Cámara del Silencio",cementerioF24Cu,1.60,1);
		Tumba tumbaF2C44=new Tumba("Tumbita Rincón de Paz",cementerioF24Cu,1.80,1);
		Tumba tumbaF2C45=new Tumba("Tumbita Hogar de Paz",cementerioF24Cu,1.75,2);
		Tumba tumbaF2C46=new Tumba("Tumbita Sendero de Tranquilidad",cementerioF24Cu,1.50,2);
		
		//Objetos Cementerio 5 Tumba Cuerpos
		
		Tumba tumbaF2C51=new Tumba("Tumbita Velo de Recuerdo",cementerioF25Cu,1.70,0);
		Tumba tumbaF2C52=new Tumba("Tumbita Cascada de Paz",cementerioF25Cu,1.70,0);
		Tumba tumbaF2C53=new Tumba("Tumbita Refugio Perpetuo",cementerioF25Cu,1.60,1);
		Tumba tumbaF2C54=new Tumba("Tumbita Sombra de Amor",cementerioF25Cu,1.65,0);
		Tumba tumbaF2C55=new Tumba("Tumbita Eterna Quietud",cementerioF25Cu,1.58,2);
		Tumba tumbaF2C56=new Tumba("Tumbita Altar de Recuerdos",cementerioF25Cu,1.68,2);
	
		//Objetos Cementerio 6 Tumba Cuerpos
		
		Tumba tumbaF2C61=new Tumba("Tumbita Una Vida de Amor y Bondad",cementerioF26Cu,1.50,0);
		Tumba tumbaF2C62=new Tumba("Tumbita Siempre en Nuestro Corazón y Pensamiento",cementerioF26Cu,1.40,0);
		Tumba tumbaF2C63=new Tumba("Tumbita En Tu Ausencia",cementerioF26Cu,1.60,1);
		Tumba tumbaF2C64=new Tumba("Tumbita Tu Presencia es Más Fuerte",cementerioF26Cu,1.68,1);
		Tumba tumbaF2C65=new Tumba("Tumbita Una Vida Dedicada al Amor",cementerioF26Cu,1.75,2);
		Tumba tumbaF2C66=new Tumba("Tumbita Un Alma Valiente",cementerioF26Cu,1.60,2);
		
		
		
		//crematorios pertenecientes a F2 --> Funeraria 2
		Crematorio crematorioF21 = new Crematorio ("Crematorio del Silencio",100,cuenta7CR,"oro", null,funeraria2); 
		Crematorio crematorioF22 = new Crematorio ("Ascenso y Tranquilidad",78,cuenta8CR,"oro", null,funeraria2); 
		
		Crematorio crematorioF23 = new Crematorio ("Brasa de Paz",78,cuenta9CR,"plata", null,funeraria2); 
		Crematorio crematorioF24 = new Crematorio ("Eterna Luz Crematorio",78,cuenta10CR,"plata", null,funeraria2);
		
		Crematorio crematorioF25 = new Crematorio ("Crematorio del Renacer",78,cuenta11CR,"bronce", null,funeraria2); 
		Crematorio crematorioF26 = new Crematorio ("Fuego y Serenidad",78,cuenta12CR,"bronce", null,funeraria2);		
	
		//Clientes F2 - Mayores de edad
		
		Cliente clienteF21 = new Cliente("Valeria Sánchez",231,30,cuenta7CL,"oro",familiarG);
		Cliente clienteF22 = new Cliente("Patricia Morales",232,25,cuenta8CL,"oro",familiarH);
								
		Cliente clienteF23 = new Cliente("Gabriela García",233,90,cuenta9CL,"plata",familiarI);
		Cliente clienteF24 = new Cliente("Andrés Vargas",234,57,cuenta10CL,"plata",familiarJ);
								
		Cliente clienteF25 = new Cliente("Sergio Pérez",235,35,cuenta11CL, "bronce",familiarK);
		Cliente clienteF26 = new Cliente("Luis García",236,50,cuenta12CL, "bronce",familiarL);
						
						
		//Clientes F2 - Menores de edad
		Cliente clienteF27 = new Cliente("Rafael Morales",5,"oro",familiarEM);
		Cliente clienteF28 = new Cliente("Pablo Sánchez",17,"oro",familiarFM);
								
		Cliente clienteF29 = new Cliente("Ana Belén Ruiz",15,"bronce",familiarGM);
		Cliente clienteF210 = new Cliente("Claudia Romero",13,"bronce",familiarHM);
				
		
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
		
		Cementerio cementerioF31Ce = new Cementerio ("Jardín de los Ángeles",78,cuenta25CE,"oro", null,"cenizas",funeraria3); 
		Cementerio cementerioF32Ce = new Cementerio ("Campo de la Tranquilidad",85,cuenta26CE,"oro", null,"cenizas",funeraria3); 
				
		Cementerio cementerioF33Ce = new Cementerio ("Oasis de Paz",79,cuenta27CE,"plata", null,"cenizas",funeraria3); 
		Cementerio cementerioF34Ce = new Cementerio ("Colinas del Recuerdo",78,cuenta28CE,"plata", null,"cenizas",funeraria3);
				 
		Cementerio cementerioF35Ce = new Cementerio ("Rincón del Silencio",50,cuenta29CE,"bronce", null,"cenizas",funeraria3); 
		Cementerio cementerioF36Ce = new Cementerio ("Eterna Armonía",78,cuenta30CE,"bronce", null,"cenizas",funeraria3); 
		
		
		//cementerios pertenecientes a F3 --> Funeraria 3 - cuerpos
		Cementerio cementerioF31Cu = new Cementerio ("Bosque de los Sueños",78,cuenta31CE,"oro", null,"cuerpos",funeraria3); 
		Cementerio cementerioF32Cu = new Cementerio ("Pradera del Descanso",85,cuenta32CE,"oro", null,"cuerpos",funeraria3); 
						
		Cementerio cementerioF33Cu = new Cementerio ("Refugio de la Memoria",50,cuenta33CE,"plata", null,"cuerpos",funeraria3); 
		Cementerio cementerioF34Cu = new Cementerio ("Sendero de la Memoria",78,cuenta34CE,"plata", null,"cuerpos",funeraria3);
						 
		Cementerio cementerioF35Cu = new Cementerio ("Valle del Reposo",78,cuenta35CE,"bronce", null,"cuerpos",funeraria3); 
		Cementerio cementerioF36Cu = new Cementerio ("Jardines del Refugio",78,cuenta36CE,"bronce", null,"cuerpos",funeraria3);
		
		//Objetos Cementerio 1 Cenizas 
		
		Urna urnaF3C11=new Urna("Urnita Eterna Paz",cementerioF31Ce,80,1,"fija");
		Urna urnaF3C12=new Urna("Urnita Memoria Serene",cementerioF31Ce,70,0,"fija");
		Urna urnaF3C13=new Urna("Urnita Descanso Sagrado",cementerioF31Ce,60,0,"ordinaria");
		Urna urnaF3C14=new Urna("Urnita Luz Eterna",cementerioF31Ce,60,1,"fija");
		
		
		//Objetos Cementerio 2 Urna Cenizas
		
		Urna urnaF3C21=new Urna("Urnita Tranquilidad Infinita",cementerioF32Ce,50,1,"fija");
		Urna urnaF3C22=new Urna("Urnita Homenaje Perpetuo",cementerioF32Ce,80,0,"ordinaria");
		Urna urnaF3C23=new Urna("Urnita Amanecer Sereno",cementerioF32Ce,70,0,"fija");
		Urna urnaF3C24=new Urna("Urnita Refugio del Alma",cementerioF32Ce,60,1,"fija");
		
		//Objetos Cementerio 3 Urna Cenizas
		
		Urna urnaF3C31=new Urna("Urnita Oasis de Recuerdo",cementerioF33Ce,80,1,"fija");
		Urna urnaF3C32=new Urna("Urnita Sombra Amada",cementerioF33Ce,90,0,"ordinaria");
		Urna urnaF3C33=new Urna("Urnita Caja de la Verdad",cementerioF33Ce,60,2,"ordinaria");
		Urna urnaF3C34=new Urna("Urnita Urna de la Democracia",cementerioF33Ce,60,1,"fija");
		
		//Objetos Cementerio 4 Urna Cenizas
		
		Urna urnaF3C41=new Urna("Urnita Voz del Pueblo",cementerioF34Ce,70,1,"fija");
		Urna urnaF3C42=new Urna("Urnita Cámara de Decisiones",cementerioF34Ce,80,0,"ordinaria");
		Urna urnaF3C43=new Urna("Urnita Bóveda de Opiniones",cementerioF34Ce,80,0,"ordinaria");
		Urna urnaF3C44=new Urna("Urnita Recinto Electoral",cementerioF34Ce,60,1,"fija");
		
		//Objetos Cementerio 5 Urna Cenizas
		
		Urna urnaF3C51=new Urna("Urnita Contenedor de Voluntades",cementerioF35Ce,70,1,"fija");
		Urna urnaF3C52=new Urna("Urnita Caja de Equidad",cementerioF35Ce,80,1,"ordinaria");
		Urna urnaF3C53=new Urna("Urnita de la Justicia",cementerioF35Ce,90,2,"fija");
		Urna urnaF3C54=new Urna("Urnita Escudo Electoral",cementerioF35Ce,60,1,"fija");
		
		//Objetos Cementerio 6 Urna Cenizas
		
		Urna urnaF3C61=new Urna("Urnita Cápsula de Sueños",cementerioF36Ce,70,1,"fija");
		Urna urnaF3C62=new Urna("Urnita Templo de Belleza",cementerioF36Ce,120,0,"ordinaria");
		Urna urnaF3C63=new Urna("Urnita Misterio Dorado",cementerioF36Ce,90,0,"ordinaria");
		Urna urnaF3C64=new Urna("Urnita Joyero de Recuerdos",cementerioF36Ce,60,1,"fija");
		
		
		/////////////////////////////// 
		
		//Objetos Cementerio 1 Tumba Cuerpos
		
		Tumba tumbaF3C11=new Tumba("Tumbita Aquí Reposa un Corazón Noble",cementerioF31Cu,1.70,1);
		Tumba tumbaF3C12=new Tumba("Tumbita Amado por Siempre",cementerioF31Cu,2.75,0);
		Tumba tumbaF3C13=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF31Cu,1.60,1);
		Tumba tumbaF3C14=new Tumba("Tumbita Un Alma Inmortal",cementerioF31Cu,1.70,1);
		Tumba tumbaF3C15=new Tumba("Tumbita Tu Luz Nos Guía",cementerioF31Cu,1.85,2);
		Tumba tumbaF3C16=new Tumba("Tumbita Querido y Recordado",cementerioF31Cu,1.60,2);
	
		//Objetos Cementerio 2 Tumba Cuerpos
		
		Tumba tumbaF3C21=new Tumba("Tumbita Descansa en Paz, Amado",cementerioF32Cu,1.70,0);
		Tumba tumbaF3C22=new Tumba("Tumbita Tu Memoria Vive en Nosotros",cementerioF32Cu,1.10,0);
		Tumba tumbaF3C23=new Tumba("Tumbita El Amor Trasciende",cementerioF32Cu,1.80,1);
		Tumba tumbaF3C24=new Tumba("Tumbita Una Vida Lleno de Amor",cementerioF32Cu,1.60,1);
		Tumba tumbaF3C25=new Tumba("Tumbita Copa del Encanto",cementerioF32Cu,1.75,2);
		Tumba tumbaF3C26=new Tumba("Tumbita Portal de Arte",cementerioF32Cu,1.50,2);
		
		//Objetos Cementerio 3 Tumba Cuerpos
		
		Tumba tumbaF3C31=new Tumba("Tumbita Esfera de Serenidad",cementerioF23Cu,1.70,0);
		Tumba tumbaF3C32=new Tumba("Tumbita Reflejo de Elegancia",cementerioF23Cu,1.70,0);
		Tumba tumbaF3C33=new Tumba("Tumbita Caja de Maravillas",cementerioF23Cu,1.60,1);
		Tumba tumbaF3C34=new Tumba("Tumbita Jardín del Recuerdo",cementerioF23Cu,1.20,1);
		Tumba tumbaF3C35=new Tumba("Tumbita Refugio del Alma",cementerioF23Cu,1.65,1);
		Tumba tumbaF3C36=new Tumba("Tumbita Lugar de Serenidad",cementerioF23Cu,1.5,2);
		
		//Objetos Cementerio 4 Tumba Cuerpos
		
		Tumba tumbaF3C41=new Tumba("Tumbita Eterna Luz",cementerioF34Cu,1.70,1);
		Tumba tumbaF3C42=new Tumba("Tumbita Sombra Sagrada",cementerioF34Cu,1.70,0);
		Tumba tumbaF3C43=new Tumba("Tumbita Cámara del Silencio",cementerioF34Cu,1.60,1);
		Tumba tumbaF3C44=new Tumba("Tumbita Rincón de Paz",cementerioF34Cu,1.87,1);
		Tumba tumbaF3C45=new Tumba("Tumbita Hogar de Paz",cementerioF34Cu,1.75,2);
		Tumba tumbaF3C46=new Tumba("Tumbita Sendero de Tranquilidad",cementerioF34Cu,1.67,0);
		
		//Objetos Cementerio 5 Tumba Cuerpos
		
		Tumba tumbaF3C51=new Tumba("Tumbita Velo de Recuerdo",cementerioF35Cu,1.70,0);
		Tumba tumbaF3C52=new Tumba("Tumbita Cascada de Paz",cementerioF35Cu,1.60,0);
		Tumba tumbaF3C53=new Tumba("Tumbita Refugio Perpetuo",cementerioF35Cu,1.60,1);
		Tumba tumbaF3C54=new Tumba("Tumbita Sombra de Amor",cementerioF35Cu,1.70,2);
		Tumba tumbaF3C55=new Tumba("Tumbita Eterna Quietud",cementerioF35Cu,1.80,2);
		Tumba tumbaF3C56=new Tumba("Tumbita Altar de Recuerdos",cementerioF35Cu,1.5,2);
	
		//Objetos Cementerio 6 Tumba Cuerpos
		
		Tumba tumbaF3C61=new Tumba("Tumbita Una Vida de Amor y Bondad",cementerioF36Cu,1.50,0);
		Tumba tumbaF3C62=new Tumba("Tumbita Siempre en Nuestro Corazón y Pensamiento",cementerioF36Cu,1.70,0);
		Tumba tumbaF3C63=new Tumba("Tumbita En Tu Ausencia",cementerioF36Cu,2.30,1);
		Tumba tumbaF3C64=new Tumba("Tumbita Tu Presencia es Más Fuerte",cementerioF36Cu,1.50,1);
		Tumba tumbaF3C65=new Tumba("Tumbita Una Vida Dedicada al Amor",cementerioF36Cu,1.75,2);
		Tumba tumbaF3C66=new Tumba("Tumbita Un Alma Valiente",cementerioF36Cu,1.89,2);
		
		
		
		//crematorios pertenecientes a F3 --> Funeraria 3
		Crematorio crematorioF31 = new Crematorio ("Crematorio del Horizonte",100,cuenta13CR,"oro", null,funeraria3); 
		Crematorio crematorioF32 = new Crematorio ("Cenizas de la Eternidad	",78,cuenta14CR,"oro", null,funeraria3); 
				
		Crematorio crematorioF33 = new Crematorio ("Refugio de la Luz",78,cuenta15CR,"plata", null,funeraria3); 
		Crematorio crematorioF34 = new Crematorio ("Fuego de Serenidad",78,cuenta16CR,"plata", null,funeraria3);
				
		Crematorio crematorioF35 = new Crematorio ("Crematorio del Horizonte Eterno",78,cuenta17CR,"bronce", null,funeraria3); 
		Crematorio crematorioF36 = new Crematorio ("Crematorio Luz de la Eternidad",78,cuenta18CR,"bronce", null,funeraria3);	
		
		//Clientes F3 - Mayores de edad
		
		Cliente clienteF31 = new Cliente("Ana Torres",3111,30,cuenta13CL,"oro",familiarM);
		Cliente clienteF32 = new Cliente("Beatriz Sánchez",3112,25,cuenta14CL,"oro",familiarN);
										
		Cliente clienteF33 = new Cliente("Alex Cruz",3113,90,cuenta15CL,"plata",familiarO);
		Cliente clienteF34 = new Cliente("Dani Morales",3114,57,cuenta16CL,"plata",familiarP);
										
		Cliente clienteF35 = new Cliente("Lucía González",3115,50,cuenta17CL, "bronce",familiarQ);
		Cliente clienteF36 = new Cliente("Jordan Silva",3115,30,cuenta18CL, "bronce",familiarR);
								
								
		//Clientes F3 - Menores de edad
		Cliente clienteF37 = new Cliente("Fernanda Salazar",5,"oro",familiarIM);
		Cliente clienteF38 = new Cliente("Carmen Vega",17,"oro",familiarJM);
										
		Cliente clienteF39 = new Cliente("Kim Hernández",15,"bronce",familiarKM);
		Cliente clienteF310 = new Cliente("Morgan López",13,"bronce",familiarLM);
		
		
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
		
		//Agregar vehiculos en  funeraria 1
		funeraria1.agregarVehiculo(veh1);
		funeraria1.agregarVehiculo(veh2);
		funeraria1.agregarVehiculo(veh3);
		funeraria1.agregarVehiculo(veh4);
		funeraria1.agregarVehiculo(veh5);
		funeraria1.agregarVehiculo(veh6);
		funeraria1.agregarVehiculo(veh7);
		
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
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Funcionalidad Exhumacion
		
		//Funeraria 1
		
		Cliente clienteF11E = new Cliente("Juan Pérez",3212,30,cuenta19CL,"oro",familiarA);
		Cliente clienteF12E = new Cliente("Carlos Fernández",3213,25,cuenta20CL,"oro",familiarA);
										
		Cliente clienteF13E = new Cliente("Miguel Rodríguez",3213,90,cuenta21CL,"plata",familiarC);
		Cliente clienteF14E = new Cliente("Dani Morales",3214,57,cuenta22CL,"plata",familiarC);
										
		Cliente clienteF15E = new Cliente("Pedro González",3215,50,cuenta23CL, "bronce",familiarB);
		Cliente clienteF16E = new Cliente("José Martínez",3215,30,cuenta24CL, "bronce",familiarA);
		

		Cliente clienteF17E = new Cliente("María López",5,"oro",familiarB);
		Cliente clienteF18E = new Cliente("Carmen García",17,"oro",familiarB);
										
		Cliente clienteF19E = new Cliente("Ana Torres",15,"bronce",familiarB);
		Cliente clienteF110E = new Cliente("Isabel Ramírez",13,"bronce",familiarB);
		

		Cliente clienteF111E = new Cliente("Laura Morales",233,90,cuenta25CL,"plata",familiarA);
		Cliente clienteF112E = new Cliente("Robert Jones",234,57,cuenta26CL,"plata",familiarC);
								
		Cliente clienteF113E = new Cliente("Olivia Miller",235,35,cuenta27CL, "bronce",familiarC);
		Cliente clienteF114E = new Cliente("Sophia Moore",236,50,cuenta28CL, "bronce",familiarC);
		

		Cliente clienteF115E = new Cliente("James Smith",5,"oro",familiarB);
		Cliente clienteF116E = new Cliente("David Brown",17,"oro",familiarB);
										
		Cliente clienteF117E = new Cliente("John Williams",15,"bronce",familiarB);
		Cliente clienteF118E = new Cliente("Michael Johnson",13,"bronce",familiarB);
		

		
		//Cementerio 1 Cenizas
		Urna urnaF1C11E=new Urna("Urnita de la Esperanza",cementerioF11Ce,70,1,"fija");
		Urna urnaF1C12E=new Urna("Urnita del Futuro",cementerioF11Ce,80,0,"fija");
		Urna urnaF1C13E=new Urna("default",cementerioF11Ce,50,0,"fija");
		
		Urna urnaF1C14E=new Urna("Urnita de la Esperanza",cementerioF11Ce,70,1,"fija");
		Urna urnaF1C15E=new Urna("Urnita del Futuro",cementerioF11Ce,80,0,"fija");
		
		//Cementerio 2 Cenizas
		Urna urnaF1C21E=new Urna("Urnita de la Sabiduría",cementerioF12Ce,70,1,"fija");
		Urna urnaF1C22E=new Urna("Urnita de la Justicia",cementerioF12Ce,80,0,"fija");
		Urna urnaF1C23E=new Urna("default",cementerioF12Ce,90,0,"fija");
		
		Urna urnaF1C24E=new Urna("Urnita de la Sabiduría",cementerioF12Ce,70,1,"fija");
		Urna urnaF1C25E=new Urna("Urnita de la Justicia",cementerioF12Ce,80,0,"fija");
		
		//Cementerio 3 Cenizas
		Urna urnaF1C31E=new Urna("Urnita de la Confianza",cementerioF13Ce,70,1,"fija");
		Urna urnaF1C32E=new Urna("Urnita del Progreso",cementerioF13Ce,80,0,"fija");
		Urna urnaF1C33E=new Urna("default",cementerioF13Ce,90,0,"fija");
		
		Urna urnaF1C34E=new Urna("Urnita de la Confianza",cementerioF13Ce,70,1,"fija");
		Urna urnaF1C35E=new Urna("Urnita del Progreso",cementerioF13Ce,80,0,"fija");
	
		
		//Cementerio 4 Cenizas
		Urna urnaF1C41E=new Urna("Urnita de la Verdadera Voz",cementerioF14Ce,70,1,"fija");
		Urna urnaF1C42E=new Urna("Urnita de la Decisión",cementerioF14Ce,80,0,"fija");
		Urna urnaF1C43E=new Urna("default",cementerioF14Ce,60,0,"fija");
		
		Urna urnaF1C44E=new Urna("Urnita de la Verdadera Voz",cementerioF14Ce,70,1,"fija");
		Urna urnaF1C45E=new Urna("Urnita de la Decisión",cementerioF14Ce,80,0,"fija");
		
		//Cementerio 5 Cenizas
		Urna urnaF1C51E=new Urna("Urnita del Cambio",cementerioF15Ce,70,1,"fija");
		Urna urnaF1C52E=new Urna("Urnita del Pueblo",cementerioF15Ce,80,0,"fija");
		Urna urnaF1C53E=new Urna("default",cementerioF15Ce,60,0,"ordinaria");
		
		Urna urnaF1C54E=new Urna("Urnita del Cambio",cementerioF15Ce,70,1,"fija");
		Urna urnaF1C55E=new Urna("Urnita del Pueblo",cementerioF15Ce,80,0,"fija");
	
		
		//Cementerio 6 Cenizas
		Urna urnaF1C61E=new Urna("Urnita de la Transparencia",cementerioF16Ce,70,1,"fija");
		Urna urnaF1C62E=new Urna("Urnita del Compromiso",cementerioF16Ce,80,0,"fija");
		Urna urnaF1C63E=new Urna("default",cementerioF16Ce,60,0,"ordinaria");
		
		Urna urnaF1C64E=new Urna("Urnita de la Transparencia",cementerioF16Ce,70,1,"fija");
		Urna urnaF1C65E=new Urna("Urnita del Compromiso",cementerioF16Ce,80,0,"fija");
	
		//Agregar clientes
		
		urnaF1C11E.agregarCliente(clienteF11E);
		urnaF1C12E.agregarCliente(clienteF12E);
		urnaF1C13E.agregarCliente(clienteF13E);
		
		urnaF1C21E.agregarCliente(clienteF14E);
		urnaF1C22E.agregarCliente(clienteF15E);
		urnaF1C23E.agregarCliente(clienteF16E);
		
		urnaF1C31E.agregarCliente(clienteF17E);
		urnaF1C32E.agregarCliente(clienteF18E);
		urnaF1C33E.agregarCliente(clienteF19E);
		
		urnaF1C41E.agregarCliente(clienteF110E);
		urnaF1C42E.agregarCliente(clienteF111E);
		urnaF1C43E.agregarCliente(clienteF112E);
		
		urnaF1C51E.agregarCliente(clienteF113E);
		urnaF1C52E.agregarCliente(clienteF114E);
		urnaF1C53E.agregarCliente(clienteF115E);
		
		urnaF1C61E.agregarCliente(clienteF116E);
		urnaF1C62E.agregarCliente(clienteF117E);
		urnaF1C63E.agregarCliente(clienteF118E);
	
		
		
		//Clientes para tumbas
		
		
		
		Cliente clienteF11ET = new Cliente("Ezequiel Andrade",123,30,cuenta29CL,"oro",familiarC);
		Cliente clienteF12ET = new Cliente("Damián Vargas",1234,25,cuenta30CL,"oro",familiarC);
		
		Cliente clienteF13ET = new Cliente("Octavio Salazar",1235,90,cuenta31CL,"plata",familiarB);
		Cliente clienteF14ET = new Cliente("Leonardo Paredes",1236,57,cuenta32CL,"plata",familiarB);
		
		Cliente clienteF15ET = new Cliente("Ulises Ortega",1237,21,cuenta33CL,"bronce",familiarC);
		Cliente clienteF16ET = new Cliente("Valeria Castro",1238,50,cuenta34CL,"bronce",familiarC);
	
		
		Cliente clienteF17ET = new Cliente("Delfina Méndez",5,"oro",familiarB);
		Cliente clienteF18ET = new Cliente("Mireya Delgado",17,"oro",familiarB);
		
		Cliente clienteF19ET = new Cliente("Renata Aguirre",15,"plata",familiarB);
		Cliente clienteF110ET = new Cliente("Alma Guzmán",13,"plata",familiarB);
		
		Cliente clienteF111ET = new Cliente("Leo Cruz",1235,90,cuenta35CL,"plata",familiarB);
		Cliente clienteF112ET = new Cliente("Luna Martínez",1236,57,cuenta36CL,"plata",familiarB);
		
		Cliente clienteF113ET = new Cliente("Lucas Moreno",1237,21,cuenta37CL,"bronce",familiarC);
		Cliente clienteF114ET = new Cliente("Sofía Rodríguez",1238,50,cuenta38CL,"bronce",familiarC);
	
		
		//Clientes F1 - Menores de edad
		Cliente clienteF115ET = new Cliente("Aitana Gómez",5,"oro",familiarB);
		Cliente clienteF116ET = new Cliente("Zoe García",17,"oro",familiarB);
		
		Cliente clienteF117ET = new Cliente("Ethan Ortega",15,"plata",familiarB);
		Cliente clienteF118ET = new Cliente("Dylan Mendoza",13,"plata",familiarB);
		
		
		
		
		//TUmbas
		
		//Cementerio 1
		Tumba tumbaF1C11E=new Tumba("Tumbita Lugar de Paz",cementerioF11Cu,1.70,1);
		Tumba tumbaF1C12E=new Tumba("Tumbita Descanso Eterno",cementerioF11Cu,1.50,0);
		Tumba tumbaF1C13E=new Tumba("default",cementerioF11Cu,1.70,0);
		
		Tumba tumbaF1C15E=new Tumba("Tumbita Lugar de Paz",cementerioF11Cu,1.70,1);
		Tumba tumbaF1C16E=new Tumba("Tumbita Descanso Eterno",cementerioF11Cu,1.50,0);
		
		//Cementerio 2
		Tumba tumbaF1C21E=new Tumba("Tumbita Siempre Recordado",cementerioF12Cu,1.70,1);
		Tumba tumbaF1C22E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF12Cu,1.60,0);
		Tumba tumbaF1C23E=new Tumba("default",cementerioF12Cu,1.60,0);
		
		Tumba tumbaF1C25E=new Tumba("Tumbita Siempre Recordado",cementerioF12Cu,1.70,1);
		Tumba tumbaF1C26E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF12Cu,1.60,0);
		
		//Cementerio 3
		Tumba tumbaF1C31E=new Tumba("Tumbita Lugar de Serenidad",cementerioF13Cu,1.70,1);
		Tumba tumbaF1C32E=new Tumba("Tumbita Eterna Paz",cementerioF13Cu,1.65,0);
		Tumba tumbaF1C33E=new Tumba("default",cementerioF13Cu,1.75,0);
		
		Tumba tumbaF1C34E=new Tumba("Tumbita Lugar de Serenidad",cementerioF13Cu,1.70,1);
		Tumba tumbaF1C35E=new Tumba("Tumbita Eterna Paz",cementerioF13Cu,1.65,0);

		//Cementerio 4
		Tumba tumbaF1C41E=new Tumba("Tumbita Un Alma Bella",cementerioF14Cu,1.70,1);
		Tumba tumbaF1C42E=new Tumba("Tumbita En Paz y Serenidad",cementerioF14Cu,1.70,0);
		Tumba tumbaF1C43E=new Tumba("default",cementerioF14Cu,1.55,0);
		
		Tumba tumbaF1C44E=new Tumba("Tumbita Un Alma Bella",cementerioF14Cu,1.70,1);
		Tumba tumbaF1C45E=new Tumba("Tumbita En Paz y Serenidad",cementerioF14Cu,1.70,0);
		
		//Cementerio 5
		Tumba tumbaF1C51E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF15Cu,1.70,1);
		Tumba tumbaF1C52E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF15Cu,1.50,0);
		Tumba tumbaF1C53E=new Tumba("default",cementerioF15Cu,1.55,0);
		
		Tumba tumbaF1C54E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF15Cu,1.70,1);
		Tumba tumbaF1C55E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF15Cu,1.50,0);
		
		//Cementerio 6
		Tumba tumbaF1C61E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF16Cu,1.70,1);
		Tumba tumbaF1C62E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF16Cu,1.60,0);
		Tumba tumbaF1C63E=new Tumba("default",cementerioF16Cu,1.60,0);
		
		Tumba tumbaF1C64E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF16Cu,1.70,1);
		Tumba tumbaF1C65E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF16Cu,1.60,0);
		
		//Agregar clientes a tumbas
		tumbaF1C11E.agregarCliente(clienteF11ET);
		tumbaF1C12E.agregarCliente(clienteF12ET);
		tumbaF1C13E.agregarCliente(clienteF13ET);
		
		tumbaF1C21E.agregarCliente(clienteF14ET);
		tumbaF1C22E.agregarCliente(clienteF15ET);
		tumbaF1C23E.agregarCliente(clienteF16ET);
		
		tumbaF1C31E.agregarCliente(clienteF17ET);
		tumbaF1C32E.agregarCliente(clienteF18ET);
		tumbaF1C33E.agregarCliente(clienteF19ET);
		
		tumbaF1C41E.agregarCliente(clienteF110ET);
		tumbaF1C42E.agregarCliente(clienteF111ET);
		tumbaF1C43E.agregarCliente(clienteF112ET);
		
		tumbaF1C51E.agregarCliente(clienteF113ET);
		tumbaF1C52E.agregarCliente(clienteF114ET);
		tumbaF1C53E.agregarCliente(clienteF115ET);
		
		tumbaF1C61E.agregarCliente(clienteF116ET);
		tumbaF1C62E.agregarCliente(clienteF117ET);
		tumbaF1C63E.agregarCliente(clienteF118ET);
		
		
		
		//Funeraria 2
		
		Cliente clienteF21E = new Cliente("Juan Pérez",3212,30,cuenta39CL,"oro",familiarA);
		Cliente clienteF22E = new Cliente("Carlos Fernández",3213,25,cuenta40CL,"oro",familiarA);
										
		Cliente clienteF23E = new Cliente("Miguel Rodríguez",3213,90,cuenta41CL,"plata",familiarC);
		Cliente clienteF24E = new Cliente("Dani Morales",3214,57,cuenta42CL,"plata",familiarC);
										
		Cliente clienteF25E = new Cliente("Pedro González",3215,50,cuenta43CL, "bronce",familiarB);
		Cliente clienteF26E = new Cliente("José Martínez",3215,30,cuenta44CL, "bronce",familiarA);
		

		Cliente clienteF27E = new Cliente("María López",5,"oro",familiarB);
		Cliente clienteF28E = new Cliente("Carmen García",17,"oro",familiarB);
										
		Cliente clienteF29E = new Cliente("Ana Torres",15,"bronce",familiarB);
		Cliente clienteF210E = new Cliente("Isabel Ramírez",13,"bronce",familiarB);
		

		Cliente clienteF211E = new Cliente("Laura Morales",233,90,cuenta45CL,"plata",familiarA);
		Cliente clienteF212E = new Cliente("Robert Jones",234,57,cuenta46CL,"plata",familiarC);
								
		Cliente clienteF213E = new Cliente("Olivia Miller",235,35,cuenta47CL, "bronce",familiarC);
		Cliente clienteF214E = new Cliente("Sophia Moore",236,50,cuenta48CL, "bronce",familiarC);
		

		Cliente clienteF215E = new Cliente("James Smith",5,"oro",familiarB);
		Cliente clienteF216E = new Cliente("David Brown",17,"oro",familiarB);
										
		Cliente clienteF217E = new Cliente("John Williams",15,"bronce",familiarB);
		Cliente clienteF218E = new Cliente("Michael Johnson",13,"bronce",familiarB);
		

		
		//Cementerio 1 Cenizas
		Urna urnaF2C11E=new Urna("Urnita de la Esperanza",cementerioF21Ce,70,1,"fija");
		Urna urnaF2C12E=new Urna("Urnita del Futuro",cementerioF21Ce,80,0,"fija");
		Urna urnaF2C13E=new Urna("default",cementerioF21Ce,60,0,"ordinaria");
		
		Urna urnaF2C14E=new Urna("Urnita de la Esperanza",cementerioF21Ce,70,1,"fija");
		Urna urnaF2C15E=new Urna("Urnita del Futuro",cementerioF21Ce,80,0,"fija");
		
		//Cementerio 2 Cenizas
		Urna urnaF2C21E=new Urna("Urnita de la Sabiduría",cementerioF22Ce,70,1,"fija");
		Urna urnaF2C22E=new Urna("Urnita de la Justicia",cementerioF22Ce,80,0,"ordinaria");
		Urna urnaF2C23E=new Urna("default",cementerioF22Ce,90,0,"fija");
	
		Urna urnaF2C24E=new Urna("Urnita de la Sabiduría",cementerioF22Ce,70,1,"fija");
		Urna urnaF2C25E=new Urna("Urnita de la Justicia",cementerioF22Ce,80,0,"ordinaria");
		
		//Cementerio 3 Cenizas
		Urna urnaF2C31E=new Urna("Urnita de la Confianza",cementerioF23Ce,70,1,"fija");
		Urna urnaF2C32E=new Urna("Urnita del Progreso",cementerioF23Ce,80,0,"fija");
		Urna urnaF2C33E=new Urna("default",cementerioF23Ce,90,1,"fija");
		
		Urna urnaF2C34E=new Urna("Urnita de la Confianza",cementerioF23Ce,70,1,"fija");
		Urna urnaF2C35E=new Urna("Urnita del Progreso",cementerioF23Ce,80,0,"fija");
		
		//Cementerio 4 Cenizas
		Urna urnaF2C41E=new Urna("Urnita de la Verdadera Voz",cementerioF24Ce,70,1,"fija");
		Urna urnaF2C42E=new Urna("Urnita de la Decisión",cementerioF24Ce,80,0,"fija");
		Urna urnaF2C43E=new Urna("default",cementerioF24Ce,60,1,"fija");
	
		Urna urnaF2C44E=new Urna("Urnita de la Verdadera Voz",cementerioF24Ce,70,1,"fija");
		Urna urnaF2C45E=new Urna("Urnita de la Decisión",cementerioF24Ce,80,0,"fija");
		
		//Cementerio 5 Cenizas
		Urna urnaF2C51E=new Urna("Urnita del Cambio",cementerioF25Ce,70,2,"fija");
		Urna urnaF2C52E=new Urna("Urnita del Pueblo",cementerioF25Ce,80,0,"fija");
		Urna urnaF2C53E=new Urna("default",cementerioF25Ce,60,1,"ordinaria");
	
		Urna urnaF2C54E=new Urna("Urnita del Cambio",cementerioF25Ce,70,2,"fija");
		Urna urnaF2C55E=new Urna("Urnita del Pueblo",cementerioF25Ce,80,0,"fija");
		
		//Cementerio 6 Cenizas
		Urna urnaF2C61E=new Urna("Urnita de la Transparencia",cementerioF26Ce,70,1,"fija");
		Urna urnaF2C62E=new Urna("Urnita del Compromiso",cementerioF26Ce,80,0,"fija");
		Urna urnaF2C63E=new Urna("default",cementerioF26Ce,60,0,"ordinaria");
		
		Urna urnaF2C64E=new Urna("Urnita de la Transparencia",cementerioF26Ce,70,1,"fija");
		Urna urnaF2C65E=new Urna("Urnita del Compromiso",cementerioF26Ce,80,0,"fija");
	
		//Agregar clientes
		
		urnaF2C11E.agregarCliente(clienteF21E);
		urnaF2C12E.agregarCliente(clienteF22E);
		urnaF2C13E.agregarCliente(clienteF23E);
		
		urnaF2C21E.agregarCliente(clienteF24E);
		urnaF2C22E.agregarCliente(clienteF25E);
		urnaF2C23E.agregarCliente(clienteF26E);
		
		urnaF2C31E.agregarCliente(clienteF27E);
		urnaF2C32E.agregarCliente(clienteF28E);
		urnaF2C33E.agregarCliente(clienteF29E);
		
		urnaF2C41E.agregarCliente(clienteF210E);
		urnaF2C42E.agregarCliente(clienteF211E);
		urnaF2C43E.agregarCliente(clienteF212E);
		
		urnaF2C51E.agregarCliente(clienteF213E);
		urnaF2C52E.agregarCliente(clienteF214E);
		urnaF2C53E.agregarCliente(clienteF215E);
		
		urnaF2C61E.agregarCliente(clienteF216E);
		urnaF2C62E.agregarCliente(clienteF217E);
		urnaF2C63E.agregarCliente(clienteF218E);
	
		
		
		//Clientes para tumbas
		
		
		
		Cliente clienteF21ET = new Cliente("Ezequiel Andrade",123,30,cuenta49CL,"oro",familiarC);
		Cliente clienteF22ET = new Cliente("Damián Vargas",1234,25,cuenta50CL,"oro",familiarC);
		
		Cliente clienteF23ET = new Cliente("Octavio Salazar",1235,90,cuenta51CL,"plata",familiarB);
		Cliente clienteF24ET = new Cliente("Leonardo Paredes",1236,57,cuenta52CL,"plata",familiarB);
		
		Cliente clienteF25ET = new Cliente("Ulises Ortega",1237,21,cuenta53CL,"bronce",familiarC);
		Cliente clienteF26ET = new Cliente("Valeria Castro",1238,50,cuenta54CL,"bronce",familiarC);
	
		
		Cliente clienteF27ET = new Cliente("Delfina Méndez",5,"oro",familiarB);
		Cliente clienteF28ET = new Cliente("Mireya Delgado",17,"oro",familiarB);
		
		Cliente clienteF29ET = new Cliente("Renata Aguirre",15,"plata",familiarB);
		Cliente clienteF210ET = new Cliente("Alma Guzmán",13,"plata",familiarB);
		
		Cliente clienteF211ET = new Cliente("Leo Cruz",1235,90,cuenta55CL,"plata",familiarB);
		Cliente clienteF212ET = new Cliente("Luna Martínez",1236,57,cuenta56CL,"plata",familiarB);
		
		Cliente clienteF213ET = new Cliente("Lucas Moreno",1237,21,cuenta57CL,"bronce",familiarC);
		Cliente clienteF214ET = new Cliente("Sofía Rodríguez",1238,50,cuenta58CL,"bronce",familiarC);
	
		
		//Clientes F1 - Menores de edad
		Cliente clienteF215ET = new Cliente("Aitana Gómez",5,"oro",familiarB);
		Cliente clienteF216ET = new Cliente("Zoe García",17,"oro",familiarB);
		
		Cliente clienteF217ET = new Cliente("Ethan Ortega",15,"plata",familiarB);
		Cliente clienteF218ET = new Cliente("Dylan Mendoza",13,"plata",familiarB);
		
		
		
		
		//TUmbas
		
		//Cementerio 1
		Tumba tumbaF2C11E=new Tumba("Tumbita Lugar de Paz",cementerioF21Cu,1.70,1);
		Tumba tumbaF2C12E=new Tumba("Tumbita Descanso Eterno",cementerioF21Cu,1.50,0);
		Tumba tumbaF2C13E=new Tumba("default",cementerioF21Cu,1.60,0);
		
		Tumba tumbaF2C14E=new Tumba("Tumbita Lugar de Paz",cementerioF21Cu,1.70,1);
		Tumba tumbaF2C15E=new Tumba("Tumbita Descanso Eterno",cementerioF21Cu,1.50,0);
		
		//Cementerio 2
		Tumba tumbaF2C21E=new Tumba("Tumbita Siempre Recordado",cementerioF22Cu,1.70,1);
		Tumba tumbaF2C22E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF22Cu,1.50,0);
		Tumba tumbaF2C23E=new Tumba("default",cementerioF22Cu,1.50,0);
		
		Tumba tumbaF2C24E=new Tumba("Tumbita Siempre Recordado",cementerioF22Cu,1.70,1);
		Tumba tumbaF2C25E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF22Cu,1.50,0);
		
		//Cementerio 3
		Tumba tumbaF2C31E=new Tumba("Tumbita Lugar de Serenidad",cementerioF23Cu,1.70,1);
		Tumba tumbaF2C32E=new Tumba("Tumbita Eterna Paz",cementerioF23Cu,1.50,0);
		Tumba tumbaF2C33E=new Tumba("default",cementerioF23Cu,1.60,0);
		
		Tumba tumbaF2C34E=new Tumba("Tumbita Lugar de Serenidad",cementerioF23Cu,1.70,1);
		Tumba tumbaF2C35E=new Tumba("Tumbita Eterna Paz",cementerioF23Cu,1.50,0);
		
		//Cementerio 4
		Tumba tumbaF2C41E=new Tumba("Tumbita Un Alma Bella",cementerioF24Cu,1.70,1);
		Tumba tumbaF2C42E=new Tumba("Tumbita En Paz y Serenidad",cementerioF24Cu,1.50,0);
		Tumba tumbaF2C43E=new Tumba("default",cementerioF24Cu,1.70,0);
		
		Tumba tumbaF2C44E=new Tumba("Tumbita Un Alma Bella",cementerioF24Cu,1.70,1);
		Tumba tumbaF2C45E=new Tumba("Tumbita En Paz y Serenidad",cementerioF24Cu,1.50,0);
		
		//Cementerio 5
		Tumba tumbaF2C51E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF25Cu,1.70,1);
		Tumba tumbaF2C52E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF25Cu,1.50,0);
		Tumba tumbaF2C53E=new Tumba("default",cementerioF25Cu,1.90,0);
		
		Tumba tumbaF2C54E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF25Cu,1.70,1);
		Tumba tumbaF2C55E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF25Cu,1.50,0);
		
		//Cementerio 6
		Tumba tumbaF2C61E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF26Cu,1.70,1);
		Tumba tumbaF2C62E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF26Cu,1.50,0);
		Tumba tumbaF2C63E=new Tumba("default",cementerioF26Cu,1.60,0);
		
		Tumba tumbaF2C64E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF26Cu,1.70,1);
		Tumba tumbaF2C65E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF26Cu,1.50,0);
		
		//Agregar clientes a tumbas
		tumbaF2C11E.agregarCliente(clienteF21ET);
		tumbaF2C12E.agregarCliente(clienteF22ET);
		tumbaF2C13E.agregarCliente(clienteF23ET);
		
		tumbaF2C21E.agregarCliente(clienteF24ET);
		tumbaF2C22E.agregarCliente(clienteF25ET);
		tumbaF2C23E.agregarCliente(clienteF26ET);
		
		tumbaF2C31E.agregarCliente(clienteF27ET);
		tumbaF2C32E.agregarCliente(clienteF28ET);
		tumbaF2C33E.agregarCliente(clienteF29ET);
		
		tumbaF2C41E.agregarCliente(clienteF210ET);
		tumbaF2C42E.agregarCliente(clienteF211ET);
		tumbaF2C43E.agregarCliente(clienteF212ET);
		
		tumbaF2C51E.agregarCliente(clienteF213ET);
		tumbaF2C52E.agregarCliente(clienteF214ET);
		tumbaF2C53E.agregarCliente(clienteF215ET);
		
		tumbaF2C61E.agregarCliente(clienteF216ET);
		tumbaF2C62E.agregarCliente(clienteF217ET);
		tumbaF2C63E.agregarCliente(clienteF218ET);
		
		
		//Funeraria 3
		
		Cliente clienteF31E = new Cliente("Juan Pérez",3212,30,cuenta59CL,"oro",familiarA);
		Cliente clienteF32E = new Cliente("Carlos Fernández",3213,25,cuenta60CL,"oro",familiarA);
										
		Cliente clienteF33E = new Cliente("Miguel Rodríguez",3213,90,cuenta61CL,"plata",familiarC);
		Cliente clienteF34E = new Cliente("Dani Morales",3214,57,cuenta62CL,"plata",familiarC);
										
		Cliente clienteF35E = new Cliente("Pedro González",3215,50,cuenta63CL, "bronce",familiarB);
		Cliente clienteF36E = new Cliente("José Martínez",3215,30,cuenta64CL, "bronce",familiarA);
		

		Cliente clienteF37E = new Cliente("María López",5,"oro",familiarB);
		Cliente clienteF38E = new Cliente("Carmen García",17,"oro",familiarB);
										
		Cliente clienteF39E = new Cliente("Ana Torres",15,"bronce",familiarB);
		Cliente clienteF310E = new Cliente("Isabel Ramírez",13,"bronce",familiarB);
		

		Cliente clienteF311E = new Cliente("Laura Morales",233,90,cuenta65CL,"plata",familiarA);
		Cliente clienteF312E = new Cliente("Robert Jones",234,57,cuenta66CL,"plata",familiarC);
								
		Cliente clienteF313E = new Cliente("Olivia Miller",235,35,cuenta67CL, "bronce",familiarC);
		Cliente clienteF314E = new Cliente("Sophia Moore",236,50,cuenta68CL, "bronce",familiarC);
		

		Cliente clienteF315E = new Cliente("James Smith",5,"oro",familiarB);
		Cliente clienteF316E = new Cliente("David Brown",17,"oro",familiarB);
										
		Cliente clienteF317E = new Cliente("John Williams",15,"bronce",familiarB);
		Cliente clienteF318E = new Cliente("Michael Johnson",13,"bronce",familiarB);
		

		
		//Cementerio 1 Cenizas
		Urna urnaF3C11E=new Urna("Urnita de la Esperanza",cementerioF31Ce,70,1,"fija");
		Urna urnaF3C12E=new Urna("Urnita del Futuro",cementerioF31Ce,80,0,"fija");
		Urna urnaF3C13E=new Urna("default",cementerioF31Ce,60,0,"ordinaria");
		
		Urna urnaF3C14E=new Urna("Urnita de la Esperanza",cementerioF31Ce,70,1,"fija");
		Urna urnaF3C15E=new Urna("Urnita del Futuro",cementerioF31Ce,80,0,"fija");
		
		//Cementerio 2 Cenizas
		Urna urnaF3C21E=new Urna("Urnita de la Sabiduría",cementerioF32Ce,70,1,"fija");
		Urna urnaF3C22E=new Urna("Urnita de la Justicia",cementerioF32Ce,80,0,"ordinaria");
		Urna urnaF3C23E=new Urna("default",cementerioF32Ce,90,0,"fija");
		
		Urna urnaF3C24E=new Urna("Urnita de la Sabiduría",cementerioF32Ce,70,1,"fija");
		Urna urnaF3C25E=new Urna("Urnita de la Justicia",cementerioF32Ce,80,0,"ordinaria");
		
		//Cementerio 3 Cenizas
		Urna urnaF3C31E=new Urna("Urnita de la Confianza",cementerioF33Ce,70,1,"fija");
		Urna urnaF3C32E=new Urna("Urnita del Progreso",cementerioF33Ce,80,0,"fija");
		Urna urnaF3C33E=new Urna("default",cementerioF33Ce,90,0,"fija");
	
		Urna urnaF3C34E=new Urna("Urnita de la Confianza",cementerioF33Ce,70,1,"fija");
		Urna urnaF3C35E=new Urna("Urnita del Progreso",cementerioF33Ce,80,0,"fija");
		
		//Cementerio 4 Cenizas
		Urna urnaF3C41E=new Urna("Urnita de la Verdadera Voz",cementerioF34Ce,70,1,"fija");
		Urna urnaF3C42E=new Urna("Urnita de la Decisión",cementerioF34Ce,80,0,"fija");
		Urna urnaF3C43E=new Urna("default",cementerioF34Ce,60,0,"fija");
	
		Urna urnaF3C44E=new Urna("Urnita de la Verdadera Voz",cementerioF34Ce,70,1,"fija");
		Urna urnaF3C45E=new Urna("Urnita de la Decisión",cementerioF34Ce,80,0,"fija");
		
		//Cementerio 5 Cenizas
		Urna urnaF3C51E=new Urna("Urnita del Cambio",cementerioF35Ce,70,1,"fija");
		Urna urnaF3C52E=new Urna("Urnita del Pueblo",cementerioF35Ce,80,0,"fija");
		Urna urnaF3C53E=new Urna("default",cementerioF35Ce,60,0,"ordinaria");
	
		Urna urnaF3C54E=new Urna("Urnita del Cambio",cementerioF35Ce,70,1,"fija");
		Urna urnaF3C55E=new Urna("Urnita del Pueblo",cementerioF35Ce,80,0,"fija");
		
		//Cementerio 6 Cenizas
		Urna urnaF3C61E=new Urna("Urnita de la Transparencia",cementerioF36Ce,70,1,"fija");
		Urna urnaF3C62E=new Urna("Urnita del Compromiso",cementerioF36Ce,80,0,"fija");
		Urna urnaF3C63E=new Urna("default",cementerioF36Ce,60,0,"ordinaria");
		
		Urna urnaF3C64E=new Urna("Urnita de la Transparencia",cementerioF36Ce,70,1,"fija");
		Urna urnaF3C65E=new Urna("Urnita del Compromiso",cementerioF36Ce,80,0,"fija");
	
		//Agregar clientes
		
		urnaF3C11E.agregarCliente(clienteF31E);
		urnaF3C12E.agregarCliente(clienteF32E);
		urnaF3C13E.agregarCliente(clienteF33E);
		
		urnaF3C21E.agregarCliente(clienteF34E);
		urnaF3C22E.agregarCliente(clienteF35E);
		urnaF3C23E.agregarCliente(clienteF36E);
		
		urnaF3C31E.agregarCliente(clienteF37E);
		urnaF3C32E.agregarCliente(clienteF38E);
		urnaF3C33E.agregarCliente(clienteF39E);
		
		urnaF3C41E.agregarCliente(clienteF310E);
		urnaF3C42E.agregarCliente(clienteF311E);
		urnaF3C43E.agregarCliente(clienteF312E);
		
		urnaF3C51E.agregarCliente(clienteF313E);
		urnaF3C52E.agregarCliente(clienteF314E);
		urnaF3C53E.agregarCliente(clienteF315E);
		
		urnaF3C61E.agregarCliente(clienteF316E);
		urnaF3C62E.agregarCliente(clienteF317E);
		urnaF3C63E.agregarCliente(clienteF318E);
	
		
		
		//Clientes para tumbas
		
		
		
		Cliente clienteF31ET = new Cliente("Ezequiel Andrade",123,30,cuenta69CL,"oro",familiarC);
		Cliente clienteF32ET = new Cliente("Damián Vargas",1234,25,cuenta70CL,"oro",familiarC);
		
		Cliente clienteF33ET = new Cliente("Octavio Salazar",1235,90,cuenta71CL,"plata",familiarB);
		Cliente clienteF34ET = new Cliente("Leonardo Paredes",1236,57,cuenta72CL,"plata",familiarB);
		
		Cliente clienteF35ET = new Cliente("Ulises Ortega",1237,21,cuenta73CL,"bronce",familiarC);
		Cliente clienteF36ET = new Cliente("Valeria Castro",1238,50,cuenta74CL,"bronce",familiarC);
	
		
		Cliente clienteF37ET = new Cliente("Delfina Méndez",5,"oro",familiarB);
		Cliente clienteF38ET = new Cliente("Mireya Delgado",17,"oro",familiarB);
		
		Cliente clienteF39ET = new Cliente("Renata Aguirre",15,"plata",familiarB);
		Cliente clienteF310ET = new Cliente("Alma Guzmán",13,"plata",familiarB);
		
		Cliente clienteF311ET = new Cliente("Leo Cruz",1235,90,cuenta75CL,"plata",familiarB);
		Cliente clienteF312ET = new Cliente("Luna Martínez",1236,57,cuenta76CL,"plata",familiarB);
		
		Cliente clienteF313ET = new Cliente("Lucas Moreno",1237,21,cuenta77CL,"bronce",familiarC);
		Cliente clienteF314ET = new Cliente("Sofía Rodríguez",1238,50,cuenta78CL,"bronce",familiarC);
	
		
		//Clientes F3 - Menores de edad
		Cliente clienteF315ET = new Cliente("Aitana Gómez",5,"oro",familiarB);
		Cliente clienteF316ET = new Cliente("Zoe García",17,"oro",familiarB);
		
		Cliente clienteF317ET = new Cliente("Ethan Ortega",15,"plata",familiarB);
		Cliente clienteF318ET = new Cliente("Dylan Mendoza",13,"plata",familiarB);
		
		
		
		
		//TUmbas
		
		//Cementerio 1
		Tumba tumbaF3C11E=new Tumba("Tumbita Lugar de Paz",cementerioF31Cu,1.70,1);
		Tumba tumbaF3C12E=new Tumba("Tumbita Descanso Eterno",cementerioF31Cu,1.50,0);
		Tumba tumbaF3C13E=new Tumba("default",cementerioF31Cu,1.80,0);
		
		Tumba tumbaF3C14E=new Tumba("Tumbita Lugar de Paz",cementerioF31Cu,1.70,1);
		Tumba tumbaF3C15E=new Tumba("Tumbita Descanso Eterno",cementerioF31Cu,1.60,0);
		
		//Cementerio 2
		Tumba tumbaF3C21E=new Tumba("Tumbita Siempre Recordado",cementerioF32Cu,1.70,1);
		Tumba tumbaF3C22E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF32Cu,1.65,0);
		Tumba tumbaF3C23E=new Tumba("default",cementerioF32Cu,1.70,0);
		
		Tumba tumbaF3C24E=new Tumba("Tumbita Siempre Recordado",cementerioF32Cu,1.70,1);
		Tumba tumbaF3C25E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF32Cu,1.65,0);
		
		//Cementerio 3
		Tumba tumbaF3C31E=new Tumba("Tumbita Lugar de Serenidad",cementerioF33Cu,1.70,1);
		Tumba tumbaF3C32E=new Tumba("Tumbita Eterna Paz",cementerioF33Cu,1.60,0);
		Tumba tumbaF3C33E=new Tumba("default",cementerioF33Cu,1.60,0);

		Tumba tumbaF3C34E=new Tumba("Tumbita Lugar de Serenidad",cementerioF33Cu,1.70,1);
		Tumba tumbaF3C35E=new Tumba("Tumbita Eterna Paz",cementerioF33Cu,1.60,0);
		
		//Cementerio 4
		Tumba tumbaF3C41E=new Tumba("Tumbita Un Alma Bella",cementerioF34Cu,1.70,1);
		Tumba tumbaF3C42E=new Tumba("Tumbita En Paz y Serenidad",cementerioF34Cu,1.70,0);
		Tumba tumbaF3C43E=new Tumba("default",cementerioF34Cu,1.50,0);
		
		Tumba tumbaF3C44E=new Tumba("Tumbita Un Alma Bella",cementerioF34Cu,1.70,1);
		Tumba tumbaF3C45E=new Tumba("Tumbita En Paz y Serenidad",cementerioF34Cu,1.70,0);
		
		//Cementerio 5
		Tumba tumbaF3C51E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF35Cu,1.70,1);
		Tumba tumbaF3C52E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF35Cu,1.85,0);
		Tumba tumbaF3C53E=new Tumba("default",cementerioF35Cu,1.70,0);
		
		Tumba tumbaF3C54E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF35Cu,1.70,1);
		Tumba tumbaF3C55E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF35Cu,1.65,0);
		
		//Cementerio 6
		Tumba tumbaF3C61E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF36Cu,1.70,1);
		Tumba tumbaF3C62E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF36Cu,1.65,0);
		Tumba tumbaF3C63E=new Tumba("default",cementerioF36Cu,1.60,0);
		
		Tumba tumbaF3C64E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF36Cu,1.70,1);
		Tumba tumbaF3C65E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF36Cu,1.90,0);
		
		//Agregar clientes a tumbas
		tumbaF3C11E.agregarCliente(clienteF31ET);
		tumbaF3C12E.agregarCliente(clienteF32ET);
		tumbaF3C13E.agregarCliente(clienteF33ET);
		
		tumbaF3C21E.agregarCliente(clienteF34ET);
		tumbaF3C22E.agregarCliente(clienteF35ET);
		tumbaF3C23E.agregarCliente(clienteF36ET);
		
		tumbaF3C31E.agregarCliente(clienteF37ET);
		tumbaF3C32E.agregarCliente(clienteF38ET);
		tumbaF3C33E.agregarCliente(clienteF39ET);
		
		tumbaF3C41E.agregarCliente(clienteF310ET);
		tumbaF3C42E.agregarCliente(clienteF311ET);
		tumbaF3C43E.agregarCliente(clienteF312ET);
		
		tumbaF3C51E.agregarCliente(clienteF313ET);
		tumbaF3C52E.agregarCliente(clienteF314ET);
		tumbaF3C53E.agregarCliente(clienteF315ET);
		
		tumbaF3C61E.agregarCliente(clienteF316ET);
		tumbaF3C62E.agregarCliente(clienteF317ET);
		tumbaF3C63E.agregarCliente(clienteF318ET);
		
		//Trabajos hechos
		empleadoF11S.setTrabajosHechos(0);
		empleadoF12S.setTrabajosHechos(2);
		empleadoF13S.setTrabajosHechos(4);
		empleadoF14S.setTrabajosHechos(6);
		empleadoF15S.setTrabajosHechos(8);
		empleadoF11C.setTrabajosHechos(10);
		empleadoF12C.setTrabajosHechos(1);
		empleadoF13C.setTrabajosHechos(5);
		empleadoF14C.setTrabajosHechos(7);
		empleadoF15C.setTrabajosHechos(9);
		empleadoF21S.setTrabajosHechos(0);
		empleadoF22S.setTrabajosHechos(2);
		empleadoF23S.setTrabajosHechos(4);
		empleadoF24S.setTrabajosHechos(6);
		empleadoF25S.setTrabajosHechos(8);
		empleadoF21C.setTrabajosHechos(10);
		empleadoF22C.setTrabajosHechos(1);
		empleadoF23C.setTrabajosHechos(5);
		empleadoF24C.setTrabajosHechos(7);
		empleadoF25C.setTrabajosHechos(9);
		empleadoF31S.setTrabajosHechos(0);
		empleadoF32S.setTrabajosHechos(2);
		empleadoF33S.setTrabajosHechos(4);
		empleadoF34S.setTrabajosHechos(6);
		empleadoF35S.setTrabajosHechos(8);
		empleadoF31C.setTrabajosHechos(10);
		empleadoF32C.setTrabajosHechos(1);
		empleadoF33C.setTrabajosHechos(5);
		empleadoF34C.setTrabajosHechos(7);
		empleadoF35C.setTrabajosHechos(9);
		
		
		//objetos de clase inventario
		
		//Cuenta jefe
    	CuentaBancaria cuentajefe = new CuentaBancaria(123456, "jefe1", 100000000, "BBVA");
    	
		//cuenta bancaria establecimientos
    	CuentaBancaria cuentalocal0 = new CuentaBancaria(123456, "local1 productos", 100000000, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal1= new CuentaBancaria(123457L, "local2 productos", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal2= new CuentaBancaria(123458L, "local3 productos", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal3= new CuentaBancaria(123459L, "local4 productos", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal4= new CuentaBancaria(123460L, "local5 Vehiculos", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal5= new CuentaBancaria(123461L, "local6 Vehiculos", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal6= new CuentaBancaria(123462L, "local7 Vehiculos", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal7= new CuentaBancaria(123463L, "local8 Empleados", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal8= new CuentaBancaria(123464L, "local9 Empleados", 100000000.00, "BANCOLOMBIA");
    	CuentaBancaria cuentalocal9= new CuentaBancaria(123465L, "local10 Empleados", 100000000.00, "BANCOLOMBIA");
    	
    	//Jefes establecimientos
    	//public Empleado(String nombre, long CC, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario
    	Empleado jefe1 = new Empleado("Fernando López",cuentajefe , "mañana", "JEFE1", 5500.00, null);
    	Empleado jefe2 = new Empleado("María García",  cuentajefe, "tarde", "JEFE2", 5600.00, null);
    	Empleado jefe3 = new Empleado("Carlos Pérez",  cuentajefe, "noche", "JEFE3", 5700.00, null);
    	Empleado jefe4 = new Empleado("Ana Martínez",  cuentajefe, "mañana", "JEFE4", 5800.00, null);
    	Empleado jefe5 = new Empleado("Luis Rodríguez",  cuentajefe, "tarde", "JEFE5", 5900.00, null);
    	Empleado jefe6 = new Empleado("Elena Fernández",  cuentajefe, "noche", "JEFE6", 6000.00, null);
    	Empleado jefe7 = new Empleado("Jorge Gómez",  cuentajefe, "mañana", "JEFE7", 6100.00, null);
    	Empleado jefe8 = new Empleado("Laura Díaz",  cuentajefe, "tarde", "JEFE8", 6200.00, null);
    	Empleado jefe9 = new Empleado("Pedro Ramírez",  cuentajefe, "noche", "JEFE9", 6300.00, null);
    	Empleado jefe10 = new Empleado("Sofía Torres",  cuentajefe, "mañana", "JEFE10", 6400.00, null);
    	
    	
    	//Establecimientos
    	//public Establecimiento(String nombre, int capacidad, CuentaBancaria cuentaCorriente, Empleado jefe,double calificacion)
    	Establecimiento local1 = new Establecimiento("D1 productos",500,cuentalocal0,jefe1,5);
    	Establecimiento local2 = new Establecimiento("ARA productos",500,cuentalocal1,jefe2,5);
    	Establecimiento local3 = new Establecimiento("JUSTO Y BUENO productos",500,cuentalocal2,jefe3,5);
    	Establecimiento local4 = new Establecimiento("HOMECENTER productos",500,cuentalocal3,jefe4,5);
    	funeraria1.agregarProveedor(local1);
    	funeraria1.agregarProveedor(local2);
    	funeraria1.agregarProveedor(local3);
    	funeraria1.agregarProveedor(local4);
    	
    	funeraria2.agregarProveedor(local1);
    	funeraria2.agregarProveedor(local2);
    	funeraria2.agregarProveedor(local3);
    	funeraria2.agregarProveedor(local4);
    	
    	funeraria3.agregarProveedor(local1);
    	funeraria3.agregarProveedor(local2);
    	funeraria3.agregarProveedor(local3);
    	funeraria3.agregarProveedor(local4);
    	
    	
    	Establecimiento local5 = new Establecimiento("Yamaha Vehiculos",500,cuentalocal4,jefe5,5);
    	Establecimiento local6 = new Establecimiento("BMW Vehiculos",500,cuentalocal5,jefe6,5);
    	Establecimiento local7 = new Establecimiento("AUDI Vehiculos",500,cuentalocal6,jefe7,5);
    	funeraria1.agregarProveedorVehiculo(local5);
    	funeraria1.agregarProveedorVehiculo(local6);
    	funeraria1.agregarProveedorVehiculo(local7);
    	
    	funeraria2.agregarProveedorVehiculo(local5);
    	funeraria2.agregarProveedorVehiculo(local6);
    	funeraria2.agregarProveedorVehiculo(local7);
    	
    	funeraria3.agregarProveedorVehiculo(local5);
    	funeraria3.agregarProveedorVehiculo(local6);
    	funeraria3.agregarProveedorVehiculo(local7);
    	
    	Establecimiento local8 = new Establecimiento("COMFAMA Empleados",500,cuentalocal7,jefe8,5);
    	Establecimiento local9 = new Establecimiento("SENA Empleados",500,cuentalocal8,jefe9,5);
    	Establecimiento local10 = new Establecimiento("CONTRATACIONES S.A Empleados",500,cuentalocal9,jefe10,5);
    	funeraria1.agregarProveedorEmpleado(local8);
    	funeraria1.agregarProveedorEmpleado(local9);
    	funeraria1.agregarProveedorEmpleado(local10);
    	
    	funeraria2.agregarProveedorEmpleado(local8);
    	funeraria2.agregarProveedorEmpleado(local9);
    	funeraria2.agregarProveedorEmpleado(local10);
    	
    	funeraria3.agregarProveedorEmpleado(local8);
    	funeraria3.agregarProveedorEmpleado(local9);
    	funeraria3.agregarProveedorEmpleado(local10);
    	
    	//Empleados para funeraria
    	// Creación de las cuentas bancarias para los empleados
    	CuentaBancaria empleadocuenta1 = new CuentaBancaria(6060606060L, "Gabriel Soto", 4800.00, "BBVA");
    	CuentaBancaria empleadocuenta2 = new CuentaBancaria(7070707070L, "Lucía Rivas", 5300.00, "BANCOLOMBIA");
    	CuentaBancaria empleadocuenta3 = new CuentaBancaria(8080808080L, "Carlos Méndez", 4600.00, "BANCO DE BOGOTÁ");
    	CuentaBancaria empleadocuenta4 = new CuentaBancaria(9090909090L, "Ana Vega", 5000.00, "BANCO DE OCCIDENTE");
    	CuentaBancaria empleadocuenta5 = new CuentaBancaria(1010101010L, "Felipe Cruz", 5100.00, "DAVIVIENDA");
    	CuentaBancaria empleadocuenta6 = new CuentaBancaria(1111111111L, "Marta Herrera", 5200.00, "BBVA");
    	CuentaBancaria empleadocuenta7 = new CuentaBancaria(1212121212L, "Luis Morales", 4700.00, "BANCOLOMBIA");
    	CuentaBancaria empleadocuenta8 = new CuentaBancaria(1313131313L, "Elena Castillo", 5400.00, "BANCO DE BOGOTÁ");
    	CuentaBancaria empleadocuenta9 = new CuentaBancaria(1414141414L, "Diego Torres", 4800.00, "BANCO DE OCCIDENTE");
    	CuentaBancaria empleadocuenta10 = new CuentaBancaria(1515151515L, "Isabel Sánchez", 5000.00, "DAVIVIENDA");
    	CuentaBancaria empleadocuenta11 = new CuentaBancaria(1616161616L, "Miguel Ortega", 5300.00, "BBVA");
    	CuentaBancaria empleadocuenta12 = new CuentaBancaria(1717171717L, "Claudia Jiménez", 5100.00, "BANCOLOMBIA");
    	CuentaBancaria empleadocuenta13 = new CuentaBancaria(1818181818L, "Javier López", 5200.00, "BANCO DE BOGOTÁ");
    	CuentaBancaria empleadocuenta14 = new CuentaBancaria(1919191919L, "Rosa Pérez", 4700.00, "BANCO DE OCCIDENTE");
    	CuentaBancaria empleadocuenta15 = new CuentaBancaria(2020202020L, "Alberto Ramírez", 5400.00, "DAVIVIENDA");

        // Creación de los empleados de establecimientos
    	//public Empleado(String nombre, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario,Funeraria funeraria)
       
        Empleado empleado1 = new Empleado("Gabriel Soto", empleadocuenta1, "mañana", "sepulturero", 3500.00, null);
        Empleado empleado2 = new Empleado("Lucía Rivas",  empleadocuenta2, "tarde", "cremador", 3700.00, null);
        Empleado empleado3 = new Empleado("Carlos Méndez",  empleadocuenta3, "noche", "padre", 3600.00, null);
        Empleado empleado4 = new Empleado("Ana Vega",  empleadocuenta4, "mañana", "sepulturero", 3400.00, null);
        Empleado empleado5 = new Empleado("Felipe Cruz",  empleadocuenta5, "tarde", "cremador", 3500.00, null);
        local8.agregarEmpleado(empleado1);
        local8.agregarEmpleado(empleado2);
        local8.agregarEmpleado(empleado3);
        local8.agregarEmpleado(empleado4);
        local8.agregarEmpleado(empleado5);
        
        Empleado empleado6 = new Empleado("Marta Herrera",  empleadocuenta6, "noche", "padre", 3700.00, funeraria1);
        Empleado empleado7 = new Empleado("Luis Morales",  empleadocuenta7, "mañana", "sepulturero", 3600.00, funeraria1);
        Empleado empleado8 = new Empleado("Elena Castillo",  empleadocuenta8, "tarde", "cremador", 3800.00, funeraria2);
        Empleado empleado9 = new Empleado("Diego Torres",  empleadocuenta9, "noche", "padre", 3500.00, funeraria2);
        Empleado empleado10 = new Empleado("Isabel Sánchez",  empleadocuenta10, "mañana", "sepulturero", 3400.00, funeraria3);
        local9.agregarEmpleado(empleado6);
        local9.agregarEmpleado(empleado8);
        local9.agregarEmpleado(empleado7);
        local9.agregarEmpleado(empleado9);
        local9.agregarEmpleado(empleado10);

        
        Empleado empleado11 = new Empleado("Miguel Ortega",  empleadocuenta11, "tarde", "cremador", 3700.00, funeraria3);
        Empleado empleado12 = new Empleado("Claudia Jiménez",  empleadocuenta12, "noche", "padre", 3600.00, funeraria1);
        Empleado empleado13 = new Empleado("Javier López", empleadocuenta13, "mañana", "sepulturero", 3800.00, funeraria1);
        Empleado empleado14 = new Empleado("Rosa Pérez", empleadocuenta14, "tarde", "cremador", 3500.00, funeraria2);
        Empleado empleado15 = new Empleado("Alberto Ramírez",  empleadocuenta15, "noche", "padre", 3900.00, funeraria2);
        local10.agregarEmpleado(empleado11);
        local10.agregarEmpleado(empleado12);
        local10.agregarEmpleado(empleado13);
        local10.agregarEmpleado(empleado14);
        local10.agregarEmpleado(empleado15);

        
        //vehiculos provedores.
        //public Vehiculo(TipoVehiculo tipoVehiculo,Funeraria funeraria,String color, String placa, int Precio, int Capacidad)
     // Creación de 24 vehículos, 3 por cada tipo
        Vehiculo berlina1 = new Vehiculo(TipoVehiculo.BERLINA, funeraria1, "Negro", "ABC123", 70000, 4);
        Vehiculo berlina2 = new Vehiculo(TipoVehiculo.BERLINA, funeraria2, "Gris", "DEF456", 72000, 4);
        Vehiculo berlina3 = new Vehiculo(TipoVehiculo.BERLINA, funeraria3, "Azul", "GHI789", 71000, 4);
        Vehiculo carroza1 = new Vehiculo(TipoVehiculo.CARROZA, funeraria1, "Negro", "JKL012", 150000, 1);
        Vehiculo carroza2 = new Vehiculo(TipoVehiculo.CARROZA,funeraria2, "Blanco", "MNO345", 152000, 1);
        Vehiculo carroza3 = new Vehiculo(TipoVehiculo.CARROZA, funeraria3, "Plata", "PQR678", 151000, 1);
        Vehiculo faeton1 = new Vehiculo(TipoVehiculo.FAETON, funeraria1, "Negro", "STU901", 120000, 4);
        Vehiculo faeton2 = new Vehiculo(TipoVehiculo.FAETON, funeraria2, "Blanco", "VWX234", 122000, 4);
        local5.agregarVehiculoEnVenta(berlina1);
        local5.agregarVehiculoEnVenta(berlina2);
        local5.agregarVehiculoEnVenta(berlina3);
        local5.agregarVehiculoEnVenta(carroza1);
        local5.agregarVehiculoEnVenta(carroza2);
        local5.agregarVehiculoEnVenta(carroza3);
        local5.agregarVehiculoEnVenta(faeton1);
        local5.agregarVehiculoEnVenta(faeton1);
        
        
        Vehiculo faeton3 = new Vehiculo(TipoVehiculo.FAETON, funeraria3, "Rojo", "YZA567", 121000, 4);
        Vehiculo cocheFunebre1 = new Vehiculo(TipoVehiculo.COCHEFUNEBRE, funeraria1, "Negro", "BCD890", 75000, 8);
        Vehiculo cocheFunebre2 = new Vehiculo(TipoVehiculo.COCHEFUNEBRE, funeraria2, "Blanco", "EFG123", 76000, 8);
        Vehiculo cocheFunebre3 = new Vehiculo(TipoVehiculo.COCHEFUNEBRE, funeraria3, "Plata", "HIJ456", 75500, 8);
        Vehiculo bus1 = new Vehiculo(TipoVehiculo.BUS, funeraria1, "Amarillo", "KLM789", 50000, 6);
        Vehiculo bus2 = new Vehiculo(TipoVehiculo.BUS, funeraria2, "Verde", "NOP012", 51000, 6);
        Vehiculo bus3 = new Vehiculo(TipoVehiculo.BUS, funeraria3, "Azul", "QRS345", 50500, 6);
        Vehiculo cocheRespeto1 = new Vehiculo(TipoVehiculo.COCHERESPETO, funeraria1, "Negro", "TUV678", 80000, 1);
        local6.agregarVehiculoEnVenta(faeton3);
        local6.agregarVehiculoEnVenta(cocheFunebre1);
        local6.agregarVehiculoEnVenta(cocheFunebre2);
        local6.agregarVehiculoEnVenta(cocheFunebre3);
        local6.agregarVehiculoEnVenta(bus1);
        local6.agregarVehiculoEnVenta(bus2);
        local6.agregarVehiculoEnVenta(bus3);
        local6.agregarVehiculoEnVenta(cocheRespeto1);
        
        
        Vehiculo cocheRespeto2 = new Vehiculo(TipoVehiculo.COCHERESPETO, funeraria2, "Gris", "WXY901", 82000, 1);
        Vehiculo cocheRespeto3 = new Vehiculo(TipoVehiculo.COCHERESPETO, funeraria3, "Blanco", "ZAB234", 81000, 1);
        Vehiculo cupe1 = new Vehiculo(TipoVehiculo.CUPE, funeraria1, "Rojo", "CDE567", 65000, 2);
        Vehiculo cupe2 = new Vehiculo(TipoVehiculo.CUPE, funeraria2, "Negro", "FGH890", 66000, 2);
        Vehiculo cupe3 = new Vehiculo(TipoVehiculo.CUPE, funeraria3, "Azul", "IJK123", 65500, 2);
        Vehiculo camion1 = new Vehiculo(TipoVehiculo.CAMION, funeraria1, "Blanco", "LMN456", 69000, 5);
        Vehiculo camion2 = new Vehiculo(TipoVehiculo.CAMION, funeraria2, "Azul", "OPQ789", 70000, 5);
        Vehiculo camion3 = new Vehiculo(TipoVehiculo.CAMION, funeraria3, "Negro", "RST012", 69500, 5);
        local7.agregarVehiculoEnVenta(cocheRespeto2);
        local7.agregarVehiculoEnVenta(cocheRespeto3);
        local7.agregarVehiculoEnVenta(cupe1);
        local7.agregarVehiculoEnVenta(cupe2);
        local7.agregarVehiculoEnVenta(cupe3);
        local7.agregarVehiculoEnVenta(camion1);
        local7.agregarVehiculoEnVenta(camion2);
        local7.agregarVehiculoEnVenta(camion3);
        
        
        //productos que tiene la Funeraria 1
        //public Producto(String nombre, double precio, int cantidad, int cantidadVendida)
        Producto trajesCaballeroF1 = new Producto("Trajes de caballero", 998.0, 22, 10);
        Producto vestidosDamaF1 = new Producto("Vestidos de dama", 1200.0, 6, 20);
        Producto recuerdoF1 = new Producto("Medalla conmemorativa", 100.0, 5, 12); // Menos de 10 unidades
        Producto recuerdo2F1 = new Producto("Joyas conmemorativas", 250.0, 0, 15); // Sin stock
        Producto recuerdo3F1 = new Producto("Álbumes de fotos", 300.0, 30, 18); // Más stock
        Producto recuerdo4F1 = new Producto("Portarretratos digitales", 120.0, 0, 45); // Sin stock
        Producto velasRojasF1 = new Producto("Velas rojas", 300.0, 7, 5); // Menos de 10 unidades
        Producto velasBlancasF1 = new Producto("Velas blancas", 300.0, 20, 30); // Más stock
        funeraria1.agregarProducto(trajesCaballeroF1);
        funeraria1.agregarProducto(vestidosDamaF1);
        funeraria1.agregarProducto(recuerdoF1);
        funeraria1.agregarProducto(recuerdo2F1);
        funeraria1.agregarProducto(recuerdo3F1);
        funeraria1.agregarProducto(recuerdo4F1);
        funeraria1.agregarProducto(velasRojasF1);
        funeraria1.agregarProducto(velasBlancasF1);
        
      //productos que tiene la Funeraria 2
        //public Producto(String nombre, double precio, int cantidad, int cantidadVendida)
        Producto trajesCaballeroF2 = new Producto("Trajes de caballero", 998.0, 6, 25); // Menos de 10 unidades
        Producto vestidosDamaF2 = new Producto("Vestidos de dama", 1200.0, 18, 9); 
        Producto recuerdoF2 = new Producto("Medalla conmemorativa", 100.0, 10, 20);
        Producto recuerdo2F2 = new Producto("Joyas conmemorativas", 250.0, 25, 10); // Más stock
        Producto recuerdo3F2 = new Producto("Álbumes de fotos", 300.0, 9, 12); // Menos de 10 unidades
        Producto recuerdo4F2 = new Producto("Portarretratos digitales", 120.0, 15, 45);
        Producto velasRojasF2 = new Producto("Velas rojas", 300.0, 7, 20); // Menos de 10 unidades
        Producto velasBlancasF2 = new Producto("Velas blancas", 300.0, 0, 40); // Sin stock
        funeraria2.agregarProducto(trajesCaballeroF2);
        funeraria2.agregarProducto(vestidosDamaF2);
        funeraria2.agregarProducto(recuerdoF2);
        funeraria2.agregarProducto(recuerdo2F2);
        funeraria2.agregarProducto(recuerdo3F2);
        funeraria2.agregarProducto(recuerdo4F2);
        funeraria2.agregarProducto(velasRojasF2);
        funeraria2.agregarProducto(velasBlancasF2);
        
      //productos que tiene la Funeraria 3
        //public Producto(String nombre, double precio, int cantidad, int cantidadVendida)
        Producto trajesCaballeroF3 = new Producto("Trajes de caballero", 998.0, 6, 30); // Menos de 10 unidades
        Producto vestidosDamaF3 = new Producto("Vestidos de dama", 1200.0, 8, 12); // Menos de 10 unidades
        Producto recuerdoF3 = new Producto("Medalla conmemorativa", 100.0, 10, 25);
        Producto recuerdo2F3 = new Producto("Joyas conmemorativas", 250.0, 0, 18); // Sin stock
        Producto recuerdo3F3 = new Producto("Álbumes de fotos", 300.0, 30, 15);
        Producto recuerdo4F3 = new Producto("Portarretratos digitales", 120.0, 35, 35); // Más stock
        Producto velasRojasF3 = new Producto("Velas rojas", 300.0, 7, 10); // Menos de 10 unidades
        Producto velasBlancasF3 = new Producto("Velas blancas", 300.0, 0, 50); // Sin stock
        funeraria3.agregarProducto(trajesCaballeroF3);
        funeraria3.agregarProducto(vestidosDamaF3);
        funeraria3.agregarProducto(recuerdoF3);
        funeraria3.agregarProducto(recuerdo2F3);
        funeraria3.agregarProducto(recuerdo3F3);
        funeraria3.agregarProducto(recuerdo4F3);
        funeraria3.agregarProducto(velasRojasF3);
        funeraria3.agregarProducto(velasBlancasF3);
       
        
        //Facturas para funeraria 1
        Factura facturaF1_1 = new Factura("Trajes de caballero");
        Factura facturaF1_2 = new Factura("Vestidos de dama");
        Factura facturaF1_3 = new Factura("Medalla conmemorativa");
        Factura facturaF1_4 = new Factura("Joyas conmemorativas");
        Factura facturaF1_5 = new Factura("Álbumes de fotos");
        Factura facturaF1_6 = new Factura("Portarretratos digitales");
        Factura facturaF1_7 = new Factura("Velas rojas");
        Factura facturaF1_8 = new Factura("Velas blancas");
        funeraria1.agregarFacturapagada(facturaF1_1);
        funeraria1.agregarFacturapagada(facturaF1_2);
        funeraria1.agregarFacturapagada(facturaF1_3);
        funeraria1.agregarFacturapagada(facturaF1_4);
        funeraria1.agregarFacturapagada(facturaF1_5);
        funeraria1.agregarFacturapagada(facturaF1_6);
        funeraria1.agregarFacturapagada(facturaF1_7);
        funeraria1.agregarFacturapagada(facturaF1_8);

        
        // facturas para funeraria 2
        Factura facturaF2_1 = new Factura("Trajes de caballero");
        Factura facturaF2_2 = new Factura("Vestidos de dama");
        Factura facturaF2_3 = new Factura("Medalla conmemorativa");
        Factura facturaF2_4 = new Factura("Joyas conmemorativas");
        Factura facturaF2_5 = new Factura("Álbumes de fotos");
        Factura facturaF2_6 = new Factura("Portarretratos digitales");
        Factura facturaF2_7 = new Factura("Velas rojas");
        Factura facturaF2_8 = new Factura("Velas blancas");
        funeraria2.agregarFacturapagada(facturaF2_1);
        funeraria2.agregarFacturapagada(facturaF2_2);
        funeraria2.agregarFacturapagada(facturaF2_3);
        funeraria2.agregarFacturapagada(facturaF2_4);
        funeraria2.agregarFacturapagada(facturaF2_5);
        funeraria2.agregarFacturapagada(facturaF2_6);
        funeraria2.agregarFacturapagada(facturaF2_7);
        funeraria2.agregarFacturapagada(facturaF2_8);

        
        // facturas para funerarias 3
        Factura facturaF3_1 = new Factura("Trajes de caballero");
        Factura facturaF3_2 = new Factura("Vestidos de dama");
        Factura facturaF3_3 = new Factura("Medalla conmemorativa");
        Factura facturaF3_4 = new Factura("Joyas conmemorativas");
        Factura facturaF3_5 = new Factura("Álbumes de fotos");
        Factura facturaF3_6 = new Factura("Portarretratos digitales");
        Factura facturaF3_7 = new Factura("Velas rojas");
        Factura facturaF3_8 = new Factura("Velas blancas");
        funeraria3.agregarFacturapagada(facturaF3_1);
        funeraria3.agregarFacturapagada(facturaF3_2);
        funeraria3.agregarFacturapagada(facturaF3_3);
        funeraria3.agregarFacturapagada(facturaF3_4);
        funeraria3.agregarFacturapagada(facturaF3_5);
        funeraria3.agregarFacturapagada(facturaF3_6);
        funeraria3.agregarFacturapagada(facturaF3_7);
        funeraria3.agregarFacturapagada(facturaF3_8);

 
        
        // productos para las facturas de la funeraria 1
      
        
        Producto trajesCaballeroVendidosF1 = new Producto("Trajes de caballero", 998.0, 10, 10);
        Producto vestidosDamaVendidosF1 = new Producto("Vestidos de dama", 1200.0, 20, 20);
        Producto recuerdoVendidosF1 = new Producto("Medalla conmemorativa", 100.0, 12, 12); 
        Producto recuerdo2VendidosF1 = new Producto("Joyas conmemorativas", 250.0, 15, 15); 
        Producto recuerdo3VendidosF1 = new Producto("Álbumes de fotos", 300.0, 18, 18); 
        Producto recuerdo4VendidosF1 = new Producto("Portarretratos digitales", 120.0, 45, 45); 
        Producto velasRojasVendidosF1 = new Producto("Velas rojas", 300.0, 5, 5); 
        Producto velasBlancasVendidosF1 = new Producto("Velas blancas", 300.0, 30, 30); 
        facturaF1_1.agregarProducto(trajesCaballeroVendidosF1);
        facturaF1_2.agregarProducto(vestidosDamaVendidosF1);
        facturaF1_3.agregarProducto(recuerdoVendidosF1);
        facturaF1_4.agregarProducto(recuerdo2VendidosF1);
        facturaF1_5.agregarProducto(recuerdo3VendidosF1);
        facturaF1_6.agregarProducto(recuerdo4VendidosF1);
        facturaF1_7.agregarProducto(velasRojasVendidosF1);
        facturaF1_8.agregarProducto(velasBlancasVendidosF1);
        
    
        // productos para las facturas de la funeraria 2
        Producto trajesCaballeroVendidosF2 = new Producto("Trajes de caballero", 998.0, 25, 25); 
        Producto vestidosDamaVendidosF2 = new Producto("Vestidos de dama", 1200.0, 9, 9); 
        Producto recuerdoVendidosF2 = new Producto("Medalla conmemorativa", 100.0, 20, 20);
        Producto recuerdo2VendidosF2 = new Producto("Joyas conmemorativas", 250.0, 10, 10); 
        Producto recuerdo3VendidosF2 = new Producto("Álbumes de fotos", 300.0, 12, 12); 
        Producto recuerdo4VendidosF2 = new Producto("Portarretratos digitales", 120.0, 45, 45);
        Producto velasRojasVendidosF2 = new Producto("Velas rojas", 300.0, 20, 20); 
        Producto velasBlancasVendidosF2 = new Producto("Velas blancas", 300.0, 40, 40); 
        facturaF2_1.agregarProducto(trajesCaballeroVendidosF2);
        facturaF2_2.agregarProducto(vestidosDamaVendidosF2);
        facturaF2_3.agregarProducto(recuerdoVendidosF2);
        facturaF2_4.agregarProducto(recuerdo2VendidosF2);
        facturaF2_5.agregarProducto(recuerdo3VendidosF2);
        facturaF2_6.agregarProducto(recuerdo4VendidosF2);
        facturaF2_7.agregarProducto(velasRojasVendidosF2);
        facturaF2_8.agregarProducto(velasBlancasVendidosF2);
        
        // productos para las facturas de la funeraria 3

        Producto trajesCaballeroVendidosF3 = new Producto("Trajes de caballero", 998.0, 30, 30); 
        Producto vestidosDamaVendidosF3 = new Producto("Vestidos de dama", 1200.0, 12, 12); 
        Producto recuerdoVendidosF3 = new Producto("Medalla conmemorativa", 100.0, 25, 25);
        Producto recuerdo2VendidosF3 = new Producto("Joyas conmemorativas", 250.0, 18, 18);
        Producto recuerdo3VendidosF3 = new Producto("Álbumes de fotos", 300.0, 15, 15);
        Producto recuerdo4VendidosF3 = new Producto("Portarretratos digitales", 120.0, 35, 35); 
        Producto velasRojasVendidosF3 = new Producto("Velas rojas", 300.0, 10, 10); 
        Producto velasBlancasVendidosF3 = new Producto("Velas blancas", 300.0, 50, 50); 
        
        facturaF3_1.agregarProducto(trajesCaballeroVendidosF3);
        facturaF3_2.agregarProducto(vestidosDamaVendidosF3);
        facturaF3_3.agregarProducto(recuerdoVendidosF3);
        facturaF3_4.agregarProducto(recuerdo2VendidosF3);
        facturaF3_5.agregarProducto(recuerdo3VendidosF3);
        facturaF3_6.agregarProducto(recuerdo4VendidosF3);
        facturaF3_7.agregarProducto(velasRojasVendidosF3);
        facturaF3_8.agregarProducto(velasBlancasVendidosF3);
        
        // PRODUCTOS LOCALES
        Producto trajesCaballeroLocales = new Producto("Trajes de caballero", 998.0, 100, 0); 
        Producto vestidosDamaLocales = new Producto("Vestidos de dama", 1200.0, 100, 0); 
        Producto recuerdoLocales = new Producto("Medalla conmemorativa", 100.0, 100, 0);
        Producto recuerdo2Locales = new Producto("Joyas conmemorativas", 250.0, 100, 0);
        Producto recuerdo3Locales = new Producto("Álbumes de fotos", 300.0, 100, 0);
        Producto recuerdo4Locales = new Producto("Portarretratos digitales", 120.0, 100, 0); 
        Producto velasRojasLocales = new Producto("Velas rojas", 300.0, 100, 0); 
        Producto velasBlancasLocales = new Producto("Velas blancas", 300.0, 100, 0);
        
        local1.agregarProducto(trajesCaballeroLocales);
        local1.agregarProducto(vestidosDamaLocales);
        local1.agregarProducto(recuerdoLocales);
        local1.agregarProducto(recuerdo2Locales);
        local1.agregarProducto(recuerdo3Locales);
        local1.agregarProducto(recuerdo4Locales);
        local1.agregarProducto(velasRojasLocales);
        local1.agregarProducto(velasBlancasLocales);
        
        Producto trajesCaballeroLocales2 = new Producto("Trajes de caballero", 998.0, 100, 0); 
        Producto vestidosDamaLocales2 = new Producto("Vestidos de dama", 1200.0, 100, 0); 
        Producto recuerdoLocales2 = new Producto("Medalla conmemorativa", 100.0, 100, 0);
        Producto recuerdo2Locales2 = new Producto("Joyas conmemorativas", 250.0, 100, 0);
        Producto recuerdo3Locales2 = new Producto("Álbumes de fotos", 300.0, 100, 0);
        Producto recuerdo4Locales2 = new Producto("Portarretratos digitales", 120.0, 100, 0); 
        Producto velasRojasLocales2 = new Producto("Velas rojas", 300.0, 100, 0); 
        Producto velasBlancasLocales2 = new Producto("Velas blancas", 300.0, 100, 0);
        
        local2.agregarProducto(trajesCaballeroLocales);
        local2.agregarProducto(vestidosDamaLocales);
        local2.agregarProducto(recuerdoLocales);
        local2.agregarProducto(recuerdo2Locales);
        local2.agregarProducto(recuerdo3Locales);
        local2.agregarProducto(recuerdo4Locales);
        local2.agregarProducto(velasRojasLocales);
        local2.agregarProducto(velasBlancasLocales);
        
        Producto trajesCaballeroLocales3 = new Producto("Trajes de caballero", 998.0, 100, 0); 
        Producto vestidosDamaLocales3 = new Producto("Vestidos de dama", 1200.0, 100, 0); 
        Producto recuerdoLocales3 = new Producto("Medalla conmemorativa", 100.0, 100, 0);
        Producto recuerdo2Locales3 = new Producto("Joyas conmemorativas", 250.0, 100, 0);
        Producto recuerdo3Locales3 = new Producto("Álbumes de fotos", 300.0, 100, 0);
        Producto recuerdo4Locales3 = new Producto("Portarretratos digitales", 120.0, 100, 0); 
        Producto velasRojasLocales3 = new Producto("Velas rojas", 300.0, 100, 0); 
        Producto velasBlancasLocales3 = new Producto("Velas blancas", 300.0, 100, 0);
        
        local3.agregarProducto(trajesCaballeroLocales);
        local3.agregarProducto(vestidosDamaLocales);
        local3.agregarProducto(recuerdoLocales);
        local3.agregarProducto(recuerdo3Locales);
        local3.agregarProducto(recuerdo3Locales);
        local3.agregarProducto(recuerdo4Locales);
        local3.agregarProducto(velasRojasLocales);
        local3.agregarProducto(velasBlancasLocales);
		
        Producto trajesCaballeroLocales4 = new Producto("Trajes de caballero", 998.0, 100, 0); 
        Producto vestidosDamaLocales4 = new Producto("Vestidos de dama", 1200.0, 100, 0); 
        Producto recuerdoLocales4 = new Producto("Medalla conmemorativa", 100.0, 100, 0);
        Producto recuerdo2Locales4 = new Producto("Joyas conmemorativas", 250.0, 100, 0);
        Producto recuerdo3Locales4= new Producto("Álbumes de fotos", 300.0, 100, 0);
        Producto recuerdo4Locales4 = new Producto("Portarretratos digitales", 120.0, 100, 0); 
        Producto velasRojasLocales4 = new Producto("Velas rojas", 300.0, 100, 0); 
        Producto velasBlancasLocales4 = new Producto("Velas blancas", 300.0, 100, 0);
        
        local4.agregarProducto(trajesCaballeroLocales);
        local4.agregarProducto(vestidosDamaLocales);
        local4.agregarProducto(recuerdoLocales);
        local4.agregarProducto(recuerdo4Locales);
        local4.agregarProducto(recuerdo4Locales);
        local4.agregarProducto(recuerdo4Locales);
        local4.agregarProducto(velasRojasLocales);
        local4.agregarProducto(velasBlancasLocales);
        
      //objetos de clase finanzas
        
        //facturas Vehiculos funeraria1
        Vehiculo ve1F1= new Vehiculo(TipoVehiculo.BERLINA,funeraria1,"azul", "2345",70000,3);
		Vehiculo ve2F1= new Vehiculo(TipoVehiculo.BUS,funeraria1,"rojo", "3444",50000,3);
		Vehiculo ve3F1= new Vehiculo(TipoVehiculo.CARROZA,funeraria1,"amarillo", "2566",150000,4);
		Vehiculo ve4F1= new Vehiculo(TipoVehiculo.FAETON,funeraria1,"naranja", "2342",120000,3);
		Vehiculo ve5F1= new Vehiculo(TipoVehiculo.COCHEFUNEBRE,funeraria1,"violeta", "3453",80000,3);
		Vehiculo ve6F1= new Vehiculo(TipoVehiculo.COCHERESPETO,funeraria1,"verde", "5476",75000,4);
		Vehiculo ve7F1= new Vehiculo(TipoVehiculo.CUPE,funeraria1,"negro", "4564",65000,3);
		Vehiculo ve8F1 = new Vehiculo(TipoVehiculo.CAMION,funeraria1,"marron", "2665",69000,3);
		
		Producto producto1F1 =  new Producto(ve1F1, local1);
		Producto producto2F1 =  new Producto(ve2F1, local1);
		Producto producto3F1 =  new Producto(ve3F1, local2);
		Producto producto4F1 =  new Producto(ve4F1, local2);
		Producto producto5F1 =  new Producto(ve5F1, local3);
		Producto producto6F1 =  new Producto(ve6F1, local3);
		Producto producto7F1 =  new Producto(ve7F1, local4);
		Producto producto8F1 =  new Producto(ve8F1, local4);
		
		ArrayList<Producto> vehiculos1F1=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos2F1=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos3F1=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos4F1=new ArrayList<Producto>();
		
		vehiculos1F1.add(producto1F1);
		vehiculos1F1.add(producto2F1);
		vehiculos2F1.add(producto3F1);
		vehiculos2F1.add(producto4F1);
		vehiculos3F1.add(producto5F1);
		vehiculos3F1.add(producto6F1);
		vehiculos4F1.add(producto7F1);
		vehiculos4F1.add(producto8F1);
		
		funeraria1.getListadoFacturasPorPagar().add(new Factura(vehiculos1F1,"vehiculo"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(vehiculos2F1,"vehiculo"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(vehiculos3F1,"vehiculo"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(vehiculos4F1,"vehiculo"));
		
		//facturas Vehiculos funeraria2
		Vehiculo ve1F2= new Vehiculo(TipoVehiculo.BERLINA,funeraria2,"azul", "2345",70000,3);
		Vehiculo ve2F2= new Vehiculo(TipoVehiculo.BUS,funeraria2,"rojo", "3444",50000,3);
		Vehiculo ve3F2= new Vehiculo(TipoVehiculo.CARROZA,funeraria2,"amarillo", "2566",150000,4);
		Vehiculo ve4F2= new Vehiculo(TipoVehiculo.FAETON,funeraria2,"naranja", "2342",120000,3);
		Vehiculo ve5F2= new Vehiculo(TipoVehiculo.COCHEFUNEBRE,funeraria2,"violeta", "3453",80000,3);
		Vehiculo ve6F2= new Vehiculo(TipoVehiculo.COCHERESPETO,funeraria2,"verde", "5476",75000,4);
		Vehiculo ve7F2= new Vehiculo(TipoVehiculo.CUPE,funeraria2,"negro", "4564",65000,3);
		Vehiculo ve8F2= new Vehiculo(TipoVehiculo.CAMION,funeraria2,"marron", "2665",69000,3);
		
		Producto producto1F2 =  new Producto(ve1F2, local1);
		Producto producto2F2 =  new Producto(ve2F2, local1);
		Producto producto3F2 =  new Producto(ve3F2, local2);
		Producto producto4F2 =  new Producto(ve4F2, local2);
		Producto producto5F2 =  new Producto(ve5F2, local3);
		Producto producto6F2 =  new Producto(ve6F2, local3);
		Producto producto7F2 =  new Producto(ve7F2, local4);
		Producto producto8F2 =  new Producto(ve8F2, local4);
		
		ArrayList<Producto> vehiculos1F2=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos2F2=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos3F2=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos4F2=new ArrayList<Producto>();
		
		vehiculos1F2.add(producto1F2);
		vehiculos1F2.add(producto8F2);
		vehiculos2F2.add(producto3F2);
		vehiculos2F2.add(producto7F2);
		vehiculos3F2.add(producto5F2);
		vehiculos3F2.add(producto2F2);
		vehiculos4F2.add(producto4F2);
		vehiculos4F2.add(producto6F2);
		
		funeraria2.getListadoFacturasPorPagar().add(new Factura(vehiculos1F2,"vehiculo"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(vehiculos2F2,"vehiculo"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(vehiculos3F2,"vehiculo"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(vehiculos4F2,"vehiculo"));
		
		//facturas Vehiculos funeraria3
		Vehiculo ve1F3= new Vehiculo(TipoVehiculo.BERLINA,funeraria3,"azul", "2345",70000,3);
		Vehiculo ve2F3= new Vehiculo(TipoVehiculo.BUS,funeraria3,"rojo", "3444",50000,3);
		Vehiculo ve3F3= new Vehiculo(TipoVehiculo.CARROZA,funeraria3,"amarillo", "2566",150000,4);
		Vehiculo ve4F3= new Vehiculo(TipoVehiculo.FAETON,funeraria3,"naranja", "2342",120000,3);
		Vehiculo ve5F3= new Vehiculo(TipoVehiculo.COCHEFUNEBRE,funeraria3,"violeta", "3453",80000,3);
		Vehiculo ve6F3= new Vehiculo(TipoVehiculo.COCHERESPETO,funeraria3,"verde", "5476",75000,4);
		Vehiculo ve7F3= new Vehiculo(TipoVehiculo.CUPE,funeraria3,"negro", "4564",65000,3);
		Vehiculo ve8F3= new Vehiculo(TipoVehiculo.CAMION,funeraria3,"marron", "2665",69000,3);
		
		Producto producto1F3 =  new Producto(ve1F3, local1);
		Producto producto2F3 =  new Producto(ve2F3, local1);
		Producto producto3F3 =  new Producto(ve3F3, local2);
		Producto producto4F3 =  new Producto(ve4F3, local2);
		Producto producto5F3 =  new Producto(ve5F3, local3);
		Producto producto6F3 =  new Producto(ve6F3, local3);
		Producto producto7F3 =  new Producto(ve7F3, local4);
		Producto producto8F3 =  new Producto(ve8F3, local4);
		
		ArrayList<Producto> vehiculos1F3=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos2F3=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos3F3=new ArrayList<Producto>();
		ArrayList<Producto> vehiculos4F3=new ArrayList<Producto>();
		
		vehiculos1F3.add(producto1F3);
		vehiculos1F3.add(producto5F3);
		vehiculos2F3.add(producto3F3);
		vehiculos2F3.add(producto6F3);
		vehiculos3F3.add(producto8F3);
		vehiculos3F3.add(producto2F3);
		vehiculos4F3.add(producto4F3);
		vehiculos4F3.add(producto7F3);
		
		funeraria3.getListadoFacturasPorPagar().add(new Factura(vehiculos1F3,"vehiculo"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(vehiculos2F3,"vehiculo"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(vehiculos3F3,"vehiculo"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(vehiculos4F3,"vehiculo"));
		
		//Producto inventario 
		
		Producto productoI1 =  new Producto("Urnas",10000,6,local1);
		Producto productoI2 =  new Producto("Urnas",10000,3,local1);
		Producto productoI3 =  new Producto("Urnas",10000,6,local2);
		Producto productoI4 =  new Producto("Urnas",10000,2,local2);
		Producto productoI5 =  new Producto("Tumbas",15000,7,local3);
		Producto productoI6 =  new Producto("Tumbas",15000,3,local3);
		Producto productoI7 =  new Producto("Tumbas",1500,8,local1);
		Producto productoI8 =  new Producto("Tumbas",15000,2,local3);
		
		//Facturas inventario funeraria 1
		ArrayList<Producto> urnas1F1=new ArrayList<Producto>();
		ArrayList<Producto> urnas2F1=new ArrayList<Producto>();
		ArrayList<Producto> tumbas1F1=new ArrayList<Producto>();
		ArrayList<Producto> tumbas2F1=new ArrayList<Producto>();
		
		urnas1F1.add(productoI1);
		urnas1F1.add(productoI2);
		urnas2F1.add(productoI3);
		urnas2F1.add(productoI4);
		tumbas1F1.add(productoI5);
		tumbas1F1.add(productoI6);
		tumbas2F1.add(productoI7);
		tumbas2F1.add(productoI8);
		
		funeraria1.getListadoFacturasPorPagar().add(new Factura(urnas1F1,"inventario"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(urnas2F1,"inventario"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(tumbas1F1,"inventario"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(tumbas2F1,"inventario"));
		
		//Facturas inventario funeraria 2
		ArrayList<Producto> urnas1F2=new ArrayList<Producto>();
		ArrayList<Producto> urnas2F2=new ArrayList<Producto>();
		ArrayList<Producto> tumbas1F2=new ArrayList<Producto>();
		ArrayList<Producto> tumbas2F2=new ArrayList<Producto>();
				
		urnas1F2.add(productoI1);
		urnas1F2.add(productoI3);
		urnas2F2.add(productoI2);
		urnas2F2.add(productoI4);
		tumbas1F2.add(productoI5);
		tumbas1F2.add(productoI7);
		tumbas2F2.add(productoI6);
		tumbas2F2.add(productoI8);
				
		funeraria2.getListadoFacturasPorPagar().add(new Factura(urnas1F2,"inventario"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(urnas2F2,"inventario"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(tumbas1F2,"inventario"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(tumbas2F2,"inventario"));
				
		//Facturas inventario funeraria 3
		ArrayList<Producto> urnas1F3=new ArrayList<Producto>();
		ArrayList<Producto> urnas2F3=new ArrayList<Producto>();
		ArrayList<Producto> tumbas1F3=new ArrayList<Producto>();
		ArrayList<Producto> tumbas2F3=new ArrayList<Producto>();
				
		urnas1F3.add(productoI1);
		urnas1F3.add(productoI4);
		urnas2F3.add(productoI2);
		urnas2F3.add(productoI3);
		tumbas1F3.add(productoI5);
		tumbas1F3.add(productoI8);
		tumbas2F3.add(productoI7);
		tumbas2F3.add(productoI6);
				
		funeraria3.getListadoFacturasPorPagar().add(new Factura(urnas1F1,"inventario"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(urnas2F1,"inventario"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(tumbas1F1,"inventario"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(tumbas2F1,"inventario"));
		
		
		//Productos establecimiento 		
		Producto productoE1 =  new Producto("Tarifa de cremacion",1000,1,local1);
		Producto productoE2 =  new Producto("Derechos de uso  de instalaciones",10000,1,local1);
		Producto productoE3 =  new Producto("Tarifa de inhumacion",1500,1,local2);
		Producto productoE4 =  new Producto("Derechos del uso de nicho o sepultura",1000,1,local2);
		Producto productoE5 =  new Producto("Mantenimiento y conservacion",15000,1,local3);
		Producto productoE6 =  new Producto("Ofrenda o donacion",500,1,local3);
		Producto productoE7 =  new Producto("Tarifa de uso de instalaciones",500,1,local1);
		
		//Facturas establecimiento funeraria 1
		ArrayList<Producto> servicios1F1 = new ArrayList<Producto>();
		ArrayList<Producto> servicios2F1 = new ArrayList<Producto>();
		ArrayList<Producto> servicios3F1 = new ArrayList<Producto>();
		
		servicios1F1.add(productoE1);
		servicios1F1.add(productoE2);
		servicios1F1.add(productoE3);
		servicios2F1.add(productoE4);
		servicios2F1.add(productoE5);
		servicios3F1.add(productoE6);
		servicios3F1.add(productoE7);
		
		funeraria1.getListadoFacturasPorPagar().add(new Factura(servicios1F1,"establecimiento"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(servicios2F1,"establecimiento"));
		funeraria1.getListadoFacturasPorPagar().add(new Factura(servicios3F1,"establecimiento"));
		
		//Facturas establecimiento funeraria 2
		ArrayList<Producto> servicios1F2 = new ArrayList<Producto>();
		ArrayList<Producto> servicios2F2 = new ArrayList<Producto>();
		ArrayList<Producto> servicios3F2 = new ArrayList<Producto>();
				
		servicios1F2.add(productoE1);
		servicios1F2.add(productoE4);
		servicios1F2.add(productoE6);
		servicios2F2.add(productoE2);
		servicios2F2.add(productoE5);
		servicios3F2.add(productoE3);
		servicios3F2.add(productoE7);
				
		funeraria2.getListadoFacturasPorPagar().add(new Factura(servicios1F2,"establecimiento"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(servicios2F2,"establecimiento"));
		funeraria2.getListadoFacturasPorPagar().add(new Factura(servicios3F2,"establecimiento"));
				
		//Facturas establecimiento funeraria 3
		ArrayList<Producto> servicios1F3 = new ArrayList<Producto>();
		ArrayList<Producto> servicios2F3 = new ArrayList<Producto>();
		ArrayList<Producto> servicios3F3 = new ArrayList<Producto>();
				
		servicios1F3.add(productoE7);
		servicios1F3.add(productoE5);
		servicios1F3.add(productoE6);
		servicios2F3.add(productoE4);
		servicios2F3.add(productoE2);
		servicios3F3.add(productoE3);
		servicios3F3.add(productoE1);
				
		funeraria3.getListadoFacturasPorPagar().add(new Factura(servicios1F3,"establecimiento"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(servicios2F3,"establecimiento"));
		funeraria3.getListadoFacturasPorPagar().add(new Factura(servicios3F3,"establecimiento"));
		
		//Facturas clientes Funeraria 1
		
		//Productos Urnas
		Producto productoF11E = new Producto(urnaF1C11E,1);
		Producto productoF12E = new Producto(urnaF1C12E,2);
		Producto productoF13E = new Producto(urnaF1C13E,3);

		Producto productoF14E = new Producto(urnaF1C21E,4);
		Producto productoF15E = new Producto(urnaF1C22E,5);
		Producto productoF16E = new Producto(urnaF1C23E,1);

		Producto productoF17E = new Producto(urnaF1C31E,2);
		Producto productoF18E = new Producto(urnaF1C32E,3);
		Producto productoF19E = new Producto(urnaF1C33E,4);

		Producto productoF110E = new Producto(urnaF1C41E,5);
		Producto productoF111E = new Producto(urnaF1C42E,1);
		Producto productoF112E = new Producto(urnaF1C43E,2);

		Producto productoF113E = new Producto(urnaF1C51E,3);
		Producto productoF114E = new Producto(urnaF1C52E,4);
		Producto productoF115E = new Producto(urnaF1C53E,5);

		Producto productoF116E = new Producto(urnaF1C61E,1);
		Producto productoF117E = new Producto(urnaF1C62E,2);
		Producto productoF118E = new Producto(urnaF1C63E,3);
		
		//Productos Tumba
		Producto productoF11ET = new Producto(tumbaF1C11E,1);
		Producto productoF12ET = new Producto(tumbaF1C12E,2);
		Producto productoF13ET = new Producto(tumbaF1C13E,3);

		Producto productoF14ET = new Producto(tumbaF1C21E,4);
		Producto productoF15ET = new Producto(tumbaF1C22E,5);
		Producto productoF16ET = new Producto(tumbaF1C23E,1);

		Producto productoF17ET = new Producto(tumbaF1C31E,2);
		Producto productoF18ET = new Producto(tumbaF1C32E,3);
		Producto productoF19ET = new Producto(tumbaF1C33E,4);

		Producto productoF110ET = new Producto(tumbaF1C41E,5);
		Producto productoF111ET = new Producto(tumbaF1C42E,1);
		Producto productoF112ET = new Producto(tumbaF1C43E,2);

		Producto productoF113ET = new Producto(tumbaF1C51E,3);
		Producto productoF114ET = new Producto(tumbaF1C52E,4);
		Producto productoF115ET = new Producto(tumbaF1C53E,5);

		Producto productoF116ET = new Producto(tumbaF1C61E,1);
		Producto productoF117ET = new Producto(tumbaF1C62E,2);
		Producto productoF118ET = new Producto(tumbaF1C63E,3);
		
		//ArrayList productos urnas
		ArrayList<Producto> productoF1U1= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U2= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U3= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U4= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U5= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U6= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U7= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U8= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U9= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U10= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U11= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U12= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U13= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U14= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U15= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U16= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U17= new ArrayList<Producto>();
		ArrayList<Producto> productoF1U18= new ArrayList<Producto>();
		
		//ArrayList productos tumbas
		ArrayList<Producto> productoF1T1= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T2= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T3= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T4= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T5= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T6= new ArrayList<Producto>();
	    ArrayList<Producto> productoF1T7= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T8= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T9= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T10= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T11= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T12= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T13= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T14= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T15= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T16= new ArrayList<Producto>();
	    ArrayList<Producto> productoF1T17= new ArrayList<Producto>();
		ArrayList<Producto> productoF1T18= new ArrayList<Producto>();
		
		// agregar objetos Array
				
		productoF1U1.add(productoF11E);
	    productoF1U2.add(productoF12E);
	    productoF1U3.add(productoF13E);
	    productoF1U6.add(productoF14E);
	    productoF1U5.add(productoF15E);
		productoF1U6.add(productoF16E);
		productoF1U7.add(productoF17E);
		productoF1U8.add(productoF18E);
		productoF1U9.add(productoF19E);
	    productoF1U10.add(productoF110E);
		productoF1U11.add(productoF111E);
		productoF1U12.add(productoF112E);
		productoF1U13.add(productoF113E);
		productoF1U14.add(productoF114E);
		productoF1U15.add(productoF115E);
		productoF1U16.add(productoF116E);
		productoF1U17.add(productoF117E);
		productoF1U18.add(productoF118E);
		
		productoF1T1.add(productoF11ET);
	    productoF1T2.add(productoF12ET);
	    productoF1T3.add(productoF13ET);
	    productoF1T6.add(productoF14ET);
	    productoF1T5.add(productoF15ET);
		productoF1T6.add(productoF16ET);
		productoF1T7.add(productoF17ET);
		productoF1T8.add(productoF18ET);
		productoF1T9.add(productoF19ET);
	    productoF1T10.add(productoF110ET);
		productoF1T11.add(productoF111ET);
		productoF1T12.add(productoF112ET);
		productoF1T13.add(productoF113ET);
		productoF1T14.add(productoF114ET);
		productoF1T15.add(productoF115ET);
		productoF1T16.add(productoF116ET);
		productoF1T17.add(productoF117ET);
		productoF1T18.add(productoF118ET);
		
		//Facturas clientes 
		
		clienteF11E.getListadoFacturas().add(new Factura(productoF1U1, "clientes"));
		clienteF12E.getListadoFacturas().add(new Factura(productoF1U2, "clientes"));
		clienteF13E.getListadoFacturas().add(new Factura(productoF1U3, "clientes"));
		clienteF14E.getListadoFacturas().add(new Factura(productoF1U4, "clientes"));
		clienteF15E.getListadoFacturas().add(new Factura(productoF1U5, "clientes"));
		clienteF16E.getListadoFacturas().add(new Factura(productoF1U6, "clientes"));
		clienteF17E.getListadoFacturas().add(new Factura(productoF1U7, "clientes"));
		clienteF18E.getListadoFacturas().add(new Factura(productoF1U8, "clientes"));
		clienteF19E.getListadoFacturas().add(new Factura(productoF1U9, "clientes"));
		clienteF110E.getListadoFacturas().add(new Factura(productoF1U10, "clientes"));
		clienteF111E.getListadoFacturas().add(new Factura(productoF1U11, "clientes"));
		clienteF112E.getListadoFacturas().add(new Factura(productoF1U12, "clientes"));
		clienteF113E.getListadoFacturas().add(new Factura(productoF1U13, "clientes"));
		clienteF114E.getListadoFacturas().add(new Factura(productoF1U14, "clientes"));
		clienteF115E.getListadoFacturas().add(new Factura(productoF1U15, "clientes"));
		clienteF116E.getListadoFacturas().add(new Factura(productoF1U16, "clientes"));
		clienteF117E.getListadoFacturas().add(new Factura(productoF1U17, "clientes"));
		clienteF118E.getListadoFacturas().add(new Factura(productoF1U18, "clientes"));
		
		clienteF11ET.getListadoFacturas().add(new Factura(productoF1T1, "clientes"));
		clienteF12ET.getListadoFacturas().add(new Factura(productoF1T2, "clientes"));
		clienteF13ET.getListadoFacturas().add(new Factura(productoF1T3, "clientes"));
		clienteF14ET.getListadoFacturas().add(new Factura(productoF1T4, "clientes"));
		clienteF15ET.getListadoFacturas().add(new Factura(productoF1T5, "clientes"));
		clienteF16ET.getListadoFacturas().add(new Factura(productoF1T6, "clientes"));
		clienteF17ET.getListadoFacturas().add(new Factura(productoF1T7, "clientes"));
		clienteF18ET.getListadoFacturas().add(new Factura(productoF1T8, "clientes"));
		clienteF19ET.getListadoFacturas().add(new Factura(productoF1T9, "clientes"));
		clienteF110ET.getListadoFacturas().add(new Factura(productoF1T10, "clientes"));
		clienteF111ET.getListadoFacturas().add(new Factura(productoF1T11, "clientes"));
		clienteF112ET.getListadoFacturas().add(new Factura(productoF1T12, "clientes"));
		clienteF113ET.getListadoFacturas().add(new Factura(productoF1T13, "clientes"));
		clienteF114ET.getListadoFacturas().add(new Factura(productoF1T14, "clientes"));
		clienteF115ET.getListadoFacturas().add(new Factura(productoF1T15, "clientes"));
		clienteF116ET.getListadoFacturas().add(new Factura(productoF1T16, "clientes"));
		clienteF117ET.getListadoFacturas().add(new Factura(productoF1T17, "clientes"));
		clienteF118ET.getListadoFacturas().add(new Factura(productoF1T18, "clientes"));
		
		//Facturas clientes Funeraria 2
		
		//Productos Urnas
		Producto productoF21E = new Producto(urnaF2C11E,1);
		Producto productoF22E = new Producto(urnaF2C12E,2);
		Producto productoF23E = new Producto(urnaF2C13E,3);

		Producto productoF24E = new Producto(urnaF2C21E,4);
		Producto productoF25E = new Producto(urnaF2C22E,5);
		Producto productoF26E = new Producto(urnaF2C23E,1);

		Producto productoF27E = new Producto(urnaF2C31E,2);
		Producto productoF28E = new Producto(urnaF2C32E,3);
		Producto productoF29E = new Producto(urnaF2C33E,4);
		Producto productoF210E = new Producto(urnaF2C41E,5);
		Producto productoF211E = new Producto(urnaF2C42E,1);
		Producto productoF212E = new Producto(urnaF2C43E,2);

		Producto productoF213E = new Producto(urnaF2C51E,3);
		Producto productoF214E = new Producto(urnaF2C52E,4);
		Producto productoF215E = new Producto(urnaF2C53E,5);

		Producto productoF216E = new Producto(urnaF2C61E,1);
		Producto productoF217E = new Producto(urnaF2C62E,2);
		Producto productoF218E = new Producto(urnaF2C63E,3);
				
		//Productos Tumba
		Producto productoF21ET = new Producto(tumbaF2C11E,1);
		Producto productoF22ET = new Producto(tumbaF2C12E,2);
		Producto productoF23ET = new Producto(tumbaF2C13E,3);

		Producto productoF24ET = new Producto(tumbaF2C21E,4);
		Producto productoF25ET = new Producto(tumbaF2C22E,5);
		Producto productoF26ET = new Producto(tumbaF2C23E,1);

		Producto productoF27ET = new Producto(tumbaF2C31E,2);
		Producto productoF28ET = new Producto(tumbaF2C32E,3);
		Producto productoF29ET = new Producto(tumbaF2C33E,4);

		Producto productoF210ET = new Producto(tumbaF2C41E,5);
		Producto productoF211ET = new Producto(tumbaF2C42E,1);
		Producto productoF212ET = new Producto(tumbaF2C43E,2);

		Producto productoF213ET = new Producto(tumbaF2C51E,3);
		Producto productoF214ET = new Producto(tumbaF2C52E,4);
		Producto productoF215ET = new Producto(tumbaF2C53E,5);

		Producto productoF216ET = new Producto(tumbaF2C61E,1);
		Producto productoF217ET = new Producto(tumbaF2C62E,2);
		Producto productoF218ET = new Producto(tumbaF2C63E,3);
				
		//ArrayList productos urnas
		ArrayList<Producto> productoF2U1= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U2= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U3= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U4= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U5= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U6= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U7= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U8= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U9= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U10= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U11= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U12= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U13= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U14= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U15= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U16= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U17= new ArrayList<Producto>();
		ArrayList<Producto> productoF2U18= new ArrayList<Producto>();
				
		//ArrayList productos tumbas
		ArrayList<Producto> productoF2T1= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T2= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T3= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T4= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T5= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T6= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T7= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T8= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T9= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T10= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T11= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T12= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T13= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T14= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T15= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T16= new ArrayList<Producto>();
		ArrayList<Producto> productoF2T17= new ArrayList<Producto>();
	    ArrayList<Producto> productoF2T18= new ArrayList<Producto>();
				
		// agregar objetos Array
						
		productoF2U1.add(productoF21E);
		productoF2U2.add(productoF22E);
	    productoF2U3.add(productoF23E);
	    productoF2U6.add(productoF24E);
		productoF2U5.add(productoF25E);
	    productoF2U6.add(productoF26E);
		productoF2U7.add(productoF27E);
		productoF2U8.add(productoF28E);
		productoF2U9.add(productoF29E);
		productoF2U10.add(productoF210E);
		productoF2U11.add(productoF211E);
		productoF2U12.add(productoF212E);
		productoF2U13.add(productoF213E);
		productoF2U14.add(productoF214E);
		productoF2U15.add(productoF215E);
		productoF2U16.add(productoF216E);
		productoF2U17.add(productoF217E);
		productoF2U18.add(productoF218E);
				
	    productoF2T1.add(productoF21ET);
		productoF2T2.add(productoF22ET);
		productoF2T3.add(productoF23ET);
	    productoF2T6.add(productoF24ET);
		productoF2T5.add(productoF25ET);
		productoF2T6.add(productoF26ET);
		productoF2T7.add(productoF27ET);
		productoF2T8.add(productoF28ET);
		productoF2T9.add(productoF29ET);
		productoF2T10.add(productoF210ET);
		productoF2T11.add(productoF211ET);
		productoF2T12.add(productoF212ET);
		productoF2T13.add(productoF213ET);
		productoF2T14.add(productoF214ET);
		productoF2T15.add(productoF215ET);
		productoF2T16.add(productoF216ET);
		productoF2T17.add(productoF217ET);
		productoF2T18.add(productoF218ET);
				
		//Facturas clientes 
				
	    clienteF21E.getListadoFacturas().add(new Factura(productoF2U1, "clientes"));
		clienteF22E.getListadoFacturas().add(new Factura(productoF2U2, "clientes"));
		clienteF23E.getListadoFacturas().add(new Factura(productoF2U3, "clientes"));
		clienteF24E.getListadoFacturas().add(new Factura(productoF2U4, "clientes"));
		clienteF25E.getListadoFacturas().add(new Factura(productoF2U5, "clientes"));
		clienteF26E.getListadoFacturas().add(new Factura(productoF2U6, "clientes"));
		clienteF27E.getListadoFacturas().add(new Factura(productoF2U7, "clientes"));
		clienteF28E.getListadoFacturas().add(new Factura(productoF2U8, "clientes"));
		clienteF29E.getListadoFacturas().add(new Factura(productoF2U9, "clientes"));
		clienteF210E.getListadoFacturas().add(new Factura(productoF2U10, "clientes"));
		clienteF211E.getListadoFacturas().add(new Factura(productoF2U11, "clientes"));
		clienteF212E.getListadoFacturas().add(new Factura(productoF2U12, "clientes"));
		clienteF213E.getListadoFacturas().add(new Factura(productoF2U13, "clientes"));
		clienteF214E.getListadoFacturas().add(new Factura(productoF2U14, "clientes"));
		clienteF215E.getListadoFacturas().add(new Factura(productoF2U15, "clientes"));
		clienteF216E.getListadoFacturas().add(new Factura(productoF2U16, "clientes"));
		clienteF217E.getListadoFacturas().add(new Factura(productoF2U17, "clientes"));
		clienteF218E.getListadoFacturas().add(new Factura(productoF2U18, "clientes"));
		clienteF21ET.getListadoFacturas().add(new Factura(productoF2T1, "clientes"));
		clienteF22ET.getListadoFacturas().add(new Factura(productoF2T2, "clientes"));
		clienteF23ET.getListadoFacturas().add(new Factura(productoF2T3, "clientes"));
		clienteF24ET.getListadoFacturas().add(new Factura(productoF2T4, "clientes"));
		clienteF25ET.getListadoFacturas().add(new Factura(productoF2T5, "clientes"));
		clienteF26ET.getListadoFacturas().add(new Factura(productoF2T6, "clientes"));
		clienteF27ET.getListadoFacturas().add(new Factura(productoF2T7, "clientes"));
		clienteF28ET.getListadoFacturas().add(new Factura(productoF2T8, "clientes"));
		clienteF29ET.getListadoFacturas().add(new Factura(productoF2T9, "clientes"));
		clienteF210ET.getListadoFacturas().add(new Factura(productoF2T10, "clientes"));
		clienteF211ET.getListadoFacturas().add(new Factura(productoF2T11, "clientes"));
		clienteF212ET.getListadoFacturas().add(new Factura(productoF2T12, "clientes"));
		clienteF213ET.getListadoFacturas().add(new Factura(productoF2T13, "clientes"));
		clienteF214ET.getListadoFacturas().add(new Factura(productoF2T14, "clientes"));
		clienteF215ET.getListadoFacturas().add(new Factura(productoF2T15, "clientes"));
		clienteF216ET.getListadoFacturas().add(new Factura(productoF2T16, "clientes"));
		clienteF217ET.getListadoFacturas().add(new Factura(productoF2T17, "clientes"));
		clienteF218ET.getListadoFacturas().add(new Factura(productoF2T18, "clientes"));
		
		//Facturas clientes Funeraria 3
				
		//Productos Urnas
		Producto productoF31E = new Producto(urnaF3C11E,1);
		Producto productoF32E = new Producto(urnaF3C12E,2);
		Producto productoF33E = new Producto(urnaF3C13E,3);

		Producto productoF34E = new Producto(urnaF3C21E,4);
		Producto productoF35E = new Producto(urnaF3C22E,5);
		Producto productoF36E = new Producto(urnaF3C23E,1);

		Producto productoF37E = new Producto(urnaF3C31E,2);
		Producto productoF38E = new Producto(urnaF3C32E,3);
		Producto productoF39E = new Producto(urnaF3C33E,4);

		Producto productoF310E = new Producto(urnaF3C41E,5);
		Producto productoF311E = new Producto(urnaF3C42E,1);
		Producto productoF312E = new Producto(urnaF3C43E,2);

		Producto productoF313E = new Producto(urnaF3C51E,3);
		Producto productoF314E = new Producto(urnaF3C52E,4);
		Producto productoF315E = new Producto(urnaF3C53E,5);

		Producto productoF316E = new Producto(urnaF3C61E,1);
		Producto productoF317E = new Producto(urnaF3C62E,2);
		Producto productoF318E = new Producto(urnaF3C63E,3);
				
		//Productos Tumba
		Producto productoF31ET = new Producto(tumbaF3C11E,1);
		Producto productoF32ET = new Producto(tumbaF3C12E,2);
		Producto productoF33ET = new Producto(tumbaF3C13E,3);

		Producto productoF34ET = new Producto(tumbaF3C21E,4);
		Producto productoF35ET = new Producto(tumbaF3C22E,5);
		Producto productoF36ET = new Producto(tumbaF3C23E,1);

		Producto productoF37ET = new Producto(tumbaF3C31E,2);
		Producto productoF38ET = new Producto(tumbaF3C32E,3);
		Producto productoF39ET = new Producto(tumbaF3C33E,4);

		Producto productoF310ET = new Producto(tumbaF3C41E,5);
		Producto productoF311ET = new Producto(tumbaF3C42E,1);
		Producto productoF312ET = new Producto(tumbaF3C43E,2);

		Producto productoF313ET = new Producto(tumbaF3C51E,3);
		Producto productoF314ET = new Producto(tumbaF3C52E,4);
		Producto productoF315ET = new Producto(tumbaF3C53E,5);

		Producto productoF316ET = new Producto(tumbaF3C61E,1);
		Producto productoF317ET = new Producto(tumbaF3C62E,2);
		Producto productoF318ET = new Producto(tumbaF3C63E,3);
				
		//ArrayList productos urnas
		ArrayList<Producto> productoF3U1= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U2= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U3= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U4= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U5= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U6= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U7= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U8= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U9= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U10= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U11= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U12= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U13= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U14= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U15= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U16= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U17= new ArrayList<Producto>();
		ArrayList<Producto> productoF3U18= new ArrayList<Producto>();
				
		//ArrayList productos tumbas
		ArrayList<Producto> productoF3T1= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T2= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T3= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T4= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T5= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T6= new ArrayList<Producto>();
	    ArrayList<Producto> productoF3T7= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T8= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T9= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T10= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T11= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T12= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T13= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T14= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T15= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T16= new ArrayList<Producto>();
	    ArrayList<Producto> productoF3T17= new ArrayList<Producto>();
		ArrayList<Producto> productoF3T18= new ArrayList<Producto>();
				
		// agregar objetos Array
						
		productoF3U1.add(productoF31E);
	    productoF3U2.add(productoF32E);
	    productoF3U3.add(productoF33E);
	    productoF3U6.add(productoF34E);
	    productoF3U5.add(productoF35E);
		productoF3U6.add(productoF36E);
		productoF3U7.add(productoF37E);
		productoF3U8.add(productoF38E);
		productoF3U9.add(productoF39E);
	    productoF3U10.add(productoF310E);
		productoF3U11.add(productoF311E);
		productoF3U12.add(productoF312E);
		productoF3U13.add(productoF313E);
		productoF3U14.add(productoF314E);
		productoF3U15.add(productoF315E);
		productoF3U16.add(productoF316E);
		productoF3U17.add(productoF317E);
		productoF3U18.add(productoF318E);
				
		productoF3T1.add(productoF31ET);
	    productoF3T2.add(productoF32ET);
	    productoF3T3.add(productoF33ET);
	    productoF3T6.add(productoF34ET);
	    productoF3T5.add(productoF35ET);
		productoF3T6.add(productoF36ET);
		productoF3T7.add(productoF37ET);
		productoF3T8.add(productoF38ET);
		productoF3T9.add(productoF39ET);
		productoF3T10.add(productoF310ET);
	    productoF3T11.add(productoF311ET);
		productoF3T12.add(productoF312ET);
		productoF3T13.add(productoF313ET);
		productoF3T14.add(productoF314ET);
		productoF3T15.add(productoF315ET);
		productoF3T16.add(productoF316ET);
		productoF3T17.add(productoF317ET);
		productoF3T18.add(productoF318ET);
				
		//Facturas clientes 
		clienteF31E.getListadoFacturas().add(new Factura(productoF3U1, "clientes"));
		clienteF32E.getListadoFacturas().add(new Factura(productoF3U2, "clientes"));
		clienteF33E.getListadoFacturas().add(new Factura(productoF3U3, "clientes"));
		clienteF34E.getListadoFacturas().add(new Factura(productoF3U4, "clientes"));
		clienteF35E.getListadoFacturas().add(new Factura(productoF3U5, "clientes"));
		clienteF36E.getListadoFacturas().add(new Factura(productoF3U6, "clientes"));
		clienteF37E.getListadoFacturas().add(new Factura(productoF3U7, "clientes"));
		clienteF38E.getListadoFacturas().add(new Factura(productoF3U8, "clientes"));
		clienteF39E.getListadoFacturas().add(new Factura(productoF3U9, "clientes"));
		clienteF310E.getListadoFacturas().add(new Factura(productoF3U10, "clientes"));
		clienteF311E.getListadoFacturas().add(new Factura(productoF3U11, "clientes"));
		clienteF312E.getListadoFacturas().add(new Factura(productoF3U12, "clientes"));
		clienteF313E.getListadoFacturas().add(new Factura(productoF3U13, "clientes"));
		clienteF314E.getListadoFacturas().add(new Factura(productoF3U14, "clientes"));
		clienteF315E.getListadoFacturas().add(new Factura(productoF3U15, "clientes"));
		clienteF316E.getListadoFacturas().add(new Factura(productoF3U16, "clientes"));
		clienteF317E.getListadoFacturas().add(new Factura(productoF3U17, "clientes"));
		clienteF318E.getListadoFacturas().add(new Factura(productoF3U18, "clientes"));
				
		clienteF31ET.getListadoFacturas().add(new Factura(productoF3T1, "clientes"));
		clienteF32ET.getListadoFacturas().add(new Factura(productoF3T2, "clientes"));
		clienteF33ET.getListadoFacturas().add(new Factura(productoF3T3, "clientes"));
		clienteF34ET.getListadoFacturas().add(new Factura(productoF3T4, "clientes"));
		clienteF35ET.getListadoFacturas().add(new Factura(productoF3T5, "clientes"));
		clienteF36ET.getListadoFacturas().add(new Factura(productoF3T6, "clientes"));
		clienteF37ET.getListadoFacturas().add(new Factura(productoF3T7, "clientes"));
		clienteF38ET.getListadoFacturas().add(new Factura(productoF3T8, "clientes"));
		clienteF39ET.getListadoFacturas().add(new Factura(productoF3T9, "clientes"));
		clienteF310ET.getListadoFacturas().add(new Factura(productoF3T10, "clientes"));
		clienteF311ET.getListadoFacturas().add(new Factura(productoF3T11, "clientes"));
		clienteF312ET.getListadoFacturas().add(new Factura(productoF3T12, "clientes"));
		clienteF313ET.getListadoFacturas().add(new Factura(productoF3T13, "clientes"));
		clienteF314ET.getListadoFacturas().add(new Factura(productoF3T14, "clientes"));
		clienteF315ET.getListadoFacturas().add(new Factura(productoF3T15, "clientes"));
		clienteF316ET.getListadoFacturas().add(new Factura(productoF3T16, "clientes"));
		clienteF317ET.getListadoFacturas().add(new Factura(productoF3T17, "clientes"));
		clienteF318ET.getListadoFacturas().add(new Factura(productoF3T18, "clientes"));
	    
		//Facturas pagadas funerarias 
		
		//facturas Vehiculos funeraria3
		Vehiculo ve100F1= new Vehiculo(TipoVehiculo.BERLINA,funeraria1,"azul", "2345",70000,3);
		Vehiculo ve100F2= new Vehiculo(TipoVehiculo.BUS,funeraria2,"rojo", "3444",50000,3);
		Vehiculo ve100F3= new Vehiculo(TipoVehiculo.CARROZA,funeraria3,"amarillo", "2566",150000,4);
		
		Producto producto100F1 =  new Producto(ve100F1, local1);
		Producto producto100F2 =  new Producto(ve100F2, local2);
		Producto producto100F3 =  new Producto(ve100F3, local3);
		
		ArrayList<Producto> vehiculos100F1 = new ArrayList<Producto>();
		ArrayList<Producto> vehiculos100F2 = new ArrayList<Producto>();
		ArrayList<Producto> vehiculos100F3 = new ArrayList<Producto>();
		
		vehiculos100F1.add(producto100F1);
		vehiculos100F2.add(producto100F2);
		vehiculos100F3.add(producto100F3);
		
		funeraria1.getListadoFacturas().add(new Factura(vehiculos100F1, "vehiculo"));
		funeraria2.getListadoFacturas().add(new Factura(vehiculos100F2, "vehiculo"));
		funeraria3.getListadoFacturas().add(new Factura(vehiculos100F3, "vehiculo"));
		
		funeraria1.getListadoFacturas().add(new Factura(urnas1F1, "inventario"));
		funeraria2.getListadoFacturas().add(new Factura(tumbas2F2, "inventario"));
		funeraria3.getListadoFacturas().add(new Factura(servicios1F3, "establecimiento"));
		
		
		
		Funeraria[] funerarias = new Funeraria[]{funeraria1, funeraria2, funeraria1};
		
		boolean validacion=true;
		Scanner scanner = new Scanner(System.in);
		
		
		while(validacion) {
			
			System.out.println("[1] Funcionalidad Crematorio");
			System.out.println("[2] Funcionalidad Exhumación");
			System.out.println("[3] Funcionalidad Entierro");
			System.out.println("[4] Funcionalidad Gestion de Inventario");
			System.out.println("[5] Funcionalidad Finanzas");
			
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
			case 4:
				FuncionalidadGestionInventario(funerarias);
				break;
			case 5:	
				funcionalidadFinanzas();
				break;
			default:
				System.out.println("Número fuera de rango");
				validacion=false;
				break;
			}
			
			
		}
		
		
		
		
		
	}
	

	

}