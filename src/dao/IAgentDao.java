package dao;

import model.Agent;

import java.sql.SQLException;

public interface IAgentDao extends Dao<Agent> {
    Agent findByEmailAndPassword(String email, String password) throws SQLException;

}
