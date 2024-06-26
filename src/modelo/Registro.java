package modelo;

import java.time.LocalDateTime;

public class Registro {
	private int id;
	private LocalDateTime datahora;
	private Veiculo veiculo;
	private String tipo; // entrada ou saida do veiculo
	
	public Registro(int id, Veiculo veiculo,String tipo) {
		this.id = id - 1;
		this.veiculo = veiculo;
		this.tipo = tipo;
		this.datahora = LocalDateTime.now(); 
	}
	public Registro() {}
	
	// Getters and Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDatahora() {
		return datahora;
	}

	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	// Methods
	
	@Override
	public String toString() {
		return "Registro [id=" + id + ", datahora=" + datahora + ", veiculo=" + veiculo.getPlaca() + ", tipo=" + tipo + "] \n ";
	}
	
	
	
	
}
