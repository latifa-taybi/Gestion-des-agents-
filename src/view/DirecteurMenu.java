package view;

import controller.DirecteurController;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import service.IAgentService;

import java.util.Scanner;

public class DirecteurMenu {

    private final DirecteurController controller;
    private final IAgentService agentService;
    private final Scanner scanner = new Scanner(System.in);

    public DirecteurMenu(DirecteurController controller, IAgentService agentService) {
        this.controller = controller;
        this.agentService = agentService;
    }

    public void afficherMenu() {
        int choix;
        do {
            System.out.println("-----------------------MENU DIRECTEUR-----------------------");
            System.out.println("1.lister les departements ");
            System.out.println("2.consulter la liste des agents par departement");
            System.out.println("3.ajouter un departement");
            System.out.println("4.modifier un departement");
            System.out.println("5.supprimer un departement");
            System.out.println("6.affecter un responsable a un departement");
            System.out.println("7.consulter le responsable d un departement");
            System.out.println("8.consulter les paiements d un departement");
            System.out.println("9.nombre de primes _ indemnite _ salaire_ bonus");
            System.out.println("10.total des paiementspar departement");
            System.out.println("11.nombres total d agents ");
            System.out.println("12.nombres total des dpartements");

            System.out.println("0.Quitter");
            System.out.print("choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 :
                    listerDepartements();
                    break;
                case 2 :
                    consulterAgentsByDepartement();
                    break;
                case 3 :
                    ajouterDepartement();
                    break;
                case 4 :
                    modifierDepartement();
                    break;
                case 5 :
                    supprimerDepartement();
                    break;
                case 6 :
                    affecterResponsable();
                    break;
                case 7 :
                    consulterResponsable();
                    break;
                case 8 :
                    consulterPaiementsDeDepartements();
                    break;
                case 9 :
                    nombresPaiementParType();
                    break;
                case 10 :
                    nbTotalAgents();
                    break;
                case 11 :
                    nbTotalDepartements();
                    break;
                case 12 :
                    calculerTotalPaiementByDepartement();
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

    private void consulterAgentsByDepartement(){
        controller.consulterAgentsByDepartement();
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

        Agent agent = agentService.getAgentById(idAgent);
        if(agent.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT){
            controller.affecterResponsable(idDep, agent);
        }else{
            System.out.println("l'agent n'est pas un responsable");
        };
    }

    private void consulterResponsable() {
        System.out.print("entrer l id du departement: ");
        int idDep = scanner.nextInt();
        controller.consulterResponsable(idDep);
    }

    private void consulterPaiementsDeDepartements(){
        System.out.println("entrer l'id d un departement");
        int idDep = scanner.nextInt();
        controller.getPaiementsByDepartement(idDep);
    }

    private void calculerTotalPaiementByDepartement(){
        System.out.println("entrer l'id de departement");
        int idDep = scanner.nextInt();
        controller.totalPaiementsByDepartement(idDep);

    }

    public void nombresPaiementParType(){
        System.out.println("entrer l'id de l agent");
        int idAgent = scanner.nextInt();
        controller.nombresPaiementParType(idAgent);
    }

    public void nbTotalAgents(){
        controller.getNombreTotalAgents();
    }


    public void nbTotalDepartements(){
        controller.getNombreTotalDep();
    }

}
