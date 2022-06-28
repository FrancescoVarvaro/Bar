package it.polito.tdp.bar.model;

public class Tavolo {
	// lista di tavoli in cui per ogni tavolo abbiamo 
	// POSTI
	// FLAG che ci dice se il tavolo è occupato oppure no
	private int posti;
	private boolean occupato;
	
	public Tavolo(int posti, boolean occupato) {
		this.posti = posti;
		this.occupato = occupato;
	}
	
	public int getPosti() {
		return posti;
	}
	public void setPosti(int posti) {
		this.posti = posti;
	}
	public boolean isOccupato() {
		return occupato;
	}
	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}
	
}
