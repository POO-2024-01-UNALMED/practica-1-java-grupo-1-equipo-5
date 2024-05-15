package gestorAplicacion;

public class Familiar extends Persona{
	
	
	private String parentesco;
	private boolean estado;
	
	private int acompañantes;
	private Familiar responsable;
	
	
	
	
	public boolean autorizar() {
		if (parentesco == "conyugue" || parentesco =="hijo" || parentesco =="hija" || parentesco =="madre" || parentesco =="padre") {
			int numero= (int) (Math.random()*10);
			if (numero<6) { return true; } }
		return false;
	}
	
	//metodos get y set 
	
	
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
