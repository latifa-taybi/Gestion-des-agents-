package service.impl;

import dao.IAgentDao;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import model.exceptions.AgentIntrouvableException;
import model.exceptions.DatabaseException;
import model.exceptions.DepartementIntrouvableException;
import model.exceptions.TypeAgentInvalideException;
import service.IResponsableService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ResponsableServiceImpl implements IResponsableService {
    private final IAgentDao agentDao;

    public ResponsableServiceImpl(IAgentDao agentDao) {
        this.agentDao = agentDao;
    }

    @Override
    public Agent ajouterAgent(Agent agent, Departement departement) throws DepartementIntrouvableException,DatabaseException {
        try {
            if (departement == null) {
                throw new DepartementIntrouvableException("Département introuvable");
            }
            if(agent.getTypeAgent() == TypeAgent.OUVRIER || agent.getTypeAgent() == TypeAgent.STAGIAIRE){
                agent.setDepartement(departement);
                Agent agentInsere = agentDao.insert(agent);
                System.out.println("l'agent est ajoute avec succes");
                return agentInsere;
            }else {
                throw new TypeAgentInvalideException("l'agent doit etre soit ouvrier ou stagiaire");
            }
        }catch(SQLException e){
            throw new DatabaseException("erreur dans l'insertion d'agent");
        }
    }

    @Override
    public Agent modifierAgent(Agent agent) throws DatabaseException, DepartementIntrouvableException, AgentIntrouvableException {
        try {
            if(agent.getDepartement() == null){
                throw new DepartementIntrouvableException("le departement est introuvable");
            }
            return agentDao.update(agent);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la modification d'agent");
        }
    }

    @Override
    public void supprimerAgent(Agent agent) throws DatabaseException, AgentIntrouvableException {
        try {
            agentDao.delete(agent);
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la suppression d'agent");
        }
    }

    @Override
    public List<Agent> getAgentsByDepartement(int idDepartement) throws DatabaseException, DepartementIntrouvableException {
        try {
            return agentDao.getAll()
                    .stream()
                    .filter( a->a.getDepartement().getIdDepartement() == idDepartement)
                    .collect(Collectors.toList());
        }catch(SQLException e){
            throw new DatabaseException("erreur dans la recuperation d'agent");
        }
    }
}
