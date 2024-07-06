package gestorAplicacion;

public class CuentaBancaria {
	private String banco;
	private double saldo;
	private long numero;
	
	//metodos get y set

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
		return numero;
	}
	public void setNumero(long numero) {
		this.numero=numero;
	}
}
