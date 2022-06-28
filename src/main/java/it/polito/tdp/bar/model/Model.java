package it.polito.tdp.bar.model;

public class Model {
	private Simulator sim;
	public Model() {
		sim = new Simulator();
	}
	public Statistiche Simula() {
		sim.init();
		sim.run();
		return sim.getStatistiche();
	}
}
