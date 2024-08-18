package uiMain; 

import java.util.ArrayList;

import java.util.Scanner;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.establecimientos.Crematorio;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.TipoVehiculo;
import gestorAplicacion.inventario.Urna;
import gestorAplicacion.inventario.Vehiculo;
import gestorAplicacion.personas.*;
import gestorAplicacion.inventario.*;
import java.util.Iterator;



public class FuncionalidadFinanzas {
	
	
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
	
	//proveedor
	CuentaBancaria cuentaProveedor = new CuentaBancaria(1231232,"proveedor",1000000,"BBVA");
	Establecimiento Proveedor = new Establecimiento("Proveedor", cuentaProveedor);
	
	//cuentasCorriente funerarias
	CuentaBancaria cuenta1 = new CuentaBancaria(199234234, "funita", 40000,40000,40000,40000,40000, "BBVA");
    CuentaBancaria cuenta2 = new CuentaBancaria(193739239, "fumita", 40000,40000,40000,40000,40000, "BANCOLOMBIA");
    CuentaBancaria cuenta3 = new CuentaBancaria(384627823, "fulanita", 40000,40000,40000,40000,40000, "DAVIVIENDA");
	    
    //cuentaAhorro funerarias
    CuentaBancaria cuenta4 = new CuentaBancaria(19934, "todasLasFunerarias", 3993, "BANCO_OCCIDENTE");
        
    //cuentaBancarias clientes
    CuentaBancaria cuenta5 = new CuentaBancaria(378273, "a1", 39999, "BBVA");
    CuentaBancaria cuenta6 = new CuentaBancaria(199919, "a2", 17271, "BANCOLOMBIA");
    CuentaBancaria cuenta7 = new CuentaBancaria(374388, "a3", 17212, "DAVIVIENDA");
    CuentaBancaria cuenta8 = new CuentaBancaria(347574, "a4", 34784, "BANCO_OCCIDENTE");
    CuentaBancaria cuenta9 = new CuentaBancaria(477373, "a5", 37437, "BANCO_BOGOTA");
    CuentaBancaria cuenta10 = new CuentaBancaria(234828, "b1", 28348, "BBVA");
    CuentaBancaria cuenta11 = new CuentaBancaria(373387, "b2", 23484, "BANCOLOMBIA");
    CuentaBancaria cuenta12 = new CuentaBancaria(138838, "b3", 23828, "DAVIVIENDA");
    CuentaBancaria cuenta13 = new CuentaBancaria(347734, "b4", 48238, "BANCO_OCCIDENTE");
    CuentaBancaria cuenta14 = new CuentaBancaria(173743, "b5", 42734, "BANCO_BOGOTA");
    CuentaBancaria cuenta15 = new CuentaBancaria(478474, "c1", 23424, "BBVA");
    CuentaBancaria cuenta16 = new CuentaBancaria(283882, "c2", 34564, "BANCOLOMBIA");
    CuentaBancaria cuenta17 = new CuentaBancaria(283823, "c3", 36464, "DAVIVIENDA");
    CuentaBancaria cuenta18 = new CuentaBancaria(382838, "c4", 57567, "BANCO_OCCIDENTE");
    CuentaBancaria cuenta19 = new CuentaBancaria(238822, "c5", 86786, "BANCO_BOGOTA");
    
    //cuentasBancaria empleados
    CuentaBancaria cuenta20 = new CuentaBancaria(345354, "Alberto", 39999, "BBVA");
    CuentaBancaria cuenta21 = new CuentaBancaria(456456, "Julio", 17271, "BANCOLOMBIA");
    CuentaBancaria cuenta22 = new CuentaBancaria(575675, "Andres", 17212, "DAVIVIENDA");
    CuentaBancaria cuenta23 = new CuentaBancaria(678676, "Ismael", 34784, "BANCO_OCCIDENTE");
    CuentaBancaria cuenta24 = new CuentaBancaria(345345, "Maria", 37437, "BANCO_BOGOTA");
    CuentaBancaria cuenta25 = new CuentaBancaria(234234, "Susana", 28348, "BBVA");
    CuentaBancaria cuenta26 = new CuentaBancaria(324322, "Camilo", 23484, "BANCOLOMBIA");
    CuentaBancaria cuenta27 = new CuentaBancaria(567567, "Aron", 23828, "DAVIVIENDA");
    CuentaBancaria cuenta28 = new CuentaBancaria(228828, "Julieta", 48238, "BANCO_OCCIDENTE");
    CuentaBancaria cuenta29 = new CuentaBancaria(454564, "Natalia", 42734, "BANCO_BOGOTA");
    CuentaBancaria cuenta30 = new CuentaBancaria(831838, "Joseph", 23424, "BBVA");
    CuentaBancaria cuenta31 = new CuentaBancaria(456304, "Valentina", 34564, "BANCOLOMBIA");
    CuentaBancaria cuenta32 = new CuentaBancaria(305340, "Jorge", 36464, "DAVIVIENDA");
    CuentaBancaria cuenta33 = new CuentaBancaria(765646, "Francy", 57567, "BANCO_OCCIDENTE");
    CuentaBancaria cuenta34 = new CuentaBancaria(300034, "Nancy", 86786, "BANCO_BOGOTA");
		
