package facades;

import DTOs.SportDTO;
import entities.Sport;
import errorhandling.exceptions.DatabaseException;
import errorhandling.exceptions.SportCreationException;
import errorhandling.exceptions.SportNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nikolaj Larsen
 */
public class SportFacade {
    
    private static EntityManagerFactory emf;
    private static SportFacade instance;

    private SportFacade (){
        
    }
    
    public static SportFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportFacade();
        }

        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public SportDTO createSport(String name, String description) throws DatabaseException, SportCreationException {
        if (name.isEmpty() || description.isEmpty()) {
            throw new SportCreationException("Not all sport credentials was provided.");
        }
        
        EntityManager em = getEntityManager();
        
        Sport sport = new Sport (name, description);
        
        try{
         if (userNameIsUsed(name)) {
                System.out.println("SPORT USED!");
                throw new SportCreationException("Sport already on list.");
            }
            em.getTransaction().begin();
            em.persist(sport);
            em.getTransaction().commit();
            
            return new SportDTO(sport);
        } catch (Exception e) {
            if (e instanceof SportCreationException) {
                throw e;
            }
            
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println(e);
            throw new DatabaseException("Something went wrong! Failed to create user, please try again later.");
        } finally {
            em.close();
        }
    }
    
    public List<SportDTO> getAllSports() {
        EntityManager em = getEntityManager();
        
        List<Sport> sports;
        List<SportDTO> sportDTOs = new ArrayList<>();
        
        try{
            Query query = em.createNamedQuery("Sport.getAll");
            sports = query.getResultList();
            
            sports.forEach(sport -> {
                sportDTOs.add(new SportDTO(sport));
            });
            
            return sportDTOs;
        } finally {
            em.close();
        }
    }
    
    public boolean userNameIsUsed(String name) {
        Sport sport = getSportByName(name);
        return sport != null;
    }
    
    public Sport getSportByName(String name) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Sport.findSport");
            query.setParameter("name", name);

            Sport sport = (Sport) query.getSingleResult();

            return sport;
        } catch (NoResultException e) {
            return null;
        }
    }
}
