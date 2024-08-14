package gestorAplicacion.establecimientos;

import java.time.LocalTime;

public enum Iglesia {
	
	HINDUISMO("Kamakhya Temple",7,true,4,new String[] {"ordinaria"},"Pundit","Guru"),
	BUDISMO("Templo de Nanjing",8,true,3,new String[] {"fija","ordinaria"},"Monje budista","Abad"),
	CRISTIANISMO("Iglesia de la Sagrada Familia",5,true,1,new String[] {"fija","ordinaria"},"Padre","Obispo"),
	ISLAM("Mezquita del Profeta",6,false,0,null,"Imán","Sheij"),
	JUDAISMO("Sinagoga de la Rua",8,false,0,null,"Rabino","Jazan"),
	TAOISMO("Templo del Valle del Jade",3,true,2,new String[] {"fija"},"Daoist","Dao Shi");
	
	private String nombre;
	private int sillas;
	private boolean cremacion;
	private int duracionEvento;
	private String[] tipoUrnas;
	private String religioso;
	private String religiosoAltoRango;
	
	
	//Constructor 
	private Iglesia(String nombre, int sillas, boolean cremacion,int duracionEvento,String[] tipoUrnas,String religioso,String religiosoAltoRango) {
		this.nombre=nombre;
		this.sillas=sillas;
		this.cremacion=cremacion;
		this.duracionEvento=duracionEvento;
		this.tipoUrnas=tipoUrnas;
		this.religioso=religioso;
		this.religiosoAltoRango=religiosoAltoRango;
		
	}
	
	public int getDuracionCremacion() {
		return duracionEvento;
	}
	
	public LocalTime duracionEvento(LocalTime horaInicio) {
		
		LocalTime horaFinCremacion = horaInicio.plusHours(duracionEvento);
		
		return horaFinCremacion;
		
	}
	
	public int getSillas() {
		return sillas;
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
	public String getReligioso() {
		return religioso;
	}
	public String getReligiosoAltoRango() {
		return religiosoAltoRango;
	}

	public int getDuracionEvento() {
		return duracionEvento;
	}

	public void setDuracionEvento(int duracionEvento) {
		this.duracionEvento = duracionEvento;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setSillas(int sillas) {
		this.sillas = sillas;
	}

	public void setCremacion(boolean cremacion) {
		this.cremacion = cremacion;
	}

	public void setTipoUrnas(String[] tipoUrnas) {
		this.tipoUrnas = tipoUrnas;
	}

	public void setReligioso(String religioso) {
		this.religioso = religioso;
	}

	public void setReligiosoAltoRango(String religiosoAltoRango) {
		this.religiosoAltoRango = religiosoAltoRango;
	}
	
	
}
