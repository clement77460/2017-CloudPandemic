package fr.efrei.nouvellonJaworski.model.entities;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.EventScreeningCenter;
import fr.efrei.nouvellonJaworski.controller.EventSpreading;
import fr.efrei.nouvellonJaworski.controller.EventDeath;
import fr.efrei.nouvellonJaworski.controller.EventDecreaseCurfew;
import fr.efrei.nouvellonJaworski.controller.EventImmigration;
import fr.efrei.nouvellonJaworski.controller.EventImproveMedicine;
import fr.efrei.nouvellonJaworski.controller.EventImproveVaccine;
import fr.efrei.nouvellonJaworski.controller.EventIncreaseCurfew;
import fr.efrei.nouvellonJaworski.controller.EventIncreaseTaxes;
import fr.efrei.nouvellonJaworski.controller.EventInfect;
import fr.efrei.nouvellonJaworski.controller.EventScreening;
import fr.efrei.nouvellonJaworski.controller.EventTaxes;
import fr.efrei.nouvellonJaworski.controller.engine.GameEngineImplement;
import fr.efrei.nouvellonJaworski.model.eventRate.RateStorage;
import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.orders.OrderType;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.simulation.Statistics;

public class SimulationImplement implements Simulation{
	
	private final static int cost=100;
	
	private final Selector selector;
	private final Clock clock;
	private final Instant beginTime;
	private final GameEngine gameEngine;
	private final CityBorder border;
	protected final List<Event> eventTriggered ;
	private final Ville ville;
	private final RateStorage rateStorage;
	
	
	
	private int money=0;
	private int nbOriginalHabitants=0;
	
	private int nbUpgradeOfTaxes=0;
	private int nbUpgradeOfScreeningCenter=0;
	private int nbIncreaseOfCurfew=0;
	private int nbDecreaseOfCurfew=0;
	
	private Instant lastUpdate;

	private Boolean firstHabitantIsInfected=false;
	
	public SimulationImplement(Clock clock, CityBorder border,Selector selector, int population) {
		
		this.clock=clock;
		this.beginTime=clock.instant();
		this.border=border;
		this.selector=selector;
		this.nbOriginalHabitants=population;
		
		this.gameEngine=new GameEngineImplement(clock);
		this.eventTriggered = new ArrayList<Event>();
		this.lastUpdate=clock.instant();
		this.ville=new Ville(population,border,selector);
		this.rateStorage=new RateStorage();
		this.launchInitialContamination();
		

	} 
	
	
	
	private void launchInitialContamination() {
		Event event1 = new EventInfect(Duration.ofSeconds(3), this.eventTriggered,this);
		Event eventScreening = new EventScreening(Duration.ofMillis(200), gameEngine,this.eventTriggered, ville, selector,this);
		Event eventTaxes=new EventTaxes(Duration.ofSeconds(5), gameEngine, this.eventTriggered,this);
		gameEngine.register(event1,eventScreening,eventTaxes);
		gameEngine.update();
		
	}
	
	public void createInfectAndDeathEvent(Habitant source,List<Event> triggeredEventsList) {
		if(ville.getHabitantsHealthy().size()>0) {
			
			Habitant target = selector.selectAmong(ville.getHabitantsHealthy());
			ville.getHabitantsHealthy().remove(target);
			ville.getHabitantsInfected().add(target);
			
			EventSpreading eventSpreading = new EventSpreading(Duration.ofSeconds(5),
					triggeredEventsList, ville, target,this);
			EventDeath eventDeath = new EventDeath(Duration.ofSeconds(15),
					triggeredEventsList, ville, target,this);
			
			if(source==null) {
				target.contaminerOuSoigner(true);
				gameEngine.register(eventSpreading,eventDeath);
				
			}
			else {
				source.infectSomeone(target);
				EventSpreading eventSpreading2 = new EventSpreading(Duration.ofSeconds(5), triggeredEventsList, ville, source,this);
				gameEngine.register(eventSpreading,eventSpreading2,eventDeath);
			}
			
			
		}
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
		
		this.money+=(this.ville.getHabitantsHealthy().size()+
				ville.getHabitantsInfected().size()+ville.getHabitantsIsolated().size())
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
		boolean toPay=true;
		if(enoughMoney()) {
			switch(order) {
				case INCREASE_TAXES:
					EventIncreaseTaxes increaseTaxes=new EventIncreaseTaxes(Duration.ofSeconds(5),
							this);
					gameEngine.register(increaseTaxes);
					break;
				
				case BUILD_SCREENING_CENTER:
					EventScreeningCenter screeningCenter=new EventScreeningCenter(Duration.ofSeconds(5),this);
					gameEngine.register(screeningCenter);
					break;	
				case RESEARCH_IMPROVED_MEDICINE:
					EventImproveMedicine improveMedicine=new EventImproveMedicine(Duration.ofSeconds(5),this);
					gameEngine.register(improveMedicine);
					break;
				case INCREASE_CURFEW:
					EventIncreaseCurfew increaseCurfew=new EventIncreaseCurfew(Duration.ofSeconds(5),
							this);
					gameEngine.register(increaseCurfew);
					this.increaseNbIncreaseOfCurfew();
					break;
				case REDUCE_CURFEW:
					toPay=this.checkConditionsAndLaunchReduceCurfew();
					break;
				case RESEARCH_IMPROVED_VACCINE:
					EventImproveVaccine improveVaccine=new EventImproveVaccine(Duration.ofSeconds(5),this);
					gameEngine.register(improveVaccine);
					break;
				default:
					break;
			}
			if(toPay) {
				this.money=this.money-cost;
			}
		}
	}
	private boolean checkConditionsAndLaunchReduceCurfew() {
		if(this.nbIncreaseOfCurfew>this.nbDecreaseOfCurfew) {
			EventDecreaseCurfew decreaseCurfew=new EventDecreaseCurfew(Duration.ofSeconds(5),this);
			gameEngine.register(decreaseCurfew);
			this.increaseNbDecreaseOfCurfew();
			return true;
		}
		return false;
	}

	private boolean enoughMoney() {
		
		if(money-cost<0) {
			
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
		Event event = new EventImmigration(Duration.ofSeconds(3), gameEngine,
				eventTriggered, ville, isInfected,this);
		gameEngine.register(event);
		
	}



	@Override
	public void sendStatistics() {
		border.sendStatistics(this.getStatistics());
	}
	
	public Statistics getStatistics() {
		return new Statistics(this.getOriginalPopulation(),
				this.getLivingPopulation(),
				this.getInfectedPopulation(),
				this.getQuarantinedPopulation(),
				this.getDeadPopulation(), 
				this.getMoney(), 
				this.getPanicLevel(), 
				Duration.between(beginTime, clock.instant()));
	}
	
	
	public RateStorage getRateStorage() {
		return this.rateStorage;
	}
	
	public Ville getVille() {
		return this.ville;
	}



	public int getNbIncreaseOfCurfew() {
		return nbIncreaseOfCurfew;
	}



	public int getNbDecreaseOfCurfew() {
		return nbDecreaseOfCurfew;
	}

	public void increaseNbIncreaseOfCurfew() {
		this.nbIncreaseOfCurfew++;
	}

	public void increaseNbDecreaseOfCurfew() {
		this.nbDecreaseOfCurfew++;
	}
	
	
}
