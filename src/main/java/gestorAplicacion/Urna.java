package gestorAplicacion;

public class Urna {
	private Cliente cliente;
	private int tamaño;
	private Factura factura;
	
	//metodos get y set
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
	}
	public int getTamaño() {
		return tamaño;
	}
	public void setTamaño(int tamaño) {
		this.tamaño=tamaño;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura=factura;
	}
	
	
	

}
