package gestorAplicacion.inventario;

public enum TipoVehiculo {
	
	BERLINA(4,false,true),//Hasta 4 acompañantes - familiares y cliente (si va el cliente irían 3 acompañantes)
	CARROZA(1,true,false), // Hasta 1 acompañante
	FAETON(4,false,true), //Hasta 4 acompañantes
	COCHEFUNEBRE(1,true,false), //1 persona - solo cliente
	BUS(6,false,true), // 6 personas
	COCHERESPETO(8,false,true), //8 personas - solo familiares
	CUPE(1,true,false); //una persona - solo cliente
	
	private int capacidad; //capacidad carro de personas 
	private boolean cliente;  // atributo para reconocer si es posible agregar objetod de tipo Cliente
	private boolean familiar; // atributo para reconocer si es posible agregar objetod de tipo Familiar
	
	private TipoVehiculo(int capacidad, boolean cliente, boolean familiar) {
		this.capacidad=capacidad;
		this.cliente=cliente;
		this.familiar=familiar;
	}
	
	public String toString() {
		return "Vehiculo tipo "+name()+" - Capacidad: "+this.getCapacidad();
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	public boolean getCliente() {
		return cliente;
	}
	public boolean getFamiliar() {
		return familiar;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

	public void setFamiliar(boolean familiar) {
		this.familiar = familiar;
	}
	
}
