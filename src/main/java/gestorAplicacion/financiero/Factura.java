package gestorAplicacion.financiero;
import gestorAplicacion.personas.*;
import gestorAplicacion.establecimientos.*;
import java.util.ArrayList;
import java.util.List;
import gestorAplicacion.inventario.*;

public class Factura {
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


	private final double IVA=0.19;
	private static int facturasCreadas;
	
	
	
	//Constructor para crear la factura con clientes 
	
	public Factura(String producto, double precio, String Fecha, Persona cliente) {
		this.producto=producto;
		this.precio=precio;
		this.cliente= cliente;
		this.Fecha=Fecha;
		this.listaProductos = new ArrayList<>();
		ID=facturasCreadas;
		calcularTotal();
		
	}
	
	public Factura(String producto, double precio, String Fecha, Establecimiento Entidad) {
		this.producto=producto;
		this.precio=precio;
		this.Fecha=Fecha;
		this.Entidad= Entidad;
		facturasCreadas++;
		ID=facturasCreadas;
		calcularTotal();
		
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

	
	//metodos get y set 
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
	

	

}
