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
			
			while(indice<1 || indice>cementerios.size()) {
				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
				indiceCementerio=scanner.nextInt();
				
			}
			Cementerio cementerio = (Cementerio) cementerios.get(indiceCementerio - 1);
			ArrayList<Cliente> clientes = cementerio.getClientes();
			indice = 0;
			for(Cliente cliente: clientes) {
				indice+=1;
				System.out.println("["+indice+"] "+ cliente);
			}
			
			System.out.print("Ingrese el índice de los clientes: ");
			int indiceCliente = scanner.nextInt();
			
			while(indice<1 || indice>clientes.size()) {
				System.out.print("El índice ingresado está fuera de rango. Ingrese nuevamente un índice: ");
				indiceCliente=scanner.nextInt();
			}
			Cliente cliente = (Cliente) clientes.get(indiceCliente - 1);
			funeraria.cobroServiciosClientes(cliente);	
			System.out.println("Cobro de  facturas del cliente: "+ cliente.getNombre()+", realizado correctamente");
			break;
		case 2:
		    valido = true;
		    ArrayList<Factura> facturas = funeraria.getListadoFacturasPorPagar();
		    ArrayList<String> resultados = funeraria.cobroFacturas(facturas);
		    for (String resultado : resultados) {
		        System.out.println(resultado);
		    }
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
			switch(indiceCredito) {
			case 1:
				System.out.println(funeraria.pedirCredito());
				break;
			case 2:
				System.out.println("Que porcentaje desea pagar ");
			System.out.println("[1] 100%");
			System.out.println("[2] 80%");
			System.out.println("[3] 60%");
			System.out.println("[4] 40%");
			System.out.println("[5] 20%");
			System.out.print("Ingrese el índice correspondiente: ");
			int indicePorcentaje = scanner.nextInt();
			switch(indicePorcentaje) {
			case 1:
				System.out.println(funeraria.pagarCredito(1.0));
				break;
			case 2:
				System.out.println(funeraria.pagarCredito(0.8));
				break;
			case 3:
				System.out.println(funeraria.pagarCredito(0.6));
				break;
			case 4:
				System.out.println(funeraria.pagarCredito(0.4));
				break;
			case 5:
				System.out.println(funeraria.pagarCredito(0.2));
				break;
			}
			break;
			case 3:
				System.out.println(funeraria.getCuentaCorriente().infoCredito());
			break;
			
			}System.out.println("Desea realizar otra accion? (s/n): ");
			String respuesta = scanner.next();
			continuar = respuesta.equalsIgnoreCase("s");
			}
		case 5:
			valido = true;
			
		     break;
		default:
			scanner.next();
			break;
		}
	}
	
public static void main(String[] args) {
		
	CuentaBancaria cuenta2 = new CuentaBancaria(19934, "funita", 3993, "BANCOLOMBIA");
	    CuentaBancaria cuenta1 = new CuentaBancaria(199919, "a1", 39999, "BBVA");
	    CuentaBancaria cuenta5 = new CuentaBancaria(199234234, "funita", 39999,180000,400000,400000,4000000, "BBVA");
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
		Empleado empleado1 = new Empleado("Alberto",12345,30,cuenta8,"mañana","sepulturero",900000,2,5);
		Empleado empleado2 = new Empleado("Maria",12345,23,null,"noche","sepulturero",900000,3,5);
		Empleado empleado3 = new Empleado("Anastasia",12345,43,null,"noche","cremador",900000,3,5);
		Empleado empleado4 = new Empleado("Gilberto",12345,44,null,"mañana","cremador",900000,3,0);
		Empleado empleado5 = new Empleado("Pepito",12345,18,null,"mañana","sepulturero",900000,1,0);
		Empleado empleado6 = new Empleado("Camila",12345,33,null,"tarde","cremador",900000,0,3);
		Empleado empleado7 = new Empleado("Santiago",12345,43,null,"noche","sepulturero",900000,32,5);
		Empleado empleado8 = new Empleado("Anastasio",12345,43,null,"tarde","cremador",900000,5,2);
		Vehiculo veh1= new Vehiculo(TipoVehiculo.BERLINA,fulanita,"azul", "2345",3232,3);
		Vehiculo veh2= new Vehiculo(TipoVehiculo.BUS,fulanita,"azul", "2345",344,3);
		Vehiculo veh3= new Vehiculo(TipoVehiculo.CARROZA,fulanita,"azul", "2345",234,4);
		Producto producto1 =  new Producto(veh1, Proveedor);
		Producto producto2 =  new Producto(veh2, Proveedor);
		Producto producto3 =  new Producto(veh3, Proveedor);
		Producto producto4 =  new Producto("Urnas",1000,3,Proveedor);
		Producto producto5 =  new Producto("Urnas",1000,3,Proveedor);
		Producto producto6 =  new Producto("Urnas",1000,3,Proveedor);
		ArrayList<Producto> urnas=new ArrayList<Producto>();
		ArrayList<Producto> transporte=new ArrayList<Producto>();
		ArrayList<Producto> transporte1=new ArrayList<Producto>();
		ArrayList<Producto> transporte2=new ArrayList<Producto>();
		transporte.add(producto1);
		transporte1.add(producto2);
		transporte2.add(producto3);
		urnas.add(producto4);
		urnas.add(producto5);
		urnas.add(producto6);
		Factura factura9 = new Factura(urnas,"inventario");
		Factura factura6 = new Factura(urnas,"inventario");
        Factura factura5 = new Factura(transporte,"vehiculo");
        Factura factura7 = new Factura(transporte1,"vehiculo");
        Factura factura8 = new Factura(transporte2,"vehiculo");
        factura7.totalFactura();
        factura8.totalFactura();
        factura6.totalFactura();
        factura5.totalFactura();
        factura9.totalFactura();
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
		a1.setCuentaBancaria(cuenta1);
		cemito1.agregarCliente(a1);
		funita.agregarEmpleado(empleado1);
		funita.agregarEmpleado(empleado4);
		funita.agregarEmpleado(empleado3);
		Factura factura1 = new Factura("Funeral", 1000, "2772", a1, "Establecimiento");
		a1.agregarFactura(factura1);	
		funcionalidadFinanzas();
			

}
}
