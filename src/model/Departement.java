package model;

import java.util.ArrayList;
import java.util.List;

public class Departement {
    private int idDepartement;
    private String nom;
    private List<Agent> agents = new ArrayList<>();

    public Departement(int idDepartement, String nom) {
        this.idDepartement = idDepartement;
        this.nom = nom;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    @Override
    public String toString() {
        return "{" +
                "idDepartement=" + idDepartement +
                ", nom='" + nom + '\'' +
                '}';
    }
}
