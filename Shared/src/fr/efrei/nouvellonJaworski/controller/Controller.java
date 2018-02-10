package fr.efrei.nouvellonJaworski.controller;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.nouvellonJaworski.vue.*;
import java.util.*;
public class Controller {
	private List<Ville> villes;
	private Partie partie;
	public Controller(int nbVilles,int nbHabitants) {
		this.villes=new ArrayList<Ville>(nbVilles);
		this.partie=new Partie(this);
		this.initVille(nbVilles,nbHabitants);
	}
	public void initVille(int nbVilles,int nbHabitants) {
		Ville temp;
		for(int i=0;i<nbVilles;i++) {
			temp=new Ville(nbHabitants,this.partie);
			this.villes.add(temp);
		}
	}
	public void lancementPartie() {
		Ville villeAleatoire=this.villes.get((int)(Math.random()*villes.size()));
		villeAleatoire.getHabitant((int)(Math.random()*villeAleatoire.getNbrHabitants())).infectSomeone();
		partie.askAction();
	}
	public void choixAction(int choix) {
		Ville temp;
		Ville temp2;
		switch(choix) {
			case 1:
				//habitant de la ville temp contamine un habitant de la ville temp2 (et inversement)
				//c'est une hypothese ! à voir par la suite
				temp=villes.get((int)(Math.random()*villes.size()));
				temp2=villes.get((int)(Math.random()*villes.size()));
				temp.getHabitant((int)(Math.random()*temp.getNbrHabitants())).infectSomeone(temp2.getHabitant((int)(Math.random()*temp2.getNbrHabitants())));
				temp2.getHabitant((int)(Math.random()*temp2.getNbrHabitants())).infectSomeone(temp.getHabitant((int)(Math.random()*temp.getNbrHabitants())));
				partie.printCarac(Ville.getStats());
				break;
			case 2:
				//un habitant aleatoire de la ville temp est choisi aleatoirement
				//pour se faire soigner 
				temp=villes.get((int)(Math.random()*villes.size()));
				temp.getHabitant((int)(Math.random()*temp.getNbrHabitants())).healHabitant();
				partie.printCarac(Ville.getStats());
				break;
			case 3:
				//un habitant aleatoire de la ville temp est choisi aleatoirement
				//pour se faire isoler 
				temp=villes.get((int)(Math.random()*villes.size()));
				temp.getHabitant((int)(Math.random()*temp.getNbrHabitants())).isolateHabitant();
				partie.printCarac(Ville.getStats());
				break;
			case 4:
				//un habitant aleatoire de la ville temp est choisi aleatoirement
				//pour se faire tuer
				temp=villes.get((int)(Math.random()*villes.size()));
				temp.getHabitant((int)(Math.random()*temp.getNbrHabitants())).killHabitant();
				partie.printCarac(Ville.getStats());
				break;
			case 5:
				System.exit(0);
				break;
		}
	}
}
