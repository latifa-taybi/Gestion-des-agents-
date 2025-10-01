package service;

import model.Departement;
import model.Agent;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;

import java.sql.SQLException;
import java.util.List;

public interface IDirecteurService {
    Departement ajouterDepartement(Departement departement) throws DatabaseException;
    Departement modifierDepartement(Departement departement) throws DatabaseException, DepartementIntrouvableException;
    void supprimerDepartement(Departement departement) throws DatabaseException, DepartementIntrouvableException;
    List<Departement> getAllDepartements() throws DatabaseException;
    Departement getDepartementById(int idDepartement) throws DatabaseException, DepartementIntrouvableException;

    void affecterResponsable(int idDepartement, Agent responsable) throws DatabaseException, DepartementIntrouvableException;
    Agent getResponsableDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException;
}
