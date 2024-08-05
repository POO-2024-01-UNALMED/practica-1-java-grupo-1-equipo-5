package gestorAplicacion.financiero;

public enum Banco {
	
	BANCOLOMBIA(0.0005, 1800),
	DAVIVIENDA(0.08, 2000),
	BBVA(0.005, 1500),
	BANCO_BOGOTA(0.0001, 1600),
	BANCO_OCCIDENTE(0.02, 2200);
  
	final double interes;
	final double cobroAdicional;
	
	Banco(double interes, double cobroAdicional){
		this.interes = interes;
		this.cobroAdicional = cobroAdicional;
	
}
	public double getInteres() {
		return interes;
	}
	public double getCobroAdicional() {
		return cobroAdicional;
	}
	
	
}