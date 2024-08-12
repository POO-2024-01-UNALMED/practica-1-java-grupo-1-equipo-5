package gestorAplicacion.financiero;

import java.util.ArrayList;

import gestorAplicacion.personas.Persona;
import gestorAplicacion.inventario.*;

public class CuentaBancaria {
	private String banco;
	private double bolsilloTrabajadores;
	private double bolsilloInventario;
	private double bolsilloTransporte;
	private double bolsilloEstablecimientos;
	private double saldo;
	private long numeroCuenta;
	private String titular;
	
	
	public CuentaBancaria(long numeroCuenta, String titular, double saldoInicial, String banco) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.banco = banco;
    }
	
	public CuentaBancaria(long numeroCuenta, String titular, double bolsilloTrabajadores, double bolsilloInventario, double bolsilloTransporte, double bolsilloEstablecimientos, String banco) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.bolsilloTrabajadores = bolsilloTrabajadores;
        this.bolsilloTransporte = bolsilloTransporte;
        this.bolsilloInventario = bolsilloInventario;
        this.bolsilloEstablecimientos = bolsilloEstablecimientos;
        this.banco = banco;
	}
    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
        }
    }

    public void retirar(double cantidad) {
        if (cantidad > 0) {
            if (cantidad <= saldo) {
                saldo -= cantidad;
            } 
        }
        
}
    
    public void transaccionCuentaAhorros(double valor, CuentaBancaria cuentaAhorros) {
    	
    	if(this.getBanco() == cuentaAhorros.getBanco()) {
    		double saldo = this.saldo - valor;
    		setSaldo(saldo);
    	}
    	else {
    		
    		String Nbanco = this.banco;
    		Banco banco = Banco.valueOf(Nbanco);
    		double cobroAdicional = banco.getCobroAdicional();
    		double saldo = this.saldo -(valor + cobroAdicional);
		    setSaldo(saldo);
    		
    	}
    	
    	
    	double saldoCuentaAhorros = cuentaAhorros.saldo + valor;
    	cuentaAhorros.setSaldo(saldoCuentaAhorros);
    	
    	String Nbanco = this.banco;
		Banco banco = Banco.valueOf(Nbanco);
    	double porcentajeInteres = banco.getInteres();
        double interes = cuentaAhorros.getSaldo() * porcentajeInteres;
        double saldoFinal = cuentaAhorros.getSaldo() - interes;
        cuentaAhorros.setSaldo(saldoFinal);
    }
    public void transaccion(double valor, CuentaBancaria cuentaCorriente) {
    	if(this.getBanco() == cuentaCorriente.getBanco()) {
    		double saldo = this.saldo - valor;
    		setSaldo(saldo);
    	}
    	else {
    		
    		String Nbanco = this.banco;
    		Banco banco = Banco.valueOf(Nbanco);
    		double cobroAdicional = banco.getCobroAdicional();
    		double saldo = this.saldo -(valor + cobroAdicional);
		    setSaldo(saldo);
    		
    	}
    }
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco=banco;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo=saldo;
	}
	public long getNumero() {
		return numeroCuenta;
	}
	public void setNumero(long numero) {
		this.numeroCuenta=numero;
	}
	
	public double getBolsilloTrabajadores() {
		return this.bolsilloTrabajadores;
	}
	
	public void setBolsilloTrabajadores(double bolsilloTrabajadores) {
		this.bolsilloTrabajadores = bolsilloTrabajadores;
	}
	
	public double getBolsilloInventario() {
		return this.bolsilloInventario;
	}
	
	public void setBolsilloInventario(double bolsilloInventario) {
		this.bolsilloInventario = bolsilloInventario;
	}
	
	public double getBolsilloTransporte() {
		return this.bolsilloTransporte;
	}
	
	public void setBolsilloTransporte(double bolsilloTransporte) {
		this.bolsilloTransporte = bolsilloTransporte;
	}
	
	public double getBolsilloEstablecimientos() {
		return this.bolsilloEstablecimientos;
	}
	
	public void setBolsilloEstablecimientos(double bolsilloEstablecimientos) {
		this.bolsilloEstablecimientos = bolsilloEstablecimientos;
	}

	public long getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}
	
}
