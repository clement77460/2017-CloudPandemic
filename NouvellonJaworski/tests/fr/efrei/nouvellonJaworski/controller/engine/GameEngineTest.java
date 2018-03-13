package fr.efrei.nouvellonJaworski.controller.engine;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertSame;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;

import fr.efrei.paumier.shared.engine.*;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.FakeEvent;
import fr.efrei.paumier.shared.time.FakeClock;


public class GameEngineTest extends BaseGameEngineTests{
	private FakeClock clock;
	private GameEngine manager;
	
	@Before
	public void setUp() {
		clock = new FakeClock(Clock.fixed(Instant.EPOCH,
				ZoneId.systemDefault()));
		manager = createGameEngine(clock); 
	}
	
	@Override
	protected GameEngine createGameEngine(Clock clock) {
		return new GameEngineImplement(clock);
	}
	
	@Test
	public void update_lowerThanAnyTime_nothingHappens() {
		System.out.println("lower");
		Event event = createEvent(Duration.ofSeconds(10));
		manager.register(event); //ajouter dans la liste

		clock.advance(Duration.ofSeconds(1));
		manager.update();

		assertEquals(0, eventTriggered.size());
	}
	@Test
	public void update_higherThanOnlyEvent_eventHappens() {
		System.out.println("higher");
		Event event = createEvent(Duration.ofSeconds(10));
		manager.register(event);

		clock.advance(Duration.ofSeconds(15));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event, eventTriggered.get(0));
	}
	@Test
	public void update_multipleSmallTimes_eventHappens() {
		System.out.println("multiple");
		FakeEvent event = createEvent(Duration.ofSeconds(9), manager);
		manager.register(event);

		clock.advance(Duration.ofSeconds(2));
		manager.update();

		clock.advance(Duration.ofSeconds(2));
		manager.update();

		clock.advance(Duration.ofSeconds(2));
		manager.update();

		clock.advance(Duration.ofSeconds(2));
		manager.update();

		clock.advance(Duration.ofSeconds(2));
		manager.update();

		clock.advance(Duration.ofSeconds(2));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event, eventTriggered.get(0));
		assertEquals(Instant.EPOCH.plusSeconds(9), event.getTriggeredInstant());
	}
	@Test
	public void update_equalsToOnlyEvent_eventHappens() {
		System.out.println("equals");
		Event event = createEvent(Duration.ofSeconds(10));
		manager.register(event);

		clock.advance(Duration.ofSeconds(10));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event, eventTriggered.get(0));
	}
	@Test
	public void update_betweenTwoEvents_ordered_firstEventHappens() {
		System.out.println("between");
		Event event1 = createEvent(Duration.ofSeconds(10));
		Event event2 = createEvent(Duration.ofSeconds(20));
		manager.register(event1, event2);

		clock.advance(Duration.ofSeconds(15));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event1, eventTriggered.get(0));
	}
	@Test
	public void update_betweenTwoEvents_firstEventHappens() {
		System.out.println("between big then little");
		Event event1 = createEvent(Duration.ofSeconds(20));
		Event event2 = createEvent(Duration.ofSeconds(10));
		manager.register(event1, event2);

		clock.advance(Duration.ofSeconds(15));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event2, eventTriggered.get(0));
	}
	@Test
	public void update_AfterTwoEvents_firstEventHappensBeforeOther() {
		Event event1 = createEvent(Duration.ofSeconds(20));
		Event event2 = createEvent(Duration.ofSeconds(10));
		manager.register(event1, event2);

		clock.advance(Duration.ofSeconds(30));
		manager.update();

		assertEquals(2, eventTriggered.size());
		assertSame(event2, eventTriggered.get(0));
		assertSame(event1, eventTriggered.get(1));
	}
	private FakeEvent createEvent(Duration duration) {
		return new FakeEvent(Instant.EPOCH, duration, null, this.eventTriggered);
	}

}
