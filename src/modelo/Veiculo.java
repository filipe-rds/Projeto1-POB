package modelo;

import java.util.ArrayList;

public class Veiculo {
	private String placa;
	private ArrayList <Registro> registros =  new ArrayList<>();
	
	public Veiculo(String placa) {
		this.placa = placa;
	}
	
	public Veiculo() {}
	
	// Getters and Setters
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public ArrayList<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(ArrayList<Registro> registros) {
		this.registros = registros;
	}
	
	// Methods
	
	@Override
	public String toString() {
		return "Veiculo [placa=" + placa + ", registros=" + registros + "] \n" ;
	}
	
	
}
