package gestorAplicacion.establecimientos;

import java.time.LocalTime;

public enum Iglesia {
	
	HINDUISMO("Kamakhya Temple",7,true,4,new String[] {"ordinaria"}),
	BUDISMO("Templo de Nanjing",8,true,3,new String[] {"fija","ordinaria"}),
	CRISTIANISMO("Iglesia de la Sagrada Familia",5,true,1,new String[] {"fija","ordinaria"}),
	ISLAM("Mezquita del Profeta",6,false,0,null),
	JUDAISMO("Sinagoga de la Rua",8,false,0,null),
	TAOISMO("Templo del Valle del Jade",3,true,2,new String[] {"fija"});
	
	private String nombre;
	private int sillas;
	private boolean cremacion;
	private int duracionCremacion;
	private String[] tipoUrnas;
	
	//Constructor 
	private Iglesia(String nombre, int sillas, boolean cremacion,int duracionCremacion,String[] tipoUrnas) {
		this.nombre=nombre;
		this.sillas=sillas;
		this.cremacion=cremacion;
		this.duracionCremacion=duracionCremacion;
		this.tipoUrnas=tipoUrnas;
		
	}
	
	public int getDuracionCremacion() {
		return duracionCremacion;
	}
	
	public LocalTime duracionEvento(LocalTime horaInicio) {
		
		LocalTime horaFinCremacion = horaInicio.plusHours(duracionCremacion);
		
		return horaFinCremacion;
		
	}
	
	public boolean getCremacion() {
		return cremacion;
	}
	public String getNombre() {
		return nombre;
	}
	
	public String[] getTipoUrnas() {
		return tipoUrnas;
	}
	
}
