package org.monopoly;

import java.util.ArrayList;
import java.util.Collections;

public class JeuDeMonopoly {

    private final ArrayList<Joueur> joueurs = new ArrayList<>();
    private final Gobelet gobelet;
    private boolean stop = false;
    private ArrayList<CaseConstructible> caseLibreAAchat = new ArrayList<>();
    private Plateau plateau ;



    public JeuDeMonopoly() {
        plateau= new Plateau();
        joueurs.add(new Joueur("Marina","Elle", plateau.depart));
        joueurs.add(new Joueur("Ambre", "Elle", plateau.depart));
        joueurs.add(new Joueur("Loubna","Elle", plateau.depart));
        joueurs.add(new Joueur("Mathieu","Il", plateau.depart));
        joueurs.add(new Joueur("Cedric","Il", plateau.depart));
        gobelet = new Gobelet();
        caseLibreAAchat=  new ArrayList<>(plateau.getCaseAchetable());
    }


    public void jouerUnePartie() {
        while (!stop) {
            for (Joueur joueur : joueurs) {
                this.jouerUnTour(joueur);
                this.liberer(joueur);
            }
        }
        this.afficheFinDePartie();
    }


    private void jouerUnTour(Joueur joueur) {
        //verifier avant le joueur suivant si la partie est arrete
        if (stop) return;


        int total = gobelet.lancer();
        System.out.println( joueur.getNomJ() + " fait un total pour son lancer de des de " + total +".");

        // si le résultat n'est pas un double
        if (!gobelet.estUnDouble()) {

            joueur.aPasFaitUnDouble();   // donc on remet compteur double à 0
            if (!joueur.estEnPrison()) {
                jouerLeTotalDe(joueur, total);   // il joue son resultat
            }
            joueur.ouSuisJe();

        }
        else // si le resultat est un double
        {
            joueur.aFaitUnDouble(plateau.prison);  // incremente double met rejouer a true, le met en prison , condition liberable

            if (!joueur.estEnPrison()) {        // si pas ne prison ->  jouer  son resultat
                jouerLeTotalDe(joueur, total);
                joueur.ouSuisJe();
            }

            if (joueur.rejoue())    // Si  il a un double il va rejouer  condition nece car appel recursif
            {
                System.out.println(joueur.getNomJ() +" rejoue.");
                joueur.uneFoisCaSuffis();    // on remet a false son droit de rejouer  car appel recursif
                jouerUnTour(joueur);  // il joue un autre tour
            }

            if (joueur.getLiberable()) {   // libere le joueur en prison qui a fait un double
                joueur.liberationDouble();
                jouerLeTotalDe(joueur, total);
                joueur.ouSuisJe();
            }
        }
    }

    private void liberer(Joueur unjoueur) {
        // verifier si il est prison
        if (unjoueur.estEnPrison()) {
            unjoueur.liberationEnVue(); // incrementer le nombre de tour et si 3 le liberer
        }
    }

    private void jouerLeTotalDe(Joueur joueur, int total) {
        joueur.joue(total, plateau.depart, plateau.impot, plateau.luxe, plateau.allerenprison, plateau.prison);   // tester si cas construtible
        if (joueur.getPosition() instanceof  CaseConstructible) {
            joueur.acheterCase((CaseConstructible) joueur.getPosition(), caseLibreAAchat);
            joueur.payerLoyer((CaseConstructible) joueur.getPosition(), caseLibreAAchat, joueurs);
        }
        stop = joueur.finDePartie();
        // avancer sur le plateau et faire action

    }

    private void afficheFinDePartie() {
        System.out.println("La partie est terminee !!!");   // L'affichage de la suite etait pas deamnde mais ca parait plus coherent
        trie();
        System.out.println("Le vainqueur est " + joueurs.get(0).getNomJ() + " avec " + joueurs.get(0).getArgent() +".");
        joueurs.get(0).afficherLesProprietes() ;
        for (int i=1; joueurs.size()>i; i++) {
            if(joueurs.get(i).getArgent()>0){
            System.out.println(joueurs.get(i).getNomJ() + " est " + (i + 1) + " place avec " + joueurs.get(i).getArgent() +".");
            joueurs.get(i).afficherLesProprietes();
        }
            else {
                System.out.println(joueurs.get(i).getNomJ() + " est " + (i + 1) + " place avec 0 argent.");
                joueurs.get(i).afficherLesProprietes();
            }
        }
    }

    private void trie() {  // non demande mais il me parait logique d afficher dans ordre
        Collections.sort(joueurs);
        Collections.sort(joueurs, Collections.reverseOrder());
    }
}