package gestorAplicacion.establecimientos;
import gestorAplicacion.inventario.*;


import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import gestorAplicacion.inventario.*;

import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.financiero.Factura;
import gestorAplicacion.personas.*;


public class Cementerio extends Establecimiento implements Serializable {
	
	private String tipo; //(cenizas o cuerpos)
	
	//En inventario se pueden agregar objetos de la clase Urna para los objetos cementerios de tipo cenizas y objetos Tumba para los cementerios de tipo Tumba
	private ArrayList<Inventario> inventario = new ArrayList<Inventario>();
	private static final long serialVersionUID = 1L;

	//Constructor
	public Cementerio(String nombre, int capacidad, CuentaBancaria cuentaCorriente,String afiliacion, Empleado empleado,String tipo,Funeraria funeraria) {
			super(nombre,capacidad,cuentaCorriente,afiliacion,empleado,funeraria);
			this.tipo=tipo;
		}
	
	
	//Recibe como primer parámetro un arreglo de tipo Establecimiento y cada elemento del arreglo debe tener también el tipo Cementerio y
	//como segundo parámetro recibe un String que debe tener como valor "cenizas" o "cuerpos"
	//Este método sirve para filtrar los objetos Cementerio por su tipo que debe ser "cenizas" o "cuerpos"
	//Devuelve un arrreglo de tipo Establecimiento 
	public static ArrayList<Establecimiento> cementerioPorTipo(ArrayList<Establecimiento> cementerios,String tipo){
		
		ArrayList <Establecimiento> cementeriosDisponibles= new ArrayList<Establecimiento>();
		
		for(int i=0;i<cementerios.size();i++) {
			
			//Especificación del objeto para poder evaluar el atributo tipo
			Cementerio cementerio = (Cementerio) cementerios.get(i);
			
			if (cementerio.getTipo()==tipo) {
				cementeriosDisponibles.add(cementerio);
			}//fin if
			
		}//fin for
		
		return cementeriosDisponibles;
	}
	
	//Recibe como primer parámetro un String que puede tomar el valor de "urna" o "tumba" además, 
	//recibe el tamaño (peso o estatura) del cliente y su atributo edad
	//Devuelve un arreglo de tipo Inventario con los objetos también de tipo Urna o Tumba seleccionados 
	public ArrayList<Inventario> disponibilidadInventario(String urnaTumba, double tamaño, int edad){
		
		ArrayList<Inventario> inventarioDisponible=new ArrayList<Inventario>();
		ArrayList<Inventario> auxInventario = new ArrayList<Inventario>();
		
		if(urnaTumba.equals("tumba")) {
			auxInventario=inventario;
		}else {
			//Devuelve un arreglo de tipo Inventario que también es de tipo Urna y 
			//se las selecciona de acuerdo al tipo del Objeto Urna y a la iglesia del cementerio
			auxInventario=this.tipoUrna();
		}
		
		for(Inventario auxUrnaTumba: auxInventario) {
	
			if (auxUrnaTumba.getCliente()==null & auxUrnaTumba.getTamaño()>=auxUrnaTumba.determinarTamaño(tamaño) & 
					auxUrnaTumba.getCategoria()==auxUrnaTumba.determinarCategoria(edad)) { 
				inventarioDisponible.add(auxUrnaTumba);
			}//Fin if
		}//Fin for 
		return inventarioDisponible;
	}
	
