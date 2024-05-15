package gestorAplicacion;

public class Empleado extends Persona{
	//Atributos
	
		private boolean estado;
		private String cargo;
		
		//Metodos get y set
		
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
