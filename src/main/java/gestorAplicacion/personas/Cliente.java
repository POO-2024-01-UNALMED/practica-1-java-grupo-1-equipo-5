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
		
		//autorizar procedimiento de exhumacion y cremacion del cliente
		public String autorizar() {
			
			for (int i=0; i<familiares.size();i++) {
				if(familiares.get(i).getCC()!=0) {
					if(familiares.get(i).getParentesco()=="conyugue" & numeroAutorizar()) {
						return "conyugue autoriza la solicitud";
					}
					if(familiares.get(i).getParentesco()=="hijo" & numeroAutorizar()) {
						return "hijo autoriza la solicitud";
					}
					if(familiares.get(i).getParentesco()=="padre" & numeroAutorizar()) {
						return "padre autoriza la solicitud";
					}
					if(familiares.get(i).getParentesco()=="hermano" & numeroAutorizar()) {
						return "Hermano autoriza la solicitud";
					}
					
				}//fin if principal
				
			} //fin ciclo for
			
			return "No se pudo autorizar la solicitud";
		}
		
		//apoyo a metodo autorizar
		private boolean numeroAutorizar() {
			int numero = (int) (Math.random() * 10);
			if (numero < 6) {
				return true;
			}
			return false;
			}
		
		//metodo cantidad familiares
		
		public int cantidadFamiliares() {
			int cantidadFamiliares=familiares.size();
			
			for (int i=0; i<familiares.size();i++) {
				if(familiares.get(i).getCC()!=0) {
					int cantidad=familiares.get(i).getAcompañantes();
					cantidadFamiliares=cantidadFamiliares+cantidad;
				}//fin if
				
			}//fin ciclo for
			
			return cantidadFamiliares;
		}
	
	
	
	
	
	//metodos get y set
	
	
	public String getAfiliacion() {
		return afiliacion;
	}
	public void setAfiliacion(String afiliacion) {
		this.afiliacion=afiliacion;
	}
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
