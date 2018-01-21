package fr.efrei.paumier.shared.selection;

import java.util.LinkedList;
import java.util.List;

public class FakeSelector<TItem> implements Selector<TItem> {

	private LinkedList<Integer> queue = new LinkedList<Integer>();
	
	public FakeSelector(int... ranks) {
		enqueueRanks(ranks);
	}
	
	public void enqueueRanks(int... ranks) {
		for (int rank : ranks) {
			queue.offer(rank);	
		}
	}

	@Override
	public TItem selectAmong(List<TItem> choices) {
		if (choices.size() <= queue.peek())
			return null;
		
		return choices.get(queue.poll());
	}

}
