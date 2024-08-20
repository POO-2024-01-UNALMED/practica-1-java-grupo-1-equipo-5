package gestorAplicacion.personas;

import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;

public class Cliente extends Persona implements Serializable {
	
	
	//Atributos
	
	private String afiliacion; //oro, plata y bronce
	//private Crematorio crematorio;
	//private Cementerio cementerio;
	private Inventario inventario; 
	private ArrayList<Familiar> familiares=new ArrayList<Familiar>();
	private ArrayList<Factura> listadoFacturas=new ArrayList<Factura>();
	private static final long serialVersionUID = 1L;
	
	
	//Contructor mayores de edad
	public Cliente(String nombre, long CC, int edad, CuentaBancaria cuentaBancaria,String afiliacion, ArrayList<Familiar> familiares) {
		super(nombre, CC, edad, cuentaBancaria);
		this.afiliacion=afiliacion;
		this.familiares=familiares;
	}
	
	//Contructor menores de edad
		public Cliente(String nombre,int edad, String plan,ArrayList<Familiar> familiares) {
			this(nombre, 0, edad,null, plan,familiares);
		}
		
		
		
		//Devuelve la cantidad de Familiares y acompañantes que tiene cada Familiar 
		public int cantidadFamiliares() {
			int cantidadFamiliares=familiares.size();
			
			//Por cada familiar de ArrayList<Familiar> familiares se suma su atributo acompañantes  
			for(Persona auxFamiliar: familiares) {
				if(auxFamiliar.getCC()!=0 & auxFamiliar instanceof Familiar) {
					int cantidad = ((Familiar) auxFamiliar).getAcompañantes();
					cantidadFamiliares=cantidadFamiliares+cantidad;
					
				}//Fin if principal
			}//fin ciclo for
			
			return cantidadFamiliares;
		}
		
			
		//}
	
		//Este método retorna al familiar con el parentesco más cercano 
		public Familiar designarFamiliar(ArrayList<Familiar> familiares) {
			
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
		
		
	public static ArrayList<Familiar> familiaresPorEdad(String adultoNiño,ArrayList<Familiar> familiares){
		
		ArrayList<Familiar> familiaresFiltrados = new ArrayList<Familiar>();
		
		for(Familiar familiar: familiares) {
 			if(familiar.getCC()==0 && adultoNiño.equals("niño") ) {
 				familiaresFiltrados.add(familiar);
 				System.out.println("Menores ");
 			}else if(familiar.getCC()!=0 && adultoNiño.equals("adulto"))
 			{familiaresFiltrados.add(familiar);}
 			
	}
		return familiaresFiltrados;
	} 
	
	//Recibe un parámetro tipo String con valor "flores" o con valor "material"
	//Sirve para generar un String con el resumen de los adornos generados
	public String pagoInmediato(String tipoAdorno) {
		
		//Se asocia al tipo más específico de Inventario (Es un método abstracto)
		inventario.generarAdornos(tipoAdorno);
		ArrayList<String> arreglo;
		ArrayList<Producto> productos=new ArrayList<Producto>();
		ArrayList<Familiar> familiaresA=new ArrayList<Familiar>();
		
		for(Familiar familiar: this.familiares) {
			familiaresA.add(familiar);
		}
		
		
		Familiar familiarDesignado = null;
		boolean validacionPago=false;
		String pagoInmediato="";
		
		if(tipoAdorno.equals("flores")) {
			arreglo=inventario.getInventarioFlores();
		}else {arreglo=inventario.getInventarioMaterial();}
		
		for(String inventario: arreglo) {
			productos.add(new Producto(inventario,Inventario.precios(inventario),1));
		}
		
		Factura factura=new Factura(productos);
		
		double total=factura.totalFactura();
		
		while(validacionPago==false) {
			familiarDesignado = designarFamiliar(familiaresA);
			
			if (familiaresA.size()==0 || familiarDesignado==null) { return 
					"No es posible hacer el cobro de la factura. No se podrán agregar adornos";}
			
			if(familiarDesignado.getCC()!=0 && familiarDesignado.getCuentaBancaria().getSaldo()>=total) {
				familiarDesignado.getCuentaBancaria().transaccionCuentaAhorros(total, 
						inventario.getCementerio().getFuneraria().getCuentaAhorros());
				validacionPago=true;
			}//Fin if
			
			else{familiaresA.remove(familiarDesignado);}
			
			
			
		}
		
		return pagoInmediato=
				"Descrpción: Pago Adornos Entierro\n"
				    + "Familiar: "+familiarDesignado+"\n"
				    + factura.retornarFactura();
		
		
		
		
	}
	
	
	
	//metodos get y set
	
	
	public String getAfiliacion(){
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
