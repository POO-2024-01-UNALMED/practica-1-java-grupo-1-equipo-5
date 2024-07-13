package gestorAplicacion.personas;


import gestorAplicacion.financiero.*;

public class Empleado extends Persona{
	//Atributos
	private String jornada;
	private String cargo;
		
	//Constructor
	public Empleado(String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria,String jornada, String cargo) {
		super(nombre, CC, edad, ubicacion, cuentaBancaria);
		this.jornada=jornada;
		this.cargo=cargo;
	}
	
	
	
	
	
	//Metodos get y set
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo=cargo;
	}
	
	public String getJornada() {
		return jornada;
	}
	public void setJornada(String jornada) {
		this.jornada=jornada;
	}
	
}
