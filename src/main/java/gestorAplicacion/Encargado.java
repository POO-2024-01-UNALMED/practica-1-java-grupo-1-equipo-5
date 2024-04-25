package gestorAplicacion;

public class Encargado {
	//Atributos
	private String nombre;
	private final long CC;
	private boolean estado;
	private String cargo;
	
	//Metodos get y set
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public long getCC() {
		return CC;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado=estado;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo=cargo;
	}
	

}