	//Recibe un arreglo de tipo Inventario filtrado de acuerdo a las especificaciones del cliente
	//El método pretende retornar el objeto de tipo Inventario que tenga el menor tamaño
	public Inventario inventarioRecomendado(ArrayList<Inventario> inventario) {
		
		
		if (inventario.size()==0) {
            return null;
        }
		
		double menor = inventario.get(0).getTamaño();
		Inventario recomendado=inventario.get(0);
		inventario.remove(0);
		
		for(Inventario auxInventario:inventario) {
			if(auxInventario.getTamaño()<menor) {
				menor=auxInventario.getTamaño();
				recomendado=auxInventario;
			}
		}
		
		 return recomendado;
        
        }
	
	
       
	
	
	
	
	
	public ArrayList<Inventario> tipoUrna(){
		
		Iglesia iglesia=this.getIglesia();
		
		if(iglesia == Iglesia.BUDISMO) {
			return inventario;
		}else if(iglesia ==Iglesia.CRISTIANISMO) {
			return inventario;
			
		}else if (iglesia ==Iglesia.HINDUISMO) {
			return urnasPorTipo("ordinaria");
		}else if(iglesia==Iglesia.TAOISMO) {
			return urnasPorTipo("fija");
		}else{return inventario;}
	
	}
	
	//Este método sirve para filtrar del arreglo de inventario los objetos de tipo Inventario 
	//que tenga como atributo nombre "default" y tengan asociado un Cliente
	public ArrayList<Inventario> inventarioDefault(){
		
		ArrayList<Inventario> porDefecto=new ArrayList<Inventario>();
		ArrayList<Inventario> auxInventario=new ArrayList<Inventario>();
		
		if(this.tipo.equals("cenizas")) {auxInventario=urnasPorTipo("fija");
		
		}else {auxInventario=inventario;}
		
		for(Inventario urnaTumba: auxInventario) {
			if(urnaTumba.getNombre().equals("default") & urnaTumba.getCliente()!=null) {
				porDefecto.add(urnaTumba);
			}
			
		}//Fin for
		return porDefecto;
		
	}
	
	public ArrayList<Inventario> urnasPorTipo(String tipo){
		
		ArrayList<Inventario> urnasPorTipo =new ArrayList<Inventario>();
		
		for(Inventario auxInventario:inventario) {
			if(auxInventario instanceof Urna) {
				Urna urna = (Urna)auxInventario;
				if(urna.getTipo().equals(tipo)) {
					urnasPorTipo.add(urna);
				}//Fin if
			}//Fin if principal
		}//Fin for
		
		return urnasPorTipo;
	} 
	
	//Recibe un parámetro tipo Cliente 
	//El método se emplea para retornar un String con la organización del evento Iglesia 
	public String organizarIglesia(Cliente cliente) {
		
		ArrayList<Familiar> familiares =new ArrayList<Familiar>();
		
		for(Familiar familiar: cliente.getFamiliares()) {
			familiares.add(familiar);
		}
		int sillas=getIglesia().getSillas();
	
		String organizacion="";
		ArrayList<Producto> productos = new ArrayList<Producto>();
		
		cliente.getInventario().generarAdornos("flores");
		
		int contador=1;
		while(familiares.size()!=0 & sillas!=0) {
			//Retorna al familiar con parentezco más cernao
			Familiar familiar = cliente.designarFamiliar(familiares);
			String flor=cliente.getInventario().getInventarioFlores().get(0);
			organizacion+="Silla ["+contador+"] - Familiar "+familiar+" Flores para decorar silla -  "+ flor+"\n";
			
			//AgregarProducto a la lista
			productos.add(new Producto(flor,Inventario.precios(flor),1));
			//Borrar a familiar designado
			familiares.remove(familiar);
			//Borrar adorno
			cliente.getInventario().getInventarioFlores().remove(flor);
			
			sillas-=1;
			contador+=1;
	}
		cliente.agregarFactura(new Factura(productos));
		return organizacion;	
	}
	
	
	
	//Método para agregar al ArrayList de inventario (En caso de los cementerios de cuerpos se agregarán tumbas y en caso de los cementerios de cenizas se agregarán urnas)
	public void agregarInventario(Inventario inventario) {
		this.inventario.add(inventario);
	}	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo=tipo;
	}

	public ArrayList<Inventario> getInventario() {
		return inventario;
	}



	public void setInventario(ArrayList<Inventario> inventario) {
		this.inventario = inventario;
	}
	

	

}
