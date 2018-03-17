package fr.efrei.paumier.shared.selection;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FakeSelector implements Selector {

	private HashMap<Integer, Integer> choicePerInvocationIndex = new HashMap<Integer, Integer>();
	private Optional<Integer> defaultValue = Optional.empty();

	private int nextRecordingIndex = 0;
	private int nextInvocationIndex = 0;

	public void enqueueRanks(int... ranks) {		
		for (int rank: ranks) {
			putNext(rank);
		}
	}

	public void enqueueRankMultipleTimes(int rank, int times) {
		for (int i = 0; i < times; i++)
			putNext(rank);
	}

	private void putNext(int rank) {
		choicePerInvocationIndex.put(nextRecordingIndex++, rank);
	}

	public void skipNext(int invocations) {
		nextRecordingIndex += invocations;
	}

	public void setDefaultValue(Integer defaultValue) {
		this.defaultValue = Optional.of(defaultValue);
	}

	@Override
	public <TItem> TItem selectAmong(List<TItem> choices) {
		if (choices.size() == 0)
			return null;
		
		int choice = getNextChoice();
		if (choice < 0)
			choice += choices.size();
		
		return choices.get(choice);
	}

	private int getNextChoice() {
		int currentInvocationIndex = nextInvocationIndex++;
		
		if (choicePerInvocationIndex.containsKey(currentInvocationIndex)) {
			return choicePerInvocationIndex.get(currentInvocationIndex);
			
		} else if (defaultValue.isPresent()) {
			return defaultValue.get();
		
		} else {		
			throw new IllegalStateException();
		}
	}
}