    package controller;

    import model.Agent;
    import model.Departement;
    import model.Paiement;
    import model.exceptions.DatabaseException;
    import service.IResponsableService;

    import java.util.List;
    import java.util.function.Predicate;

    public class ResponsableController {

        private final IResponsableService responsableService;

        public ResponsableController(IResponsableService responsableService) {
            this.responsableService = responsableService;
        }

        public void ajouterAgent(Agent agent, Departement departement) {
            try {
                responsableService.ajouterAgent(agent, departement);
                System.out.println("agent ajoute avec succes");
            } catch (DatabaseException e) {
                System.err.println("erreur: " + e.getMessage());
            }
        }

        public void modifierAgent(Agent agent) {
            try {
                responsableService.modifierAgent(agent);
                System.out.println("agent modifie avec succes");
            } catch (DatabaseException e) {
                System.err.println("erreur " + e.getMessage());
            }
        }

        public void supprimerAgent(Agent agent) {
            try {
                responsableService.supprimerAgent(agent);
                System.out.println("agent supprime avec succes");
            } catch (DatabaseException e) {
                System.err.println("erreur " + e.getMessage());
            }
        }

        public void listerAgents(int idDepartement) {
            try {
                List<Agent> agents = responsableService.getAgentsByDepartement(idDepartement);
                if (agents.isEmpty()) {
                    System.out.println("aucune agent trouve dans ce departement");
                } else {
                    agents.forEach(System.out::println);
                }
            } catch (DatabaseException e) {
                System.err.println("erreur " + e.getMessage());
            }
        }


        public void ajouterPaiement(Paiement paiement) {
            try {
                responsableService.ajouterPaiement(paiement);
                System.out.println("paiement ajoute avec succes");
            } catch (DatabaseException e) {
                System.err.println("erreur: " + e.getMessage());
            }
        }

        public void modifierPaiement(Paiement paiement) {
            try {
                responsableService.modifierPaiement(paiement);
                System.out.println("paiement modifie avec succes");
            } catch (DatabaseException e) {
                System.err.println("erreur: " + e.getMessage());
            }
        }

        public void supprimerPaiement(Paiement paiement) {
            try {
                responsableService.supprimerPaiement(paiement);
                System.out.println("paiement supprime avec succes");
            } catch (DatabaseException e) {
                System.err.println("erreur: " + e.getMessage());
            }
        }

        public void listerPaiementsDepartement(int idDepartement) {
            try {
                List<Paiement> paiements = responsableService.getPaiementsAgentsDepartement(idDepartement);
                if (paiements.isEmpty()) {
                    System.out.println("pas de paiement pour ce departement");
                } else {
                    paiements.forEach(System.out::println);
                }
            } catch (DatabaseException e) {
                System.err.println("erreur: " + e.getMessage());
            }
        }

        public void filtrerPaiementsDepartement(int idDepartement, Predicate<Paiement> critere) {
            try {
                List<Paiement> paiements = responsableService.filtrerPaiementsDepartement(idDepartement, critere);
                paiements.forEach(System.out::println);
            } catch (DatabaseException e) {
                System.err.println("erreur : " + e.getMessage());
            }
        }

        public void afficherStatistiquesDepartement(int idDepartement) {
            try {
                double total = responsableService.calculerTotalPaiementsDepartement(idDepartement);
                double moyenne = responsableService.calculerMoyennePaiementsDepartement(idDepartement);
                System.out.println("statistique su departement: " + idDepartement);
                System.out.println("total des paiements est: " + total );
                System.out.println("moyenne des paiements : " + moyenne);
            } catch (DatabaseException e) {
                System.err.println("erreur : " + e.getMessage());
            }
        }
    }
