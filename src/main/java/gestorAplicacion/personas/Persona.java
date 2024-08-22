/* Autores  Violeta Gomez
 * Esta clase representa a las Personas y tiene la funcionalidad de encapsular 
 * a las clases Empleado, Familiar y Cliente que también representa a Personas */
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
	private static long auxCC = 1000;
	private static final long serialVersionUID = 1L;
	
	
	public Persona(String nombre, long CC, int edad, CuentaBancaria cuentaBancaria) {
		this.nombre=nombre;
		this.CC=CC;
		this.edad=edad;
		this.cuentaBancaria=cuentaBancaria;
		personas.add(this);
		auxCC+=1;
	
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
