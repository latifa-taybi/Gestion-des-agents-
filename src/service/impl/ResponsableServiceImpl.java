package service.impl;

import dao.IAgentDao;
import dao.IPaiementDao;
import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
import model.exceptions.PaiementIntrouvableException;
import service.IResponsableService;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResponsableServiceImpl implements IResponsableService {
    private final IAgentDao agentDao;
    private final IPaiementDao paiementDao;

    public ResponsableServiceImpl(IAgentDao agentDao, IPaiementDao paiementDao) {
        this.agentDao = agentDao;
        this.paiementDao = paiementDao;
    }

    @Override
    public Agent ajouterAgent(Agent agent, Departement departement) throws DepartementIntrouvableException,DatabaseException {
        try {
            agent.setDepartement(departement);
            return agentDao.insert(agent);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans l'insertion d'agent");
        }
    }

    @Override
    public Agent modifierAgent(Agent agent) throws DatabaseException, AgentIntrouvableException {
        try {
            return agentDao.update(agent);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la modification d'agent");
        }
    }

    @Override
    public void supprimerAgent(Agent agent) throws DatabaseException, AgentIntrouvableException {
        try {
            agentDao.delete(agent);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la suppression d'agent");
        }
    }

    @Override
    public List<Agent> getAgentsByDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        try {
            return agentDao.getAll()
                    .stream()
                    .filter( a->a.getDepartement().getIdDepartement() == idDepartement)
                    .collect(Collectors.toList());
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la recuperation d'agent");
        }

    }

    @Override
    public Paiement ajouterPaiement(Paiement paiement) throws DatabaseException, AgentIntrouvableException {
        try {
            Agent agent = agentDao.get(paiement.getAgent().getIdAgent());
            if (agent == null) {
                throw new DatabaseException("l agent n existe pas");
            }
            if ((paiement.getTypePaiement() == TypePaiement.BONUS || paiement.getTypePaiement() == TypePaiement.INDEMNITE)) {
                if (!(agent.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT) || !(agent.getTypeAgent() == TypeAgent.DIRECTEUR)) {
                    throw new DatabaseException("l agent n est pas eligible");
                }
                if (!paiement.isConditionValidee()) {
                    throw new DatabaseException("condition invalide");
                }
            }
            if (paiement.getMontant() < 0) {
                throw new DatabaseException("le montant est negatif");
            }

            return paiementDao.insert(paiement);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans l'insertion de paiement");
        }
    }

    @Override
    public Paiement modifierPaiement(Paiement paiement) throws DatabaseException, PaiementIntrouvableException {
        try {
            return paiementDao.update(paiement);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la modification de paiement");
        }
    }

    @Override
    public void supprimerPaiement(Paiement paiement) throws DatabaseException, PaiementIntrouvableException {
        try {
            paiementDao.delete(paiement);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la suppression de paiement");
        }
    }

    @Override
    public List<Paiement> getPaiementsAgentsDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        try {
            return paiementDao.getAll()
                    .stream()
                    .filter(p -> p.getAgent().getDepartement().getIdDepartement() == idDepartement)
                    .collect(Collectors.toList());
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la recuperation des paiements par departement");
        }

    }

    @Override
    public List<Paiement> filtrerPaiementsDepartement(int idDepartement, Predicate<Paiement> critere) throws DatabaseException, DepartementIntrouvableException {
        return getPaiementsAgentsDepartement(idDepartement)
                .stream()
                .filter(critere)
                .collect(Collectors.toList());
    }

    @Override
    public double calculerTotalPaiementsDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        return getPaiementsAgentsDepartement(idDepartement)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    @Override
    public double calculerMoyennePaiementsDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        List<Paiement> paiements = getPaiementsAgentsDepartement(idDepartement);
        return paiements.isEmpty() ? 0 :
                paiements.stream()
                        .mapToDouble(Paiement::getMontant)
                        .average()
                        .orElse(0);
    }


}
