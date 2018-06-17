package fr.efrei.nouvellonJaworski.model.entities;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.paumier.shared.domain.FakeCityBorder;


public class CityTest {
	@Test
	public void createNewCity() {
		Ville ville=new Ville(100,null,null);
		
		assertEquals(100,ville.getHabitantsHealthy().size());
		assertEquals(0,ville.getHabitantsDead().size());
		assertEquals(0,ville.getHabitantsIsolated().size());
		assertEquals(0,ville.getHabitantsInfected().size());
		assertEquals(0.0,ville.getPanic(),0.0);
	}
	@Test
	public void CantdecreasePanic() {
		Ville ville=new Ville(100,null,null);
		
		ville.decreasePanic(10.0);
		assertEquals(0.0,ville.getPanic(),0.0);
	}
	@Test
	public void increasePanicOfCity() {
		Ville ville=new Ville(100,null,null);
		
		ville.incrPanic(0.0);
		assertEquals(0.0,ville.getPanic(),0.0);
		
		ville.incrPanic(90.0);
		assertEquals(90.0,ville.getPanic(),0.0);
	}
	@Test
	public void increasePanicOfCityThenDecrease() {
		Ville ville=new Ville(100,null,null);
		
		ville.incrPanic(90.0);
		assertEquals(90.0,ville.getPanic(),0.0);
		
		ville.decreasePanic(90.0);
		assertEquals(0.0,ville.getPanic(),0.0);
	}
	
	@Test
	public void increasePanicMoreThanHabitantsAndBorderIsNull() {
		Ville ville=new Ville(100,null,null);
		
		ville.incrPanic(110.0);
		assertEquals(110.0,ville.getPanic(),0.0);
	}
	
	@Test
	public void increasePanicOfCityThenDecreaseMoreThenTheIncrease() {
		Ville ville=new Ville(100,null,null);
		
		ville.incrPanic(90.0);
		assertEquals(90.0,ville.getPanic(),0.0);
		
		ville.decreasePanic(100.0);
		assertEquals(90.0,ville.getPanic(),0.0);
	}
	
	@Test
	public void PanicIsHigherThanHabitants() {
		Ville ville=new Ville(100,new FakeCityBorder(),new MySelector());
		
		ville.incrPanic(110.0);
		
		assertEquals(95.0,ville.getPanic(),0.0);
		assertEquals(97,ville.getHabitantsHealthy().size());

	}
	
	@Test
	public void HighPanicEveryoneLeaveCity() {
		Ville ville=new Ville(100,new FakeCityBorder(),new MySelector());
		
		ville.incrPanic(500.0);
		
		assertEquals(0.0,ville.getPanic(),0.0);
		assertEquals(0,ville.getHabitantsHealthy().size());
	}
	
	@Test
	//Permet de vérifier que le selector ne sélectionne pas un habitant alors qu'aucun ne sont dispo
	public void HighPanicEveryoneLeaveCityButNotEnoughHabitants_AvoidSelectorError() {
		Ville ville=new Ville(100,new FakeCityBorder(),new MySelector());
		
		ville.incrPanic(600.0);
		
		assertEquals(0.0,ville.getPanic(),0.0);
		assertEquals(0,ville.getHabitantsHealthy().size());
	}
}
