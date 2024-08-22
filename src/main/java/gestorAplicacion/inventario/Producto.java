/* Autores Violeta Gómez, Andrés Perez, Sebastian Guerra
 * Tiene la funcionalidad de simular un Producto que contenga 
 * atributos claves como nombre, cantidad y precio como apoyo a la clase Factura*/


package gestorAplicacion.inventario;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.personas.Persona;

public class Producto implements Serializable {

	// Atributos
    private String nombre;
    private double precio;
    private int cantidad;

    private Establecimiento establecimiento;
    private Vehiculo vehiculo;
    private Urna urna;
    private Tumba tumba;
    
    private int cantidadVendida=0;
    private static final long serialVersionUID = 1L;
    
    private static ArrayList<Producto> productos=new ArrayList<Producto>();

    
    
    
    
    public Producto(String nombre, double precio, int cantidad, int cantidadVendida) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.cantidadVendida = cantidadVendida;
		productos.add(this);
	}
	public Producto(String nombre, double precio, int cantidad) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		productos.add(this);
	}
    public Producto(String nombre, double precio, int cantidad, Establecimiento establecimiento) {
    	this(nombre,precio,cantidad);
		this.establecimiento = establecimiento;
		productos.add(this);
	}
    

    //Constructor para crear productos con establecimientos (Crematorio y Cementerio)
    public Producto(Establecimiento establecimiento) {
    	this.establecimiento=establecimiento;
    	this.nombre=establecimiento.getNombre();
    	this.cantidad=1;
    	productos.add(this);

    }public Producto(Urna urna, int cantidadFlores){
    	this.urna = urna;
    	this.precio = urna.precioTotal(cantidadFlores);
    	this.cantidad = 1;
    	productos.add(this);
    }
    public Producto(Tumba tumba, int cantidadFlores){
    	this.tumba = tumba;
    	this.precio = tumba.precioTotal(cantidadFlores);
    	this.cantidad = 1;
    	productos.add(this);
    }
    
    public Producto(Vehiculo vehiculo) {
    	this.vehiculo=vehiculo;
    	this.nombre=vehiculo.getTipoVehiculo().name();
    	this.precio=vehiculo.getTipoVehiculo().getPrecio();
    	this.cantidad = 1;
    	productos.add(this);
    	
    }
    public Producto(Vehiculo vehiculo, Establecimiento establecimiento) {
    	this.vehiculo=vehiculo;
    	this.establecimiento = establecimiento;
    	this.nombre=vehiculo.getTipoVehiculo().name();
    	this.precio=vehiculo.getPrecio();
    	this.cantidad = 1;
    	productos.add(this);
    }
    
    
    //Descripción del evento (Cremación o Entierro)
    public String evento(Cliente cliente) {
    	String concepto=null;
    	LocalTime hora=establecimiento.getHoraEvento();
    	String nombreIglesia=establecimiento.getIglesia().getNombre();;
    	String familiares="";
    	
    	//Si el producto tiene asociado un objeto de tipo Establecimiento que a su vez
    	//es de tipo Crematorio el evento es una Cremación  
    	if (establecimiento instanceof Crematorio) {
    		Crematorio crematorio=(Crematorio) establecimiento;
    		concepto="Cremación";
    	
    	//Si el producto tiene asociado un objeto de tipo Establecimiento que a su vez 
    	//es de tipo Cementerio el evento es un Entierro  
    	}else if(establecimiento instanceof Cementerio) {
    		Cementerio cementerio=(Cementerio) establecimiento;
    		if( ((Cementerio)establecimiento).getTipo()=="cuerpos")
    			concepto="Entierro";
    	}else {concepto="Entierro de cenizas";}
    	
    	for(Persona familiar:cliente.getFamiliares()) {
    		familiares=familiares+familiar+"\n";
    	}
    	
    	//Resume de los datos del evento guardados en el objeto de tipo Establecimiento
    	String evento=
    			"Asunto: Invitación a la Ceremonia de "+concepto+" de "+cliente+
    			"\nInvita\n"+familiares+
    			"\n Hora de la Ceremonia: "+hora+
    			"\n Lugar de "+concepto+": " +establecimiento.getNombre()+
    			"\n Centro religioso: "+nombreIglesia;
    	return evento;
    	
    }
    
   
    
    
    
    
 
 // Constructor que acepta nombre y cantidadVendida
    public Producto(String nombre, int cantidadVendida) {
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
        productos.add(this);
    }

	// Métodos getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

	public int getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(int cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}
	
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
	
	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}


	public Vehiculo getVehiculo() {
		return vehiculo;
	}


	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	public static ArrayList<Producto> getProductos(){
		return productos;
	}
    
}
