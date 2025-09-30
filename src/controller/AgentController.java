package controller;

import model.Agent;
import model.Paiement;
import service.IAgentService;

import java.sql.SQLException;
import java.util.List;

public class AgentController {

    private final IAgentService agentService;

    public AgentController(IAgentService agentService) {
        this.agentService = agentService;
    }

    public void consulterInfos(int idAgent) {
        try {
            Agent agent = agentService.getAgentById(idAgent);
            System.out.println(agent);
        } catch (SQLException e) {
            System.err.println("erreu: " + e.getMessage());
        }
    }

    public void afficherHistoriquePaiements(int idAgent) {
        try {
            List<Paiement> paiements = agentService.getHistoriquePaiements(idAgent);
            if (paiements.isEmpty()) {
                System.out.println("il n existe aucune paiement");
            } else {
                paiements.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }

    public void afficherTotalPaiements(int idAgent) {
        try {
            double total = agentService.calculerTotalPaiements(idAgent);
            System.out.println("total des paiements :  " + total );
        } catch (SQLException e) {
            System.err.println("erreur: " + e.getMessage());
        }
    }
}
