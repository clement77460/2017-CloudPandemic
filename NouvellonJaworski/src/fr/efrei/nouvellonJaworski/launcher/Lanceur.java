package fr.efrei.nouvellonJaworski.launcher;
import fr.efrei.nouvellonJaworski.controller.Controller;
import fr.efrei.nouvellonJaworski.model.stats.*;
public class Lanceur {

	public static void main(String[] args) {
		FakeStatistique test=new FakeStatistique();
		test.setUp();
		test.starts_everybodyIsHealthy();
		test.sec003_onePersonIsInfected();
		test.sec008_twoPersonsAreInfected();
		//Controller controllerPartie=new Controller(2,10);
		// generation aleatoire de la ville et de l'habitant pour la premiere infect
		//controllerPartie.lancementPartie();
		//FakeStatistique test=new FakeStatistique();
	}

}
