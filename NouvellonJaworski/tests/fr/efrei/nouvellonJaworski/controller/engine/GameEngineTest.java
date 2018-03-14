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

	@Override
	protected GameEngine createGameEngine(Clock clock) {
		return new GameEngineImplement(clock);
	}
	

}
