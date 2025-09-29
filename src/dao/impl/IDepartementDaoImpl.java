package dao.impl;

import Util.DatabaseConnexion;
import dao.IDepartementDao;
import model.Departement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IDepartementDaoImpl implements IDepartementDao {

    @Override
    public Departement get(int id) throws SQLException {

        Connection con = DatabaseConnexion.getConnection();
        Departement departement = null;

        String sql = "SELECT * FROM departements WHERE idDepartement = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet result = stmt.executeQuery();

        if(result.next()){
            int idD = result.getInt("idDepartement");
            String nom = result.getString("nom");

            departement = new Departement(idD, nom);
        }
        return departement;
    }

    @Override
    public List<Departement> getAll() throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        List<Departement> departements = new ArrayList<>();

        String sql = "SELECT * FROM departements";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();

        while (result.next()){
            int idDep = result.getInt("idDepartement");
            String idNom = result.getString("nom");

            departements.add(new Departement(idDep, idNom));
        }


        return departements;
    }

    @Override
    public Departement insert(Departement departement) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "INSERT INTO departements (nom) values (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, departement.getNom());
        stmt.executeUpdate();
        return departement;
    }

    @Override
    public Departement update(Departement departement) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "UPDATE departements SET nom = ? WHERE idDepartement = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, departement.getNom());
        stmt.setInt(2,departement.getIdDepartement());

        stmt.executeUpdate();
        return departement;
    }

    @Override
    public void delete(Departement departement) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "DELETE FROM departements WHERE idDepartement = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, departement.getIdDepartement());
        stmt.executeUpdate();
    }
}
