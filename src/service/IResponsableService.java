package service;

import model.Agent;
import model.Departement;
import model.Paiement;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public interface IResponsableService {
    Agent ajouterAgent(Agent agent, Departement departement) throws SQLException;
    Agent modifierAgent(Agent agent) throws SQLException;
    void supprimerAgent(Agent agent) throws SQLException;
    List<Agent> getAgentsByDepartement(int idDepartement) throws SQLException;

    Paiement ajouterPaiement(Paiement paiement) throws SQLException;
    Paiement modifierPaiement(Paiement paiement) throws SQLException;
    void supprimerPaiement(Paiement paiement) throws SQLException;
    List<Paiement> getPaiementsAgentsDepartement(int idDepartement) throws SQLException;
    List<Paiement> filtrerPaiementsDepartement(int idDepartement, Predicate<Paiement> critere) throws SQLException;
    double calculerTotalPaiementsDepartement(int idDepartement) throws SQLException;
    double calculerMoyennePaiementsDepartement(int idDepartement) throws SQLException;
}
