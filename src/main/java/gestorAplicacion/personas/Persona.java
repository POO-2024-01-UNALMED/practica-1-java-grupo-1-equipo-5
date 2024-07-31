package gestorAplicacion.personas;



import gestorAplicacion.financiero.*;

public abstract class Persona {
	
	private String nombre;
	private final long CC;
	private int edad;
	private String ubicacion;
	private CuentaBancaria cuentaBancaria;
	
	
	
	public Persona(String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria) {
		this.nombre=nombre;
		this.CC=CC;
		this.edad=edad;
		this.ubicacion=ubicacion;
		this.cuentaBancaria=cuentaBancaria;
	
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
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion=ubicacion;
	}
	public CuentaBancaria getCuentaBancaria() {
		return this.cuentaBancaria;
	}
	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

}
