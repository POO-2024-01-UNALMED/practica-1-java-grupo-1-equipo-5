package gestorAplicacion;
import java.util.ArrayList;

public class Cliente {
	//Atributos
	private String nombre;
	private final long CC;
	private Urna urna;
	private Crematorio crematorio;
	private Cementerio cementerio;
	private Velorio velorio;
	private ArrayList<Familiar> listadoFamiliares;
	private ArrayList<Factura> listadoFacturas;
	
	//metodos get y set
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public long getCC() {
		return CC;
	}
	public Urna getUrna() {
		return urna;
	}
	public void setUrna(Urna urna) {
		this.urna=urna;
	}
	public Crematorio getCrematorio() {
		return crematorio;
	}
	public void setCrematorio(Crematorio crematorio) {
		this.crematorio=crematorio;
	}
	public Cementerio getCementerio() {
		return cementerio;
	}
	public void setCementerio(Cementerio cementerio) {
		this.cementerio=cementerio;
	}
	public Velorio getVelorio() {
		return velorio;
	}
	public void setVelorio(Velorio velorio) {
		this.velorio=velorio;
	}
	public ArrayList<Familiar> getListadoFamiliares() {
		return listadoFamiliares;
	}
	public void setListadoFamiliares(ArrayList<Familiar> listadoFamiliares) {
		this.listadoFamiliares=listadoFamiliares;
	}
	public ArrayList<Factura> getListadoFacturas() {
		return listadoFacturas;
	}
	public void setListadoFacturas(ArrayList<Factura> listadoFacturas) {
		this.listadoFacturas=listadoFacturas;
	}
}
