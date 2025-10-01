package service.impl;


import dao.IAgentDao;
import dao.IPaiementDao;
import model.Agent;
import model.Paiement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import service.IAgentService;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AgentServiceImpl implements IAgentService {
    private final IAgentDao agentDao;
    private final IPaiementDao paiementDao;

    public AgentServiceImpl(IAgentDao agentDao, IPaiementDao paiementDao) {
        this.agentDao = agentDao;
        this.paiementDao = paiementDao;
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
    public List<Paiement> getHistoriquePaiements(int idAgent) throws DatabaseException {
        try {
            return paiementDao.getAll()
                    .stream()
                    .filter(p -> p.getAgent().getIdAgent() == idAgent)
                    .collect(Collectors.toList());
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la recuperation de l'historiaue de paiements");
        }
    }

    @Override
    public double calculerTotalPaiements(int idAgent) throws DatabaseException {
        return getHistoriquePaiements(idAgent)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    @Override
    public List<Paiement> filtrerPaiements(int idAgent, Predicate<Paiement> critere) throws DatabaseException {
        return getHistoriquePaiements(idAgent)
                .stream()
                .filter(critere)
                .collect(Collectors.toList());
    }

}

