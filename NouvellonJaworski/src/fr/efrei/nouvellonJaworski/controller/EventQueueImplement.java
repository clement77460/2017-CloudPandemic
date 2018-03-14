package fr.efrei.nouvellonJaworski.controller;

import java.util.ArrayList;

import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.EventQueue;

public class EventQueueImplement implements EventQueue{

	private ArrayList<EventStorage> list = new ArrayList<EventStorage>();

	@Override
	public void register(Event... events) { 
		for (Event event : events) {
			list.add((EventStorage) event);
		}
	} 
	
	public ArrayList<EventStorage> extractRegisteredList() {
		ArrayList<EventStorage> list = this.list;
		
		this.list = new ArrayList<EventStorage>();
		
		return list;
	}
}
