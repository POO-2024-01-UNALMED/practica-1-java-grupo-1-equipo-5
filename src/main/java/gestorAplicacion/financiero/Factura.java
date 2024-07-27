package gestorAplicacion.financiero;
import gestorAplicacion.personas.*;
import gestorAplicacion.establecimientos.*;

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

	private final double IVA=0.19;
	private static int facturasCreadas;
	
	
	
	//Constructor
	
	public Factura(String producto, int precio) {
		this.producto=producto;
		this.precio=precio;
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
	
	

	

}
