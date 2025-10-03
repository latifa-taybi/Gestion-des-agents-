package service.impl;

import dao.IAgentDao;
import dao.IPaiementDao;
import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
import model.exceptions.PaiementIntrouvableException;
import service.IPaiementService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PaiementServiceImpl implements IPaiementService {
    private final IAgentDao agentDao;
    private final IPaiementDao paiementDao;

    public PaiementServiceImpl(IAgentDao agentDao, IPaiementDao paiementDao) {
        this.agentDao = agentDao;
        this.paiementDao = paiementDao;
    }

    @Override
    public Paiement ajouterPaiement(Paiement paiement) throws DatabaseException, AgentIntrouvableException {
        try {
            Agent agent = agentDao.get(paiement.getAgent().getIdAgent());
            if (agent == null) {
                throw new DatabaseException("l agent n existe pas");
            }
            if ((paiement.getTypePaiement() == TypePaiement.BONUS || paiement.getTypePaiement() == TypePaiement.INDEMNITE)) {
                if (!(agent.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT || agent.getTypeAgent() == TypeAgent.DIRECTEUR)) {
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
    public boolean supprimerPaiement(Paiement paiement) throws DatabaseException, PaiementIntrouvableException {
        try {
            return paiementDao.delete(paiement);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la suppression de paiement");
        }
    }

    @Override
    public List<Paiement> getPaiementsByAgent(int idAgent) throws DatabaseException {
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
    public List<Paiement> getPaiementsByDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        try {
            return paiementDao.getAll()
                    .stream()
                    .filter(p->p.getAgent().getDepartement().getIdDepartement() == idDepartement)
                    .collect(Collectors.toList());
        }catch (SQLException e){
            throw new DatabaseException("erreur dans la recuperation des paiement");
        }
    }



    @Override
    public double calculerTotalPaiements(int idAgent) throws DatabaseException {
        return getPaiementsByAgent(idAgent)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    @Override
    public List<Paiement> filtrerPaiementsParType(int idAgent, TypePaiement type, Predicate<Paiement> critere) throws DatabaseException {
        return getPaiementsByAgent(idAgent)
                .stream()
                .filter(p->p.getTypePaiement() == type)
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> filtrerPaiementsParMontant(int idAgent, double montant, Predicate<Paiement> critere) throws DatabaseException {
        return getPaiementsByAgent(idAgent)
                .stream()
                .filter(p->p.getMontant() == montant)
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> filtrerPaiementsParDate(int idAgent, LocalDate date, Predicate<Paiement> critere) throws DatabaseException {
        return getPaiementsByAgent(idAgent)
                .stream()
                .filter(p->p.getDate().isEqual(date))
                .collect(Collectors.toList());
    }


    @Override
    public double calculerTotalPaiementsDepartement(int idDepartement) throws DatabaseException {
        return getPaiementsByDepartement(idDepartement)
                .stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    public double calculerMoyennePaiementsDepartement(int idDepartement) throws DatabaseException {
        List<Paiement> paiements = getPaiementsByDepartement(idDepartement);
        return paiements.isEmpty() ? 0 :
                paiements.stream()
                        .mapToDouble(Paiement::getMontant)
                        .average()
                        .orElse(0);
    }


    public void nombresPaiementParType(int idAgent) throws DatabaseException {
        double nbSalaire = getPaiementsByAgent(idAgent).stream()
                .filter(p -> p.getTypePaiement() == TypePaiement.SALAIRE)
                .count();

        double nbPrime = getPaiementsByAgent(idAgent).stream()
                .filter(p -> p.getTypePaiement() == TypePaiement.PRIME)
                .count();

        double nbIndemnite = getPaiementsByAgent(idAgent).stream()
                .filter(p -> p.getTypePaiement() == TypePaiement.INDEMNITE)
                .count();

        double nbBonus = getPaiementsByAgent(idAgent).stream()
                .filter(p -> p.getTypePaiement() == TypePaiement.BONUS)
                .count();

        System.out.println("salaire: " + nbSalaire);
        System.out.println("prime: " + nbPrime);
        System.out.println("indemnite : " + nbIndemnite);
        System.out.println("bonus: " + nbBonus);
    }

}
