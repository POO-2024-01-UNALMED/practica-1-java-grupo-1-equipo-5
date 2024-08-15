package gestorAplicacion.personas;


import gestorAplicacion.financiero.*;

public class Empleado extends Persona{
	//Atributos
	private String jornada; //mañana - tarde - noche
	private String cargo; //Cargos disponibles sepulturero - cremador - padre
	private double salario;
	private int Calificacion=5;
	private int Experiencia;
	private int trabajosHechos;
	private boolean disponible= true;
	private String descripcionCalificacion; // Se utiliza al momento de las encuestas
	
	
		
	//Constructor
	public Empleado(String nombre, long CC, String ubicacion, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario) {
		super(nombre, CC, 0, ubicacion, cuentaBancaria);
		this.jornada=jornada;
		this.cargo=cargo;
		this.salario=salario;
	}
	
	public Empleado(String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario, int Experiencia,int trabajosHechos) {
		super(nombre, CC, edad, ubicacion, cuentaBancaria);
		this.jornada=jornada;
		this.cargo=cargo;
		this.salario=salario;
		this.Experiencia= Experiencia;
		this.trabajosHechos = trabajosHechos;
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
        if(!this.disponible && disponible) {
        	this.trabajosHechos++;
        }
    	this.disponible = disponible;
    }

	public int getCalificacion() {
		return Calificacion;
	}

	public void setCalificacion(int calificacion) {
		Calificacion = calificacion;
	}

	public int getExperiencia() {
		return Experiencia;
	}

	public void setExperiencia(int experiencia) {
		Experiencia = experiencia;
	}

	public String getDescripcionCalificacion() {
		return descripcionCalificacion;
	}

	public void setDescripcionCalificacion(String descripcionCalificacion) {
		this.descripcionCalificacion = descripcionCalificacion;
	}
	
	
	public int getTrabajosHechos() {
		return this.trabajosHechos;
	}
	
	public void setTrabajosHechos(int trabajosHechos) {
		
		this.trabajosHechos = trabajosHechos;
	}
	
}
