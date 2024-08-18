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
		// Inicializa las funerarias y otros datos necesarios aquí
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

        // Paso 4: Realizar encuesta sobre el desempeño de empleados y establecimientos
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

    public static void imprimirInformacion(Funeraria funeraria) {
        System.out.println("Nombre: " + funeraria.getNombre());
        System.out.println("Calificación: " + funeraria.getCalificacion());
        System.out.println("Cantidad de empleados: " + funeraria.getEmpleados().size());

        Producto[] productosVendidos = Funeraria.calcularProductosVendidos(funeraria);

        System.out.println("Productos más vendidos:");
        for (Producto producto : productosVendidos) {
            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidadVendida());
            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidadVendida());
            System.out.println("    ____");
            System.out.println("   |    |");
            System.out.println("   | " + producto.getNombre().charAt(0) + "  | " + producto.getCantidadVendida());
            System.out.println("   |____|");
            System.out.println();
        }

        System.out.println("------------------------");
    }

    private static boolean analizarIntercambios(Funeraria[] funerarias, Funeraria seleccionada, Scanner scanner) {
        for (Funeraria otraFuneraria : funerarias) {
            if (!otraFuneraria.equals(seleccionada)) {
                Producto[] productosSeleccionada = Funeraria.calcularProductosVendidos(seleccionada);
                Producto[] productosOtra = Funeraria.calcularProductosVendidos(otraFuneraria);

                for (Producto productoA : productosSeleccionada) {
                    for (Producto productoB : productosOtra) {
                        if (!productoA.getNombre().equals(productoB.getNombre()) && productoA.getCantidad() > 10 && productoB.getCantidad() > 10) {
                            System.out.println("Intercambio posible entre " + seleccionada.getNombre() + " y " + otraFuneraria.getNombre());
                            System.out.println("- " + seleccionada.getNombre() + " puede intercambiar " + productoA.getNombre() + " con " + otraFuneraria.getNombre());
                            System.out.println("- " + otraFuneraria.getNombre() + " puede intercambiar " + productoB.getNombre() + " con " + seleccionada.getNombre());

                            System.out.println("¿Desea realizar este intercambio? (S/N)");
                            String respuesta = scanner.next();
                            if (respuesta.equalsIgnoreCase("S")) {
                                realizarIntercambio(seleccionada, otraFuneraria, productoA, productoB, scanner);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void realizarIntercambio(Funeraria funA, Funeraria funB, Producto productoA, Producto productoB, Scanner scanner) {
        // Mostrar empleados disponibles y vehículos
        System.out.println("Seleccione los empleados para realizar el intercambio:");
        List<Empleado> empleadosSeleccionados = seleccionarEmpleados(funA, scanner);

        System.out.println("Seleccione los vehículos para realizar el intercambio:");
        List<Vehiculo> vehiculosSeleccionados = seleccionarVehiculos(funA, scanner);

        // Mostrar productos y realizar intercambio
        System.out.println("Seleccione los productos para intercambiar:");
        int cantidadIntercambio = seleccionarCantidadProducto(productoA, scanner);
        realizarIntercambioProductos(funA, funB, productoA, productoB, cantidadIntercambio, empleadosSeleccionados, vehiculosSeleccionados);
    }

    private static List<Empleado> seleccionarEmpleados(Funeraria funeraria, Scanner scanner) {
        List<Empleado> empleadosSeleccionados = new ArrayList<>();
        for (Empleado empleado : funeraria.getEmpleados()) {
            System.out.println(empleado.getNombre() + " (" + empleado.getCargo() + ") - Jornada: " + empleado.getJornada());
            System.out.println("    O ");
            System.out.println("   /|\\");
            System.out.println("   / \\");
            System.out.println("¿Seleccionar este empleado? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equalsIgnoreCase("S")) {
                empleadosSeleccionados.add(empleado);
            }
        }
        return empleadosSeleccionados;
    }

    private static List<Vehiculo> seleccionarVehiculos(Funeraria funeraria, Scanner scanner) {
        List<Vehiculo> vehiculosSeleccionados = new ArrayList<>();
        for (Vehiculo vehiculo : funeraria.getVehiculos()) {
            System.out.println(vehiculo.getTipoVehiculo() + " - Capacidad: " + vehiculo.getCapacidad() + " - Conductor: " + vehiculo.getConductor().getNombre());
            System.out.println("   ______");
            System.out.println("  /|_||_\\`.__");
            System.out.println(" (   _    _ _\\");
            System.out.println(" =`-(_)--(_)-'");
            System.out.println("¿Seleccionar este vehículo? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equalsIgnoreCase("S")) {
                vehiculosSeleccionados.add(vehiculo);
            }
        }
        return vehiculosSeleccionados;
    }

    private static int seleccionarCantidadProducto(Producto producto, Scanner scanner) {
        System.out.println("Ingrese la cantidad de " + producto.getNombre() + " para intercambiar:");
        return scanner.nextInt();
    }

    private static void realizarIntercambioProductos(Funeraria funA, Funeraria funB, Producto productoA, Producto productoB, int cantidad, List<Empleado> empleados, List<Vehiculo> vehiculos) {
        // Lógica para realizar el intercambio, actualizar inventarios, etc.
        System.out.println("Intercambio realizado exitosamente.");
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

                            // Crear y almacenar la factura
                            Producto productoComprado = new Producto(productoFaltante.getNombre(), productoFaltante.getPrecio(), cantidadCompra);
                            ArrayList<Producto> productosComprados = new ArrayList<>();
                            productosComprados.add(productoComprado);
                            Factura nuevaFactura = new Factura(productosComprados, "Compra de productos");
                            
                            funeraria.agregarFactura(nuevaFactura);

                            System.out.println("Compra realizada exitosamente. Factura generada y almacenada.");
                        }
                    }
                }
            }
        } else {
            System.out.println("Selección inválida.");
        }
    }

    private static void contratarEmpleados(Funeraria funeraria, Scanner scanner) {
        System.out.println("Empleados disponibles para contratación:");
        for (Establecimiento est : funeraria.getListadoProveedoresEmpleados()) {
            for (Empleado empleado : est.getEmpleados()) {
                System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                System.out.println("  Empleado: " + empleado.getNombre() + ", Cargo: " + empleado.getCargo() + ", Experiencia: " + empleado.getExperiencia() + " años, Edad: " + empleado.getEdad());
            }
        }

        // Seleccionar empleados y realizar contratación
    }

    private static void comprarVehiculos(Funeraria funeraria, Scanner scanner) {
        System.out.println("Vehículos disponibles para la compra:");

        // Aquí asumimos que cada establecimiento puede tener vehículos a la venta
        for (Establecimiento est : funeraria.getListadoProveedoresVehiculos()) {
            for (Vehiculo vehiculo : est.getVehiculosEnVenta()) { 
                System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                System.out.println("  Vehículo: " + vehiculo.getTipoVehiculo() + ", Capacidad: " + vehiculo.getCapacidad() + ", Precio: " + vehiculo.getPrecio());
                
                // vehículo de forma visual
                System.out.println("   ______");
                System.out.println("  /|_||_\\`.__");
                System.out.println(" (   _    _ _\\");
                System.out.println(" =`-(_)--(_)-'");
                
                System.out.println("¿Desea comprar este vehículo? (S/N)");
                String respuesta = scanner.next();
                if (respuesta.equalsIgnoreCase("S")) {
                    // Aquí se realizaría la compra y la actualización de la lista de vehículos de la funeraria
                    funeraria.agregarVehiculo(vehiculo); 
                    est.removerVehiculoEnVenta(vehiculo); 
                    System.out.println("Vehículo comprado exitosamente.");
                }
            }
        }
    }
    
    private static void realizarEncuesta(Funeraria funeraria, Scanner scanner) {
        System.out.println("Realizando encuesta de desempeño...");

        // Encuesta para empleados
        for (Empleado empleado : funeraria.getEmpleados()) {
            System.out.println("Empleado: " + empleado.getNombre());
            System.out.println("Califique el desempeño del empleado (1-5):");
            int calificacion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            System.out.println("Ingrese una descripción opcional sobre el desempeño del empleado:");
            String descripcion = scanner.nextLine();

            empleado.setCalificacion(calificacion);
            empleado.setDescripcionCalificacion(descripcion);

            System.out.println("Calificación guardada: " + calificacion);
            if (!descripcion.isEmpty()) {
                System.out.println("Descripción: " + descripcion);
            }
        }

        // Encuesta para proveedores
        for (Establecimiento est : funeraria.getListadoProveedores()) {
            System.out.println("Proveedor: " + est.getNombre());
            System.out.println("Califique el desempeño del proveedor (1-5):");
            int calificacion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            System.out.println("Ingrese una descripción opcional sobre el desempeño del proveedor:");
            String descripcion = scanner.nextLine();

            est.setCalificacion(calificacion);
            est.setDescripcionCalificacion(descripcion);

            System.out.println("Calificación guardada: " + calificacion);
            if (!descripcion.isEmpty()) {
                System.out.println("Descripción: " + descripcion);
            }
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
            System.out.println("- " + vehiculo.getTipoVehiculo() + " - Conductor: " + vehiculo.getConductor().getNombre());
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
    		
    		ArrayList<Establecimiento> funerarias =Establecimiento.filtarEstablecimiento("funeraria");
    		System.out.println("Seleccione la funeraria correspondiente");
    		int indice=0;
    		for(Establecimiento auxFuneraria:funerarias) {
    			indice+=1;
    			System.out.println("["+indice+"]"+auxFuneraria);
    		}
    	
    		System.out.print("Ingrese el índice correspondiente: ");
    		int indiceFuneraria=scanner.nextInt();
    		
    		
    		while (indiceFuneraria<1 || indiceFuneraria>funerarias.size()) {
    			System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    			indiceFuneraria=scanner.nextInt();
    		}
    		
    		Funeraria funeraria=(Funeraria) funerarias.get(indiceFuneraria-1);
    		boolean continuarY = true;
    		while(continuarY) {
    		boolean valido = false;
    		int indiceHacer = 0 ;
    		System.out.println("Que proceso quiere hacer ");
    		System.out.println("[1] Cobro clientes");
    		System.out.println("[2] Pagar facturas ");
    		System.out.println("[3] Pago empleados");
    		System.out.println("[4] credito");
    		System.out.println("[5] reajuste de dinero");
    		
    		System.out.print("Ingrese el índice correspondiente: ");
    	    indiceHacer = scanner.nextInt();
    		while(!valido) {
    		
    			if(indiceHacer >= 1 && indiceHacer <= 5) {
    				valido = true;
    			}else {
    				System.out.println("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceHacer = scanner.nextInt();
    			}
    		}
    		 int a = 0;
    		switch(indiceHacer) {
    		case 1:
    			valido = true;
    			ArrayList<Establecimiento> cementerios = funeraria.cementerios();
    			indice=0;
    			for(Establecimiento cementerio: cementerios) {
    				indice+=1;
    				System.out.println("["+indice+"] "+ cementerio);
    			}
    			
    			System.out.print("Ingrese el índice del cementerio: ");
    			int indiceCementerio = scanner.nextInt();
    			
    			while(indiceCementerio < 1 || indiceCementerio > cementerios.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceCementerio=scanner.nextInt();
    				
    			} 
    			Cementerio cementerio = (Cementerio) cementerios.get(indiceCementerio - 1);
    			ArrayList<Cliente> clientes = cementerio.getClientes();
    			indice = 0;
    			if(clientes.size() > 0) {
    			for(Cliente cliente: clientes) {
    				indice+=1;
    				System.out.println("["+indice+"] "+ cliente);
    			}
    			
    			System.out.print("Ingrese el índice de los clientes: ");
    			int indiceCliente = scanner.nextInt();
    			
    			while(indiceCliente<1 || indiceCliente>clientes.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceCliente=scanner.nextInt();
    			}
    			Cliente cliente = (Cliente) clientes.get(indiceCliente - 1);
    			int cantidadFacturas = cliente.getListadoFacturas().size();
    			if(cantidadFacturas > 0 ) {
    			funeraria.cobroServiciosClientes(cliente);	
    			System.out.println("Cobro de  facturas del cliente: "+ cliente.getNombre()+", realizado correctamente");}
    			else { System.out.println("No hay facturas que cobrar");}}else{System.out.println("No hay clientes disponibles");}
    			break;
    		case 2:
    		    valido = true;
    		    boolean continuarq = true;
    			while(continuarq) {
    		    ArrayList<Factura> facturas = funeraria.getListadoFacturasPorPagar();
    		    if(facturas.size() > 0) {
    		    for(int i = 0; i < facturas.size();i++) {
    		    	Factura factura = facturas.get(i);
    		    	System.out.println("["+(i+1)+"]"+"Factura con ID: "+ factura.getID());}
    		    System.out.print("Ingrese el índice de las facturas: ");
    			int indiceFactura = scanner.nextInt();
    			scanner.nextLine();
    			
    			while(indiceFactura<1 || indiceFactura>facturas.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceFactura=scanner.nextInt();
    			}
    			Factura factura1 = (Factura) facturas.get(indiceFactura - 1);
    			System.out.println(funeraria.cobroFacturas(factura1));
    		    System.out.println("Desea pagar otra factura? (s/n): ");
    		    String respuesta1 = scanner.next();
    		    continuarq = respuesta1.equalsIgnoreCase("s");}else{System.out.println("No hay facturas disponibles");
    		    break;}}
    		    break;
    		case 3:
    			valido = true;
    			ArrayList <Empleado> empleados = funeraria.getEmpleados();
    			for(int i = 0; i < empleados.size();i++) {
    				System.out.println("["+(i+1)+"] "+ empleados.get(i).getNombre());
    			}
    		
    			System.out.print("Ingrese el índice correspondiente: ");
    			int indiceEmpleado=scanner.nextInt();
    			while (indiceEmpleado <1 || indiceEmpleado>empleados.size()) {
    				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
    				indiceEmpleado=scanner.nextInt();
    			}
    			
    			Empleado empleado=(Empleado) empleados.get(indiceEmpleado-1);
    			
    			System.out.println(funeraria.pagoTrabajadores(empleado));
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
		CuentaBancaria cuenta1F = new CuentaBancaria(199234234, "Eterna Paz", 40000,40000,40000,40000,40000, "BBVA");
	    CuentaBancaria cuenta2F = new CuentaBancaria(193739239, "Caminos de luz", 40000,40000,40000,40000,40000, "BANCOLOMBIA");
	    CuentaBancaria cuenta3F = new CuentaBancaria(384627823, "Recuerdos eternos", 40000,40000,40000,40000,40000, "DAVIVIENDA");
		    
	    //cuentaAhorro funerarias
	    CuentaBancaria cuenta4F = new CuentaBancaria(19934, "todasLasFunerarias", 3993, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta = new CuentaBancaria(123, "Alfredo", 1000000, "Ala"); 
	    
	    
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
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Funcionalidad Exhumacion
		
		//Funeraria 1
		
		Cliente clienteF11E = new Cliente("Juan Pérez",3212,30,null,"oro",familiarA);
		Cliente clienteF12E = new Cliente("Carlos Fernández",3213,25,null,"oro",familiarA);
										
		Cliente clienteF13E = new Cliente("Miguel Rodríguez",3213,90,null,"plata",familiarC);
		Cliente clienteF14E = new Cliente("Dani Morales",3214,57,null,"plata",familiarC);
										
		Cliente clienteF15E = new Cliente("Pedro González",3215,50,null, "bronce",familiarB);
		Cliente clienteF16E = new Cliente("José Martínez",3215,30,null, "bronce",familiarA);
		

		Cliente clienteF17E = new Cliente("María López",5,"oro",familiarB);
		Cliente clienteF18E = new Cliente("Carmen García",17,"oro",familiarB);
										
		Cliente clienteF19E = new Cliente("Ana Torres",15,"bronce",familiarB);
		Cliente clienteF110E = new Cliente("Isabel Ramírez",13,"bronce",familiarB);
		

		Cliente clienteF111E = new Cliente("Laura Morales",233,90,null,"plata",familiarA);
		Cliente clienteF112E = new Cliente("Robert Jones",234,57,null,"plata",familiarC);
								
		Cliente clienteF113E = new Cliente("Olivia Miller",235,35,null, "bronce",familiarC);
		Cliente clienteF114E = new Cliente("Sophia Moore",236,50,null, "bronce",familiarC);
		

		Cliente clienteF115E = new Cliente("James Smith",5,"oro",familiarB);
		Cliente clienteF116E = new Cliente("David Brown",17,"oro",familiarB);
										
		Cliente clienteF117E = new Cliente("John Williams",15,"bronce",familiarB);
		Cliente clienteF118E = new Cliente("Michael Johnson",13,"bronce",familiarB);
		

		
		//Cementerio 1 Cenizas
		Urna urnaF1C11E=new Urna("Urnita de la Esperanza",cementerioF11Ce,70,1,"fija");
		Urna urnaF1C12E=new Urna("Urnita del Futuro",cementerioF11Ce,80,0,"fija");
		Urna urnaF1C13E=new Urna("default",cementerioF11Ce,10,0,"ordinaria");
		
		
		//Cementerio 2 Cenizas
		Urna urnaF1C21E=new Urna("Urnita de la Sabiduría",cementerioF12Ce,70,1,"fija");
		Urna urnaF1C22E=new Urna("Urnita de la Justicia",cementerioF12Ce,80,0,"ordinaria");
		Urna urnaF1C23E=new Urna("default",cementerioF12Ce,90,0,"fija");
	
		
		//Cementerio 3 Cenizas
		Urna urnaF1C31E=new Urna("Urnita de la Confianza",cementerioF13Ce,70,1,"fija");
		Urna urnaF1C32E=new Urna("Urnita del Progreso",cementerioF13Ce,80,0,"fija");
		Urna urnaF1C33E=new Urna("default",cementerioF13Ce,90,0,"fija");
	
		
		//Cementerio 4 Cenizas
		Urna urnaF1C41E=new Urna("Urnita de la Verdadera Voz",cementerioF14Ce,70,1,"fija");
		Urna urnaF1C42E=new Urna("Urnita de la Decisión",cementerioF14Ce,80,0,"fija");
		Urna urnaF1C43E=new Urna("default",cementerioF14Ce,60,0,"fija");
	
		
		//Cementerio 5 Cenizas
		Urna urnaF1C51E=new Urna("Urnita del Cambio",cementerioF15Ce,70,1,"fija");
		Urna urnaF1C52E=new Urna("Urnita del Pueblo",cementerioF15Ce,80,0,"fija");
		Urna urnaF1C53E=new Urna("default",cementerioF15Ce,60,0,"ordinaria");
	
		
		//Cementerio 6 Cenizas
		Urna urnaF1C61E=new Urna("Urnita de la Transparencia",cementerioF16Ce,70,1,"fija");
		Urna urnaF1C62E=new Urna("Urnita del Compromiso",cementerioF16Ce,80,0,"fija");
		Urna urnaF1C63E=new Urna("default",cementerioF16Ce,60,0,"ordinaria");
		
	
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
		
		
		
		Cliente clienteF11ET = new Cliente("Ezequiel Andrade",123,30,null,"oro",familiarC);
		Cliente clienteF12ET = new Cliente("Damián Vargas",1234,25,null,"oro",familiarC);
		
		Cliente clienteF13ET = new Cliente("Octavio Salazar",1235,90,null,"plata",familiarB);
		Cliente clienteF14ET = new Cliente("Leonardo Paredes",1236,57,null,"plata",familiarB);
		
		Cliente clienteF15ET = new Cliente("Ulises Ortega",1237,21,null,"bronce",familiarC);
		Cliente clienteF16ET = new Cliente("Valeria Castro",1238,50,null,"bronce",familiarC);
	
		
		Cliente clienteF17ET = new Cliente("Delfina Méndez",5,"oro",familiarB);
		Cliente clienteF18ET = new Cliente("Mireya Delgado",17,"oro",familiarB);
		
		Cliente clienteF19ET = new Cliente("Renata Aguirre",15,"plata",familiarB);
		Cliente clienteF110ET = new Cliente("Alma Guzmán",13,"plata",familiarB);
		
		Cliente clienteF111ET = new Cliente("Leo Cruz",1235,90,null,"plata",familiarB);
		Cliente clienteF112ET = new Cliente("Luna Martínez",1236,57,null,"plata",familiarB);
		
		Cliente clienteF113ET = new Cliente("Lucas Moreno",1237,21,null,"bronce",familiarC);
		Cliente clienteF114ET = new Cliente("Sofía Rodríguez",1238,50,null,"bronce",familiarC);
	
		
		//Clientes F1 - Menores de edad
		Cliente clienteF115ET = new Cliente("Aitana Gómez",5,"oro",familiarB);
		Cliente clienteF116ET = new Cliente("Zoe García",17,"oro",familiarB);
		
		Cliente clienteF117ET = new Cliente("Ethan Ortega",15,"plata",familiarB);
		Cliente clienteF118ET = new Cliente("Dylan Mendoza",13,"plata",familiarB);
		
		
		
		
		//TUmbas
		
		//Cementerio 1
		Tumba tumbaF1C11E=new Tumba("Tumbita Lugar de Paz",cementerioF11Cu,1.70,1);
		Tumba tumbaF1C12E=new Tumba("Tumbita Descanso Eterno",cementerioF11Cu,1.50,0);
		Tumba tumbaF1C13E=new Tumba("default",cementerioF11Cu,1.10,0);
		
		//Cementerio 2
		Tumba tumbaF1C21E=new Tumba("Tumbita Siempre Recordado",cementerioF12Cu,1.70,1);
		Tumba tumbaF1C22E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF12Cu,1.50,0);
		Tumba tumbaF1C23E=new Tumba("default",cementerioF12Cu,1.10,0);
		
		//Cementerio 3
		Tumba tumbaF1C31E=new Tumba("Tumbita Lugar de Serenidad",cementerioF13Cu,1.70,1);
		Tumba tumbaF1C32E=new Tumba("Tumbita Eterna Paz",cementerioF13Cu,1.50,0);
		Tumba tumbaF1C33E=new Tumba("default",cementerioF13Cu,1.10,0);

		//Cementerio 4
		Tumba tumbaF1C41E=new Tumba("Tumbita Un Alma Bella",cementerioF14Cu,1.70,1);
		Tumba tumbaF1C42E=new Tumba("Tumbita En Paz y Serenidad",cementerioF14Cu,1.50,0);
		Tumba tumbaF1C43E=new Tumba("default",cementerioF14Cu,1.10,0);
		
		//Cementerio 5
		Tumba tumbaF1C51E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF15Cu,1.70,1);
		Tumba tumbaF1C52E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF15Cu,1.50,0);
		Tumba tumbaF1C53E=new Tumba("default",cementerioF15Cu,1.10,0);
		
		//Cementerio 6
		Tumba tumbaF1C61E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF16Cu,1.70,1);
		Tumba tumbaF1C62E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF16Cu,1.50,0);
		Tumba tumbaF1C63E=new Tumba("default",cementerioF16Cu,1.60,0);
		
		
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
		
		Cliente clienteF21E = new Cliente("Juan Pérez",3212,30,null,"oro",familiarA);
		Cliente clienteF22E = new Cliente("Carlos Fernández",3213,25,null,"oro",familiarA);
										
		Cliente clienteF23E = new Cliente("Miguel Rodríguez",3213,90,null,"plata",familiarC);
		Cliente clienteF24E = new Cliente("Dani Morales",3214,57,null,"plata",familiarC);
										
		Cliente clienteF25E = new Cliente("Pedro González",3215,50,null, "bronce",familiarB);
		Cliente clienteF26E = new Cliente("José Martínez",3215,30,null, "bronce",familiarA);
		

		Cliente clienteF27E = new Cliente("María López",5,"oro",familiarB);
		Cliente clienteF28E = new Cliente("Carmen García",17,"oro",familiarB);
										
		Cliente clienteF29E = new Cliente("Ana Torres",15,"bronce",familiarB);
		Cliente clienteF210E = new Cliente("Isabel Ramírez",13,"bronce",familiarB);
		

		Cliente clienteF211E = new Cliente("Laura Morales",233,90,null,"plata",familiarA);
		Cliente clienteF212E = new Cliente("Robert Jones",234,57,null,"plata",familiarC);
								
		Cliente clienteF213E = new Cliente("Olivia Miller",235,35,null, "bronce",familiarC);
		Cliente clienteF214E = new Cliente("Sophia Moore",236,50,null, "bronce",familiarC);
		

		Cliente clienteF215E = new Cliente("James Smith",5,"oro",familiarB);
		Cliente clienteF216E = new Cliente("David Brown",17,"oro",familiarB);
										
		Cliente clienteF217E = new Cliente("John Williams",15,"bronce",familiarB);
		Cliente clienteF218E = new Cliente("Michael Johnson",13,"bronce",familiarB);
		

		
		//Cementerio 1 Cenizas
		Urna urnaF2C11E=new Urna("Urnita de la Esperanza",cementerioF21Ce,70,1,"fija");
		Urna urnaF2C12E=new Urna("Urnita del Futuro",cementerioF21Ce,80,0,"fija");
		Urna urnaF2C13E=new Urna("default",cementerioF21Ce,10,0,"ordinaria");
		
		
		//Cementerio 2 Cenizas
		Urna urnaF2C21E=new Urna("Urnita de la Sabiduría",cementerioF22Ce,70,1,"fija");
		Urna urnaF2C22E=new Urna("Urnita de la Justicia",cementerioF22Ce,80,0,"ordinaria");
		Urna urnaF2C23E=new Urna("default",cementerioF22Ce,90,0,"fija");
	
		
		//Cementerio 3 Cenizas
		Urna urnaF2C31E=new Urna("Urnita de la Confianza",cementerioF23Ce,70,1,"fija");
		Urna urnaF2C32E=new Urna("Urnita del Progreso",cementerioF23Ce,80,0,"fija");
		Urna urnaF2C33E=new Urna("default",cementerioF23Ce,90,1,"fija");
	
		
		//Cementerio 4 Cenizas
		Urna urnaF2C41E=new Urna("Urnita de la Verdadera Voz",cementerioF24Ce,70,1,"fija");
		Urna urnaF2C42E=new Urna("Urnita de la Decisión",cementerioF24Ce,80,0,"fija");
		Urna urnaF2C43E=new Urna("default",cementerioF24Ce,60,1,"fija");
	
		
		//Cementerio 5 Cenizas
		Urna urnaF2C51E=new Urna("Urnita del Cambio",cementerioF25Ce,70,2,"fija");
		Urna urnaF2C52E=new Urna("Urnita del Pueblo",cementerioF25Ce,80,0,"fija");
		Urna urnaF2C53E=new Urna("default",cementerioF25Ce,60,1,"ordinaria");
	
		
		//Cementerio 6 Cenizas
		Urna urnaF2C61E=new Urna("Urnita de la Transparencia",cementerioF26Ce,70,1,"fija");
		Urna urnaF2C62E=new Urna("Urnita del Compromiso",cementerioF26Ce,80,0,"fija");
		Urna urnaF2C63E=new Urna("default",cementerioF26Ce,60,0,"ordinaria");
		
	
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
		
		
		
		Cliente clienteF21ET = new Cliente("Ezequiel Andrade",123,30,null,"oro",familiarC);
		Cliente clienteF22ET = new Cliente("Damián Vargas",1234,25,null,"oro",familiarC);
		
		Cliente clienteF23ET = new Cliente("Octavio Salazar",1235,90,null,"plata",familiarB);
		Cliente clienteF24ET = new Cliente("Leonardo Paredes",1236,57,null,"plata",familiarB);
		
		Cliente clienteF25ET = new Cliente("Ulises Ortega",1237,21,null,"bronce",familiarC);
		Cliente clienteF26ET = new Cliente("Valeria Castro",1238,50,null,"bronce",familiarC);
	
		
		Cliente clienteF27ET = new Cliente("Delfina Méndez",5,"oro",familiarB);
		Cliente clienteF28ET = new Cliente("Mireya Delgado",17,"oro",familiarB);
		
		Cliente clienteF29ET = new Cliente("Renata Aguirre",15,"plata",familiarB);
		Cliente clienteF210ET = new Cliente("Alma Guzmán",13,"plata",familiarB);
		
		Cliente clienteF211ET = new Cliente("Leo Cruz",1235,90,null,"plata",familiarB);
		Cliente clienteF212ET = new Cliente("Luna Martínez",1236,57,null,"plata",familiarB);
		
		Cliente clienteF213ET = new Cliente("Lucas Moreno",1237,21,null,"bronce",familiarC);
		Cliente clienteF214ET = new Cliente("Sofía Rodríguez",1238,50,null,"bronce",familiarC);
	
		
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
		
		//Cementerio 2
		Tumba tumbaF2C21E=new Tumba("Tumbita Siempre Recordado",cementerioF22Cu,1.70,1);
		Tumba tumbaF2C22E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF22Cu,1.50,0);
		Tumba tumbaF2C23E=new Tumba("default",cementerioF22Cu,1.50,0);
		
		//Cementerio 3
		Tumba tumbaF2C31E=new Tumba("Tumbita Lugar de Serenidad",cementerioF23Cu,1.70,1);
		Tumba tumbaF2C32E=new Tumba("Tumbita Eterna Paz",cementerioF23Cu,1.50,0);
		Tumba tumbaF2C33E=new Tumba("default",cementerioF23Cu,1.60,0);

		//Cementerio 4
		Tumba tumbaF2C41E=new Tumba("Tumbita Un Alma Bella",cementerioF24Cu,1.70,1);
		Tumba tumbaF2C42E=new Tumba("Tumbita En Paz y Serenidad",cementerioF24Cu,1.50,0);
		Tumba tumbaF2C43E=new Tumba("default",cementerioF24Cu,1.70,0);
		
		//Cementerio 5
		Tumba tumbaF2C51E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF25Cu,1.70,1);
		Tumba tumbaF2C52E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF25Cu,1.50,0);
		Tumba tumbaF2C53E=new Tumba("default",cementerioF25Cu,1.90,0);
		
		//Cementerio 6
		Tumba tumbaF2C61E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF26Cu,1.70,1);
		Tumba tumbaF2C62E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF26Cu,1.50,0);
		Tumba tumbaF2C63E=new Tumba("default",cementerioF26Cu,1.60,0);
		
		
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
		
		Cliente clienteF31E = new Cliente("Juan Pérez",3212,30,null,"oro",familiarA);
		Cliente clienteF32E = new Cliente("Carlos Fernández",3213,25,null,"oro",familiarA);
										
		Cliente clienteF33E = new Cliente("Miguel Rodríguez",3213,90,null,"plata",familiarC);
		Cliente clienteF34E = new Cliente("Dani Morales",3214,57,null,"plata",familiarC);
										
		Cliente clienteF35E = new Cliente("Pedro González",3215,50,null, "bronce",familiarB);
		Cliente clienteF36E = new Cliente("José Martínez",3215,30,null, "bronce",familiarA);
		

		Cliente clienteF37E = new Cliente("María López",5,"oro",familiarB);
		Cliente clienteF38E = new Cliente("Carmen García",17,"oro",familiarB);
										
		Cliente clienteF39E = new Cliente("Ana Torres",15,"bronce",familiarB);
		Cliente clienteF310E = new Cliente("Isabel Ramírez",13,"bronce",familiarB);
		

		Cliente clienteF311E = new Cliente("Laura Morales",233,90,null,"plata",familiarA);
		Cliente clienteF312E = new Cliente("Robert Jones",234,57,null,"plata",familiarC);
								
		Cliente clienteF313E = new Cliente("Olivia Miller",235,35,null, "bronce",familiarC);
		Cliente clienteF314E = new Cliente("Sophia Moore",236,50,null, "bronce",familiarC);
		

		Cliente clienteF315E = new Cliente("James Smith",5,"oro",familiarB);
		Cliente clienteF316E = new Cliente("David Brown",17,"oro",familiarB);
										
		Cliente clienteF317E = new Cliente("John Williams",15,"bronce",familiarB);
		Cliente clienteF318E = new Cliente("Michael Johnson",13,"bronce",familiarB);
		

		
		//Cementerio 1 Cenizas
		Urna urnaF3C11E=new Urna("Urnita de la Esperanza",cementerioF31Ce,70,1,"fija");
		Urna urnaF3C12E=new Urna("Urnita del Futuro",cementerioF31Ce,80,0,"fija");
		Urna urnaF3C13E=new Urna("default",cementerioF31Ce,10,0,"ordinaria");
		
		
		//Cementerio 2 Cenizas
		Urna urnaF3C21E=new Urna("Urnita de la Sabiduría",cementerioF32Ce,70,1,"fija");
		Urna urnaF3C22E=new Urna("Urnita de la Justicia",cementerioF32Ce,80,0,"ordinaria");
		Urna urnaF3C23E=new Urna("default",cementerioF32Ce,90,0,"fija");
	
		
		//Cementerio 3 Cenizas
		Urna urnaF3C31E=new Urna("Urnita de la Confianza",cementerioF33Ce,70,1,"fija");
		Urna urnaF3C32E=new Urna("Urnita del Progreso",cementerioF33Ce,80,0,"fija");
		Urna urnaF3C33E=new Urna("default",cementerioF33Ce,90,0,"fija");
	
		
		//Cementerio 4 Cenizas
		Urna urnaF3C41E=new Urna("Urnita de la Verdadera Voz",cementerioF34Ce,70,1,"fija");
		Urna urnaF3C42E=new Urna("Urnita de la Decisión",cementerioF34Ce,80,0,"fija");
		Urna urnaF3C43E=new Urna("default",cementerioF34Ce,60,0,"fija");
	
		
		//Cementerio 5 Cenizas
		Urna urnaF3C51E=new Urna("Urnita del Cambio",cementerioF35Ce,70,1,"fija");
		Urna urnaF3C52E=new Urna("Urnita del Pueblo",cementerioF35Ce,80,0,"fija");
		Urna urnaF3C53E=new Urna("default",cementerioF35Ce,60,0,"ordinaria");
	
		
		//Cementerio 6 Cenizas
		Urna urnaF3C61E=new Urna("Urnita de la Transparencia",cementerioF36Ce,70,1,"fija");
		Urna urnaF3C62E=new Urna("Urnita del Compromiso",cementerioF36Ce,80,0,"fija");
		Urna urnaF3C63E=new Urna("default",cementerioF36Ce,60,0,"ordinaria");
		
	
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
		
		
		
		Cliente clienteF31ET = new Cliente("Ezequiel Andrade",123,30,null,"oro",familiarC);
		Cliente clienteF32ET = new Cliente("Damián Vargas",1234,25,null,"oro",familiarC);
		
		Cliente clienteF33ET = new Cliente("Octavio Salazar",1235,90,null,"plata",familiarB);
		Cliente clienteF34ET = new Cliente("Leonardo Paredes",1236,57,null,"plata",familiarB);
		
		Cliente clienteF35ET = new Cliente("Ulises Ortega",1237,21,null,"bronce",familiarC);
		Cliente clienteF36ET = new Cliente("Valeria Castro",1238,50,null,"bronce",familiarC);
	
		
		Cliente clienteF37ET = new Cliente("Delfina Méndez",5,"oro",familiarB);
		Cliente clienteF38ET = new Cliente("Mireya Delgado",17,"oro",familiarB);
		
		Cliente clienteF39ET = new Cliente("Renata Aguirre",15,"plata",familiarB);
		Cliente clienteF310ET = new Cliente("Alma Guzmán",13,"plata",familiarB);
		
		Cliente clienteF311ET = new Cliente("Leo Cruz",1235,90,null,"plata",familiarB);
		Cliente clienteF312ET = new Cliente("Luna Martínez",1236,57,null,"plata",familiarB);
		
		Cliente clienteF313ET = new Cliente("Lucas Moreno",1237,21,null,"bronce",familiarC);
		Cliente clienteF314ET = new Cliente("Sofía Rodríguez",1238,50,null,"bronce",familiarC);
	
		
		//Clientes F1 - Menores de edad
		Cliente clienteF315ET = new Cliente("Aitana Gómez",5,"oro",familiarB);
		Cliente clienteF316ET = new Cliente("Zoe García",17,"oro",familiarB);
		
		Cliente clienteF317ET = new Cliente("Ethan Ortega",15,"plata",familiarB);
		Cliente clienteF318ET = new Cliente("Dylan Mendoza",13,"plata",familiarB);
		
		
		
		
		//TUmbas
		
		//Cementerio 1
		Tumba tumbaF3C11E=new Tumba("Tumbita Lugar de Paz",cementerioF31Cu,1.70,1);
		Tumba tumbaF3C12E=new Tumba("Tumbita Descanso Eterno",cementerioF31Cu,1.50,0);
		Tumba tumbaF3C13E=new Tumba("default",cementerioF31Cu,1.80,0);
		
		//Cementerio 2
		Tumba tumbaF3C21E=new Tumba("Tumbita Siempre Recordado",cementerioF32Cu,1.70,1);
		Tumba tumbaF3C22E=new Tumba("Tumbita En Honor a un Ser Querido",cementerioF32Cu,1.50,0);
		Tumba tumbaF3C23E=new Tumba("default",cementerioF32Cu,1.10,0);
		
		//Cementerio 3
		Tumba tumbaF3C31E=new Tumba("Tumbita Lugar de Serenidad",cementerioF33Cu,1.70,1);
		Tumba tumbaF3C32E=new Tumba("Tumbita Eterna Paz",cementerioF33Cu,1.50,0);
		Tumba tumbaF3C33E=new Tumba("default",cementerioF33Cu,1.10,0);

		//Cementerio 4
		Tumba tumbaF3C41E=new Tumba("Tumbita Un Alma Bella",cementerioF34Cu,1.70,1);
		Tumba tumbaF3C42E=new Tumba("Tumbita En Paz y Serenidad",cementerioF34Cu,1.50,0);
		Tumba tumbaF3C43E=new Tumba("default",cementerioF34Cu,1.10,0);
		
		//Cementerio 5
		Tumba tumbaF3C51E=new Tumba("Tumbita Siempre en Nuestros Corazones",cementerioF35Cu,1.70,1);
		Tumba tumbaF3C52E=new Tumba("Tumbita Aquí Descansa en Paz",cementerioF35Cu,1.50,0);
		Tumba tumbaF3C53E=new Tumba("default",cementerioF35Cu,1.10,0);
		
		//Cementerio 6
		Tumba tumbaF3C61E=new Tumba("Tumbita La Luz de Nuestra Vida",cementerioF36Cu,1.70,1);
		Tumba tumbaF3C62E=new Tumba("Tumbita Aquí La Memoria Vive",cementerioF36Cu,1.50,0);
		Tumba tumbaF3C63E=new Tumba("default",cementerioF36Cu,1.60,0);
		
		
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
    	Establecimiento local1 = new Establecimiento("local1 productos",500,cuentalocal0,jefe1,5);
    	Establecimiento local2 = new Establecimiento("local2 productos",500,cuentalocal1,jefe2,5);
    	Establecimiento local3 = new Establecimiento("local3 productos",500,cuentalocal2,jefe3,5);
    	Establecimiento local4 = new Establecimiento("local4 productos",500,cuentalocal3,jefe4,5);
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
    	
    	
    	Establecimiento local5 = new Establecimiento("local5 Vehiculos",500,cuentalocal4,jefe5,5);
    	Establecimiento local6 = new Establecimiento("local6 Vehiculos",500,cuentalocal5,jefe6,5);
    	Establecimiento local7 = new Establecimiento("local7 Vehiculos",500,cuentalocal6,jefe7,5);
    	funeraria1.agregarProveedorVehiculo(local5);
    	funeraria1.agregarProveedorVehiculo(local6);
    	funeraria1.agregarProveedorVehiculo(local7);
    	
    	funeraria2.agregarProveedorVehiculo(local5);
    	funeraria2.agregarProveedorVehiculo(local6);
    	funeraria2.agregarProveedorVehiculo(local7);
    	
    	funeraria3.agregarProveedorVehiculo(local5);
    	funeraria3.agregarProveedorVehiculo(local6);
    	funeraria3.agregarProveedorVehiculo(local7);
    	
    	Establecimiento local8 = new Establecimiento("local8 Empleados",500,cuentalocal7,jefe8,5);
    	Establecimiento local9 = new Establecimiento("local9 Empleados",500,cuentalocal8,jefe9,5);
    	Establecimiento local10 = new Establecimiento("local110 Empleados",500,cuentalocal9,jefe10,5);
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
