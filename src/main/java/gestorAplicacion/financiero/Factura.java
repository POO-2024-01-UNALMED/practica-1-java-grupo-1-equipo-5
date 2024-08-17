package gestorAplicacion.financiero;
import gestorAplicacion.personas.*;

import gestorAplicacion.establecimientos.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gestorAplicacion.inventario.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;


public class Factura implements Serializable{
	//Atributos
	private final int ID;
	private String producto;
	private double precio;
	private double total;
	private Persona cliente;
	private Establecimiento Entidad;
	private String Fecha;
	private double precioFinal;
	private List<Producto> listaProductos;
	private double porcentajeCreditoPorPagar = 1.0;
	private String Servicio ;
	


	private final double IVA=0.19;
	private static int facturasCreadas;
	private static ArrayList<Factura> facturas=new ArrayList<Factura>();
	
	
	
	//Constructor para crear la factura con clientes 
	
	public Factura(String producto, double precio, String Fecha, Persona cliente,String Servicio) {
		this.producto=producto;
		this.precio=precio;
		this.cliente= cliente;
		this.Fecha=Fecha;
		this.listaProductos = new ArrayList<>();
		this.Servicio= Servicio;
		facturasCreadas++;
		ID=facturasCreadas;
		calcularTotal();
		facturas.add(this);
		
	}
	
	
	public Factura(String servicio) {
		facturasCreadas++;
		ID=facturasCreadas;
		this.listaProductos = new ArrayList<>();
		Servicio = servicio;
		this.total= 0;
		facturas.add(this);
	}


	public Factura(String producto, double precio, String Fecha, Establecimiento Entidad,String Servicio) {
		this.producto=producto;
		this.precio=precio;
		this.Fecha=Fecha;
		this.Entidad= Entidad;
		this.Servicio= Servicio;
		facturasCreadas++;
		ID=facturasCreadas;
		calcularTotal();
		facturas.add(this);
		
	}
	public Factura(ArrayList<Producto> productos) {
		listaProductos=productos;
		ajustarProductos();
		facturasCreadas++;
		ID=facturasCreadas;
		calcularTotal();
		facturas.add(this);
	}
	public Factura(ArrayList<Producto> productos, String servicio) {
		listaProductos=productos;
		this.Servicio = servicio;
		facturasCreadas++;
		ID=facturasCreadas;
		calcularTotal();
		facturas.add(this);
	}
	
	
	public void ajustarProductos() {
		
		//Nueva lista de productos
		ArrayList<Producto> productos=new ArrayList<Producto>();
		
		// Usar un HashMap para contar las ocurrencias de cada nombre
        Map<String, Integer> nameCountMap = new HashMap<>();
        
        for (Producto producto : listaProductos) {
            String nombre = producto.getNombre();
            nameCountMap.put(nombre, nameCountMap.getOrDefault(nombre, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entrada : nameCountMap.entrySet()) {
        	productos.add(new Producto(entrada.getKey(),Inventario.precios(entrada.getKey()),entrada.getValue()));
 
        }//Fin for
        
        listaProductos=productos;
	}
	
	public double totalFactura() {
		total=0;
		for(Producto producto:listaProductos) {
			total+=producto.getPrecio()*producto.getCantidad();
		}
		
		return total;
	}
	
	public String retornarFactura() {
		
		String factura="";
		for(Producto producto:listaProductos) {
			factura+="Concepto: "+producto.getNombre()+" - Precio unitario: "+producto.getPrecio()+" - Cantidad: "+producto.getCantidad()+"\n";
		}
		factura+="\n Total: "+totalFactura();
		return factura;
	}
		

	        

	      
	
	
	
	
	
	// Método para aplicar un descuento al precio de la factura
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje > 0 && porcentaje <= 100) {
        	precio -= precio * (porcentaje / 100);
        }
    }
    // Método para calcular el total con IVA
    private void calcularTotal() {
        total = precio + (precio * IVA);
    }
    // Método para agregar un producto a la lista
    public void agregarProducto(Producto producto) {
        listaProductos.add(producto);
        precio += producto.getPrecio() * producto.getCantidad();
        calcularTotal();
    }
    
    

    public List<Producto> obtenerProductosOrdenadosPorPrecio() {
        return listaProductos.stream()
                             .sorted(Comparator.comparingDouble(Producto::getPrecio).reversed())
                             .collect(Collectors.toList());
    }

    
    
	
	//metodos get y set 
    
    public static ArrayList<Factura> getFacturas(){
    	return facturas;
    }

    
	public int getID() {
		return ID;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto=producto;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio=precio;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total ) {
		this.total=total;
	}
	public double getIVA() {
		return IVA;
	}

	public Persona getCliente() {
		return cliente;
	}
	public void setCliente(Persona cliente) {
		this.cliente = cliente;
	}
	public Establecimiento getEntidad() {
		return Entidad;
	}
	public void setEntidad(Establecimiento entidad) {
		Entidad = entidad;
	}
	public List<Producto> getListaProductos() {
        return listaProductos;
    }

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

	public double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	public static int getFacturasCreadas() {
		return facturasCreadas;
	}

	public static void setFacturasCreadas(int facturasCreadas) {
		Factura.facturasCreadas = facturasCreadas;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public String getServicio() {
		return Servicio;
	}

	public void setServicio(String servicio) {
		Servicio = servicio;
	}
	
	public double getPorcentajeCreditoPorPagar() {
		return this.porcentajeCreditoPorPagar;
	}
	
	public void setPorcentajeCreditoPorPagar(double porcentajeCreditoPorPagar) {
		this.porcentajeCreditoPorPagar = porcentajeCreditoPorPagar;
	}
	
	
	

	

}
