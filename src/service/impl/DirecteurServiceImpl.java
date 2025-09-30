package service.impl;

import dao.IDepartementDao;
import dao.IAgentDao;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import service.IDirecteurService;

import java.sql.SQLException;
import java.util.List;

public class DirecteurServiceImpl implements IDirecteurService {

    private final IDepartementDao departementDao;
    private final IAgentDao agentDao;

    public DirecteurServiceImpl(IDepartementDao departementDao, IAgentDao agentDao) {
        this.departementDao = departementDao;
        this.agentDao = agentDao;
    }

    @Override
    public Departement ajouterDepartement(Departement departement) throws SQLException {
        if (departement.getNom() == null || departement.getNom().isBlank()) {
            throw new SQLException("le nom de departement est obligatoire");
        }
        return departementDao.insert(departement);
    }

    @Override
    public Departement modifierDepartement(Departement departement) throws SQLException {
        return departementDao.update(departement);
    }

    @Override
    public void supprimerDepartement(Departement departement) throws SQLException {
        departementDao.delete(departement);
    }

    @Override
    public List<Departement> getAllDepartements() throws SQLException {
        return departementDao.getAll();
    }

    @Override
    public Departement getDepartementById(int idDepartement) throws SQLException {
        return departementDao.get(idDepartement);
    }

    @Override
    public Agent getResponsableDepartement(int idDepartement) throws SQLException {
        List<Agent> agents = agentDao.getAll();

        return agents.stream()
                .filter(a -> a.getDepartement().getIdDepartement() == idDepartement)
                .filter(a -> a.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void affecterResponsable(int idDepartement, Agent responsable) throws SQLException {
        Departement departement = departementDao.get(idDepartement);
        if (departement == null) {
            throw new SQLException("le departement n existe pas");
        }

        Agent agent = agentDao.get(responsable.getIdAgent());
        if (agent == null) {
            throw new SQLException("l agent n existe pas");
        }

        agent.setTypeAgent(TypeAgent.RESPONSABLE_DEPARTEMENT);
        agent.setDepartement(departement);
        agentDao.update(agent);
    }
}
