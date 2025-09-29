package model;

import model.enums.TypePaiement;

import java.time.LocalDate;

public class Paiement {
    private int idPaiement;
    private TypePaiement typePaiement;
    private double montant;
    private LocalDate date;
    private String motif;
    private boolean conditionValidee;
    private Agent agent;

    public Paiement(int idPaiement, TypePaiement typePaiement, double montant, LocalDate date, String motif, boolean conditionValidee, Agent agent) {
        this.idPaiement = idPaiement;
        this.typePaiement = typePaiement;
        this.montant = montant;
        this.date = date;
        this.motif = motif;
        this.conditionValidee = conditionValidee;
        this.agent = agent;
    }


    public int getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(int idPaiement) {
        this.idPaiement = idPaiement;
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public boolean isConditionValidee() {
        return conditionValidee;
    }

    public void setConditionValidee(boolean conditionValidee) {
        this.conditionValidee = conditionValidee;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
