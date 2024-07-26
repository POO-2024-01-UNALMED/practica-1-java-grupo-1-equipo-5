package uiMain;

import java.util.ArrayList;
import java.util.Scanner;

import gestorAplicacion.financiero.*;
import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.inventario.TipoVehiculo;
import gestorAplicacion.inventario.Vehiculo;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.personas.Persona;

public class PruebaVehiculo {
	public static void main(String[] args) {
		
		
		Scanner scanner = new Scanner(System.in);
		
		//Prueba personas
		
		
		CuentaBancaria cuenta= new CuentaBancaria(1234, "Ale", 10000, "comeva");
		Funeraria funi = new Funeraria("funi", cuenta, cuenta);
		
		ArrayList<Persona> familiares = new ArrayList<Persona>();

		Familiar fam1 =new Familiar("fam1", 123, 19, "XXXX", cuenta, "hermano", 18);
		Familiar fam2 =new Familiar("fam2", 124, 20, "XXXX", cuenta, "hijo", 17);
		Familiar fam3 =new Familiar("fam3", 125, 21, "XXXX", cuenta, "padre", 14);
		Familiar fam4 =new Familiar("fam4", 126, 22, "XXXX", cuenta, "padre", 16);
		
		//familiares.add(fam1);
		familiares.add(fam1);
		familiares.add(fam2);
		familiares.add(fam3);
		familiares.add(fam4);
		
		Cliente cli1=new Cliente("cli1", 127, 19, "HHHH", cuenta,"oro", familiares);
		
		Vehiculo veh1= new Vehiculo(TipoVehiculo.BERLINA, funi ,"xd", "azul", "XYZW");
		Vehiculo veh2= new Vehiculo(TipoVehiculo.BUS, funi ,"xd", "azul", "XYZW");
		Vehiculo veh3= new Vehiculo(TipoVehiculo.COCHEFUNEBRE, funi ,"xd", "azul", "XYZW");
		Vehiculo veh4= new Vehiculo(TipoVehiculo.CARROZA, funi ,"xd", "azul", "XYZW");
		Vehiculo veh5= new Vehiculo(TipoVehiculo.BERLINA, funi ,"xd", "azul", "XYZW");
		
		int cantidadFamiliares=cli1.getFamiliares().size();
		
		System.out.println("Indique el índice para escoger el vehículo para el cliente");
		
		ArrayList<Vehiculo> vehiculos=funi.asignarVehiculo("cliente");
		int numero=0;
		
		for(Vehiculo vehiculo:vehiculos) {
			if(vehiculo.isDisponible()) {
				System.out.println("["+numero+"]"+" "+vehiculo);
				numero+=1;
				}//fin if
		}
		int indice = scanner.nextInt();
		//Información de carro escogido para el cliente
		Vehiculo vehiculo=vehiculos.get(indice);
		vehiculo.agregarPasajero(cli1);	
		
		System.out.println(vehiculo.getPasajeros());
		
		
		while (cantidadFamiliares>0) {
			ArrayList<Vehiculo> vehiculosFam=funi.asignarVehiculo("familiar");
			int numeroFam=0;
			
			for(Vehiculo auxVehiculo:vehiculosFam) {
				if(auxVehiculo.isDisponible()) {
					System.out.println("["+numeroFam+"]"+" "+auxVehiculo);
					numeroFam+=1;
					}//fin if
				cantidadFamiliares=0;
			}
			
			
		}
		
		
		
		
		
	}
}
