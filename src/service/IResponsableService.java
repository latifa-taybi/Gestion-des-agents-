package service;

import model.Agent;
import model.Departement;
import model.Paiement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
import model.exceptions.PaiementIntrouvableException;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public interface IResponsableService {
    Agent ajouterAgent(Agent agent, Departement departement) throws DepartementIntrouvableException, DatabaseException;
    Agent modifierAgent(Agent agent) throws DatabaseException, AgentIntrouvableException;
    void supprimerAgent(Agent agent) throws DatabaseException, AgentIntrouvableException;
    List<Agent> getAgentsByDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException;

    Paiement ajouterPaiement(Paiement paiement) throws DatabaseException, AgentIntrouvableException;
    Paiement modifierPaiement(Paiement paiement) throws DatabaseException, PaiementIntrouvableException;
    void supprimerPaiement(Paiement paiement) throws DatabaseException, PaiementIntrouvableException;
    List<Paiement> getPaiementsAgentsDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException;
    List<Paiement> filtrerPaiementsDepartement(int idDepartement, Predicate<Paiement> critere) throws DatabaseException, DepartementIntrouvableException;
    double calculerTotalPaiementsDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException;
    double calculerMoyennePaiementsDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException;
}
