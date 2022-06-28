package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDate;

public class Event implements Comparable<Event> {
	
	//2 tipi di eventi quando arrivano i clienti e quando se ne vanno
	public enum EventType{
		ARRIVO_GRUPPO_CLIENTI,
		TAVOLO_LIBERATO
	}
	
	private EventType type;
	private Duration time; // anche un INTERO va bene
	private int nPersone; // quelli che arrivano
	private Duration durata; // quanto sta il gruppo di clienti al tavolo
	private double tolleranza; 
	private Tavolo tavolo;
	
	public Event(Duration time, EventType type, int nPersone, Duration durata, double tolleranza, Tavolo tavolo) {
		super();
		this.type = type;
		this.time = time;
		this.nPersone = nPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.tavolo = tavolo;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Duration getTime() {
		return time;
	}

	public void setTime(Duration time) {
		this.time = time;
	}

	public int getnPersone() {
		return nPersone;
	}

	public void setnPersone(int nPersone) {
		this.nPersone = nPersone;
	}

	public Duration getDurata() {
		return durata;
	}

	public void setDurata(Duration durata) {
		this.durata = durata;
	}

	public double getTolleranza() {
		return tolleranza;
	}

	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.getTime());
	}
	
}
