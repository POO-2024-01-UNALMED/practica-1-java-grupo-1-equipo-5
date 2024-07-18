package gestorAplicacion.personas;

import java.util.ArrayList;


import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.Inventario;

public class Cliente extends Persona {
	
	
	//Atributos
	
	private String afiliacion;
	//private Crematorio crematorio;
	//private Cementerio cementerio;
	private Inventario inventario; //(tumba o urna según corresponda)
	private ArrayList<Persona> familiares=new ArrayList<Persona>();
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	
	
	//Contructor mayores de edad
	public Cliente(String nombre, long CC, int edad, String ubicacion, CuentaBancaria cuentaBancaria,String afiliacion, ArrayList<Persona> familiares) {
		super(nombre, CC, edad, ubicacion, cuentaBancaria);
		this.afiliacion=afiliacion;
		this.familiares=familiares;
	}
	
	//Contructor menores de edad
		public Cliente(String nombre,int edad, String ubicacion, String plan,ArrayList<Persona> familiares) {
			this(nombre, 0, edad, ubicacion,null, plan,familiares);
		}
		
		//autorizar procedimiento de exhumacion y cremacion del cliente
		public String autorizar() {
			
			for(Persona auxFamiliar: familiares) {
				if(auxFamiliar.getCC()!=0 & auxFamiliar instanceof Familiar) {
					String parentesco= ((Familiar) auxFamiliar).getParentesco();
					if(parentesco=="conyugue" & numeroAutorizar()) {
						return "conyugue autoriza la solicitud";
					}
					if(parentesco=="hijo" & numeroAutorizar()) {
						return "hijo autoriza la solicitud";
					}
					if(parentesco=="padre" & numeroAutorizar()) {
						return "padre autoriza la solicitud";
					}
					if(parentesco=="hermano" & numeroAutorizar()) {
						return "Hermano autoriza la solicitud";
					}
					
				}//fin if principal
			}//fin for
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
			
			for(Persona auxFamiliar: familiares) {
				if(auxFamiliar.getCC()!=0 & auxFamiliar instanceof Familiar) {
					int cantidad = ((Familiar) auxFamiliar).getAcompañantes();
					cantidadFamiliares=cantidadFamiliares+cantidad;
				}//Fin if
			}//fin ciclo for
			
			return cantidadFamiliares;
		}
		
		public void asignarParientesco(Cliente cliente, String parentesco) {
			
			
			
		}
	
	
	
	
	
	//metodos get y set
	
	
	public String getAfiliacion() {
		return afiliacion;
	}
	public void setAfiliacion(String afiliacion) {
		this.afiliacion=afiliacion;
	}
	public Inventario getInventario() {
		return inventario;
	}
	public void setInventario(Inventario inventario) {
		this.inventario=inventario;
	}
	//public Cementerio getCementerio() {
		//return cementerio;
	//}
	//public void setCementerio(Cementerio cementerio) {
		//this.cementerio=cementerio;
	//}
	
	public ArrayList<Persona> getFamiliares() {
		return familiares;
	}
	public void setFamiliares(ArrayList<Persona> familiares) {
		this.familiares=familiares;
	}
	public ArrayList<Factura> getListadoFacturas() {
		return listadoFacturas;
	}
	public void setListadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
}
