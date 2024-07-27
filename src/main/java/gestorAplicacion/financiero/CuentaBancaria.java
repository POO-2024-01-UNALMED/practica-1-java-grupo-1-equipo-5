package gestorAplicacion.financiero;

import java.util.ArrayList;

import gestorAplicacion.personas.Persona;
import gestorAplicacion.inventario.Inventario;

public class CuentaBancaria {
	private String Banco;
	private double saldo;
	private long numeroCuenta;
	private String titular;
	
	public CuentaBancaria(long numeroCuenta, String titular, double saldoInicial, String banco) {
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
	public String getBanco() {
		return Banco;
	}
	public void setBanco(String banco) {
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
