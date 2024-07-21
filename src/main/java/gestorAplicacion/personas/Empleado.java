package gestorAplicacion.personas;


import gestorAplicacion.financiero.*;

public class Empleado extends Persona{
	//Atributos
	private String jornada;
	private String cargo;
	private double salario;
		
	//Constructor
	public Empleado(String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria,String jornada, String cargo, Double salario) {
		super(nombre, CC, edad, ubicacion, cuentaBancaria);
		this.jornada=jornada;
		this.cargo=cargo;
		this.salario=salario;
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
	public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
	
}
