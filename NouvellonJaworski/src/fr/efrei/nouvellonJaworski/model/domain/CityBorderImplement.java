package fr.efrei.nouvellonJaworski.model.domain;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.paumier.shared.domain.CityBorder;

public class CityBorderImplement implements CityBorder{

	private final ArrayList<Boolean> emigrants = new ArrayList<>();
	
	@Override
	public void sendEmigrant(boolean isInfected) {
		emigrants.add(isInfected);
	}
	
	public List<Boolean> getEmigrants() {
		return emigrants;
	}

}
