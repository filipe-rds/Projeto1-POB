package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Arrecadacao {
	private LocalDate data;
	private double total;
	
	public Arrecadacao (String dataString) {


		// Formatter para o formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parsing da string para obter um objeto LocalDate
        LocalDate dataFormatada = LocalDate.parse(dataString, formatter);

		this.data = dataFormatada;
		this.total = 0.0;
	}
	
	public Arrecadacao () {}
	
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	// Methods
	@Override
	public String toString() {
		return "Arrecadacao [data=" + data + ", total=R$" + total + "]";
	}
	
	
	
	
}
