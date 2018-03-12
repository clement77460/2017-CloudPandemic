package fr.efrei.nouvellonJaworski.model.entities;

import java.util.*;

public class Ville {
	private List<Habitant> habitants;
	private List<Habitant> habitantsInfected;
	protected static SimulationImplement stats;
	
	 
	
	public Ville(int nbHabitants) {
		this.habitants=new ArrayList<Habitant>(nbHabitants);
		this.habitantsInfected=new ArrayList<Habitant>();
		this.initialisationHabitants(nbHabitants); 
	}
	
	
	private void initialisationHabitants(int nbHabitants) {
		Habitant temp;
		for(int i=0;i<nbHabitants;i++) {
			temp=new Habitant();
			this.habitants.add(temp);
		}
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
	
	
}
