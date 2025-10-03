package service;

import model.Paiement;
import model.enums.TypePaiement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
import model.exceptions.PaiementIntrouvableException;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public interface IPaiementService {
    Paiement ajouterPaiement(Paiement paiement) throws DatabaseException, AgentIntrouvableException;
    Paiement modifierPaiement(Paiement paiement) throws DatabaseException, PaiementIntrouvableException;
    boolean supprimerPaiement(Paiement paiement) throws DatabaseException, PaiementIntrouvableException;
    List<Paiement> getPaiementsByDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException;
    List<Paiement> getPaiementsByAgent(int idAgent) throws DatabaseException;
    double calculerTotalPaiements(int idAgent) throws DatabaseException;
    List<Paiement> filtrerPaiementsParType(int idAgent, TypePaiement type, Predicate<Paiement> critere) throws DatabaseException;
    List<Paiement> filtrerPaiementsParMontant(int idAgent, double montant, Predicate<Paiement> critere) throws DatabaseException;
    List<Paiement> filtrerPaiementsParDate(int idAgent, LocalDate date, Predicate<Paiement> critere) throws DatabaseException;

    double calculerTotalPaiementsDepartement(int idDepartement) throws DatabaseException;
    double calculerMoyennePaiementsDepartement(int idDepartement) throws DatabaseException;
    void nombresPaiementParType(int idAgent) throws DatabaseException;
}
