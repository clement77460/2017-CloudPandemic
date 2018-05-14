package fr.efrei.nouvellonJaworski.model.eventRate;

public class RateStorage {
	
	private double spreadingRate=1.0;
	private double cureRate=1.0;
	private double deathRate=1.0;
	
	public RateStorage() {
		
	}
	
	public double getCureRate() {
		return this.cureRate;
	}
	
	public void incrCureRate() {
		this.cureRate++;
	}
	
	public double getDeathRate() {
		return this.deathRate;
	}
	
	public void incrDeathRate() {
		this.deathRate=this.deathRate*0.20;
		System.out.println(this.deathRate);
	}
}
