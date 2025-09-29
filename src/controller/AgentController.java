package controller;

import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import service.impl.AgentServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class AgentController {

    private AgentServiceImpl agentServiceImpl = new AgentServiceImpl();

    // Exemple méthode pour récupérer un agent
    public Agent getAgentById(int id) {
        try {
            return agentServiceImpl.getAgent(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Exemple méthode pour afficher tous les agents
    public List<Agent> getAllAgents() {
        try {
            return agentServiceImpl.getAllAgents();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Exemple méthode pour créer un agent
    public void createAgent(String nom, String prenom, String email, String motDePasse,
                            TypeAgent typeAgent, Departement departement) {
        Agent agent = new Agent(0, nom, prenom, email, motDePasse, typeAgent);
        agent.setDepartement(departement);
        try {
            agentServiceImpl.createAgent(agent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Exemple méthode pour mettre à jour un agent
    public void updateAgent(Agent agent) {
        try {
            agentServiceImpl.updateAgent(agent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Exemple méthode pour supprimer un agent
    public void deleteAgent(int id) {
        try {
            agentServiceImpl.deleteAgent(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
