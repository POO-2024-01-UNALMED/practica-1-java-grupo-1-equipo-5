package gestorAplicacion.financiero;

import java.util.ArrayList;

import gestorAplicacion.personas.Persona;
import gestorAplicacion.inventario.*;

public class CuentaBancaria {
	private Banco Banco;
	private double saldo;
	private long numeroCuenta;
	private String titular;
	
	public CuentaBancaria(long numeroCuenta, String titular, double saldoInicial, Banco banco) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.Banco = banco;
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
    
    public void transaccion(double valor, CuentaBancaria cuentaAhorros) {
    	double saldo = this.saldo - valor;
    	setSaldo(saldo);
    	double saldoCuentaAhorros = cuentaAhorros.saldo + valor;
    	cuentaAhorros.setSaldo(saldoCuentaAhorros);
        
    }
	public Banco getBanco() {
		return Banco;
	}
	public void setBanco(Banco banco) {
		this.Banco=banco;
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
}
