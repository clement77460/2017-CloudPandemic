package fr.efrei.paumier.shared.domain;

import java.util.ArrayList;
import java.util.List;

public class FakeCityBorder implements CityBorder {
	private final ArrayList<Boolean> emigrants = new ArrayList<>();
	
	@Override
	public void sendEmigrant(boolean isInfected) {
		emigrants.add(isInfected);
	}
	
	public List<Boolean> getEmigrants() {
		return emigrants;
	}
}
