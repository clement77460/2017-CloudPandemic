package fr.efrei.paumier.shared.selection;

import java.util.List;

public interface Selector<TItem> {
	TItem selectAmong(List<TItem> choices);
}
