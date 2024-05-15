package gestorAplicacion;

import java.util.ArrayList;

public class Cementerio {
	private String nombre;
	private String ubicacion;
	private int capacidad;
	private String tipo; //(cenizas o cuerpos)
	private String afiliacion; //oro-plata-bronce
	private ArrayList<Empleado> listadoEmpleados=new ArrayList<Empleado>();
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion=ubicacion;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad=capacidad;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}
	public String getAfiliacion() {
		return afiliacion;
	}
	public void setAfiliacion(String afiliacion) {
		this.afiliacion=afiliacion;
	}
	public ArrayList<Empleado> getListadoEmpleados(){
		return listadoEmpleados;
	}
	public void setListadoEmpleados(ArrayList<Empleado> listadoEmpleados) {
		this.listadoEmpleados=listadoEmpleados;
	}
	

}
