package fr.efrei.nouvellonJaworski.controller.engine;


import java.time.Clock;
import fr.efrei.paumier.shared.engine.*;



public class GameEngineTest extends BaseGameEngineTests{

	@Override
	protected GameEngine createGameEngine(Clock clock) {
		return new GameEngineImplement(clock);
	}
	

}
