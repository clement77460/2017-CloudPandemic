package fr.efrei.paumier.shared.domain;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.paumier.shared.simulation.Statistics;

public class FakeCityBorder implements CityBorder {
	private final ArrayList<Boolean> emigrants = new ArrayList<>();
	private final ArrayList<Statistics> statisticsList = new ArrayList<>();
	
	@Override
	public void sendEmigrant(boolean isInfected) {
		emigrants.add(isInfected);
	}
	
	public List<Boolean> getEmigrants() {
		return emigrants;
	}
	
	public List<Statistics> getStatisticsList() {
		return statisticsList;
	}

	@Override
	public void sendStatistics(Statistics statistics) {
		statisticsList.add(statistics);
	}
}
