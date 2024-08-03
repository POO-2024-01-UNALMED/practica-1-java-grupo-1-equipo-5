package uiMain;

import gestorAplicacion.financiero.CuentaBancaria;
import java.time.LocalTime;
import gestorAplicacion.personas.*;
import gestorAplicacion.establecimientos.*;
import java.util.ArrayList;

public class pruebaPersonas {
	
	public static void main (String[] args) {
	
	//CuentaBancaria cuenta = new CuentaBancaria(123, "fam1", 100000, "comeva");
	ArrayList<Persona> familiares = new ArrayList<Persona>();

	Familiar fam1 =new Familiar("fam1", 123, 19, "XXXX", null, "hermano", 18);
	Familiar fam2 =new Familiar("fam2", 124, 20, "XXXX", null, "hijo", 17);
	Familiar fam3 =new Familiar("fam3", 125, 21, "XXXX", null, "padre", 14);
	Familiar fam4 =new Familiar("fam4", 126, 22, "XXXX", null, "padre", 16);
	
	//familiares.add(fam1);
	familiares.add(fam1);
	familiares.add(fam2);
	familiares.add(fam3);
	familiares.add(fam4);
	
	Cliente cli1=new Cliente("cli1", 127, 19, null,"oro", familiares);
	//Trae al familiar correcto
	System.out.println((cli1.designarFamiliar(familiares)).getParentesco());
	
	ArrayList<Persona> familiaresAsociados= new ArrayList<Persona>();
	familiaresAsociados.add(cli1);
	
	//Metodo asignarParentesco funciona bien igual que el de autorizar 
	Cliente cli2=new Cliente("cli1", 127, 19, null,"oro", familiaresAsociados);
	
	cli2.asignarParentesco(cli1, "padre");
	
	System.out.println(cli2.getFamiliares());
	
	//metodos buscarCliente(adultoNiño) de la clase funeraria
	Funeraria funi = new Funeraria("funi", null, null);
	funi.agregarCliente(cli1);
	funi.agregarCliente(cli2);
	
	System.out.println(funi.buscarCliente("adulto"));
	
	//buscar cliente de la clase establecimiento
	
	 LocalTime horaGenerada = LocalTime.of(23, 55);
	 LocalTime horaGenerada2 = LocalTime.of(23, 54);
	 
	 System.out.print(horaGenerada.isBefore(horaGenerada2));
		
	}
	
	}
	

