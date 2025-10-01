package service.impl;

import dao.IDepartementDao;
import dao.IAgentDao;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
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
    public Departement ajouterDepartement(Departement departement) throws DatabaseException {
        try{
            if (departement.getNom() == null || departement.getNom().isBlank()) {
                throw new DatabaseException("le nom de departement est obligatoire");
            }
            return departementDao.insert(departement);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de l insertion d'un departement");
        }

    }

    @Override
    public Departement modifierDepartement(Departement departement) throws DatabaseException, DepartementIntrouvableException {
        try{
            return departementDao.update(departement);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la modification de departement");
        }

    }

    @Override
    public void supprimerDepartement(Departement departement) throws DatabaseException, DepartementIntrouvableException {
        try{
            departementDao.delete(departement);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la suppression de departement");
        }
    }

    @Override
    public List<Departement> getAllDepartements() throws DatabaseException {
        try{
            return departementDao.getAll();
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la recuperation des departements");
        }
    }

    @Override
    public Departement getDepartementById(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        try{
            return departementDao.get(idDepartement);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la recuperation de departement");
        }
    }

    @Override
    public Agent getResponsableDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        try{
            List<Agent> agents = agentDao.getAll();

            return agents.stream()
                    .filter(a -> a.getDepartement().getIdDepartement() == idDepartement)
                    .filter(a -> a.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT)
                    .findFirst()
                    .orElse(null);
        }catch (SQLException e){
            throw new DatabaseException("erreur lors de la re de departement");
        }
    }

    @Override
    public void affecterResponsable(int idDepartement, Agent responsable) throws DatabaseException {
        try {
            Departement departement = departementDao.get(idDepartement);
            if (departement == null) {
                throw new DatabaseException("le departement n existe pas");
            }
            Agent agent = agentDao.get(responsable.getIdAgent());
            if (agent == null) {
                throw new DatabaseException("l agent n existe pas");
            }
            agent.setTypeAgent(TypeAgent.RESPONSABLE_DEPARTEMENT);
            agent.setDepartement(departement);
            agentDao.update(agent);
        }catch (SQLException e){
            throw new DatabaseException("erreur de l affectation d un responsable au departement");
        }
    }
}
