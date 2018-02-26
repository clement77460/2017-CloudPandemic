package fr.efrei.paumier.shared.events;

import java.util.ArrayList;

public class FakeEventQueue implements EventQueue {

	private ArrayList<Event> list = new ArrayList<Event>();

	@Override
	public void register(Event... events) {
		for (Event event : events) {
			list.add(event);
		}
	}
	
	public ArrayList<Event> extractRegisteredList() {
		ArrayList<Event> list = this.list;
		
		this.list = new ArrayList<Event>();
		
		return list;
	}
}
