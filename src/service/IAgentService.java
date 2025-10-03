package service;

import model.Agent;
import model.Paiement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public interface IAgentService {
    Agent getAgentById(int id) throws AgentIntrouvableException, DatabaseException;
    List<Agent> getAllAgents() throws DatabaseException;
    Agent addAgent(Agent agent) throws DatabaseException;
    Agent updateAgent(Agent agent) throws DatabaseException;
    void deleteAgent(Agent agent) throws DatabaseException;

    Agent authentifier(String email, String motDePasse) throws SQLException;


}