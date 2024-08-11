package gestorAplicacion.personas;

import java.util.ArrayList;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;

public class Cliente extends Persona {
	
	
	//Atributos
	
	private String afiliacion; //oro, plata y bronce
	//private Crematorio crematorio;
	//private Cementerio cementerio;
	private Inventario inventario; 
	private ArrayList<Persona> familiares=new ArrayList<Persona>();
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	
	
	//Contructor mayores de edad
	public Cliente(String nombre, long CC, int edad, CuentaBancaria cuentaBancaria,String afiliacion, ArrayList<Persona> familiares) {
		super(nombre, CC, edad, null, cuentaBancaria);
		this.afiliacion=afiliacion;
		this.familiares=familiares;
	}
	
	//Contructor menores de edad
		public Cliente(String nombre,int edad, String plan,ArrayList<Persona> familiares) {
			this(nombre, 0, edad,null, plan,familiares);
		}
		
		//autorizar procedimiento de exhumacion y cremacion del cliente
		public String autorizar() {
			ArrayList<Persona> familiares=this.familiares;
			Familiar familiarDesignado= this.designarFamiliar(familiares);
			String mensaje =" autoriza la solicitud";
			
			for(Persona auxFamiliar: familiares) {
				if(auxFamiliar instanceof Cliente) {
					mensaje= " del cliente "+auxFamiliar.getNombre()+" autoriza la solicitud";
					break;
				}
			}
			
			while (familiares.size()!=0) {
				if (familiarDesignado !=null) {
					if(numeroAutorizar() & familiarDesignado.getCC() !=0) {
					return familiarDesignado.getParentesco() + mensaje;
				}	else{
						familiares.remove(familiarDesignado);
						familiarDesignado=this.designarFamiliar(familiares);}
				
				}else {return "La solicitud no podrá ser autorizada";}
						
			}
			return 	"La solicitud no podrá ser autorizada";
			
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
				if(auxFamiliar instanceof Cliente) {
					cantidadFamiliares-=1;
				}
					
				}//Fin if principal
			}//fin ciclo for
			
			return cantidadFamiliares;
		}
		
		//Este método se empleará para asignar a familiares del cliente únicamente que tengan un parentesco directo con le nuevo cliente de conyuge, hijo, padre, hermano 
		
		public void asignarParentesco(Cliente cliente, String parentesco) {
			ArrayList<String> parentescos = new ArrayList<String>();
			//familiares.add(cliente);
			
			if(parentesco=="conyuge") {
				parentescos.add("hijo");
				
			}else if(parentesco=="hijo") {
				parentescos.add("conyuge");
				parentescos.add("hijo");
			
			}else if(parentesco=="padre") {
				parentescos.add("hermano");
				parentescos.add("padre");
			}else {
				parentescos.add("padre");
				parentescos.add("hermano");
			}
			
			for (String auxParentesco: parentescos) {
				for(Persona auxFamiliar: cliente.familiares) {
					if(auxFamiliar instanceof Familiar) {
						Familiar familiar= (Familiar) auxFamiliar;
						if(familiar.getParentesco()==auxParentesco) {
							familiares.add(familiar);
						}//fin if
					}//fin if principal
				}//fin for 
			}//fin for principal
			
			
			
		}
	
		
		public Familiar designarFamiliar(ArrayList<Persona> familiares) {
			
			String[] parentescos ={"conyuge","hijo","padre","hermano"};
			
			for(String parentesco:parentescos) {
				for(Persona auxFamiliar:familiares) {
					if(auxFamiliar instanceof Familiar ) {
						Familiar familiar=(Familiar)auxFamiliar;
						if(familiar.getParentesco()==parentesco ) {
							return familiar;
						}// fin if 
					}//fin if principal
				}//fin for
			}//Fin for principal
			return null;
			
		}
		
		public void agregarFactura(Factura factura) {
			listadoFacturas.add(factura);
		}
		
		
	
	
	
	
	//metodos get y set
	
	
	public String getAfiliacion() {
		return afiliacion;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public void setAfiliacion(String afiliacion) {
		this.afiliacion=afiliacion;
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
