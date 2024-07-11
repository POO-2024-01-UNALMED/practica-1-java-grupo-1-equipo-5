package gestorAplicacion.personas;



import java.util.ArrayList;

import gestorAplicacion.financiero.*;

public class Familiar extends Persona {

	private String parentesco;
	// por el momento no es necesario private boolean estado;

	private int acompañantes;
	private Familiar responsable;

	// Sobrecarga de contructores

	// Contructor mayores de edad
	public Familiar(String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria, String parentesco, int acompañantes ) {
		super(nombre, CC, edad, ubicacion,cuentaBancaria);
		this.parentesco = parentesco;
		this.acompañantes = acompañantes;

	}

	// Contructor menores de edad
		//Los menores de edad no tienen CC, no pueden tener acompañantes y su ubicacion será determinada por su familiar responsable
	public Familiar(String nombre, int edad, String parentesco,Familiar responsable) {
		super(nombre,0,edad,null,null);
		this.parentesco=parentesco;
		this.responsable=responsable;
		
	}

	public boolean autorizar(ArrayList<Familiar> familiares) {
		if (parentesco == "conyugue") {
			int numero = (int) (Math.random() * 10);
			if (numero < 6) {
				return true;
			}
		}
		return false;
	}

	// metodos get y set

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}


	public int getAcompañantes() {
		return acompañantes;
	}

	public void setAcompañantes(int acompañantes) {
		this.acompañantes = acompañantes;
	}

	public Familiar getResponsable() {
		return responsable;
	}

	public void setResponsable(Familiar responsable) {
		this.responsable = responsable;
	}

}
