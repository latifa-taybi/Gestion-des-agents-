package controller;

import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypePaiement;
import model.exceptions.DatabaseException;
import service.IPaiementService;
import service.IResponsableService;

import java.util.List;
import java.util.function.Predicate;

public class ResponsableController {

    private final IResponsableService responsableService;
    private final IPaiementService paiementService;

    public ResponsableController(IResponsableService responsableService, IPaiementService paiementService) {
        this.responsableService = responsableService;
        this.paiementService = paiementService;
    }

    public void ajouterAgent(Agent agent, Departement departement) {
        try {
            responsableService.ajouterAgent(agent, departement);
        } catch (DatabaseException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void modifierAgent(Agent agent) {
        try {
            responsableService.modifierAgent(agent);
            System.out.println("agent modifie avec succes");
        } catch (DatabaseException e) {
            System.err.println("erreur " + e.getMessage());
        }
    }

    public void supprimerAgent(Agent agent) {
        try {
            responsableService.supprimerAgent(agent);
            System.out.println("agent supprime avec succes");
        } catch (DatabaseException e) {
            System.err.println("erreur " + e.getMessage());
        }
    }

    public void listerAgents(int idDepartement) {
        try {
            List<Agent> agents = responsableService.getAgentsByDepartement(idDepartement);
            if (agents.isEmpty()) {
                System.out.println("aucune agent trouve dans ce departement");
            } else {
                agents.forEach(System.out::println);
            }
        } catch (DatabaseException e) {
            System.err.println("erreur " + e.getMessage());
        }
    }


    public void ajouterPaiement(Paiement paiement) {
        try {

            paiementService.ajouterPaiement(paiement);
            System.out.println("paiement ajoute avec succes");
        } catch (DatabaseException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void modifierPaiement(Paiement paiement) {
        try {
            paiementService.modifierPaiement(paiement);
            System.out.println("paiement modifie avec succes");
        } catch (DatabaseException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void supprimerPaiement(Paiement paiement) {
        try {
            paiementService.supprimerPaiement(paiement);
            System.out.println("paiement supprime avec succes");
        } catch (DatabaseException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void listerPaiementsDepartement(int idDepartement) {
        try {
            List<Paiement> paiements = paiementService.getPaiementsByDepartement(idDepartement);
            if (paiements.isEmpty()) {
                System.out.println("pas de paiement pour ce departement");
            } else {
                paiements.forEach(System.out::println);
            }
        } catch (DatabaseException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void filtrerPaiementsParType(int idAgent, TypePaiement type, Predicate<Paiement> critere) {
        try {
            List<Paiement> paiements = paiementService.filtrerPaiementsParType(idAgent,type,critere);
            paiements.forEach(System.out::println);
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void afficherStatistiquesDepartement(int idDepartement) {
        try {
            double total = paiementService.calculerTotalPaiementsDepartement(idDepartement);
            double moyenne = paiementService.calculerMoyennePaiementsDepartement(idDepartement);
            System.out.println("statistique su departement: " + idDepartement);
            System.out.println("total des paiements est: " + total );
            System.out.println("moyenne des paiements : " + moyenne);
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }
}
