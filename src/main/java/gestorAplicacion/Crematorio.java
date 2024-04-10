package gestorAplicacion;

import java.util.ArrayList;


public class Crematorio {
	//Atributos
	private String nombre;
	private String ubicacion;
	private int sillas;
	private ArrayList<Cliente> listadoClientes=new ArrayList<Cliente>();
	private static ArrayList<Encargado> listadoEncargados=new ArrayList<Encargado>();
	private static ArrayList<Transporte> listadoTransportes=new ArrayList<Transporte>();
	
	//Métodos get y set 
	
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
	
	public int getSillas() {
		return sillas;
	}
	public void setSillas(int sillas) {
		this.sillas=sillas;
	}
	
	public ArrayList<Cliente> getListadoClientes(){
		return listadoClientes;
	}

	
	//Métodos get y set estáticos
	
	public static ArrayList<Encargado> getListadoEncargados(){
		return listadoEncargados;
	}
	public static ArrayList<Transporte> getListadoTransportes(){
		return listadoTransportes;
	}
	
}
