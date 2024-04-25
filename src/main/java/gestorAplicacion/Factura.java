package gestorAplicacion;

public class Factura {
	//Atributos
	private final int ID;
	private String producto;
	private double precio;
	private double total;
	private final float IVA;
	
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
	public float getIVA() {
		return IVA;
	}
	

	

}
