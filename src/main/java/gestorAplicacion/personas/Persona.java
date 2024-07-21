package gestorAplicacion.personas;



import gestorAplicacion.financiero.*;

public class Persona {
	
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
	// Método para mostrar información de la persona
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
        System.out.println("Dirección: " + ubicacion);
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

}
