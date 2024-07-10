package gestorAplicacion.financiero;

public class Factura {
	//Atributos
	private final int ID;
	private String producto;
	private double precio;
	private double total;
	private final double IVA=0.19;
	private static int facturasCreadas;
	
	
	
	//Constructor
	
	public Factura(String producto, int precio) {
		this.producto=producto;
		this.precio=precio;
		facturasCreadas++;
		ID=facturasCreadas;
		
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
	

	

}
