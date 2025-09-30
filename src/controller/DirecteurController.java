package controller;

import model.Departement;
import model.Agent;
import service.IDirecteurService;

import java.sql.SQLException;
import java.util.List;

public class DirecteurController {

    private final IDirecteurService directeurService;

    public DirecteurController(IDirecteurService directeurService) {
        this.directeurService = directeurService;
    }

    public void creerDepartement(Departement departement) {
        try {
            directeurService.ajouterDepartement(departement);
            System.out.println("departement ajoute avec succes");
        } catch (SQLException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void modifierDepartement(Departement departement) {
        try {
            directeurService.modifierDepartement(departement);
            System.out.println("departement modifie avec succes");
        } catch (SQLException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void supprimerDepartement(Departement departement) {
        try {
            directeurService.supprimerDepartement(departement);
            System.out.println("departement supprimer avec succes");
        } catch (SQLException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void listerDepartements() {
        try {
            List<Departement> departements = directeurService.getAllDepartements();
            departements.forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void affecterResponsable(int idDepartement, Agent responsable) {
        try {
            directeurService.affecterResponsable(idDepartement, responsable);
            System.out.println("responsable affecte avec succes");
        } catch (SQLException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void consulterResponsable(int idDepartement) {
        try {
            Agent responsable = directeurService.getResponsableDepartement(idDepartement);
            if (responsable != null) {
                System.out.println("le responsable de departement est: " + responsable);
            } else {
                System.out.println("pas de responsable");
            }
        } catch (SQLException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }
}
