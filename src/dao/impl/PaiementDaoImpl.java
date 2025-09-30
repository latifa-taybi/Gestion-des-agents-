package dao.impl;

import Util.DatabaseConnexion;
import dao.IPaiementDao;
import model.Agent;
import model.Paiement;
import model.Departement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaiementDaoImpl implements IPaiementDao {

    @Override
    public Paiement get(int id) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        Paiement paiement = null;

        String sql = """
            SELECT p.idPaiement, p.typePaiement, p.montant, p.datePaiement, p.motif, p.conditionValidee,
                   a.idAgent, a.nom AS agentNom, a.prenom AS agentPrenom, a.email, a.motDePasse, a.typeAgent,
                   d.idDepartement AS depId, d.nom AS depNom
            FROM paiements p
            JOIN agents a ON p.idAgent = a.idAgent
            JOIN departements d ON a.idDepartement = d.idDepartement
            WHERE p.idPaiement = ?
            """;

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idPaiement = rs.getInt("idPaiement");
            TypePaiement type = TypePaiement.valueOf(rs.getString("typePaiement"));
            double montant = rs.getDouble("montant");
            LocalDate date = rs.getDate("datePaiement").toLocalDate();
            String motif = rs.getString("motif");
            boolean conditionValidee = rs.getBoolean("conditionValidee");

            int idAgent = rs.getInt("idAgent");
            String nomAgent = rs.getString("agentNom");
            String prenomAgent = rs.getString("agentPrenom");
            String email = rs.getString("email");
            String motDePasse = rs.getString("motDePasse");
            TypeAgent typeAgent = TypeAgent.valueOf(rs.getString("typeAgent"));

            int depId = rs.getInt("depId");
            String depNom = rs.getString("depNom");

            Departement departement = new Departement(depId, depNom);
            Agent agent = new Agent(idAgent, nomAgent, prenomAgent, email, motDePasse, typeAgent);
            agent.setDepartement(departement);

            paiement = new Paiement(idPaiement, type, montant, date, motif, conditionValidee, agent);
        }
        return paiement;
    }

    @Override
    public List<Paiement> getAll() throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        List<Paiement> paiements = new ArrayList<>();

        String sql = """
            SELECT p.idPaiement, p.typePaiement, p.montant, p.datePaiement, p.motif, p.conditionValidee,
                   a.idAgent, a.nom AS agentNom, a.prenom AS agentPrenom, a.email, a.motDePasse, a.typeAgent,
                   d.idDepartement AS depId, d.nom AS depNom
            FROM paiements p
            JOIN agents a ON p.idAgent = a.idAgent
            JOIN departements d ON a.idDepartement = d.idDepartement
            """;

        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int idPaiement = rs.getInt("idPaiement");
            TypePaiement type = TypePaiement.valueOf(rs.getString("typePaiement"));
            double montant = rs.getDouble("montant");
            LocalDate date = rs.getDate("datePaiement").toLocalDate();
            String motif = rs.getString("motif");
            boolean conditionValidee = rs.getBoolean("conditionValidee");

            int idAgent = rs.getInt("idAgent");
            String nomAgent = rs.getString("agentNom");
            String prenomAgent = rs.getString("agentPrenom");
            String email = rs.getString("email");
            String motDePasse = rs.getString("motDePasse");
            TypeAgent typeAgent = TypeAgent.valueOf(rs.getString("typeAgent"));

            int depId = rs.getInt("depId");
            String depNom = rs.getString("depNom");

            Departement departement = new Departement(depId, depNom);
            Agent agent = new Agent(idAgent, nomAgent, prenomAgent, email, motDePasse, typeAgent);
            agent.setDepartement(departement);

            Paiement paiement = new Paiement(idPaiement, type, montant, date, motif, conditionValidee, agent);
            paiements.add(paiement);
        }
        return paiements;
    }

    @Override
    public Paiement insert(Paiement paiement) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "INSERT INTO paiements (typePaiement, montant, datePaiement, motif, conditionValidee, idAgent) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, paiement.getTypePaiement().name());
        stmt.setDouble(2, paiement.getMontant());
        stmt.setDate(3, Date.valueOf(paiement.getDate()));
        stmt.setString(4, paiement.getMotif());
        stmt.setBoolean(5, paiement.isConditionValidee());
        stmt.setInt(6, paiement.getAgent().getIdAgent());

        stmt.executeUpdate();

        return paiement;
    }

    @Override
    public Paiement update(Paiement paiement) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "UPDATE paiements SET typePaiement = ?, montant = ?, datePaiement = ?, motif = ?, conditionValidee = ?, idAgent = ? WHERE idPaiement = ?";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, paiement.getTypePaiement().name());
        stmt.setDouble(2, paiement.getMontant());
        stmt.setDate(3, Date.valueOf(paiement.getDate()));
        stmt.setString(4, paiement.getMotif());
        stmt.setBoolean(5, paiement.isConditionValidee());
        stmt.setInt(6, paiement.getAgent().getIdAgent());
        stmt.setInt(7, paiement.getIdPaiement());

        stmt.executeUpdate();
        return paiement;
    }

    @Override
    public void delete(Paiement paiement) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "DELETE FROM paiements WHERE idPaiement = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, paiement.getIdPaiement());
        stmt.executeUpdate();
    }
}
