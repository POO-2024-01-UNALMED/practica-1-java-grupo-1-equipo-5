package gestorAplicacion.personas;

import java.util.ArrayList;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.*;

public class Cliente extends Persona {
	
	
	//Atributos
	
	private String afiliacion;
	private Crematorio crematorio;
	private Cementerio cementerio;
	private ArrayList<Familiar> familiares=new ArrayList<Familiar>();
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	
	
	//Contructor mayores de edad
	public Cliente(String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria,String afiliacion, ArrayList<Familiar> familiares) {
		super(nombre, CC, edad, ubicacion, cuentaBancaria);
		this.afiliacion=afiliacion;
		this.familiares=familiares;
	}
	
	//Contructor menores de edad
		public Cliente(String nombre,int edad, String ubicacion, String plan,ArrayList<Familiar> familiares) {
			this(nombre, 0, edad, ubicacion,null, plan,familiares);
		}
	
	
	
	
	
	
	
	//metodos get y set
	
	
	
	public Crematorio getCrematorio() {
		return crematorio;
	}
	public void setCrematorio(Crematorio crematorio) {
		this.crematorio=crematorio;
	}
	public Cementerio getCementerio() {
		return cementerio;
	}
	public void setCementerio(Cementerio cementerio) {
		this.cementerio=cementerio;
	}
	
	public ArrayList<Familiar> getFamiliares() {
		return familiares;
	}
	public void setFamiliares(ArrayList<Familiar> familiares) {
		this.familiares=familiares;
	}
	public ArrayList<Factura> getListadoFacturas() {
		return listadoFacturas;
	}
	public void setListadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
}
