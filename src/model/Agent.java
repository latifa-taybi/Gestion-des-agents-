package model;

import model.enums.TypeAgent;

import java.util.List;

public class Agent extends Personne{
    private int idAgent;
    private TypeAgent typeAgent;
    private Departement departement;
    private List<Paiement> paiements;

    public Agent( int idAgent, String nom, String prenom, String email, String motDePasse, TypeAgent typeAgent) {
        super(nom, prenom, email, motDePasse);
        this.idAgent = idAgent;
        this.typeAgent = typeAgent;
    }



    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public TypeAgent getTypeAgent() {
        return typeAgent;
    }

    public void setTypeAgent(TypeAgent typeAgent) {
        this.typeAgent = typeAgent;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + idAgent +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", typeAgent=" + typeAgent +
                ", departement=" + (departement != null ? departement.getNom() : "NULL") +
                '}';
    }
}
