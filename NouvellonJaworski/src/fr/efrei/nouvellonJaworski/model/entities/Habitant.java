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
	 * infecte un habitant donné en début de partie
	 */
	public void infectSomeone() {
		this.contaminerOuSoigner(true);
		Ville.stats.incrNbHabitantsInfected(); 
	}
	/**
	 * fonction qui permet à un habitant d'en infecter un autre
	 * 
	 */
	public void infectSomeone(Habitant habitant) {
		if(!habitant.isInfected() && !habitant.isIsolated() && !habitant.isDead && this.isInfected && !this.isDead && !isIsolated) {//la cible pas isolé,pas infecté  
			habitant.contaminerOuSoigner(true);//on le contamine
			
		}
	}
	/**
	 * permet de guérir un habitant
	 * 
	 */
	public boolean healHabitant() {
		if(!this.isDead && this.isInfected && this.isIsolated) {//cible infecté, isolé, pas mort 
			this.contaminerOuSoigner(false);//on le soigne
			this.setIsolated(false);//on le retire de la quarantaine
			
			return true;
		}
		return false;
	}
	/**
	 * permet d'isoler un habitant
	 */
	public boolean isolateHabitant() {
		if(!this.isDead && this.isInfected && !this.isIsolated) {//cible infecté, pas isolé, pas mort
			this.setIsolated(true);//on l'isole
			return true;
		}
		return false;
	}
	/**
	 * faire succomber l'habitant
	 */
	public boolean killHabitant() {
		if(isInfected  && !isDead) {//infecte et pas mort
			this.setDead(true);
			return true;
		}
		return false;
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
