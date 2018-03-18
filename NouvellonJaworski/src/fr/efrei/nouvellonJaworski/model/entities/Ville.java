package fr.efrei.nouvellonJaworski.model.entities;

import java.util.*;



public class Ville {
	private List<Habitant> habitants;
	private List<Habitant> habitantsInfected;
	private List<Habitant> habitantsIsolated;
	private List<Habitant> habitantsDead;
	private List<Habitant> habitantsAlive;
	protected static SimulationImplement stats;
	
	 
	
	public Ville(int nbHabitants) {
		this.habitants=new ArrayList<Habitant>(nbHabitants);
		this.habitantsAlive=new ArrayList<Habitant>(nbHabitants);
		this.habitantsInfected=new ArrayList<Habitant>();
		this.habitantsIsolated = new ArrayList<Habitant>();
		this.habitantsDead = new ArrayList<Habitant>();
		this.initialisationHabitants(nbHabitants); 
	}
	
	
	private void initialisationHabitants(int nbHabitants) {
		Habitant temp;
		for(int i=0;i<nbHabitants;i++) {
			temp=new Habitant();
			this.habitants.add(temp);
			this.habitantsAlive.add(temp);
		}
	} 
	
	public List<Habitant> getHabitantsIsolated() {
		return habitantsIsolated;
	}
	
	public List<Habitant> getHabitantsAlive() {
		return habitantsAlive;
	}
	
	public List<Habitant> getHabitantsInfected() {
		return habitantsInfected;
	}
	
	
	public List<Habitant> getHabitants() {
		return habitants;
	}
	
	
	public void setHabitants(List<Habitant> habitants) {
		this.habitants = habitants;
	}
	
	
	public static SimulationImplement getStats() {
		return stats;
	}
	
	
	public int getNbrHabitants() {
		return this.habitants.size();
	}
	
	
	public Habitant getHabitant(int index) {
		return this.habitants.get(index);
	}


	public List<Habitant> getHabitantsDead() {
		
		return habitantsDead;
	}
	

	
}
