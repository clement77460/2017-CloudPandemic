package fr.efrei.nouvellonJaworski.model.entities;

import java.util.*;

import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.domain.MigrationMessage;
import fr.efrei.paumier.shared.selection.Selector;



public class Ville {
	private final CityBorder border;
	private final Selector selector;
	
	private List<Habitant> habitantsHealthy;
	private List<Habitant> habitantsInfected;
	private List<Habitant> habitantsIsolated;
	private List<Habitant> habitantsDead;
	protected static SimulationImplement stats;
	private Double panicLVL;
	 
	
	public Ville(int nbHabitants,CityBorder border,Selector selector) {
		this.habitantsHealthy=new ArrayList<Habitant>(nbHabitants);
		this.border=border;
		this.selector=selector;
		this.habitantsInfected=new ArrayList<Habitant>();
		this.habitantsIsolated = new ArrayList<Habitant>();
		this.habitantsDead = new ArrayList<Habitant>();
		this.initialisationHabitants(nbHabitants); 
		this.panicLVL=0.0;
	}
	
	
	private void initialisationHabitants(int nbHabitants) {	
		for(int i=0;i<nbHabitants;i++) {
			this.habitantsHealthy.add(new Habitant());
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
	

	
	
	
	public int getNbrHabitantsHealthy() {
		return this.habitantsHealthy.size();
	}
	


	public List<Habitant> getHabitantsDead() {
		
		return habitantsDead;
	}
	
	public void decrPanic() {
		
		if(panicLVL>=2.5)
			this.panicLVL=this.panicLVL-2.5;
	}
	
	public void incrPanic() {
		int livingPopulation=this.getHabitantsHealthy().size()+this.getHabitantsInfected().size()
				+this.getHabitantsIsolated().size();
		
		this.panicLVL=this.panicLVL+5.0;
		
		if(this.panicLVL>=livingPopulation && border!=null) {
			
			this.emigration();
			if(panicLVL>=5) 
				this.panicLVL=this.panicLVL-5.0;
		}
	}
	
	private void emigration() {
		List<Habitant> habitantsHealthyAndInfected=new ArrayList<Habitant>();
		
		habitantsHealthyAndInfected.addAll(habitantsInfected);
		habitantsHealthyAndInfected.addAll(habitantsHealthy);
		habitantsHealthyAndInfected.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
		
		Habitant target=selector.selectAmong(habitantsHealthyAndInfected);
		
		
		habitantsHealthy.remove(target);
		habitantsInfected.remove(target);
		
		target.setEmigrated(true);
		border.sendEmigrant(target.isInfected());
	}
	
	
	public double getPanic() {
		return this.panicLVL;
	}
	
}
