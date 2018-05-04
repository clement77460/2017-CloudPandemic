package fr.efrei.paumier.shared.simulation;

import java.time.Duration;

import fr.efrei.paumier.shared.networking.messaging.Message;

public class Statistics implements Message {
	private static final long serialVersionUID = -754972027220032817L;
	
	private final int originalPopulation;
	private final int livingPopulation;
	private final int infectedPopulation;
	private final int quarantinedPopulation;
	private final int deadPopulation;
	
	private final long money;
	private final double panicLevel;
	private final Duration ellapsedDuration;
	
	public Statistics(
			int originalPopulation,
			int livingPopulation,
			int infectedPopulation,
			int quarantinedPopulation,
			int deadPopulation, 
			long money, 
			double panicLevel, 
			Duration ellapsedDuration) {
		
		this.originalPopulation = originalPopulation;
		this.livingPopulation = livingPopulation;
		this.infectedPopulation = infectedPopulation;
		this.quarantinedPopulation = quarantinedPopulation;
		this.deadPopulation = deadPopulation;
		
		this.money = money;
		this.panicLevel = panicLevel;
		this.ellapsedDuration = ellapsedDuration;
	}

	public int getOriginalPopulation() {
		return originalPopulation;
	}

	public int getLivingPopulation() {
		return livingPopulation;
	}

	public int getInfectedPopulation() {
		return infectedPopulation;
	}

	public int getQuarantinedPopulation() {
		return quarantinedPopulation;
	}

	public int getDeadPopulation() {
		return deadPopulation;
	}

	public long getMoney() {
		return money;
	}

	public double getPanicLevel() {
		return panicLevel;
	}

	public Duration getEllapsedDuration() {
		return ellapsedDuration;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Ellapsed : ").append(ellapsedDuration).append("\n");
		builder.append("Population : ").append(getLivingPopulation()).append("\n");
		builder.append("Infected : ").append(infectedPopulation).append("\n");
		builder.append("Quarantined : ").append(quarantinedPopulation).append("\n");
		builder.append("Money : ").append(money).append("\n");
		builder.append("Panic level : ").append(panicLevel);
		
		return builder.toString();
	}
}
