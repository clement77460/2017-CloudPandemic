package fr.efrei.nouvellonJaworski.vue;

import java.time.Clock;

import fr.efrei.nouvellonJaworski.model.abstractmodel.TableModelPandemic;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.nouvellonJaworski.networking.Client;
import fr.efrei.nouvellonJaworski.vue.frame.PandemicFrame;
import fr.efrei.paumier.shared.orders.OrderType;

public abstract class AbstractNetworkGame {
	
	private Clock clock2;
	private MySelector selector;
	protected SimulationImplement simulation;
	protected Client cityBorder;
	private PandemicFrame vue;
	private TableModelPandemic tableModel;
	
	
	public AbstractNetworkGame(int population,String ip,int port) {
		clock2=Clock.systemUTC();
		this.selector=new MySelector();
		
		//this.cityBorder=new Client("localhost",11111);
		this.cityBorder=new Client("178.62.119.191",4242);
		 
		this.simulation = new SimulationImplement(clock2, cityBorder,selector, population);
		((Client) cityBorder).setSimulation(simulation);
		new Thread( (Runnable) this.cityBorder).start();
		
		this.tableModel=new TableModelPandemic();
		this.tableModel.fillTableWithStats(this.simulation.getStatistics());
		
		vue=new PandemicFrame(this.tableModel,this);
        vue.setVisible(true);
	}
	
	
	public void boucleJeu() {
		while(this.simulation.getFirstHabitantIsInfected()==false  ||
				!(this.simulation.getInfectedPopulation()==0)) {
			
			try {
				Thread.sleep(200);//pause de 200 ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.simulation.update();
			this.envoiStats();
			this.tableModel.fillTableWithStats(this.simulation.getStatistics());
			this.vue.repaint();
			
		}
		if(this.simulation.getLivingPopulation()!=0) {
			System.out.println("victoire");
		}else {
			System.out.println("fin de la partie, toute la population est infectée");
		}
	}
	
	private void envoiStats() {
		this.simulation.update();
		this.simulation.sendStatistics();
	}
	
	abstract public void launchOrder(String destinataire,OrderType orderType);
}
