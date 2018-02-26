package fr.efrei.nouvellonJaworski.model.entities;
import fr.efrei.nouvellonJaworski.vue.*;
import java.util.*;

public class Ville {
	private List<Habitant> habitants;
	protected static Statistique stats;
	private Partie partie;
	public Ville(int nbHabitants,Partie partie) {
		this.habitants=new ArrayList<Habitant>(nbHabitants);
		this.initialisationOrAjoutStats(nbHabitants);
		this.initialisationHabitants(nbHabitants);
	}
	private void initialisationOrAjoutStats(int nbHabitants) {
		if(Ville.stats==null) {
			Ville.stats=new Statistique(nbHabitants);
		}
		else {
			Ville.stats.addHabitantsAlive(nbHabitants);
		}
	}
	private void initialisationHabitants(int nbHabitants) {
		Habitant temp;
		for(int i=0;i<nbHabitants;i++) {
			temp=new Habitant();
			this.habitants.add(temp);
		}
	}
	/*
	public void infectation(int numHabitant) {
		this.habitants.get(numHabitant).infectSomeone();
	}
	public void infectation(Habitant habitant1,Habitant habitant2) {
		habitant1.infectSomeone(habitant2);
	}*/
	public static Statistique getStats() {
		return stats;
	}
	public int getNbrHabitants() {
		return this.habitants.size();
	}
	public Habitant getHabitant(int index) {
		return this.habitants.get(index);
	}
}
