package it.polito.tdp.bar.model;

import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	//Modello Bar
	private List<Tavolo> tavoli;
	
	//Parametri della simulazione
	private int NUM_EVENTI = 2000;
	private int T_ARRIVO_MAX = 10;
	private int NUM_PERSONE_MAX = 10;
	private int DURATA_MIN = 60;
	private int DURATA_MAX = 120;
	private double TOLLERANZA_MAX = 0.9;
	private double OCCUPAZIONE_MAX = 0.5;
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	// Statistiche
	private Statistiche statistiche;
	
	
	private void creaTavolo(int quantita, int dimensione) {
		for(int i = 0; i< quantita; i++) {
			this.tavoli.add(new Tavolo(dimensione,false));
		}
	}
	private void creaTavoli() {
		// TODO Auto-generated method stub
		creaTavolo(2, 10);
		creaTavolo(4, 8);
		creaTavolo(4, 6);
		creaTavolo(5, 4);
		
		Collections.sort(this.tavoli, new Comparator<Tavolo>() {

			@Override
			public int compare(Tavolo o1, Tavolo o2) {
				// TODO Auto-generated method stub
				return o1.getPosti() - o2.getPosti();
			}
		});
		
	}
	
	//LoadQueue
	public void creaEventi() {
		Duration arrivo = Duration.ofMinutes(0);
		for(int i =0; i< this.NUM_EVENTI; i++) {
			
			int nPersone = (int) (Math.random() * this.NUM_PERSONE_MAX + 1);
			Duration durata = Duration.ofMinutes(this.DURATA_MIN + 
					(int)(Math.random() * (this.DURATA_MAX - this.DURATA_MIN + 1)));
			double tolleranza = Math.random() * this.TOLLERANZA_MAX;
			
			Event e = new Event(arrivo, EventType.ARRIVO_GRUPPO_CLIENTI, nPersone, durata, tolleranza, null);
			this.queue.add(e);
			
			arrivo = arrivo.plusMinutes((int)(Math.random() * this.T_ARRIVO_MAX + 1));
			
		}
	}
	
	//inizializzazione
	public void init() {
		this.queue = new PriorityQueue<Event>();
		this.statistiche = new Statistiche();
		
		creaTavoli();
		creaEventi();
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		// SE ABBIAMO DIVERSE TIPOLOGIE DI EVENTI DIVERSI ---> FARE SWITCH SULLE VARIE TIPOLOGIE
		switch(e.getType()) {
			case ARRIVO_GRUPPO_CLIENTI:
				//conto i clieti totali
				this.statistiche.incrementaClienti(e.getnPersone());
				
				//cerco se c'è il tavolo
				Tavolo tavolo = null;
				for(Tavolo t : this.tavoli) {
					if(!t.isOccupato() && t.getPosti() >= e.getnPersone() && t.getPosti()*this.OCCUPAZIONE_MAX <= e.getnPersone()) {
						tavolo = t;
						break; // IL PRIMO CHE TROVO E' IL PIU'PICCOLO
					}
				}
				if(tavolo != null) {
					System.out.format("trovato tavolo da %d per %d persone", tavolo.getPosti(), e.getnPersone());
					statistiche.incrementaSoddisfatti(e.getnPersone());
					tavolo.setOccupato(true);
					e.setTavolo(tavolo);
					//dopo un po i clienti si alzano
					queue.add(new Event(e.getTime().plus(e.getDurata()),  EventType.TAVOLO_LIBERATO, e.getnPersone(), e.getDurata(), e.getTolleranza(), tavolo));
				} else {
					//c'è solo il bancone
					double bancone = Math.random();
					
					if(bancone <= e.getTolleranza()) {
						//sì, ci fermiamo
						System.out.format("%d persone si fermano al bancone\n", e.getnPersone());
						statistiche.incrementaSoddisfatti(e.getnPersone());
					} else {
						//no, andiamo a casa
						System.out.format("%d persone vanno a casa\n", e.getnPersone());
						statistiche.incrementaInsoddisfatti(e.getnPersone());
					}
				}
				
				break;
			case TAVOLO_LIBERATO:
				e.getTavolo().setOccupato(false);
				break;
		}
		
	}
	
	public Statistiche getStatistiche() {
		return this.statistiche;
	}

}
