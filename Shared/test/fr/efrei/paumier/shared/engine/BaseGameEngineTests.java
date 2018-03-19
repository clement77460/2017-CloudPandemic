package fr.efrei.paumier.shared.engine;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import fr.efrei.paumier.shared.GradingTests;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.FakeEvent;
import fr.efrei.paumier.shared.time.FakeClock;
import fr.efrei.paumier.shared.time.TimeManager;

@Category(GradingTests.class)
public abstract class BaseGameEngineTests {

	private FakeClock clock;
	private GameEngine manager;
	
	@Before
	public void setUp() {
		clock = new FakeClock();
		manager = createGameEngine(clock);
	}
	
	protected List<Event> eventTriggered = new ArrayList<Event>();

	protected abstract GameEngine createGameEngine(Clock clock);

	@Test
	public void update_lowerThanAnyTime_nothingHappens() {
		Event event = createEvent(Duration.ofSeconds(10));
		manager.register(event);

		clock.advance(Duration.ofSeconds(1));
		manager.update();

		assertEquals(0, eventTriggered.size());
	}

	@Test
	public void update_higherThanOnlyEvent_eventHappens() {
		Event event = createEvent(Duration.ofSeconds(10));
		manager.register(event);

		clock.advance(Duration.ofSeconds(15));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event, eventTriggered.get(0));
	}

	@Test
	public void update_multipleSmallTimes_eventHappens() {
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
		Event event = createEvent(Duration.ofSeconds(10));
		manager.register(event);

		clock.advance(Duration.ofSeconds(10));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event, eventTriggered.get(0));
	}

	@Test
	public void update_betweenTwoEvents_ordered_firstEventHappens() {
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

	@Test
	public void update_eventRegisteredLate_happensAfterDuration() {
		Event event1 = createEvent(Duration.ofSeconds(10));

		clock.advance(Duration.ofSeconds(5));
		manager.update();
		
		manager.register(event1);

		clock.advance(Duration.ofSeconds(5));
		manager.update();

		assertEquals(0, eventTriggered.size());

		clock.advance(Duration.ofSeconds(5));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event1, eventTriggered.get(0));
	}

	@Test
	public void update_secondEventLongAndRegisteredLate_firstEventHappensFirst() {
		Event event1 = createEvent(Duration.ofSeconds(20));
		manager.register(event1);

		clock.advance(Duration.ofSeconds(5));
		manager.update();

		Event event2 = createEvent(Duration.ofSeconds(20));
		manager.register(event2);

		clock.advance(Duration.ofSeconds(15));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event1, eventTriggered.get(0));

		clock.advance(Duration.ofSeconds(5));
		manager.update();

		assertEquals(2, eventTriggered.size());
		assertSame(event1, eventTriggered.get(0));
		assertSame(event2, eventTriggered.get(1));
	}

	@Test
	public void update_secondEventShortAndRegisteredLate_secondEventHappensFirst() {
		Event event1 = createEvent(Duration.ofSeconds(20));
		manager.register(event1);

		clock.advance(Duration.ofSeconds(5));
		manager.update();
		
		Event event2 = createEvent(Duration.ofSeconds(10));
		manager.register(event2);

		clock.advance(Duration.ofSeconds(10));
		manager.update();

		assertEquals(1, eventTriggered.size());
		assertSame(event2, eventTriggered.get(0));

		clock.advance(Duration.ofSeconds(5));
		manager.update();

		assertEquals(2, eventTriggered.size());
		assertSame(event2, eventTriggered.get(0));
		assertSame(event1, eventTriggered.get(1));
	}

	private FakeEvent createEvent(Duration duration) {
		return new FakeEvent(Instant.EPOCH, duration, null, this.eventTriggered);
	}

	protected FakeEvent createEvent(Duration duration, TimeManager manager) {
		return new FakeEvent(Instant.EPOCH, duration, manager,
				this.eventTriggered);
	}
}
