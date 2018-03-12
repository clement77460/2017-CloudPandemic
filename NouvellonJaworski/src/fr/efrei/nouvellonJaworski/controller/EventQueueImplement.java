package fr.efrei.nouvellonJaworski.controller;

import java.util.ArrayList;

import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.EventQueue;

public class EventQueueImplement implements EventQueue{

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
