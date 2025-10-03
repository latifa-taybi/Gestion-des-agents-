package view;

import controller.AgentController;
import java.util.Scanner;

public class AgentMenu {

    private final AgentController controller;
    private final int idAgent;
    private static final Scanner scanner = new Scanner(System.in);

    public AgentMenu(AgentController controller, int idAgent) {
        this.controller = controller;
        this.idAgent = idAgent;
    }

    public void afficherMenu() {
        int choix;
        do {
            System.out.println("-----------------------MENU AGENT-----------------------");
            System.out.println("1.consulter mes informations");
            System.out.println("2.consulter mon historique de paiements");
            System.out.println("3.calculer le total de mes paiements");
//            System.out.println("3.calculer le salaire annuel total");
//            System.out.println("3.affichage des paiments les plus eleves");
//            System.out.println("3.afficher tous les statistiques");

            System.out.println("0.Quitter");
            System.out.print("choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 :
                    voirInformations();
                    break;
                case 2 :
                    menuPaiementsAgent();
                    break;
                case 3 :
                    calculerTotalPaiements();
                    break;
                case 0 :
                    System.out.println("retour au menu precedent");
                    break;
                default :
                    System.out.println("choix non valide");
            }
        } while (choix != 0);
    }

    private void voirInformations() {
        controller.consulterInfos(idAgent);
    }

    private void voirHistoriquePaiements() {
        controller.afficherHistoriquePaiements(idAgent);
    }

    private void calculerTotalPaiements() {
        controller.afficherTotalPaiements(idAgent);
    }

    public void menuPaiementsAgent() {
        boolean quitter = false;
        while (!quitter) {
            System.out.println("------------- HISTORIQUE DE MES PAIEMENTS --------------");
            System.out.println("1.afficher tous les paiements");
            System.out.println("2.filtrer par type de paiement");
            System.out.println("3.filtrer par montant");
            System.out.println("4.filtrer par date");
            System.out.println("0.quitter");
            System.out.print("saisir votre choix: ");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1 ->  voirHistoriquePaiements();
                case 2 -> System.out.println("[Paiements filtrés par type]");
                case 3 -> System.out.println("[Paiements filtrés par montant]");
                case 4 -> System.out.println("[Paiements filtrés par date]");
                case 0 -> quitter = true;
                default -> System.out.println("Choix invalide !");
            }
        }
    }
}
