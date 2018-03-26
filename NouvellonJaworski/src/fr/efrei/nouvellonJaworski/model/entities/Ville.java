package fr.efrei.nouvellonJaworski.model.entities;

import java.util.*;



public class Ville {
	private List<Habitant> habitantsHealthy;
	private List<Habitant> habitantsInfected;
	private List<Habitant> habitantsIsolated;
	private List<Habitant> habitantsDead;
	protected static SimulationImplement stats;
	
	 
	
	public Ville(int nbHabitants) {
		this.habitantsHealthy=new ArrayList<Habitant>(nbHabitants);
		this.habitantsInfected=new ArrayList<Habitant>();
		this.habitantsIsolated = new ArrayList<Habitant>();
		this.habitantsDead = new ArrayList<Habitant>();
		this.initialisationHabitants(nbHabitants); 
	}
	
	
	private void initialisationHabitants(int nbHabitants) {
		Habitant temp;
		for(int i=0;i<nbHabitants;i++) {
			temp=new Habitant(i);
			this.habitantsHealthy.add(temp);
		}
	} 
	
	public List<Habitant> getHabitantsIsolated() {
		return habitantsIsolated;
	}
	
	
	public List<Habitant> getHabitantsInfected() {
		return habitantsInfected;
	}
	
	
	public List<Habitant> getHabitantsHealthy() {
		return habitantsHealthy;
	}
	
	
	public void setHabitantsHealthy(List<Habitant> habitants) {
		this.habitantsHealthy = habitants;
	}
	
	
	public static SimulationImplement getStats() {
		return stats;
	}
	
	
	public int getNbrHabitantsHealthy() {
		return this.habitantsHealthy.size();
	}
	
	
	public Habitant getHabitantHealthy(int index) {
		return this.habitantsHealthy.get(index);
	}


	public List<Habitant> getHabitantsDead() {
		
		return habitantsDead;
	}
	

	
}
