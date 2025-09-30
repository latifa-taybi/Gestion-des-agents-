package ui;

import controller.AgentController;
import controller.DirecteurController;
import controller.ResponsableController;
import dao.IAgentDao;
import dao.IDepartementDao;
import dao.IPaiementDao;
import dao.impl.AgentDaoImpl;
import dao.impl.DepartementDaoImpl;
import dao.impl.PaiementDaoImpl;
import service.IAgentService;
import service.IDirecteurService;
import service.IResponsableService;
import service.impl.AgentServiceImpl;
import service.impl.DirecteurServiceImpl;
import service.impl.ResponsableServiceImpl;
import view.AgentMenu;
import view.DirecteurMenu;
import view.ResponsableMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        IAgentDao agentDao = new AgentDaoImpl();
        IPaiementDao paiementDao = new PaiementDaoImpl();
        IDepartementDao departementDao = new DepartementDaoImpl();

        IAgentService agentService = new AgentServiceImpl(agentDao, paiementDao);
        IResponsableService responsableService = new ResponsableServiceImpl(agentDao, paiementDao);
        IDirecteurService directeurService = new DirecteurServiceImpl(departementDao, agentDao);

        AgentController agentController = new AgentController(agentService);
        ResponsableController responsableController = new ResponsableController(responsableService);
        DirecteurController directeurController = new DirecteurController(directeurService);

        int choix;

        do {
            System.out.println("----------------------MENU PRINCIPALE------------------------");
            System.out.println("1.Je suis agent");
            System.out.println("2.Je suis responsable");
            System.out.println("3.Je suis directeur");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 :
                {
                    System.out.print("ID Agent : ");
                    int idAgent = scanner.nextInt();
                    scanner.nextLine();
                    AgentMenu menuAgent = new AgentMenu(agentController, idAgent);
                    menuAgent.afficherMenu();
                }
                break;
                case 2 :
                {
                    ResponsableMenu menuResponsable = new ResponsableMenu(responsableController);
                    menuResponsable.afficherMenu();
                }
                break;
                case 3 :
                {
                    DirecteurMenu menuDirecteur = new DirecteurMenu(directeurController);
                    menuDirecteur.afficherMenu();
                }
                break;
                case 0 :
                    System.out.println("je suis quitter ");
                    break;
                default :
                    System.out.println("choix invalide");
            }

        } while (choix != 0);

    }
}
