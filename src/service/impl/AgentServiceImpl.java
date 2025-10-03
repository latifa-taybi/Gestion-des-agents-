package service.impl;


import dao.IAgentDao;
import dao.IPaiementDao;
import model.Agent;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import service.IAgentService;

import java.sql.SQLException;
import java.util.List;

public class AgentServiceImpl implements IAgentService {
    private final IAgentDao agentDao;

    public AgentServiceImpl(IAgentDao agentDao) {
        this.agentDao = agentDao;
    }

    @Override
    public Agent getAgentById(int id) throws AgentIntrouvableException, DatabaseException {
        try{
            Agent agent = agentDao.get(id);
            if (agent == null) {
                throw new AgentIntrouvableException("agent introuvable");
            }
            return agent;
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la recuperation de l'agent");
        }
    }

    @Override
    public List<Agent> getAllAgents() throws DatabaseException {
        try {
            return agentDao.getAll();
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la recuperation des agents");
        }
    }

    @Override
    public Agent addAgent(Agent agent) throws DatabaseException {
        try {
            return agentDao.insert(agent);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de l'ajout de l'agent");
        }
    }

    @Override
    public Agent updateAgent(Agent agent) throws DatabaseException {
        try {
            return agentDao.update(agent);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la modification de l'agent");
        }
    }

    @Override
    public void deleteAgent(Agent agent) throws DatabaseException {
        try {
            agentDao.delete(agent);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la suppression de l'agent");
        }
    }

    @Override
    public Agent authentifier(String email, String motDePasse) throws DatabaseException {
        try {
            return agentDao.findByEmailAndPassword(email, motDePasse);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la recuperation d un agent");
        }
    }
}

