package fr.efrei.nouvellonJaworski.model.eventRate;

import java.math.BigDecimal;

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
		BigDecimal rate=new BigDecimal(deathRate);
		BigDecimal multiplicateur=new BigDecimal("0.80");
		this.deathRate=rate.multiply(multiplicateur).doubleValue();
		System.out.println(this.deathRate);
	}
}
