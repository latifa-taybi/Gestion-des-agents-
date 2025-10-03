package controller;

import model.Agent;
import model.Paiement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import service.IAgentService;
import service.IPaiementService;

import java.sql.SQLException;
import java.util.List;

public class AgentController {

    private final IAgentService agentService;
    private final IPaiementService paiementService;

    public AgentController(IAgentService agentService, IPaiementService paiementService) {
        this.agentService = agentService;
        this.paiementService = paiementService;
    }

    public void consulterInfos(int idAgent) {
        try {
            Agent agent = agentService.getAgentById(idAgent);
            System.out.println(agent);
        } catch (AgentIntrouvableException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void afficherHistoriquePaiements(int idAgent) {
        try {
            List<Paiement> paiements = paiementService.getPaiementsByAgent(idAgent);
            if (paiements.isEmpty()) {
                System.out.println("il n existe aucune paiement");
            } else {
                paiements.forEach(System.out::println);
            }
        } catch (DatabaseException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void afficherTotalPaiements(int idAgent) {
        try {
            double total = paiementService.calculerTotalPaiements(idAgent);
            System.out.println("total des paiements :  " + total );
        } catch (DatabaseException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public Agent authentifier(String email, String motDePasse) throws DatabaseException{
        try {
            return agentService.authentifier(email,motDePasse);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " );
            return null;
        }
    }
}
