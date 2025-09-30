package dao.impl;

import Util.DatabaseConnexion;
import dao.IAgentDao;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentDaoImpl implements IAgentDao {

    @Override
    public Agent get(int id) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        Agent agent = null;

        String sql = "SELECT agents.idAgent, agents.nom, agents.prenom, agents.email, agents.motDePasse, agents.typeAgent, departements.idDepartement AS depId, departements.nom AS depNom FROM agents JOIN departements ON agents.idDepartement = departements.idDepartement WHERE idAgent = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            int idA = rs.getInt("idAgent");
            String firstName = rs.getString("nom");
            String lastName = rs.getString("prenom");
            String email = rs.getString("email");
            String mdp = rs.getString("motDePasse");
            TypeAgent typeAgent = TypeAgent.valueOf(rs.getString("typeAgent"));
            int depId = rs.getInt("depId");
            String depNom = rs.getString("depNom");

            Departement dep = new Departement(depId, depNom);
            agent = new Agent(idA, firstName, lastName, email, mdp, typeAgent);
            agent.setDepartement(dep);
        }
        return agent;
    }

    @Override
    public List<Agent> getAll() throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT agents.idAgent, agents.nom, agents.prenom, agents.email, agents.motDePasse, agents.typeAgent, departements.idDepartement AS depId, departements.nom AS depNom FROM agents JOIN departements ON agents.idDepartement = departements.idDepartement";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        while (result.next()){
            int idA = result.getInt("idAgent");
            String nom = result.getString("nom");
            String prenom = result.getString("prenom");
            String email = result.getString("email");
            String motDePasse = result.getString("motDePasse");
            TypeAgent typeAgent = TypeAgent.valueOf(result.getString("typeAgent"));
            int depId = result.getInt("depId");
            String depNom = result.getString("depNom");

            Departement departement = new Departement(depId,depNom);
            Agent agent = new Agent(idA, nom, prenom, email, motDePasse, typeAgent);

            agent.setDepartement(departement);
            agents.add(agent);

        }
        return agents;
    }

    @Override
    public Agent insert(Agent agent) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "INSERT INTO agents (nom, prenom, email, motDePasse, typeAgent, idDepartement) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, agent.getNom());
        stmt.setString(2, agent.getPrenom());
        stmt.setString(3, agent.getEmail());
        stmt.setString(4, agent.getMotDePasse());
        stmt.setString(5, agent.getTypeAgent().name());
        stmt.setInt(6, agent.getDepartement().getIdDepartement());
        stmt.executeUpdate();

        return agent;
    }

    @Override
    public Agent update(Agent agent) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "UPDATE agents SET nom = ?, prenom = ?, email = ?, motDePasse = ?, typeAgent = ?, idDepartement = ? WHERE idAgent = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, agent.getNom());
        stmt.setString(2, agent.getPrenom());
        stmt.setString(3, agent.getEmail());
        stmt.setString(4, agent.getMotDePasse());
        stmt.setString(5, agent.getTypeAgent().name());
        stmt.setInt(6, agent.getDepartement().getIdDepartement());
        stmt.setInt(7, agent.getIdAgent());
        stmt.executeUpdate();

        return agent;
    }

    @Override
    public void delete(Agent agent) throws SQLException {
        Connection con = DatabaseConnexion.getConnection();
        String sql = "DELETE FROM agents WHERE idAgent = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, agent.getIdAgent());
        stmt.executeUpdate();
    }
}