    //cuentaBancaria establecimiento 
       //cuentacrematorios
       CuentaBancaria cuenta35 = new CuentaBancaria(234234, "La_13", 23484, "BANCOLOMBIA");
       CuentaBancaria cuenta36 = new CuentaBancaria(655345, "A_Todo_Mil", 23828, "DAVIVIENDA");
       CuentaBancaria cuenta37 = new CuentaBancaria(675667, "Crematorio_del_recuerdo", 48238, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta38 = new CuentaBancaria(678786, "Puerta_del_cielo", 42734, "BANCO_BOGOTA");
       CuentaBancaria cuenta39 = new CuentaBancaria(897888, "La_ultima_despedida", 34564, "BANCOLOMBIA");
       CuentaBancaria cuenta40 = new CuentaBancaria(888888, "Memoria_eterna", 36464, "DAVIVIENDA");
       CuentaBancaria cuenta41 = new CuentaBancaria(342342, "La_llama_de_la_vida", 86786, "BANCO_BOGOTA");
       CuentaBancaria cuenta42 = new CuentaBancaria(623133, "El_faro_de_la_eternidad", 34784, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta43 = new CuentaBancaria(238949, "La_puerta_de_la_eternidad", 37437, "BANCO_BOGOTA");
    
       //cuentacementerios
    
       CuentaBancaria cuenta44 = new CuentaBancaria(121321, "Jardines_de_la_eternidad", 23484, "BANCOLOMBIA");
       CuentaBancaria cuenta45 = new CuentaBancaria(465465, "Campos_de_paz", 23828, "DAVIVIENDA");
       CuentaBancaria cuenta46 = new CuentaBancaria(768767, "Monte_sacro", 48238, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta47 = new CuentaBancaria(879789, "Parque_de_la_memoria_eterna", 23424, "BBVA");
       CuentaBancaria cuenta48 = new CuentaBancaria(242332, "Parque_de_la_eternidad", 36464, "DAVIVIENDA");
       CuentaBancaria cuenta49 = new CuentaBancaria(234232, "Jardines_del_recuerdo", 57567, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta50 = new CuentaBancaria(768676, "Ciudad_de_los_angeles", 34784, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta51 = new CuentaBancaria(876896, "Valle_de_la_serenidad", 37437, "BANCO_BOGOTA");
       CuentaBancaria cuenta52 = new CuentaBancaria(413212, "Santuario_de_la_eternidad", 28348, "BBVA");
       
    //CuentaBancaria Familiares
       CuentaBancaria cuenta53 = new CuentaBancaria(456454, "Mario", 23484, "BANCOLOMBIA");
       CuentaBancaria cuenta54 = new CuentaBancaria(678678, "Alberto", 23828, "DAVIVIENDA");
       CuentaBancaria cuenta55 = new CuentaBancaria(899999, "Juliana", 48238, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta56 = new CuentaBancaria(787788, "Michel", 23424, "BBVA");
       CuentaBancaria cuenta57 = new CuentaBancaria(454545, "Sebastian", 36464, "DAVIVIENDA");
       CuentaBancaria cuenta58 = new CuentaBancaria(232323, "Natalia", 57567, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta59 = new CuentaBancaria(453455, "Valeria", 34784, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta60 = new CuentaBancaria(121211, "Juana", 37437, "BANCO_BOGOTA");
    
    //CuentaBancarias empleados   
       CuentaBancaria cuenta61 = new CuentaBancaria(345345, "Alberto", 23484, "BANCOLOMBIA");
       CuentaBancaria cuenta62 = new CuentaBancaria(565555, "Maria", 23828, "DAVIVIENDA");
       CuentaBancaria cuenta63 = new CuentaBancaria(766677, "Anastasia", 48238, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta64 = new CuentaBancaria(676767, "Gilberto", 23424, "BBVA");
       CuentaBancaria cuenta65 = new CuentaBancaria(767666, "Pepito", 36464, "DAVIVIENDA");
       CuentaBancaria cuenta66 = new CuentaBancaria(344434, "Camilo", 57567, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta67 = new CuentaBancaria(223335, "Santiago", 34784, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta68 = new CuentaBancaria(134443, "Anastasio", 37437, "BANCO_BOGOTA");
       
    //CuentaBancaria clientes
       CuentaBancaria cuenta69 = new CuentaBancaria(122211, "Aurora", 23484, "BANCOLOMBIA");
       CuentaBancaria cuenta70 = new CuentaBancaria(709900, "Lyra", 48238, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta71 = new CuentaBancaria(770070, "Ophelia", 36464, "DAVIVIENDA");
       CuentaBancaria cuenta72 = new CuentaBancaria(890070, "Atticus", 34784, "BANCO_OCCIDENTE");
       CuentaBancaria cuenta73 = new CuentaBancaria(976799, "Sage", 23484, "BANCOLOMBIA");
       CuentaBancaria cuenta74 = new CuentaBancaria(507057, "Capian", 48238, "BANCO_OCCIDENTE");
   
    //Funerarias
    Funeraria funita = new Funeraria("funita",cuenta1, cuenta4);
	Funeraria fumita = new Funeraria("fumita", cuenta2, cuenta4);
	Funeraria fulanita = new Funeraria("fulanita", cuenta3, cuenta4);
		
	//Cementerios
	Cementerio Jardines_de_la_eternidad = new Cementerio ("Jardines_de_la_eternidad",78,cuenta44,"oro", null,"cenizas",fulanita); 
	Cementerio Campos_de_paz = new Cementerio ("Campos_de_paz",78,cuenta45,"oro", null,"cuerpos",fumita); 
	Cementerio Monte_sacro = new Cementerio ("Monte_sacro",78,cuenta46,"oro", null,"cenizas",funita); 
	Cementerio Parque_de_la_memoria_eterna = new Cementerio ("Parque_de_la_memoria_eterna",78,cuenta47,"oro", null,"cuerpos",fulanita); 
	Cementerio Jardines_del_recuerdo = new Cementerio ("Jardines_del_recuerdo",78,cuenta48,"oro", null,"cenizas",fumita); 
	Cementerio Parque_de_la_eternidad = new Cementerio ("Parque_de_la_eternidad",78,cuenta49,"oro", null,"cuerpos",fulanita); 
	Cementerio Ciudad_de_los_angeles = new Cementerio ("Ciudad_de_los_angeles",78,cuenta50,"oro", null,"cenizas",funita); 
	Cementerio Valle_de_la_cerenidad = new Cementerio ("Valle_de_la_cerenidad",78,cuenta51,"oro", null,"cuerpos",fumita); 
	Cementerio Santuario_de_la_eternidad = new Cementerio ("Santuario_de_la_eternidad",78,cuenta52,"oro", null,"cenizas",funita); 
		
	//crematorios	
		

	Crematorio La_13 = new Crematorio ("La_13",100,null,"oro", null,funita); 
	Crematorio A_Todo_Mil = new Crematorio ("A_Todo_Mil",78,null,"oro", null,fumita); 
	Crematorio Crematorio_del_recuerdo = new Crematorio ("Crematorio_del_recuerdo",78,null,"oro", null,fulanita); 
	Crematorio Puerta_del_cielo = new Crematorio ("Puerta_del_cielo",100,null,"oro", null,funita); 
	Crematorio La_ultima_despedida = new Crematorio ("La_ultima_despedida",78,null,"oro", null,fumita); 
	Crematorio Memoria_eterna = new Crematorio ("Memoria_eterna",78,null,"oro", null,fulanita); 
	Crematorio La_llama_de_la_vida = new Crematorio ("La_llama_de_la_vida",100,null,"oro", null,funita); 
	Crematorio El_faro_de_la_eternidad = new Crematorio ("El_faro_de_la_eternidad",78,null,"oro", null,fumita); 
	Crematorio La_puerta_de_la_eternidad = new Crematorio ("La_puerta_de_la_eternidad",78,null,"oro", null,fulanita);	
		 
		
	//Familiares	
	Familiar Mario = new Familiar("Mario",16341,40,cuenta53,"padre",17);
	Familiar Alberto = new Familiar("Alberto",15332,38,cuenta54,"conyugue",17);
	Familiar Juliana = new Familiar("Juliana",13423,25,cuenta55,"padre",17);
	Familiar Michael = new Familiar("Michael",12345,18,cuenta56,"conyugue",17);
	Familiar Sebastian = new Familiar("Sebastian",12375,70,cuenta57,"padre",17);
	Familiar Natalia = new Familiar("Natalia",14356,20,cuenta58,"conyugue",17);
	Familiar Valeria = new Familiar("Valeria",52376,29,cuenta59,"padre",17);
	Familiar Juana = new Familiar("Juana",22356,38,cuenta60,"conyugue",17);
	Familiar Miguel = new Familiar("Miguel",16,"hermano",Juana);
	Familiar Juan = new Familiar("Juan",17,"hijo", Michael);
	Familiar Ismael = new Familiar("Ismael",3,"hermano",Mario);
	Familiar Daniel = new Familiar("Daniel",12,"hijo",Alberto);
	Familiar Daniela = new Familiar("Daniela",7,"hermano",Juliana);
	Familiar Estefania = new Familiar("Estefania",8,"hijo",Sebastian);
	Familiar Emma = new Familiar("Emma",9,"hermano",Natalia);
	Familiar Mia = new Familiar("Mia",10,"hijo",Valeria);
	
	//Familias
	ArrayList<Familiar> familia1=new ArrayList<Familiar>();
	ArrayList<Familiar> familia2=new ArrayList<Familiar>();
	ArrayList<Familiar> familia3=new ArrayList<Familiar>();
	   
	    //agregarFamiliar
		familia1.add(Mario);
		familia1.add(Natalia);
		familia1.add(Emma);
		familia1.add(Ismael);
		familia2.add(Juliana);
		familia2.add(Daniel);
		familia2.add(Alberto);
		familia2.add(Daniel);
		familia3.add(Michael);
		familia3.add(Juana);
		familia3.add(Miguel);
		familia3.add(Juan);
		
	//Empleados	
	Empleado empleado1 = new Empleado("Alberto",30,cuenta61,"mañana","sepulturero",9000,2,5);
	Empleado empleado2 = new Empleado("Maria",23,cuenta61,"noche","sepulturero",9000,3,9);
	Empleado empleado3 = new Empleado("Anastasia",43,cuenta63,"noche","cremador",10000,3,10);
	Empleado empleado4 = new Empleado("Gilberto",44,cuenta64,"mañana","cremador",10000,3,0);
	Empleado empleado5 = new Empleado("Pepito",18,cuenta65,"mañana","sepulturero",9000,1,0);
	Empleado empleado6 = new Empleado("Camila",33,cuenta66,"tarde","cremador",10000,0,3);
	Empleado empleado7 = new Empleado("Santiago",43,cuenta67,"noche","sepulturero",9000,32,5);
	Empleado empleado8 = new Empleado("Anastasio",43,cuenta68,"tarde","cremador",10000,5,2);
		
	//clientes
	Cliente Aurora = new Cliente("Aurora",12344,18,cuenta69,"oro",familia1);
	Cliente Saskia = new Cliente("Saskia",17,"oro",familia2);
	Cliente Lyra = new Cliente("Lyra",12344,34,cuenta70,"oro",familia3);
	Cliente Vesper = new Cliente("Vesper",13,"oro",familia1);
	Cliente Ophelia = new Cliente("Ophelia",12344,65,cuenta71,"oro",familia2);
	Cliente Clio = new Cliente("Clio",6,"oro",familia3);
	Cliente Atticus = new Cliente("Atticus",12344,34,cuenta72,"oro",familia1);
	Cliente Kaius = new Cliente("Kaius",4,"oro",familia2);
	Cliente Sage = new Cliente("Sage",12344,64,cuenta73,"oro",familia3);
	Cliente Orion = new Cliente("Orion",14,"oro",familia1);
	Cliente Caspian = new Cliente("Caspian",12344,39,cuenta74,"oro",familia2);
	Cliente Rowan = new Cliente("Rowan",13,"oro",familia3);
	
	//Agregar clientes
	funita.agregarCliente(Aurora);
	funita.agregarCliente(Rowan);
	funita.agregarCliente(Saskia);
	funita.agregarCliente(Caspian);
	fumita.agregarCliente(Lyra);
	fumita.agregarCliente(Orion);
	fumita.agregarCliente(Vesper);
	fumita.agregarCliente(Kaius);
	fulanita.agregarCliente(Ophelia);
	fulanita.agregarCliente(Sage);
	fulanita.agregarCliente(Clio);
	fulanita.agregarCliente(Atticus);
	
	Vehiculo veh1= new Vehiculo(TipoVehiculo.BERLINA,fulanita,"azul", "2345",3232,3);
		Vehiculo veh2= new Vehiculo(TipoVehiculo.BUS,fulanita,"azul", "2345",344,3);
		Vehiculo veh3= new Vehiculo(TipoVehiculo.CARROZA,fulanita,"azul", "2345",234,4);
		Producto producto1 =  new Producto(veh1, Proveedor);
		Producto producto2 =  new Producto(veh2, Proveedor);
		Producto producto3 =  new Producto(veh3, Proveedor);
		Producto producto4 =  new Producto("Urnas",1000,3,Proveedor);
		Producto producto5 =  new Producto("Urnas",1000,3,Proveedor);
		Producto producto6 =  new Producto("Urnas",1000,3,Proveedor);
		Producto producto7 =  new Producto("derechos de cremacion",10000,3,Proveedor);
		ArrayList<Producto> urnas=new ArrayList<Producto>();
		ArrayList<Producto> derechos = new ArrayList<Producto>();
		ArrayList<Producto> transporte=new ArrayList<Producto>();
		ArrayList<Producto> transporte1=new ArrayList<Producto>();
		ArrayList<Producto> transporte2=new ArrayList<Producto>();
		transporte.add(producto1);
		transporte1.add(producto2);
		transporte2.add(producto3);
		urnas.add(producto4);
		urnas.add(producto5);
		urnas.add(producto6);
		derechos.add(producto7);
		Factura factura9 = new Factura(urnas,"inventario");
		Factura factura6 = new Factura(urnas,"inventario");
		Factura factura10 = new Factura(derechos,"establecimiento");
        Factura factura5 = new Factura(transporte,"vehiculo");
        Factura factura7 = new Factura(transporte1,"vehiculo");
        Factura factura8 = new Factura(transporte2,"vehiculo");
        factura7.totalFactura();
        factura8.totalFactura();
        factura6.totalFactura();
        factura5.totalFactura();
        factura9.totalFactura();
        factura10.totalFactura();
        funita.getListadoFacturasPorPagar().add(factura10);
        funita.getListadoFacturasPorPagar().add(factura7);
		funita.getListadoFacturasPorPagar().add(factura8);
		funita.getListadoFacturasPorPagar().add(factura9);
        funita.getListadoFacturasPorPagar().add(factura5);
		funita.getListadoFacturasPorPagar().add(factura6);
		funita.setCuentaCorriente(cuenta5);
		fulanita.setCuentaCorriente(cuenta6);
		fumita.setCuentaCorriente(cuenta7);
		a1.setCuentaBancaria(cuenta1);
		cemito1.agregarCliente(a1);
		funita.agregarEmpleado(empleado1);
		funita.agregarEmpleado(empleado4);
		funita.agregarEmpleado(empleado3);
		Factura factura1 = new Factura("Funeral", 1000, "2772", a1, "establecimiento");
		a1.agregarFactura(factura1);
		funcionalidadFinanzas();
		//cuentaBancarias clientes
	    CuentaBancaria cuenta5 = new CuentaBancaria(378273, "a1", 39999, "BBVA");
	    CuentaBancaria cuenta6 = new CuentaBancaria(199919, "a2", 17271, "BANCOLOMBIA");
	    CuentaBancaria cuenta7 = new CuentaBancaria(374388, "a3", 17212, "DAVIVIENDA");
	    CuentaBancaria cuenta8 = new CuentaBancaria(347574, "a4", 34784, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta9 = new CuentaBancaria(477373, "a5", 37437, "BANCO_BOGOTA");
	    CuentaBancaria cuenta10 = new CuentaBancaria(234828, "b1", 28348, "BBVA");
	    CuentaBancaria cuenta11 = new CuentaBancaria(373387, "b2", 23484, "BANCOLOMBIA");
	    CuentaBancaria cuenta12 = new CuentaBancaria(138838, "b3", 23828, "DAVIVIENDA");
	    CuentaBancaria cuenta13 = new CuentaBancaria(347734, "b4", 48238, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta14 = new CuentaBancaria(173743, "b5", 42734, "BANCO_BOGOTA");
	    CuentaBancaria cuenta15 = new CuentaBancaria(478474, "c1", 23424, "BBVA");
	    CuentaBancaria cuenta16 = new CuentaBancaria(283882, "c2", 34564, "BANCOLOMBIA");
	    CuentaBancaria cuenta17 = new CuentaBancaria(283823, "c3", 36464, "DAVIVIENDA");
	    CuentaBancaria cuenta18 = new CuentaBancaria(382838, "c4", 57567, "BANCO_OCCIDENTE");
	    CuentaBancaria cuenta19 = new CuentaBancaria(238822, "c5", 86786, "BANCO_BOGOTA");		

}



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


//d


								





						