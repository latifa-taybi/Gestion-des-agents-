

import controller.AgentController;
import controller.DirecteurController;
import controller.ResponsableController;
import dao.IAgentDao;
import dao.IDepartementDao;
import dao.IPaiementDao;
import dao.impl.AgentDaoImpl;
import dao.impl.DepartementDaoImpl;
import dao.impl.PaiementDaoImpl;
import model.Agent;
import service.IAgentService;
import service.IDirecteurService;
import service.IPaiementService;
import service.IResponsableService;
import service.impl.AgentServiceImpl;
import service.impl.DirecteurServiceImpl;
import service.impl.PaiementServiceImpl;
import service.impl.ResponsableServiceImpl;
import view.AgentMenu;
import view.DirecteurMenu;
import view.ResponsableMenu;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        IAgentDao agentDao = new AgentDaoImpl();
        IPaiementDao paiementDao = new PaiementDaoImpl();
        IDepartementDao departementDao = new DepartementDaoImpl();

        IAgentService agentService = new AgentServiceImpl(agentDao);
        IResponsableService responsableService = new ResponsableServiceImpl(agentDao);
        IDirecteurService directeurService = new DirecteurServiceImpl(departementDao, agentDao);
        IPaiementService paiementService = new PaiementServiceImpl(agentDao, paiementDao);

        AgentController agentController = new AgentController(agentService, paiementService);
        ResponsableController responsableController = new ResponsableController(responsableService, paiementService);
        DirecteurController directeurController = new DirecteurController(directeurService, paiementService);

        boolean connected = false;

        do {
            System.out.println("----------------------S'AUTHENTIFIER------------------------");
            System.out.print("saisir votre email : ");
            String email = scanner.nextLine();
            System.out.print("saisir votre mot de passe : ");
            String mdp = scanner.nextLine();

            Agent agent = agentController.authentifier(email, mdp);
            if (agent == null){
                System.out.println("l'email ou le mot de passe est incorrecte");
            }else {
                connected = true;
                switch (agent.getTypeAgent()) {
                    case OUVRIER, STAGIAIRE -> {
                        AgentMenu menuAgent = new AgentMenu(agentController, agent.getIdAgent());
                        menuAgent.afficherMenu();
                    }
                    case RESPONSABLE_DEPARTEMENT -> {
                        int idDep = agent.getDepartement().getIdDepartement();
                        ResponsableMenu menuResponsable = new ResponsableMenu(responsableController, idDep, agentDao);
                        menuResponsable.afficherMenu();
                    }
                    case DIRECTEUR -> {
                        DirecteurMenu menuDirecteur = new DirecteurMenu(directeurController, agentService);
                        menuDirecteur.afficherMenu();
                    }
                    default -> System.out.println("le role inconnu");
                }
            }



        } while (!connected);

    }
}
