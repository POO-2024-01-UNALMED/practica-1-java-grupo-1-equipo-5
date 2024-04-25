package gestorAplicacion;

public class Familiar {
	private String nombre;
	private final long CC;
	private int edad;
	private String parentesco;
	private boolean estado;
	private String ubicacion;
	private int acompañantes;
	private Familiar responsable;
	
	//metodos get y set 
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
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco=parentesco;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado=estado;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion=ubicacion;
	}
	public int getAcompañantes() {
		return acompañantes;
	}
	public void setAcompañantes(int acompañantes) {
		this.acompañantes=acompañantes;
	}
	public Familiar getResponsable() {
		return responsable;
	}
	public void setResponsable(Familiar responsable) {
		this.responsable=responsable;
	}
	

}
