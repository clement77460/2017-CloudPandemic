package fr.efrei.nouvellonJaworski.model.entities;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.EventScreeningCenter;
import fr.efrei.nouvellonJaworski.controller.EventImmigration;
import fr.efrei.nouvellonJaworski.controller.EventIncreaseTaxes;
import fr.efrei.nouvellonJaworski.controller.EventInfect;
import fr.efrei.nouvellonJaworski.controller.EventScreening;
import fr.efrei.nouvellonJaworski.controller.EventTaxes;
import fr.efrei.nouvellonJaworski.controller.engine.GameEngineImplement;
import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.orders.OrderType;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.Simulation;

public class SimulationImplement implements Simulation{
	
	private final static int cost=100;
	
	private final Selector selector;
	private final Clock clock;
	private final GameEngine gameEngine;
	private final CityBorder border;
	protected final List<Event> eventTriggered ;
	private final Ville ville;
	
	private int money;
	private int nbHabitantsAlive;
	private int nbOriginalHabitants;
	private int nbUpgradeOfTaxes;
	private int nbUpgradeOfScreeningCenter;
	
	private Instant lastUpdate;

	private Boolean firstHabitantIsInfected;
	
	public SimulationImplement(Clock clock, CityBorder border,Selector selector, int population) {
		this.firstHabitantIsInfected=false;
		this.clock=clock;
		this.border=border;
		this.selector=selector;
		this.nbOriginalHabitants=population;
		this.nbHabitantsAlive=population;
		this.gameEngine=new GameEngineImplement(clock);
		this.eventTriggered = new ArrayList<Event>();
		this.lastUpdate=clock.instant();
		this.money=0;
		this.nbUpgradeOfTaxes=0;
		this.nbUpgradeOfScreeningCenter=0;
		this.ville=new Ville(population,border,selector);
		this.launchInitialContamination();
		

	} 
	
	
	
	public void addHabitantsAlive(int nbr) {
		this.nbHabitantsAlive=this.nbHabitantsAlive+nbr;
	}
	
	
	private void launchInitialContamination() {
		Event event1 = new EventInfect(Instant.EPOCH,  Duration.ofSeconds(3), gameEngine, this.eventTriggered,ville, selector,this);
		Event eventScreening = new EventScreening(Instant.EPOCH, Duration.ofMillis(200), gameEngine,this.eventTriggered, ville, selector,this);
		Event eventTaxes=new EventTaxes(Instant.EPOCH,  Duration.ofSeconds(5), gameEngine, this.eventTriggered,this);
		gameEngine.register(event1,eventScreening,eventTaxes);
		gameEngine.update();
		
	}
	
	

	
	
	@Override 
	public void update() {
		this.gameEngine.update();
	}
	
	
	@Override
	public Instant getCurrentInstant() {
		return lastUpdate;
	}
	
	
	@Override
	public int getOriginalPopulation() {
		return nbOriginalHabitants;
	}
	
	
	@Override
	public int getLivingPopulation() {
		
		return ville.getHabitantsHealthy().size()+ville.getHabitantsInfected().size()
				+ville.getHabitantsIsolated().size();
	}
	
	
	@Override
	public int getInfectedPopulation() {
		return ville.getHabitantsInfected().size()+ville.getHabitantsIsolated().size(); 
	}
	
	
	@Override
	public int getQuarantinedPopulation() {
		return ville.getHabitantsIsolated().size();
	}
	
	
	@Override
	public int getDeadPopulation() {
		
		return ville.getHabitantsDead().size();
	}

	public void updateMoney() {
		
		this.money=this.money+
				(this.ville.getHabitantsHealthy().size()+ville.getHabitantsInfected().size()+ville.getHabitantsIsolated().size())
				*(this.nbUpgradeOfTaxes+1);
	
	}
	@Override
	public long getMoney() {
		return money;
	}
	
	public void incrUpradeOfScreeningCenter() {
		this.nbUpgradeOfScreeningCenter++;
	}
	
	public void incrUpradeOfTaxes() {
		this.nbUpgradeOfTaxes++;
	}
	
	public int getNbUpgradeOfScreeningCenter() {
		return this.nbUpgradeOfScreeningCenter;
	}
	@Override
	public void executeOrder(OrderType order) {
		if(enoughMoney()) {
			if(order.equals(OrderType.INCREASE_TAXES)) {
				EventIncreaseTaxes center=new EventIncreaseTaxes(this.lastUpdate, Duration.ofSeconds(5),
						gameEngine, eventTriggered,this);
				gameEngine.register(center);
	
			}else {
				EventScreeningCenter center=new EventScreeningCenter(this.lastUpdate, Duration.ofSeconds(5),
						gameEngine, eventTriggered,this);
				gameEngine.register(center);
			}
		
		
			this.money=this.money-cost;
		}
	}

	private boolean enoughMoney() {
		
		if(money-cost<0) {
			System.out.println("manque d'argent pour effectuer cette t�che");
			return false;
		}
		
		return true;
	}



	@Override
	public double getPanicLevel() {
		return ville.getPanic();
	}

	public void setFirstHabitantIsInfected(Boolean status) {
		this.firstHabitantIsInfected=status;
	}
	
	public Boolean getFirstHabitantIsInfected() {
		return this.firstHabitantIsInfected;
	}
	
	@Override
	public void startReceivingImmigrant(boolean isInfected) {
		gameEngine.update();
		Event event = new EventImmigration(clock.instant(), Duration.ofSeconds(3), gameEngine,
				eventTriggered, ville, isInfected,selector);
		gameEngine.register(event);
		
	}
	
	
}
