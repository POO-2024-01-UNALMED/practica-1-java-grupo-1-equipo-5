package gestorAplicacion.inventario;

public enum TipoVehiculo {
	
	BERLINA(4,false,true,70000),//Hasta 4 acompañantes - familiares y cliente (si va el cliente irían 3 acompañantes)
	CARROZA(6,true,false,150000), // Hasta 1 acompañante
	FAETON(4,false,true,120000), //Hasta 4 acompañantes
	COCHEFUNEBRE(1,true,false,80000), //1 persona - solo cliente
	BUS(6,false,true,50000), // 6 personas
	COCHERESPETO(8,false,true,75000), //8 personas - solo familiares
	CUPE(4,false,true,65000), //una persona - solo cliente
	CAMION(5,false,false,69000);
	
	private int capacidad; //capacidad carro de personas 
	private boolean cliente;  // atributo para reconocer si es posible agregar objetod de tipo Cliente
	private boolean familiar; // atributo para reconocer si es posible agregar objetod de tipo Familiar
	private double precio;
	
	private TipoVehiculo(int capacidad, boolean cliente, boolean familiar,double precio) {
		this.capacidad=capacidad;
		this.cliente=cliente;
		this.familiar=familiar;
		this.precio=precio;
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

	public double getPrecio() {
		return this.precio;
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

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
