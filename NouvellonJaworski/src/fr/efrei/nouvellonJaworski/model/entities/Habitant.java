package fr.efrei.nouvellonJaworski.model.entities;

public class Habitant {
	boolean isInfected;
	boolean isIsolated;
	boolean isDead;
	public Habitant() {
		this.isInfected=false;
		this.isIsolated=false;//false => pas en quarantaine
		this.isDead=false;
	}
	/**
	 * infecte un habitant donn� en d�but de partie
	 */
	public void infectSomeone() {
		this.contaminerOuSoigner(true);
		Ville.stats.incrNbHabitantsInfected();
	}
	/**
	 * fonction qui permet � un habitant d'en infecter un autre
	 * 
	 */
	public void infectSomeone(Habitant habitant) {
		if(!habitant.isInfected() && !habitant.isIsolated() && !habitant.isDead && this.isInfected && !this.isDead && !isIsolated) {//la cible pas isol�,pas infect�  et porteur infect�, pas mort,pas isol�
			habitant.contaminerOuSoigner(true);//on le contamine
			//Ville.stats.incrNbHabitantsInfected();
		}
	}
	/**
	 * permet de gu�rir un habitant
	 * 
	 */
	public void healHabitant() {
		if(!this.isDead && this.isInfected && this.isIsolated) {//cible infect�, isol�, pas mort 
			this.contaminerOuSoigner(false);//on le soigne
			this.setIsolated(false);//on le retire de la quarantaine
			Ville.stats.decrNbHabitantsInfected();
			Ville.stats.decrNbHabitantsIsolated();
		}
	}
	/**
	 * permet d'isoler un habitant
	 */
	public boolean isolateHabitant() {
		if(!this.isDead && this.isInfected && !this.isIsolated) {//cible infect�, pas isol�, pas mort
			this.setIsolated(true);//on l'isole
			return true;
		}
		return false;
	}
	/**
	 * faire succomber l'habitant
	 */
	public void killHabitant() {
		if(isInfected && !isIsolated && !isDead) {//infecte et pas en quarantaine
			this.setDead(true);
			Ville.stats.decrNbHabitantsInfected();
			Ville.stats.decrNbHabitantsAlive();
		}
	}
	public boolean isInfected() {
		return isInfected;
	}
	/**
	 * changer l'etat d'infection d'un habitant
	 * @param isInfected
	 */
	public void contaminerOuSoigner(boolean isInfected) {
		this.isInfected = isInfected;
	}
	
	public boolean isIsolated() {
		return isIsolated;
	}
	/**
	 * changer l'etat d'isolement d'un habitant
	 * @param isIsolated
	 */
	public void setIsolated(boolean isIsolated) {
		this.isIsolated = isIsolated;
	}
	public boolean isDead() {
		return isDead;
	}
	/**
	 * permet de changer le status de mort d'un habitant
	 * @param isDead
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
}
