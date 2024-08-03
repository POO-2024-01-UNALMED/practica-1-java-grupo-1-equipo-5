package gestorAplicacion.inventario;

import java.time.LocalTime;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Persona;

public class Producto {

	// Atributos
    private String nombre;
    private double precio;
    private int cantidad;

    private Establecimiento establecimiento;

    private int cantidadVendida=0;

    
    
    
    
    public Producto(String nombre, double precio, int cantidad) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
	}
    

    //Constructor para crear productos con establecimientos (Crematorio y Cementerio)
    public Producto(Establecimiento establecimiento) {
    	this.establecimiento=establecimiento;
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
    	}else {concepto="Entrega de cenizas";}
    	
    	for(Persona familiar:cliente.getFamiliares()) {
    		familiares=familiares+familiar+"\n";
    	}
    	
    	
    	
    	String evento=
    			"Asunto: Invitación a la Ceremonia de "+concepto+" de "+cliente+
    			"\nInvita\n"+familiares+
    			"\n Hora de la Ceremonia: "+hora+
    			"\n Lugar de Cremación "+establecimiento.getNombre()+
    			"\n Dirección: "+establecimiento.getUbicacion()+
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
    
}
