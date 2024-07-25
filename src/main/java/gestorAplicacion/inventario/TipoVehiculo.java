package gestorAplicacion.inventario;

public enum TipoVehiculo {
	
	BERLINA(4,true,true),//Hasta 4 acompañantes - familiares y cliente (si va el cliente irían 3 acompañantes)
	CARROZA(2,true,true), // Hasta 2 acompañantes
	FAETON(4,true,true), //Hasta 4 acompañantes
	COCHEFUNEBRE(1,true,false), //1 persona - solo cliente
	BUS(6,false,true), // 6 personas
	COCHERESPETO(8,false,true), //8 personas - solo familiares
	CUPE(1,true,false); 
	
	private int capacidad; //capacidad carro de personas 
	private boolean cliente;  // atributo para reconocer si es posible agregar objetod de tipo Cliente
	private boolean familiar; // atributo para reconocer si es posible agregar objetod de tipo Familiar
	
	TipoVehiculo(int capacidad, boolean cliente, boolean familiar) {
		this.capacidad=capacidad;
		this.cliente=cliente;
		this.familiar=familiar;
	}
}
