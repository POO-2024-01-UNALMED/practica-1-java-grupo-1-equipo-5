package gestorAplicacion.personas;


import gestorAplicacion.financiero.*;

public class Empleado extends Persona{
	//Atributos
	private String jornada; //mañana - tarde - noche
	private String cargo; //Cargos disponibles sepulturero - cremador - padre
	private double salario;
	private int Calificacion=5;
	private boolean disponible= true;
	
	
		
	//Constructor
	public Empleado(String nombre, long CC, String ubicacion, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario) {
		super(nombre, CC, 0, ubicacion, cuentaBancaria);
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
    
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
	
}
