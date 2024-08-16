package gestorAplicacion.personas;



import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.financiero.*;

public class Persona implements Serializable{
	
	private String nombre;
	private final long CC;
	private int edad;
	private CuentaBancaria cuentaBancaria;
	private static ArrayList<Persona> personas = new ArrayList<Persona>();
	
	
	public Persona(String nombre, long CC, int edad, CuentaBancaria cuentaBancaria) {
		this.nombre=nombre;
		this.CC=CC;
		this.edad=edad;
		this.cuentaBancaria=cuentaBancaria;
		personas.add(this);
	
	}
	
	
	public static ArrayList<Persona> getPersonas(){
		return personas;
	}
	public String toString() {
		return nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public long getCC() {
		return CC;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad=edad;
	}
	public CuentaBancaria getCuentaBancaria() {
		return this.cuentaBancaria;
	}
	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}
	

}
