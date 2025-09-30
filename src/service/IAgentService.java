package service;

import model.Agent;
import model.Paiement;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public interface IAgentService {
    Agent getAgentById(int id) throws SQLException;
    List<Agent> getAllAgents() throws SQLException;
    Agent addAgent(Agent agent) throws SQLException;
    Agent updateAgent(Agent agent) throws SQLException;
    void deleteAgent(Agent agent) throws SQLException;

    List<Paiement> getHistoriquePaiements(int idAgent) throws SQLException;
    double calculerTotalPaiements(int idAgent) throws SQLException;
    List<Paiement> filtrerPaiements(int idAgent, Predicate<Paiement> critere) throws SQLException;

}