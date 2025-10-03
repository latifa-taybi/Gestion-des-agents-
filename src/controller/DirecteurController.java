package controller;

import model.Departement;
import model.Agent;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
import service.IDirecteurService;
import service.IPaiementService;

import java.sql.SQLException;

public class DirecteurController {

    private final IDirecteurService directeurService;
    private final IPaiementService paiementService;

    public DirecteurController(IDirecteurService directeurService, IPaiementService paiementService) {
        this.directeurService = directeurService;
        this.paiementService = paiementService;
    }

    public void creerDepartement(Departement departement) {
        try {
            directeurService.ajouterDepartement(departement);
            System.out.println("departement ajoute avec succes");
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void modifierDepartement(Departement departement) {
        try {
            directeurService.modifierDepartement(departement);
            System.out.println("departement modifie avec succes");
        } catch (DepartementIntrouvableException e) {
            System.err.println("departement introuvable");
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void supprimerDepartement(Departement departement) {
        try {
            directeurService.supprimerDepartement(departement);
            System.out.println("departement supprime avec succes");
        }catch (DepartementIntrouvableException e) {
            System.err.println("departement introuvable");
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void listerDepartements() {
        try {
            directeurService.getAllDepartements();
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void consulterAgentsByDepartement(){
        try{
            directeurService.consulterAgentsByDepartement();
        }catch (DatabaseException e){
            System.out.println("erreur : lors de la recuperation des agents");
        }
    }



    public void affecterResponsable(int idDepartement, Agent responsable) {
        try {
            directeurService.affecterResponsable(idDepartement, responsable);
            System.out.println("responsable affecte avec succes");
        }catch (DepartementIntrouvableException e) {
            System.err.println("departement introuvable");
        } catch (DatabaseException e) {
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
        } catch (DepartementIntrouvableException e) {
            System.err.println("departement introuvable");
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void getPaiementsByDepartement(int idDep){
        try {
             paiementService.getPaiementsByDepartement(idDep);
        } catch (DepartementIntrouvableException e) {
            System.err.println("departement introuvable");
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void totalPaiementsByDepartement(int idDep){
        try {
            double total = paiementService.calculerTotalPaiementsDepartement(idDep);
            System.out.println("le total des paiement de ce departement est: "+total);
        } catch (DepartementIntrouvableException e) {
            System.err.println("departement introuvable");
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public void nombresPaiementParType(int idAgent){
        try {
            paiementService.nombresPaiementParType(idAgent);
        } catch (AgentIntrouvableException e) {
            System.err.println("agent introuvable");
        } catch (DatabaseException e) {
            System.err.println("erreur : " + e.getMessage());
        }
    }

    public int getNombreTotalAgents(){
        return directeurService.getNombreTotalAgents();
    }
    public int getNombreTotalDep(){
        return directeurService.getNombreTotalDepartements();
    }


}
