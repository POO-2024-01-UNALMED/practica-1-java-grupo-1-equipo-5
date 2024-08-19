package gestorAplicacion.personas;


import java.io.Serializable;

import gestorAplicacion.establecimientos.Funeraria;
import gestorAplicacion.financiero.*;

public class Empleado extends Persona implements Serializable{
	//Atributos
	private String jornada; //mañana - tarde - noche
	private String cargo; //Cargos disponibles sepulturero - cremador - padre
	private double salario;
	private int Calificacion=5;
	private int Experiencia;
	private int trabajosHechos;
	private boolean disponible= true;
	private Funeraria funeraria;
	private String descripcionCalificacion; // Se utiliza al momento de las encuestas
	 private static final long serialVersionUID = 1L;
	
	
		
	//Constructor
	public Empleado(String nombre, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario,Funeraria funeraria) {
		super(nombre, 0, 0,  cuentaBancaria);
		this.jornada=jornada;
		this.cargo=cargo;
		this.salario=salario;
		if (funeraria != null) {
			funeraria.agregarEmpleado(this);
		}
		
	}
	public Empleado(String nombre, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario) {
		super(nombre, 0, 0,  cuentaBancaria);
		this.jornada=jornada;
		this.cargo=cargo;
		this.salario=salario;
	}
	
	public Empleado(String nombre,int edad, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario, int Experiencia,int trabajosHechos) {
		super(nombre, 0, edad, cuentaBancaria);
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
