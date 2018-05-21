package fr.efrei.nouvellonJaworski.launcher;

import java.util.Scanner;

import fr.efrei.nouvellonJaworski.vue.PartieLocale;
import fr.efrei.nouvellonJaworski.vue.PartieNoeud;
import fr.efrei.nouvellonJaworski.vue.PartieTerminal;

public class Lanceur {

	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		System.out.println("choix du type d'application :");
		System.out.println(" 0 : Partie Locale \n 1: Partie Noeud \n 2: Partie Terminale");
		switch(sc.nextInt()) {
			case 0:
				PartieLocale game=new PartieLocale(100);
				game.boucleJeu(); 
				sc.close();
				break;
			case 1:
				PartieNoeud game2=new PartieNoeud(100);
				game2.boucleJeu();
				sc.close();
				break;
			default:
				PartieTerminal game3=new PartieTerminal(100);
				game3.boucleJeu();
				sc.close();
				break;
		}
	}

}
