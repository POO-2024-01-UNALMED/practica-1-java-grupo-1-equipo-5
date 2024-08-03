package gestorAplicacion.inventario;

public class Producto {

	// Atributos
    private String nombre;
    private double precio;
    private int cantidad;
    private int cantidadVendida=0;
    
    
    
    
    public Producto(String nombre, double precio, int cantidad) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
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
