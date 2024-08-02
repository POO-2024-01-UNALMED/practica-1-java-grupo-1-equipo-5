package gestorAplicacion.establecimientos;

public enum Iglesia {
	
	HINDUISMO("Kamakhya Temple",7,true,4),
	BUDISMO("Templo de Nanjing",8,true,3),
	CRISTIANISMO("Iglesia de la Sagrada Familia",5,true,1),
	ISLAM("Mezquita del Profeta",6,false,0),
	JUDAISMO("Sinagoga de la Rua",8,false,0),
	TAOISMO("Templo del Valle del Jade",3,true,2);
	
	private String nombre;
	private int sillas;
	private boolean cremacion;
	private int duracionCeremonia;
	
	//Constructor 
	private Iglesia(String nombre, int sillas, boolean cremacion,int duracionCeremonia) {
		this.nombre=nombre;
		this.sillas=sillas;
		this.cremacion=cremacion;
		this.duracionCeremonia=duracionCeremonia;
		
	}

	
}
