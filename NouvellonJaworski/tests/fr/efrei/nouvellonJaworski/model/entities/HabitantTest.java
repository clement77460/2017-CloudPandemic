package fr.efrei.nouvellonJaworski.model.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HabitantTest {
	
	@Test
	//Test l'infection d'un habitant 
	public void infection_new_Habitant() {
		
		Habitant habitantInfected = new Habitant();
		habitantInfected.infectSomeone();
		
		assertTrue(habitantInfected.isInfected());
		
	}
	
	@Test
	//Test l'infection d'un habitant par un autre habitant infecté
	public void Habitant_Infect_Habitant() {
		
		Habitant habitantInfected = new Habitant();
		habitantInfected.infectSomeone();
		
		Habitant habitantToInfect = new Habitant();
		
		habitantInfected.infectSomeone(habitantToInfect);
	
		assertTrue(habitantToInfect.isInfected());	
	}
	
	@Test
	//Test l'infection d'un habitant sain par un habitant sain 
	
	public void HabitantSain_Infect_HabitantSain() {
		Habitant habitantSain = new Habitant();
		Habitant habitantSain2 = new Habitant();
		
		habitantSain.infectSomeone(habitantSain2);
		
		assertFalse(habitantSain2.isInfected());
	}
	
	@Test
	//Test l'infection d'un habitant en quarantaine par habitant sain
	public void HabitantQuarantaine_Infect_HabitantSain() {
		Habitant habitantQuarantaine = new Habitant();
		habitantQuarantaine.setIsolated(true);
		
		Habitant habitantSain = new Habitant();
		
		habitantSain.infectSomeone(habitantQuarantaine);
		
		assertFalse(habitantQuarantaine.isInfected());
	}
	
	@Test
	//Test l'infection d'un habitant infecté par un habitant infecté
	public void HabitantInfected_Infect_HabitantInfected() {
		Habitant habitantInfected = new Habitant();
		habitantInfected.infectSomeone();
		
		Habitant habitantInfected2 = new Habitant();
		habitantInfected2.infectSomeone();
		
		habitantInfected.infectSomeone(habitantInfected2);
		assertTrue(habitantInfected2.isInfected());
	}
	
	@Test
	//Test la guérison d'un habitant sain
	public void test_habitantSain() {
		Habitant habitantSain = new Habitant();
		habitantSain.healHabitant();
		
		assertFalse(habitantSain.isDead());
		assertFalse(habitantSain.isEmigrated());
		assertFalse(habitantSain.isInfected());
		assertFalse(habitantSain.isIsolated());
	}
	
	@Test
	//Test de la guérison d'un habitant infecté 
	public void test_healHabitantInfeted() {
		
		Habitant habitantToHeal = new Habitant();
		habitantToHeal.infectSomeone();
		habitantToHeal.isolateHabitant();
		habitantToHeal.healHabitant();
		
		assertTrue(!habitantToHeal.isInfected());
	}
	
	@Test
	//Test la guérison d'un habitant en quarantaine
	public void test_healHabitantQuarantine() {
		Habitant habitantQuarantine = new Habitant();
		habitantQuarantine.healHabitant();
		
		assertFalse(habitantQuarantine.isIsolated());
	}
	
	@Test
	//Test la guérison d'un habitant mort
	public void test_healHabitantDead() {
		Habitant habitantDead = new Habitant();
		habitantDead.killHabitant();
		habitantDead.healHabitant();
		
		assertFalse(habitantDead.isDead());
	}
	
	@Test
	//Test l'isolation d'un habitant mort 
	public void test_isolateHabitantDead() {
		Habitant habitantDead = new Habitant();
		habitantDead.killHabitant();
		habitantDead.isolateHabitant();
		
		assertFalse(habitantDead.isIsolated());
	}
	@Test
	//Test l'isolation d'un habitant isolé
	public void test_isolateHabitantIsolated() {
		Habitant habitantIsolated = new Habitant();
		habitantIsolated.isolateHabitant();
		
		assertFalse(habitantIsolated.isIsolated());
	}
	@Test
	//Test l'isolation d'un habitant sain 
	public void test_isolateHabitantHealthy() {
		Habitant habitantHealthy = new Habitant();
		habitantHealthy.isolateHabitant();
		
		assertFalse(habitantHealthy.isIsolated());
	}
	
	@Test
	//Test de l'isolation d'un habitant infecté
	public void test_isolateHabitantInfected() {
		
		Habitant habitantToIsolate = new Habitant();
		habitantToIsolate.infectSomeone();
		habitantToIsolate.isolateHabitant();
		
		assertTrue(habitantToIsolate.isIsolated());
	}
	
	@Test
	//Test la mort d'un habitant
	public void test_killHabitant() {
		Habitant habitantToKill = new Habitant();
		habitantToKill.infectSomeone();
		habitantToKill.killHabitant();
		
		assertTrue(habitantToKill.isDead());
	}
	@Test
	//Test la mort d'un habitant isolé
	public void test_killHabitantIsolated() {
		Habitant habitantIsolated = new Habitant();
		habitantIsolated.isolateHabitant();
		
		habitantIsolated.killHabitant();
		
		assertFalse(habitantIsolated.isDead());
	}
	
	@Test
	//Test la mort d'un habitant sain
	public void test_killHabitantHealthy() {
		Habitant habitantHealthy = new Habitant();
		habitantHealthy.killHabitant();
		
		assertFalse(habitantHealthy.isDead());
	}
	
	@Test
	//Test la mort d'un habitant mort
	public void test_killHabitantDead() {
		Habitant habitantDead = new Habitant();
		habitantDead.killHabitant();
		
		habitantDead.killHabitant();
		
		assertFalse(habitantDead.isDead());
	}
	
	@Test
	//Test la méthode isInfected
	public void test_IsInfected() {
		Habitant habitantInfected = new Habitant();
		habitantInfected.infectSomeone();
		
		assertTrue(habitantInfected.isInfected());
	}
	
	@Test
	//Test la partie contamination de la méthode contaminerOuSoigner
	public void test_contaminer() {
		Habitant habitantToInfect = new Habitant();
		
		habitantToInfect.contaminerOuSoigner(true);
		
		assertTrue(habitantToInfect.isInfected());
	}
	
	@Test 
	//Test la partie soigner de la méthode contaminerOuSoigner
	public void test_soigner() {
		Habitant habitantToHeal = new Habitant();
		habitantToHeal.infectSomeone();
		
		habitantToHeal.contaminerOuSoigner(false);
		
		assertFalse(habitantToHeal.isInfected());
	}
	
	@Test 
	//Test la méthode isIsolated
	public void test_isIsolated() {
		Habitant habitantToIsolate = new Habitant();
		habitantToIsolate.setIsolated(true);
		
		assertTrue(habitantToIsolate.isIsolated());
	}
	
	@Test
	//Test l'isolation d'un habitant
	public void test_isolation() {
		Habitant habitantToIsolate = new Habitant();
		habitantToIsolate.setIsolated(true);
		
		assertTrue(habitantToIsolate.isIsolated());
	}
	
	@Test
	//Test la sortie de l'isolation
	public void test_sortieIsolation() {
		Habitant habitantIsolated = new Habitant();
		habitantIsolated.setIsolated(true);
		
		habitantIsolated.setIsolated(false);
		
		assertFalse(habitantIsolated.isIsolated());
	}
	
	@Test
	//Test de la mort d'un habitant Sans l'infection
	public void test_deathHabitantWithoutInfection() {
		Habitant habitantToKill = new Habitant();
		habitantToKill.killHabitant();
		
		assertFalse(habitantToKill.isDead());
	}
	
	@Test
	//Test de la mort d'un habitant 
	public void test_deathHabitantWithInfection() {
		Habitant habitantToKill = new Habitant();
		habitantToKill.infectSomeone();
		habitantToKill.killHabitant();
		
		assertTrue(habitantToKill.isDead());
	}
	
	@Test
	//Test de l'emigration d'habitant
	public void test_emigration() {
		Habitant habitantEmigrated = new Habitant();
		habitantEmigrated.setEmigrated(true);
		
		assertTrue(habitantEmigrated.isEmigrated());
	}
	
	@Test
	public void test_stopEmigration() {
		Habitant habitantNotEmigrated = new Habitant();
		habitantNotEmigrated.setEmigrated(false);
		
		assertFalse(habitantNotEmigrated.isEmigrated());
	}
}
