package uiMain;

import java.util.ArrayList;


import java.util.Scanner;
import java.time.LocalTime;
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

	
	
	public static void main(String[] args) {
		
		CuentaBancaria cuenta = new CuentaBancaria(123, "Alfredo", 1000000, "Ala");
		//Cliente
		Familiar b= new Familiar("Mario",123,45,"345",cuenta,"padre",17);
		Familiar e= new Familiar("Alberto",123,45,"345",cuenta,"conyugue",17);
				
				
				
		ArrayList<Familiar> familiar=new ArrayList<Familiar>();
		familiar.add(b);
		familiar.add(e);
				
		Cliente a1 = new Cliente("a1",123,17,null,"oro",familiar);
		Cliente b1 = new Cliente("b1",123,17,null,"oro",familiar);
		Cliente c1 = new Cliente("c1",123,17,null,"oro",familiar);
		Cliente d1 = new Cliente("d1",123,17,null,"oro",familiar);
		Cliente e1 = new Cliente("e1",123,17,null,"oro",familiar);
				
				
		
		
		Funeraria funita = new Funeraria("funita", cuenta,cuenta);
		Funeraria fumita = new Funeraria("fumita", cuenta,cuenta);
		Funeraria fulanita = new Funeraria("fulanita", cuenta,cuenta);
		
		funita.agregarCliente(e1);
		fumita.agregarCliente(d1);
		fulanita.agregarCliente(c1);
		fumita.agregarCliente(a1);
		
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
		
		Vehiculo veh1= new Vehiculo(TipoVehiculo.BERLINA,fulanita,"azul", "2345");
		Vehiculo veh2= new Vehiculo(TipoVehiculo.BUS,fulanita,"azul", "2345");
		Vehiculo veh3= new Vehiculo(TipoVehiculo.CARROZA,fulanita,"azul", "2345");
		
		Vehiculo veh4= new Vehiculo(TipoVehiculo.FAETON,funita,"azul", "2345");
		Vehiculo veh5= new Vehiculo(TipoVehiculo.BUS,funita,"azul", "2345");
		Vehiculo veh6= new Vehiculo(TipoVehiculo.COCHERESPETO,funita,"azul", "2345");
		
		Vehiculo veh7= new Vehiculo(TipoVehiculo.CUPE,fumita,"azul", "2345");
		Vehiculo veh8= new Vehiculo(TipoVehiculo.BERLINA,fumita,"azul", "2345");
		Vehiculo veh9= new Vehiculo(TipoVehiculo.CARROZA,fumita,"azul", "2345");
	
		
		
		ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		vehiculos.add(veh7);
		vehiculos.add(veh5);
		vehiculos.add(veh6);
		
		System.out.println(fulanita.gestionarTrasnsporte(e1, vehiculos, LocalTime.of(3, 3)));
		
		
		}
		
		
	
		
		
	}

