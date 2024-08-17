package uiMain; 

import java.util.ArrayList;

import java.util.Scanner;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.establecimientos.Crematorio;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.financiero.*;
import gestorAplicacion.financiero.Factura;
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
    
    //Funerarias
    Funeraria funita = new Funeraria("funita",cuenta1, cuenta4);
	Funeraria fumita = new Funeraria("fumita", cuenta2, cuenta4);
	Funeraria fulanita = new Funeraria("fulanita", cuenta3, cuenta4);
		
	//Cementerios
	Cementerio Jardines_de_la_eternidad = new Cementerio ("Jardines_de_la_eternidad",78,cuenta44,"oro", null,"cenizas",fulanita); 
	Cementerio Campos_de_paz = new Cementerio ("Campos_de_paz",78,cuenta45,"oro", null,"cenizas",fumita); 
	Cementerio Monte_sacro = new Cementerio ("Monte_sacro",78,cuenta46,"oro", null,"cenizas",funita); 
	Cementerio Parque_de_la_memoria_eterna = new Cementerio ("Parque_de_la_memoria_eterna",78,cuenta47,"oro", null,"cenizas",fulanita); 
	Cementerio Jardines_del_recuerdo = new Cementerio ("Jardines_del_recuerdo",78,cuenta48,"oro", null,"cenizas",fumita); 
	Cementerio Parque_de_la_eternidad = new Cementerio ("Parque_de_la_eternidad",78,cuenta49,"oro", null,"cenizas",fulanita); 
	Cementerio Ciudad_de_los_angeles = new Cementerio ("Ciudad_de_los_angeles",78,cuenta50,"oro", null,"cenizas",funita); 
	Cementerio Valle_de_la_cerenidad = new Cementerio ("Valle_de_la_cerenidad",78,cuenta51,"oro", null,"cenizas",fumita); 
	Cementerio Santuario_de_la_eternidad = new Cementerio ("Santuario_de_la_eternidad",78,cuenta52,"oro", null,"cenizas",funita); 
		
		
		
		
		
		
		Crematorio Jardines_de_la_eternidad = new Crematorio ("Jardines_de_la_eternidad",100,null,"oro", null,funita); 
		Crematorio Campos_de_paz = new Crematorio ("Campos_de_paz",78,null,"oro", null,fumita); 
		Crematorio Monte_sacro = new Crematorio ("Monte_sacro",78,null,"oro", null,fulanita); 
		Crematorio Jardines_de_la_reflexion = new Crematorio ("Jardines_de_la_reflexion",100,null,"oro", null,funita); 
		Crematorio Parque_de_la_memoria_eterna = new Crematorio ("Parque_de_la_memoria_eterna",78,null,"oro", null,fumita); 
		Crematorio Cementerio_de_la_trinidad = new Crematorio ("Cementerio_de_la_trinidad",78,null,"oro", null,fulanita); 
		Crematorio crematorio = new Crematorio ("crematorio",100,null,"oro", null,funita); 
		Crematorio creno = new Crematorio ("creno",78,null,"oro", null,fumita); 
		Crematorio  = new Crematorio ("cremita",78,null,"oro", null,fulanita); 
		
	

		CuentaBancaria cuenta3 = new CuentaBancaria(32323, "b", 32324, "BBVA");
		CuentaBancaria cuenta4 = new CuentaBancaria(342343, "e", 32, "BBVA");
		CuentaBancaria cuenta8 = new CuentaBancaria(342343, "empleado1", 32, "BBVA");
		Cementerio cementerio1 = new Cementerio ("cementerio1",78,null,"oro", null,"cuerpos",fulanita); 
		Cementerio cemi1 = new Cementerio ("cemi1",78,null,"oro", null,"cuerpos",fumita); 
		Cementerio cemito1 = new Cementerio ("cemito1",78,null,"oro", null,"cuerpos",funita);
		Familiar b= new Familiar("Mario",123,45,cuenta3,"padre",17);
		Familiar e= new Familiar("Alberto",123,45,cuenta4,"conyugue",17);
		ArrayList<Familiar> familiar=new ArrayList<Familiar>();
		familiar.add(b);
		familiar.add(e);
		CuentaBancaria cuentaProveedor = new CuentaBancaria(1231232,"proveedor",1000000,"BBVA");
		Establecimiento Proveedor = new Establecimiento("Proveedor", cuentaProveedor);
		Empleado empleado1 = new Empleado("Alberto",30,cuenta8,"mañana","sepulturero",9000,2,5);
		Empleado empleado2 = new Empleado("Maria",23,null,"noche","sepulturero",9000,3,9);
		Empleado empleado3 = new Empleado("Anastasia",43,null,"noche","cremador",10000,3,10);
		Empleado empleado4 = new Empleado("Gilberto",44,null,"mañana","cremador",10000,3,0);
		Empleado empleado5 = new Empleado("Pepito",18,null,"mañana","sepulturero",9000,1,0);
		Empleado empleado6 = new Empleado("Camila",33,null,"tarde","cremador",10000,0,3);
		Empleado empleado7 = new Empleado("Santiago",43,null,"noche","sepulturero",9000,32,5);
		Empleado empleado8 = new Empleado("Anastasio",43,null,"tarde","cremador",10000,5,2);
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
		Cliente a1 = new Cliente("a1",12344,18,null,"oro",familiar);
		Cliente b1 = new Cliente("b1",17,"oro",familiar);
		funita.agregarCliente(a1);
		fumita.agregarCliente(b1);
		Funeraria.setCuentaAhorros(cuenta2);
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
			

}
}
