package service.impl;


import dao.IAgentDao;
import dao.IPaiementDao;
import model.Agent;
import model.Paiement;
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
    public Agent getAgentById(int id) throws SQLException {
        Agent agent = agentDao.get(id);
        if (agent == null) {
            throw new SQLException("agent introuvable");
        }
        return agent;
    }

    @Override
    public List<Agent> getAllAgents() throws SQLException {
        return agentDao.getAll();
    }

    @Override
    public Agent addAgent(Agent agent) throws SQLException {
        return agentDao.insert(agent);
    }

    @Override
    public Agent updateAgent(Agent agent) throws SQLException {
        return agentDao.update(agent);
    }

    @Override
    public void deleteAgent(Agent agent) throws SQLException {
        agentDao.delete(agent);
    }

    @Override
    public List<Paiement> getHistoriquePaiements(int idAgent) throws SQLException {
        return paiementDao.getAll()
                .stream()
                .filter(p -> p.getAgent().getIdAgent() == idAgent)
                .collect(Collectors.toList());
    }

    @Override
    public double calculerTotalPaiements(int idAgent) throws SQLException {
        return getHistoriquePaiements(idAgent)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    @Override
    public List<Paiement> filtrerPaiements(int idAgent, Predicate<Paiement> critere) throws SQLException {
        return getHistoriquePaiements(idAgent)
                .stream()
                .filter(critere)
                .collect(Collectors.toList());
    }

}

