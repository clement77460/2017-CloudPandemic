package fr.efrei.nouvellonJaworski.model.eventRate;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

public class RateStorage {
	
	private double spreadingRate=1.0;
	private Deque<Double> spreadingRateStack;
	
	private double cureRate=1.0;
	private double deathRate=1.0;
	
	public RateStorage() {
		this.spreadingRateStack=new ArrayDeque<Double>();
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
		
	}
	
	public void increaseSpreadingRate() {
		
		BigDecimal rate=new BigDecimal(spreadingRate);
		BigDecimal multiplicateur=new BigDecimal("0.80");
		this.spreadingRateStack.push(spreadingRate);
		this.spreadingRate=rate.multiply(multiplicateur).doubleValue();
	}
	
	public void decreaseSpreadingRate() {
		this.spreadingRate=this.spreadingRateStack.peek();
		
	}
	
	public double getSpreadingRate() {
		return this.spreadingRate;
	}
	
	
	
}
