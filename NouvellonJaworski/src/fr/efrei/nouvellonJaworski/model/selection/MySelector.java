package fr.efrei.nouvellonJaworski.model.selection;

import java.util.LinkedList;
import java.util.List;

import fr.efrei.paumier.shared.selection.Selector;

public class MySelector implements Selector{
	private LinkedList<Integer> queue = new LinkedList<Integer>();
	@Override
	public <TItem> TItem selectAmong(List<TItem> choices) {
		if (choices.size() <= queue.peek())
			return null;
		
		return choices.get(queue.poll());
	}

	public void enqueueRanks(int... ranks) {
		for (int rank : ranks) {
			queue.offer(rank);	
		}
	}
	
}
