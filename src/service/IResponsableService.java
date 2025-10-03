package service;

import model.Agent;
import model.Departement;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
import java.util.List;

public interface IResponsableService {
    Agent ajouterAgent(Agent agent, Departement departement) throws DepartementIntrouvableException, DatabaseException;
    Agent modifierAgent(Agent agent) throws DatabaseException, AgentIntrouvableException;
    void supprimerAgent(Agent agent) throws DatabaseException, AgentIntrouvableException;
    List<Agent> getAgentsByDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException;
  }
