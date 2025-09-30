package service.impl;

import dao.IAgentDao;
import dao.IPaiementDao;
import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
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
    public Agent ajouterAgent(Agent agent, Departement departement) throws SQLException {
        agent.setDepartement(departement);
        return agentDao.insert(agent);
    }

    @Override
    public Agent modifierAgent(Agent agent) throws SQLException {
        return agentDao.update(agent);
    }

    @Override
    public void supprimerAgent(Agent agent) throws SQLException {
        agentDao.delete(agent);
    }

    @Override
    public List<Agent> getAgentsByDepartement(int idDepartement) throws SQLException {
        return agentDao.getAll()
                .stream()
                .filter( a->a.getDepartement().getIdDepartement() == idDepartement)
                .collect(Collectors.toList());
    }


    @Override
    public Paiement ajouterPaiement(Paiement paiement) throws SQLException {
        Agent agent = agentDao.get(paiement.getAgent().getIdAgent());
        if (agent == null) {
            throw new SQLException("la gent n existe pas");
        }

        if ((paiement.getTypePaiement() == TypePaiement.BONUS || paiement.getTypePaiement() == TypePaiement.INDEMNITE)) {
            if (!(agent.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT) || !(agent.getTypeAgent() == TypeAgent.DIRECTEUR)) {
                throw new SQLException("l agent n est pas eligible");
            }
            if (!paiement.isConditionValidee()) {
                throw new SQLException("condition invalide");
            }
        }

        if (paiement.getMontant() < 0) {
            throw new SQLException("le montant est negatif");
        }

        return paiementDao.insert(paiement);
    }

    @Override
    public Paiement modifierPaiement(Paiement paiement) throws SQLException {
        return paiementDao.update(paiement);
    }

    @Override
    public void supprimerPaiement(Paiement paiement) throws SQLException {
        paiementDao.delete(paiement);
    }

    @Override
    public List<Paiement> getPaiementsAgentsDepartement(int idDepartement) throws SQLException {
        return paiementDao.getAll()
                .stream()
                .filter(p -> p.getAgent().getDepartement().getIdDepartement() == idDepartement)
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> filtrerPaiementsDepartement(int idDepartement, Predicate<Paiement> critere) throws SQLException {
        return getPaiementsAgentsDepartement(idDepartement)
                .stream()
                .filter(critere)
                .collect(Collectors.toList());
    }

    @Override
    public double calculerTotalPaiementsDepartement(int idDepartement) throws SQLException {
        return getPaiementsAgentsDepartement(idDepartement)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    @Override
    public double calculerMoyennePaiementsDepartement(int idDepartement) throws SQLException {
        List<Paiement> paiements = getPaiementsAgentsDepartement(idDepartement);
        return paiements.isEmpty() ? 0 :
                paiements.stream()
                        .mapToDouble(Paiement::getMontant)
                        .average()
                        .orElse(0);
    }
}
