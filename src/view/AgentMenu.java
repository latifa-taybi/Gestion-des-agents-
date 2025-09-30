package view;

import controller.AgentController;
import java.util.Scanner;

public class AgentMenu {

    private final AgentController controller;
    private final int idAgent;
    private final Scanner scanner = new Scanner(System.in);

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
            System.out.println("0.Quitter");
            System.out.print("choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 :
                    voirInformations();
                    break;
                case 2 :
                    voirHistoriquePaiements();
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
}
