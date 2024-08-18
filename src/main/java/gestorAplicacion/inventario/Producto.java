package gestorAplicacion.inventario;

import java.time.LocalTime;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Familiar;
import gestorAplicacion.personas.Persona;

public class Producto {

	// Atributos
    private String nombre;
    private double precio;
    private int cantidad;

    private Establecimiento establecimiento;
    private Vehiculo vehiculo;
    private Urna urna;
    private Tumba tumba;
    
    private int cantidadVendida=0;

    
    
    
    
    public Producto(String nombre, double precio, int cantidad, int cantidadVendida) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.cantidadVendida = cantidadVendida;
	}
	public Producto(String nombre, double precio, int cantidad) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
	}
    public Producto(String nombre, double precio, int cantidad, Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
	}
    

    //Constructor para crear productos con establecimientos (Crematorio y Cementerio)
    public Producto(Establecimiento establecimiento) {
    	this.establecimiento=establecimiento;
    	this.nombre=establecimiento.getNombre();
    	this.cantidad=1;

    }public Producto(Urna urna, int cantidadFlores){
    	this.urna = urna;
    	this.precio = urna.precioTotal(cantidadFlores);
    }
    public Producto(Tumba tumba, int cantidadFlores){
    	this.tumba = tumba;
    	this.precio = tumba.precioTotal(cantidadFlores);
    }
    
    public Producto(Vehiculo vehiculo) {
    	this.vehiculo=vehiculo;
    	this.nombre=vehiculo.getTipoVehiculo().name();
    	this.precio=vehiculo.getTipoVehiculo().getPrecio();
    	this.cantidad = 1;
    	
    }
    public Producto(Vehiculo vehiculo, Establecimiento establecimiento) {
    	this.vehiculo=vehiculo;
    	this.establecimiento = establecimiento;
    	this.nombre=vehiculo.getTipoVehiculo().name();
    	this.precio=vehiculo.getPrecio();
    	this.cantidad = 1;
    	
    }
    
    
    //Descripción del evento (Cremación o Entierro)
    public String evento(Cliente cliente) {
    	String concepto=null;
    	LocalTime hora=establecimiento.getHoraEvento();
    	String nombreIglesia=establecimiento.getIglesia().getNombre();;
    	String familiares="";
    	
    	if (establecimiento instanceof Crematorio) {
    		Crematorio crematorio=(Crematorio) establecimiento;
    		concepto="Cremación";
    		
    	}else if(establecimiento instanceof Cementerio) {
    		Cementerio cementerio=(Cementerio) establecimiento;
    		if( ((Cementerio)establecimiento).getTipo()=="cuerpos")
    			concepto="Entierro";
    	}else {concepto="Entierro de cenizas";}
    	
    	for(Persona familiar:cliente.getFamiliares()) {
    		familiares=familiares+familiar+"\n";
    	}
    	
    	
    	
    	String evento=
    			"Asunto: Invitación a la Ceremonia de "+concepto+" de "+cliente+
    			"\nInvita\n"+familiares+
    			"\n Hora de la Ceremonia: "+hora+
    			"\n Lugar de Cremación "+establecimiento.getNombre()+
    			"\n Centro religioso: "+nombreIglesia;
    	
    	return evento;
    	
    }
    
   
    
    
    
    
 
 // Constructor que acepta nombre y cantidadVendida
    public Producto(String nombre, int cantidadVendida) {
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
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
    
}
