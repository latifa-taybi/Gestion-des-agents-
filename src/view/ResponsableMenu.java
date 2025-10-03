package view;

import controller.ResponsableController;
import dao.IAgentDao;
import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import model.exceptions.AgentHorsDepartementException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class ResponsableMenu {

    private final ResponsableController responsableController;
    private final int idDep;
    private final IAgentDao agentDao;
    private final Scanner scanner = new Scanner(System.in);

    public ResponsableMenu(ResponsableController responsableController, int idDep, IAgentDao agentDao) {
        this.responsableController = responsableController;
        this.idDep = idDep;
        this.agentDao = agentDao;
    }

    public void afficherMenu() throws SQLException {
        int choix;
        do {
            System.out.println("-----------------------MENU RESPONSABLE-----------------------");
            System.out.println("1.lister les agents du departement");
            System.out.println("2.ajouter l agent");
            System.out.println("3.modifier l agent");
            System.out.println("4.supprimer l agent");
            System.out.println("5.lister les paiements");
            System.out.println("6.ajouter un paiement");
            System.out.println("7.modifier un paiement");
            System.out.println("8.supprimer un paiement");
            System.out.println("9.statistiques du departement");
            System.out.println("0.Quitter");
            System.out.print("choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 :
                    listerAgents();
                    break;
                case 2 :
                    ajouterAgent();
                    break;
                case 3 :
                    modifierAgent();
                    break;
                case 4 :
                    supprimerAgent();
                    break;
                case 5 :
                    listerPaiements();
                    break;
                case 6 :
                    ajouterPaiement();
                    break;
                case 7 :
                    modifierPaiement();
                    break;
                case 8 :
                    supprimerPaiement();
                    break;
                case 9 :
                    afficherStatistiques();
                    break;
                case 0 :
                    System.out.println("retour au menu precedant");
                    break;
                default :
                    System.out.println("choin non valide");
            }
        } while (choix != 0);
    }

    private void listerAgents() {
        responsableController.listerAgents(idDep);
    }

    private void ajouterAgent() {
        System.out.print("entrer le nom: ");
        String nom = scanner.nextLine();
        System.out.print("entrer le prenom: ");
        String prenom = scanner.nextLine();
        System.out.print("entrer l email");
        String email = scanner.nextLine();
        System.out.print("entrer le mot de passe: ");
        String mdp = scanner.nextLine();
        System.out.print("entrer le type de l agent (OUVRIER - STAGIAIRE) ");
        TypeAgent type = TypeAgent.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("saisir l id de departement: ");
        int idDep = scanner.nextInt();

        Departement departement = new Departement(idDep, null);
        Agent agent = new Agent(0, nom, prenom, email, mdp, type);
        responsableController.ajouterAgent(agent, departement);
    }

    private void modifierAgent() {
        System.out.print("l id de l agent a modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("entrer le nouveau nom: ");
        String nom = scanner.nextLine();
        System.out.print("entrer le nouveau prenom: ");
        String prenom = scanner.nextLine();
        System.out.print("entrer le nouveau email: ");
        String email = scanner.nextLine();
        System.out.print("entrer le nouveau mot de passe : ");
        String mdp = scanner.nextLine();
        System.out.print("entrer le nouveau type ");
        TypeAgent type = TypeAgent.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("entrer l id du nouveau departement de l'agent");
        int idDep = scanner.nextInt();

        Departement departement = new Departement(idDep, null);
        Agent agent = new Agent(id, nom, prenom, email, mdp, type);
        agent.setDepartement(departement);
        responsableController.modifierAgent(agent);
    }

    private void supprimerAgent() {
        System.out.print("saisir l id de l agent a supprimer: ");
        int id = scanner.nextInt();
        Agent agent = new Agent(id, null, null, null, null, TypeAgent.OUVRIER);
        responsableController.supprimerAgent(agent);
    }

    private void listerPaiements() {
        responsableController.listerPaiementsDepartement(idDep);
    }

    private void ajouterPaiement() throws SQLException {
        try{
            System.out.print("entrer l id de l agent: ");
            int idAgent = scanner.nextInt();
            if(agentDao.get(idAgent).getDepartement().getIdDepartement() != idDep){
                throw new AgentHorsDepartementException("l'agent n'appartient as a ce departement");
            }else {
                scanner.nextLine();
                System.out.print("entrer le type de paiement (PRIME - SALAIRE): ");

                TypePaiement type = TypePaiement.valueOf(scanner.nextLine().toUpperCase());
                System.out.print("entrer le montant: ");
                double montant = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("entrer le motif: ");
                String motif = scanner.nextLine();

                Paiement paiement = new Paiement(0, type, montant, LocalDate.now(), motif, true, new Agent(idAgent, null, null, null, null, TypeAgent.OUVRIER));
                responsableController.ajouterPaiement(paiement);
            }
        }catch (SQLException e){
            throw new SQLException("erreur lors de la recuperation de l agent");
        }

    }

    private void modifierPaiement() {
        System.out.print("entrer l id de paiement: ");
        int idPaiement = scanner.nextInt();
        System.out.print("entrer le nouveau montant : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("entrer le nouveau motif : ");
        String motif = scanner.nextLine();

        Paiement paiement = new Paiement(idPaiement, TypePaiement.SALAIRE, montant, LocalDate.now(), motif, true, null);
        responsableController.modifierPaiement(paiement);
    }

    private void supprimerPaiement() {
        System.out.print("saisir l id de paiement a supprimer : ");
        int id = scanner.nextInt();
        Paiement paiement = new Paiement(id, null, 0, null, null, false, null);
        responsableController.supprimerPaiement(paiement);
    }

    private void afficherStatistiques() {
        System.out.print("saisir l id de departement : ");
        int idDep = scanner.nextInt();
        responsableController.afficherStatistiquesDepartement(idDep);
    }
}
