package fr.efrei.nouvellonJaworski.model.selection;


import java.util.List;

import fr.efrei.paumier.shared.selection.Selector;

public class MySelector implements Selector{
	@Override
	public <TItem> TItem selectAmong(List<TItem> choices) {

		return choices.get((int )(Math.random() * choices.size()));
	}
	
}
