package fr.efrei.nouvellonJaworski.model.entities;

public class Statistique {
	int nbHabitantsAlive;
	int nbHabitantsInfected;
	int nbHabitantsIsolated;
	public Statistique(int nbHabitants) {
		this.nbHabitantsAlive=nbHabitants;
		this.nbHabitantsInfected=0;
		this.nbHabitantsIsolated=0;
	}
	public int getNbHabitantsAlive() {
		return nbHabitantsAlive;
	}
	public int getNbHabitantsInfected() {
		return nbHabitantsInfected;
	}
	public int getNbHabitantsIsolated() {
		return nbHabitantsIsolated;
	}
	
	public void incrNbHabitantsInfected() {
		System.out.println("un habitant est infecté");
		this.nbHabitantsInfected++;
	}
	public void incrNbHabitantsIsolated() {
		this.nbHabitantsIsolated++;
	}
	public void decrNbHabitantsInfected() {
		this.nbHabitantsInfected--;
	}
	public void decrNbHabitantsIsolated() {
		this.nbHabitantsIsolated--;
	}
	public void decrNbHabitantsAlive() {
		this.nbHabitantsAlive--;
	}
	@Override
	public String toString() {
		return "Il y a "+this.nbHabitantsAlive+" habitants en vie\n"
				+"Il y a "+this.nbHabitantsInfected+" habitants infectées\n"
				+"Il y a "+this.nbHabitantsIsolated+" habitants isolées\n";
	}
	public void addHabitantsAlive(int nbr) {
		this.nbHabitantsAlive=this.nbHabitantsAlive+nbr;
	}
}
