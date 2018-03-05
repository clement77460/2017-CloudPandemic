package fr.efrei.nouvellonJaworski.model.entities;
import java.time.Instant;

import fr.efrei.nouvellonJaworski.model.selection.*;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.Simulation;

public class Statistique implements Simulation{
	int nbHabitantsAlive;
	int nbHabitantsInfected;
	int nbHabitantsIsolated;
	int nbHabitantsDead;
	int nbOriginalHabitants;
	Selector selecteurHabAlive;
	public Statistique(int nbHabitants,Selector mySelector) {
		this.nbHabitantsAlive=nbHabitants;
		this.nbOriginalHabitants=nbHabitants;
		this.nbHabitantsInfected=0;
		this.nbHabitantsIsolated=0;
		this.nbHabitantsDead=0;
		this.selecteurHabAlive=mySelector;
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
	@Override
	public void update() {
		//System.out.println(((MySelector) selecteurHabAlive).dqueRanks());
		nbHabitantsInfected=((MySelector) selecteurHabAlive).dqueRanks();
	}
	@Override
	public Instant getCurrentInstant() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getOriginalPopulation() {
		// TODO Auto-generated method stub
		return nbOriginalHabitants;
	}
	@Override
	public int getLivingPopulation() {
		// TODO Auto-generated method stub
		return nbHabitantsAlive;
	}
	@Override
	public int getInfectedPopulation() {
		// TODO Auto-generated method stub
		return nbHabitantsInfected;
	}
	@Override
	public int getQuarantinedPopulation() {
		// TODO Auto-generated method stub
		return nbHabitantsIsolated;
	}
	@Override
	public int getDeadPopulation() {
		// TODO Auto-generated method stub
		return nbHabitantsDead;
	}
}
