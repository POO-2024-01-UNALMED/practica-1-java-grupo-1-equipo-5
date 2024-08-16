package gestorAplicacion.financiero;

import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.personas.Persona;
import gestorAplicacion.inventario.*;

public class CuentaBancaria implements Banco, Serializable{
	private String banco;
	private double bolsilloTrabajadores;
	private double bolsilloInventario;
	private double bolsilloTransporte;
	private double bolsilloEstablecimientos;
	private double bolsilloPagoCredito;
	private double saldo;
	private long numeroCuenta;
	private String titular;
    private double interes ;
    private double cobroAdicional;
    private Factura credito;
    
    private static ArrayList<CuentaBancaria> cuentas = new ArrayList<CuentaBancaria>(); 
	
	
	public CuentaBancaria(long numeroCuenta, String titular, double saldoInicial, String banco) {
        this.establecerValores(banco);
		this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.banco = banco;
        cuentas.add(this);
    }
	
	public CuentaBancaria(long numeroCuenta, String titular, double bolsilloTrabajadores, double bolsilloInventario, double bolsilloTransporte, double bolsilloEstablecimientos, double bolsilloPagoCredito, String banco) {
        this.establecerValores(banco);
		this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.bolsilloTrabajadores = bolsilloTrabajadores;
        this.bolsilloTransporte = bolsilloTransporte;
        this.bolsilloInventario = bolsilloInventario;
        this.bolsilloEstablecimientos = bolsilloEstablecimientos;
        this.bolsilloPagoCredito = bolsilloPagoCredito;
        this.banco = banco;
        this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
        cuentas.add(this);
	}
	
	private void establecerValores(String banco) {
		switch(banco.toUpperCase()) {
		case "BANCOLOMBIA":
            this.interes = INTERES_BANCOLOMBIA;
            this.cobroAdicional = COBRO_ADICIONAL_BANCOLOMBIA;
            break;
        case "DAVIVIENDA":
            this.interes = INTERES_DAVIVIENDA;
            this.cobroAdicional = COBRO_ADICIONAL_DAVIVIENDA;
            break;
		case "BBVA":
		    this.interes = INTERES_BBVA;
		    this.cobroAdicional = COBRO_ADICIONAL_DAVIVIENDA;
		    break;
		case "BANCO_BOGOTA":
		    this.interes = INTERES_BANCO_BOGOTA;
		    this.cobroAdicional = COBRO_ADICIONAL_BANCO_BOGOTA;
		    break;
		case "BANCO_OCCIDENTE":
		    this.interes = INTERES_BANCO_OCCIDENTE;
		    this.cobroAdicional = COBRO_ADICIONAL_BANCO_OCCIDENTE;
		    break;
		}
	}
    public void depositar(double cantidad, String tipo) {
        if(tipo == "saldo") {
    	   if (cantidad > 0) {
            saldo += cantidad;
        }
      }else if(tipo == "bolsilloTrabajadores") {
    	  if (cantidad > 0) {
              bolsilloTrabajadores += cantidad;
              this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
      }}else if(tipo == "bolsilloInventario") {
    	  if (cantidad > 0) {
              bolsilloInventario += cantidad;
              this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
      }}else if(tipo == "bolsilloTransporte") {
    	  if (cantidad > 0) {
              bolsilloTransporte += cantidad;
              this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
      }}else if(tipo == "bolsilloEstablecimientos") {
    	  if (cantidad > 0) {
              bolsilloEstablecimientos += cantidad;
              this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
      }}else if(tipo == "bolsilloPagoCredito") {
    	  if (cantidad > 0) {
              bolsilloPagoCredito += cantidad;
              this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
      }}
    }
public String infoCredito() {
	if(this.getCredito() != null) {
	return "ID: " + this.getCredito().getID() + "\n" +
			"precio: " + this.getCredito().getPrecio() + "\n" +
			"Porcentaje por pagar: " + this.getCredito().getPorcentajeCreditoPorPagar();}
	else {
		return "No hay credito activo";
	}
}
    public void retirar(double cantidad, String tipo) {
    	if(tipo == "saldo") {
     	   if (cantidad <= saldo) {
               saldo -= cantidad;
           }
         }else if(tipo == "bolsilloTrabajadores") {
       	  if (cantidad <= bolsilloTrabajadores) {
                 bolsilloTrabajadores -= cantidad;
                 this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
         }}else if(tipo == "bolsilloInventario") {
       	  if (cantidad <= bolsilloInventario) {
                 bolsilloInventario -= cantidad;
                 this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
         }}else if(tipo == "bolsilloTransporte") {
       	  if (cantidad <= bolsilloTransporte) {
                 bolsilloTransporte -= cantidad;
                 this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
         }}else if(tipo == "bolsilloEstablecimientos") {
       	  if (cantidad <= bolsilloEstablecimientos) {
                 bolsilloEstablecimientos -= cantidad;
                 this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
         }} else if(tipo == "bolsilloPagoCredito") {
    	  if (cantidad <= bolsilloPagoCredito) {
              bolsilloPagoCredito -= cantidad;
              this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
      }}
       }

    
    public void transaccionCuentaAhorros(double valor, CuentaBancaria cuentaAhorros) {
    	
    	if(this.getBanco() == cuentaAhorros.getBanco()) {
    		double saldo = this.saldo - valor;
    		this.setSaldo(saldo);
 
    	}   
    	else {
    		
    		double cobroAdicional = this.getCobroAdicional();
    		double saldo = this.saldo -(valor + cobroAdicional);
		    this.setSaldo(saldo);
    		
    	}
    	
    	
    	double saldoCuentaAhorros = cuentaAhorros.saldo + valor;
    	cuentaAhorros.setSaldo(saldoCuentaAhorros);
    
    	double porcentajeInteres = this.getInteres();
        double interes = cuentaAhorros.obtenerSaldo() * porcentajeInteres;
        double saldoFinal = cuentaAhorros.obtenerSaldo() - interes;
        cuentaAhorros.setSaldo(saldoFinal);
       
    }
    public void transaccion(double valor, CuentaBancaria cuentaCorriente, String tipo) {
    	
    	if(this.getBanco() == cuentaCorriente.getBanco()) {
    		if(tipo == "saldo") {
    			double saldo = this.saldo - valor;
    	   		setSaldo(saldo);
    		}else if(tipo == "bolsilloTrabajadores") {
    			double saldo1 = this.bolsilloTrabajadores - valor;
    	   		setBolsilloTrabajadores(saldo1);
    		}else if(tipo == "bolsilloInventario") {
    			double saldo2 = this.bolsilloInventario - valor;
    	   		setBolsilloInventario(saldo2);	
    		}else if(tipo == "bolsilloTransporte") {
    			double saldo3 = this.bolsilloTransporte - valor;
    	   		setBolsilloTransporte(saldo3);
    	    }else if(tipo == "bolsilloEstablecimientos") {
    	    	double saldo4 = this.bolsilloEstablecimientos - valor;
    	   		setBolsilloEstablecimientos(saldo4);
    	    }
    	else {
    		
    		double cobroAdicional = this.getCobroAdicional();
    		if(tipo == "saldo") {
    			double saldo = this.saldo -(valor + cobroAdicional);
    		    setSaldo(saldo);
    		}else if(tipo == "bolsilloTrabajadores") {
    			double saldo = this.bolsilloTrabajadores -(valor + cobroAdicional);
    		    setBolsilloTrabajadores(saldo);
    		}else if(tipo == "bolsilloInventario") {
    			double saldo = this.bolsilloInventario -(valor + cobroAdicional);
    		    setBolsilloInventario(saldo);
    		}else if(tipo == "bolsilloTransporte") {
    			double saldo = this.bolsilloTransporte -(valor + cobroAdicional);
    		    setBolsilloTransporte(saldo);
    	    }else if(tipo == "bolsilloEstablecimientos") {
    	    	double saldo = this.bolsilloEstablecimientos -(valor + cobroAdicional);
    		    setBolsilloEstablecimientos(saldo);
    	    }
    		
    		
    	}
    		double saldoCuenta = cuentaCorriente.saldo + valor;
        	cuentaCorriente.setSaldo(saldoCuenta);
   
    }
	   
		
   }
    
