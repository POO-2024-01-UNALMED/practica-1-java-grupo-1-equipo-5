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
		
	CuentaBancaria cuenta2 = new CuentaBancaria(19934, "funita", 3993, "BANCOLOMBIA");
	    CuentaBancaria cuenta1 = new CuentaBancaria(199919, "a1", 39999, "BBVA");
	    CuentaBancaria cuenta5 = new CuentaBancaria(199234234, "funita", 39999,180000,400000,400000,4000000, "BBVA");
	    CuentaBancaria cuenta6 = new CuentaBancaria(199234234, "fumita", 39999,180000,400000,400000,4000000, "BBVA");
	    CuentaBancaria cuenta7 = new CuentaBancaria(199234234, "fulanita", 39999,180000,400000,400000,4000000, "BBVA");
		Funeraria funita = new Funeraria("funita", null, null);
		Funeraria fumita = new Funeraria("fumita", null, null);
		Funeraria fulanita = new Funeraria("fulanita", null, null);
		Crematorio crematorio = new Crematorio ("crematorio",100,null,"oro", null,funita); 
		Crematorio creno = new Crematorio ("creno",78,null,"oro", null,fumita); 
		Crematorio cremita = new Crematorio ("cremita",78,null,"oro", null,fulanita); 
		
		Cementerio cementerio = new Cementerio ("cementerio",78,null,"oro", null,"cenizas",fulanita); 
		Cementerio cemi = new Cementerio ("cemi",78,null,"oro", null,"cenizas",fumita); 
		Cementerio cemito = new Cementerio ("cemito",78,null,"oro", null,"cenizas",funita); 
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
