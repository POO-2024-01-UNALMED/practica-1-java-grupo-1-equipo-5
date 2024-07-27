package uiMain;

import java.util.ArrayList;

import java.util.Scanner;

import gestorAplicacion.financiero.*;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.inventario.TipoVehiculo;
import gestorAplicacion.inventario.Vehiculo;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.personas.Persona;

public class PruebaVehiculo {
	
	public static ArrayList<Vehiculo> imprimirVehiculos(Funeraria funeraria, String clienteFamiliar) {
		
		ArrayList<Vehiculo> vehiculos=funeraria.asignarVehiculo(clienteFamiliar);
		int numero=0;
		for(Vehiculo vehiculo:vehiculos) {
			if(vehiculo.isDisponible()) {
				System.out.println("["+numero+"]"+" "+vehiculo);
				numero+=1;
			}else {vehiculos.remove(vehiculo);}
			
			}//Fin for
		return vehiculos;
		
		}
	
	public static void imprimirFamiliares(ArrayList<Persona> familiares){
		
		int numero=0;
		for(Persona familiar: familiares) {
			System.out.println("["+numero+"]"+" "+familiar);
			numero+=1;
		}
	}
	
	
	public static void main(String[] args) {
		
		
		Scanner scanner = new Scanner(System.in);
		
		//Prueba personas
		
		
		CuentaBancaria cuenta= new CuentaBancaria(1234, "Ale", 10000, "comeva");
		Funeraria funi = new Funeraria("funi", cuenta, cuenta);
		Cementerio cemi =new Cementerio("cemi", "1010", 56, cuenta,"oro", null,"cenizas",funi);
		
		ArrayList<Persona> familiares = new ArrayList<Persona>();

		Familiar fam1 =new Familiar("fam1", 123, 19, "0010", cuenta, "hermano", 18);
		Familiar fam2 =new Familiar("fam2", 124, 20, "0020", cuenta, "hijo", 17);
		Familiar fam3 =new Familiar("fam3", 125, 21, "1020", cuenta, "padre", 14);
		Familiar fam4 =new Familiar("fam4", 126, 22, "2005", cuenta, "padre", 16);
		
		//familiares.add(fam1);
		familiares.add(fam1);
		familiares.add(fam2);
		familiares.add(fam3);
		familiares.add(fam4);
		
		Cliente cli1=new Cliente("cli1", 127, 19, cuenta,"oro", familiares);
		
		Vehiculo veh1= new Vehiculo(TipoVehiculo.BERLINA, funi ,"xd", "azul", "XYZW");
		Vehiculo veh2= new Vehiculo(TipoVehiculo.BUS, funi ,"xd", "azul", "XYZW");
		Vehiculo veh3= new Vehiculo(TipoVehiculo.COCHEFUNEBRE, funi ,"xd", "azul", "XYZW");
		Vehiculo veh4= new Vehiculo(TipoVehiculo.CARROZA, funi ,"xd", "azul", "XYZW");
		Vehiculo veh5= new Vehiculo(TipoVehiculo.BERLINA, funi ,"xd", "azul", "XYZW");
		
		//Asignar vehiculos
		
		int cantidadFamiliares=cli1.getFamiliares().size();
		
		System.out.println("Indique el índice para escoger el vehículo para el cliente");
		ArrayList<Vehiculo> vehiculosCliente = imprimirVehiculos(funi,"cliente");
		
		int indiceCliente = scanner.nextInt();
		//Información de carro escogido para el cliente
		Vehiculo vehiculoCliente=vehiculosCliente.get(indiceCliente);
		vehiculoCliente.agregarPasajero(cli1);	
		
		System.out.println(vehiculoCliente.getPasajeros());
		
		ArrayList<Persona> listaFamiliares = cli1.getFamiliares();
		
		while (cantidadFamiliares>0) {
			
			
			System.out.println("Indique el índice para escoger el vehículo para los familiares");
			System.out.println("Quedan "+listaFamiliares.size()+" familiares por asignar asiento");
			ArrayList<Vehiculo> vehiculosFamiliar=imprimirVehiculos(funi,"familiar");
			
			//Vehiculo familiares
			int indiceFamiliar = scanner.nextInt();
			
			//Información de Vehiculo escogido para los familiares
			Vehiculo vehiculoFamiliar=vehiculosFamiliar.get(indiceFamiliar);
			imprimirFamiliares(listaFamiliares);
			int numero=vehiculoFamiliar.getTipoVehiculo().getCapacidad();
			System.out.println("Escoja "+numero+" familiares");
			for(int i=0;i<numero;i++) {
				System.out.println("Indique el indice del familiar "+(i+1)+": ");
				int familiar=scanner.nextInt();
				vehiculoFamiliar.agregarPasajero(listaFamiliares.get(familiar));
			}
			cantidadFamiliares=0;
			System.out.println("Vehiculo con pasajeros "+vehiculoFamiliar.getPasajeros());
			System.out.print(vehiculoFamiliar.generarTrayectoria(cemi, listaFamiliares, cemi));
		}
		
		
		
		}
		
		
	
		
		
	}