    public static ArrayList<CuentaBancaria> getCuentas(){
    	return cuentas;
    }

	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco=banco;
	}
	public double obtenerSaldo() {
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
		this.saldo = this.bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
	}
	
	public double getBolsilloInventario() {
		return this.bolsilloInventario;
	}
	
	public void setBolsilloInventario(double bolsilloInventario) {
		this.bolsilloInventario = bolsilloInventario;
		this.saldo = bolsilloTrabajadores + bolsilloTransporte + this.bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
	}
	
	public double getBolsilloTransporte() {
		return this.bolsilloTransporte;
	}
	
	public void setBolsilloTransporte(double bolsilloTransporte) {
		this.bolsilloTransporte = bolsilloTransporte;
		this.saldo = bolsilloTrabajadores + this.bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + bolsilloPagoCredito;
	}
	
	public double getBolsilloEstablecimientos() {
		return this.bolsilloEstablecimientos;
	}
	
	public void setBolsilloEstablecimientos(double bolsilloEstablecimientos) {
		this.bolsilloEstablecimientos = bolsilloEstablecimientos;
		this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + this.bolsilloEstablecimientos + bolsilloPagoCredito;
	}
	public double getBolsilloPagoCredito() {
		return this.bolsilloPagoCredito;
	}
	public void setBolsilloPagoCredito(double bolsilloPagoCredito) {
		this.bolsilloPagoCredito = bolsilloPagoCredito;
		this.saldo = bolsilloTrabajadores + bolsilloTransporte + bolsilloInventario + bolsilloEstablecimientos + this.bolsilloPagoCredito;
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
	

	
	public double getInteres() {
		return this.interes; 
	}
	public double getCobroAdicional(){
		return this.cobroAdicional;
	
	}


	public double getSaldo() {
		return saldo;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public void setCobroAdicional(double cobroAdicional) {
		this.cobroAdicional = cobroAdicional;
	}
	

	
	public Factura getCredito() {
		return this.credito;
	}
	
	public void setCredito(Factura credito) {
		this.credito = credito;
	}
	

}
