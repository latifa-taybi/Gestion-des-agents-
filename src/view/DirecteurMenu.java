package view;

import controller.DirecteurController;
import model.Agent;
import model.Departement;

import java.util.Scanner;

public class DirecteurMenu {

    private final DirecteurController controller;
    private final Scanner scanner = new Scanner(System.in);

    public DirecteurMenu(DirecteurController controller) {
        this.controller = controller;
    }

    public void afficherMenu() {
        int choix;
        do {
            System.out.println("-----------------------MENU DIRECTEUR-----------------------");
            System.out.println("1.lister les departements");
            System.out.println("2.ajouter un departement");
            System.out.println("3.modifier un departement");
            System.out.println("4.supprimer un departement");
            System.out.println("5.affecter un responsable a un departement");
            System.out.println("6.consulter le responsable d un departement");
            System.out.println("0.Quitter");
            System.out.print("choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 :
                    listerDepartements();
                    break;
                case 2 :
                    ajouterDepartement();
                    break;
                case 3 :
                    modifierDepartement();
                    break;
                case 4 :
                    supprimerDepartement();
                    break;
                case 5 :
                    affecterResponsable();
                    break;
                case 6 :
                    consulterResponsable();
                    break;
                case 0 :
                    System.out.println("retour au menu precedent");
                    break;
                default :
                    System.out.println("choix non valide");
            }
        } while (choix != 0);
    }

    private void listerDepartements() {
        controller.listerDepartements();
    }

    private void ajouterDepartement() {
        System.out.print("entrer le nom du departement: ");
        String nom = scanner.nextLine();
        Departement departement = new Departement(0, nom);
        controller.creerDepartement(departement);
    }

    private void modifierDepartement() {
        System.out.print("entrer l id du departement a modifier: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("entrer le nouveau nom: ");
        String nom = scanner.nextLine();
        Departement departement = new Departement(id, nom);
        controller.modifierDepartement(departement);
    }

    private void supprimerDepartement() {
        System.out.print("entrer l id du departement a supprimer: ");
        int id = scanner.nextInt();
        Departement departement = new Departement(id, null);
        controller.supprimerDepartement(departement);
    }

    private void affecterResponsable() {
        System.out.print("entrer l id du departement: ");
        int idDep = scanner.nextInt();
        System.out.print("entrer l id de l agent a affecter comme responsable: ");
        int idAgent = scanner.nextInt();

        Agent responsable = new Agent(idAgent, null, null, null, null, null);
        controller.affecterResponsable(idDep, responsable);
    }

    private void consulterResponsable() {
        System.out.print("entrer l id du departement: ");
        int idDep = scanner.nextInt();
        controller.consulterResponsable(idDep);
    }
}
