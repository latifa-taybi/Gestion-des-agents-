package service;

import model.Departement;
import model.Agent;

import java.sql.SQLException;
import java.util.List;

public interface IDirecteurService {
    Departement ajouterDepartement(Departement departement) throws SQLException;
    Departement modifierDepartement(Departement departement) throws SQLException;
    void supprimerDepartement(Departement departement) throws SQLException;
    List<Departement> getAllDepartements() throws SQLException;
    Departement getDepartementById(int idDepartement) throws SQLException;

    void affecterResponsable(int idDepartement, Agent responsable) throws SQLException;
    Agent getResponsableDepartement(int idDepartement) throws SQLException;
}
