package gestorAplicacion.financiero;

public interface Banco{
	
	double INTERES_BANCOLOMBIA = 0.0005;
	double COBRO_ADICIONAL_BANCOLOMBIA = 1800;
	double INTERES_DAVIVIENDA = 0.08;
	double COBRO_ADICIONAL_DAVIVIENDA = 2000;
	double INTERES_BBVA = 0.005;
	double COBRO_ADICIONAL_BBVA = 1500;
	double INTERES_BANCO_BOGOTA = 0.0001;
	double COBRO_ADICIONAL_BANCO_BOGOTA = 1600;
	double INTERES_BANCO_OCCIDENTE = 0.02;
	double COBRO_ADICIONAL_BANCO_OCCIDENTE = 2200;
	

	double getInteres();
	
	double getCobroAdicional();
	
	void depositar(double cantidad, String tipo);
	
	void retirar(double cantidad, String tipo);
	
	double obtenerSaldo();
	
	default void consultarEstadoCuenta() {
		
	double saldo = obtenerSaldo();
		
	}
	
}